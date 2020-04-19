/*
 *  Copyright 2017 Google Inc. All Rights Reserved.
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.google.blockly.android.demo;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import com.google.blockly.android.AbstractBlocklyActivity;
import com.google.blockly.android.codegen.CodeGenerationRequest;
import com.google.blockly.android.codegen.LanguageDefinition;
import com.google.blockly.model.DefaultBlocks;
import com.google.blockly.util.installApkUtil;
import com.google.blockly.util.submitUtil;
import java.util.Arrays;
import java.util.List;


public class AndroidActivity extends AbstractBlocklyActivity {

    private static Context mContext;
    private static final String TAG = "AndroidActivity";
    private static String screen_id = "1";
    private static String SAVE_FILENAME = "android_workspace_" + screen_id + ".xml";
    private static final String AUTOSAVE_FILENAME = "android_workspace_temp.xml";

    //Top View
    private static Button btn_screen1, btn_screen2, btn_screen3, btn_screen4;
    private static DrawerLayout drawer_layout;

    // Add custom blocks to this list.
    private static final List<String> ANDROID_BLOCK_DEFINITIONS = Arrays.asList(
            DefaultBlocks.COLOR_BLOCKS_PATH,
            DefaultBlocks.LOGIC_BLOCKS_PATH,
            DefaultBlocks.LOOP_BLOCKS_PATH,
            DefaultBlocks.MATH_BLOCKS_PATH,
            DefaultBlocks.TEXT_BLOCKS_PATH,
            DefaultBlocks.VARIABLE_BLOCKS_PATH,
            DefaultBlocks.LIST_BLOCKS_PATH,
            DefaultBlocks.PROCEDURE_BLOCKS_PATH,
            "android/android_blocks.json"

    );

    private static final List<String> ANDROID_GENERATORS = Arrays.asList(
            "generators/android_compressed.js"
    );

    private static final LanguageDefinition ANDROID_LANGUAGE_DEF
            = LanguageDefinition.ANDROID_LANGUAGE_DEFINITION;

    private TextView mGeneratedTextView, mGeneratedTextView2;
    private Handler mHandler;
    private static String generatedCodeSave;

    private String mNoCodeText;

    CodeGenerationRequest.CodeGeneratorCallback mCodeGeneratorCallback =
            new CodeGenerationRequest.CodeGeneratorCallback() {
                @Override
                public void onFinishCodeGeneration(final String generatedCode) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mGeneratedTextView.setText(generatedCode+"\n}}");
                            DemoUtil.updateTextMinWidth(mGeneratedTextView, AndroidActivity.this);
                            generatedCodeSave=generatedCode + "\n}}";

                        }
                    });
                }
            };

    @Override
    public boolean onSupportNavigateUp() {
//

        return super.onSupportNavigateUp();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return onDemoItemSelected(item, this) || super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    static boolean onDemoItemSelected(MenuItem item, AbstractBlocklyActivity activity) {
        int id = item.getItemId();
        boolean flag=false;
        if (id == R.id.action_submitCode) {
            Toast.makeText(mContext,"正在下载...(需30-60秒)",Toast.LENGTH_LONG).show();
            submitUtil.submit(mContext, generatedCodeSave);//提交到服务器
            flag=true;
        }
        else if(id==R.id.action_installApk)
        {
            installApkUtil.installApk(mContext, "default.apk");
            flag=true;
        }
        else if(id==R.id.action_refresh)
        {
            mContext.startActivity(new Intent(mContext,AndroidActivity.class));
            flag=true;
        }

        return flag;

    }



    //重载入: 当前页面xml + 代码生成Area
    @Override
    protected void onResume() {
        super.onResume();
        screen_id = getIntent().getStringExtra("screen_id");
        SAVE_FILENAME = "android_workspace_" + screen_id + ".xml";
        onLoadWorkspace();
        if (getController().getWorkspace().hasBlocks()) {
            onRunCode();
        } else {
            Log.i(TAG, "No blocks in workspace. Skipping run request.");
        }

        Log.i(TAG, SAVE_FILENAME);
    }


//Top View bind


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new Handler();
        mContext=this;


    }

    private int content_seq = 1;
    @Override
    protected View onCreateContentView(int parentId) {
        View root;
        if(content_seq == 1){
            root = getLayoutInflater().inflate(R.layout.split_content, null);
            mGeneratedTextView = root.findViewById(R.id.generated_code);
            DemoUtil.updateTextMinWidth(mGeneratedTextView, this);
        }
        else if(content_seq == 2){
            root = getLayoutInflater().inflate(R.layout.split_content2, null);
            mGeneratedTextView2 = root.findViewById(R.id.generated_code_2);
            DemoUtil.updateTextMinWidth(mGeneratedTextView2, this);
        }
        else if(content_seq == 3){
            root = getLayoutInflater().inflate(R.layout.split_content3, null);
        }
        else if(content_seq == 4){
            root = getLayoutInflater().inflate(R.layout.split_content4, null);
        }
        else{
            root = null;
        }
        content_seq++;


        mNoCodeText = mGeneratedTextView.getText().toString(); // Capture initial value.

        return root;

    }

    @Override
    protected int getActionBarMenuResId() {
        return R.menu.android_actionbar;
    }
    @NonNull
    @Override
    protected List<String> getBlockDefinitionsJsonPaths() {
        return ANDROID_BLOCK_DEFINITIONS;
    }

    @NonNull
    @Override
    protected LanguageDefinition getBlockGeneratorLanguage() {
        return ANDROID_LANGUAGE_DEF;//这个是Android.js
    }

    @NonNull
    @Override
    protected String getToolboxContentsXmlPath() {
        return DefaultBlocks.TOOLBOX_PATH_Android;
    }

    @NonNull
    @Override
    protected List<String> getGeneratorsJsPaths() {
        return ANDROID_GENERATORS;//这个是用户extend的js
    }

    @NonNull
    @Override
    protected CodeGenerationRequest.CodeGeneratorCallback getCodeGenerationCallback() {
        // Uses the same callback for every generation call.
        return mCodeGeneratorCallback;
    }

    @Override
    public void onClearWorkspace() {
        super.onClearWorkspace();
        mGeneratedTextView.setText(mNoCodeText);
        DemoUtil.updateTextMinWidth(mGeneratedTextView, this);
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

    private String getWorkspaceById(int id){
        return "workspace_" + id + ".xml";
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
