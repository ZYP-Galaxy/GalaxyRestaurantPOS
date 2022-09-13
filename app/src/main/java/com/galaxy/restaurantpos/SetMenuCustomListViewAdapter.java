package com.galaxy.restaurantpos;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
 
public class SetMenuCustomListViewAdapter extends ArrayAdapter<SetmenuItem> { 
    Context context; 
    public static Typeface font;
    public SetMenuCustomListViewAdapter(Context context, int resourceId,
            List<SetmenuItem> items) {
        super(context, resourceId, items);
        this.context = context;
    } 
    /*private view holder class*/
    private class ViewHolder {
        TextView txttranid ;
        TextView txtheader;        
        TextView txtqty;
        
    }
 
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        SetmenuItem rowItem = getItem(position);
 
        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.setmenu_item, null);
            holder = new ViewHolder();
            holder.txttranid = (TextView) convertView.findViewById(R.id.txttranid);
            holder.txtheader = (TextView) convertView.findViewById(R.id.txtheader);
            holder.txtqty = (TextView) convertView.findViewById(R.id.txtqty);
            
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();
        
        font = Typeface.createFromAsset(this.context.getAssets(), "fonts/Zawgyi-One.ttf");
        
        holder.txttranid.setText(Integer.toString(rowItem.gettranid()));
        holder.txtheader.setText(rowItem.getheader());
        holder.txtqty.setText(rowItem.getqty()); 
        return convertView;
    }
}