package com.renkun.mnpic.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.renkun.mnpic.R;
import com.renkun.mnpic.data.Api;
import com.renkun.mnpic.data.OkHttpClientManager;
import com.renkun.mnpic.module.Picture;
import com.renkun.mnpic.ui.adapter.PhotoViewPagerAdapter;
import com.renkun.mnpic.util.WallpaperUtli;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import static android.support.design.widget.Snackbar.LENGTH_SHORT;

public class PhotoDetailsActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private int id;//被点击图片的id,
    private int size;
    private String title;
    private Picture mPicture;
    private TextView mTextNnm;
    private TextView mTextTitle;
    private TextView mImageButton;
    private Context mContext;
    private ImageButton mButCollect;
    private ImageButton mButWrallper;
    private ImageView mback;
    private PhotoViewPagerAdapter mPhotoViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_photo_details);
        mContext=this;
        mViewPager = (ViewPager) findViewById(R.id.photo_pager);
        mTextTitle = (TextView) findViewById(R.id.title);
        mTextNnm = (TextView) findViewById(R.id.size);
        mback= (ImageView) findViewById(R.id.fabBtn);
        mback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mButCollect= (ImageButton) findViewById(R.id.but_collect);
        mButWrallper= (ImageButton) findViewById(R.id.set_wrallper);

        id = getIntent().getIntExtra("id", 1);
        size=getIntent().getIntExtra("size",5);
        title=getIntent().getStringExtra("title");

        mTextTitle.setText(title);
        mTextNnm.setText("1/"+size);
        loadPhoto();
    }

    private void loadPhoto() {
        Request request = new Request.Builder()
                .url(String.format(Api.TNPIC_SHOW, id))
                .get()
                .addHeader("accept", "application/json")
                .addHeader("content-type", "application/json")
                .build();
        OkHttpClientManager.getInstance().getOkHttpClient()
                .newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(PhotoDetailsActivity.this,"网络连接失败,请刷新重试。",Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onResponse(final Response response) throws IOException {
                //解析字符串
                String s = response.body().string();
                mPicture = OkHttpClientManager.getJsonBean(s, Picture.class);
                mPhotoViewPagerAdapter = new PhotoViewPagerAdapter(PhotoDetailsActivity.this,
                        mPicture);
                if (mPicture != null && mPicture.list.size() > 0) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mTextTitle.setText(mPicture.title);
                            mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
                                @Override
                                public void onPageSelected(int position) {
                                    super.onPageSelected(position);
                                    mTextNnm.setText(position + 1 + "/" + mPicture.size);
                                }
                            });
                            mTextNnm.setText(1 + "/" + mPicture.size);
                            mViewPager.setAdapter(mPhotoViewPagerAdapter);

                            mButWrallper.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Snackbar.make(mViewPager, "设置为壁纸吗？", Snackbar.LENGTH_LONG)
                                            .setAction("是的", new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    WallpaperUtli.setBitmap(PhotoDetailsActivity.this, Api.TNPIC_http + mPicture.list.get(mViewPager.getCurrentItem()).src);
                                                    Snackbar.make(mViewPager,"正在设置。。",Snackbar.LENGTH_LONG).show();

                                                }
                                            }).show();
                                }
                            });

                            mButCollect.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Snackbar.make(mViewPager, "要保存图片吗？", Snackbar.LENGTH_LONG)
                                            .setAction("是的", new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    WallpaperUtli.savePic(PhotoDetailsActivity.this,
                                                            Api.TNPIC_http + mPicture.list.get(mViewPager.getCurrentItem()).src,mPicture.id+""+mViewPager.getCurrentItem()+".png");
                                                    Snackbar.make(mViewPager,"正在保存。。",Snackbar.LENGTH_LONG).show();

                                                }
                                            }).show();
                                }
                            });

                        }
                    });
                } else {
                    Snackbar.make(getCurrentFocus(), "加载失败", LENGTH_SHORT)
                            .setAction("返回", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            })
                            .show();
                }

            }
        });
    }

}
