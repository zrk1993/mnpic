package com.renkun.mnpic.ui.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.renkun.mnpic.R;
import com.renkun.mnpic.dao.DataProvider;

import net.youmi.android.AdManager;
import net.youmi.android.update.AppUpdateInfo;

/**
 * Created by rk on 2015/10/26.
 */
public class SettingsFragment extends PreferenceFragment {

    final static String Collection="Collection";
    final static String Cache="Cache";
    final static String About="About";
    final static String Edition="Edition";
    final static String News="news";
    final static String Statement="Statement";
    final static String Feedback="Feedback";
    final static String SignOut="SignOut";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference);

    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        switch (preference.getKey() ){
            case  Collection:showToast("图片保存在sd卡的 mntp 目录下");break;

            case  Cache:clearCache();
                showSnakbar("清除成功");
                break;
            case  About:showAlertDialog("关于", "      美女图片，每天更新海量美女图片，根本看不完!!!  可缩放，张张精品，更有保存及设为壁纸的功能!!!");
                break;

            case  Edition:showAlertDialog("版本",getAppVersionName(getActivity()));break;
            case  News:new UpdateHelper(getActivity()).execute();break;
            case  Statement:showAlertDialog("免责声明","美女图片内容来源于网络，我们尊重他人知识产权和其他合法权益。在使用本软件的过程中，如果您认为您的著作权/信息网络传播权被侵犯，请通过QQ和我们取得联系，出具权利通知（保证权利通知并未失实，否则相关法律责任由出具人承担），并详细说明侵权的内容，核实后我们将删除被控内容，断开相关连接");break;

            case  Feedback:showAlertDialog("意见反馈","请联系发送邮件至:1370940829@qq.com");break;

            case  SignOut:getActivity().finish();
                break;

            default:break;

        }


        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(
                (SharedPreferences.OnSharedPreferenceChangeListener) getActivity()
        );
    }
    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(
                (SharedPreferences.OnSharedPreferenceChangeListener) getActivity());
    }
    private void showSnakbar(String s){
        Snackbar.make(getView(), s, Snackbar.LENGTH_LONG).show();
    }

    private void showToast(String s){
        Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
    }

    private void clearCache(){
        Uri mUri = Uri.parse(DataProvider.SCHEME + DataProvider.AUTHORITY + 0);
        //分类的数据库
        Uri mUri2 = Uri.parse(DataProvider.SCHEME + DataProvider.AUTHORITY + 1);
        int i=getActivity().getContentResolver().delete(mUri, null, null);
        int  k=getActivity().getContentResolver().delete(mUri2, null, null);
        //将SharedPreferences记录的各类图片最新1d值清0；
        SharedPreferences.Editor editor=getActivity().getSharedPreferences("mnpic", Context.MODE_PRIVATE).edit();
        for (int j=1;j<8;j++){
            editor.putInt("MAX_ID_" + j, 0);
        }
        editor.apply();

    }
    private void showAlertDialog(String title,String message){
        new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("确定", null)
                .show();
    }

    public static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;

            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }
    //更新
    public class UpdateHelper extends AsyncTask<Void, Void, AppUpdateInfo> {
        private Context mContext;
        public UpdateHelper(Context context) {
            mContext = context;
        }

        @Override
        protected AppUpdateInfo doInBackground(Void... params) {
            try {
                // 在 doInBackground 中调用 AdManager 的 checkAppUpdate 即可从有米服务器获得应用更新信息。
                return AdManager.getInstance(mContext).syncCheckAppUpdate();
                // 此方法务必在非 UI 线程调用，否则有可能不成功。
            }
            catch (Throwable e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(final AppUpdateInfo result) {
            super.onPostExecute(result);
            try {
                if (result == null || result.getUrl() == null) {
                    // 如果 AppUpdateInfo 为 null 或它的 url 属性为 null，则可以判断为没有新版本。
                    Toast.makeText(mContext, "当前版本已经是最新版", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 这里简单示例使用一个对话框来显示更新信息
                new AlertDialog.Builder(mContext)
                        .setTitle("发现新版本")
                        .setMessage(result.getUpdateTips()) // 这里是版本更新信息
                        .setNegativeButton("马上升级",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse(result.getUrl()) );
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        mContext.startActivity(intent);
                                        // ps：这里示例点击“马上升级”按钮之后简单地调用系统浏览器进行新版本的下载，
                                        // 但强烈建议开发者实现自己的下载管理流程，这样可以获得更好的用户体验。
                                    }
                                })
                        .setPositiveButton("下次再说",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                }).create().show();
            }
            catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

}
