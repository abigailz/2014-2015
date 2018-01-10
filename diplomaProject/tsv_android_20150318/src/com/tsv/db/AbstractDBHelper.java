package com.tsv.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

abstract class AbstractDBHelper extends SQLiteOpenHelper {
	
	public static final int VESTION = 1;
	public static final String DB_DBNAME = "tsv.db";
	
	protected AbstractDBHelper(Context context){
		super(context, DB_DBNAME, null, VESTION);
	}

	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
	}

	
}
