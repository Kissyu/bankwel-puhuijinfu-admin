package com.bankwel.phjf_admin.webapi;

import com.bankwel.framework.core.F;
import com.bankwel.framework.core.excep.MsgBusinessException;
import com.bankwel.framework.core.kit.TpsLogoKit;
import com.bankwel.phjf_admin.common.constants.admin.AdminConstants;
import com.bankwel.phjf_admin.common.model.core.AuthOperator;
import com.bankwel.phjf_admin.common.model.core.WebPartner;
import com.bankwel.phjf_admin.support.SensitiveWordsInterceptor;
import com.bankwel.phjf_admin.support.jfconfig.PhjfAdminBaseController;
import com.bankwel.phjf_admin.webapi.vo.WebPartnerVO;
import com.jfinal.aop.Before;
import com.jfinal.core.ActionKey;
import com.jfinal.kit.PathKit;
import org.apache.commons.io.FileUtils;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.FileResourceLoader;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

/**
 * Created by bankwel on 2018/1/24.
 */
public class WebIndexController extends PhjfAdminBaseController {
    /**
     * 生成网站首页
     */
    //http://localhost:8081/phjfweb/api/v1/resetIndex
    @ActionKey("/phjfweb/api/v1/resetIndex")
    public void resetIndex() {
        Map resultMap = null;
        try {
         //   String language = AdminConstants.LanguageType.ZH_SIMP.value;
            //更新网站模板
            this.checkSensitiveMsg();
            String root = PathKit.getWebRootPath() + "/WEB-INF/template";
            System.out.println("classPath="+PathKit.getWebRootPath()+"     "+ root);
            FileResourceLoader resourceLoader = new FileResourceLoader(root,"utf-8");
            Configuration cfg = null;
            try {
                cfg = Configuration.defaultConfiguration();
            } catch (IOException e) {
                e.printStackTrace();
            }
            GroupTemplate gt = new GroupTemplate(resourceLoader, cfg);
            //注册全局共享变量
//            gt.setSharedVars(partners);
            Template t = gt.getTemplate("index.html");
           // t.binding("data", partners);
            String str = t.render();
            System.out.println(str);
            try {
                String dirPath =PathKit.getWebRootPath()+"/../../..";
                FileUtils.writeStringToFile(new File(dirPath+"/PhjfWebSite/src/main/webapp/html/index.html"), str,"UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
            resultMap = F.transKit.asMap("code", 1, "msg", "", "data","");
        }catch (MsgBusinessException e){
            e.printStackTrace();
            resultMap = F.transKit.asMap("code", 2, "msg", e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            resultMap = F.transKit.asMap("code", 2, "msg", "很抱歉！操作失败，请检查网络是否通畅！") ;
        }
    }
}
