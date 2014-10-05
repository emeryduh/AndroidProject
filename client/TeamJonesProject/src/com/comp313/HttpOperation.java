package com.comp313;
import android.os.AsyncTask;


public class HttpOperation extends AsyncTask<Void, Void, String>{

	 @Override
     protected void onPreExecute() {
         // TODO Auto-generated method stub
         /**
          * show dialog
          */
         super.onPreExecute();
     }

	
	@Override
	protected String doInBackground(Void... params) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
    protected void onPostExecute(String result) {
        // TODO Auto-generated method stub
        /**
         * update ui thread and remove dialog
         */
        super.onPostExecute(result);
    }
}
