package com.example.viewmodelja.ui.rotated;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.viewmodelja.MainActivityViewModel;
import com.example.viewmodelja.R;
import com.example.viewmodelja.databinding.ActivityRotatedOneBinding;

public class RotatedTwoActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TEST_DATA = "ViewModel 的替代方案為純類別，可保留 UI 中顯示的資料。";
    private ActivityRotatedOneBinding m_viewBinding = null;

    private RotatedTwoViewModel m_viewModel = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        m_viewBinding = ActivityRotatedOneBinding.inflate(getLayoutInflater());
        setContentView(m_viewBinding.getRoot());

        initLayout();
        initViewModel();
    }

    private void initLayout() {
        m_viewBinding.btnSetData.setOnClickListener(this);
    }

    private void initViewModel() {
        m_viewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(RotatedTwoViewModel.class);
        m_viewModel.getTestData().observe(this,
                strTestData -> {
                    m_viewBinding.tvTestData.setText(strTestData);

                });
    }

    @Override
    public void onClick(View view) {
        int iViewId = view.getId();

        if (iViewId == R.id.btnSetData) {
            m_viewModel.setTestData(TEST_DATA);
        }
    }
}
