package com.tao.adjustview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.tao.adjustview.view.SkyVerticalMarqueeTextview;

public class ScollTextActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SkyVerticalMarqueeTextview versionMarquee = (SkyVerticalMarqueeTextview) findViewById(R.id.test_marquee);
        versionMarquee.setPadding(8, 6, 8, 6);
        versionMarquee.setLineSpacing(28, 1.0f);
        versionMarquee.setText(
                "1，个赛季就已经定形，大家都知道他的投篮能力非常差，面对他基本上都是放他这一\n" +
                "2，个赛季就已经定形，大家都知道他的投篮能力非常差，面对他基本上都是放他这一\n" +
                "3，个赛季就已经定形，大家都知道他的投篮能力非常差，面对他基本上都是放他这一\n" +
                "4，个赛季就已经定形，大家都知道他的投篮能力非常差，面对他基本上都是放他这一\n" +
                "5，个赛季就已经定形，大家都知道他的投篮能力非常差，面对他基本上都是放他这一\n" +
                "6，个赛季就已经定形，大家都知道他的投篮能力非常差，面对他基本上都是放他这一\n" +
                "7，个赛季就已经定形，大家都知道他的投篮能力非常差，面对他基本上都是放他这一\n" +
                "8，个赛季就已经定形，大家都知道他的投篮能力非常差，面对他基本上都是放他这一\n" +
                "9，个赛季就已经定形，大家都知道他的投篮能力非常差，面对他基本上都是放他这一\n" +
                "10，个赛季就已经定形，大家都知道他的投篮能力非常差，面对他基本上都是放他这\n" +
                "11，个赛季就已经定形，大家都知道他的投篮能力非常差，面对他基本上都是放他这\n" +
                "12，个赛季就已经定形，大家都知道他的投篮能力非常差，面对他基本上都是放他这");

        /*versionMarquee.setDBCText(
                "1，这是测试智能汽车升级\n" +
                        "2，请不要删除\n" +
                        "3,其实对于一个射手来说，出手投篮的那一刻自信心非常重要。" +
                        "然而隆多在进入NBA的第一个赛季就已经定形，大家都知道他的投篮能力非常差，" +
                        "面对他基本上都是放他这一点。虽然偶尔会投进一两个球，都只是一个意外。" +
                        "而随着联盟球队都采取了这样的战术，这也导致了隆多越来越没信心。");*/
        /*versionMarquee.setDBCText(
                "1，这是测试智能汽车升级\n" +
                        "2，请不要删除\n" +
                        "3，其实对于一个射手来说，出手投篮的那一刻自信心非常重要。然而隆多在进入NBA的第一个赛季就已经定形，大家都知道他的投篮能力非常差，面对他基本上都是放他这一点。虽然偶尔会投进一两个球，都只是一个意外。而随着联盟球队都采取了这样的战术，这也导致了隆多越来越没信心。\n" +
                        "4，其实对于一个射手来说，出手投篮的那一刻自信心非常重要。然而隆多在进入NBA的第一个赛季就已经定形，大家都知道他的投篮能力非常差，面对他基本上都是放他这一点。虽然偶尔会投进一两个球，都只是一个意外。" +
                        "而随着联盟球队都采取了这样的战术，这也导致了隆多越来越没信心。");*/


    }
}
