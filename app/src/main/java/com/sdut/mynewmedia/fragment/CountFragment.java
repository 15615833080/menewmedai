package com.sdut.mynewmedia.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.TextInsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;
import com.sdut.mynewmedia.R;
import com.sdut.mynewmedia.activity.AndroidCountActivity;
import com.sdut.mynewmedia.activity.JavaCountActivity;
import com.sdut.mynewmedia.utils.BuilderManager;

public class CountFragment extends Fragment {
    private BoomMenuButton bmb;
    public CountFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_count, container, false);
        bmb = (BoomMenuButton) view.findViewById(R.id.bmb);
        assert bmb != null;
        //设置点击圆形菜单后显示的多个按钮为圆形且带文本的
        bmb.setButtonEnum(ButtonEnum.TextInsideCircle);
        bmb.setPiecePlaceEnum(PiecePlaceEnum.DOT_6_1); //设置右下角圆形菜单中有9个圆形
        //设置点击右下角圆形菜单后显示的按钮为9个圆形Button
        bmb.setButtonPlaceEnum(ButtonPlaceEnum.SC_6_1);
        for (int i = 0; i < bmb.getPiecePlaceEnum().pieceNumber(); i++) {
            addBuilder();
        }
        return view;
    }

    private void addBuilder() {
        bmb.addBuilder(new TextInsideCircleButton.Builder()
                .normalImageRes(BuilderManager.getImageResource())
                .normalTextRes(BuilderManager.getTextResource())
                .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        Intent android = new Intent(getActivity(), AndroidCountActivity.class);
                        Intent java = new Intent(getActivity(), JavaCountActivity.class);
                        switch (index) {
                            case 0:   //跳转到Android统计详情界面
                                android.putExtra("subject", "Android");
                                startActivity(android);
                                break;
                            case 1:   //跳转到Java统计详情界面
                                java.putExtra("subject", "Java");
                                startActivity(java);
                                break;
                            case 2:   //跳转到Android统计详情界面
                                android.putExtra("subject", "Python");
                                startActivity(android);
                                break;
                            case 3:   //跳转到Java统计详情界面
                                java.putExtra("subject", "PHP");
                                startActivity(java);
                                break;
                            case 4:   //跳转到Android统计详情界面
                                android.putExtra("subject", "C/C++");
                                startActivity(android);
                                break;
                            case 5:   //跳转到Java统计详情界面
                                java.putExtra("subject", "IOS");
                                startActivity(java);
                                break;
                        }
                    }
                }));
    }
}
