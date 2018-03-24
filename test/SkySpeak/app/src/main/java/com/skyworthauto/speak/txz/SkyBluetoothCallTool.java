package com.skyworthauto.speak.txz;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadsetClient;
import android.bluetooth.BluetoothHeadsetClientCall;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Contacts.Photo;
import android.text.TextUtils;

import com.skyworthauto.speak.Config;
import com.skyworthauto.speak.SpeakApp;
import com.skyworthauto.speak.util.Constant;
import com.skyworthauto.speak.util.L;
import com.skyworthauto.speak.util.ThreadPool;
import com.txznet.sdk.TXZCallManager;
import com.txznet.sdk.TXZCallManager.CallToolStatusListener;
import com.txznet.sdk.TXZCallManager.Contact;
import com.txznet.sdk.TXZConfigManager;

import java.util.ArrayList;
import java.util.List;

public class SkyBluetoothCallTool implements TXZCallManager.CallTool {
    public static final String TAG = "SkyBluetoothCallTool";

    private static final String BT_PHONE_CALLS = "com.skyworthauto.skybluetoothapp.bt.phone.calls";
    private static final String BT_PHONE_HANGUP = "com.skyworthauto.skybluetoothapp.phone.hangup";
    private static final String BT_PHONE_CALL_SUCCEED =
            "com.skyworthauto.skybluetoothapp.bt.call.succeed";

    private static final String ACTION_QUERY_BT_CONNECT_STATE =
            "com.skyworthauto.skybluetoothapp.connect.state.query";
    private static final String ACTION_ANSWER_FOR_QUERY_BT_CONNECT_STATE =
            "com.skyworthauto.skybluetoothapp.connect.state.return";
    private static final String ACTION_QUERY_BT_PHONE_CALL_STATE =
            "com.skyworthauto.skybluetoothapp.phone.state.query";
    private static final String ACTION_ANSWER_FOR_QUERY_BT_PHONE_CALL_STATE =
            "com.skyworthauto.skybluetoothapp.phone.state.return";

    private static final String ACTION_BLUETOOT_CONNECT_STATE_CHANGED =
            "com.skyworthauto.skybluetoothapp.connect.state.changed";
    private static final String EXTRA_CONNECT_STATE = "extra_connect_status";
    private static final int BLUETOOTH_DISCONNECTED = 0;
    private static final int BLUETOOTH_CONNECTED = 1;

    private static final String ACTION_CALL_STATE_CHANGED =
            "com.skyworthauto.skybluetoothapp.phone.state.changed";
    private static final String EXTRA_CALL_STATE = "extra_call_status";
    private static final String EXTRA_PHONE_NUMBER = "extra_phone_number";

    private static final int CALL_STATE_IDLE = 0;
    private static final int CALL_STATE_INCOMING = 1;
    private static final int CALL_STATE_TALKING = 2;
    private static final int CALL_STATE_OUTGOING = 3;

    private static final int MESSAGE_SYCN_CONTACT = 0;
    private static final long SYNC_DELAY = 500;

    private static final String[] PHONES_PROJECTION =
            new String[]{Phone.DISPLAY_NAME, Phone.NUMBER, Photo.PHOTO_ID, Phone.CONTACT_ID};

    private Context mContext;
    private boolean mIsSynchronizing;
    private final boolean mIsUsedInnerBT;

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_SYCN_CONTACT:
                    syncContacts();
                    break;
                default:
                    break;
            }
        }
    };

    private BroadcastReceiver mInnerBTReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            L.d(TAG, "innerBt receive:" + action);
            if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED) || action
                    .equals(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED)) {
                updateBluetoothState(isInnerBTConnected());
            } else if (action.equals(BluetoothHeadsetClient.ACTION_CALL_CHANGED)) {
                onInnerBTCallChanged(intent);
            }
        }

    };

    private BroadcastReceiver mOutBTReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            L.d(TAG, "outBt receive:" + action);
            if (action.equals(ACTION_BLUETOOT_CONNECT_STATE_CHANGED) || action
                    .equals(ACTION_ANSWER_FOR_QUERY_BT_CONNECT_STATE)) {
                updateBluetoothState(isOutBTConnected(intent));
            } else if (action.equals(ACTION_CALL_STATE_CHANGED) || action
                    .equals(ACTION_ANSWER_FOR_QUERY_BT_PHONE_CALL_STATE)) {
                onOutBTCallChanged(intent);
            }
        }

    };

    private ContentObserver mContentObserver = new ContentObserver(mHandler) {
        @Override
        public void onChange(boolean selfChange) {
            L.d(TAG, "ContentObserver:" + Constant.CONTENT_URI + ", onChange =" + selfChange);
            scheduleSyncContact();
        }
    };

    public SkyBluetoothCallTool(Context context) {
        mContext = context;
        mIsUsedInnerBT = Config.isInnerBt();

        init();
    }

    private void init() {
        registerReceiver();
        registerContentObserver(mContext);
    }

    public void exit(Context context) {
        unregisterReceiver();
        unregisterContentObserver(context);
        mHandler.removeMessages(MESSAGE_SYCN_CONTACT);
    }

    private void registerReceiver() {
        if (mIsUsedInnerBT) {
            registerInnerBTReceiver();
        } else {
            registerOuterBTReceiver();
        }
    }

    private void registerInnerBTReceiver() {
        IntentFilter filter = new IntentFilter();

        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        filter.addAction(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED);
        filter.addAction(BluetoothHeadsetClient.ACTION_CALL_CHANGED);
        mContext.registerReceiver(mInnerBTReceiver, filter);
    }

    private void registerOuterBTReceiver() {
        IntentFilter filter = new IntentFilter();

        filter.addAction(ACTION_BLUETOOT_CONNECT_STATE_CHANGED);
        filter.addAction(ACTION_ANSWER_FOR_QUERY_BT_CONNECT_STATE);
        filter.addAction(ACTION_CALL_STATE_CHANGED);
        mContext.registerReceiver(mOutBTReceiver, filter);
    }

    private void unregisterReceiver() {
        if (mIsUsedInnerBT) {
            mContext.unregisterReceiver(mInnerBTReceiver);
        } else {
            mContext.unregisterReceiver(mOutBTReceiver);
        }
    }

    public void registerContentObserver(Context context) {
        context.getContentResolver()
                .registerContentObserver(Constant.CONTENT_URI, true, mContentObserver);
    }

    public void unregisterContentObserver(Context context) {
        context.getContentResolver().unregisterContentObserver(mContentObserver);
    }

    @Override
    public boolean acceptIncoming() {
        L.d(TAG, "AndroidCallTool acceptIncoming");

        Intent intent = new Intent();
        intent.setAction(BT_PHONE_CALL_SUCCEED);
        mContext.sendBroadcast(intent);
        return true;
    }

    @Override
    public boolean hangupCall() {
        L.d(TAG, "AndroidCallTool hangupCall");
        endCall();
        return true;
    }

    @Override
    public boolean makeCall(Contact con) {
        L.d(TAG, "AndroidCallTool makeCall");
        L.d(TAG, "contact=" + con);

        if (con == null) {
            return false;
        }

        try {
            Intent intent = new Intent();
            intent.setAction(BT_PHONE_CALLS);
            intent.putExtra("bluetooth_call", con.getNumber());
            mContext.sendBroadcast(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean rejectIncoming() {
        L.d(TAG, "AndroidCallTool rejectIncoming");

        endCall();
        return true;
    }

    private void endCall() {
        Intent intent = new Intent();
        intent.setAction(BT_PHONE_HANGUP);
        mContext.sendBroadcast(intent);
    }

    private static CallStatus mLastStatus = null;
    private static Contact mLastContact = null;
    private static CallToolStatusListener mCallToolStatusListener = null;

    public static void onIncoming(Contact con) {
        L.d(TAG, "AndroidCallTool onIncoming");

        mLastContact = con;
        if (mCallToolStatusListener != null) {
            mCallToolStatusListener.onIncoming(mLastContact, true, true);
        }

        /*
         * Intent intent = new Intent("com.skyworthauto.voice.ctrol.mute.nav");
         * intent.putExtra("mute", "on"); SpeakApp.getApp().sendBroadcast(intent);
         */
    }

    public static void onMakeCall(Contact con) {
        L.d(TAG, "AndroidCallTool onMakeCall");

        mLastContact = con;
        if (mCallToolStatusListener != null) {
            mCallToolStatusListener.onMakeCall(mLastContact);
        }
    }

    public static void onIdle() {
        L.d(TAG, "AndroidCallTool onIdle");

        mLastContact = new Contact();
        if (mCallToolStatusListener != null) {
            mCallToolStatusListener.onIdle();
        }
    }

    public static void onOffhook() {
        L.d(TAG, "AndroidCallTool onOffhook mCallToolStatusListener=" + mCallToolStatusListener);

        if (mCallToolStatusListener != null) {
            mCallToolStatusListener.onOffhook();
        }
    }

    public static void onEnabled() {
        L.d(TAG, "AndroidCallTool onEnabled");
        if (mCallToolStatusListener != null) {
            mCallToolStatusListener.onEnabled();
        }
    }

    public static void onDisabled() {
        L.d(TAG, "AndroidCallTool onDisabled");
        if (mCallToolStatusListener != null) {
            mCallToolStatusListener.onDisabled("请先连接蓝牙");
        }
    }

    @Override
    public CallStatus getStatus() {
        // if (mTelephonyManager != null) {
        // switch (mTelephonyManager.getCallState()) {
        // case TelephonyManager.CALL_STATE_IDLE: {
        // if (mLastStatus != CallStatus.CALL_STATUS_IDLE) {
        // onIdle();
        // }
        // return mLastStatus = CallStatus.CALL_STATUS_IDLE;
        // }
        // case TelephonyManager.CALL_STATE_OFFHOOK:
        // if (mLastStatus != CallStatus.CALL_STATUS_OFFHOOK) {
        // onOffhook();
        // }
        // return mLastStatus = CallStatus.CALL_STATUS_OFFHOOK;
        // case TelephonyManager.CALL_STATE_RINGING:
        // if (mLastStatus != CallStatus.CALL_STATUS_RINGING) {
        // onIncoming(mLastContact);
        // }
        // return mLastStatus = CallStatus.CALL_STATUS_RINGING;
        // }
        // }
        return mLastStatus = null;
    }

    @Override
    public void setStatusListener(CallToolStatusListener listener) {
        L.d(TAG, "callTool setStatusListener");
        mCallToolStatusListener = listener;

        onDisabled();
        updateBluetoothState();
    }

    private void updateBluetoothState() {
        L.d(TAG, "updateBluetoothState");
        if (mIsUsedInnerBT) {
            updateBluetoothState(isInnerBTConnected());
        } else {
            mContext.sendBroadcast(new Intent(ACTION_QUERY_BT_CONNECT_STATE));
        }
    }

    private final void updateBluetoothState(boolean isBluetoothConnected) {
        L.d(TAG, "mIsBluetoothConnected==" + isBluetoothConnected);
        if (isBluetoothConnected) {
            onEnabled();
            scheduleSyncContact();
        } else {
            onDisabled();
        }
    }

    private boolean isInnerBTConnected() {
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        if (adapter != null) {
            //            if (adapter.getConnectionState() == BluetoothAdapter.STATE_CONNECTED) {
            //                return true;
            //            }
        }
        return false;
    }

    private void onInnerBTCallChanged(Intent intent) {
        BluetoothDevice device =
                (BluetoothDevice) intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
        BluetoothHeadsetClientCall call = (BluetoothHeadsetClientCall) intent
                .getParcelableExtra(BluetoothHeadsetClient.EXTRA_CALL);

        if (call == null) {
            return;
        }

        int state = call.getState();
        String number = call.getNumber();
        L.d(TAG, "onCallChanged: " + " state " + state + " number " + number);
        switch (state) {
            case BluetoothHeadsetClientCall.CALL_STATE_TERMINATED:
                onIdle();
                break;
            case BluetoothHeadsetClientCall.CALL_STATE_INCOMING:
                onCallComing(number);
                break;
            case BluetoothHeadsetClientCall.CALL_STATE_ACTIVE:
                onOffhook();
                break;
            case BluetoothHeadsetClientCall.CALL_STATE_DIALING:
                break;
            case BluetoothHeadsetClientCall.CALL_STATE_ALERTING:
            case BluetoothHeadsetClientCall.CALL_STATE_WAITING:
                break;
            default:
                break;
        }
    }

    private boolean isOutBTConnected(Intent intent) {
        return BLUETOOTH_CONNECTED == intent
                .getIntExtra(EXTRA_CONNECT_STATE, BLUETOOTH_DISCONNECTED);
    }

    private void onOutBTCallChanged(Intent intent) {
        int state = intent.getIntExtra(EXTRA_CALL_STATE, CALL_STATE_IDLE);
        String number = intent.getStringExtra(EXTRA_PHONE_NUMBER);
        L.d(TAG, "onCallChanged: " + " state=" + state + ",number=" + number);
        switch (state) {
            case CALL_STATE_IDLE:
                onIdle();
                break;
            case CALL_STATE_INCOMING:
                onCallComing(number);
                break;
            case CALL_STATE_TALKING:
                onOffhook();
                break;
            default:
                break;
        }
    }

    private void onCallComing(String num) {
        if (TextUtils.isEmpty(num)) {
            return;
        }
        num = num.trim();

        L.d(TAG, "onCallComing num=" + num);
        Contact contact = new Contact();
        contact.setNumber(num);
        onIncoming(contact);
    }

    private void scheduleSyncContact() {
        mHandler.removeMessages(MESSAGE_SYCN_CONTACT);
        mHandler.sendEmptyMessageDelayed(MESSAGE_SYCN_CONTACT, SYNC_DELAY);
    }

    public void syncContacts() {
        if (!TXZConfigManager.getInstance().isInitedSuccess()) {
            return;
        }

        if (isSynchronizing()) {
            scheduleSyncContact();
            return;
        }

        L.d(TAG, "syncContacts begin");
        ThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                setIsSynchronizing(true);
                TXZCallManager.getInstance().syncContacts(getContactsData());
                setIsSynchronizing(false);
            }
        });
    }

    private List<Contact> getContactsData() {
        List<Contact> contactList = new ArrayList<Contact>();
        ContentResolver resolver = SpeakApp.getApp().getContentResolver();
        Cursor cursor = resolver.query(Constant.CONTENT_URI, PHONES_PROJECTION,
                /*ContactsContract.RawContacts.ACCOUNT_NAME + " = 'PHONE'"*/null, null, null);

        if (cursor == null) {
            L.w(TAG, "cannot open uir" + Constant.CONTENT_URI);
            return contactList;
        }

        String contactName;
        String phoneNumber;
        String lookfornumber;
        try {
            while (cursor.moveToNext()) {
                contactName = cursor.getString(0);
                phoneNumber = cursor.getString(1);
                if (TextUtils.isEmpty(phoneNumber)) {
                    lookfornumber = null;
                } else {
                    if (phoneNumber.contains("+86")) {
                        lookfornumber = phoneNumber.replace("+86", "").replaceAll("[^\\d]", "");
                    } else {
                        lookfornumber = phoneNumber.replaceAll("[^\\d]", "");
                    }
                }

                L.d(TAG, "contactName=" + contactName + ",lookfornumber=" + lookfornumber);
                Contact contact = new Contact();
                contact.setName(contactName);
                contact.setNumber(lookfornumber);
                contactList.add(contact);
            }
        } finally {
            cursor.close();
        }

        L.d(TAG, "contactlist=" + contactList.toArray());

        return contactList;
    }

    private synchronized boolean isSynchronizing() {
        return mIsSynchronizing;
    }

    private synchronized void setIsSynchronizing(boolean isSynchronizing) {
        mIsSynchronizing = isSynchronizing;
    }

}
