package com.bn.hk.humananatomy.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bn.hk.humananatomy.R;
import com.bn.hk.humananatomy.alert.AlertPurchase;
import com.bn.hk.humananatomy.inteface.CenterActivity;
import com.bn.hk.humananatomy.inteface.CircleImageView;
import com.bn.hk.humananatomy.inteface.ItemList;
import com.bn.hk.humananatomy.inteface.ItemType;
import com.bn.hk.humananatomy.inteface.MenuAdapter;
import com.bn.hk.humananatomy.inteface.SegmentControl;
import com.bn.hk.humananatomy.inteface.SlidingMenu;
import com.bn.hk.humananatomy.util.DynamicInterfaceUtil;
import com.bn.hk.humananatomy.util.SharedPreferencesManage;

import static com.bn.hk.humananatomy.constant.Constant.baa;
import static com.bn.hk.humananatomy.constant.Constant.categoryIndex;
import static com.bn.hk.humananatomy.constant.Constant.categoryItemCount;
import static com.bn.hk.humananatomy.constant.Constant.initunChapterArray;
import static com.bn.hk.humananatomy.constant.Constant.isFirst;
import static com.bn.hk.humananatomy.constant.Constant.isLogin;
import static com.bn.hk.humananatomy.constant.Constant.loadAndroidOriPic;
import static com.bn.hk.humananatomy.constant.Constant.lock;
import static com.bn.hk.humananatomy.constant.Constant.userName;
import static com.bn.hk.humananatomy.constant.Constant.menu_State;
import static com.bn.hk.humananatomy.constant.Constant.categoryGroup_D;
import static com.bn.hk.humananatomy.constant.Constant.screenWidth;
import static com.bn.hk.humananatomy.constant.Constant.screenHeight;
import static com.bn.hk.humananatomy.constant.Constant.LOADING_OVER;
import static com.bn.hk.humananatomy.constant.Constant.categoryGroup_R;
import static com.bn.hk.humananatomy.constant.Constant.itemIndex;
public class MainActivity extends Activity {

    private CircleImageView circleImageView;
    private SlidingMenu slideMenu1;
    private ListView lv_menu;
    private MenuAdapter menuAdapter;
    private ImageView iv_menu;
    private TextView textView;
    private SharedPreferencesManage spref;


    private SegmentControl mSegmentHorzontal;
    DynamicInterfaceUtil dynamicInterfaceUtil=new DynamicInterfaceUtil();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initunChapterArray();
        setContentView(R.layout.activity_main);

        if(isFirst)
        {
            DisplayMetrics dm = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(dm);
            screenWidth = dm.widthPixels;
            screenHeight = dm.heightPixels;

            loadAndroidOriPic();
            dynamicInterfaceUtil.loadFromSDFile();

            categoryItemCount[0]=categoryGroup_D.size();
            categoryItemCount[1]=categoryGroup_R.size();
            isFirst=false;
        }
        setContentView(R.layout.layout_main);

        initView();
        mSegmentHorzontal = (SegmentControl) findViewById(R.id.segment_control);
        mSegmentHorzontal.setOnSegmentControlClickListener(new SegmentControl.OnSegmentControlClickListener() {
            @Override
            public void onSegmentControlClick(int index) {
                categoryIndex=index;
                changeCategory(); //更改目录
                System.out.println("当前项的选项 ：：："+categoryItemCount[categoryIndex]);
            }
        });

    }
    private void initView() {

        textView=(TextView) findViewById(R.id.user_name);
        textView.setText(userName);
        circleImageView=(CircleImageView) findViewById(R.id.circleImageView) ;
        slideMenu1 = (SlidingMenu) findViewById(R.id.slideMenu1);
        lv_menu = (ListView) findViewById(R.id.lv_menu);
        iv_menu = findViewById(R.id.menu_img);//(ImageView) findViewById(R.id.iv_menu);
        menuAdapter = new MenuAdapter(this);
        lv_menu.setAdapter(menuAdapter);


        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu_State=false;
                Intent intent=new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });


        lv_menu.setOnItemClickListener(new ItemClickEvent());

        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slideMenu1.toggle();
            }
        });
        slideMenu1.setOnStatusListener(new SlidingMenu.OnStatusListener() {

            @Override
            public void statusChanged(SlidingMenu.Status status) {
                if (status == SlidingMenu.Status.Open) {
                    menu_State=true;
                } else {
                    menu_State=false;
                }

            }
        });
    }
    //继承OnItemClickListener，当子项目被点击的时候触发
    private final class ItemClickEvent implements AdapterView.OnItemClickListener {
        @Override
        //这里需要注意的是第三个参数arg2，这是代表单击第几个选项
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                long arg3) {


            //通过单击事件，获得单击选项的内容
            String text = lv_menu.getItemAtPosition(arg2)+"";
            System.out.println("点击成功！！！！"+text+arg2);
            switch (arg2)
            {
                case 0:
                    if(!isLogin)
                    {
                        Toast.makeText(MainActivity.this,"请先点击头像登录！",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Intent intent=new Intent(MainActivity.this, CenterActivity.class);
                    startActivity(intent);
                    break;

                case 1:
                    AlertPurchase al=new AlertPurchase(MainActivity.this);
                    al.setAlertContext(5);
                    break;
                case 2:android.os.Process.killProcess(android.os.Process.myPid());
                    break;
                default:break;
            }
        }
    }


    @Override
    protected void onResume()
    {
        super.onResume();
        System.out.println("activity由暂停再次进入");
        dynamicInterfaceUtil.loadFromSDFile();
        changeCategory(); //更改目录
        LOADING_OVER=false;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void changeCategory()
    {
        System.out.println("更改目录");
        baa=new BaseAdapter() {
            @Override
            public int getCount() {
                return categoryItemCount[categoryIndex];//多少个选项
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

                ItemType item=new ItemType(ItemList.context);
                return item;
            }
        };
        ListView listView = findViewById(R.id.itemList);
        listView.setAdapter(baa);
    }

}