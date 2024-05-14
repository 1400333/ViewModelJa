package com.example.viewmodelja.api;

import android.content.Context;

import org.json.JSONObject;

import java.util.Hashtable;

public class RequestData {
    public Context m_context = null;
    public String m_strCmd = null;
    public String m_strUrl = null;
    public Hashtable<String, String> m_htHeader = null;
    public JSONObject m_joBody = null;
    private boolean m_bBlocking = false;

    public RequestData(Context context,
                            String strCmd,
                            String strUrl,
                            Hashtable<String, String> htHeader,
                            JSONObject joBody,
                            boolean bBlocking) {
        m_context = context;
        m_strCmd = strCmd;
        m_strUrl = strUrl;
        m_htHeader = htHeader;
        m_joBody = joBody;
        m_bBlocking = bBlocking;
    }

    public ResponseData constructConnTimeoutResp() {
        ResponseData responseData = ResponseData.constructConnTimeoutResp();
        responseData.setRequestData(this);

        return responseData;
    }

    public ResponseData constructConnErrorResp(int iHttpStatus) {
        ResponseData responseData = ResponseData.constructConnErrorResp(iHttpStatus);
        responseData.setRequestData(this);

        return responseData;
    }

    public ResponseData constructResp(int iHttpStatus, JSONObject joResp) {
        ResponseData responseData = ResponseData.constructResp(iHttpStatus, joResp);
        responseData.setRequestData(this);

        return responseData;
    }

    public boolean isBlocking() {
        return m_bBlocking;
    }
}
