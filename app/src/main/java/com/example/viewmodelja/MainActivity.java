package com.example.viewmodelja;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;

import com.example.viewmodelja.databinding.ActivityMainBinding;
import com.example.viewmodelja.manager.LoginManager;
import com.example.viewmodelja.ui.base.BaseActivity;
import com.example.viewmodelja.ui.base.BaseActivityViewModel;
import com.example.viewmodelja.ui.country.CountryActivity;
import com.example.viewmodelja.ui.login.LoginActivity;
import com.example.viewmodelja.ui.rotated.RotatedOneActivity;
import com.example.viewmodelja.ui.rotated.RotatedTwoActivity;

public class MainActivity extends BaseActivity {
    private ActivityMainBinding m_viewBinding = null;
    private MainActivityViewModel m_viewModel = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        m_viewBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(m_viewBinding.getRoot());
    }

    @Override
    protected BaseActivityViewModel createViewModelByProvider() {
        m_viewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(MainActivityViewModel.class);

        return m_viewModel;
    }

    @Override
    protected void initLayout() {
        m_viewBinding.btnLogin.setOnClickListener(this);
        m_viewBinding.btnLogout.setOnClickListener(this);
        m_viewBinding.btnCountry.setOnClickListener(this);
        m_viewBinding.btnRotatedOne.setOnClickListener(this);
        m_viewBinding.btnRotatedTwo.setOnClickListener(this);
        updateLoginState();
    }

    @Override
    public void onClick(View view) {
        int iViewId = view.getId();

        if (iViewId == R.id.btnLogin) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);

            startActivity(intent);
        } else if (iViewId == R.id.btnLogout) {
            LoginManager.getInstance().logout();
            updateLoginState();
        } else if (iViewId == R.id.btnCountry) {
            Intent intent = new Intent(MainActivity.this, CountryActivity.class);

            startActivity(intent);
        } else if (iViewId == R.id.btnRotatedOne){
            Intent intent = new Intent(MainActivity.this, RotatedOneActivity.class);

            startActivity(intent);
        } else if (iViewId == R.id.btnRotatedTwo){
            Intent intent = new Intent(MainActivity.this, RotatedTwoActivity.class);

            startActivity(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        updateLoginState();
    }

    private void updateLoginState() {
        if (isManagerDataReady()) {
            if (LoginManager.getInstance().isUserLogin()) {
                m_viewBinding.btnLogin.setVisibility(View.GONE);
                m_viewBinding.btnLogout.setVisibility(View.VISIBLE);
            } else {
                m_viewBinding.btnLogin.setVisibility(View.VISIBLE);
                m_viewBinding.btnLogout.setVisibility(View.GONE);
            }
        } else {
            m_viewBinding.btnLogin.setVisibility(View.GONE);
            m_viewBinding.btnLogout.setVisibility(View.GONE);
        }
    }
}