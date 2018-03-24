package activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import andorid.taow.selfview.R;
import util.LogUtil;

/**
 * Created by taow on 2017/6/16.
 */

public class SpeedActivity extends Activity {
    private Button btStart;
    private ImageView ivSpeedPan;
    private ImageView ivSpeedArrow;
    private TextView tvSpeedNow;
    private LinearLayout linearLayoutStart;
    private FrameLayout frameLayout;
    private RelativeLayout relativeLayoutNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speed);
        LogUtil.i("SpeedActivity.onCreate...");
        btStart = (Button) findViewById(R.id.speed_start);
        ivSpeedPan = (ImageView) findViewById(R.id.speed_pan);
        ivSpeedArrow = (ImageView) findViewById(R.id.speed_arrow);
        tvSpeedNow = (TextView) findViewById(R.id.speed_tv_now);
        linearLayoutStart = (LinearLayout)findViewById(R.id.speed_ll_start);
        frameLayout = (FrameLayout) findViewById(R.id.speed_fl);
        relativeLayoutNow = (RelativeLayout) findViewById(R.id.speed_rl_now);

        frameLayout.removeAllViews();
        frameLayout.addView(linearLayoutStart);


        btStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                LogUtil.i("SpeedActivity.onClick...");
                frameLayout.removeAllViews();
                frameLayout.addView(relativeLayoutNow);
//                frameLayout.removeViewInLayout(linearLayoutStart);
//                linearLayoutStart.setVisibility(View.INVISIBLE);
//                frameLayout.removeViewAt(0);

            }
        });
    }


}
