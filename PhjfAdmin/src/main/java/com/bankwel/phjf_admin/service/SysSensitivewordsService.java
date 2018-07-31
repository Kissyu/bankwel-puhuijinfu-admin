package com.bankwel.phjf_admin.service;

import com.bankwel.framework.core.F;
import com.bankwel.framework.core.excep.MsgBusinessException;
import com.bankwel.framework.core.kit.PageKit;
import com.bankwel.phjf_admin.common.model.core.AuthOperator;
import com.bankwel.phjf_admin.common.model.core.ManagepointInfo;
import com.bankwel.phjf_admin.common.model.core.SysDatalibrary;
import com.bankwel.phjf_admin.common.model.core.SysSensitivewordsLibrary;
import com.bankwel.phjf_admin.support.PhjfDfaKit;
import com.jfinal.plugin.activerecord.Record;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

public class SysSensitivewordsService {

    /**
     * @Title:
     * @Description: 查询条件的获取
     * @author: DukeMr.Chen
     */
    public Map queryMpSearchList() {
        List<SysDatalibrary> sysLanguageList = SysDatalibrary.dao.querySysDatalibrary("Phjf","sys_language");
        List<SysDatalibrary> sysStatusList = SysDatalibrary.dao.querySysDatalibrary("Phjf","sys_status");
        return F.transKit.asMap("languageList", sysLanguageList, "sysStatusList", sysStatusList);
    }

    /**
     * @Title:
     * @Description: 分页查询 敏感词
     * @author: DukeMr.Chen
     */
    public Map querySensitiveByPage(String language, String words, String status, PageKit page) {
        Pair<List<Record>,PageKit<Record>> swIdsPage = SysSensitivewordsLibrary.dao.queryIdByPage(language, words, status, page);
        List<Record> idList = swIdsPage.getLeft();
        List<Map> sensitivewordsList = new ArrayList<Map>();
        for (Record r : idList) {
            SysSensitivewordsLibrary sensitiveWords= SysSensitivewordsLibrary.dao.findCacheById(language, "" + r.get("sw_id"));
            sensitivewordsList.add(F.transKit.asMap(
                    "sw_id", sensitiveWords.getSw_id()
                    , "seq_uuid", sensitiveWords.getSeq_uuid()
                    ,"status", sensitiveWords.getStatus()
                    ,"words", sensitiveWords.getWords()
                    ,"status_show", sensitiveWords.getStatusShow(language)
                    ,"create_time", sensitiveWords.getCreate_time()
            ));
        }
        return F.transKit.asMap("rows", sensitivewordsList, "total", swIdsPage.getRight().getTotal());
    }

    /**
     * @Title:
     * @Description: 添加敏感词
     * @author: DukeMr.Chen
     */
    public void saveSensitiveWords(AuthOperator opt, String language, String words) {
        if(F.validate.isEmpty(language)){
            throw new MsgBusinessException("语言为空");
        }
        if(F.validate.isEmpty(words)){
            throw new MsgBusinessException("请填入敏感词，多个敏感词，以英文逗号分割。");
        }
        int index = words.indexOf("，");
        if(index > -1){
            throw new MsgBusinessException("存在中文逗号分割，请检查，并改为英文逗号分割。");
        }
        String[] sensitiveWordsArray = words.split(",");
        List<SysSensitivewordsLibrary> sensitiveWordsList_add = new ArrayList<SysSensitivewordsLibrary>();
        List<SysSensitivewordsLibrary> sensitiveWordsList_update = new ArrayList<SysSensitivewordsLibrary>();
        for (String sensitiveWords : sensitiveWordsArray) {
            //去除重复敏感词
            if(PhjfDfaKit.contains(language, sensitiveWords)){
                continue;
            }
            SysSensitivewordsLibrary sensitivewords = SysSensitivewordsLibrary.dao.findObjectByWords(language, sensitiveWords);
            if(sensitivewords == null || !sensitiveWords.equals(sensitivewords.getWords())){
                SysSensitivewordsLibrary sysSensitivewordsLibrary= new SysSensitivewordsLibrary();
                sysSensitivewordsLibrary.setSeq_uuid(UUID.randomUUID().toString());
                sysSensitivewordsLibrary.setLanguage(language);
                sysSensitivewordsLibrary.setWords(sensitiveWords);
                sensitiveWordsList_add.add(sysSensitivewordsLibrary);
            }else if("4".equals(sensitivewords.getStatus())){
                SysSensitivewordsLibrary sysSensitivewordsLibrary= new SysSensitivewordsLibrary();
                sysSensitivewordsLibrary.setStatus("1");
                sysSensitivewordsLibrary.setSeq_uuid(sensitivewords.getSeq_uuid());
                sensitiveWordsList_update.add(sysSensitivewordsLibrary);
            }
        }
        if(sensitiveWordsList_update.size() > 0){
            SysSensitivewordsLibrary.dao.updateBatch(opt, language, sensitiveWordsList_update);
        }
        if(sensitiveWordsList_add.size() > 0){
            SysSensitivewordsLibrary.dao.addBatch(opt, language, sensitiveWordsList_add);
        }
    }

    /**
     * @Title:
     * @Description: 生效/失效 敏感词
     * @author: DukeMr.Chen
     */
    public void setWordsStatus(AuthOperator user, String sw_id, String status) {
        if(F.validate.isEmpty(sw_id)){
            throw new MsgBusinessException("敏感词序号不能为空");
        }
        SysSensitivewordsLibrary sysSensitivewords = SysSensitivewordsLibrary.dao.findById(sw_id);
        sysSensitivewords.setStatus("1".equals(status) ? "4" : "1");
        sysSensitivewords.saveOrUpdate(user);
    }

    /**
     * @Title:
     * @Description: 导出敏感词
     * @author: DukeMr.Chen
     */
    public String bulkExportBtn(String language, String words, String status) {
        StringBuffer sb = new StringBuffer();
        if(F.validate.isEmpty(language)){
            throw new MsgBusinessException("请选择导出的敏感词语言");
        }
        List<Record> list = SysSensitivewordsLibrary.dao.bulkExportBtn(language,status);
        for (Record r : list) {
            sb.append(r.get("words"))
            .append(",");
        }
        return sb.toString();
    }
}
