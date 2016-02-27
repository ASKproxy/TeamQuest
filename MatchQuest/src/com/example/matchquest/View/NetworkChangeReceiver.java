package com.example.matchquest.View;


import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

public class NetworkChangeReceiver extends BroadcastReceiver {

	CommonFragment fragmentObject;
	ProgressDialog mProgressDialog;
	boolean toShowProgressBarOnPreExecute = false;
	
	RemoteDataTask remoteDataTask;
	
	public NetworkChangeReceiver(CommonFragment frament,boolean toShow)
	{
		this.fragmentObject = frament;
		this.toShowProgressBarOnPreExecute = toShow;
	}
	  @Override
	  public void onReceive(final Context context, final Intent intent) {

		  if(CommonViewClass.isNetworkAvailable(context))
		  {
			  Toast.makeText(context, "connected", 1000).show();
			  remoteDataTask  = new RemoteDataTask();
			  remoteDataTask.execute();
			 
		  }else{
				Toast.makeText(context, "Connectivity Lost ! connect to internet to get latest update", 1000).show();;
		  }
	  }
	  
	  public void updateData()
	  {
		  if(CommonViewClass.isNetworkAvailable(fragmentObject.getActivityObject().getApplicationContext()))
		  {
			 remoteDataTask  = new RemoteDataTask();
			 remoteDataTask.execute();
		  }
		 
	  }
	  
	// RemoteDataTask AsyncTask
			public class RemoteDataTask extends AsyncTask<Void, Void, Void> {
				@Override
				protected void onPreExecute() {
					super.onPreExecute();
					
					if(!isCancelled())
					{
					if(toShowProgressBarOnPreExecute)
					{
					mProgressDialog = new ProgressDialog(fragmentObject.getActivity());
					mProgressDialog.setMessage("Loading...");
					mProgressDialog.setIndeterminate(true);
					mProgressDialog.setCancelable(false);
					mProgressDialog.show();
					}
					}
				}
		 
				@Override
				protected Void doInBackground(Void... params) {
					if(!isCancelled())
					{
					fragmentObject.loadInBackGround();
					}
					return null;
				}
		 
				@Override
				protected void onPostExecute(Void result) {
					if(!isCancelled())
					{
						if(!toShowProgressBarOnPreExecute)
						{
						mProgressDialog = new ProgressDialog(fragmentObject.getActivity());
						mProgressDialog.setMessage("Loading...");
						mProgressDialog.setIndeterminate(true);
						mProgressDialog.setCancelable(false);
						mProgressDialog.show();
						}
						fragmentObject.updateView();
						mProgressDialog.dismiss();
					}
				}
			}

			public RemoteDataTask getRemoteDataTask() {
				return remoteDataTask;
			}
			
}


