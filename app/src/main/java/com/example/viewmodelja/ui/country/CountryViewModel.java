package com.example.viewmodelja.ui.country;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.viewmodelja.ui.base.BaseViewModel;

import org.json.JSONObject;

public class CountryViewModel extends BaseViewModel {
    private MutableLiveData<String> m_liveSelName = new MutableLiveData<>();

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
}
