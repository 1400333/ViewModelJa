package com.example.viewmodelja.ui.base;

import androidx.fragment.app.Fragment;

public class BaseFragment extends Fragment {

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
}
