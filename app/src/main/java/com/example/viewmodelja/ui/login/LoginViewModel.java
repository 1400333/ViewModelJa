package com.example.viewmodelja.ui.login;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.viewmodelja.api.UrlUtil;
import com.example.viewmodelja.data.LoginRespInfo;
import com.example.viewmodelja.manager.LoginManager;
import com.example.viewmodelja.ui.base.BaseActivityViewModel;

import org.json.JSONObject;

public class LoginViewModel extends BaseActivityViewModel {
    private MutableLiveData<String> m_liveDataAccount = new MutableLiveData<>("");
    private MutableLiveData<String> m_liveDataPwd = new MutableLiveData<>("");
    private MutableLiveData<Boolean> m_liveLoginCompleted = new MutableLiveData<>(false);

    public LoginViewModel(@NonNull Application application) {
        super(application);

        initModel();
    }

    private void initModel(){
        m_liveLoginCompleted.setValue(false);
    }

    public void queryLogin() {
        String strAccount = m_liveDataAccount.getValue();
        String strPwd = m_liveDataPwd.getValue();

        postData(UrlUtil.newGetRequestObj(getApplication(),
                        UrlUtil.WS_CMD_AUTH_LOGIN,
                        UrlUtil.packReqAuthLogin(getApplication(), strAccount, strPwd),
                        true),
                responseData -> {
                    if (responseData.isSuccess() == true) {
                        JSONObject joResp = responseData.getRespBody();

                        if (joResp != null) {
                            LoginManager.getInstance().setLoginInfo(new LoginRespInfo(joResp));

                            m_liveLoginCompleted.postValue(true);
                        }
                    }else {
                        m_liveAlertMsg.postValue(responseData.getApiMsg());
                    }
                }
        );
    }

    public void onClickLogin(View view) {
        //帳號：kminchelle
        //密碼：0lelplR
        queryLogin();
    }

    public void setPwdLiveData(MutableLiveData<String> liveDataPwd) {
        m_liveDataPwd = liveDataPwd;
    }

    public MutableLiveData<String> getPwdLiveData() {
        return m_liveDataPwd;
    }

    public void setAccountLiveData(MutableLiveData<String> liveDataAccount) {
        m_liveDataAccount = liveDataAccount;
    }

    public MutableLiveData<String> getAccountLiveData() {
        return m_liveDataAccount;
    }

    public LiveData<Boolean> isLoginCompleted() {
        return m_liveLoginCompleted;
    }

}
