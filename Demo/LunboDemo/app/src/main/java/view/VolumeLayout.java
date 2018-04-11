package view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tao.lunbodemo.R;

/**
 * Created by SDT14324 on 2018/4/11.
 */

public class VolumeLayout extends LinearLayout {
    private VolumeView volumeView;
    private TextView tvValue;
    private TextView tvType;
    private ImageView imageView;
    public VolumeLayout(Context context) {
        this(context, null);
    }

    public VolumeLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public VolumeLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = LayoutInflater.from(context).inflate(R.layout.view_volume_layout, this);
        volumeView = view.findViewById(R.id.vol_progess);
        tvValue = view.findViewById(R.id.vol_value);
        tvType = view.findViewById(R.id.vol_type);
        imageView = view.findViewById(R.id.vol_img);
        init();
    }

    private void init() {
        tvValue.setText(volumeView.getProgress()+"");
    }

    public void setVolume(int volume){
        volumeView.setProgress(volume);
        tvValue.setText(volumeView.getProgress()+"");
    }

    public void setMaxVolume(int maxVolume){
        volumeView.setMax(maxVolume);
    }

    public void addVolume(){
        volumeView.addProgress();
        tvValue.setText(volumeView.getProgress()+"");
    }

    public void subVolume(){
        volumeView.subProgress();
        tvValue.setText(volumeView.getProgress()+"");
    }

    public int getMaxVolume(){
        return volumeView.getMax();
    }

    public int getCurrentVolume(){
        return volumeView.getProgress();
    }
}
