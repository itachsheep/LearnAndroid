package com.tao.statemachinedemo;

import android.os.Message;
import android.util.Log;

/**
 * Created by SDT14324 on 2017/12/1.
 */

public class Hsm1 extends StateMachine {

    private static final String TAG = "MainActivity";

    public static final int CMD_1 = 1;
    public static final int CMD_2 = 2;
    public static final int CMD_3 = 3;
    public static final int CMD_4 = 4;
    public static final int CMD_5 = 5;

    public static Hsm1 makeHsm1() {
        Log.d(TAG, "makeHsm1() ");
        Hsm1 sm = new Hsm1("hsm1");
        sm.start();

        return sm;
    }

    Hsm1(String name) {
        super(name);
        Log.d(TAG, "Hsm1()");

        // Add states, use indentation to show hierarchy
        addState(mP1);
        addState(mS1, mP1);
        addState(mS2, mP1);
        addState(mP2);

        // Set the initial state
        setInitialState(mS1);

    }

    class P1 extends State {
        @Override public void enter() {
            Log.d(TAG, "mP1.enter");
        }
        @Override public boolean processMessage(Message message) {
            boolean retVal;
            Log.d(TAG, "mP1.processMessage what=" + message.what);
            switch(message.what) {
                case CMD_1:
                    Log.d(TAG, "mP1.processMessage CMD_1");
                    retVal = HANDLED;
                    break;
                case CMD_2:
                    // CMD_2 will arrive in mS2 before CMD_3
                    sendMessage(obtainMessage(CMD_3));
                    deferMessage(message);
                    transitionTo(mS2);
                    retVal = HANDLED;
                    break;
                default:
                    // Any message we don't understand in this state invokes unhandledMessage
                    retVal = NOT_HANDLED;
                    break;
            }
            return retVal;
        }
        @Override public void exit() {
            Log.d(TAG, "mP1.exit");
        }
    }

    class S1 extends State {
        @Override public void enter() {
            Log.d(TAG, "mS1.enter");
        }
        @Override public boolean processMessage(Message message) {
            Log.d(TAG, "S1.processMessage what=" + message.what);
            if (message.what == CMD_1) {
                // Transition to ourself to show that enter/exit is called
                deferMessage(message);
                transitionTo(mS2);
                Log.d(TAG, "S1.processMessage CMD_1 !!" );
                return true;
            } else {
                // Let parent process all other messages
                return NOT_HANDLED;
            }
        }
        @Override public void exit() {
            Log.d(TAG, "mS1.exit");
        }
    }

    class S2 extends State {
        @Override public void enter() {
            Log.d(TAG, "mS2.enter");
        }
        @Override public boolean processMessage(Message message) {
            boolean retVal;
            Log.d(TAG, "mS2.processMessage what=" + message.what);
            switch(message.what) {
                case(CMD_2):
                    sendMessage(obtainMessage(CMD_4));
                    retVal = HANDLED;
                    break;
                case(CMD_3):
                    deferMessage(message);
                    transitionTo(mP2);
                    retVal = HANDLED;
                    break;
                default:
                    retVal = NOT_HANDLED;
                    break;
            }
            return retVal;
        }
        @Override public void exit() {
            Log.d(TAG, "mS2.exit");
        }
    }

    class P2 extends State {
        @Override public void enter() {
            Log.d(TAG, "mP2.enter");
            //sendMessage(obtainMessage(CMD_5));
        }
        @Override public boolean processMessage(Message message) {
            Log.d(TAG, "P2.processMessage what=" + message.what);
            switch(message.what) {
                case(CMD_3):
                    break;
                case(CMD_4):
                    break;
                case(CMD_5):
                    transitionToHaltingState();
                    break;
            }
            return HANDLED;
        }
        @Override public void exit() {
            Log.d(TAG, "mP2.exit");
        }
    }

    @Override
    public void onHalting() {
        Log.d(TAG, "halting");
        synchronized (this) {
            this.notifyAll();
        }
    }

    P1 mP1 = new P1();
    S1 mS1 = new S1();
    S2 mS2 = new S2();
    P2 mP2 = new P2();
}
