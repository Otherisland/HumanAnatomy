package com.bn.hk.humananatomy.inteface;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bn.hk.humananatomy.R;

import java.util.ArrayList;
import java.util.List;

import static com.bn.hk.humananatomy.constant.Constant.menu_State;


public class Carousel extends FrameLayout {
    private static final int DELAY = 3000;  //播放图片间隔时间
    private Context mContext;  //上下文，运行环境
    //存放图片的ImageView
    private List<View> mPagesIV;
    //存放圆点的ImageView
    private List<ImageView> mDotsIV;
    //待显示图片的资源ID
    private int[] mDrawableIds = {R.drawable.hx_introduction, R.drawable.yd_introduction, R.drawable.xh_introduction};
    private ViewPager mVP;   //一个容器。有自己适配器
    private boolean isAutoPlay;//自动播放
    private int currentItem; //当前项

    private Handler mHandler = new Handler(); //异步消息处理

    public Carousel(Context context) {
        this(context, null);
    }

    public Carousel(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Carousel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();   //构造方法中初始化View
    }

    private void initView() {   //初始化View
        mPagesIV = new ArrayList<>();   //初始化图片，圆点列表
        mDotsIV = new ArrayList<>();
        initContent();
    }

    private void initContent() {    //初始化内容

        //对应界面的xml,找到其中的圆点，图片id
        View view = LayoutInflater.from(mContext).inflate(
                R.layout.carousel_layout, this, true);

        mVP = (ViewPager) view.findViewById(R.id.view_pager);      //对应界面的容器
        LinearLayout mDotsLayout = (LinearLayout) view.findViewById(R.id.dots); //对应界面的圆点LInerLayout

        int len = 3;   //圆点《——————》图片数量
        for (int i = 0; i < len; i++) {
            ImageView dotIV = new ImageView(mContext);   //初始化圆点区域对象
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);   //用代码设置LinearLayout格式 LayoutParams
            params.leftMargin = params.rightMargin = 4;   //代码设置LinerLayout左右距离（格式）
            mDotsLayout.addView(dotIV, params); //将图片和（图片在LinearLayout中的）格式加载进界面LinerLayout中
            mDotsIV.add(dotIV); //将放进单个圆点区域的LinearLayout加载进圆点列表
        }

        for (int i = 0; i < len; i++) {
            ImageView pageIV = new ImageView(mContext);  //初始化图片page区域对象
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            pageIV.setImageResource(mDrawableIds[i]);  //图片区域放置对应索引的图片资源
            mPagesIV.add(pageIV);  //将单个图片加载进图片列表
        }


        mVP.setAdapter(new TopPagerAdapter());  //容器放置适配器
        mVP.setFocusable(true);   //容器可点击
        mVP.setCurrentItem(currentItem);  //放置当前项
        mVP.addOnPageChangeListener(new TopOnPageChangeListener()); //当前页改变监听器
        startCarousel(); //开始轮播
    }

    private void startCarousel() {
        isAutoPlay = true;    //开始自动播放
        mHandler.postDelayed(task, DELAY);  //给异步消息传递机制，设置任务、间隔时间
    }

    private final Runnable task = new Runnable() {//图片轮播线程
        @Override
        public void run() {
            if (isAutoPlay&&menu_State==false) {
                currentItem = (currentItem + 1) % (mPagesIV.size()); //当前项切换到下一项
                mVP.setCurrentItem(currentItem);   //给容器设置新的当前项内容
                mHandler.postDelayed(task, DELAY);
            } else {                             //选中时也正常发送异步消息
                mHandler.postDelayed(task, DELAY);
            }
        }
    };

    class TopPagerAdapter extends PagerAdapter {        //PagerAdapter主要是viewpager的适配器
        // 获取要滑动的控件的数量，在这里我们以滑动的广告栏为例，那么这里就应该是展示的广告图片的ImageView数量
        @Override
        public int getCount() {
            return mPagesIV.size();
        }

        // 来判断显示的是否是同一张图片，这里我们将两个参数相比较返回即可
        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        // 当要显示的图片可以进行缓存的时候，会调用这个方法进行显示图片的初始化，我们将要显示的ImageView加入到ViewGroup中，然后作为返回值返回即可
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mPagesIV.get(position));

            return mPagesIV.get(position);
        }
        // PagerAdapter只缓存三张要显示的图片，如果滑动的图片超出了缓存的范围，就会调用这个方法，将图片销毁
       @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

    }

    class TopOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {  //position是滑动结果index
            for (int i = 0; i < mDotsIV.size(); i++) {  //将对应索引的圆点变成蓝色选中状态，   其余为淡灰色
                if (i == position) {
                    mDotsIV.get(i).setImageResource(R.drawable.dot0);
                } else {
                    mDotsIV.get(i).setImageResource(R.drawable.dot1);
                }
            }
        }

 /*       共同
        1.state有三个值：0（END）,1(PRESS) , 2(UP)
        2. 手指滑动
            1（滑动时）手指按下去的时候会触发这个方法，state值为1
            2手指抬起时，如果发生了滑动（即使很小），就会触发这个方法，这个值会变为2
            3最后滑动结束，页面停止的时候，也会触发这个方法，值变为0
            4所以一次手指滑动会执行这个方法三次
            5 一种特殊情况是手指按下去以后一点滑动也没有发生，这个时候只会调用这个方法两次，state值分别是1,0
        3.setCurrentItem翻页
            1.会执行这个方法两次，state值分别为2 , 0 。*/
        @Override
        public void onPageScrollStateChanged(int state) {
            switch (state) {
                case 1:
                    isAutoPlay = false;
                    break;
                case 2:
                    isAutoPlay = true;
                    break;
                default:
                    break;
            }
        }
    }


}
