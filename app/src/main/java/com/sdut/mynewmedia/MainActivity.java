package com.sdut.mynewmedia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sdut.mynewmedia.utils.LogUtil;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private RadioGroup radioGroup;
    private TextView tv_main_title;
    private RelativeLayout rl_title_bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        tv_main_title = findViewById(R.id.tv_main_title);
        tv_main_title.setText("首页");
        rl_title_bar = findViewById(R.id.title_bar);
        rl_title_bar.setBackgroundColor(getResources().getColor(R.color.rdTextColorPress));
        viewPager = findViewById(R.id.viewPager);
        radioGroup = findViewById(R.id.radioGroup);

        //选中状态改变监听
        radioGroup.setOnCheckedChangeListener((group,checkedId)-> {
            switch (checkedId){
                case R.id.rb_home:
                    viewPager.setCurrentItem(0,false);
                    LogUtil.d("MainActivity", "主页1");
                    break;
                case R.id.rb_count:
                    viewPager.setCurrentItem(1,false);
                    LogUtil.d("MainActivity", "统计1");
                    break;
                case R.id.rb_video:
                    viewPager.setCurrentItem(2,false);
                    break;
                case R.id.rb_me:
                    viewPager.setCurrentItem(3,false);
                    break;

            }
        });


        //页面切换监听
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        radioGroup.check(R.id.rb_home);
                        tv_main_title.setText("首页");
                        rl_title_bar.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        radioGroup.check(R.id.rb_count);
                        tv_main_title.setText("统计");
                        rl_title_bar.setVisibility(View.VISIBLE);
                        LogUtil.d("MainActivity,", "统计界面");
                        break;
                    case 2:
                        radioGroup.check(R.id.rb_video);
                        tv_main_title.setText("视频");
                        rl_title_bar.setVisibility(View.VISIBLE);
                        LogUtil.d("MainActivity,", "视频界面");
                        break;
                    case 3:
                        radioGroup.check(R.id.rb_me);
                        tv_main_title.setText("我");
                        rl_title_bar.setVisibility(View.VISIBLE);
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    protected long exitTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if ((System.currentTimeMillis() - exitTime) > 2000){
                Toast.makeText(MainActivity.this,"再按一次推出",Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            }else {
                MainActivity.this.finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
