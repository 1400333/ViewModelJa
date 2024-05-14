package com.example.viewmodelja.util;

import android.util.Base64;

public class EncodeUtil {
    public static String encodeBase64(String strData) {
        String strRet = "";

        try {
            strRet = Base64.encodeToString(strData.getBytes("UTF-8"), Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return strRet;
    }

    public static String decodeBase64(String strData) {
        String strRet = "";

        try {
            byte[] arbyData = Base64.decode(strData.getBytes("UTF-8"), Base64.DEFAULT);

            strRet = new String(arbyData, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return strRet;
    }
}
