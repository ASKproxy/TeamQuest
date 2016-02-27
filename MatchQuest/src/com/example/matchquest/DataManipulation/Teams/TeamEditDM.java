package com.example.matchquest.DataManipulation.Teams;

import java.util.ArrayList;
import java.util.List;

import com.example.matchquest.model.Contacts;
import com.example.matchquest.model.Players;
import com.example.matchquest.model.Team;



public class TeamEditDM {

	public List<Players> getPLayerDetails(Team team)
	{
		List<Players> playerList = new ArrayList<Players>();
		for (int i = 0 ; i < 19; i ++)
		{
			Players player = new Players();
			player.setPlayerId("955159116" + i);
			player.setPlayerName("playerName" + i);
			player.setRegistered((i>5)?false : true);
			playerList.add(player);
		}
		return playerList;
	}
	
	public List<Players> addTeamMembers(Team team,List<Contacts> contacts)
	{
		List<Players> playersList = new ArrayList<Players>();
		for (int i = 0 ; i < contacts.size(); i ++)
		{
			Players player = new Players();
			player.setPlayerId(contacts.get(i).getNumber());
			player.setPlayerName(contacts.get(i).getName());
			player.setRegistered((i>5)?false : true);
			playersList.add(player);
		}
		return playersList;
	}
	
	public String updateTeamName(String name)
	{
		return name;
	}
	
	public int removeFromTeam(Team team , String playerId)
	{
		return 1;
	}
	public int makeViceCaptain(Team team , String playerId)
	{
		return 1;
	}
	public int makeCaptain(Team team , String playerId)
	{
		return 1;
	}
	
	public Team getTeamDetails(Team team )
	{
		return team;
	}
}
