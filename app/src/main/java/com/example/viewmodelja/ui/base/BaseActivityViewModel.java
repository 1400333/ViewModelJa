package com.example.viewmodelja.ui.base;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.viewmodelja.manager.CommonDataManager;
import com.example.viewmodelja.manager.LoginManager;
import com.example.viewmodelja.util.LogUtil;

public class BaseActivityViewModel extends BaseViewModel{
    private MutableLiveData<Boolean> m_liveManagerDataReady = new MutableLiveData<>(); //manager是否都初始完成
    public BaseActivityViewModel(@NonNull Application application) {
        super(application);

        initModel();
    }

    private void initModel() {
        m_liveManagerDataReady.setValue(false);
        //開始判斷Manager是否初始完成
        monitorManagerData();
    }

    @Override
    protected void onCleared() {
        super.onCleared();

        uninitManager();
    }

    public void uninitManager() {
        LoginManager.UnInit();
        CommonDataManager.UnInit();
    }

    private void monitorManagerData() {
        new Thread(() -> {
            try {
                if (checkManagerData()) {
                    m_liveManagerDataReady.postValue(true);
                } else {
                    m_liveManagerDataReady.postValue(false);
                    Thread.sleep(300);

                    monitorManagerData();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private boolean checkManagerData() {
        boolean bCommonDataReady = CommonDataManager.getInstance().isInitDone();
        boolean bLoginDataReady = LoginManager.getInstance().isInitDone();

        LogUtil.log("CommonDataReady " + bCommonDataReady + ", LoginDataReady " + bLoginDataReady );

        return bCommonDataReady == true && bLoginDataReady == true;
    }

    public LiveData<Boolean> isManagerDataReady() {
        return m_liveManagerDataReady;
    }
}
