package com.skyworth.dest;

import android.content.Context;

import com.amap.api.services.core.PoiItem;
import com.skyworth.common.rv.SingleTypeAdapter;
import com.skyworth.common.rv.ViewHolder;
import com.skyworth.navi.R;

public class DestResultAdapter extends SingleTypeAdapter<PoiItem> {
    private int mSelectedPos;

    public DestResultAdapter(Context context) {
        super(context, R.layout.auto_result_item_layout);
    }

    @Override
    protected void bindData(ViewHolder viewHolder, PoiItem item, int position) {
        viewHolder.setSelected(R.id.poi_item_layout_rl, mSelectedPos == position);
        viewHolder.setText(R.id.poi_name, String.valueOf(position + 1) + "." + item.getTitle());
        viewHolder.setText(R.id.distance, String.valueOf(item.getDistance()));
        viewHolder.setText(R.id.poi_addr, item.getSnippet());
    }

    public void setSelectedPos(int position) {
        mSelectedPos = position;
    }

    public int getSelectedPos() {
        return mSelectedPos;
    }
}
