package com.example.viewmodelja.ui.base;

import android.view.View;

import androidx.fragment.app.Fragment;

public class BaseFragment extends Fragment implements View.OnClickListener{

    protected void showProgressDlg() {
        BaseActivity baseActivity = (BaseActivity) getActivity();

        if (baseActivity != null) {
            baseActivity.showProgressDlg();
        }
    }

    protected void closeProgressDlg() {
        BaseActivity baseActivity = (BaseActivity) getActivity();

        if (baseActivity != null) {
            baseActivity.closeProgressDlg();
        }
    }

    protected void addFragment(BaseFragment fragment) {
        BaseActivity baseActivity = (BaseActivity) getActivity();

        if (baseActivity != null) {
            baseActivity.addFragment(fragment);
        }
    }

    protected void popFragment() {
        BaseActivity baseActivity = (BaseActivity) getActivity();

        if (baseActivity != null) {
            baseActivity.popFragment();
        }
    }

    @Override
    public void onClick(View view) {

    }
}
