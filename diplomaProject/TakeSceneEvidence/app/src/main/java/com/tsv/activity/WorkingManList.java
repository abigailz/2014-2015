package com.tsv.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.evidence.test.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class WorkingManList extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_list);
        //填充ListView
        List<HashMap<String, String>> taskData = new ArrayList<>();

        HashMap<String, String> hashMapOne = new HashMap<>();
        hashMapOne.put("task_title", "任务标题");
        hashMapOne.put("description", "摘要");
        hashMapOne.put("status", "未处理");
        hashMapOne.put("task_time", "2015-1-5");
        taskData.add(hashMapOne);

        HashMap<String, String> hashMapTwo = new HashMap<>();
        hashMapTwo.put("task_title", "任务标题");
        hashMapTwo.put("description", "摘要");
        hashMapTwo.put("status", "未关闭");
        hashMapTwo.put("task_time", "2015-1-5");
        taskData.add(hashMapTwo);

        HashMap<String, String> hashMapThere = new HashMap<>();
        hashMapThere.put("task_title", "任务标题");
        hashMapThere.put("description", "摘要");
        hashMapThere.put("status", "已完成");
        hashMapThere.put("task_time", "2015-1-5");
        taskData.add(hashMapThere);

        SimpleAdapter task_list = new SimpleAdapter(this, taskData, R.layout.task_item,
                new String[]{"task_title", "description", "status", "task_time"},
                new int[]{R.id.task_title, R.id.description, R.id.status, R.id.task_time});

        ListView list = (ListView) findViewById(R.id.task_list);
        list.setAdapter(task_list);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView text = (TextView) view.findViewById(R.id.status);
                String str = text.getText().toString();
                Intent intent;
                switch (str) {
                    case "未处理":
                        intent = new Intent(WorkingManList.this, WorkingManSolveTask.class);
                        startActivity(intent);
                        break;
                    case "未关闭":
                        intent = new Intent(WorkingManList.this, TaskNoClosed.class);
                        startActivity(intent);
                        break;
                    case "已完成":
                        intent = new Intent(WorkingManList.this, TaskCompleted.class);
                        startActivity(intent);
                        break;
                }
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
                AlertDialog.Builder builder = new AlertDialog.Builder(WorkingManList.this);
                builder.setMessage("确定退出?");
                builder.setPositiveButton(R.string.ok,
                        new android.content.DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                arg0.dismiss();
                                Intent it = new Intent(WorkingManList.this, LoginActivity.class);
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
