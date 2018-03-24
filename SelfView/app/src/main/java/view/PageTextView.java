package view;

import android.content.Context;
import android.graphics.Canvas;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.TextView;

import util.LogUtil;

/**
 * Created by taow on 2017/7/7.
 * 整个 View 都由自己实现的话, 的确能很方便地控制所有细节, 但随之而来的麻烦就是,
 * 所有的细节都得自己实现. 比如我的断行, 和布局自适应这两点处理得就没原生的 TextView 那么好,
 * 只能说勉强能用.更别提超链接这类的东西了, 要想全部实现还真不是一时半会能搞定的.
 */

public class PageTextView extends TextView {
    private CharSequence mText ;
    public PageTextView(Context context) {
        this(context,null);
    }

    public PageTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 计算textview显示的行数
     * @return
     */
    public int getLineNum(){
        LogUtil.i("PageTextView.getLineNum");
        Layout layout = getLayout();
        int topOfLastLine = getHeight() - getPaddingTop() - getPaddingBottom() - getLineHeight();
        //根据纵坐标得到对应的行号
        int lineForVertical = layout.getLineForVertical(topOfLastLine);
        return lineForVertical;
    }

    /**
     * 计算textview显示的最后一个字符在整个字符串中的位置
     * @return
     */
    public int getCharNum(){
        LogUtil.i("PageTextView.getCharNum");
        int lineEnd = getLayout().getLineEnd(getLineNum());
        return lineEnd;
    }

    public int resize(){
        LogUtil.i("PageTextView.resize");
        CharSequence oldContent = getText();
        CharSequence newContent = oldContent.subSequence(0,getCharNum());
        setText(newContent);
        return oldContent.length() - newContent.length();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        LogUtil.i("PageTextView.onLayout");
//        resize();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_CHANNEL_UP){
            //下一页
            LogUtil.i("PageTextView.onKeyDown pageUp");

        }else if(event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_CHANNEL_DOWN){
            //上一页
            LogUtil.i("PageTextView.onKeyDown pageDown");
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        LogUtil.i("PageTextView.onMeasure");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        LogUtil.i("PageTextView.onDraw");
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);

    }
}
