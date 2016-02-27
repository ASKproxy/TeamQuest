package com.example.matchquest.View;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract  class CommonFragment extends Fragment{
	
	NetworkChangeReceiver networkChangeReceiver;
		
	protected void registernetworkListener(boolean toShowProgressBarONPreExecute){
		IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
		networkChangeReceiver = new NetworkChangeReceiver(this,toShowProgressBarONPreExecute);
		getActivity().registerReceiver(networkChangeReceiver, filter);
	}
	
	public void reGainLayout()
	{
		
	}
	
	public void onPopUpOpen()
	{
		
	}
	protected abstract void loadInBackGround();

	public abstract void updateView();

	protected void preExecute()
	{
		
	}
	  @Override
	public void onDestroy() {		
		
		if(networkChangeReceiver != null)
		{
			
			if(getActivity() != null)
			{
			getActivity().unregisterReceiver(networkChangeReceiver);
			}
			networkChangeReceiver = null;
		}
		
		super.onDestroy();
	}

	public NetworkChangeReceiver getNetworkChangeReceiver() {
		return networkChangeReceiver;
	}

	public void setNetworkChangeReceiver(NetworkChangeReceiver networkChangeReceiver) {
		this.networkChangeReceiver = networkChangeReceiver;
	}
	
	 public ActionBarActivity getActivityObject()
	  {
		  return (ActionBarActivity) getActivity();
	  }
	 
	 public void reload()
	 {
		 new RemoteDataTask().execute();
	 }
	 
	 private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				if(!isCancelled()){
					
					preExecute();
				}
			}
	 
			@Override
			protected Void doInBackground(Void... params) {
				if(!isCancelled())
				{
				loadInBackGround();
				}
				return null;
			}
	 
			@Override
			protected void onPostExecute(Void result) {
				if(!isCancelled())
				{
					
					updateView();
				}
				
			}
		}
}
