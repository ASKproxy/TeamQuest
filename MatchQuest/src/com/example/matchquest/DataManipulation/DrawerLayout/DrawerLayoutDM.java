package com.example.matchquest.DataManipulation.DrawerLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.example.matchquest.model.MatchStatus;


public class DrawerLayoutDM {

	Set<String> teamSet = new LinkedHashSet<String>();
	Set<String> dateStringSet = new LinkedHashSet<String>(); 
	public List<MatchStatus> getMatchStatus()
	{
		List<MatchStatus> matchStausData = new ArrayList<MatchStatus>();
		Calendar cal = Calendar.getInstance();
		teamSet.add("All (team)");
		dateStringSet.add("All (date)");
		for(int i = 0 ; i < 4 ; i ++)
		{
			MatchStatus matchStatus = new MatchStatus();
			matchStatus.setOpponentTeam("opponent " + i);
			matchStatus.setOpponentTeamId("opponent id " + i);
			matchStatus.setOpponentCode("4321 " + i);
			matchStatus.setYourTeamName("teamName " + i);
			matchStatus.setYourTeamId("teamName id " + i);
			matchStatus.setYourTeamCode("1234");
			matchStatus.setMatchStatusId("matchStatus id " + i);
			matchStatus.setLocation("location " + i);
			matchStatus.setDate(cal.getTime());
			matchStatus.setTime("7:00 am");
			matchStausData.add(matchStatus);
			teamSet.add(matchStatus.getYourTeamName() + " (" + matchStatus.getYourTeamCode() + ")");
			dateStringSet.add(matchStatus.getDateString());
		}
		cal.add(Calendar.DATE, 1);

		for(int i = 0 ; i < 4 ; i ++)
		{
			MatchStatus matchStatus = new MatchStatus();
			matchStatus.setOpponentTeam("opponent " + i);
			matchStatus.setOpponentTeamId("opponent id " + i);
			matchStatus.setYourTeamName("teamName " + i);
			matchStatus.setYourTeamId("teamName id " + i);
			matchStatus.setYourTeamCode("1234");
			matchStatus.setOpponentCode("4321 " + i);
			matchStatus.setMatchStatusId("matchStatus id " + i);
			matchStatus.setLocation("location 22" + i);
			matchStatus.setDate(cal.getTime());
			matchStatus.setTime("7 :30 am");
			matchStausData.add(matchStatus);
			
			teamSet.add(matchStatus.getYourTeamName() + " (" + matchStatus.getYourTeamCode() + ")");
			dateStringSet.add(matchStatus.getDateString());
		}
		
		cal.add(Calendar.DATE, 4);

		for(int i = 0 ; i < 4 ; i ++)
		{
			MatchStatus matchStatus = new MatchStatus();
			matchStatus.setOpponentTeam("opponent " + i);
			matchStatus.setOpponentTeamId("opponent id " + i);
			matchStatus.setYourTeamName("teamName " + i);
			matchStatus.setYourTeamId("teamName id " + i);
			matchStatus.setMatchStatusId("matchStatus id " + i);
			matchStatus.setLocation("location 44" + i);
			matchStatus.setYourTeamCode("1234");
			matchStatus.setOpponentCode("4321 " + i);
			matchStatus.setDate(cal.getTime());
			matchStatus.setTime("9 :30 am");
			matchStausData.add(matchStatus);
			
			teamSet.add(matchStatus.getYourTeamName() + " (" + matchStatus.getYourTeamCode() + ")");
			dateStringSet.add(matchStatus.getDateString());
		}
		return matchStausData;
		
	}
	public Set<String> getTeamSet() {
		return teamSet;
	}
	public void setTeamSet(Set<String> teamSet) {
		this.teamSet = teamSet;
	}
	public Set<String> getDateStringSet() {
		return dateStringSet;
	}
	public void setDateStringSet(Set<String> dateStringSet) {
		this.dateStringSet = dateStringSet;
	}
	
	
}
