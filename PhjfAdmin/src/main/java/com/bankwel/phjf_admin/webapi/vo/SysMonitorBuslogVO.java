package com.bankwel.phjf_admin.webapi.vo;

import com.bankwel.phjf_admin.common.model.core.SysMonitorBuslog;

public class SysMonitorBuslogVO extends SysMonitorBuslog {
    private String startTime;
    private String endTime;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
