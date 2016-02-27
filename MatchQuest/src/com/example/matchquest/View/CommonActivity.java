package com.example.matchquest.View;

import com.example.matchquest.common.TeamQuestConstants;

import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.support.v7.app.ActionBarActivity;

public abstract class CommonActivity  extends ActionBarActivity{
	
	NetworkChangeReceiverActvity networkChangeReceiverActvity;
	
	
	protected void registernetworkListener(boolean toShowProgressBarOnPreExecute){
		IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
		networkChangeReceiverActvity = new NetworkChangeReceiverActvity(this,toShowProgressBarOnPreExecute);
		registerReceiver(networkChangeReceiverActvity, filter);
	}
	
	protected abstract void loadInBackGround();

	protected abstract void updateView();

	public NetworkChangeReceiverActvity getNetworkChangeReceiverActvity() {
		return networkChangeReceiverActvity;
	}
	
	  @Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(networkChangeReceiverActvity != null)
		{
			unregisterReceiver(networkChangeReceiverActvity);
			networkChangeReceiverActvity = null;
		}
	}
	  
	  public CommonActivity getActivityObject()
	  {
		  return this;
	  }
}
