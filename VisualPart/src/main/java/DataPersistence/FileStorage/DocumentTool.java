package DataPersistence.FileStorage;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;

/**
 * Created by Administrator on 2020/2/2.
 */

/**
 * 包含对于文件的具体操作
 */
public class DocumentTool {
    public static final String OUT_SPLIT="$";
    public static final String ROOTFILE="/Blockly/";
    /**
     * 【动态申请SD卡读写的权限】
     * Android6.0之后系统对权限的管理更加严格了，不但要在AndroidManifest中添加，还要在应用运行的时候动态申请
     * **/
    private static final int REQUEST_EXTERNAL_STORAGE = 1 ;
    private static String[] PERMISSON_STORAGE = {"android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};


    public static void verifyStoragePermissions(Activity activity){
        try {
            int permission = ActivityCompat.checkSelfPermission(activity,"android.permission.WRITE_EXTERNAL_STORAGE");
            if(permission!= PackageManager.PERMISSION_GRANTED){/**【判断是否已经授予权限】**/
                ActivityCompat.requestPermissions(activity,PERMISSON_STORAGE,REQUEST_EXTERNAL_STORAGE);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**【检查文件目录是否存在，不存在就创建新的目录】**/
    public static void checkFilePath(File file ,boolean isDir){
        if(file!=null){
            if(!isDir){     //如果是文件就返回父目录
                file = file.getParentFile();
            }
            if(file!=null && !file.exists()){
                file.mkdirs();
            }
        }
    }

    /**【创建一个新的文件夹】**/
    public static void addFolder(String folderName){
        try {
            if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                File sdCard = Environment.getExternalStorageDirectory();
                File newFolder = new File(sdCard + File.separator +ROOTFILE+ folderName);
                if(!newFolder.exists()){
                    boolean isSuccess = newFolder.mkdirs();
                    Log.i("TAG:","文件夹创建状态--->" + isSuccess);
                }
                Log.i("TAG:","文件夹所在目录：" + newFolder.toString());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**【创建文件】**/
    public static void addFile(String fileName){
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            try {
                File sdCard = Environment.getExternalStorageDirectory();
                File newFile = new File(sdCard.getCanonicalPath()+File.separator+ROOTFILE+fileName);

                System.out.println("文件日志："+"待创建的文件路径："+newFile.getAbsolutePath());
                if(!newFile.exists()){
                    boolean isSuccess = newFile.createNewFile();
                    Log.i("TAG:","文件创建状态--->"+isSuccess);
                    Log.i("TAG:","文件所在路径："+newFile.toString());
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**【删除文件】**/
    public static void deleteFile(File file){
        if(file.exists()){
            if(file.isFile()){                      //判断是否是文件
                boolean isSucess = file.delete();
                Log.i("TAG:","文件删除状态--->" + isSucess);
            }else if(file.isDirectory()){           //判断是否是文件夹
                File files[] = file.listFiles();    //声明目录下所有文件
                for (int i=0;i<files.length;i++){   //遍历目录下所有文件
                    deleteFile(files[i]);           //把每个文件迭代删除
                }
                boolean isSucess = file.delete();
                Log.i("TAG:","文件夹删除状态--->" + isSucess);
            }
        }
    }

    /**【重写数据到文件】**/
    public static void writeData(String path , String fileData){
        try {
            if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                File sdCard = Environment.getExternalStorageDirectory();
                File file = new File(sdCard.getCanonicalPath()+File.separator+ROOTFILE+path);
                String[] datas=fileData.split(OUT_SPLIT);
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
                for(int i=0;i<datas.length;i++){
                    bufferedWriter.write(datas[i]);
                    bufferedWriter.newLine();
                }

                bufferedWriter.close();
                Log.i("TAG:","将数据写入到文件中："+fileData);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**【续写数据到文件】**/
    public static void writtenFileData(String path , String data){
        try {
            if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                File sdCard = Environment.getExternalStorageDirectory();
                File file = new File(sdCard.getCanonicalPath()+File.separator+ROOTFILE+path);
                RandomAccessFile raf = new RandomAccessFile(file,"rw");  //按读写方式
                raf.seek(file.length());                                        //将文件指针移到文件尾
                raf.write(data.getBytes("UTF-8"));                //将数据写入到文件中
                Log.i("TAG:","要续写进去的数据：" + data);
                raf.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**【读取文件内容,记录按行读取，并传出时，采用“$”分割】**/
    public static String readFileContent(String path){
        try {
            if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                File sdCard = Environment.getExternalStorageDirectory();
                File file = new File(sdCard.getCanonicalPath()+File.separator+ROOTFILE+path);

                System.out.println("读取文件："+file.getAbsolutePath());
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                String line="";
                StringBuffer data=new StringBuffer("");

                while((line=bufferedReader.readLine())!=null){
                    data.append(line);
                    data.append("$");
                }

                return data.toString();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**【判断文件是否存在】**/
    public static boolean isFileExists(String fileName){
        File file = null;
        try {
            File sdCard = Environment.getExternalStorageDirectory();
            file = new File(sdCard.getCanonicalPath()+File.separator+ROOTFILE+fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file.exists();
    }

    /**【判断文件夹是否存在】**/
    public static boolean isFolderExists(String directoryPath){
        if(TextUtils.isEmpty(directoryPath)){
            return false;
        }
        File dire = null;
        try {
            File sdCard = Environment.getExternalStorageDirectory();
            dire = new File(sdCard.getCanonicalPath()+File.separator+ROOTFILE+directoryPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (dire.exists() && dire.isDirectory());  //如果是文件夹并且文件夹存在则返回true
    }

    /**【获取文件夹名称】**/
    public static String getFolderName(String folderName){
        if(TextUtils.isEmpty(folderName)){
            return folderName;
        }
        int filePosi = folderName.lastIndexOf(File.separator);
        return (filePosi == -1 ) ? "" : folderName.substring(0 , filePosi);
    }

    /**【重命名文件】**/
    public static boolean renameFile(String oldFileName , String newFileName){
        File oldName = new File(oldFileName);
        File newName = new File(newFileName);
        return oldName.renameTo(newName);
    }

    /**【判断文件夹里是否有文件】**/
    public static boolean hasFileExists(String folderPath){
        File file = null;
        try {
            File sdCard = Environment.getExternalStorageDirectory();
            file = new File(sdCard.getCanonicalPath()+File.separator+ROOTFILE+folderPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(file.exists()){
            File [] files = file.listFiles();
            if(files.length>0){
                return true;
            }
        }
        return false;
    }

    /**【复制文件】参数为：String **/
    public static int copyFile(String fromFile , String toFile){
        try {
            InputStream fosfrom = new FileInputStream(fromFile);
            OutputStream outto = new FileOutputStream(toFile);
            byte[] bt = new byte[1024];
            int len = fosfrom.read(bt);
            if(len > 0){
                outto.write(bt,0,len);
            }
            fosfrom.close();
            outto.close();
            return 0;
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }
    /**【复制文件】参数为：File  **/
    public static int copyFile(File formFile , File toFile){
        try {
            InputStream forform = new FileInputStream(formFile);
            OutputStream forto = new FileOutputStream(toFile);
            byte [] bt = new byte[1024];
            int len = forform.read(bt);
            if(len > 0){
                forto.write(bt,0,len);
            }
            forform.close();
            forto.close();
            return 0;
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }
    /**【复制文件】使用：AssetManager  **/
    public static void copyFileFormAsset(Context context,String assetFile , String toFilePath){
        if(!new File(toFilePath).exists()){
            try {
                AssetManager assetManager = context.getAssets();
                InputStream is = assetManager.open(assetFile);
                OutputStream os = new FileOutputStream(new File(toFilePath));
                byte [] bt = new byte[1024];
                int len = 0;
                while ((is.read(bt))>0){        //循环从输入流读取
                    os.write(bt,0,len);     //将读取到的输入流写到输出流
                }
                is.close();
                os.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**【复制文件夹】**/
    public static int copyDir(String fromFolder , String toFolder){
        File [] currentFiles;
        File root = new File(fromFolder);
        if(!root.exists()){                     //如果文件不存在就返回出去
            return -1;
        }
        currentFiles = root.listFiles();        //存在则获取当前目录下的所有文件
        File targetDir = new File(toFolder);    //目标目录
        if(!targetDir.exists()){                //不存在就创建新目录
            targetDir.mkdirs();
        }
        for(int i=0;i<currentFiles.length;i++){ //遍历currentFiles下的所有文件
            if(currentFiles[i].isDirectory()){  //如果当前目录为子目录
                copyDir(currentFiles[i].getPath() + "/" , currentFiles[i].getName()+"/");  /**进行当前函数递归操作**/
            }else{                              //当前为文件，则进行文件拷贝
                copyFile(currentFiles[i].getPath() , toFolder + currentFiles[i].getName());
            }
        }
        return 0;
    }

}
