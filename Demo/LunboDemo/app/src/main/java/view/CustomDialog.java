package view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Window;

import com.tao.lunbodemo.R;

/**
 * Created by SDT14324 on 2018/4/10.
 */

public class CustomDialog extends Dialog {
    public CustomDialog(@NonNull Context context) {
        this(context,0);
    }

    public CustomDialog(@NonNull Context context, int themeResId) {
//        super(context, R.style.CustomDialog);
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog);
        setCanceledOnTouchOutside(false);
        Window window = getWindow();
        window.setBackgroundDrawable(null);
    }
}
