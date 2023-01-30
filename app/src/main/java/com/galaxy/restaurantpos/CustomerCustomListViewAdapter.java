package com.galaxy.restaurantpos;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class CustomerCustomListViewAdapter extends ArrayAdapter<RowItem> {
    Context context;

    public CustomerCustomListViewAdapter(Context context, int resourceId,
                                         List<RowItem> items) {
        super(context, resourceId, items);
        this.context = context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        RowItem rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.customerlist_item, null);
            holder = new ViewHolder();
            holder.txttranid = (TextView) convertView.findViewById(R.id.txttranid);
            holder.txtheader = (TextView) convertView.findViewById(R.id.txtheader);
            holder.txtdetail = (TextView) convertView.findViewById(R.id.txtdetail);
            holder.txttime = (TextView) convertView.findViewById(R.id.txttime);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        holder.txttranid.setText(Integer.toString(rowItem.gettranid()));
        holder.txtheader.setText(rowItem.getheader());
        holder.txtheader.setTag(Integer.toString(rowItem.getheaderid()));
        holder.txtdetail.setText(rowItem.getdetail());
        holder.txtdetail.setTag(Integer.toString(rowItem.getheaderid()));
        holder.txttime.setText(rowItem.getTime());
        return convertView;
    }

    /*private view holder class*/
    private class ViewHolder {
        TextView txttranid;
        TextView txtheader;
        TextView txtdetail;
        TextView txttime;
    }
}