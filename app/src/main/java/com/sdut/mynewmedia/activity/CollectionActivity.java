package com.sdut.mynewmedia.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sdut.mynewmedia.R;
import com.sdut.mynewmedia.adapter.CollectionAdapter;
import com.sdut.mynewmedia.bean.NewsBean;
import com.sdut.mynewmedia.utils.DBUtils;
import com.sdut.mynewmedia.utils.UtilsHelper;

import java.util.ArrayList;
import java.util.List;

public class CollectionActivity extends AppCompatActivity implements
        CollectionAdapter.IonSlidingViewClickListener {
    private RecyclerView mRecyclerView;
    private CollectionAdapter mAdapter;
    private TextView tv_main_title, tv_back, tv_none;
    private RelativeLayout rl_title_bar;
    private DBUtils db;
    private List<NewsBean> newsList;
    private String userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        db = DBUtils.getInstance(CollectionActivity.this);
        userName = UtilsHelper.readLoginUserName(CollectionActivity.this);
        initView();
        setAdapter();
    }
    private void initView() {
        newsList = new ArrayList<>();
        newsList = db.getCollectionNewsInfo(userName);
        tv_main_title = (TextView) findViewById(R.id.tv_main_title);
        tv_main_title.setText("收藏");
        rl_title_bar = (RelativeLayout) findViewById(R.id.title_bar);
        rl_title_bar.setBackgroundColor(getResources().getColor(R.color.
                rdTextColorPress));
        tv_back = (TextView) findViewById(R.id.tv_back);
        tv_back.setVisibility(View.VISIBLE);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_recyclerView);
        tv_none = (TextView) findViewById(R.id.tv_none);
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CollectionActivity.this.finish();
            }
        });
    }
    private void setAdapter() {
        mAdapter = new CollectionAdapter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setData(newsList);
        if (newsList.size() == 0) tv_none.setVisibility(View.VISIBLE);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }
    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(CollectionActivity.this, NewsDetailActivity.class);
        intent.putExtra("newsBean", newsList.get(position));
        intent.putExtra("position", position + "");
        startActivityForResult(intent, 1);
    }
    @Override
    public void onDeleteBtnCilck(View view, int position) {
        mAdapter.removeData(position, tv_none, userName);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            String position = data.getStringExtra("position");
            mAdapter.removeData(Integer.parseInt(position), tv_none, userName);
        }
    }
}
