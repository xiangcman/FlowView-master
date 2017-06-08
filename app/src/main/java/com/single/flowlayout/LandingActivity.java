package com.single.flowlayout;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by xiangcheng on 17/6/2.
 */

public class LandingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lanuch);
        findViewById(R.id.text_flow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LandingActivity.this, NormalLoadActivity.class));
            }
        });
        findViewById(R.id.line_flow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LandingActivity.this, LineFlowActivity.class));
            }
        });
        findViewById(R.id.diff_height_text_flow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LandingActivity.this, DiffHeightTextActivity.class));
            }
        });
        findViewById(R.id.diff_height_text_flow_center).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LandingActivity.this, DiffHeightTextCenterActivity.class));
            }
        });
        findViewById(R.id.scroll_flow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LandingActivity.this, ScrollFlowActivity.class));
            }
        });
    }
}
