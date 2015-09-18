package com.example.ImageSelect;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.LruCache;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by WYJ on 2015-09-18.
 */
public class ImageLoader {

    private static ImageLoader mInstance;

    private ImageLoader(int threadCount, Type type) {
        init(threadCount, type);
    }

    private void init(int threadCount, Type type) {
        //后台轮询
        mPoolThread = new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                mPoolThreadHandler = new Handler() {


                    @Override
                    public void handleMessage(Message msg) {
                        //去线程池取出一个任务进行执行
                        mThrealPool.execute(getTack());
                    }
                };
            }
        };


        //获取应用可用的最大内存
        mPoolThread.start();
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheMemory = maxMemory / 8;
        mLruCache = new LruCache<String, Bitmap>(cacheMemory) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight();
            }
        };


        //创建线程池
        mThrealPool = Executors.newFixedThreadPool(threadCount);
        mtaskQueue = new LinkedList<Runnable>();
        mType = type;

    }

    public static ImageLoader getInstance() {
        if (mInstance == null) {
            synchronized (ImageLoader.class) {
                if (mInstance == null) {
                    mInstance = new ImageLoader();
                }
            }
        }

        return mInstance;
    }

    private LruCache<String, Bitmap> mLruCache;

    private ExecutorService mThrealPool;

    private Type mType = Type.LIFO;

    private LinkedList<Runnable> mtaskQueue;

    //后台轮询线程
    private Thread mPoolThread;
    private Handler mPoolThreadHandler;


    private Handler mUIHandler;


    public enum Type {
        FIFO, LIFO;
    }

    /**
     * 设置图片
     *
     * @param path
     * @param imageView
     */
    public void loadImage(String path, ImageView imageView) {
        imageView.setTag(path);

        if (mUIHandler == null) {
            mUIHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    //获取图片
                }
            };
        }

        //根据path获取缓存中的图片
        Bitmap bm = getBitmapFromLruCache(path);

        if (bm != null) {
            Message message = Message.obtain();
            ImgBeanHolder holder = new ImgBeanHolder();
            holder.bitmap = bm;
            holder.path = path;
            holder.imageView = imageView;
            message.obj = mUIHandler.sendMessage(message);
        } else {
            addTakes(new Runnable() {
                @Override
                public void run() {
                    //加载图片
                    //图片压缩
                    //
                }
            });
        }
    }

    private class ImageSize {
        int width;
        int heith;

    }

    protected ImageSize getImageViewSize(ImageView imageView) {
        ImageSize ImageSize = new ImageSize();

        ViewGroup.LayoutParams lp = imageView.getLayoutParams();

        int width = (lp.width == ViewGroup.LayoutParams.WRAP_CONTENT
                ? 0 : imageView.getWidth());
        if (width <= 0) {
            width = lp.width;//获取在ImageView在Layout中声明的宽度
        }

        if (width <= 0) {
            width = imageView.getMaxWidth();//获取最大值
        }

        if (width <= 0) {
            DisplayMetrics displayMetrics = imageView.getContext().getResources().getDisplayMetrics();
        }

        return ImageSize;
    }

    private Bitmap getBitmapFromLruCache(String path) {
        return mLruCache.get(path);
    }


    private class ImgBeanHolder {
        public Bitmap bitmap;
        public String path;
        public ImageView imageView;
    }

    private void addTakes(Runnable runnable) {
        mtaskQueue.add(runnable);
        mPoolThreadHandler.sendEmptyMessage(0x110);
    }

    public Runnable getTack() {
        if (mType == Type.FIFO) {
            return mtaskQueue.removeFirst();
        } else if (mType == Type.LIFO) {
            return mtaskQueue.removeLast();
        }

        return null;
    }
}
