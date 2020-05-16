package com.sdut.mynewmedia.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import com.sdut.mynewmedia.MainActivity;
import com.sdut.mynewmedia.R;
import com.sdut.mynewmedia.utils.PermisionUtils;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    private boolean flag = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        boolean flag = PermisionUtils.verifyStoragePermissions(this);
        if(flag){
            init();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length > 0){
                    for(int result : grantResults){
                        if(result != PackageManager.PERMISSION_GRANTED)
                        {
                            flag = false;
                        }
                    }
                    if(flag){
                        init();
                    }else {
                        Toast.makeText(this,"You denied the permissions", Toast.LENGTH_SHORT).show();
                        SplashActivity.this.finish();
                    }

                }

                break;
        }
    }

    private void init() {

        //利用Time类让此界面延迟3s后再跳转，Timer中只有一个线程，该线程不断执行task
        Timer timer = new Timer();
        //实现runable接口，Timertask类表示一个再指定时间内执行的task
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                SplashActivity.this.finish();
            }
        };
        timer.schedule(task,2000);
    }
}
