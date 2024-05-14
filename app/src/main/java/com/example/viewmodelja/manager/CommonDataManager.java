package com.example.viewmodelja.manager;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.viewmodelja.util.EncodeUtil;
import com.example.viewmodelja.util.LogUtil;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;

public class CommonDataManager {
    public static final String SP_KEY_LOGIN_INFO = "SP_KEY_LOGIN_INFO";
    private static CommonDataManager s_instance = null;

    private Context m_context = null;
    private SharedPreferences m_prefData = null;

    private Hashtable<String, String> m_htData = new Hashtable<>();

    private boolean m_bInitDone = false;

    public static boolean instanceAlive() {
        return s_instance != null;
    }

    public static CommonDataManager getInstance() {
        if (s_instance == null) {
            s_instance = new CommonDataManager();
        }

        return s_instance;
    }

    public static void Init(Context context) {
        if (s_instance != null) {
            return;
        }

        getInstance().init(context);
    }

    public static void UnInit() {
        if (s_instance == null) {
            return;
        }

        getInstance().uninit();
        s_instance = null;
    }

    private void init(Context context) {
        m_context = context;
        m_prefData = m_context.getSharedPreferences(getClass().getSimpleName(), Context.MODE_PRIVATE);

        new Thread(() -> {
            loadData();

            m_bInitDone = true;
        }).start();
    }

    private void uninit() {
    }

    private void loadData() {
        if (m_prefData == null) {
            LogUtil.log("commonDataManager load data, sharepref is null");

            return;
        }

        synchronized(this) {
            m_htData.clear();

            Map<String, ?> items = m_prefData.getAll();

            for(String s : items.keySet()){
                m_htData.put(EncodeUtil.decodeBase64(s), EncodeUtil.decodeBase64(items.get(s).toString()));
            }

            LogUtil.log("commonDataManager loaded key pair count = " + m_htData.size());
        }
    }

    private void saveData() {
        if (m_prefData == null) {
            return;
        }

        SharedPreferences.Editor editor = m_prefData.edit();

        editor.clear();

        synchronized(this) {
            Enumeration<String> enumeration = m_htData.keys();
            String strKey;

            while (enumeration.hasMoreElements() == true) {
                strKey = enumeration.nextElement();
                editor.putString(EncodeUtil.encodeBase64(strKey), EncodeUtil.encodeBase64(m_htData.get(strKey)));
            }
        }

        editor.apply();
    }

    public boolean isInitDone() {
        return m_bInitDone;
    }

    public void setCommonData(String strKey, String strValue) {
        synchronized(this) {
            m_htData.put(strKey, strValue);

            saveData();
        }
    }

    public void setCommonData(String strKey, boolean bFlag) {
        String strFlag = "N";

        if (bFlag == true) {
            strFlag = "Y";
        }

        setCommonData(strKey, strFlag);
    }

    public void setCommonData(String strKey, long value) {
        setCommonData(strKey, String.valueOf(value));
    }

    public String getCommonData(String strKey) {
        String strRet = "";

        synchronized(this) {
            if (m_htData.containsKey(strKey) == true) {
                strRet = m_htData.get(strKey);
            }
        }

        return strRet;
    }

    public long getCommonDataAsLong(String strKey) {
        String strData = getCommonData(strKey);
        long lRet = -1;

        try {
            lRet = Long.parseLong(strData);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return lRet;
    }

    public int getCommonDataAsInt(String strKey) {
        String strData = getCommonData(strKey);
        int iRet = -1;

        try {
            iRet = Integer.parseInt(strData);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return iRet;
    }

    public boolean getCommonDataAsBool(String strKey) {
        String strData = getCommonData(strKey);

        if (strData != null) {
            if (strData.compareToIgnoreCase("Y") == 0) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
