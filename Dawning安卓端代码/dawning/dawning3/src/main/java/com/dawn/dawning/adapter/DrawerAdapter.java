package com.dawn.dawning.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.dawn.dawning.Entity.MenuItem;
import com.dawn.dawning.R;


/**自定义设置侧滑菜单ListView的Adapter*/
public class DrawerAdapter extends BaseAdapter{

    //存储侧滑菜单中的各项的数据
    List<MenuItem> MenuItems = new ArrayList<MenuItem>( ) ;
    //构造方法中传过来的activity
    Context context ;

    //构造方法
    public DrawerAdapter( Context context ){

        this.context = context ;

//        MenuItems.add(new MenuItem("", R.drawable.) ;
//        MenuItems.add(new MenuItem("推荐", R.drawable.advise)) ;
//        MenuItems.add(new MenuItem("发现", R.drawable.find)) ;
//        MenuItems.add(new MenuItem("主题", R.drawable.theme)) ;
//        MenuItems.add(new MenuItem("站点", R.drawable.point)) ;
//        MenuItems.add(new MenuItem("搜索", R.drawable.search)) ;
//        MenuItems.add(new MenuItem("离线", R.drawable.leave)) ;
//        MenuItems.add(new MenuItem("设置", R.drawable.set)) ;
    }

    @Override
    public int getCount() {

        return MenuItems.size();

    }

    @Override
    public MenuItem getItem(int position) {

        return MenuItems.get(position) ;
    }

    @Override
    public long getItemId(int position) {

        return position ;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView ;
//        if(view == null){
//            view =LayoutInflater.from(context).inflate(R.layout.menudrawer_item, parent, false);
//            ((TextView) view).setText(getItem(position).menuTitle) ;
//            ((TextView) view).setCompoundDrawablesWithIntrinsicBounds(getItem(position).menuIcon, 0, 0, 0) ;
//        }
        return view ;
    }

}