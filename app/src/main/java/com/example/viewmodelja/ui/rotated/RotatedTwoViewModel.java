package com.example.viewmodelja.ui.rotated;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class RotatedTwoViewModel extends AndroidViewModel {
    private MutableLiveData<String> m_liveTestData = new MutableLiveData<>();

    public RotatedTwoViewModel(@NonNull Application application) {
        super(application);
    }

    public void setTestData(String strTestData) {
        m_liveTestData.setValue(strTestData);
    }

    public MutableLiveData<String> getTestData() {
        return m_liveTestData;
    }
}
