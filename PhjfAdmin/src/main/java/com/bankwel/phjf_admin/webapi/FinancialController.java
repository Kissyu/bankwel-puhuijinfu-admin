package com.bankwel.phjf_admin.webapi;

import com.bankwel.framework.core.F;
import com.bankwel.framework.core.excep.MsgBusinessException;
import com.bankwel.framework.core.kit.PageKit;
import com.bankwel.framework.core.kit.TpsLogoKit;
import com.bankwel.phjf_admin.common.model.core.AuthOperator;
import com.bankwel.phjf_admin.common.model.core.FinancialProduct;
import com.bankwel.phjf_admin.service.FinancialService;
import com.bankwel.phjf_admin.support.jfconfig.PhjfAdminBaseController;
import com.bankwel.phjf_admin.webapi.vo.FinancialProductVO;
import com.jfinal.aop.Duang;
import com.jfinal.core.ActionKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by Administrator on 2017/11/17.
 */
public class FinancialController extends PhjfAdminBaseController {
    private static final Logger log = LoggerFactory.getLogger(FinancialController.class);
    FinancialService financialService = Duang.duang(FinancialService.class);

    /**
     * @Title:
     * @Description: 修改理财产品信息
     * @author: DukeMr.Chen
     */
    @ActionKey("/phjfht/api/v1/financial/modifyFinancialProduct")
    public void modifyFinancialProduct() {
        Map params = this.getReqParameterMap();
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), params);
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            FinancialProductVO vo = this.getBean(FinancialProductVO.class,"");
            AuthOperator opt = this.getUser();
            FinancialProduct model = financialService.modifyFinancialProduct(opt,vo);
            Map data = financialService.findFpById(model.getSeq_uuid());
            returnMap = F.transKit.asMap("code", 1, "msg", "保存成功", "data",data);
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
        this.renderJsp("/WEB-INF/velocity/financial/financialProduct/financialProductDetail.jsp");
    }

    /**
     *
     * 理财产品信息 - 跳转理财产品信息查询页
     */
    //http://localhost:8081/phjfht/api/v1/financial/goFpQueryPage
    @ActionKey("/phjfht/api/v1/financial/goFpQueryPage")
    public void goFpQueryPage() {
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            Map data = financialService.queryFPSearchList();
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
        this.render("/WEB-INF/velocity/financial/financialProduct/financialProductQuery.vm");
    }

    /**
     * 理财产品信息 - 理财产品信息查询
     */
    //http://localhost:8081/phjfht/api/v1/financial/queryFpByPage
    @ActionKey("/phjfht/api/v1/financial/queryFpByPage")
    public void queryFpByPage() {
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String name = this.getPara("name");
            String risk_level = this.getPara("risk_level");
            String language = this.getPara("language");
            String status = this.getPara("status");
            PageKit page = this.getBean(PageKit.class, "");
            resultMap = financialService.queryFPByPage(name,risk_level,language,status, page);
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
     * 理财产品信息 - 跳转理财产品信息查看页
     */
    @ActionKey("/phjfht/api/v1/financial/goFpViewPage")
    public void goFpViewPage(){
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String seq_uuid = this.getPara("seq_uuid");
            Map data = financialService.findFpById(seq_uuid);
            returnMap = F.transKit.asMap("code", 1, "msg", "", "data",data);
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
        this.setAttrs(returnMap);
        String isModify = this.getPara("isModify", "");
        if("true".equals(isModify)){
            this.renderJsp("/WEB-INF/velocity/financial/financialProduct/financialProductModify.jsp");
        }else{
            this.renderJsp("/WEB-INF/velocity/financial/financialProduct/financialProductDetail.jsp");
        }
    }

    /**
     * 理财产品信息 - 下架
     */
    //http://localhost:8081/phjfht/api/v1/financial/unPublishFp
    @ActionKey("/phjfht/api/v1/financial/unPublishFp")
    public void unPublishFp() {
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String seq_uuid = this.getPara("seq_uuid");
            AuthOperator opt = this.getUser();
            financialService.unPublishFP(opt,seq_uuid);
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
     *
     * 货币基金产品管理 - 跳转货币基金产品信息查询页
     */
    //http://localhost:8081/phjfht/api/v1/financial/goFinancialFundQueryPage
    @ActionKey("/phjfht/api/v1/financial/goFinancialFundQueryPage")
    public void goFinancialFundQueryPage() {
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            Map data = financialService.queryFPSearchList();
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
        this.render("/WEB-INF/velocity/financial/financialFund/financialFundQuery.vm");
    }

    /**
     * 货币基金产品信息 - 货币基金产品信息查询
     */
    //http://localhost:8081/phjfht/api/v1/financial/queryFinancialFundByPage
    @ActionKey("/phjfht/api/v1/financial/queryFinancialFundByPage")
    public void queryFinancialFundByPage() {
        Map resultMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String title = this.getPara("title");
            String language = this.getPara("language");
            String status = this.getPara("status");
            PageKit page = this.getBean(PageKit.class, "");
            resultMap = financialService.queryFinancialFundByPage(title,language,status, page);
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
     * 理财产品信息 - 跳转理财产品信息查看页
     */
    @ActionKey("/phjfht/api/v1/financial/goFinancialFundViewPage")
    public void goFinancialFundViewPage(){
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String seq_uuid = this.getPara("seq_uuid");
            Map data = financialService.findFinancialFundById(seq_uuid);
            returnMap = F.transKit.asMap("code", 1, "msg", "", "data",data);
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
        this.setAttrs(returnMap);
        this.renderJsp("/WEB-INF/velocity/financial/financialFund/financialFundDetail.jsp");
    }
    /**
     * 理财产品信息 - 下架
     */
    //http://localhost:8081/phjfht/api/v1/financial/unPublishFinancialFund
    @ActionKey("/phjfht/api/v1/financial/unPublishFinancialFund")
    public void unPublishFinancialFund() {
        Map returnMap = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        try {
            this.checkSensitiveMsg();
            String seq_uuid = this.getPara("seq_uuid");
            AuthOperator opt = this.getUser();
            financialService.unPublishFinancialFund(opt,seq_uuid);
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
}
