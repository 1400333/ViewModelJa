package com.example.viewmodelja.ui.base;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.viewmodelja.R;
import com.example.viewmodelja.api.RequestData;
import com.example.viewmodelja.api.RequestManager;
import com.example.viewmodelja.api.ResponseData;
import com.example.viewmodelja.common.SingleLiveEvent;
import com.example.viewmodelja.util.LogUtil;

abstract public class BaseViewModel extends AndroidViewModel  {
    private RequestManager m_reqManager = null;
    private MutableLiveData<Boolean> m_bLoading = new MutableLiveData<>(); // 是否要顯示轉圈圈
    protected SingleLiveEvent<String> m_liveAlertMsg = new SingleLiveEvent<>();  //錯誤訊息

    private int m_iBlockingCount = 0;       //強制轉圈數
    private int m_iNonBlockingCount = 0;    //未強制轉圈數

    public BaseViewModel(@NonNull Application application) {
        super(application);

        initModel();
    }

    private void initModel() {
        m_reqManager = new RequestManager();

        //初始參數
        m_liveAlertMsg.setValue(null);
    }

    protected void postData(RequestData requestData, RequestManager.RequestCallback callback) {
        if (requestData.isBlocking()) {
            addBlockingCount();

            setLoading(true);
        } else {
            addNonBlockingCount();
        }

        //送api
        m_reqManager.postData(requestData, new RequestManager.RequestCallback() {
            @Override
            public void onDataRecovery(ResponseData responseData) {
                if (responseData.isBlocking()) {
                    removeBlockingCount();
                } else {
                    removeNonBlockingCount();
                }

                if (getBlockingCount() == 0) {
                    setLoading(false);
                }

                if (responseData.isConnTimeout()) {
                    responseData.setApiMsg(getApplication().getString(R.string.alert_dialog_connection_timeout));
                } else if (responseData.isConnError()) {
                    responseData.setApiMsg(getApplication().getString(R.string.alert_dialog_connection_error));
                } else if (responseData.isSuccess()) {
                    String strErrMsg = responseData.getApiMsg();

                    responseData.setApiMsg(TextUtils.isEmpty(strErrMsg) ? "" : strErrMsg);
                }

                if (callback != null) {
                    callback.onDataRecovery(responseData);
                }
            }
        });
    }

    protected void addBlockingCount() {
        m_iBlockingCount++;
    }

    protected void removeBlockingCount() {
        m_iBlockingCount--;
    }

    protected void addNonBlockingCount() {
        m_iNonBlockingCount++;
    }

    protected void removeNonBlockingCount() {
        m_iNonBlockingCount--;
    }

    protected int getBlockingCount() {
        return m_iBlockingCount;
    }

    protected int getNonBlockingCount() {
        return m_iNonBlockingCount;
    }

    protected void setLoading(boolean bLoading) {
        m_bLoading.postValue(bLoading);
    }

    public LiveData<Boolean> isLoading() {
        return m_bLoading;
    }

    public LiveData<String> getAlertMsg() {
        return m_liveAlertMsg;
    }
}
