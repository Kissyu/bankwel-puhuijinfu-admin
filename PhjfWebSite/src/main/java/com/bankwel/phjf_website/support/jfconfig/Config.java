package com.bankwel.phjf_website.support.jfconfig;

import com.bankwel.phjf_website.common.model.website._PhjfMappingKit;
import com.bankwel.framework.core.encrypt.AES;
import com.bankwel.framework.support.jfinal.VelocityLayoutRenderFactory;
import com.jfinal.config.*;
import com.jfinal.ext.handler.ContextPathHandler;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.bankwel.framework.support.jfinal.AutoBindRoutes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Generated by su_ln on 2016/2/5.
 */
public class Config extends JFinalConfig {

    private static final Logger log = LoggerFactory.getLogger(Config.class);

    public static AES aes = AES.getInstance("BfjDbKeyPass2017");

    public void configConstant(Constants me) {
        PropKit.use("config.properties");

        me.setDevMode(true);
       // me.setViewType(ViewType.VELOCITY);
        //me.setViewType(ViewType.JSP);
        me.setEncoding("UTF-8");
        me.setRenderFactory(new VelocityLayoutRenderFactory());

    }

    public void configRoute(Routes me) {
        AutoBindRoutes bind = new AutoBindRoutes();
        bind.regist(me,"com.bankwel.phjf_website");

    }

    public void configPlugin(Plugins me) {
        DruidPlugin DBPlugin = new DruidPlugin(PropKit.get("public.db.url"), PropKit.get("public.db.username"), aes.decode(PropKit.get("public.db.password")));
        DBPlugin.setInitialSize(1) //配置初始化大小
                .setMinIdle(1) //配置最小数量
                .setMaxActive(10) //配置最大数量
                .setTimeBetweenEvictionRunsMillis(60000) //<!-- 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒 -->
                .setMinEvictableIdleTimeMillis(300000) //配置一个连接在池中最小生存的时间，单位是毫秒
                .setValidationQuery("select 1")
//                .setMaxPoolPreparedStatementPerConnectionSize(200)
                .setTestWhileIdle(true).setTestOnBorrow(true).setTestOnReturn(true);
//                .setFilters("stat");
        me.add(DBPlugin);

        ActiveRecordPlugin arpPublic = new ActiveRecordPlugin("DBPublic", DBPlugin);
        _PhjfMappingKit.mapping(arpPublic);
        arpPublic.setShowSql(true);

        me.add(arpPublic);

//        me.add(new SpringPlugin());

    }

    public void configInterceptor(Interceptors me) {
    }

    public void configHandler(Handlers me) {
        me.add(new ContextPathHandler("ctx"));
//        DruidStatViewHandler dvh =  new DruidStatViewHandler("/druid");
//        me.add(dvh);
    }

    public void configEngine(com.jfinal.template.Engine var1){
    }


}