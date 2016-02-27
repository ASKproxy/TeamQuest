package com.example.matchquest.DataManipulation.MatchSchedule;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.matchquest.model.Players;
import com.example.matchquest.model.RequestStatus;
import com.example.matchquest.model.Team;

public class MatchScheduleDM {

	public List<RequestStatus> getMatchSchedule(Team team)
	{
		Calendar cal = Calendar.getInstance();
		
		List<RequestStatus> matchScheduleList = new ArrayList<RequestStatus>();
		for(int j = 0 ; j < 9; j ++)
		{
			RequestStatus requestStatusChild = new RequestStatus();
			requestStatusChild.setTeamId("opp id" + " " + j);
			requestStatusChild.setTeamName("opp team Name" + " " + j);
			requestStatusChild.setTeamCode("opp team code"+ " " + j);
			requestStatusChild.setUniqueId("opp inviteId" + " " + j);
			requestStatusChild.setDate(cal.getTime());
			requestStatusChild.setTime("7 : 00 am");
			requestStatusChild.setLocation("santhome, adyar"  + j);
			
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
			requestStatusChild.setNop(5+j + "");
			
			Map<String,Players> selectedPlayersMap = new HashMap<String, Players>();
			if(j > 4)
			{
			for(int k = 0 ; k < Integer.parseInt(requestStatusChild.getNop()) ; k ++)
			{
				Players player = new Players();
				player.setPlayerId("955159116" + k);
				player.setPlayerName("955159116" + k);
				player.setRegistered(k<(Integer.parseInt(requestStatusChild.getNop())/2)  ? true : false);
				selectedPlayersMap.put(player.getPlayerId(), player);
			}
			}
			requestStatusChild.setSelectedPlayersMap(selectedPlayersMap);
			
			
			matchScheduleList.add(requestStatusChild);
		}
		
		return matchScheduleList;
	}
	
	public void savePlayerOpinion(RequestStatus status, String playerId,boolean toRemove)
	{
//			this update is done on match detail collection
		//if toremove is false then add the player id in list
		
	}
}
