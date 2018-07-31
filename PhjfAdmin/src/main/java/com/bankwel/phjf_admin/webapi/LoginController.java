package com.bankwel.phjf_admin.webapi;

import com.bankwel.framework.core.F;
import com.bankwel.framework.core.kit.TpsLogoKit;
import com.bankwel.framework.support.codis.RedisClient;
import com.bankwel.phjf_admin.auth.service.AuthOperatorService;
import com.bankwel.phjf_admin.auth.webapi.AuthBranchController;
import com.bankwel.phjf_admin.common.constants.admin.AdminConstants;
import com.bankwel.phjf_admin.common.model.core.AuthBranch;
import com.bankwel.phjf_admin.common.model.core.AuthOperator;
import com.bankwel.phjf_admin.common.model.core.SysApplyCenter;
import com.bankwel.phjf_admin.service.LoginService;
import com.bankwel.phjf_admin.service.SystemService;
import com.bankwel.phjf_admin.support.FlowControlKit;
import com.bankwel.phjf_admin.support.jfconfig.PhjfAdminBaseController;
import com.bankwel.phjf_baseModel.common.model.phjf.enumKey.FlowControlEnum;
import com.jfinal.aop.Duang;
import com.jfinal.core.ActionKey;
import com.jfinal.kit.PropKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import java.util.*;

/**
 * Created by admin on 2017/9/18.
 */
public class LoginController extends PhjfAdminBaseController {

    private static final Logger log = LoggerFactory.getLogger(AuthBranchController.class);
    private AuthOperatorService operatorService = Duang.duang(AuthOperatorService.class);
    private LoginService loginService = Duang.duang(LoginService.class);
    SystemService systemService = Duang.duang(SystemService.class);
    /**
     * 初始入口
     *
     * @return
     */
    @ActionKey(value = "/")
    public void index() {
        this.render("/WEB-INF/velocity/login.vm");
    }
    @ActionKey("/checkLogin")
    public void checkLogin(){
        try{
            this.checkSensitiveMsg();
            String method = getRequest().getMethod();
            if("POST".equalsIgnoreCase(method)){
                String realIpAddr = this.getRealIpAddr();
                String dateStr = F.dateKit.translate2Str(new Date(),"yyyyMMdd");
                String errMsg = "该IP今日密码输入错误次数已达到上线，IP已被添加黑名单。次日系统自动移除。";
                boolean cacheStatus = FlowControlKit.cacheIsOpen();
                if(FlowControlKit.ruleIpLoginAttack(FlowControlEnum.CK_PHJF_SYS_FC_IPLOGINFLKEY.getKey(),realIpAddr, AdminConstants.PLATFORM)){
                    String operator_name = this.getPara("userName");
                    String password = this.getPara("password");
                    String apply_center_seq_id = this.getPara("apply_center_seq_id");

                    errMsg = "今日密码输入错误次数已达到上线，账号已被锁定。次日系统自动解锁，或联系管理员进行解锁。";
                    //如果密码错误达到配置次数，锁定一个自然日。
                    boolean passwordErrLock = false;
                    if(cacheStatus){//缓存开启用缓存策略
                        passwordErrLock = FlowControlKit.ruleUserLoginErr(FlowControlEnum.CK_PHJF_SYS_FC_USERLOGINERRKEY.getKey(), operator_name, AdminConstants.PLATFORM);
                    }else{//缓存没开启用登录日志策略
                        String loginDate = F.dateKit.translate2Str(new Date(),"yyyyMMdd");
                        passwordErrLock = FlowControlKit.ruleUserLoginErr(loginService.getLoginErrCount(operator_name,loginDate,true));
                    }
                    if(passwordErrLock){
                        String ilflKey = F.strKit.f(FlowControlEnum.CK_PHJF_SYS_FC_IPLOGINFLKEY.getKey(), dateStr, realIpAddr, AdminConstants.PLATFORM);
                        String userLoginErrKey = F.strKit.f(FlowControlEnum.CK_PHJF_SYS_FC_USERLOGINERRKEY.getKey(), dateStr, operator_name, AdminConstants.PLATFORM);
                        AuthOperator authOperator = operatorService.getAuthOperator(operator_name, password);
                        String code = "0";
                        String message = "";
                        List<SysApplyCenter> result = null;
                        if (F.validate.isEmpty(authOperator)){
                            code = "-1";
                            message = "用户名或密码错误！";
                            //登陆次数控制
                            RedisClient.getClient().incr(userLoginErrKey, FlowControlEnum.CK_PHJF_SYS_FC_USERLOGINERRKEY.getTime());
                            RedisClient.getClient().incr(ilflKey, FlowControlEnum.CK_PHJF_SYS_FC_IPLOGINFLKEY.getTime());
                            loginService.logData(operator_name,realIpAddr,"2");
                        }else {
                            AuthOperator authOperator2 = (AuthOperator)this.getRequest().getSession().getAttribute("operator");
                            if(F.validate.isEmpty(authOperator2)){
                                //验证码校验
                                String verifyCode = this.getPara("verifyCode");
                                if(F.validate.isEmpty(verifyCode)){
                                    code = "-1";
                                    message = "手机验证码不能为空";
                                }else{
                                    boolean flag = systemService.checkCode(authOperator.getMobile(), AdminConstants.Sms_Type_IPLOGINCHECK,verifyCode);
                                    if(flag){
                                        if(F.validate.isEmpty(apply_center_seq_id)) {
                                            //插入登录日志表一条记录
                                            loginService.logData(authOperator.getOperate_name(),realIpAddr,"1");
                                            code = "1";
                                            message = "请选择应用中心！";
                                            this.getRequest().getSession().setAttribute("operator", authOperator);
                                    /* Token的处理*/
                                            String DukeToken = UUID.randomUUID().toString();
                                            String tokenKey = PropKit.use("config.properties").get("phjf.catch.sys.patch") + authOperator.getSeq_id();
                                            this.getRequest().getSession().setAttribute("DukeToken", DukeToken);
                                            operatorService.setToken(authOperator.getSeq_id(), DukeToken);
                                            RedisClient.getClient().set(tokenKey + "", DukeToken);

                                            //如果登录成功清空错误次数记录
                                            RedisClient.getClient().set(userLoginErrKey, 5, 0l);
                                            loginService.logData(operator_name, realIpAddr, "1");
                                            result = AuthOperator.dao.findApplyList(authOperator.getSeq_id() + "");
                                        }
                                    }else{
                                        code = "-1";
                                        message = "手机验证码错误";
                                    }
                                }
                            }else{
                                if(F.validate.isEmpty(apply_center_seq_id)) {
                                    code = "1";
                                    message = "请选择应用中心！";
                                    result = AuthOperator.dao.findApplyList(authOperator.getSeq_id() + "");
                                }
                            }
                        }
                        this.renderJson(F.transKit.asMap("code", code, "message", message, "applyList", result, "DukeToken", this.getRequest().getSession().getAttribute("DukeToken")));
                    }else{
                        this.renderJson(F.transKit.asMap("code", "-1", "message", errMsg, "applyList", ""));
                    }
                }else{
                    this.renderJson(F.transKit.asMap("code", "-1", "message", errMsg, "applyList", ""));
                }
            }else{
                this.renderJson(F.transKit.asMap("code", "-1", "message", "非法请求", "applyList", ""));
            }
        }catch (Exception e){
            this.renderJson(F.transKit.asMap("code", "-1", "message", e.getMessage(), "applyList", ""));
        }
    }



    @ActionKey("/login")
    public void login(){
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        String method = getRequest().getMethod();
        //if("POST".equalsIgnoreCase(method)) {

            Map map = null;
            try {
                this.checkSensitiveMsg();
                AuthOperator authOperator = this.getUser();
                if (F.validate.isEmpty(authOperator)) {
                    this.render("/WEB-INF/velocity/login.vm");
                }
                List<SysApplyCenter> applyCenters = AuthOperator.dao.findApplyList("" + authOperator.getSeq_id());
                List<AuthBranch> branches = AuthOperator.dao.queryBranchList("" + authOperator.getSeq_id());
                String apply_seq_id = this.getPara("apply_center_seq_id");
                authOperator.setApply_center_seq_id(Integer.parseInt(apply_seq_id));
                this.getRequest().getSession().setAttribute("operator", authOperator);
                map = F.transKit.asMap("operator", authOperator, "apply_seq_id", apply_seq_id, "applyList", applyCenters, "branchList", branches);

            } catch (Exception e) {
                e.printStackTrace();
                log.error("", e);
            }
            this.setAttrs(map);
            this.render("/WEB-INF/velocity/index.vm");
//        }else{
//            this.render("/WEB-INF/velocity/login.vm");
//        }
        TpsLogoKit.addPoint(this.getRequest().getRequestURI(),tps_startTime,System.currentTimeMillis());
    }

    @ActionKey(value = "/logout")
    public void logout() {
        //方式一
//        this.getRequest().changeSessionId();
        //方式二
        this.getRequest().getSession(false).invalidate();
        //方式三
        Cookie cookie = new Cookie("JSESSIONID","");
        this.getResponse().addCookie(cookie);
        this.getRequest().getSession().removeAttribute("operator");
        this.getRequest().getSession().removeAttribute("DukeToken");

        this.render("/WEB-INF/velocity/login.vm");
    }

}
