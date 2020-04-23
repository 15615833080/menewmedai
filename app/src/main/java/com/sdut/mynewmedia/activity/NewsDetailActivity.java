package com.sdut.mynewmedia.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sdut.mynewmedia.R;
import com.sdut.mynewmedia.bean.NewsBean;
import com.sdut.mynewmedia.utils.DBUtils;
import com.sdut.mynewmedia.utils.UtilsHelper;
import com.sdut.mynewmedia.view.SwipeBackLayout;

public class NewsDetailActivity extends AppCompatActivity {
    private RelativeLayout rl_title_bar;
    private WebView webView;
    private TextView tv_main_title, tv_back;
    private ImageView iv_collection;
    private SwipeBackLayout layout;
    private String newsUrl;
    private NewsBean bean;
    private String position;
    private LinearLayout ll_loading;
    private boolean isCollection=false;
    private DBUtils db;
    private String userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layout = (SwipeBackLayout) LayoutInflater.from(this).inflate(
                R.layout.base, null);
        layout.attachToActivity(this);
        setContentView(R.layout.activity_news_detail);
        bean = (NewsBean) getIntent().getSerializableExtra("newsBean");
        position = getIntent().getStringExtra("position");
        if (bean == null) return;
        //db=DBUtils.getInstance(NewsDetailActivity.this);
        newsUrl = bean.getNewsUrl();
        userName= UtilsHelper.readLoginUserName(NewsDetailActivity.this);
        init();
        initWebView();
    }
    private void init() {
        tv_main_title = (TextView) findViewById(R.id.tv_main_title);
        tv_main_title.setText("新闻详情");
        rl_title_bar = (RelativeLayout) findViewById(R.id.title_bar);
        rl_title_bar.setBackgroundColor(getResources().getColor(R.color.
                rdTextColorPress));
        ll_loading = (LinearLayout) findViewById(R.id.ll_loading);
        iv_collection = (ImageView) findViewById(R.id.iv_collection);
        iv_collection.setVisibility(View.VISIBLE);
//        if(db.hasCollectionNewsInfo(bean.getId(),bean.getType(),userName)){
//            iv_collection.setImageResource(R.drawable.collection_selected);
//            isCollection=true;
//        }else{
//            iv_collection.setImageResource(R.drawable.collection_normal);
//            isCollection=false;
//        }
        tv_back = (TextView) findViewById(R.id.tv_back);
        tv_back.setVisibility(View.VISIBLE);
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewsDetailActivity.this.finish();
            }
        });
        webView = (WebView) findViewById(R.id.webView);
        iv_collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
    private void initWebView() {
        webView.loadUrl(newsUrl);
        WebSettings mWebSettings = webView.getSettings();
        mWebSettings.setSupportZoom(true);
        mWebSettings.setLoadWithOverviewMode(true);
        mWebSettings.setUseWideViewPort(true);
        mWebSettings.setDefaultTextEncodingName("GBK");//设置解码格式
        mWebSettings.setLoadsImagesAutomatically(true);
        mWebSettings.setJavaScriptEnabled(true);//支持js 特效
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                ll_loading.setVisibility(View.VISIBLE);//开始加载动画
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                ll_loading.setVisibility(View.GONE);//当加载结束时隐藏动画
            }
        });
    }
}

