package com.bankwel.phjf_admin.webapi;

import com.bankwel.phjf_admin.support.jfconfig.PhjfAdminBaseController;
import com.jfinal.core.ActionKey;
import com.jfinal.kit.PathKit;
import org.apache.commons.io.FileUtils;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.FileResourceLoader;

import java.io.File;
import java.io.IOException;

/**
 * Created by admin on 2018/1/19.
 */
public class BeetlController extends PhjfAdminBaseController {

    @ActionKey("/beetl/test1")
    public void test1() {
        String root = PathKit.getWebRootPath() + "/WEB-INF/template";
        System.out.println("classPath="+PathKit.getWebRootPath()+"     "+ root);
        FileResourceLoader resourceLoader = new FileResourceLoader(root,"utf-8");
        Configuration cfg = null;
        try {
            this.checkSensitiveMsg();
            cfg = Configuration.defaultConfiguration();
        } catch (IOException e) {
            e.printStackTrace();
        }
        GroupTemplate gt = new GroupTemplate(resourceLoader, cfg);
        Template t = gt.getTemplate("test.html");
        t.binding("test", "hold word test 2");
        t.binding("lxwm","/static_html/testq.html");
        String str = t.render();
        System.out.println(str);

        try {
            FileUtils.writeStringToFile(new File("/Users/admin/Documents/1/index.html"), str);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        this.render("/WEB-INF/template/test.html");
    }
}
