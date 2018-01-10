package com.tsv.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DBHelper extends AbstractDBHelper {
	
	public static String USER_TABLE = new StringBuilder()
		.append("CREATE TABLE tb_user (")
		.append(" id int(32) NOT NULL AUTO_INCREMENT,")
		.append(" uid varchar(32) NOT NULL ,")
		.append(" userName varchar(32) NOT NULL,")
		.append(" type int(10) NOT NULL ,")
		.append(" pwd varchar(64) NOT NULL,")
		.append(" tmsi varchar(32) NOT NULL DEFAULT '0',")
		.append(" createTime bigint(32) NOT NULL,")
		.append(" PRIMARY KEY (id)")
		.append(") ")
		.toString();
		
	public static String QUESTION_TABLE = new StringBuilder()
		.append(" CREATE TABLE tb_question (")
		.append(" id bigint(32) NOT NULL AUTO_INCREMENT,")
		.append(" qid varchar(32) NOT NULL ,")
		.append(" uid varchar(32) NOT NULL,")
		.append(" desc varchar(500) DEFAULT NULL,")
		.append(" title varchar(250) DEFAULT NULL ,")
		.append(" state int(4) NOT NULL ,")
		.append(" closeTime bigint(32) DEFAULT '0',")
		.append(" createTime bigint(32) NOT NULL,")
		.append(" url varchar(255) DEFAULT NULL,")
		.append(" loc varchar(64) DEFAULT NULL,")
		.append(" PRIMARY KEY (id)")
		.append(") ")
		.toString();
	
	public static String QUESTION_LOG_TABLE = new StringBuilder()
		.append(" CREATE TABLE tb_question_log (")
		.append(" creator varchar(255) DEFAULT NULL,")
		.append(" id int(10) NOT NULL AUTO_INCREMENT,")
		.append(" qid varchar(32) NOT NULL,")
		.append(" uid varchar(32) NOT NULL,")
		.append(" state int(4) NOT NULL,")
		.append(" url varchar(255) DEFAULT NULL,")
		.append(" createTime bigint(32) NOT NULL,")
		.append(" loc varchar(64) DEFAULT NULL,")
		.append(" desc varchar(500) DEFAULT NULL,")
		.append(" PRIMARY KEY (id)")
		.append(" )")
		.toString();
	
	
	public DBHelper(Context context){
		super(context);
	}
	
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(USER_TABLE);
		db.execSQL(QUESTION_TABLE);
		db.execSQL(QUESTION_LOG_TABLE);
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if (oldVersion < 3) {
//			db.execSQL("alter table tb_user add updateTime  INTEGER ");
		}
	}

}
