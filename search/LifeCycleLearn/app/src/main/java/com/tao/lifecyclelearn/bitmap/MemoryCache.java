package com.tao.lifecyclelearn.bitmap;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class MemoryCache<K,V> extends LruCache<String,Bitmap> {
    /**
     * @param maxSize for caches that do not override {@link #sizeOf}, this is
     *                the maximum number of entries in the cache. For all other caches,
     *                this is the maximum sum of the sizes of the entries in this cache.
     */
    public MemoryCache(int maxSize) {
        super(maxSize);
        reuseablePoll = Collections.synchronizedSet(new HashSet<WeakReference<Bitmap>>());
    }

    private Set<WeakReference<Bitmap>> reuseablePoll;//复用池

    @Override
    protected int sizeOf(String key, Bitmap value) {
//        return super.sizeOf(key, value);
        return value.getByteCount();
    }

    @Override
    protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
//        super.entryRemoved(evicted, key, oldValue, newValue);
        if(oldValue.isMutable()){
            reuseablePoll.add(new WeakReference<Bitmap>(oldValue));
        }else {
            oldValue.recycle();
        }
    }
}
