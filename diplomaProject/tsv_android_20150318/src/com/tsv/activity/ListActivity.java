package com.tsv.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.tsv.R;
import com.tsv.model.db.Question;

public class ListActivity extends BaseActivity {

	private SimpleAdapter adapter;
	private List<HashMap<String, Object>> data;
	private int count = 30;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);
//		service = new PersonService(this);
		ListView listView = (ListView) this.findViewById(R.id.listView);

		// 获取到集合数据
//		List<Person> persons = service.getScrollData(0, 10);
//		List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
//		for (Person person : persons) {
//			HashMap<String, Object> item = new HashMap<String, Object>();
//			item.put("id", person.getId());
//			item.put("name", person.getName());
//			item.put("phone", person.getPhone());
//			item.put("amount", person.getAmount());
//			data.add(item);
//		}
		
		List<Question> list = new ArrayList<Question>();
		for(int i = 0;i< 30;i++){
			Question q = new Question();
			q.setQid(""+i);
			q.setTitle("title"+i);
			q.setCreateTime(System.currentTimeMillis());
			q.setState(1);
			list.add(q);
		}
		
		data = new ArrayList<HashMap<String,Object>>();
		for(Question ques : list){
			HashMap<String, Object> item = new HashMap<String, Object>();  
            item.put("qid", ques.getQid());
			item.put("question", ques.getTitle());  
            item.put("time", ques.getCreateTime());  
            item.put("state", ques.getState());  
            data.add(item);  
		}
		// 创建SimpleAdapter适配器将数据绑定到item显示控件上
		adapter = new SimpleAdapter(this, data, R.layout.list_item,
				new String[] { "question", "time", "state" }, 
				new int[] { R.id.txQuestion, R.id.txTime, R.id.txState });
		// 实现列表的显示
		listView.setAdapter(adapter);
		
		// 条目点击事件
		listView.setOnItemClickListener(new ItemClickListener());
		listView.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				System.out.println("onScrollStateChanged");
				int num = adapter.getCount();
				if(num > 300){
					return;
				}
				
				List<Question> list = new ArrayList<Question>();
				for(int i = 0;i< 30;i++){
					Question q = new Question();
					q.setQid(""+i);
					q.setTitle("title"+i);
					q.setCreateTime(System.currentTimeMillis());
					q.setState(1);
					list.add(q);
				}
				
				for(Question ques : list){
					HashMap<String, Object> item = new HashMap<String, Object>();  
					item.put("qid", ques.getQid());
					item.put("question", ques.getTitle());  
					item.put("time", ques.getCreateTime());  
					item.put("state", ques.getState());  
					data.add(item);  
				}
				
//                    adapter. = data;
				adapter.notifyDataSetChanged();
				
				//Toast.makeText(TestAdapter.this, "正在刷新", Toast.LENGTH_SHORT).show();
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				System.out.println("onScroll");
			}
		});
	}
	
	private class ItemClickListener implements OnItemClickListener{
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			ListView listView = (ListView) parent;  
            @SuppressWarnings("unchecked")
			HashMap<String, Object> data = 
            		(HashMap<String, Object>) listView.getItemAtPosition(position);  
            String qid = data.get("qid").toString();  
            Toast.makeText(getApplicationContext(), qid, 1).show();  
		}
	}

}
