package com.bankwel.phjf_admin.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

public class RealIpAddrKit {
    private static final Logger log = LoggerFactory.getLogger(RealIpAddrKit.class);
    public static String getRealIpAddr(HttpServletRequest request) {
        //HttpServletRequest request = this.getRequest();
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        if ("0:0:0:0:0:0:0:1".equals(ip)) {
            ip = "127.0.0.1";
        }

        if (ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }

        log.info("本次登录的用户IP地址：" + ip);
        return ip;
    }
}
