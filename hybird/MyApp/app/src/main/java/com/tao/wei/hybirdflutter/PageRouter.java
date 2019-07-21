package com.tao.wei.hybirdflutter;

import android.content.Context;
import android.content.Intent;

import test.FirstPageActivity;
import test.MyFlutterPage;

public class PageRouter {
    private static String TAG = PageRouter.class.getSimpleName();
    public static final String NATIVE_PAGE_URL = "sample://nativePage";
    public static final String FLUTTER_PAGE_URL = "sample://flutterPage";
    public static final String FLUTTER_FRAGMENT_PAGE_URL = "sample://flutterFragmentPage";

    //add for test
    public static final String FLUTTER_MY_PAGE_URL = "flutterbus://myFlutterPage";
    public static final String FLUTTER_FIRST = "flutterbus://first";

    public static boolean openPageByUrl(Context context, String url) {
        return openPageByUrl(context, url, 0);
    }

    public static boolean openPageByUrl(Context context, String url, int requestCode) {
        LogUtil.d(TAG,"openPageByUrl url: "+url+", requestCode: " + requestCode);
        try {
            if (url.startsWith(FLUTTER_PAGE_URL)) {
                context.startActivity(new Intent(context, FlutterPageActivity.class));
                return true;
            } else if (url.startsWith(FLUTTER_FRAGMENT_PAGE_URL)) {
                context.startActivity(new Intent(context, FlutterFragmentPageActivity.class));
                return true;
            } else if (url.startsWith(NATIVE_PAGE_URL)) {
                context.startActivity(new Intent(context, NativePageActivity.class));
                return true;
            }else if(url.startsWith(FLUTTER_MY_PAGE_URL)){
                context.startActivity(new Intent(context, MyFlutterPage.class));
                return true;
            }else if(url.startsWith(FLUTTER_FIRST)) {
                context.startActivity(new Intent(context, FirstPageActivity.class));
                return true;
            }
            else {
                return false;
            }
        } catch (Throwable t) {
            return false;
        }
    }
}
