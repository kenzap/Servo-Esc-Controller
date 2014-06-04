package com.kenzap.servo;



import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class Instruct extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
	            // Show the Up button in the action bar.
	            getActionBar().setDisplayHomeAsUpEnabled(true);     
	    }	
    	InstructFragment prefFragment = new InstructFragment();
    	FragmentManager fragmentManager = getFragmentManager();
    	FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    	fragmentTransaction.replace(android.R.id.content, prefFragment);
    	fragmentTransaction.commit();
    	

	}
	@Override
	public void onBackPressed() {
	    	
	    	this.finish();
	        return;
	}
	
    //SHOW TOP RIGHT MENU
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	
    	//MenuInflater inflater = getMenuInflater();
    	//inflater.inflate(R.menu.instruct, menu);
    	return super.onCreateOptionsMenu(menu);    
    }
    
	
    @Override
	public boolean onOptionsItemSelected(MenuItem item){
		

    	    // Handle presses on the action bar items
    	    switch (item.getItemId()) {           
    	       
    	        default:
    	        	this.finish();
    	            return super.onOptionsItemSelected(item);
    	    }
    }
}
