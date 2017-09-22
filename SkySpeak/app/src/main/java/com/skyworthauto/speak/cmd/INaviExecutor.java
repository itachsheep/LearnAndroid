package com.skyworthauto.speak.cmd;


import com.txznet.sdk.bean.Poi;

interface INaviExecutor {
    void openApp();

    void closeApp();

    void minApp();

    void exitNavi();

    void showRoadCondition();

    void hideRoadCondition();

    void zoomIn();

    void zoomOut();

    void carHeadUpwards();

    void northUpwards();

    void twoDimenMode();

    void threeDimenMode();

    void enterPreview();

    void exitPreview();

    void dayMode();

    void nightMode();

    void autoMode();

    void priorityHighspeed();

    void avoidCongestion();

    void avoidHighspeed();

    void avoidCost();

    void naviWithDest(Poi poi);
}
