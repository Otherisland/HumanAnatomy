package com.bn.hk.humananatomy.inteface;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.bn.hk.humananatomy.R;

import static com.bn.hk.humananatomy.constant.Constant.baa;
import static com.bn.hk.humananatomy.constant.Constant.categoryIndex;
import static com.bn.hk.humananatomy.constant.Constant.categoryItemCount;
import static com.bn.hk.humananatomy.constant.Constant.lock;
import static com.bn.hk.humananatomy.constant.Constant.itemIndex;
public class ItemList extends FrameLayout {

    public static Context context;

    public ItemList(@NonNull Context context) {
        this(context,null);
    }

    public ItemList(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ItemList(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
      //  System.out.println("该listView一共走了几遍");
        initView();
    }
    public void initView()
    {
        View view = LayoutInflater.from(context).inflate(
                R.layout.list, this, true); //必须有

        initContent();
    }
    public  void initContent() {
        ListView listView = findViewById(R.id.itemList);
        //为ListView准备内容适配器
       baa= new BaseAdapter() {
               @Override
               public int getCount() {
                   return categoryItemCount[categoryIndex];//总共15个选项
               }
               @Override
               public Object getItem(int arg0) {
                   return null;
               }
               @Override
               public long getItemId(int arg0) {
                   return 0;
               }
               @Override
               public View getView(int arg0, View arg1, ViewGroup arg2) {
                   /*
                    * 动态生成每个下拉项对应的View，每个下拉项View由LinearLayout
                    *中包含一个ImageView及一个TextView构成
                    */
                   synchronized (lock)
                   {
                       itemIndex=arg0%categoryItemCount[categoryIndex];
                   }
                   ItemType item=new ItemType(context);//动态生成界面
                   return item;
               }
           };
       listView.setAdapter(baa);//为ListView设置内容适配器

    }
}
