package com.android.common.okgo;

/**
 * Created by Werwolf on 2017/4/18.
 */

public class HeadBean {

    /**
     * clientFlag : 25600001
     * reqNo : 1492506139735
     * secretKey : 111
     * service : test_service_and
     * version : 0.1.0
     */

    private String clientFlag;
    private String reqNo;
    private String secretKey;
    private String service;
    private String version;

    public String getClientFlag() {
        return clientFlag;
    }

    public void setClientFlag(String clientFlag) {
        this.clientFlag = clientFlag;
    }

    public String getReqNo() {
        return reqNo;
    }

    public void setReqNo(String reqNo) {
        this.reqNo = reqNo;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
