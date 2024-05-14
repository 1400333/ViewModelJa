package com.example.viewmodelja.ui.login;


import com.example.viewmodelja.ui.base.BaseActivity;


public class LoginActivity extends BaseActivity {
    @Override
    protected void initLayout() {
        initFragment(LoginFragment.class.getName());
    }
}
