package com.example.matchquest.DataManipulation.FindOpponent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.example.matchquest.model.RequestStatus;
import com.example.matchquest.model.Team;

public class FindOpponentDM {

	public List<RequestStatus> searchOpponent(String loc,Date date, String nop,Team team)
	{
		
		List<RequestStatus> requestStatusList = new ArrayList<RequestStatus>();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		if(Integer.parseInt(nop) == 10)
		{
			return requestStatusList;
		}
		for(int i = 0 ; i < Integer.parseInt(nop) ; i++)
		{
			cal.add(Calendar.DAY_OF_MONTH, i);
			RequestStatus requestStatus = new RequestStatus();
			requestStatus.setTeamId("opp id " + i);
			requestStatus.setTeamName("opp name " + i);
			requestStatus.setTeamCode("opp code " + i);
			requestStatus.setDate(cal.getTime());
			requestStatus.setLocation("loc opp "+i);
			requestStatus.setTime("10 :00 AM");
			requestStatus.setNop(i %2 == 0 ? nop : Integer.parseInt(nop)-1 + "");
			requestStatus.setUniqueId("inviteId " + i);
			
			requestStatusList.add(requestStatus);
		}
		return requestStatusList;
	}
	
	public int saveInvite(RequestStatus searchStatus,RequestStatus opponentStatus)
	{
		if(!searchStatus.isInviteSaved())
		{
//			insert this searchStatus only inside condition 
		}
//		save opponent status and update each other invite id some attribute
		return 1;
	}
	
	public int postInvite(List<RequestStatus> statusList)
	{
		if(statusList.get(statusList.size()-1).isInviteSaved())
		{
//			save in DB 
//			which means no invite has been made for this search so post invite so that other can invite 
		}
		
	
		
	for(int i = 0 ; i < statusList.size()-1 ;i ++)
	{
		if(statusList.get(i).isInviteSaved())
		{
//			just update a attribute which denotes that this invite should not get any more request from other teams
		}
	
	}
		return 1;
	}
	
	public int updateStatus(List<RequestStatus> statusList)
	{
		for(int i = 0 ; i < statusList.size()-1 ;i ++)
		{
			if(statusList.get(i).isInviteSaved())
			{
//				just update a attribute which denotes that this invite should not get any more request from other teams
			}
		
		}
		return 1;
	}
}
