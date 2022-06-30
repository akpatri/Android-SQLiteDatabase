package com.demo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import android.database.sqlite.SQLiteOpenHelper;

public class dbHelper extends SQLiteOpenHelper{
	public static class student{
		public static final String tab_name=Contract.tab_Stud.name;
		public static final String roll=Contract.tab_Stud.col._ID;
		public static final String name=Contract.tab_Stud.col.name;
		public static final String marks=Contract.tab_Stud.col.marks;
	}
	public static class SqlCommands{
		public final static String create_table=String.format("create table if not exists %s (%s integer 3,%s varchar 24,%s integer 3)",student.tab_name,student.roll,student.name,student.marks);
	}
	public dbHelper(Context c){
		super(c,"database",null,1);
		getWritableDatabase();
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SqlCommands.create_table);
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
	
}