package com.example.materialsetup;

public class DrawerTexts {
	
	String teamName ;
	String loc;
	String date;
	String nop;
	
	public DrawerTexts(String teamName, String loc, String date, String nop)
	{
		setTeamName(teamName);
		setLoc(loc);
		setDate(date);
		setNop(nop);
	}
	
	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getLoc() {
		return loc;
	}
	public void setLoc(String loc) {
		this.loc = loc;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getNop() {
		return nop;
	}
	public void setNop(String nop) {
		this.nop = nop;
	}
	
	

}
