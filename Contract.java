package com.demo;

import android.provider.BaseColumns;
import java.net.URI;
//uri,table,columns
public class Contract{
	public static final class tab_Stud{
		public static final String name="student";
		public static final class col implements BaseColumns{
			//write all column info for table here
			public static final String name="name";
			public static final String marks="marks";
		}
		public static final class uris{
			//write all Uri here
		} 	
	}
	
}