package com.renkun.mnpic.ui.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
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
import com.squareup.picasso.Picasso;

/**
 * Created by rk on 2015/10/26.
 */
public class SettingsFragment extends PreferenceFragment {

    final static String Collection="Collection";
    final static String Cache="Cache";
    final static String About="About";
    final static String Edition="Edition";
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
                showSnakbar("正在清除缓存。。。");
                break;
            case  About:showAlertDialog("关于","      美女图片，每天更新海量美女图片，根本看不完!!!  可缩放，张张精品，更有保存及设为壁纸的功能!!!");
                break;

            case  Edition:showAlertDialog("版本",getAppVersionName(getActivity()));break;

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
        Uri mUri2 = Uri.parse(DataProvider.SCHEME + DataProvider.AUTHORITY +1);
        int i=getActivity().getContentResolver().delete(mUri, null,null);
        int  k=getActivity().getContentResolver().delete(mUri2,null,null);

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
}
