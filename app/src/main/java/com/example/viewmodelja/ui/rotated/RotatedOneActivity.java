package com.example.viewmodelja.ui.rotated;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.viewmodelja.R;
import com.example.viewmodelja.databinding.ActivityRotatedOneBinding;

public class RotatedOneActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TEST_DATA = "ViewModel 的替代方案為純類別，可保留 UI 中顯示的資料。";
    private ActivityRotatedOneBinding m_viewBinding = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        m_viewBinding = ActivityRotatedOneBinding.inflate(getLayoutInflater());
        setContentView(m_viewBinding.getRoot());

        m_viewBinding.btnSetData.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int iViewId = view.getId();

        if (iViewId == R.id.btnSetData) {
            m_viewBinding.tvTestData.setText(TEST_DATA);
        }
    }
}
