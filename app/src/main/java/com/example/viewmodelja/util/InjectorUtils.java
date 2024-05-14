package com.example.viewmodelja.util;

import android.app.Application;

import com.example.viewmodelja.ui.country.CountryViewModel;

public class InjectorUtils {
    public static CountryViewModel.Factory provideCouponFragmentViewModel(Application application, String strTestInput) {
        return new CountryViewModel.Factory(application, strTestInput);
    }
}
