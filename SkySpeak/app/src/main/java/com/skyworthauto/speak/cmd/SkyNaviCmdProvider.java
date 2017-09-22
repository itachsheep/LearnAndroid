package com.skyworthauto.speak.cmd;

import android.content.Context;

import com.skyworthauto.speak.R;
import com.skyworthauto.speak.SpeakApp;

import java.util.ArrayList;
import java.util.Collection;

public class SkyNaviCmdProvider implements ICmdProvider {

    @Override
    public int getId() {
        return CmdProviderFactory.ID_CONTROL_SKY_NAVI;
    }

    @Override
    public Collection<? extends CmdSpeakable> getList() {
        return ceateSkyNaviCmds(SpeakApp.getApp());
    }

    private static Collection<? extends CmdSpeakable> ceateSkyNaviCmds(Context context) {
        ArrayList<CmdSpeakable> cmdList = new ArrayList<CmdSpeakable>();
        SkyNaviExecutor naviExecutor = new SkyNaviExecutor();

        cmdList.add(new SkyNaviCmd(context, R.array.nav_not_run_expressway_array,
                R.string.nav_not_run_expressway_id, R.string.nav_not_run_expressway_speak,
                naviExecutor) {

            @Override
            public void run() {
                getNaviExecutor().avoidHighspeed();
            }

        });

        cmdList.add(new SkyNaviCmd(context, R.array.nav_escape_crush_array,
                R.string.nav_escape_crush_id, R.string.nav_escape_crush_speak, naviExecutor) {

            @Override
            public void run() {
                getNaviExecutor().avoidCongestion();
            }

        });

        cmdList.add(new SkyNaviCmd(context, R.array.nav_priority_highspeed_array,
                R.string.nav_priority_highspeed_id, R.string.nav_priority_highspeed_speak,
                naviExecutor) {

            @Override
            public void run() {
                getNaviExecutor().priorityHighspeed();
            }

        });

        cmdList.add(
                new SkyNaviCmd(context, R.array.nav_charge_less_array, R.string.nav_charge_less_id,
                        R.string.nav_charge_less_speak, naviExecutor) {

                    @Override
                    public void run() {
                        getNaviExecutor().avoidCost();
                    }

                });
        cmdList.add(new SkyNaviCmd(context, R.array.nav_check_whole_journey_array,
                R.string.nav_check_whole_journey_id, R.string.nav_check_whole_journey_speak,
                naviExecutor) {

            @Override
            public void run() {
                getNaviExecutor().enterPreview();
            }

        });

        cmdList.add(new SkyNaviCmd(context, R.array.nav_zoom_in_array, R.string.nav_zoom_in_id,
                R.string.nav_zoom_in_speak, naviExecutor) {

            @Override
            public void run() {
                getNaviExecutor().zoomIn();
            }

        });
        cmdList.add(new SkyNaviCmd(context, R.array.nav_zoom_out_array, R.string.nav_zoom_out_id,
                R.string.nav_zoom_out_speak, naviExecutor) {

            @Override
            public void run() {
                getNaviExecutor().zoomOut();
            }

        });
        cmdList.add(new SkyNaviCmd(context, R.array.nav_open_road_condition_array,
                R.string.nav_open_road_condition_id, R.string.nav_open_road_condition_speak,
                naviExecutor) {

            @Override
            public void run() {
                getNaviExecutor().showRoadCondition();
            }

        });
        cmdList.add(new SkyNaviCmd(context, R.array.nav_close_road_condition_array,
                R.string.nav_close_road_condition_id, R.string.nav_close_road_condition_speak,
                naviExecutor) {

            @Override
            public void run() {
                getNaviExecutor().hideRoadCondition();
            }

        });
        cmdList.add(new SkyNaviCmd(context, R.array.nav_3d_mode_array, R.string.nav_3d_mode_id,
                R.string.nav_3d_mode_speak, naviExecutor) {

            @Override
            public void run() {
                getNaviExecutor().threeDimenMode();
            }

        });
        cmdList.add(new SkyNaviCmd(context, R.array.nav_2d_mode_array, R.string.nav_2d_mode_id,
                R.string.nav_2d_mode_speak, naviExecutor) {

            @Override
            public void run() {
                getNaviExecutor().twoDimenMode();
            }

        });

        cmdList.add(new SkyNaviCmd(context, R.array.nav_northward_array, R.string.nav_northward_id,
                R.string.nav_northward_speak, naviExecutor) {

            @Override
            public void run() {
                getNaviExecutor().northUpwards();
            }

        });
        cmdList.add(new SkyNaviCmd(context, R.array.nav_car_head_upwards_array,
                R.string.nav_car_head_upwards_id, R.string.nav_car_head_upwards_speak,
                naviExecutor) {

            @Override
            public void run() {
                getNaviExecutor().carHeadUpwards();
            }

        });
        cmdList.add(new SkyNaviCmd(context, R.array.nav_car_light_mode_array,
                R.string.nav_car_light_mode_id, R.string.nav_car_light_mode_speak, naviExecutor) {

            @Override
            public void run() {
                getNaviExecutor().dayMode();
            }

        });
        cmdList.add(new SkyNaviCmd(context, R.array.nav_car_night_mode_array,
                R.string.nav_car_night_mode_id, R.string.nav_car_night_mode_speak, naviExecutor) {

            @Override
            public void run() {
                getNaviExecutor().nightMode();
            }

        });

        cmdList.add(new SkyNaviCmd(context, R.array.nav_auto_mode_array, R.string.nav_auto_mode_id,
                R.string.nav_auto_mode_speak, naviExecutor) {

            @Override
            public void run() {
                getNaviExecutor().autoMode();
            }

        });
        return cmdList;
    }

    @Override
    public ICommand getCommand(CmdSpeakable cmdData) {
        return new CustomCommand(cmdData);
    }

}
