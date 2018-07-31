package com.bankwel.phjf_admin.component.c11assistant;


import com.bankwel.framework.core.F;
import com.bankwel.phjf_admin.common.model.core.AuthOperator;
import com.bankwel.phjf_admin.component.c10monitor.MRequestHeader;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URLDecoder;
import java.util.Date;
import java.util.Enumeration;

/**
 * C11_6 - Servlet Helper Component
 * Servlet处理助手
 * @author sln
 *
 */
public class ServletHelper {


	public static final String StartDateStr = F.dateKit.translate2Str(new Date(),"yyyyMMddHHmmss");

	/**
	 * 获取浏览器随机版本
	 * @return
	 */
	public static String getPVersion(){
		return StartDateStr;
	}

	/**
	 * 获取HTTP客户端浏览器版本及型号
	 * @param req
	 * @return
	 */
	public static String getHttpClientAgent(HttpServletRequest req){
		return req.getHeader("User-Agent");
	}
	
	/**
	 * 获取HTTP客户端IP地址 
	 * @param req
	 * @return
	 */
	public static String getHttpClientIpAddr(HttpServletRequest req) {
	    String ip = req.getHeader("x-forwarded-for");  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = req.getHeader("Proxy-Client-IP");  
	    }  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = req.getHeader("WL-Proxy-Client-IP");  
	    }  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = req.getRemoteAddr();  
	    }  
	    return ip;  
	}
	
	/**
	 * 获取HTTP请求URL链接完整地址
	 * @param req
	 * @return
	 */
	public static String getHttpRequestURL(HttpServletRequest req){
		return req.getRequestURL().toString();
	}
	
	/**
	 * 获取HTTP请求完整参数
	 * @param req
	 * @return
	 */
	public static String getHttpRequestQueryString(HttpServletRequest req){
		StringBuffer qStr = new StringBuffer();
		Enumeration<String> e = req.getParameterNames();
        String parameterName, parameterValue = "";
        while(e.hasMoreElements()){
            parameterName = e.nextElement();
            if("nowPage".equals(parameterName)){continue;}
            try {
            	parameterValue=URLDecoder.decode(req.getParameter(parameterName).replaceAll("<YfdID<><", "#").replaceAll("%", "%25"),"UTF-8");
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
            TransHelper.stringBufferAppender(qStr,parameterName,"=",parameterValue,"&");
        }
//        System.out.println(qStr);
        return qStr.toString();
	}
	
	/**
	 * 获取HTTP请求信息
	 * @param req
	 * @return
	 */
	public static MRequestHeader getMRequestHeader(HttpServletRequest req){
		return new MRequestHeader(req);
	}

	/**
	 * 获取服务端IP地址列表
	 * @return
	 */
	public static String getHostIp() {
		try{
			Enumeration allNetInterfaces = NetworkInterface.getNetworkInterfaces();
			InetAddress ip = null;
			while (allNetInterfaces.hasMoreElements()){
				NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
				Enumeration addresses = netInterface.getInetAddresses();
				while (addresses.hasMoreElements()){
					ip = (InetAddress) addresses.nextElement();
					if (ip != null && ip instanceof Inet4Address){
						System.out.println("网卡名=["+netInterface.getName()+"] 本机的IP = " + ip.getHostAddress());
					} 
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			return " ip not found!";
		}
		return "";
	}
	
	public static AuthOperator getSessionUser(HttpServletRequest request) {
        AuthOperator operator = (AuthOperator) request.getSession().getAttribute("operator");
		if (operator==null) {
//			operator=new AuthOperator();
//			operator.setSeq_id(1);
//			User userSession=(User) request.getSession().getAttribute("CF_USER");
//			operator.setBranch_id(userSession.getDid());
//			operator.setUserName(userSession.getOptName());
//			operator.setSex_code(userSession.getSex());
//			operator.setEmail(userSession.getMail());
//			operator.setStatus(userSession.getStatus());
//			operator.setPassword(userSession.getOptpass());
//			operator.setUser_type(userSession.getOptType());
////			operator.setSeq_id();
//			operator.setEmp_id(userSession.getOptID());
		}
		return operator;
	}
}
