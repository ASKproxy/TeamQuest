package com.example.matchquest.View.RequestStatus;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.example.matchquest.R;
import com.example.matchquest.DataManipulation.RequestStatus.RequestStatusDM;
import com.example.matchquest.View.CommonActivity;
import com.example.matchquest.View.CommonActivityWithFragment;
import com.example.matchquest.View.CommonViewClass;
import com.example.matchquest.View.teams.TeamAdapter;
import com.example.matchquest.common.Details;
import com.example.matchquest.common.TeamQuestConstants;
import com.example.matchquest.model.Players;
import com.example.matchquest.model.RequestStatus;
import com.example.matchquest.model.Team;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.PopupMenu.OnDismissListener;
import se.emilsjolander.stickylistheaders.ExpandableStickyListHeadersListView;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

public class RequestStatusAdapter extends BaseAdapter implements StickyListHeadersAdapter,
							android.widget.PopupMenu.OnMenuItemClickListener{

   
    private LayoutInflater inflater;
    
    List<RequestStatus> requestStatusChildList;
    
    Map<String,RequestStatus> requestStatusChildMap = new LinkedHashMap<String, RequestStatus>();
    
    Map<Long,RequestStatus> requestStatusMap;
    
    Context mContext;
    
    Team team;
    
    RequestStatus selectedRequestStatus;
    
    ExpandableStickyListHeadersListView expandableStickyList;
    
    CommonActivityWithFragment activityObject;
    
    int lastSelectedId = R.id.all_request;

    public RequestStatusAdapter(CommonActivityWithFragment activityObject,List<RequestStatus> requestStatusChildList,
    				Map<Long,RequestStatus> requestStatusMap,Team team,ExpandableStickyListHeadersListView expandableStickyList) {
        this.mContext = activityObject.getApplicationContext();
        
        inflater = LayoutInflater.from(mContext);
        
        setRequestStatusChildList(requestStatusChildList);
        this.requestStatusMap = requestStatusMap;
        this.team = team;
        this.expandableStickyList = expandableStickyList;
        
        this.activityObject = activityObject;
    }

    @Override
    public int getCount() {
        return requestStatusChildList.size();
    }

    @Override
    public Object getItem(int position) {
        return requestStatusChildList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override 
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        
        RequestStatus status = requestStatusChildList.get(position);
       
        if (convertView == null) {
        	
            	holder = new ViewHolder();
	            convertView = inflater.inflate(R.layout.request_status_adapter_layout, parent, false);
	            holder.requestStatusTeamName = (TextView) convertView.findViewById(R.id.request_status_adpter_team_text);
	            holder.requestStatusLoc = (TextView) convertView.findViewById(R.id.request_status_adapter_loc_text);
	            holder.requestStatusDate = (TextView) convertView.findViewById(R.id.request_status_adpter_date_text);
	            holder.requestStatusNop = (TextView) convertView.findViewById(R.id.request_status_adpter_nop_text);
	            holder.requestStatusLayout = (RelativeLayout) convertView.findViewById(R.id.request_status_adapter_details);
	            holder.likeButton = (ImageView) convertView.findViewById(R.id.request_status_adapter_like);
	            holder.unlikeButton = (ImageView) convertView.findViewById(R.id.request_status_adapter_unlike);
	            holder.optionsIcon = (RelativeLayout) convertView.findViewById(R.id.request_status_adapter_options);
	            
	            holder.requestStatusEmptyResponse = (TextView) convertView.findViewById(R.id.requset_status_empty_response_text);
	            holder.requestStatusDetailLayout = (RelativeLayout) convertView.findViewById(R.id.request_status_adapter_detail_layout);
	            
	            if(team.getCaptain().equals(Details.getPlayer(activityObject).getPlayerId()) || team.getViceCaptain().equals(Details.getPlayer(activityObject).getPlayerId()))
	            {
	            	holder.optionsIcon.setVisibility(View.VISIBLE);
	            	
	            }
	           
	            convertView.setTag(holder);
            
        } else {
            	holder = (ViewHolder) convertView.getTag();
        }
        
        holder.optionsIcon.setTag(status);
        if(status.isRequestSent())
        {
        	showPopUp(holder.optionsIcon, R.menu.request_status_invite_menu);
        }else{
        	showPopUp(holder.optionsIcon, R.menu.request_status_cancel_request_menu);
        }
        
        setDetailListener(holder.requestStatusDetailLayout, status);
        likeButtonVisibilty(holder.likeButton,holder.unlikeButton, status,false);
        
        if(status.getUniqueId().equals(TeamQuestConstants.emptyUniqueId_key+status.getUniqueIdLong()))
        {
        	 holder.requestStatusDetailLayout.setVisibility(View.INVISIBLE);
        	 holder.requestStatusEmptyResponse.setVisibility(View.VISIBLE);
        }else{
        	
        	holder.requestStatusEmptyResponse.setVisibility(View.INVISIBLE);
        	holder.requestStatusDetailLayout.setVisibility(View.VISIBLE);
       	 
			holder.requestStatusTeamName.setText(status.getTeamName());
			holder.requestStatusLoc.setText(" " + status.getLocation());
			holder.requestStatusDate.setText(" " + status.getDateString() + "   " + status.getTime());
			holder.requestStatusNop.setText(" " + status.getNop());
			holder.requestStatusDetailLayout.setTag(status);
        }
        return convertView;
    }
    
    private void likeButtonVisibilty(final ImageView likeButton, final ImageView unlikeButton,
    								final RequestStatus status,final boolean isParent)
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
        	likeButton.setVisibility(isParent == true ? View.VISIBLE : View.GONE);
        	unlikeButton.setVisibility(isParent == true ? View.GONE : View.VISIBLE);
        }else{
        	unlikeButton.setVisibility(isParent == true ? View.VISIBLE : View.GONE);
        	likeButton.setVisibility(isParent == true ? View.GONE : View.VISIBLE);
        }
     
        unlikeButton.setOnClickListener(new OnClickListener() {
   			
   			@Override
   			public void onClick(View v) {
   				if(CommonViewClass.isNetworkAvailable(mContext))
   				{
	   				unlikeButton.setVisibility(View.GONE);
	   				likeButton.setVisibility(View.VISIBLE);
	   				Toast.makeText(mContext, status.getUniqueId(), 1000).show();
	   				savePlayerOpinion(status,isParent == true ? false : true);
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
   				savePlayerOpinion(status,isParent == true ? true : false);
   				}else{
   					CommonViewClass.showdialog(mContext, TeamQuestConstants.connectToInternet_key);
   				}
   			}
   		});
       
    }
    
    private void setDetailListener(RelativeLayout requestStatusLayout,final RequestStatus status)
    {
    	
         requestStatusLayout.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				RequestStatusDetailPopUp popUp = new RequestStatusDetailPopUp();
 				popUp.showPopup(activityObject, status,TeamQuestConstants.requestStatus_key);
 				activityObject.getFrament().onPopUpOpen();
				return false;
			}
		});
    }
   

    @Override 
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        final HeaderViewHolder holder;
        
        long uniqueIdLong = requestStatusChildList.get(position).getUniqueIdLong();
        
        RequestStatus status = requestStatusMap.get(uniqueIdLong);
        
        if (convertView == null) {
            holder = new HeaderViewHolder();
            convertView = inflater.inflate(R.layout.request_status_adapter_layout, parent, false);
            convertView.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));
            holder.requestStatusTeamName = (TextView) convertView.findViewById(R.id.request_status_adpter_team_text);
            holder.requestStatusLoc = (TextView) convertView.findViewById(R.id.request_status_adapter_loc_text);
            holder.requestStatusDate = (TextView) convertView.findViewById(R.id.request_status_adpter_date_text);
            holder.requestStatusNop = (TextView) convertView.findViewById(R.id.request_status_adpter_nop_text);
            holder.requestStatusLayout = (RelativeLayout) convertView.findViewById(R.id.request_status_adapter_details);
            holder.likeButton = (ImageView) convertView.findViewById(R.id.request_status_adapter_like);
            holder.unlikeButton = (ImageView) convertView.findViewById(R.id.request_status_adapter_unlike);
            holder.optionsIcon = (RelativeLayout) convertView.findViewById(R.id.request_status_adapter_options);
            
            if(team.getCaptain().equals(Details.getPlayer(activityObject).getPlayerId()) || team.getViceCaptain().equals(Details.getPlayer(activityObject).getPlayerId()))
            {
            	holder.optionsIcon.setVisibility(View.VISIBLE);
            }
            
            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }
        
        holder.optionsIcon.setTag(status);
        showPopUp(holder.optionsIcon, R.menu.request_status_close_invite_menu);
        
        likeButtonVisibilty(holder.likeButton,holder.unlikeButton, status, true);
        
		holder.requestStatusTeamName.setText(status.getTeamName());
		holder.requestStatusLoc.setText(" " + status.getLocation());
		holder.requestStatusDate.setText(" " + status.getDateString() + "   " + status.getTime());
		holder.requestStatusNop.setText(" " + status.getNop());
		holder.requestStatusLayout.setTag(status);
        return convertView;
    }

    @Override
    public long getHeaderId(int position) {    	
        return requestStatusChildList.get(position).getUniqueIdLong();
    }

    class HeaderViewHolder {
    	TextView requestStatusTeamName;
        TextView requestStatusLoc;
        TextView requestStatusDate;
        TextView requestStatusNop;
        RelativeLayout requestStatusLayout;
        ImageView likeButton;
        ImageView unlikeButton;
        RelativeLayout optionsIcon;
    }

    class ViewHolder {
    	TextView requestStatusTeamName;
        TextView requestStatusLoc;
        TextView requestStatusDate;
        TextView requestStatusNop;
        RelativeLayout requestStatusLayout;
        ImageView likeButton;
        ImageView unlikeButton;
        RelativeLayout optionsIcon;
        
        TextView requestStatusEmptyResponse;
        RelativeLayout requestStatusDetailLayout;
    }
    
    class ViewEmptyHolder {
    	TextView requestStatusEmptyResponse;
        
    }

    private void showPopUp(RelativeLayout relativeLayout , final int menuId)
    {
    	
    	
    	 
    	relativeLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				PopupMenu popUp = new PopupMenu(mContext, v);
				
	           	 popUp.setOnMenuItemClickListener(RequestStatusAdapter.this);
	           	 MenuInflater inflator = popUp.getMenuInflater();
	           	 inflator.inflate(menuId, popUp.getMenu());
	           	 popUp.show();
	           	 
	           	selectedRequestStatus = (RequestStatus) v.getTag();
				
			}
		});
    }
    
    private void savePlayerOpinion(RequestStatus status,boolean toRemove)
    {
    	CommonViewClass.showProgressdialog(activityObject);
    	
    	RequestStatusDM saveRequestStatusDm = new RequestStatusDM();
			
			RequestStatus saveRequestStatus = new RequestStatus();
			saveRequestStatus.setUniqueId(status.getUniqueId());
			saveRequestStatus.setParentInviteId(status.getParentInviteId());
			saveRequestStatus.setRequestSent(status.isRequestSent());
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

	@Override
	public boolean onMenuItemClick(MenuItem item) {
		
		saveInviteStatus(item.getItemId(),selectedRequestStatus);
		return false;
	}
	
	private void saveInviteStatus(int id , RequestStatus requestStatus)
	{
		if(CommonViewClass.isNetworkAvailable(mContext))
		{
			CommonViewClass.showProgressdialog(activityObject);
			
				switch (id) {
				case R.id.request_status_cancel_request:
					
						cancelRequest(requestStatus);
						break;
						
				case R.id.request_status_close_invite:
					
						closeInvite(requestStatus);
						break;
					
				case R.id.request_status_accept_invite:
					
						acceptInvite(requestStatus);
						break;
					
				case R.id.request_status_reject_invite:
					
						rejectInvite(requestStatus);
						break;
				}
				
			CommonViewClass.hideProgressDialog();
		}
		else{
			CommonViewClass.showdialog(mContext, TeamQuestConstants.connectToInternet_key);
		}
	}
	
	private void rejectInvite(RequestStatus requestStatus)
	{
		int saveStatus;
		RequestStatusDM saveRequestStatusDm = new RequestStatusDM();
		saveStatus = saveRequestStatusDm.acceptInvite(requestStatus);
		if(saveStatus == TeamQuestConstants.failure_key)
		{
			CommonViewClass.showdialog(mContext, TeamQuestConstants.saveRequestStatusFailure_key);
		}else{
			boolean isEmptyresponse = true;
			
			requestStatusChildMap.remove(requestStatus.getUniqueId());
			
			for(RequestStatus rs : requestStatusChildMap.values())
			{
				if(rs.getUniqueIdLong() == requestStatus.getUniqueIdLong())
				{
					isEmptyresponse = false;
					break;
				}
			}
			
			if(isEmptyresponse)
			{
				RequestStatus requestStatusChild = new RequestStatus();
				requestStatusChild.setTeamId(null);
				requestStatusChild.setUniqueId(TeamQuestConstants.emptyUniqueId_key+requestStatus.getUniqueIdLong());
				requestStatusChild.setUniqueIdLong(requestStatus.getUniqueIdLong());
				requestStatusChild.setParent(false);
				requestStatusChild.setParentInviteId(requestStatusMap.get(requestStatus.getUniqueIdLong()).getUniqueId());	
				List<Players> playersListChild = new ArrayList<Players>();
						
				requestStatusChild.setPlayersList(playersListChild);
				requestStatusChildMap.put(requestStatusChild.getUniqueId(), requestStatusChild);
			}
			filterRequset(lastSelectedId);
			Toast.makeText(mContext, "you have rejected the invite from team " + requestStatus.getTeamName() , 1500).show();
		}
	}
	
	private void acceptInvite(RequestStatus requestStatus)
	{
		int saveStatus;
		RequestStatusDM saveRequestStatusDm = new RequestStatusDM();
		saveStatus = saveRequestStatusDm.acceptInvite(requestStatus);
		if(saveStatus == TeamQuestConstants.failure_key)
		{
			CommonViewClass.showdialog(mContext, TeamQuestConstants.saveRequestStatusFailure_key);
		}else{
			Toast.makeText(mContext, "you have accepted the invite from team " + requestStatus.getTeamName() , 1500).show();
			activityObject.getFrament().getNetworkChangeReceiver().updateData();
		}
	}
	
	private void closeInvite(RequestStatus requestStatus)
	{
		int saveStatus;
		RequestStatusDM saveRequestStatusDm = new RequestStatusDM();
		saveStatus = saveRequestStatusDm.closeInvite(requestStatus);
		if(saveStatus == TeamQuestConstants.failure_key)
		{
			CommonViewClass.showdialog(mContext, TeamQuestConstants.saveRequestStatusFailure_key);
		}else{
//			for(Iterator<RequestStatus> iterator = requestStatusChildList.iterator(); iterator.hasNext();)
//			{
//				RequestStatus rs = iterator.next();
//				if(rs.getUniqueIdLong() == requestStatus.getUniqueIdLong())
//				{
//					iterator.remove();
//				}
//			}
			
			for(Iterator<Map.Entry<String, RequestStatus>> it = requestStatusChildMap.entrySet().iterator(); it.hasNext(); ) {
			      Map.Entry<String, RequestStatus> entry = it.next();
			      if(entry.getValue().getUniqueIdLong() == requestStatus.getUniqueIdLong()) {
			        it.remove();
			      }
			  }
			
			requestStatusMap.remove(requestStatus.getUniqueIdLong());
			
			filterRequset(lastSelectedId);
			Toast.makeText(mContext, "Selected invite has be closed !!", 1500).show();
		}
	}
	
	private void cancelRequest(RequestStatus requestStatus)
	{
		int saveStatus;
		RequestStatusDM saveRequestStatusDm = new RequestStatusDM();
		saveStatus = saveRequestStatusDm.cancelRequest(requestStatus);
		if(saveStatus == TeamQuestConstants.failure_key)
		{
			CommonViewClass.showdialog(mContext, TeamQuestConstants.saveRequestStatusFailure_key);
		}else{
			boolean isEmptyresponse = true;
			
			requestStatusChildMap.remove(requestStatus.getUniqueId());
			
			for(RequestStatus rs : requestStatusChildMap.values())
			{
				if(rs.getUniqueIdLong() == requestStatus.getUniqueIdLong())
				{
					isEmptyresponse = false;
					break;
				}
			}
			
			if(isEmptyresponse)
			{
				RequestStatus requestStatusChild = new RequestStatus();
				requestStatusChild.setTeamId(null);
				requestStatusChild.setUniqueId(TeamQuestConstants.emptyUniqueId_key+requestStatus.getUniqueIdLong());
				requestStatusChild.setUniqueIdLong(requestStatus.getUniqueIdLong());
				requestStatusChild.setParent(false);
				requestStatusChild.setParentInviteId(requestStatusMap.get(requestStatus.getUniqueIdLong()).getUniqueId());	
				List<Players> playersListChild = new ArrayList<Players>();
						
				requestStatusChild.setPlayersList(playersListChild);
				requestStatusChildMap.put(requestStatusChild.getUniqueId(), requestStatusChild);
			}
			filterRequset(lastSelectedId);
			
			Toast.makeText(mContext, "Request to " + requestStatus.getTeamName() + " has been cancelled", 1500).show();
		}
		
	}
	
	public void filterRequset(int id)
	{
		if(lastSelectedId == id)
		{
			return;
		}
		lastSelectedId = id;
		requestStatusChildList.clear();
		switch (id)
		{
		case R.id.all_request:
			requestStatusChildList.addAll(requestStatusChildMap.values());
			break;
		case R.id.sent_request:
			for(RequestStatus requestStatus : requestStatusChildMap.values())
			{
				if(requestStatus.isRequestSent())
				{
					requestStatusChildList.add(requestStatus);
				}
			}
			break;
		case R.id.received_request:
			
			for(RequestStatus requestStatus : requestStatusChildMap.values())
			{
				if(!requestStatus.isRequestSent())
				{
					requestStatusChildList.add(requestStatus);
				}
			}
			break;
		}	
		notifyDataSetChanged();
	}

	public List<RequestStatus> getRequestStatusChildList() {
		return requestStatusChildList;
	}

	public void setRequestStatusChildList(List<RequestStatus> requestStatusChildList) {
		this.requestStatusChildList = requestStatusChildList;
		requestStatusChildMap.clear();
		for(RequestStatus requestStatus : this.requestStatusChildList)
		{
			requestStatusChildMap.put(requestStatus.getUniqueId(), requestStatus);
		}
	}

	public Map<Long, RequestStatus> getRequestStatusMap() {
		return requestStatusMap;
	}

	public void setRequestStatusMap(Map<Long, RequestStatus> requestStatusMap) {
		this.requestStatusMap = requestStatusMap;
	}
}