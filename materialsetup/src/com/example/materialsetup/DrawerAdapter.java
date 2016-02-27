package com.example.materialsetup;

import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.graphics.PorterDuff.Mode;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.MyViewHolder>  implements  android.widget.PopupMenu.OnMenuItemClickListener{
    List<DrawerTexts> data = Collections.emptyList();
    private LayoutInflater inflater;
    private Context context;
    DrawerTexts selectedViewText;
    
    public DrawerAdapter(Context context, List<DrawerTexts> data)
    {
    	 this.context = context;
         inflater = LayoutInflater.from(context);
         this.data = data;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.drawer_layout_row, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }
 
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
    	DrawerTexts current = data.get(position);
        holder.teamName.setText(current.getTeamName());
        holder.loc.setText(current.getLoc());
        holder.date.setText(current.getDate());
        holder.nop.setText(current.getNop());
        holder.serachMenu.setTag(current);
    }
 
    @Override
    public int getItemCount() {
        return data.size();
    }
    
    public DrawerTexts getItem(int position) {
    	// TODO Auto-generated method stub
    	return data.get(position);
    }
    
    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView teamName;
        TextView loc;
        TextView date;
        TextView nop;
        ImageView serachMenu;
        RelativeLayout teamDetailLayout;
 
        public MyViewHolder(View itemView) {
            super(itemView);
            serachMenu = (ImageView) itemView.findViewById(R.id.team_options_icon);
            serachMenu.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if(v.getId() == R.id.team_options_icon)
					{
						selectedViewText = (DrawerTexts) v.getTag();
					PopupMenu popUp = new PopupMenu(context, v);
		           	 popUp.setOnMenuItemClickListener(DrawerAdapter.this);
		           	 MenuInflater inflator = popUp.getMenuInflater();
		           	 inflator.inflate(R.menu.main, popUp.getMenu());
		           	 popUp.show();
					}
					
				}
			});
            teamName = (TextView) itemView.findViewById(R.id.teamName);
            loc = (TextView) itemView.findViewById(R.id.loc);
            date = (TextView) itemView.findViewById(R.id.date);
            nop = (TextView) itemView.findViewById(R.id.nop);
            teamDetailLayout = (RelativeLayout) itemView.findViewById(R.id.team_details_layout);
            teamDetailLayout.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Toast.makeText(context, "loyout",1000).show();			
				}
			});
        }
    }

	@Override
	public boolean onMenuItemClick(MenuItem item) {
		
			Toast.makeText(context,selectedViewText.getTeamName(), 1000).show();
		
		return false;
	}
    
    

}
