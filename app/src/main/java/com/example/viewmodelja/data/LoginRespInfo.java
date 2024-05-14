package com.example.viewmodelja.data;

import org.json.JSONObject;

public class LoginRespInfo {
    private static final String JO_KEY_TOKEN = "token";

    private JSONObject m_joData = null;
    public LoginRespInfo(JSONObject joData) {
        m_joData = joData;
    }

    public LoginRespInfo(String strData) {
        try {
            m_joData = new JSONObject(strData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getToken() {
        if (m_joData == null || m_joData.isNull(JO_KEY_TOKEN) == true) {
            return null;
        }

        return m_joData.optString(JO_KEY_TOKEN);
    }

    @Override
    public String toString() {
        return m_joData == null ? "" : m_joData.toString();
    }
}
