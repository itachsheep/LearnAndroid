package andorid.taow.player;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.tv.TvContract;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.VideoView;

public class MainActivity extends Activity {
    private Button btRecycle;
    private Button btSingle;
    private EditText etUrl;
    private VideoView mVideoView;
    private ProgressBar mPb;
    private String TAG = "Player";
    private boolean isSingle = true;
    private String mUrl;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    private int mDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btRecycle = (Button) findViewById(R.id.main_bt_recycle);
        btSingle = (Button) findViewById(R.id.main_bt_single);
        etUrl = (EditText) findViewById(R.id.main_et);
        mVideoView = (VideoView) findViewById(R.id.main_vv);
        mPb = (ProgressBar) findViewById(R.id.main_pb);
        btSingle.requestFocus();
        initListener();

    }

    private Runnable pbRunnable = new Runnable() {
        @Override
        public void run() {
            int duration = mVideoView.getDuration();
            int currentPosition = mVideoView.getCurrentPosition();
            Log.i(TAG,"pbRunnable duration:"+duration+", currentPosition:"+currentPosition);
            mPb.setMax(duration);
            mPb.setProgress(currentPosition);
            mHandler.postDelayed(pbRunnable,1500);
        }
    };
    private boolean isStartMove = false;
    private void initListener() {
        btSingle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUrl = etUrl.getText().toString();
                mVideoView.setVideoPath(mUrl);
                isSingle = true;
            }
        });


        btRecycle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUrl = etUrl.getText().toString();
                mVideoView.setVideoPath(mUrl);
                isSingle = false;
            }
        });

        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mVideoView.start();
                mHandler.post(pbRunnable);
                Log.i(TAG,"setOnPreparedListener start..");
            }
        });

        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if(isSingle){
                    mHandler.removeCallbacks(pbRunnable);
                    mVideoView.stopPlayback();
                    Log.i(TAG,"setOnCompletionListener btSingle onCompletion stop ############# isSingle: "+isSingle);
                }else {
                    mVideoView.setVideoPath(mUrl);
                    mVideoView.start();
                    Log.i(TAG,"setOnCompletionListener btRecycle onCompletion start ############ isSingle: "+isSingle);
                }

            }
        });

        mPb.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_BACK){
                    MainActivity.this.finish();
                }

                if(event.getAction() == KeyEvent.ACTION_DOWN ){
                    if(keyCode == KeyEvent.KEYCODE_DPAD_LEFT){
                        mHandler.removeCallbacks(pbRunnable);

                        if(!isStartMove){
                            handleMove(false);
                            isStartMove = true;
                        }

                        return true;
                    }else if(keyCode == KeyEvent.KEYCODE_DPAD_RIGHT){
                        mHandler.removeCallbacks(pbRunnable);
                        if(!isStartMove){
                            isStartMove = true;
                            handleMove(true);
                        }

                        return true;

                    }
                }

                if(event.getAction() == KeyEvent.ACTION_UP){
                    isStartMove = false;
                    mHandler.removeCallbacks(moveAddRunnable);
                    mHandler.removeCallbacks(moveSubRunnable);

                    int pb = mPb.getProgress();
                    Log.i(TAG,"onKeyup pb:"+pb);
                    mVideoView.seekTo(pb);
                    mVideoView.start();
                    mPb.post(pbRunnable);
                    return true;
                }
                return false;
            }
        });
    }
    private Runnable moveAddRunnable = new Runnable() {
        @Override
        public void run() {
            int duration = mVideoView.getDuration();
            Log.i(TAG,"moveAddRunnable -------------------");
            int progress = (int) (mPb.getProgress()+ duration * 0.005);
            mPb.setProgress(progress);
            mHandler.postDelayed(moveAddRunnable,50);
        }
    };

    private Runnable moveSubRunnable = new Runnable() {
        @Override
        public void run() {
            int duration = mVideoView.getDuration();
            Log.i(TAG,"moveSubRunnable -------------------");
            int progress = (int) (mPb.getProgress()- duration * 0.005);
            mPb.setProgress(progress);
            mHandler.postDelayed(moveSubRunnable,50);
        }
    };
    private void handleMove(boolean isAdd) {
        if(isAdd){
            mHandler.post(moveAddRunnable);
        }else {
            mHandler.post(moveSubRunnable);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mHandler.removeCallbacks(pbRunnable);
        mVideoView.stopPlayback();
    }
}
