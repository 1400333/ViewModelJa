package com.example.viewmodelja.ui.login;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.viewmodelja.R;
import com.example.viewmodelja.databinding.FragmentLoginBinding;
import com.example.viewmodelja.ui.base.BaseFragment;
import com.example.viewmodelja.util.AlertDialogUtil;

public class LoginFragment extends BaseFragment {
    private FragmentLoginBinding m_binding = null;
    private LoginViewModel m_viewModel = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            return null;
        }

        m_viewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);
        m_binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
        m_binding.setLoginViewModel(m_viewModel);
        m_binding.setLifecycleOwner(this);

        initLayout();
        initViewModel();

        return m_binding.getRoot();
    }

    private void initLayout() {
    }

    private void initViewModel() {
        m_viewModel.isLoading().observe(getViewLifecycleOwner(),
                bLoading -> {
                    if (bLoading) {
                        showProgressDlg();
                    } else {
                        closeProgressDlg();
                    }
                });

        m_viewModel.getAlertMsg().observe(getViewLifecycleOwner(),
                strMsg -> {
                    if (TextUtils.isEmpty(strMsg) == false) {
                        AlertDialogUtil.showAlertDialog(getActivity(), strMsg);
                    }
                });

        m_viewModel.isLoginCompleted().observe(getViewLifecycleOwner(),
                bLoginCompleted -> {
                    if (bLoginCompleted) {
                        AlertDialogUtil.showAlertDialog(getActivity(), getString(R.string.login_login_success), new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                getActivity().finish();
                            }
                        });
                    }
                });
    }
}
