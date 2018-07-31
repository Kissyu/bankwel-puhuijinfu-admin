package com.bankwel.phjf_admin.webapi;

import com.bankwel.framework.core.F;
import com.bankwel.framework.core.excep.MsgBusinessException;
import com.bankwel.framework.core.file.FileKit;
import com.bankwel.framework.core.kit.PageKit;
import com.bankwel.framework.core.kit.TpsLogoKit;
import com.bankwel.phjf_admin.common.model.core.AuthOperator;
import com.bankwel.phjf_admin.service.SysSensitivewordsService;
import com.bankwel.phjf_admin.support.jfconfig.PhjfAdminBaseController;
import com.jfinal.aop.Duang;
import com.jfinal.core.ActionKey;
import com.jfinal.upload.UploadFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileReader;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;

/**
 * @ClassName: SysSensitivewordsController
 * @Description: 敏感词 控制
 * @author: DukeMr.Chen
 * @date: 2018/3/13 15:32
 * @version: V1.0
 *
 */
public class SysSensitivewordsController extends PhjfAdminBaseController {
    private static final Logger log = LoggerFactory.getLogger(SysSensitivewordsController.class);

    private SysSensitivewordsService sysSensitivewordsService = Duang.duang(SysSensitivewordsService.class);

    /**
     * @Title:
     * @Description: 敏感词管理 页面跳转
     * @author: DukeMr.Chen
     */
    @ActionKey("/phjfht/api/v1/sensitiveWords/indexMainPage")
    public void indexMain(){
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            Map data = sysSensitivewordsService.queryMpSearchList();
            resultMap = F.transKit.asMap("code", 1, "msg", "", "data",data);
        } catch (MsgBusinessException e){
            e.printStackTrace();
            log.error("",e);
            resultMap = F.transKit.asMap("code", 2, "msg", e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            log.error("",e);
            resultMap = F.transKit.asMap("code", 2, "msg", "很抱歉！操作失败，请检查网络是否通畅！") ;
        }
        TpsLogoKit.addPoint(this.getRequest().getRequestURI(),tps_startTime,System.currentTimeMillis());
        this.setAttrs(resultMap);
        this.render("/WEB-INF/velocity/sensitiveWords/main.vm");
    }

    /**
     * @Title:
     * @Description: 敏感词 查询
     * @author: DukeMr.Chen
     */
    @ActionKey("/phjfht/api/v1/sensitiveWords/querySensitiveByPage")
    public void querySensitiveByPage(){
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String language = this.getPara("language");
            String words = this.getPara("words");
            String status = this.getPara("status");
            PageKit page = this.getBean(PageKit.class, "");
            resultMap = sysSensitivewordsService.querySensitiveByPage(language, words, status, page);
        } catch (MsgBusinessException e){
            e.printStackTrace();
            log.error("",e);
        } catch (Exception e){
            e.printStackTrace();
            log.error("",e);
        }
        TpsLogoKit.addPoint(this.getRequest().getRequestURI(),tps_startTime,System.currentTimeMillis());
        this.renderJson(resultMap);
    }

    /**
     * @Title:
     * @Description: 添加敏感词 跳转
     * @author: DukeMr.Chen
     */
    @ActionKey("/phjfht/api/v1/sensitiveWords/goAddPage")
    public void goAddPage() {
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try{
            this.checkSensitiveMsg();
            TpsLogoKit.addPoint(this.getRequest().getRequestURI(),tps_startTime,System.currentTimeMillis());
            this.setAttrs(F.transKit.asMap("code", 1, "msg", "", "data",""));
            this.renderJsp("/WEB-INF/velocity/sensitiveWords/add.jsp");
        }catch (MsgBusinessException e){
            e.printStackTrace();
            log.error("",e);
        }
    }

    /**
     * @Title:
     * @Description: 保存敏感词
     * @author: DukeMr.Chen
     */
    @ActionKey("/phjfht/api/v1/sensitiveWords/save")
    public void saveSensitiveWords() {
        UploadFile file = getFile();
        Map params = this.getReqParameterMap();
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), params);
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            AuthOperator opt = this.getUser();
            String language = this.getPara("language");
            String words = this.getPara("words");
            if(file != null ){
                String fileName= file.getFileName();
                String extensionType = fileName.substring(fileName.lastIndexOf("."));
                if(!".txt".equals(extensionType)){
                    throw new MsgBusinessException("大批量的敏感词导入的文件请选择文本文件（.txt）。");
                }
                File wordFile = file.getFile();
                if(wordFile.isFile() && wordFile.length() > 0){
                    words = FileKit.readToString(wordFile);
                }
            }
            sysSensitivewordsService.saveSensitiveWords(opt, language, words);
            returnMap = F.transKit.asMap("code", 1, "msg", "保存成功", "data","");
        } catch (MsgBusinessException e){
            e.printStackTrace();
            log.error("",e);
            returnMap = F.transKit.asMap("code", 2, "msg", e.getMessage(), "data", params);
        } catch (Exception e){
            e.printStackTrace();
            log.error("",e);
            returnMap = F.transKit.asMap("code", 2, "msg", "很抱歉！操作失败，请检查网络是否通畅！", "data", params) ;
        }
        TpsLogoKit.addPoint(this.getRequest().getRequestURI(),tps_startTime,System.currentTimeMillis());
        this.setAttrs(returnMap);
        if("1".equals(returnMap.get("code")+"")){
            this.render("/WEB-INF/velocity/sensitiveWords/main.vm");
        }else {
            this.renderJsp("/WEB-INF/velocity/sensitiveWords/add.jsp");
        }
    }

    /**
     * @Title:
     * @Description: 敏感词生效/失效
     * @author: DukeMr.Chen
     */
    @ActionKey("/phjfht/api/v1/sensitiveWords/setWordsStatus")
    public void setWordsStatus() {
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            AuthOperator user = this.getUser();
            String sw_id = this.getPara("sw_id");
            String status = this.getPara("status");
            sysSensitivewordsService.setWordsStatus(user, sw_id, status);
            returnMap = F.transKit.asMap("code", 1, "msg", "", "data","");
        } catch (MsgBusinessException e){
            e.printStackTrace();
            log.error("",e);
            returnMap = F.transKit.asMap("code", 2, "msg", e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            log.error("",e);
            returnMap = F.transKit.asMap("code", 2, "msg", "很抱歉！操作失败，请检查网络是否通畅！") ;
        }
        TpsLogoKit.addPoint(this.getRequest().getRequestURI(),tps_startTime,System.currentTimeMillis());
        this.renderJson(returnMap);
    }

    /**
     * @Title:
     * @Description: 导出
     * @author: DukeMr.Chen
     */
    @ActionKey("/phjfht/api/v1/sensitiveWords/bulkExportBtn")
    public void bulkExportBtn(){
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String language = this.getPara("language");
            String words = this.getPara("words");
            String status = this.getPara("status");
            String exportCon = sysSensitivewordsService.bulkExportBtn(language, words, status);
            String sFileName = "sensitive_" + new Date().getTime() + ".txt";
            this.getResponse().setHeader("Content-Disposition", "attachment;filename=".concat(URLEncoder.encode(sFileName, "UTF-8")));
            this.getResponse().setHeader("Connection", "close");
            this.getResponse().setHeader("Content-Type", "application/vnd.ms-excel;charset=UTF-8");
            BufferedOutputStream buff = new BufferedOutputStream(this.getResponse().getOutputStream());
            buff.write(exportCon.getBytes("UTF-8"));
            buff.flush();
            buff.close();
            resultMap = F.transKit.asMap("code", 1, "msg", "导出成功!", "data","");
        } catch (MsgBusinessException e){
            resultMap = F.transKit.asMap("code", 2, "msg", e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            log.error("",e);
        }
        TpsLogoKit.addPoint(this.getRequest().getRequestURI(),tps_startTime,System.currentTimeMillis());
    }
}
