package com.google.blockly.util;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class submitUtil {


    static MediaType mediaType = MediaType.parse("text/plain;charset=UTF-8");


    public static void submitOnly(String generatedCodeSave){
        RequestBody requestBody = RequestBody.create(generatedCodeSave,mediaType);


        final OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .post(requestBody)
                .url("http:47.102.108.197:8080/submitOnly")
                .build();
        client.newCall(request).enqueue( new Callback() {

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                if (response.isSuccessful()) {
                    Log.d("lyt", "status = " + response.code());
                }else
                    Log.d("lyt", "Time out !");

            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }
        });
    }

    /**
        Input : generatedCodeSave :生成的源代码
        Desc : 发送Post请求，提交源代码
        Return : default.apk
     **/
    public static void submit(Context context, String generatedCodeSave) {


        RequestBody requestBody = RequestBody.create(generatedCodeSave,mediaType);


        final OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .post(requestBody)
                .url("http:47.102.108.197:8080/postApk")
                .build();
        client.newCall(request).enqueue(callback);
    }


    private static Callback callback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            Log.d("lyt", "失败");
        }

        @Override
        public void onResponse(Call call, Response response){
            if (response.isSuccessful()) {//回调的方法执行在子线程。
                Log.d("lyt", "status = " + response.code());
                try {
                    String mDestFileName = "/data/com.google.blockly.demo/files/default.apk";
                    InputStream is = response.body().byteStream();//从服务器得到输入流对象
                    File targetFile = new File(Environment.getDataDirectory(), mDestFileName);

                    Log.d("lyt", "targetFile = " + targetFile.getPath());

                    if (targetFile.exists()) {
                        Log.d("lyt", "file existed! Delete it.");
                        targetFile.delete();
                    }

                    FileOutputStream fos = new FileOutputStream(targetFile);
                    byte[] buffer = new byte[1024 * 100];
                    int length = 0;
                    int current = 0;

                    Log.d("lyt", "is.available() = " + is.available());

                    while ((length = is.read(buffer)) != -1) {
                        fos.write(buffer, 0, length);
                        fos.flush();
                        current += length;
                        Log.d("lyt", "current = " + current);
                    }
                    fos.close();
                    is.close();

                    Log.d("lyt", "Successful!");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    };




    /**
     Input : 无
     Desc : 下载打包后的Apk
     Return : <body> default.apk </body>
     **/
    public static void getApk() {

        final OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url("http:47.102.108.197:8080/download")
                .build();
        client.newCall(request).enqueue(callback);
    }
}
