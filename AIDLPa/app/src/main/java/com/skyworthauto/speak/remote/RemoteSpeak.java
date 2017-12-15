package com.skyworthauto.speak.remote;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;


public class RemoteSpeak {
	private static Object lock = new Object();
	private  String TAG ="SkySpeak.RemoteSpeak";
	private  ISpeak speakBinder;
	private static RemoteSpeak remoteSpeak;

	private RemoteSpeak(){}
	private IService svrCallback;

	public static RemoteSpeak getInstance(){
		synchronized (lock) {
			if(remoteSpeak == null){
				remoteSpeak = new RemoteSpeak();
			}
			return remoteSpeak;
		}
	}

	public void bindService(Context context,IService callback){
		if(speakBinder == null){
			svrCallback = callback;
			bindService(context);
		}
	}

	public void unBindService(Context context){
		Log.d(TAG, "unBindRemoteService ");
		context.unbindService(conn);
	}
	public void registerCmdForAM(int from, int to,ICmdAM radio){
		synchronized (lock){
			if(speakBinder == null){
				Log.d(TAG, "speakBinder is null !!");
			}else{
				try {
					speakBinder.registerCmdForAM(from,to,radio);
				} catch (RemoteException e) {
					Log.e(TAG, "registerCmdForAM error e: "+e.getMessage(),e);
					e.printStackTrace();
				}
			}
		}
	}

	public void registerCmdForFM(float from, float to,ICmdFM radio){
		synchronized (lock){
			if(speakBinder == null){
				Log.d(TAG, "speakBinder is null !!");
			}else{
				try {
					speakBinder.registerCmdForFM(from,to,radio);
				} catch (RemoteException e) {
					Log.e(TAG, "registerCmdForFM error e: "+e.getMessage(),e);
					e.printStackTrace();
				}
			}
		}
	}

	public void registerGlobalCmd(Context context,IRemote remoteCmd){
		synchronized (lock) {
			if(speakBinder == null){
				Log.d(TAG, "speakBinder is null !!");
			}else{
				try {
					IdInfo idInfo = new IdInfo(context.getPackageName()+"_"+ remoteCmd.getId());
					speakBinder.registerGlobalCmd(remoteCmd,idInfo);
				} catch (RemoteException e) {
					Log.e(TAG, "registerGlobalCmd error e: "+e.getMessage(),e);
					e.printStackTrace();
				}
			}
		}
	}

	public void unRegisterGlobalCmd(Context context,IRemote remoteCmd){
		if(speakBinder == null){
			Log.d(TAG, "speakBinder is null!!");
		}else{
			try {
				IdInfo idInfo = new IdInfo(context.getPackageName()+"_"+remoteCmd.getId());
				speakBinder.unRegisterGlobalCmd(remoteCmd,idInfo);
			} catch (RemoteException e) {
				Log.e(TAG, "unRegisterGlobalCmd error e: "+e.getMessage(),e);
				e.printStackTrace();
			}
		}
	}

	public void registerCustomCmd(Context context,IRemote remoteCmd){
		synchronized (lock) {
			if(speakBinder == null){
				Log.d(TAG, "speakBinder is null!!");
			}else{
				try {
					IdInfo idInfo = new IdInfo(context.getPackageName()+"_"+remoteCmd.getId());
					speakBinder.registerCustomCmd(remoteCmd,idInfo);
				} catch (RemoteException e) {
					Log.e(TAG, "registerCustomCmd error e: "+e.getMessage(),e);
					e.printStackTrace();
				}
			}
		}
	}

	public void unRegisterCustomCmd(Context context,IRemote remoteCmd){
		if(speakBinder == null){
			Log.d(TAG, "speakBinder is null!!");
		}else{
			try {
				IdInfo idInfo = new IdInfo(context.getPackageName()+"_"+remoteCmd.getId());
				speakBinder.unRegisterCustomCmd(remoteCmd, idInfo);
			} catch (RemoteException e) {
				Log.e(TAG, "unRegisterCustomCmd error e: "+e.getMessage(),e);
				e.printStackTrace();
			}
		}
	}

	private void bindService(Context context){
		Intent intent = new Intent();
		ComponentName component = new ComponentName("com.skyworthauto.speak","com.skyworthauto.speak.SpeakService");
		intent.setComponent(component);
		context.bindService(intent, conn, Context.BIND_AUTO_CREATE);
	}

	private ServiceConnection conn = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			speakBinder = null;
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			speakBinder =  ISpeak.Stub.asInterface(service);
			if(svrCallback != null){
				svrCallback.onServiceConnected();
			}
			/*try {
				service.linkToDeath(serverBinder,0);
			} catch (RemoteException e) {
				Log.e(TAG, "onServiceConnected error e: "+e.getMessage(),e);
				e.printStackTrace();
			}*/
		}
	};

	 /*IBinder.DeathRecipient serverBinder = new IBinder.DeathRecipient() {
		 @Override
		 public void binderDied() {
			 Log.d(TAG, "binderDied : server is killed !!");
		 }
	 };*/
}
