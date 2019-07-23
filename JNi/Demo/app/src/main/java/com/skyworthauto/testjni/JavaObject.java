package com.skyworthauto.testjni;

/**
 * Created by Administrator on 2018/6/6.
 */

public class JavaObject {

	public int mNum = 10;
	public static int sNum = 1000;
	
	public JavaObject(){
	
	}
	
	public JavaObject(int num){
		this.mNum = num;
	}
	
	public int method(){
		return 20;
	}
	
	public static int methodStatic(){
		return 2000;
	}
	
	@Override
	public String toString() {
		return "[JavaObject] mNum = " + mNum + " sNum = " + sNum;
	}
}
