package com.google.blockly.util;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import com.google.blockly.android.demo.BuildConfig;

import java.io.File;
import java.io.IOException;

public class installApkUtil {

    /** Input : context: Context上下文
                apkPath: apk文件名称

        Desc : 调用Intent安装Apk
    * */

    //4月3日------权限异常
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void installApk(Context context, String apkPath) {
        if (context == null || TextUtils.isEmpty(apkPath)) {
            return;
        }

        Log.d("lyt","Datadir = " + context.getDataDir());

        File file = new File(context.getDataDir() + File.separator + apkPath);

        try{
            Runtime runtime = Runtime.getRuntime();

            String command = "chmod 777 " + file.getPath();
            Process proc = runtime.exec(command);
            Log.d("lyt", "command = " + command);

            proc.destroy();
        }catch (IOException e){
            Log.d("lyt","Exec Fail !");
            e.printStackTrace();
        }


        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//对目标应用临时授权该Uri所代表的文件
        intent.addCategory(Intent.CATEGORY_DEFAULT);

        Uri fileURI;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // 直接跳过权限
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            fileURI = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".fileProvider", file);

            //fileURI = Uri.fromFile(file);
        }else{
            //fileURI = FileProvider.getUriForFile(context,"com.google.blockly",file);
            fileURI = Uri.fromFile(file);
        }
        Log.d("lyt","fileURI = " + fileURI);
        intent.setDataAndType(fileURI, "application/vnd.android.package-archive");
        //跳转
        if (context.getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) != null) {
            context.startActivity(intent);
        } else {
            Toast.makeText(context, "没有找到对应的程序", Toast.LENGTH_SHORT).show();
        }


    }
}
