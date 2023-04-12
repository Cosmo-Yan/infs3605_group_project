package com.example.infs3605_group_project.Dashboard;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.infs3605_group_project.R;

/**
 * Handles Detailed View of Graph
 */
public class DashboardGeneralDetailActivity extends AppCompatActivity {
    //Defines local Variable
    private ImageView img;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Removes Title Bar and Sets to FullScreen
        //Also sets Content View to dashboard_general_detail.xml
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dashboard_general_detail);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //Connects Imageview to code
        img = findViewById(R.id.general_imageview);

        //Decodes Bitmap and sets it to the ImageView as an image
        byte[] chartData = getIntent().getByteArrayExtra("chartData");
        Bitmap chartBitmap = BitmapFactory.decodeByteArray(chartData, 0, chartData.length);
        img.setImageBitmap(chartBitmap);
    }
}
