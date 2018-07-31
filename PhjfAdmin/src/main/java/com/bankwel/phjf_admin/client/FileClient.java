package com.bankwel.phjf_admin.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.bankwel.framework.core.F;
import com.bankwel.framework.core.excep.MsgBusinessException;
import com.bankwel.phjf_admin.common.constants.admin.AdminConstants;
import com.bankwel.phjf_admin.common.model.core.FileInfo;
import com.bankwel.phjf_admin.common.model.core.SysParam;
import com.jfinal.kit.JsonKit;
import com.jfinal.upload.UploadFile;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2017/11/23.
 */
public class FileClient {
    private static final Logger log = LoggerFactory.getLogger(FileClient.class);

    public static Map<String, Object> uploadImg(String busPath, String extension, String base64, String isDatePath) {
        Map<String, Object> resultMap = null;
        try{
            String requestJson = JsonKit.toJson(F.transKit.asMap("extension",extension,"busPath",busPath,"base64",base64,"isDatePath", isDatePath));
            String json = HttpInterConstants.BankFile_Upload_Img_INF.httpsPostJson(requestJson);
            if(F.validate.isNotEmpty(json)){
                JSONObject rMap = JSONObject.parseObject(json);
                if(F.validate.isNotEmpty(rMap)){
                    String code = rMap.getString("code");
                    if("1".equals(code)){
                        JSONObject data = rMap.getJSONObject("data");
                        resultMap = F.transKit.asMap("data", data);
//                        if(F.validate.isNotEmpty(data) && F.validate.isNotEmpty(data.get("url"))){
//                            result = data.getString("url");
//                        }
                    }else{
                        throw new MsgBusinessException("图片上传失败");
                    }
                }
            }
        }catch (MsgBusinessException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return resultMap;
    }


    /**
     * 上传文件至文件服务器
     * @param busPath
     * @param fileName
     * @param base64
     * @return
     */
    public static Map<String, Object> uploadFile(String busPath, String fileName, String base64, String isDatePath) {
        Map<String,Object> result = new HashedMap();
        try{
            String extension = fileName.substring(fileName.lastIndexOf(".")+1);
            fileName = fileName.substring(0,fileName.lastIndexOf("."));
            String requestJson = JsonKit.toJson(F.transKit.asMap("extension",extension,"busPath",busPath,"base64",base64,"fileName",fileName,"isDatePath", isDatePath));
            String json = HttpInterConstants.BankFile_Upload_File_INF.httpsPostJson(requestJson);
            if(F.validate.isNotEmpty(json)){
                JSONObject rMap = JSONObject.parseObject(json);
                if(F.validate.isNotEmpty(rMap)){
                    JSONObject data = rMap.getJSONObject("data");
//                    if(F.validate.isNotEmpty(data) && F.validate.isNotEmpty(data.get("url"))){
//                        result.put("url",data.getString("url"));
//                    }
                    result.put("data", data);
                    result.put("code",rMap.get("code").toString());
                    result.put("msg",rMap.get("msg").toString());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 上传文件至文件服务器
     * @param busPath
     * @param fileName
     * @param fileInput
     * @return
     */
    public static Map<String, Object> uploadFile(String busPath, String fileName, FileInputStream fileInput, String filePath, String isDatePath) {
        Map<String,Object> result = new HashedMap();
        try{
            String extension = fileName.substring(fileName.lastIndexOf(".")+1);
            fileName = fileName.substring(0,fileName.lastIndexOf("."));
            byte[] datas = null;
            try {
                datas = new byte[fileInput.available()];
                fileInput.read(datas);
                fileInput.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            FileInfo fileInfo = new FileInfo();
            fileInfo.setExtension(extension);
            fileInfo.setBusPath(busPath);
            fileInfo.setFileName(fileName);
            fileInfo.setFileStr(filePath);
            fileInfo.setData(datas);
            fileInfo.setFileInput(null);
            fileInfo.setIsDatePath(isDatePath);
            String requestJson = JSONObject.toJSONString(fileInfo);

            String json = HttpInterConstants.BankFile_Upload_File_INF.httpsPostJson(requestJson);
            if(F.validate.isNotEmpty(json)){
                JSONObject rMap = JSONObject.parseObject(json);
                if(F.validate.isNotEmpty(rMap)){
                    JSONObject data = rMap.getJSONObject("data");
//                    if(F.validate.isNotEmpty(data) && F.validate.isNotEmpty(data.get("url"))){
//                        result.put("url",data.getString("url"));
//                    }
                    result.put("data", data);
                    result.put("code",rMap.get("code").toString());
                    result.put("msg",rMap.get("msg").toString());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public static String getStrFromInputSteam(FileInputStream in){
        BufferedReader bf= null;
        try {
            bf = new BufferedReader(new InputStreamReader(in,"UTF-8"));
            //最好在将字节流转换为字符流的时候 进行转码
            StringBuffer buffer=new StringBuffer();
            String line = "";
            while((line = bf.readLine()) != null){
                buffer.append(line);
            }

            return buffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 上传图片
     * @param base64
     * @return
     */
    public static Map<String, Object> uploadImg(String base64){
        Map<String, Object> map = uploadImg(AdminConstants.FILEUPLOAD_IMG, AdminConstants.FILEUPLOAD_IMGTYPE_IMG, base64, "yes");
        return map;
    }

    /**
     * 上传图片
     * @param base64
     * @return
     */
    public static Map<String, Object> uploadImg(String img_path, String base64){
        Map<String, Object> map = uploadImg(AdminConstants.FILEUPLOAD_IMG+"/"+img_path, AdminConstants.FILEUPLOAD_IMGTYPE_IMG, base64, "no");
        return map;
    }

    /**
     * 上传用户端安卓安装包
     * @param fileInput
     * @return
     */
    public static Map<String,Object> uploadMainclientApk(String fileName, FileInputStream fileInput, String filePath){

        Map<String,Object> map = uploadFile(AdminConstants.FILEUPLOAD_APP_MAINCLIENT, fileName, fileInput, filePath, "no");
        return map;
    }

    /**
     * 上传商户端安卓安装包
     * @param fileInput
     * @return
     */
    public static Map<String,Object> uploadMerchclientApk(String fileName, FileInputStream fileInput, String filePath){
        Map<String,Object> map = uploadFile(AdminConstants.FILEUPLOAD_APP_MERCHCLIENT, fileName, fileInput, filePath, "no");
        return map;
    }


}
