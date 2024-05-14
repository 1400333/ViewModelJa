package com.example.viewmodelja.ui.country;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.example.viewmodelja.R;
import com.example.viewmodelja.databinding.FragmentSearchBinding;
import com.example.viewmodelja.ui.base.BaseFragment;

public class SearchFragment extends BaseFragment {
    private FragmentSearchBinding m_binding = null;
    private CountryViewModel m_viewModel = null;

    public static SearchFragment newInstance() {
        SearchFragment ret = new SearchFragment();

        return ret;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        m_binding = FragmentSearchBinding.inflate(inflater, container, false);

        initLayout();
        initViewModel();

        return m_binding.getRoot();
    }

    private void initLayout() {
        m_binding.btnOK.setOnClickListener(this);
    }

    private void initViewModel() {
        m_viewModel = new ViewModelProvider(getActivity(),
                new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())).get(CountryViewModel.class);


        getViewLifecycleOwner().getLifecycle().addObserver(m_viewModel);

    }

    @Override
    public void onClick(View view) {
        int iViewId = view.getId();

        if (iViewId == R.id.btnOK) {
            String strSelItem = "";
            int iCheckedId = m_binding.radioGroupCountry.getCheckedRadioButtonId();

            if (iCheckedId == R.id.radioButton1) {
                strSelItem = m_binding.radioButton1.getText().toString();
            } else if (iCheckedId == R.id.radioButton2) {
                strSelItem = m_binding.radioButton2.getText().toString();
            }

            m_viewModel.setSelItem(strSelItem);
            popFragment();
        }
    }
}
