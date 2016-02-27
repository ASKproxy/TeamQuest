package com.example.matchquest.View.teams;

import java.util.ArrayList;
import java.util.List;

import com.example.matchquest.R;
import com.example.matchquest.DataManipulation.Teams.TeamEditDM;
import com.example.matchquest.DataManipulation.Teams.TeamListDM;
import com.example.matchquest.SQLiteData.TeamQuestSqlite;
import com.example.matchquest.SQLiteData.Teams.TeamListSqlite;
import com.example.matchquest.View.CommonFragment;
import com.example.matchquest.View.CommonViewClass;
import com.example.matchquest.View.NetworkChangeReceiver;
import com.example.matchquest.common.Details;
import com.example.matchquest.model.Team;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.opengl.Visibility;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;





public class TeamListFragment extends CommonFragment implements View.OnClickListener {

	
	// Declare Variables
	View rootView;
	RecyclerView recyclerView;
	TextView emptyText;
	ProgressDialog mProgressDialog;
	TeamAdapter adapter;
	List<String> teamId;
	String playerId;
	List<Team> teamData;
	TeamQuestSqlite teamListSqlite ;
	NetworkChangeReceiver networkChangeReceiver;
	
	boolean initialLoad = false;
	
	public TeamListFragment() {
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);

	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		playerId = getArguments().getString("playerNo");
		rootView = inflater.inflate(R.layout.fragment_team_list, container, false);
		emptyText = (TextView) rootView.findViewById(R.id.empty_view);
		recyclerView = (RecyclerView) rootView.findViewById(R.id.drawerList);
			registerForContextMenu(recyclerView);

		 ((ActionBarActivity)getActivity()).getSupportActionBar().setDisplayShowCustomEnabled(false);
		teamData = new ArrayList<Team>();
		teamListSqlite = new  TeamQuestSqlite(getActivity());
		teamData.addAll(teamListSqlite.getTeamListSqlite().getAllUsers());
		recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
			// Pass the results into an ArrayAdapter
			adapter = new TeamAdapter(getActivity().getApplicationContext(), 0, 0, teamData,recyclerView,this);
			recyclerView.setAdapter(adapter);
	

		registernetworkListener(false);
		
		initialLoad = true;
		return rootView;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(!initialLoad)
		{
			if(getNetworkChangeReceiver()!= null)
			{
			getNetworkChangeReceiver().updateData();
			}
			
		}
		initialLoad = false;
	}
	@Override
	public void onClick(View v)
	{

		switch(v.getId())
		{
		
		}

	}
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		
		 super.onCreateOptionsMenu(menu, inflater);
		
	}
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
	  	 MenuInflater inflater = getActivity().getMenuInflater();
         inflater.inflate(R.menu.longpress_menu, menu);
       
	  	 

	}
	 
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id){
			
			case R.id.team_info:
//				deRegisterNetworkReceiver();
				Intent teamEditIntent = new Intent(getActivity(), TeamEditActivity.class);
				teamEditIntent.putExtra("Team", adapter.getLongPressTeam());  
				startActivity(teamEditIntent);
				break;
			case R.id.delete_team : 
				deleteFromTeam(adapter.getLongPressTeam(), Details.getPlayer(getActivityObject()).getPlayerId());
				getNetworkChangeReceiver().updateData();
				break;	
		}
		return true;
	}
	

	public void deleteFromTeam(Team team, String playerId)
	{
		if(!CommonViewClass.isNetworkAvailable(getActivity()))
		{
			CommonViewClass.showdialog(getActivity(), "Please ,Connect to internet !!");
			return;
		}
		TeamEditDM teamEdit = new TeamEditDM();
		
		int result = teamEdit.removeFromTeam(team, playerId);
		if(result == 1)
		{
		if(networkChangeReceiver != null)
		{
			networkChangeReceiver.updateData();
		}
		
		}
	}
	
		@Override
		protected void loadInBackGround() {
			TeamListDM teamListDm = new TeamListDM();
			teamData.clear();
			
			 teamData.addAll(teamListDm.getTeamData());
			 teamListSqlite.getTeamListSqlite().insertTeams(teamData);
			
		}

		@Override
		public void updateView() {
			if(teamData != null && teamData.size() >0 )
			{
			teamId = new ArrayList<String>();
			
			adapter.notifyDataSetChanged();


			}else{
				recyclerView.setVisibility(View.GONE);
				emptyText.setVisibility(View.VISIBLE);
			}
			
		}
	
		
}