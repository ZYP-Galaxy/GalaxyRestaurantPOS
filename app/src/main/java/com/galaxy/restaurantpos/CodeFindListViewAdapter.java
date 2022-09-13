package com.galaxy.restaurantpos;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CodeFindListViewAdapter extends ArrayAdapter<ItemsObj> {
    Context context;

    public CodeFindListViewAdapter(Context context, int resourceId, List<ItemsObj> items) {
        super(context, resourceId, items);
        this.context = context;
    }

    /*private view holder class*/
    private class ViewHolder {
        TextView code;
        TextView description;
        TextView price;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        CodeFindListViewAdapter.ViewHolder holder = null;

        ItemsObj item = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        convertView = mInflater.inflate(R.layout.search_item, null);
        holder = new CodeFindListViewAdapter.ViewHolder();
        holder.code = (TextView) convertView.findViewById(R.id.txtScode);
        holder.description = (TextView) convertView.findViewById(R.id.txtSdes);
        holder.price = (TextView) convertView.findViewById(R.id.txtSprice);

        holder.code.setText(item.getusr_code());
        holder.description.setText(item.getdescription());
        //holder.price.setText(item.getsale_price());
        DatabaseHelper dbhelper=new DatabaseHelper(context);
        holder.price.setText(dbhelper.getCurrencyFormat(item.getsale_price()));



        return convertView;
    }
}
