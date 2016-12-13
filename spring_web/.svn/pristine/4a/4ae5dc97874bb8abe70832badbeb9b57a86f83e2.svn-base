package com.jsc.service;

import java.io.File;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.Vector;

import org.junit.Test;


public class TestFile {

	public static void main(String args){
		System.out.println("hello!");
		
		TestFile tf = new TestFile();
		tf.testExt();
		
	}
	
//	@Test
	public void testExt(){
		String s = System.getProperty("java.ext.dirs");
		System.out.println(s);
		
		
		File[] dirs;
		if(s!= null){
			StringTokenizer st = new StringTokenizer(s,File.pathSeparator);
			int count = st.countTokens();
			dirs = new File[count];
			for(int i = 0; i < count; i++){
				dirs[i] = new File(st.nextToken());
			}
		}
		else{
			dirs = new File[0];
		}
		
		
		Vector urls = new Vector();
		for(int i = 0; i < dirs.length; i++){
			String[] files = dirs[i].list();
			if(files != null){
				for(int j = 0; j < files.length; j++){
					if(!files[i].equals("meta-index")){
						File f = new File(dirs[i], files[j]);
						urls.add(getFileURL(f));
					}
				}
			}
			
		}
		
		System.out.println("lib count : " + urls.size());
		
		s = System.getProperty("java.class.path");
		System.out.println(s);
	}
	
	public String getFileURL(File f){
		
		try {
			f = f.getCanonicalFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return f.getAbsolutePath();
		
	}
	
	
}
