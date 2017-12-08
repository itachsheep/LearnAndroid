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

	private RemoteSpeak(){

	}


	public static RemoteSpeak getInstance(){
		synchronized (lock) {
			if(remoteSpeak == null){
				remoteSpeak = new RemoteSpeak();
			}
			return remoteSpeak;
		}
	}

	public void init(Context context){
		if(speakBinder == null){
			bindService(context);
		}
	}


	public void registerGlobalCmd(IRemoteCmd remoteCmd,Context context){
		synchronized (lock) {
			if(speakBinder == null){
				Log.d(TAG, "speakBinder is null, register again !!");
				bindService(context);
			}else{
				context.getPackageName();
				try {
					speakBinder.registerGlobalCmd(remoteCmd);
				} catch (RemoteException e) {
					Log.e(TAG, "RemoteException e: "+e.getMessage(),e);
					e.printStackTrace();
				}
			}
		}
	}

	private void bindService(Context context){
		Intent intent = new Intent();
		ComponentName component = new ComponentName("com.skyworthauto.speak","com.skyworthauto.speak.SpeakService");
		intent.setComponent(component);
		context.bindService(intent, conn, Context.BIND_AUTO_CREATE);
	}

	private  ServiceConnection conn = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			speakBinder = null;
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {

			speakBinder =  ISpeak.Stub.asInterface(service);
		}
	};
}
