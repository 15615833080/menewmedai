package com.sdut.mynewmedia.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.sdut.mynewmedia.R;

public class ViewPagerIndicator extends LinearLayout {
    private int mCount;    //小圆点个数
    private int mIndex;    //当前小圆点个数
    private Context context;
    public ViewPagerIndicator(Context context) {
        this(context,null);
    }

    public ViewPagerIndicator(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    /**
     * 设置滑动到当前小圆点时其他圆点位置
     */
    public void setCurrentPostion(int currentIndex){
        mIndex = currentIndex;
        this.removeAllViews();
        int pex = context.getResources().getDimensionPixelSize(R.dimen.view_indicator_padding);
        for(int i = 0; i < this.mCount; i++){
            //创建一个ImageView控件放置小圆点
            ImageView imageView = new ImageView(context);
            if(mIndex == i){
                //设置小圆点为白色图片
                imageView.setImageResource(R.drawable.indicator_on);
            }else {
                imageView.setImageResource(R.drawable.indicator_off);
            }

            imageView.setPadding(pex,0, pex,0);
            this.addView(imageView);
        }
    }

    /**
     *设置小圆点的数目
     */
    public void setCount(int count){
        this.mCount = count;
    }
}
