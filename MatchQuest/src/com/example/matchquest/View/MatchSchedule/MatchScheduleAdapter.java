package com.example.matchquest.View.MatchSchedule;

import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.matchquest.R;
import com.example.matchquest.DataManipulation.MatchSchedule.MatchScheduleDM;
import com.example.matchquest.DataManipulation.RequestStatus.RequestStatusDM;
import com.example.matchquest.View.CommonActivityWithFragment;
import com.example.matchquest.View.CommonViewClass;
import com.example.matchquest.View.RequestStatus.RequestStatusDetailPopUp;
import com.example.matchquest.View.teamDetail.TeamDetailActivity;
import com.example.matchquest.common.Details;
import com.example.matchquest.common.TeamQuestConstants;
import com.example.matchquest.model.Players;
import com.example.matchquest.model.RequestStatus;
import com.example.matchquest.model.Team;

public class MatchScheduleAdapter  extends RecyclerView.Adapter<MatchScheduleAdapter.MyViewHolder>{

	Context mContext;
	List<RequestStatus> matchScheduleList;
	Team team;
	LayoutInflater inflater;
	CommonActivityWithFragment activityObject;
	
	public MatchScheduleAdapter(CommonActivityWithFragment activityObject,List<RequestStatus> matchScheduleList,
			Team team)
	{
		this.mContext = activityObject.getApplicationContext();
        this.activityObject = activityObject;
        inflater = LayoutInflater.from(mContext);
        this.matchScheduleList = matchScheduleList;
        this.team = team;
	}
	
	@Override
	public int getItemCount() {
		return matchScheduleList.size();
	}

	@Override
	public void onBindViewHolder(MyViewHolder holder, int position) {
		RequestStatus status = matchScheduleList.get(position);
		
		holder.matchScheduleTeamName.setText(status.getTeamName());
		holder.matchScheduleLoc.setText(" " + status.getLocation());
		holder.matchScheduleDate.setText(" " + status.getDateString() + "   " + status.getTime());
		holder.matchScheduleNop.setText(" " + status.getNop());
		holder.matchScheduleDetailLayout.setTag(status);
		
		
		likeButtonVisibilty(holder.likeButton,holder.unlikeButton, status);
		
	}

	@Override
	public MyViewHolder onCreateViewHolder(ViewGroup parent, int position) {
		View view = inflater.inflate(R.layout.match_schedule_adapter_layout, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
	}
	
	private void likeButtonVisibilty(final ImageView likeButton, final ImageView unlikeButton,
			final RequestStatus status)
		{
		
		boolean isLiked = false;
		for(Players player :status.getPlayersList())
		{
			if(player.getPlayerId().equals(Details.getPlayer(activityObject).getPlayerId()))
			{
			isLiked = true;
			break;
			}
		}
		if(isLiked)
		{
			likeButton.setVisibility(View.GONE);
			unlikeButton.setVisibility(View.VISIBLE);
		}else{
			unlikeButton.setVisibility(View.GONE);
			likeButton.setVisibility(View.VISIBLE);
		}
		
		unlikeButton.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if(CommonViewClass.isNetworkAvailable(mContext))
			{
				unlikeButton.setVisibility(View.GONE);
				likeButton.setVisibility(View.VISIBLE);
				Toast.makeText(mContext, status.getUniqueId(), 1000).show();
				savePlayerOpinion(status, true);
			}else{
				CommonViewClass.showdialog(mContext, TeamQuestConstants.connectToInternet_key);
			}
		
		}
		});
		likeButton.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if(CommonViewClass.isNetworkAvailable(mContext))
			{
				likeButton.setVisibility(View.GONE);
				unlikeButton.setVisibility(View.VISIBLE);
				Toast.makeText(mContext, status.getUniqueId(), 1000).show();
				savePlayerOpinion(status, false);
			}else{
				CommonViewClass.showdialog(mContext, TeamQuestConstants.connectToInternet_key);
			}
			}
		});
		
		}

	 private void savePlayerOpinion(RequestStatus status,boolean toRemove)
	    {
	    	CommonViewClass.showProgressdialog(activityObject);
	    	
	    	MatchScheduleDM saveRequestStatusDm = new MatchScheduleDM();
				
				RequestStatus saveRequestStatus = new RequestStatus();
				saveRequestStatus.setUniqueId(status.getUniqueId());
				saveRequestStatus.setParent(status.isParent());
				
				saveRequestStatusDm.savePlayerOpinion(saveRequestStatus, Details.getPlayer(activityObject).getPlayerId(), toRemove);
				if(toRemove)
				{
					for(Iterator<Players> it = status.getPlayersList().iterator();it.hasNext(); )
					{
						Players player = it.next();
						if(player.getPlayerId().equals(Details.getPlayer(activityObject).getPlayerId()))
						{
							it.remove();
							break;
						}
					}
				}else{
					status.getPlayersList().add(Details.getPlayer(activityObject));
				}
			CommonViewClass.hideProgressDialog();
	    }
	 
	class MyViewHolder extends RecyclerView.ViewHolder {
		TextView matchScheduleTeamName;
        TextView matchScheduleLoc;
        TextView matchScheduleDate;
        TextView matchScheduleNop;
        RelativeLayout matchScheduleLayout;
        ImageView likeButton;
        ImageView unlikeButton;
        
        RelativeLayout matchScheduleDetailLayout;
        
        RelativeLayout optionsIcon;
 
        public MyViewHolder(View itemView) {
            super(itemView);
            
            matchScheduleTeamName = (TextView) itemView.findViewById(R.id.match_schedule_adpter_team_text);
            matchScheduleLoc = (TextView) itemView.findViewById(R.id.match_schedule_adapter_loc_text);
            matchScheduleDate = (TextView) itemView.findViewById(R.id.match_schedule_adpter_date_text);
            matchScheduleNop = (TextView) itemView.findViewById(R.id.match_schedule_adpter_nop_text);
            matchScheduleLayout = (RelativeLayout) itemView.findViewById(R.id.match_schedule_adapter_details);
            likeButton = (ImageView) itemView.findViewById(R.id.match_schedule_adapter_like);
            unlikeButton = (ImageView) itemView.findViewById(R.id.match_schedule_adapter_unlike);
            
            matchScheduleDetailLayout = (RelativeLayout) itemView.findViewById(R.id.match_schedule_adapter_detail_layout);
            
            matchScheduleDetailLayout.setOnClickListener(new OnClickListener() {
    			
    			@Override
    			public void onClick(View v) {
    				RequestStatus status = (RequestStatus) v.getTag();
    				Toast.makeText(activityObject, status.getTeamName(),1000).show();
    				
					Intent matchDetailIntent = new Intent(mContext, MatchDetailActivity.class); 
					matchDetailIntent.putExtra(TeamQuestConstants.requestStatus_key, status);  
					matchDetailIntent.putExtra(TeamQuestConstants.team_key, team);
					matchDetailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					mContext.startActivity(matchDetailIntent);
    				
    			}
    		});
    		
    		matchScheduleDetailLayout.setOnLongClickListener(new OnLongClickListener() {
    			
    			@Override
    			public boolean onLongClick(View v) {
    				RequestStatus status = (RequestStatus) v.getTag();
    				RequestStatusDetailPopUp popUp = new RequestStatusDetailPopUp();
     				popUp.showPopup(activityObject, status,TeamQuestConstants.matchSchedule_key);
     				activityObject.getFrament().onPopUpOpen();
     				
    				return true;
    			}
    		});
            
        }
    }

}
