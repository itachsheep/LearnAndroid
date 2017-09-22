package com.skyworthauto.speak.cmd;

import android.content.Intent;

import com.skyworthauto.speak.SpeakApp;
import com.skyworthauto.speak.util.SkyNaviConstant;
import com.txznet.sdk.bean.Poi;

public class SkyNaviExecutor implements INaviExecutor {

    @Override
    public void openApp() {
        SpeakApp.getApp().sendBroadcast(createIntent(SkyNaviConstant.KEY_TYPE_OPEN_APP));
    }

    @Override
    public void closeApp() {
        SpeakApp.getApp().sendBroadcast(createIntent(SkyNaviConstant.KEY_TYPE_CLOSE_APP));
    }

    @Override
    public void minApp() {
        SpeakApp.getApp().sendBroadcast(createIntent(SkyNaviConstant.KEY_TYPE_MIN_APP));
    }

    @Override
    public void exitNavi() {
        SpeakApp.getApp().sendBroadcast(createIntent(SkyNaviConstant.KEY_TYPE_EXIT_NAVI));
    }

    @Override
    public void showRoadCondition() {
        SpeakApp.getApp().sendBroadcast(createCtrlIntent(SkyNaviConstant.EXTRA_TYPE_ROAD_CONDITION,
                SkyNaviConstant.OPEN_ROAD_CONDITION));
    }

    @Override
    public void hideRoadCondition() {
        SpeakApp.getApp().sendBroadcast(createCtrlIntent(SkyNaviConstant.EXTRA_TYPE_ROAD_CONDITION,
                SkyNaviConstant.CLOSE_ROAD_CONDITION));
    }

    @Override
    public void zoomIn() {
        SpeakApp.getApp().sendBroadcast(
                createCtrlIntent(SkyNaviConstant.EXTRA_TYPE_ZOOM, SkyNaviConstant.ZOOM_IN));
    }

    @Override
    public void zoomOut() {
        SpeakApp.getApp().sendBroadcast(
                createCtrlIntent(SkyNaviConstant.EXTRA_TYPE_ZOOM, SkyNaviConstant.ZOOM_OUT));
    }

    @Override
    public void carHeadUpwards() {
        SpeakApp.getApp().sendBroadcast(createCtrlIntent(SkyNaviConstant.EXTRA_TYPE_VISUAL,
                SkyNaviConstant.CAR_HEAD_UPWARDS));
    }

    @Override
    public void northUpwards() {
        SpeakApp.getApp().sendBroadcast(
                createCtrlIntent(SkyNaviConstant.EXTRA_TYPE_VISUAL, SkyNaviConstant.NORTH_UPWARDS));
    }

    @Override
    public void twoDimenMode() {
        SpeakApp.getApp().sendBroadcast(
                createCtrlIntent(SkyNaviConstant.EXTRA_TYPE_VISUAL, SkyNaviConstant.NORTH_UPWARDS));
    }

    @Override
    public void threeDimenMode() {
        SpeakApp.getApp().sendBroadcast(createCtrlIntent(SkyNaviConstant.EXTRA_TYPE_VISUAL,
                SkyNaviConstant.THREE_DIMEN_MODE));
    }

    @Override
    public void enterPreview() {
        SpeakApp.getApp().sendBroadcast(createPreviewIntent(SkyNaviConstant.ENTER_PREVIEW));
    }

    @Override
    public void exitPreview() {
        SpeakApp.getApp().sendBroadcast(createPreviewIntent(SkyNaviConstant.EXIT_PREVIEW));
    }

    @Override
    public void dayMode() {
        SpeakApp.getApp().sendBroadcast(createDayNightIntent(SkyNaviConstant.DAY_MODE));
    }

    @Override
    public void nightMode() {
        SpeakApp.getApp().sendBroadcast(createDayNightIntent(SkyNaviConstant.NIGHT_MODE));
    }

    @Override
    public void autoMode() {
        SpeakApp.getApp().sendBroadcast(createDayNightIntent(SkyNaviConstant.AUTO_MODE));
    }

    @Override
    public void priorityHighspeed() {
        SpeakApp.getApp()
                .sendBroadcast(createRoutePreferIntent(SkyNaviConstant.PRIORITY_HIGHSPEED));
    }

    @Override
    public void avoidCongestion() {
        SpeakApp.getApp().sendBroadcast(createRoutePreferIntent(SkyNaviConstant.AVOID_CONGESTION));
    }

    @Override
    public void avoidHighspeed() {
        SpeakApp.getApp().sendBroadcast(createRoutePreferIntent(SkyNaviConstant.AVOID_HIGHSPEED));
    }

    @Override
    public void avoidCost() {
        SpeakApp.getApp().sendBroadcast(createRoutePreferIntent(SkyNaviConstant.AVOID_COST));
    }

    @Override
    public void naviWithDest(Poi poi) {
        Intent intent = createIntent(SkyNaviConstant.KEY_TYPE_NAVI_WITH_DEST);
        intent.putExtra(SkyNaviConstant.POINAME, poi.getName());
        intent.putExtra(SkyNaviConstant.LAT, poi.getLat());
        intent.putExtra(SkyNaviConstant.LON, poi.getLng());
        SpeakApp.getApp().sendBroadcast(intent);
    }

    private Intent createCtrlIntent(int extraType, int extraOpera) {
        Intent intent = createIntent(SkyNaviConstant.KEY_TYPE_CTRL_MAP);
        intent.putExtra(SkyNaviConstant.EXTRA_TYPE, extraType);
        intent.putExtra(SkyNaviConstant.EXTRA_OPERA, extraOpera);
        return intent;
    }

    private Intent createPreviewIntent(int type) {
        Intent intent = createIntent(SkyNaviConstant.KEY_TYPE_SHOW_PREVIEW);
        intent.putExtra(SkyNaviConstant.EXTRA_IS_SHOW_PREVIEW, type);
        return intent;
    }

    private Intent createDayNightIntent(int type) {
        Intent intent = createIntent(SkyNaviConstant.KEY_TYPE_SHOW_PREVIEW);
        intent.putExtra(SkyNaviConstant.EXTRA_DAY_NIGHT_MODE, type);
        return intent;
    }

    private Intent createRoutePreferIntent(int type) {
        Intent intent = createIntent(SkyNaviConstant.KEY_TYPE_NAVI_ROUTE_PREFER);
        intent.putExtra(SkyNaviConstant.EXTRA_NAVI_ROUTE_PREFER, type);
        return intent;
    }

    private Intent createIntent(int type) {
        Intent intent = new Intent(SkyNaviConstant.ACTION_SKY_NAVI_BROADCAST_RECV);
        intent.putExtra(SkyNaviConstant.KEY_TYPE, type);
        return intent;
    }

}
