package com.dawn.dawning.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.dawn.dawning.R;

public class ProgramDetailDialog extends Dialog {
    TextView mProgramName;

    public ProgramDetailDialog(Context context){
        super(context);
    }

    public ProgramDetailDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(super.getContext()).inflate(R.layout.program_detail_popup_dialog, null);
        setContentView(view);
    }

    private void initViewEvent(){

    }


    public void show(){


        Window win = getWindow();
        if (win != null) {
            win.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams lp = win.getAttributes();
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            win.setAttributes(lp);
            // dialog 布局位于底部
            win.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL);
            // 设置进出场动画
            win.setWindowAnimations(R.style.BottomDialog_Animation);
            super.show();
        }

    }




}
