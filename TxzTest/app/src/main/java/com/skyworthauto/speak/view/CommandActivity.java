package com.skyworthauto.speak.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.skyworthauto.speak.R;
import com.skyworthauto.speak.util.LogUtils;
import com.txznet.sdk.TXZAsrManager;
import com.txznet.sdk.TXZResourceManager;

/**
 * Created by SDT14324 on 2017/11/22.
 */

public class CommandActivity extends AppCompatActivity {
    private String tag = CommandActivity.class.getSimpleName();
    String[] arrOpenAirconCmd = new String[] { "打开空调", "开启空调" };
    String[] arrCloseAirconCmd = new String[] { "关闭空调", "关掉空调" };

    String[] openScreenCmd = new String[]{"打开屏幕", "开启屏幕"};
    String[] closeScreenCmd = new String[]{"关闭屏幕", "熄灭屏幕"};

    TXZAsrManager.CommandListener mCommandListener = new TXZAsrManager.CommandListener() {
        @Override
        public void onCommand(String cmd, String data) {
            LogUtils.i(tag,"onCommand  cmd: "+cmd+", data: "+data);
            if ("OPEN_AIRCON".equals(data)) {
                TXZResourceManager.getInstance().speakTextOnRecordWin("好好学习，天天向上，将为您打开空调 ", true, new Runnable() {
                    @Override
                    public void run() {
                        // TODO: 2017/11/22  打开空调
                        LogUtils.i(tag," open the arr!!");

                    }
                });
            }


            if ("CLOSE_AIRCON".equals(data)) {
                TXZResourceManager.getInstance().speakTextOnRecordWin("好好学习，天天向上，将为您关闭空调", true, new Runnable() {
                    @Override
                    public void run() {
                        // TODO: 2017/11/22  关闭空调
                        LogUtils.i(tag," close the arr!!");

                    }
                });
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_command);

        findViewById(R.id.bt_reg_command).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                LogUtils.i(tag," regCommand OPEN_AIRCON command!! ");
                TXZAsrManager.getInstance().addCommandListener(mCommandListener);
                TXZAsrManager.getInstance().regCommand(arrOpenAirconCmd, "OPEN_AIRCON");
                TXZAsrManager.getInstance().regCommand(arrCloseAirconCmd,"CLOSE_AIRCON");
                Toast.makeText(CommandActivity.this,"注册指令成功！",Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.bt_wakeup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.i(tag," regCommand global OPEN_SCREEN command!! ");
                mAsrCallback.addCommand("OPEN_SCREEN",openScreenCmd);
                mAsrCallback.addCommand("CLOSE_SCREEN",closeScreenCmd);
                TXZAsrManager.getInstance().useWakeupAsAsr(mAsrCallback);
            }
        });
    }

    public void unRegGlobalCmd(View view){
        LogUtils.i(tag," unRegGlobalCmd global OPEN_SCREEN command!! ");
        TXZAsrManager.getInstance().recoverWakeupFromAsr("WAKEUP_TASK111");
    }
    TXZAsrManager.AsrComplexSelectCallback mAsrCallback = new TXZAsrManager.AsrComplexSelectCallback() {
        @Override
        public String getTaskId() {
            // TODO 返回任务ID，可以取消唤醒识别任务
            return "WAKEUP_TASK";
        }

        @Override
        public boolean needAsrState() {
            // TODO 是否需要识别状态，识别会对系统静音
            return false;
        }

        @Override
        public void onCommandSelected(String type, String command) {
            LogUtils.i(tag,"onCommandSelected  type: "+type+", command: "+command);
            if("OPEN_SCREEN".equals(type)){
                LogUtils.i(tag,"global onCommandSelected type: "+type+", command: "+command);

                return;
            }

            if("CLOSE_SCREEN".equals(type)){
                LogUtils.i(tag,"global onCommandSelected type: "+type+", command: "+command);

                return;
            }
        }
    };
}
