package com.example.viewmodelja.ui.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.viewmodelja.MainActivity;
import com.example.viewmodelja.MainActivityViewModel;
import com.example.viewmodelja.R;
import com.example.viewmodelja.databinding.ActivityBaseBinding;
import com.example.viewmodelja.manager.CommonDataManager;
import com.example.viewmodelja.manager.LoginManager;
import com.example.viewmodelja.util.LogUtil;

import java.util.ArrayList;

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    private static ArrayList<BaseActivity> BASE_ACTIVITY_GROUP = new ArrayList<>();
    private static String m_strFinishUpTo = "";
    protected BaseActivityViewModel m_baseViewModel = null;
    private ActivityBaseBinding m_activityBaseBinding = null;
    private ProgressDialog m_progressDlg = null;

    private FragmentHelper m_fragmentHelper = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LogUtil.log("onCreate " + getClass().getName());

        boolean bFinishSelf = false;

        m_activityBaseBinding = ActivityBaseBinding.inflate(getLayoutInflater());
        setContentView(m_activityBaseBinding.getRoot());

        if (CommonDataManager.instanceAlive() == false) {
            // 第一次啟動
            initManager();
        } else {
            if (savedInstanceState != null && MainActivity.class.isInstance(this) == true) {
                //HomeActivity重生後 BASE_ACTIVITY_GROUP 清空
                BASE_ACTIVITY_GROUP.clear();
            }

            if (TextUtils.isEmpty(m_strFinishUpTo) == false) {
                if (this.getClass().getName().compareToIgnoreCase(m_strFinishUpTo) == 0) {
                    m_strFinishUpTo = "";
                } else {
                    bFinishSelf = true;
                    finish();
                }
            }
        }

        if (bFinishSelf == false) {
            initViewModel();

            BASE_ACTIVITY_GROUP.add(this);

            LogUtil.log("ACTIVITY COUNT = " + BASE_ACTIVITY_GROUP.size());
        }
    }

    private void initManager() {
        LogUtil.log("initManager");

        CommonDataManager.Init(this);
        LoginManager.Init(this);
    }

    protected void uninitManager() {
        m_baseViewModel.uninitManager();
    }

    private void initViewModel() {
        m_baseViewModel = createViewModelByProvider();

        observeViewModel();
    }

    /**
     * 建立ViewModel
     */
    protected BaseActivityViewModel createViewModelByProvider() {
        return new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(BaseActivityViewModel.class);
    }

    /**
     * 畫面觀察ViewModel資料更新
     */
    protected void observeViewModel() {
        m_baseViewModel.isManagerDataReady().observe(this,
                bManagerReady -> {
                    if (bManagerReady != null) {
                        if (bManagerReady) {
                            showProgress(false);

                            //Manager初始完成才能長畫面
                            initLayout();
                        } else {
                            showProgress(true);
                        }
                    }

                });
        getLifecycle().addObserver(m_baseViewModel);
    }

    /**
     * 初始頁面
     */
    abstract protected void initLayout();

    protected boolean isManagerDataReady() {
        Boolean bReady = m_baseViewModel.isManagerDataReady().getValue();

        if (bReady == null) {
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View view) {

    }

    public void exitApp() {
        uninitManager();

        finishAllActivity();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        popFragmentWithCondition();
    }

    private void popFragmentWithCondition() {
        if (getActivityCount() == 1 && MainActivity.class.isInstance(this) == true) {
            exitApp();
        } else {
            BaseFragment topFragment = null;
            boolean bDoFinish = false;

            if (m_fragmentHelper != null) {
                topFragment = m_fragmentHelper.getTopFragment();
            }

            if (topFragment != null) {
                int iFragmentCount = m_fragmentHelper.getFragmentCount();

                if (iFragmentCount > 1) {
                    m_fragmentHelper.popFragment();
                } else {
                    bDoFinish = true;
                }
            } else {
                bDoFinish = true;
            }

            if (bDoFinish) {
                finish();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        LogUtil.log("onDestroy " + getClass().getSimpleName());

        BaseActivity baseActivity;

        for (int i = 0; i < BASE_ACTIVITY_GROUP.size(); i++) {
            baseActivity = BASE_ACTIVITY_GROUP.get(i);

            if (baseActivity == this) {
                BASE_ACTIVITY_GROUP.remove(i);

                break;
            }
        }

        LogUtil.log("ACTIVITY COUNT = " + BASE_ACTIVITY_GROUP.size());
    }

    private int getActivityCount() {
        int iCnt = BASE_ACTIVITY_GROUP.size();

        BaseActivity baseActivity;

        for (int i = 0; i < BASE_ACTIVITY_GROUP.size(); i++) {
            baseActivity = BASE_ACTIVITY_GROUP.get(i);

            if (baseActivity.isFinishing() == true) {
                iCnt--;
            }
        }

        return iCnt;
    }

    protected void finishAllActivity() {
        BaseActivity baseActivity;

        for (int i = BASE_ACTIVITY_GROUP.size() - 1; i >= 0; i--) {
            baseActivity = BASE_ACTIVITY_GROUP.get(i);

            if (baseActivity != null) {
                baseActivity.finish();
            }
        }
    }

    protected void showProgress(boolean bShow) {
        if (bShow) {
            showProgressDlg();
        } else {
            closeProgressDlg();
        }
    }

    protected void showProgressDlg() {
        if (m_progressDlg == null) {
            m_progressDlg = ProgressDialog.show(this,
                    "",
                    getString(R.string.alert_dialog_loading_data));
            m_progressDlg.show();
        }
    }

    protected void closeProgressDlg() {
        if (m_progressDlg != null) {
            if (m_progressDlg.isShowing() == true) {
                try {
                    m_progressDlg.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            m_progressDlg = null;
        }
    }

    protected void initFragment(String strFragmentName) {
        try {
            BaseFragment fragment = (BaseFragment) Class.forName(strFragmentName).newInstance();

            initFragment(fragment);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void initFragment(BaseFragment fragment) {
        m_fragmentHelper = new FragmentHelper(getSupportFragmentManager());
        m_fragmentHelper.replaceFragment(fragment);
    }

    public void replaceFragment(String strFragmentName) {
        try {
            BaseFragment fragment = (BaseFragment) Class.forName(strFragmentName).newInstance();

            replaceFragment(fragment);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void replaceFragment(BaseFragment fragment) {
        m_fragmentHelper.replaceFragment(fragment);
    }

    public void addFragment(BaseFragment fragment) {
        m_fragmentHelper.addFragment(fragment);
    }

    public void popFragment() {
        m_fragmentHelper.popFragment();
    }

    public void popFragmentUpTo(String strFragmentName) {
        m_fragmentHelper.popFragmentUpTo(strFragmentName);
    }
}
