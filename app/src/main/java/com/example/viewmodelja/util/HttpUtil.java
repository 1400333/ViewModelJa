package com.example.viewmodelja.util;

import android.text.TextUtils;

import com.example.viewmodelja.api.RequestData;
import com.example.viewmodelja.api.ResponseData;

import org.json.JSONObject;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtil {
    private static final long CONNECTION_TIMEOUT_MS = 35*1000;
    private static final long CONNECTION_READ_WRITE_TIMEOUT_MS = 35*1000;
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static ResponseData postData(RequestData requestData) {
        String strResp;
        ResponseData respData = null;
        StringBuilder sbReqLog = new StringBuilder();
        StringBuilder sbRespLog = new StringBuilder();

        try {
            String strURL = requestData.m_strUrl;

            Request.Builder reqBuilder = new Request.Builder().url(strURL);

            // add request headers
            Enumeration<String> eKeys = requestData.m_htHeader.keys();
            JSONObject joHeaderLog = new JSONObject();
            String strKey;
            String strValue;

            while (eKeys.hasMoreElements() == true) {
                strKey = eKeys.nextElement();
                strValue = requestData.m_htHeader.get(strKey);
                reqBuilder.addHeader(strKey, strValue);

                joHeaderLog.put(strKey, strValue);
            }

            String strPostBody = requestData.m_joBody.toString();


            reqBuilder.post(RequestBody.create(JSON, strPostBody));

            String strMethod = "POST";

            sbReqLog.append("Request Info\n");
            sbReqLog.append("======================================\n");
            sbReqLog.append("req method = ").append(strMethod).append("\n");
            sbReqLog.append("req url = ").append(strURL).append("\n");
            sbReqLog.append("req cmd = ").append(requestData.m_strCmd).append("\n");
            sbReqLog.append("req header = ").append(joHeaderLog.toString()).append("\n");
            sbReqLog.append("req body = ").append(requestData.m_joBody.toString()).append("\n");

            sbReqLog.append("====================================\n");
            LogUtil.log(sbReqLog.toString());

            OkHttpClient client = new OkHttpClient.Builder().connectTimeout(CONNECTION_TIMEOUT_MS, TimeUnit.MILLISECONDS)
                    .readTimeout(CONNECTION_READ_WRITE_TIMEOUT_MS, TimeUnit.MILLISECONDS)
                    .writeTimeout(CONNECTION_READ_WRITE_TIMEOUT_MS, TimeUnit.MILLISECONDS)
                    .build();

            Response response = client.newCall(reqBuilder.build()).execute();
            strResp = response.body().string();


            sbRespLog.append("Response Info\n");
            sbRespLog.append("======================================\n");
            sbRespLog.append("resp method = ").append(strMethod).append("\n");
            sbRespLog.append("resp url = ").append(strURL).append("\n");
            sbRespLog.append("resp cmd = ").append(requestData.m_strCmd).append("\n");
            sbRespLog.append("resp http status code = ").append(response.code()).append(",  status msg = ").append(response.message()).append("\n");

            if (TextUtils.isEmpty(strResp) == true) {
                respData = requestData.constructConnErrorResp(response.code());
            } else if (strResp.charAt(0) == '{') {
                respData = requestData.constructResp(response.code(), new JSONObject(strResp));
            } else {
                respData = requestData.constructConnErrorResp(response.code());
            }
        } catch (Exception e) {
            e.printStackTrace();

            if (respData == null) {
                respData = requestData.constructConnTimeoutResp();
            }
        }

        sbRespLog.append("resp data = ").append(respData.toString()).append("\n");
        sbRespLog.append("======================================");

        LogUtil.log(sbRespLog.toString());

        return respData;
    }
}
