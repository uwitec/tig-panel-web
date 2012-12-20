package com.web.form.administration;

import com.web.form.base.BaseForm;

public class UserOprLog extends BaseForm {
    
    private String unitid;

    private Integer logdate;

    private Integer logtime;

    private String username;

    private String hostip;

    private String msg;

    public String getUnitid() {
        return unitid;
    }

    public void setUnitid(String unitid) {
        this.unitid = unitid;
    }

    public Integer getLogdate() {
        return logdate;
    }

    public void setLogdate(Integer logdate) {
        this.logdate = logdate;
    }

    public Integer getLogtime() {
        return logtime;
    }

    public void setLogtime(Integer logtime) {
        this.logtime = logtime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHostip() {
        return hostip;
    }

    public void setHostip(String hostip) {
        this.hostip = hostip;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}