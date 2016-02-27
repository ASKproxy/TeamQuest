package com.example.matchquest.View.teams;

import java.util.List;

import com.example.matchquest.R;
import com.example.matchquest.DataManipulation.Teams.TeamEditDM;
import com.example.matchquest.SQLiteData.TeamQuestSqlite;
import com.example.matchquest.View.CommonViewClass;
import com.example.matchquest.model.Team;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.PopupWindow.OnDismissListener;

public class TeamNamePopUp {

	ActionBarActivity context;
	Team team;
	EditText teamName;
	TextView teamNameCount;
	Button close;
	Button done;
	String totalCount;
	PopupWindow teamNamePopup;
	
	public void showPopup(ActionBarActivity mcontext,Team t) 
	{
		this.context = mcontext;
		this.team = t;
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.team_name_popup,(ViewGroup) context.findViewById(R.id.popup_element));
		totalCount = context.getString(R.string.team_name_count);
		
		
		teamName = (EditText) layout.findViewById(R.id.teamName);
		teamName.setText(team.getTeamName());
		teamName.setSelection(0, teamName.getText().toString().length() );
		teamNameCount = (TextView) layout.findViewById(R.id.team_name_count);
		teamNameCount.setText("(" + teamName.getText().toString().length() + "/" + totalCount + ")");
		
		Display display = context.getWindowManager().getDefaultDisplay();
		double width = display.getWidth();
		double height = display.getHeight();
		
		int heightSize = (int) ((height/3)*1);
		int widthSize = (int) ((width/5)*4);
		
		teamNamePopup = new PopupWindow(context);
		teamNamePopup.setContentView(layout);
		teamNamePopup.setFocusable(true);
        teamNamePopup.setHeight(heightSize);
        teamNamePopup.setWidth(widthSize);
        
        teamNamePopup.showAtLocation(layout, Gravity.CENTER, 0, -(heightSize/2));
        teamNamePopup.setOnDismissListener(new OnDismissListener() {
			
			@Override
			public void onDismiss() {
				
				((TeamEditActivity)context).reGainLayout();
				
			}
		});

	       // Getting a reference to Close button, and close the popup when clicked.
	       Button close = (Button) layout.findViewById(R.id.close_popup);
	       close.setOnClickListener(new OnClickListener() {

	         @Override
	         public void onClick(View v) {
	        	 teamNamePopup.dismiss();
	         }
	       });
	       
	       Button done = (Button) layout.findViewById(R.id.team_name_done);
	       done.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(!CommonViewClass.isNetworkAvailable(context))
				{
					CommonViewClass.showdialog(context, "Please ,Connect to internet to change Team Name");
					return;
				}
				if(teamName.getText()!= null && !teamName.getText().toString().equals(""))
				{
					teamNamePopup.dismiss();
					TeamEditDM teamEdit = new TeamEditDM();
					
					TeamQuestSqlite teamlist = new TeamQuestSqlite(context);
					String name = teamEdit.updateTeamName(teamName.getText().toString().trim());
					teamlist.getTeamListSqlite().updateTeamName(team,name );
					((TeamEditActivity)context).updateTeamName(name);
				}else{
					Toast.makeText(context, "Team Name cannot be empty", 1000).show();
				}
				
			}
		});
	       
	    teamName.addTextChangedListener(new TextWatcher() {
				
				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {
					teamNameCount.setText("(" + teamName.getText().toString().length() + "/" + totalCount + ")");					
				}

				@Override
				public void beforeTextChanged(CharSequence s, int start, int count,
						int after) {
					
				}

				@Override
				public void afterTextChanged(Editable s) {
					
				}
				
			});
	}
}
