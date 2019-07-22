package hybird1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.tao.wei.hybirdflutter.R;


public class Hybird1NativeActivity extends AppCompatActivity implements View.OnClickListener {
    TextView mStartFlutter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hybird1);

        mStartFlutter = findViewById(R.id.start_flutter);

        mStartFlutter.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == mStartFlutter) {
            Intent intent = new Intent(this, Hybird1FlutterPageActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("routeName", "first");
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }
}
