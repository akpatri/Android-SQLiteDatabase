package com.demo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	String roll,name,marks;
	EditText er,en,em;
	TextView tv;
	dbHelper dh;
	SQLiteDatabase db;
	public static class student{
		public static final String tab_name=Contract.tab_Stud.name;
		public static final String roll=Contract.tab_Stud.col._ID;
		public static final String name=Contract.tab_Stud.col.name;
		public static final String marks=Contract.tab_Stud.col.marks;
	}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		
		er=findViewById(R.id.roll);
		en=findViewById(R.id.name);
		em=findViewById(R.id.marks);
		tv=findViewById(R.id.txt);
		
		
    }
	public String create(){
		String s=String.format("create table if not exists %s (%s integer primary key not null,%s varchar ,%s integer )",student.tab_name,student.roll,student.name,student.marks);
		tv.setText(s);
		return s;
	}
	public String delete(){
		String s=String.format("drop table %s",student.tab_name);
		tv.setText("Table Deleted");
		return  s;
	}
	@Override
	protected void onResume() {
		super.onResume();
		dh=new dbHelper(this);
		
		try{
			db=openOrCreateDatabase("database",MODE_PRIVATE,null);
			if(db.isOpen()){
				tv.setText("DataBase opened: Do Write or read operation");
			}
			else{
				tv.setText("Cant open: Exception occurs");
			}
		}
		catch (Exception e){
			Toast.makeText(this,"Opening Exception",Toast.LENGTH_SHORT).show();
		}
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		db.close();
	}
	public void insert(View v){
		db.execSQL(create());
		roll=er.getText().toString();
		name=en.getText().toString();
		marks=em.getText().toString();
		if(roll==null){
			Toast.makeText(this,"This should not be Empty",Toast.LENGTH_SHORT).show();
			return;
		}
		try{
			ContentValues ci=new ContentValues();
			ci.put(Contract.tab_Stud.col._ID,roll);
			ci.put(Contract.tab_Stud.col.name,name);
			ci.put(Contract.tab_Stud.col.marks,marks);
			int i=(int)db.insert(Contract.tab_Stud.name,null,ci);
			tv.setText(i+" row inserted");	
		}
		catch (Exception e){
			Toast.makeText(this,"Exception in Insert",Toast.LENGTH_SHORT).show();
		}
	}
	public void delete(View v){
		roll=er.getText().toString();
		name=en.getText().toString();
		marks=em.getText().toString();
		if(roll==null){
			Toast.makeText(this,"This should not be Empty",Toast.LENGTH_SHORT).show();
			return;
		}

		try{
			String where=Contract.tab_Stud.col._ID+"=?";
			String[] arg={roll};
			int i=db.delete(Contract.tab_Stud.name,where,arg);
			tv.setText(i+" row affected");
		}
		catch(Exception e){
			Toast.makeText(this,"Exception in Delete",Toast.LENGTH_SHORT).show();	
		}
	}
	public void update(View v){
		roll=er.getText().toString();
		name=en.getText().toString();
		marks=em.getText().toString();
		if(roll==null){
			Toast.makeText(this,"This should not be Empty",Toast.LENGTH_SHORT).show();
			return;
		}

		String where=Contract.tab_Stud.col._ID+"=?";
		String[] arg={roll};
		try{
			ContentValues ci=new ContentValues();
			ci.put(Contract.tab_Stud.col._ID,roll);
			ci.put(Contract.tab_Stud.col.name,name);
			ci.put(Contract.tab_Stud.col.marks,marks);
			int i=db.update(Contract.tab_Stud.name,ci,where,arg);
			tv.setText(i+" row updated");
		}
		catch (Exception e){
			Toast.makeText(this,"Exception in update",Toast.LENGTH_SHORT).show();	
		}
	}
	public void retrive(View v){
		StringBuffer bf=new StringBuffer();
		try{
			Cursor c=db.query(Contract.tab_Stud.name,null,null,null,null,null,null);
			while(c.moveToNext()){
				bf.append("Roll :"+c.getString(0)+"\n");
				bf.append("Name :"+c.getString(1)+"\n");
				bf.append("Marks :"+c.getString(2)+"\n\n");
			}
			AlertDialog.Builder ab=new AlertDialog.Builder(this);
			ab.setMessage(bf.toString());
			ab.setCancelable(true);
			ab.setTitle("Your Data");
			ab.show();	
		}
		catch (Exception e){
			Toast.makeText(this,"Exception in retrive",Toast.LENGTH_SHORT).show();	
		}		
	}
	public void deleteTable(View v){
		db.execSQL(delete());
	}
}