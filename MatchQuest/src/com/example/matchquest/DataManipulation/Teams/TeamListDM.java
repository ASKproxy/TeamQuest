package com.example.matchquest.DataManipulation.Teams;

import java.util.ArrayList;
import java.util.List;

import com.example.matchquest.model.Team;


public class TeamListDM {

	public List<Team> getTeamData() 
	{
		List<Team> teamData = new ArrayList<Team>();
		
		for(int i = 1 ;i < 20 ; i ++)
		{
			Team team = new Team();
			team.setTeamId("teamId" + i);
			team.setTeamName("teamName " + i);
			List<String> players = new ArrayList<String>();
			for(int j = i; j < 20 ; j++)
			{
				players.add("players " + j );
			}
			TeamEditDM playersList = new TeamEditDM();
			team.setPlayersList(playersList.getPLayerDetails(team));
			team.setNumPlayers(team.getPlayersList().size());
			team.setCaptain("95515911"+i+"0");
			team.setViceCaptain("vice captain" + i);
			team.setTeamCode("1234");
			teamData.add(team);
		}
		return teamData;
	}
}
