package com.galaxy.restaurantpos;


import java.util.List;

import android.content.Context;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class ViewPageAdapter extends PagerAdapter {
	
	Context context;
	List<ItemsObj> itemlist;
	LayoutInflater inflater;
	
	public ViewPageAdapter(Context context,List<ItemsObj> objlist) {
		super();
		this.context = context;
		itemlist = objlist;
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return itemlist.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		// TODO Auto-generated method stub
		return view == ((RelativeLayout) object);
	}
	
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// TODO Auto-generated method stub
		
		TextView txtDes;

		ImageView itemimage;

		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View itemView = inflater.inflate(R.layout.activity_viewpager_item, container,
				false);

		// Locate the TextViews in viewpager_item.xml
		txtDes = (TextView) itemView.findViewById(R.id.txtDescription);

		// Capture position and set to the TextViews
		txtDes.setText(itemlist.get(position).getdescription());

		txtDes.setTag(itemlist.get(position).getcode());
		// Locate the ImageView in viewpager_item.xml
		itemimage = (ImageView) itemView.findViewById(R.id.Item_ImageView);
		// Capture position and set to the ImageView

		//itemimage.setImageDrawable(itemlist.get(position).getimgdrawable());
		final String dataurl = new DatabaseHelper(context).getServiceURL();
		Picasso.get().load(dataurl + "/Data/GetImage?usr_code="
						+ itemlist.get(position).getusr_code().trim())
				.placeholder(R.drawable.default_menuimage)
				.error(R.drawable.default_menuimage)
				.into(itemimage);

		// Add viewpager_item.xml to ViewPager
		((ViewPager) container).addView(itemView);

		return itemView;
	}
	
	@Override
	public void destroyItem(View container, int position, Object object) {
		// TODO Auto-generated method stub
		((ViewPager) container).removeView((View) object);
	}

}
