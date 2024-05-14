package com.example.viewmodelja.api;

import android.content.Context;

import com.example.viewmodelja.R;

import org.json.JSONObject;

import java.util.Hashtable;

public class UrlUtil {
    public static final String WS_CMD_AUTH_LOGIN = "auth/login";
    public static RequestData newGetRequestObj(Context context, String strCmd, JSONObject joBody, boolean bBlocking) {
        return new RequestData(context,
                strCmd,
                context.getString(R.string.api_server_url) + strCmd,
                createReqHeader(context),
                joBody,
                bBlocking);
    }

    private static Hashtable<String, String> createReqHeader(Context context) {
        Hashtable<String, String> htRet = new Hashtable<>();

        htRet.put("content-type", "application/json; Charset=UTF-8");

        return htRet;
    }

    public static JSONObject packReqAuthLogin(Context context, String strUsername, String strPassword) {
        JSONObject joRet = new JSONObject();

        try {
            joRet.put("username", strUsername);
            joRet.put("password", strPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return joRet;
    }
}
