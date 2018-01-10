package com.tsv.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.evidence.test.R;

import java.lang.reflect.Field;

public class PatrolCreateTask extends ActionBarActivity {
    private Toast toast = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patrol_create_task);

        Button takephone = (Button) findViewById(R.id.takePhone);
        Button location = (Button) findViewById(R.id.location);
        Button upload = (Button) findViewById(R.id.upload);
        Button ok = (Button) findViewById(R.id.ok);
        final TextView coord = (TextView) findViewById(R.id.coord);

        takephone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coord.setText("\"12.34\",\"34.56\"");
                toast = Toast.makeText(PatrolCreateTask.this, "获取坐标成功", Toast.LENGTH_LONG);
                if (coord.getText() == " ") {
                    toast = Toast.makeText(PatrolCreateTask.this, "获取坐标失败", Toast.LENGTH_LONG);
                }
                toast.setGravity(Gravity.CENTER, toast.getXOffset() / 3, toast.getYOffset() / 3);
                toast.show();
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toast = Toast.makeText(PatrolCreateTask.this, "上传成功", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, toast.getXOffset() / 3, toast.getYOffset() / 3);
                toast.show();
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PatrolCreateTask.this);
                builder.setMessage("提交成功");
                builder.setPositiveButton(R.string.ok,
                        new android.content.DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                arg0.dismiss();
                                Intent it = new Intent(PatrolCreateTask.this, PatrolTaskList.class);
                                startActivity(it);
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
        getSupportActionBar().setBackgroundDrawable(this.getBaseContext().getResources().getDrawable(R.drawable.BackBar));
        getSupportActionBar().show();
        setOverflowShowingAlways();
    }

    private void setOverflowShowingAlways() {
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            menuKeyField.setAccessible(true);
            menuKeyField.setBoolean(config, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_back, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.back:
                AlertDialog.Builder builder = new AlertDialog.Builder(PatrolCreateTask.this);
                builder.setMessage("确定退出?");
                builder.setPositiveButton(R.string.ok,
                        new android.content.DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                arg0.dismiss();
                                Intent it = new Intent(PatrolCreateTask.this, LoginActivity.class);
                                startActivity(it);
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
                break;
        }
        return true;
    }
}
