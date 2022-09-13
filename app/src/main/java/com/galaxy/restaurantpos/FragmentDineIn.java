package com.galaxy.restaurantpos;

import java.util.ArrayList;
import java.util.List;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

public class FragmentDineIn extends Fragment {
	ListView oddlistView;
	List<RowItem> oddrowItems;
	View v ;
	Context ctx;
   @Override
   public View onCreateView(LayoutInflater inflater,
      ViewGroup container, Bundle savedInstanceState) {      
       //Inflate the layout for this fragment
	   View v = inflater.inflate(
		    	R.layout.fragment_dinein, container, false); 
	   
	   List<Table_Name> tablelist= new DatabaseHelper(this.getActivity()).getActiveTable();		
		 oddrowItems = new ArrayList<RowItem>();				
		 for (int i = 0; i < tablelist.size(); i++) {	        	
	        	RowItem item = new RowItem(tablelist.get(i).getTranid(),tablelist.get(i).getTable_Name_ID(),tablelist.get(i).getTable_Name(),tablelist.get(i).getDocID(),"");
	        	oddrowItems.add(item);	        		           
	     }   

	        oddlistView = (ListView)this.getActivity().findViewById(R.id.listorder);
	        
	        CustomListViewAdapter oddadapter = new CustomListViewAdapter(this.getActivity(),R.layout.list_item, oddrowItems);	       
	        oddlistView.setAdapter(oddadapter);
	        
	        oddlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	        	  public void onItemClick(AdapterView<?> parent, View view,
	                      int position, long id) {

	        		  RowItem o = (RowItem)oddlistView.getItemAtPosition(position);

	        		  //String TableID = o.getTitle();
	        		  //String TableName = o.getDesc();	        		 	        		 	        		         	
	        	      //  OpenOrderedData(TableID, TableName);	 	        		  	        		 
	        	  }
	        	}); 
	   
      return v;                
   }   
   @Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);		
              	        		
	}
}
