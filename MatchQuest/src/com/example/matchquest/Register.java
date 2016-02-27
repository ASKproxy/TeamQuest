package com.example.matchquest;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends Activity{
	
	Button create;
	EditText nameText; 
	EditText noText;
	String name;
	String no;
	boolean save = true;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.register);
		
		final SharedPreferences prefs = getSharedPreferences("TeamQuest", MODE_PRIVATE);
		nameText = (EditText) findViewById(R.id.name_register);
		noText = (EditText) findViewById(R.id.no_register);
		create = (Button) findViewById(R.id.create_register);
		
		create.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(nameText.getText().toString() == null && nameText.getText().toString().trim().equals(""))
				{
					Toast.makeText(getApplicationContext(), "Please enter a name", 2000).show();
					save = false;
				}
				else{
					name = nameText.getText().toString().trim();
				}
				if(noText.getText().toString() == null && noText.getText().toString().trim().equals(""))
				{
					Toast.makeText(getApplicationContext(), "Please enter a no", 2000).show();
					save = false;
				}
				else{
					
					no = noText.getText().toString().trim();
					if(no.length() != 10)
					{
						Toast.makeText(getApplicationContext(), "Please enter a proper no", 2000).show();
						save = false;
					}
					
				}
				
				if(save)
				{
				SharedPreferences.Editor editor = prefs.edit();
				editor.putString("PlayerNo", no);
				editor.putString("PlayerName", name);
				editor.commit();
                 new RegisterPlayer().execute();
                 Intent data = new Intent(getApplicationContext(),MainActivity.class);
                 startActivity(data);
				}
				save = true;
				
			}
		});
		
		
	}
	
	private class RegisterPlayer extends AsyncTask<Void, Void, Void>{

	@Override
		protected Void doInBackground(Void... params) {
		
//		 ParseQuery<ParseObject> query = ParseQuery.getQuery("Player");
//		 query.whereEqualTo("playerNo",no);
//		 query.findInBackground(new FindCallback<ParseObject>() {
//			
//			@Override
//			public void done(List<ParseObject> players, ParseException arg1) {
//				try{
//				if(players != null && players.size() > 0 )
//				{
//					if(!players.get(0).getBoolean("isRegistered")){
//
//						players.get(0).put("playerName", name);
//						players.get(0).put("isRegistered", true);
//						players.get(0).saveInBackground();
//
//					
//					}
//				}else{
//					Player player = new Player();
//					player.setPlayerName(name);
//					player.setPlayerNo(no);
//					player.setRegistered(true);
//					player.save();
//					
//				}
//				}
//				catch(Exception e)
//				{
//					e.printStackTrace();
//				}
//				
//			}
//		});
			return null;
		}
	}
}
