package com.example.matchquest.DataManipulation.RequestStatus;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.example.matchquest.R;
import com.example.matchquest.common.TeamQuestConstants;
import com.example.matchquest.model.Players;
import com.example.matchquest.model.RequestStatus;
import com.example.matchquest.model.Team;

public class RequestStatusDM {
	
	List<RequestStatus> requestStatusChildList = new ArrayList<RequestStatus>();
	
	
	public Map<Long,RequestStatus> getRequestStatusDetails(Team team)
	{
		
		Calendar cal = Calendar.getInstance();
		Map<Long,RequestStatus> requestStatusMap = new LinkedHashMap<Long, RequestStatus>();
		
		for(int i = 0 ; i < 4 ; i ++)
		{
			cal.add(Calendar.DATE, 2);
				long uniqueIdLong = 111L +i;
				RequestStatus requestStatusParent = new RequestStatus();
			
				requestStatusParent.setTeamId(team.getTeamId());
				requestStatusParent.setTeamName(team.getTeamName());
				requestStatusParent.setTeamCode(team.getTeamCode());
				requestStatusParent.setUniqueId("home inviteId" + i);
				requestStatusParent.setUniqueIdLong(uniqueIdLong);
				requestStatusParent.setParent(true);
				requestStatusParent.setDate(cal.getTime());
				requestStatusParent.setTime("7 : 00 am");
				
				requestStatusParent.setLocation("santhome, adyar");
				requestStatusParent.setParentInviteId(requestStatusParent.getUniqueId());
				
				List<Players> playersList = new ArrayList<Players>();
				for(int k = i ; k < 4 ; k++)
				{
					Players player = new Players();
					player.setPlayerId("955159116" + k);
					player.setPlayerName("955159116" + k);
					player.setRegistered(k%2 == 0 ? true : false);
					playersList.add(player);
				}
				requestStatusParent.setPlayersList(playersList);
				requestStatusParent.setNop(requestStatusParent.getPlayersList().size() + "");
				
				requestStatusMap.put(requestStatusParent.getUniqueIdLong(), requestStatusParent);
				
				if(i == 2)
				{
					RequestStatus requestStatusChild = new RequestStatus();
					requestStatusChild.setTeamId(null);
					requestStatusChild.setUniqueId(TeamQuestConstants.emptyUniqueId_key+uniqueIdLong);
					requestStatusChild.setUniqueIdLong(uniqueIdLong);
					requestStatusChild.setParent(false);
					requestStatusChild.setParentInviteId(requestStatusParent.getUniqueId());	
					List<Players> playersListChild = new ArrayList<Players>();
							
					requestStatusChild.setPlayersList(playersListChild);
					
					requestStatusChildList.add(requestStatusChild);
				}else{
				for(int j = 0 ; j < 4; j ++)
				{
					RequestStatus requestStatusChild = new RequestStatus();
					requestStatusChild.setTeamId("opp id" + i + " " + j);
					requestStatusChild.setTeamName("opp team Name" + i + " " + j);
					requestStatusChild.setTeamCode("opp team code" + i + " " + j);
					requestStatusChild.setUniqueId("opp inviteId" + i + " " + j);
					requestStatusChild.setUniqueIdLong(uniqueIdLong);
					requestStatusChild.setParent(false);
					requestStatusChild.setRequestSent(i>2 ? true : false);
					requestStatusChild.setDate(cal.getTime());
					requestStatusChild.setTime("7 : 00 am");
					
					requestStatusChild.setLocation("santhome, adyar" + i + " " + j);
					requestStatusChild.setParentInviteId(requestStatusParent.getUniqueId());	
					List<Players> playersListChild = new ArrayList<Players>();
							for(int k = j ; k < 8 ; k++)
							{
								Players player = new Players();
								player.setPlayerId("955159116" + k);
								player.setPlayerName("955159116" + k);
								player.setRegistered(k%2 == 0 ? true : false);
								playersListChild.add(player);
							}
					requestStatusChild.setPlayersList(playersListChild);
					requestStatusChild.setNop(requestStatusChild.getPlayersList().size()+"");
					
					requestStatusChildList.add(requestStatusChild);
				}
				}
			
				
		}
		
		return requestStatusMap;
	}

	public List<RequestStatus> getRequestStatusChildList()
	{
		return requestStatusChildList;
	}
	
	public void savePlayerOpinion(RequestStatus status, String playerId,boolean toRemove)
	{
		//ifchildinviteId is empty then change have been made on parent invite , so upadte for parent invite id
		
		//id child inviteid is not null then change made on child sp update on subdocument
		
		//if toremove is false then add the player id in list
		
	}
	
	public int saveInviteStatus(int id , RequestStatus status)
	{
		int saveStatus = 0;
		
		switch (id) {
		case R.id.request_status_cancel_request:
			saveStatus = cancelRequest(status);
				break;
				
		case R.id.request_status_close_invite:
			saveStatus = closeInvite(status);
			break;
			
		case R.id.request_status_accept_invite:
			saveStatus = acceptInvite(status);
			break;
			
		case R.id.request_status_reject_invite:
			saveStatus = rejectInvite(status);
			break;
		}
		
		return saveStatus;
	}
	
	public int acceptInvite(RequestStatus status)
	{
//		use parent invite id , unique id ,isrequestsent from status 
//		to get their data from collection and use then to insert into matchdetail collection 
//		delete their invites 
		
//		by the time we update if someone else updates that invite then we give status as failue (-1)
//		so display messageing saying that invites no longer existes and reload the screen
		
		return TeamQuestConstants.success_key;
	}
	
	public int rejectInvite(RequestStatus status)
	{
//		use the unique id, isrequestsent to remove this uniqueid from list 
//		if it is already removed then no problem anyway
//		so mostly we wont get any failure in this step
		
		return TeamQuestConstants.success_key;
	}
	
	public int closeInvite(RequestStatus status) 
	{
//		use the uniqueid to remove the invite itself and remove this invite id in other invite too
		
//		if this invite id is not present that means someone else has already accpeted the invite 
//		so dispaly message saying a match has already been setup for this match
		return TeamQuestConstants.success_key;
	}

	public int cancelRequest(RequestStatus status)
	{
//		use uniqueid, parentuniqueid and isrequestsent for removing the request
//		remove parent uniqueid from list of invite with uniqueid
		
		
		return TeamQuestConstants.success_key;
	}
}
