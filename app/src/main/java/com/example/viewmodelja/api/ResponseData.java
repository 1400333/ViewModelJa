package com.example.viewmodelja.api;

import org.json.JSONObject;

public class ResponseData {
    private static final String JSON_TAG_API_MSG = "message";

    private static final int API_CODE_CONNECTION_TIMEOUT = 8999;
    private static final int API_CODE_CONNECTION_ERROR = 8998;
    private static final int API_CODE_ERROR = 9999;
    private static final int API_CODE_SUCCESS = 200;

    private RequestData m_requestData = null;
    private int m_iHttpStatus = -1;
    private int m_iApiCode = 0;
    private String m_strApiMsg = null;
    private JSONObject m_joRespData = null;

    public static ResponseData constructResp(int iHttpStatus, JSONObject joResp) {
        ResponseData ret = new ResponseData();

        ret.setHttpStatus(iHttpStatus);

        if (iHttpStatus == 200) {
            ret.setApiCode(API_CODE_SUCCESS);
        } else {
            ret.setApiCode(API_CODE_ERROR);
        }

        ret.setApiMsg(joResp.optString(JSON_TAG_API_MSG));
        ret.setRespBody(joResp);

        return ret;
    }

    public static ResponseData constructConnTimeoutResp() {
        ResponseData ret = new ResponseData();
        ret.setApiCode(API_CODE_CONNECTION_TIMEOUT);

        return ret;
    }

    public static ResponseData constructConnErrorResp(int iHttpStatus) {
        ResponseData ret = new ResponseData();
        ret.setHttpStatus(iHttpStatus);
        ret.setApiCode(API_CODE_CONNECTION_ERROR);

        return ret;
    }

    public void setRequestData(RequestData trunkRequestData) {
        m_requestData = trunkRequestData;
    }

    public void setHttpStatus(int iHttpStatus) {
        m_iHttpStatus = iHttpStatus;
    }

    public void setApiCode(int iApiCode) {
        m_iApiCode = iApiCode;
    }

    public void setApiMsg(String strApiMsg) {
        m_strApiMsg = strApiMsg;
    }

    public void setRespBody(JSONObject joBody) {
        m_joRespData = joBody;
    }

    public JSONObject getRespBody() {
        return m_joRespData;
    }

    public String getApiMsg() {
        return m_strApiMsg;
    }

    public boolean isBlocking(){
        return m_requestData.isBlocking();
    }

    public boolean isConnTimeout() {
        return m_iApiCode == API_CODE_CONNECTION_TIMEOUT;
    }

    public boolean isConnError() {
        return m_iApiCode == API_CODE_CONNECTION_ERROR;
    }

    public boolean isSuccess() {
        return m_iApiCode == API_CODE_SUCCESS;
    }

    @Override
    public String toString() {
        return m_requestData.m_strCmd + ", HttpStatus = " + m_iHttpStatus + ", ApiCode = " + m_iApiCode + ", ApiMsg = " + m_strApiMsg + ", RespBody = " + (m_joRespData == null ? "" : m_joRespData.toString());
    }
}
