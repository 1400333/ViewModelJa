package com.example.viewmodelja.manager;

import android.content.Context;
import android.text.TextUtils;

import com.example.viewmodelja.data.LoginRespInfo;
import com.example.viewmodelja.util.LogUtil;

public class LoginManager {
    private static LoginManager s_instance = null;
    private Context m_context = null;
    private boolean m_bInitDone = false;

    private LoginRespInfo m_loginInfo = null;

    public static boolean instanceAlive() {
        return s_instance != null;
    }

    public static LoginManager getInstance() {
        if (s_instance == null) {
            s_instance = new LoginManager();
        }

        return s_instance;
    }
    public static void Init(Context context) {
        if (s_instance != null) {
            return;
        }

        getInstance().init(context);
    }

    private void uninit() {

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

        new Thread(() -> {
            // load data after and common data ready
            boolean bCommonDataReady = CommonDataManager.instanceAlive() == true && CommonDataManager.getInstance().isInitDone() == true;

            while (bCommonDataReady == false) {
                try {
                    Thread.sleep(300);

                    bCommonDataReady = CommonDataManager.instanceAlive() == true && CommonDataManager.getInstance().isInitDone() == true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            loadLoginInfo();
            m_bInitDone = true;
        }).start();
    }

    private void loadLoginInfo() {
        String strData = CommonDataManager.getInstance().getCommonData(CommonDataManager.SP_KEY_LOGIN_INFO);

        if (TextUtils.isEmpty(strData) == false) {
            m_loginInfo = new LoginRespInfo(strData);

            LogUtil.log("login info = " + m_loginInfo.toString());
        } else {
            LogUtil.log("no login info loaded");
        }
    }

    public boolean isInitDone() {
        return m_bInitDone;
    }

    public void setLoginInfo(LoginRespInfo loginInfo) {
        m_loginInfo = loginInfo;

        CommonDataManager.getInstance().setCommonData(CommonDataManager.SP_KEY_LOGIN_INFO, m_loginInfo == null ? "" : m_loginInfo.toString());
    }

    public boolean isUserLogin() {
        if (m_loginInfo == null) {
            return false;
        }

        return TextUtils.isEmpty(m_loginInfo.getToken()) == false;
    }

    public void logout() {
        m_loginInfo = null;

        CommonDataManager.getInstance().setCommonData(CommonDataManager.SP_KEY_LOGIN_INFO, m_loginInfo == null ? "" : m_loginInfo.toString());
    }
}
