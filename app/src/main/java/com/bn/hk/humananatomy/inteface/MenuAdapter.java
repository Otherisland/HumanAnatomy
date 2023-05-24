package com.bn.hk.humananatomy.inteface;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bn.hk.humananatomy.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anyway on 2016/2/2.
 */
public class MenuAdapter extends BaseAdapter {

    private List<String> list;
    private List<Integer> iConlist;
    private Context context;

    public MenuAdapter(Context context) {
        this.context = context;
        list = new ArrayList();
        list.add("个人中心");
      //  list.add("我的消息");
       // list.add("购买记录");
      //  list.add("意见反馈");
        list.add("关于我们");
        list.add("退出软件");
        iConlist=new ArrayList<>();
        iConlist.add(R.drawable.menu_center);
      //  iConlist.add(R.drawable.menu_message);
       // iConlist.add(R.drawable.menu_record);
       // iConlist.add(R.drawable.menu_feedback);
        iConlist.add(R.drawable.menu_about);
        iConlist.add(R.drawable.menu_exit);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        String item = list.get(position);
        int image_item=iConlist.get(position);
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.menu_item, null);
            viewHolder.tv_item = (TextView) convertView.findViewById(R.id.tv_item);
            convertView.setTag(viewHolder);

            viewHolder.image_item=(ImageView)convertView.findViewById(R.id.image_item);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_item.setText(item);
        viewHolder.image_item.setImageResource(image_item);
        return convertView;
    }

    static class ViewHolder {
        TextView tv_item;
        ImageView image_item;
    }
}
