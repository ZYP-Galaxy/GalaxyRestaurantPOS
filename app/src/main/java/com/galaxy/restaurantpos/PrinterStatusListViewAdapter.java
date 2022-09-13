package com.galaxy.restaurantpos;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class PrinterStatusListViewAdapter extends ArrayAdapter<Printer> {
    Context context;

    public PrinterStatusListViewAdapter(Context context, int resourceId, List<Printer> items) {
        super(context, resourceId, items);
        this.context = context;
    }

    /*private view holder class*/
    private class ViewHolder {
        TextView printerName;
        ImageView printerStatus;

    }

    public View getView(int position, View convertView, ViewGroup parent) {

        PrinterStatusListViewAdapter.ViewHolder holder = null;

        Printer printer = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        convertView = mInflater.inflate(R.layout.printer_status_list, null);
        holder = new PrinterStatusListViewAdapter.ViewHolder();
        holder.printerName = (TextView) convertView.findViewById(R.id.tvprinterName);
        holder.printerStatus = (ImageView) convertView.findViewById(R.id.ivprinterStatus);


        holder.printerName.setText(printer.getPrinterName());

        if (printer.get_PrinterStatus().equals("Connected")) {
            holder.printerStatus.setImageResource(R.drawable.greenstatus);
        } else {
            holder.printerStatus.setImageResource(R.drawable.redstatus);
        }

        return convertView;
    }
}
