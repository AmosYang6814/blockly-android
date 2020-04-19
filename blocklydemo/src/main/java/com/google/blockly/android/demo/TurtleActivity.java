/*
 * Copyright 2015 Google Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.blockly.android.demo;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.google.blockly.android.AbstractBlocklyActivity;
import com.google.blockly.android.BlocklySectionsActivity;
import com.google.blockly.android.codegen.CodeGenerationRequest;
import com.google.blockly.android.control.BlocklyController;
import com.google.blockly.model.DefaultBlocks;
import com.google.blockly.utils.BlockLoadingException;
import okhttp3.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;


/**
 * Demo app with the Blockly Games turtle game in a webview.
 */
public class TurtleActivity extends BlocklySectionsActivity {
    private static final String TAG = "TurtleActivity";

    private static final String SAVE_FILENAME = "turtle_workspace.xml";
    private static final String AUTOSAVE_FILENAME = "turtle_workspace_temp.xml";
    private static String generatedCodeSave;
    private String mNoCodeText;

    static final List<String> TURTLE_BLOCK_DEFINITIONS = Arrays.asList(
            DefaultBlocks.COLOR_BLOCKS_PATH,
            DefaultBlocks.LOGIC_BLOCKS_PATH,
            DefaultBlocks.LOOP_BLOCKS_PATH,
            DefaultBlocks.MATH_BLOCKS_PATH,
            DefaultBlocks.TEXT_BLOCKS_PATH,
            DefaultBlocks.VARIABLE_BLOCKS_PATH,
            "turtle/turtle_blocks.json"
    );
    static final List<String> TURTLE_BLOCK_GENERATORS = Arrays.asList(
            "turtle/generators.js"
            //"yail/yail.js"
    );
    private static final int MAX_LEVELS = 10;
    private static final String[] LEVEL_TOOLBOX = new String[MAX_LEVELS];

    static {
        LEVEL_TOOLBOX[0] = "toolbox_basic.xml";
        LEVEL_TOOLBOX[1] = "toolbox_basic.xml";
        LEVEL_TOOLBOX[2] = "toolbox_colour.xml";
        LEVEL_TOOLBOX[3] = "toolbox_colour_pen.xml";
        LEVEL_TOOLBOX[4] = "toolbox_colour_pen.xml";
        LEVEL_TOOLBOX[5] = "toolbox_colour_pen.xml";
        LEVEL_TOOLBOX[6] = "toolbox_colour_pen.xml";
        LEVEL_TOOLBOX[7] = "toolbox_colour_pen.xml";
        LEVEL_TOOLBOX[8] = "toolbox_colour_pen.xml";
        LEVEL_TOOLBOX[9] = "toolbox_advanced.xml";
    }

    private final Handler mHandler = new Handler();
    private TextView mGeneratedTextView;
    CodeGenerationRequest.CodeGeneratorCallback mCodeGeneratorCallback =
            new CodeGenerationRequest.CodeGeneratorCallback() {
                @Override
                public void onFinishCodeGeneration(final String generatedCode) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mGeneratedTextView.setText(generatedCode);
                            DemoUtil.updateTextMinWidth(mGeneratedTextView, TurtleActivity.this);
                            generatedCodeSave=generatedCode;
                        }
                    });
                }
            };

    @Override
    public void onLoadWorkspace() {
        mBlocklyActivityHelper.loadWorkspaceFromAppDirSafely(SAVE_FILENAME);
    }

    @Override
    public void onSaveWorkspace() {
        mBlocklyActivityHelper.saveWorkspaceToAppDirSafely(SAVE_FILENAME);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return onDemoItemSelected(item, this) || super.onOptionsItemSelected(item);
    }


    static boolean onDemoItemSelected(MenuItem item, AbstractBlocklyActivity activity) {
        BlocklyController controller = activity.getController();
        int id = item.getItemId();
        boolean loadWorkspace = false;
        String filename = "";
        if (id == R.id.action_demo_android) {
            loadWorkspace = true;
            filename = "android.xml";
        } else if (id == R.id.action_demo_lacey_curves) {
            loadWorkspace = true;
            filename = "lacey_curves.xml";
        } else if (id == R.id.action_demo_paint_strokes) {
            loadWorkspace = true;
            filename = "paint_strokes.xml";
        }else if(id==R.id.action_submit){
            //提交代码至服务器解析
            //OkHttpClient client = new OkHttpClient();
            //Toast.makeText(MyApplication.getContext(),generatedCodeSave,Toast.LENGTH_LONG).show();
            Log.d("logCode","ing...");
            Log.d("logCode",generatedCodeSave);

            //MediaType fileType = MediaType.parse("File/*");
            //File file=new File("path");
            //RequestBody body=RequestBody.create(file,fileType);

            generatedCodeSave="public class default{\n" +
                    "public static void main(String[] args)\n" +
                    "{\n" +
                    generatedCodeSave +
                    "}}";


            OkHttpClient client = new OkHttpClient();
            final Request request = new Request.Builder()
                    .url("http://47.102.108.197/?" + "type=codeToAPK" + "&&code=" +generatedCodeSave)//请求服务地址47.102.108.197/codeAPK
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d("logCode","失败");
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if(response.isSuccessful()){//回调的方法执行在子线程。
                            Log.d("logCode","status = "+response.code());
                            Log.d("logCode","Uploading Generated Code...");//test


                        try{
                            InputStream is = response.body().byteStream();//从服务器得到输入流对象

                            String mDestFileName="/data/com.google.blockly.demo/files/default.class";
                            File targetFile = new File(Environment.getDataDirectory(),mDestFileName);
                            Log.d("logCode",(Environment.getDataDirectory()).toString());

                            if(targetFile.exists()){
                                Log.i("logCode", "file existed!");
                                return;
                            }
//                            if(!targetFile.getParentFile().exists()){
//                                targetFile.getParentFile().mkdirs();
//                            }

                            FileOutputStream fos = new FileOutputStream(targetFile);
                            byte[] buffer = new byte[1024*100];
                            int length=0;
                            int current = 0;

                            Log.d("logCode", "is.available() = "+ is.available());

                            while((length=is.read(buffer)) != -1){
                                fos.write(buffer, 0, length);
                                fos.flush();
                                current += length;
                                Log.d("logCode", "length = "+length);
                                Log.d("logCode", "cur = "+current);
                            }
                            //5. 文件下载完成
                            fos.close();

                        }catch(Exception e)
                        {
                            e.printStackTrace();
                        }

                        //do other
                        //...


                }
            }



        });

        }

        if (loadWorkspace) {
            String assetFilename = "turtle/demo_workspaces/" + filename;
            try {
                controller.loadWorkspaceContents(activity.getAssets().open(assetFilename));
            } catch (IOException | BlockLoadingException e) {
                throw new IllegalStateException(
                        "Couldn't load demo workspace from assets: " + assetFilename, e);
            }
            addDefaultVariables(controller);
            return true;
        }

        return false;
    }

    @NonNull
    @Override
    protected List<String> getBlockDefinitionsJsonPaths() {
        // Use the same blocks for all the levels. This lets the user's block code carry over from
        // level to level. The set of blocks shown in the toolbox for each level is defined by the
        // toolbox path below.
        return TURTLE_BLOCK_DEFINITIONS;
    }

    @Override
    protected int getActionBarMenuResId() {
        return R.menu.turtle_actionbar;
    }

    @NonNull
    @Override
    protected List<String> getGeneratorsJsPaths() {
        return TURTLE_BLOCK_GENERATORS;
    }

    @NonNull
    @Override
    protected String getToolboxContentsXmlPath() {
        // Expose a different set of blocks to the user at each level.
        return "turtle/" + LEVEL_TOOLBOX[getCurrentSectionIndex()];
    }

    @Override
    protected void onInitBlankWorkspace() {
        addDefaultVariables(getController());
    }

    @NonNull
    @Override
    protected ListAdapter onCreateSectionsListAdapter() {
        // Create the game levels with the labels "Level 1", "Level 2", etc., displaying
        // them as simple text items in the sections drawer.
        String[] levelNames = new String[MAX_LEVELS];
        for (int i = 0; i < MAX_LEVELS; ++i) {
            levelNames[i] = "Level " + (i + 1);
        }
        return new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_activated_1,
                android.R.id.text1,
                levelNames);
    }

    @Override
    protected boolean onSectionChanged(int oldSection, int newSection) {
        reloadToolbox();
        return true;
    }

    @Override
    protected View onCreateContentView(int parentId) {
        View root = getLayoutInflater().inflate(R.layout.turtle_content, null);

        mGeneratedTextView =root.findViewById(R.id.generated_code);
        DemoUtil.updateTextMinWidth(mGeneratedTextView, this);
        mNoCodeText = mGeneratedTextView.getText().toString(); // Capture initial value.

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

        return root;
    }

    @NonNull
    @Override
    protected CodeGenerationRequest.CodeGeneratorCallback getCodeGenerationCallback() {
        return mCodeGeneratorCallback;
    }

    static void addDefaultVariables(BlocklyController controller) {
        // TODO: (#22) Remove this override when variables are supported properly
        controller.addVariable("item");
        controller.addVariable("count");
        controller.addVariable("marshmallow");
        controller.addVariable("lollipop");
        controller.addVariable("kitkat");
        controller.addVariable("android");
    }

    /**
     * Optional override of the save path, since this demo Activity has multiple Blockly
     * configurations.
     * @return Workspace save path used by this Activity.
     */
    @Override
    @NonNull
    protected String getWorkspaceSavePath() {
        return SAVE_FILENAME;
    }

    /**
     * Optional override of the auto-save path, since this demo Activity has multiple Blockly
     * configurations.
     * @return Workspace auto-save path used by this Activity.
     */
    @Override
    @NonNull
    protected String getWorkspaceAutosavePath() {
        return AUTOSAVE_FILENAME;
    }
}
