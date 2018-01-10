package com.tsv.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.evidence.test.R;


public class LoginActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        //获取控件
        Button login = (Button) findViewById(R.id.login);
        Button cancel = (Button) findViewById(R.id.cancel);
        final RadioGroup type = (RadioGroup) findViewById(R.id.radioGroup);

        //监听登陆按钮
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < type.getChildCount(); i++) {
                    RadioButton r = (RadioButton) type.getChildAt(i);
                    if (r.isChecked()) {
                        String str = r.getText().toString();
                        switch (str) {
                            case "执勤":
                                Intent intentOne = new Intent(LoginActivity.this, PatrolTaskList.class);
                                startActivity(intentOne);
                                break;
                            case "分配":
                                Intent intentTwo = new Intent(LoginActivity.this, AssignTaskList.class);
                                startActivity(intentTwo);
                                break;
                            case "办事":
                                Intent intentThere = new Intent(LoginActivity.this, WorkingManList.class);
                                startActivity(intentThere);
                                break;
                        }
                        break;
                    }
                }
            }
        });

        //监听退出按钮
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                builder.setMessage("确定退出?");
                builder.setPositiveButton(R.string.ok,
                        new android.content.DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                arg0.dismiss();
                                finish();
                            }
                        });
                builder.setNegativeButton(R.string.cancel,
                        new android.content.DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.create().show();
            }
        });
    }
}
