package com.galaxy.restaurantpos;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
 
public class ComboCustomListViewAdapter extends ArrayAdapter<RowItem> { 
    Context context; 
    public static Typeface font;
    public ComboCustomListViewAdapter(Context context, int resourceId,
            List<RowItem> items) {
        super(context, resourceId, items);
        this.context = context;
    } 
    /*private view holder class*/
    private class ViewHolder {
        TextView txttranid ;
        TextView txtheader;        
        TextView txtdetail;
        TextView txttime;
    }
 
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        RowItem rowItem = getItem(position);
 
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.combolist_item, null);
            holder = new ViewHolder();
            holder.txttranid = (TextView) convertView.findViewById(R.id.txttranid);
            holder.txtheader = (TextView) convertView.findViewById(R.id.txtheader);
            holder.txtdetail = (TextView) convertView.findViewById(R.id.txtdetail);
            holder.txttime = (TextView) convertView.findViewById(R.id.txttime);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();
        
        font = Typeface.createFromAsset(this.context.getAssets(), "fonts/Zawgyi-One.ttf");
        
        holder.txttranid.setText(Integer.toString(rowItem.gettranid()));
        //holder.txttranid.setTextColor(Color.parseColor("#FFFFFF"));
        
        holder.txtheader.setText(rowItem.getheader());
        //holder.txtheader.setTextColor(Color.parseColor("#FFFFFF"));
        
        holder.txtheader.setTag(Integer.toString(rowItem.getheaderid()));
        
        holder.txtdetail.setText(rowItem.getdetail());
        //holder.txtdetail.setTextColor(Color.parseColor("#FFFFFF"));

        holder.txtdetail.setTypeface(font);
        holder.txttime.setText(rowItem.getTime()); 
        return convertView;
    }
}