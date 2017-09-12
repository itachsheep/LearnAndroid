package skyworth.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.skyworth.navi.R;

/**
 * Created by SDT14324 on 2017/9/12.
 */

public class SkyworthActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skyworth);
    }
}
