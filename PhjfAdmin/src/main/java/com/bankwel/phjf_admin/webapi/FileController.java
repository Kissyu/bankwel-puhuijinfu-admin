package com.bankwel.phjf_admin.webapi;

import com.alibaba.fastjson.JSONObject;
import com.bankwel.framework.core.F;
import com.bankwel.framework.core.kit.TpsLogoKit;
import com.bankwel.phjf_admin.client.FileClient;
import com.bankwel.phjf_admin.common.constants.admin.AdminConstants;
import com.bankwel.phjf_admin.support.jfconfig.PhjfAdminBaseController;
import com.jfinal.core.ActionKey;
import com.jfinal.upload.UploadFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2017/11/23.
 */
public class FileController extends PhjfAdminBaseController {
    private static final Logger log = LoggerFactory.getLogger(FileController.class);

    @ActionKey("/phjfht/api/v1/file/goAddFilePage")
    public void goAddFilePage() {
        this.renderJsp("/WEB-INF/velocity/file/fileAdd.jsp");
    }

    /**
     * 图片上传
     */
    @ActionKey("/phjfht/api/v1/uploadPic")
    public void uploadPic() {
        Map map = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        Map resultMap = null;
        try {
            this.checkSensitiveMsg();
            resultMap = FileClient.uploadImg(this.getPara("img64", ""));
        } catch (Exception ex) {
            log.error("", ex);
            resultMap = F.transKit.asMap("code", 2, "msg", ex.getMessage(), "data", "");
        }
        this.renderJson(resultMap);
    }

    /**
     * 图片上传，自定义路径
     */
    @ActionKey("/phjfht/api/v1/uploadPicByPath")
    public void uploadPicByPath() {
        Map map = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        Map resultMap = null;
        try {
            this.checkSensitiveMsg();
            String img_path = this.getPara("img_path", "");
            resultMap = FileClient.uploadImg(img_path, this.getPara("img64", ""));
        } catch (Exception ex) {
            log.error("", ex);
            resultMap = F.transKit.asMap("code", 2, "msg", ex.getMessage(), "data", "");
        }
        this.renderJson(resultMap);
    }

    /**
     *  用于Ueditor传图片
     */
    @ActionKey("/phjfht/api/v1/uploadPicForUE")
    public void uploadPicForUeditor() {
        Map map = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        Map resultMap = null;
        try {
            this.checkSensitiveMsg();
            String img64 = this.getPara("img64");
            String extension = img64.substring(img64.indexOf("/") + 1, img64.indexOf(";base64"));
            String base64 = img64.substring(img64.indexOf(";base64,") + 8);
            resultMap = FileClient.uploadImg(AdminConstants.FILEUPLOAD_IMG, extension, base64, "yes");
            resultMap.put("state", "SUCCESS");
        } catch (Exception ex) {
            log.error("", ex);
            resultMap = F.transKit.asMap("state", "false");
        }
        this.renderJson(resultMap);
    }

    /**
     * 文件上传
     */
    @ActionKey("/phjfht/api/v1/uploadFile")
    public void uploadFile() {
        Map map = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        Map resultMap = null;
        try {
            this.checkSensitiveMsg();
            List<UploadFile> uf = getFiles();
            UploadFile files = uf.get(0);
            FileInputStream fileInput = new FileInputStream(files.getUploadPath()+"/"+files.getFileName());
            String filePath = files.getUploadPath()+"/"+files.getFileName();
            if (files.getFileName().contains("merch")){
                resultMap = FileClient.uploadMerchclientApk(files.getFileName(), fileInput, filePath);
            } else {
                resultMap = FileClient.uploadMainclientApk(files.getFileName(), fileInput, filePath);
            }
            File file = new File(files.getUploadPath()+"/"+files.getFileName());
            if(file.exists() && file.isFile()) {
                file.delete();
            }

        } catch (Exception ex) {
            log.error("", ex);
            resultMap = F.transKit.asMap("code", 2, "msg", ex.getMessage(), "data", "");
        }
        TpsLogoKit.addPoint(this.getRequest().getRequestURI(),tps_startTime,System.currentTimeMillis());
        this.renderJson(resultMap);
    }

    /**
     * 文件上传
     */
    @ActionKey("/phjfht/api/v1/uploadFileByPath")
    public void uploadFileByPath() {
        Map map = null;
        log.info("RequestURI=[{}] RealIpAddr=[{}] params=[{}]", this.getRequest().getRequestURI(), this.getRealIpAddr(), this.getReqParameterMap());
        long tps_startTime = System.currentTimeMillis();
        Map resultMap = null;
        try {
            this.checkSensitiveMsg();
            List<UploadFile> uf = getFiles();
            UploadFile files = uf.get(0);
            FileInputStream fileInput = new FileInputStream(files.getUploadPath()+"/"+files.getFileName());
            String filePath = files.getUploadPath()+"/"+files.getFileName();

            String uploadPath = this.getPara("uploadPath","");
            System.out.print(uploadPath);
            resultMap = FileClient.uploadFile(uploadPath, files.getFileName(), fileInput, filePath, "no");

            File file = new File(files.getUploadPath()+"/"+files.getFileName());
            if(file.exists() && file.isFile()) {
                file.delete();
            }

        } catch (Exception ex) {
            log.error("", ex);
            resultMap = F.transKit.asMap("code", 2, "msg", ex.getMessage(), "data", "");
        }
        TpsLogoKit.addPoint(this.getRequest().getRequestURI(),tps_startTime,System.currentTimeMillis());
        this.renderJson(resultMap);
    }
}
