package com.renkun.mnpic.util;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.renkun.mnpic.data.OkHttpClientManager;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by rk on 2015/10/25.
 */
public class WallpaperUtli {

    public static void setBitmap(final Activity mActivity,String url){
        Request request=new Request.Builder()
                .url(url)
                .build();
        Call call = OkHttpClientManager.getInstance().getOkHttpClient().newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                byte[] bytes = response.body().bytes();
                final Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        WallpaperUtli.setWallpaper(mActivity, bitmap);

                    }
                });
            }
        });
    }

    public static void setWallpaper(Context context,Bitmap bitmap){
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(context);
        try {
            WallpaperManager instance = WallpaperManager.getInstance(context);
                               int[] i= Screenutil.getScreenHeightANDheight(context);
                                instance.suggestDesiredDimensions(i[0], i[1]);
                               instance.setBitmap(bitmap);
                                Toast.makeText(context,"壁纸设置成功",Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    /** 保存方法 */

    public static void savePic(final Activity context,final String url, final String fileName)
    {
        final Request request = new Request.Builder()
                .url(url)
                .build();
        final Call call = OkHttpClientManager.getInstance().getOkHttpClient().newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(final Request request, final IOException e) {
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("set","xiazaierro");
                        Toast.makeText(context.getApplicationContext(), "保存在失败", Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onResponse(Response response) {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                try {
                    is = response.body().byteStream();
                    new File(getSDPath()+"/mntp").mkdir();
                    final File file = new File(getSDPath() + "/mntp/", fileName);
                    Log.d("set", file.toString());
                    fos = new FileOutputStream(file);
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                    }
                    fos.flush();

                    //如果下载文件成功，第一个参数为文件的绝对路径
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "保存在" + file.getPath(), Toast.LENGTH_LONG).show();
                        }
                    });

                } catch (IOException e) {

                } finally {
                    try {
                        if (is != null) is.close();
                    } catch (IOException e) {
                    }
                    try {
                        if (fos != null) fos.close();
                    } catch (IOException e) {
                    }
                }

            }
        });
    }

    public static String getSDPath(){
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED);   //判断sd卡是否存在
        if   (sdCardExist)
        {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
        }
        Log.d("set",sdDir.toString());
        return sdDir.toString();

    }

}
