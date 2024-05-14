package com.example.viewmodelja.ui.country;

import com.example.viewmodelja.ui.base.BaseActivity;

public class CountryActivity extends BaseActivity {

    @Override
    protected void initLayout() {
        initFragment(CountryFragment.class.getName());
    }
}
