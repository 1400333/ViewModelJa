package com.example.viewmodelja.ui.country;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.viewmodelja.ui.base.BaseViewModel;
import com.example.viewmodelja.util.LogUtil;

public class CountryViewModel extends BaseViewModel {
    private MutableLiveData<String> m_liveSelName = new MutableLiveData<>();

    public CountryViewModel(@NonNull Application application, String strTestInput) {
        super(application);
        //範例：如何在ViewModel constructor 時傳參數進來
        LogUtil.log("初始送數值:" + strTestInput);
        initModel();
    }

    public CountryViewModel(@NonNull Application application) {
        super(application);

        initModel();
    }

    private void initModel() {
        m_liveSelName.setValue(null);
    }

    public void setSelItem(String strName) {
        m_liveSelName.setValue(strName);
    }

    public LiveData<String> getSelItem() {
        return m_liveSelName;
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {
        private final Application m_application;
        private final String m_strTestInput;

        public Factory(Application application, String strTestInput) {
            m_application = application;
            m_strTestInput = strTestInput;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new CountryViewModel(m_application, m_strTestInput);
        }
    }
}
