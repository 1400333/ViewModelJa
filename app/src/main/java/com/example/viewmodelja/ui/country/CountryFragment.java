package com.example.viewmodelja.ui.country;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.example.viewmodelja.R;
import com.example.viewmodelja.databinding.FragmentCountryBinding;
import com.example.viewmodelja.ui.base.BaseFragment;
import com.example.viewmodelja.util.AlertDialogUtil;
import com.example.viewmodelja.util.InjectorUtils;

public class CountryFragment extends BaseFragment {
    private FragmentCountryBinding m_binding = null;
    private CountryViewModel m_viewModel = null;

    public static CountryFragment newInstance() {
        CountryFragment ret = new CountryFragment();

        return ret;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        m_binding = FragmentCountryBinding.inflate(inflater, container, false);

        initLayout();
        initViewModel();

        return m_binding.getRoot();
    }

    private void initLayout() {
        m_binding.btnSearh.setOnClickListener(this);
    }

    private void initViewModel() {
        m_viewModel = new ViewModelProvider(requireActivity(), InjectorUtils.provideCouponFragmentViewModel(
                requireActivity().getApplication(),
                getContext().getString(R.string.view_model_test))).get(CountryViewModel.class);

//        m_viewModel = new ViewModelProvider(getActivity(),
//                new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())).get(CountryViewModel.class);

        m_viewModel.getSelItem().observe(getViewLifecycleOwner(),
                strSelItem -> {
                    if (TextUtils.isEmpty(strSelItem)) {
                        m_binding.tvSelItem.setText("");
                    } else {
                        m_binding.tvSelItem.setText(strSelItem);
                    }
                });
    }

    @Override
    public void onClick(View view) {
        int iViewId = view.getId();

        if (iViewId == R.id.btnSearh) {
            addFragment(SearchFragment.newInstance());
        }
    }
}
