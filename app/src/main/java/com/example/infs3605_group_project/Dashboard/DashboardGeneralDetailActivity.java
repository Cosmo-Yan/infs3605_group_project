package com.example.infs3605_group_project.Dashboard;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.infs3605_group_project.R;

public class DashboardGeneralDetailActivity extends AppCompatActivity {
    private ImageView img;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.dashboard_general_detail);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        img = findViewById(R.id.general_imageview);

        byte[] chartData = getIntent().getByteArrayExtra("chartData");

        // Create a Bitmap object from the chart data
        Bitmap chartBitmap = BitmapFactory.decodeByteArray(chartData, 0, chartData.length);

        // Use the chartBitmap object to display the chart
        img.setImageBitmap(chartBitmap);
    }
}
