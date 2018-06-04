package com.tao.weexsearch.components;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tao.weexsearch.glide.GlideCircleTransform;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.dom.WXDomObject;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXComponentProp;
import com.taobao.weex.ui.component.WXVContainer;

import static java.security.AccessController.getContext;


public class CircleImageView extends WXComponent {
    public CircleImageView(WXSDKInstance instance, WXDomObject dom, WXVContainer parent) {
        super(instance, dom, parent);
    }

    @Override
    protected ImageView initComponentHostView(@NonNull Context context) {
        ImageView view = new ImageView(context);
        return view;
    }

    @WXComponentProp(name = "setSrc")
    public void setImage(String url) {
        Glide.with(getContext()).load(url)
                .transform(new GlideCircleTransform(getContext())).into((ImageView) getHostView());
    }
}
