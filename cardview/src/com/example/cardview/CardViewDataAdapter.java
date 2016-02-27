package com.example.cardview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CardViewDataAdapter extends RecyclerView.Adapter<CardViewDataAdapter.ViewHolder> {
 public String[] mDataset;

 

 // Provide a suitable constructor (depends on the kind of dataset)
 public CardViewDataAdapter(String[] myDataset) {
  mDataset = myDataset;
 }

 // Create new views (invoked by the layout manager)
 @Override
 public CardViewDataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
   int viewType) {
  // create a new view
  View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
    R.layout.cardview_row, null);

  // create ViewHolder

  ViewHolder viewHolder = new ViewHolder(itemLayoutView);
  return viewHolder;
 }

 // Replace the contents of a view (invoked by the layout manager)
 @Override
 public void onBindViewHolder(ViewHolder viewHolder, int position) {

  // - get data from your itemsData at this position
  // - replace the contents of the view with that itemsData

  viewHolder.tvtinfo_text.setText(mDataset[position].toString());
  viewHolder.info_det.setText(mDataset[position].toString() + "000");
  

 }

 // Return the size of your dataset (invoked by the layout manager)
 @Override
 public int getItemCount() {
  return mDataset.length;
 }

 // inner class to hold a reference to each item of RecyclerView
 public static class ViewHolder extends RecyclerView.ViewHolder {

  public TextView tvtinfo_text;
  
  public TextView info_det;

  public ViewHolder(View itemLayoutView) {
   super(itemLayoutView);
   tvtinfo_text = (TextView) itemLayoutView
     .findViewById(R.id.info_text);
   
   info_det = (TextView) itemLayoutView
		     .findViewById(R.id.info_det);
   tvtinfo_text.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, 300);
		tvtinfo_text.setLayoutParams(lp);
		
	}
});

  }
 }

}
