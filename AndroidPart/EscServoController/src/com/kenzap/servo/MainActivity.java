package com.kenzap.servo;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.hoho.android.usbserial.driver.UsbSerialDriver;
import com.hoho.android.usbserial.driver.UsbSerialProber;
import com.hoho.android.usbserial.util.HexDump;
import com.hoho.android.usbserial.util.SerialInputOutputManager;

public class MainActivity extends Activity{

	IntentFilter filter = new IntentFilter();
	SeekBar seekBar1, seekBar2, seekBar3, seekBar4, seekBar5;
	TextView serial;
	
	//SERIAL COMMUNICATION
	Long serialBlock = 0l;
	UsbSerialDriver sDriver = null;
	private SerialInputOutputManager mSerialIoManager;
	private final ExecutorService mExecutor = Executors.newSingleThreadExecutor();
	private final SerialInputOutputManager.Listener mListener = new SerialInputOutputManager.Listener() {

        @Override
        public void onRunError(Exception e) {
            Log.d("SERIAL", "Runner stopped.");
        }

        @Override
        public void onNewData(final byte[] data) {
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                   MainActivity.this.updateReceivedData(data);
                }
            });
        }};
        
        private static int pr1 = 0, pr2 = 0, pr3 = 0, pr4 = 0, pr5 = 0;
        private OnSeekBarChangeListener seekBarListener = new OnSeekBarChangeListener(){

    		@Override
    		public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    				 String id = seekBar.getTag().toString();
    				 if(id.equals("1"))
    					 pr1 = progress;
    				 if(id.equals("2"))
    					 pr2 = progress;
    				 if(id.equals("3"))
    					 pr3 = progress;
    				 if(id.equals("4"))
    					 pr4 = progress;
    				 if(id.equals("5"))
    					 pr5 = progress;
    		
	    			 if (sDriver != null &&  (System.currentTimeMillis()-serialBlock)>10) {
	    				 
	    				   //Toast.makeText(getApplicationContext(), "Sending..", Toast.LENGTH_SHORT).show();
	    				   try {
	    				    	
	    				    	byte buffer[] = new byte[32];
	    				    	buffer = ("XA"+String.valueOf(pr1)+"B"+String.valueOf(pr2)+"C"+String.valueOf(pr3)+"D"+String.valueOf(pr4)+"E"+String.valueOf(pr5)+"F"+"00000000000000000000000000000").getBytes("UTF-8");
	    				    	sDriver.write(buffer, 5);

	    				   } catch (UnsupportedEncodingException e) {
	    						
	    						e.printStackTrace();
	    				   } catch (IOException e) {
	    				
	    						e.printStackTrace();
	    				   }
	    				   serialBlock = System.currentTimeMillis();
	    			 }
    		}

    		@Override
    		public void onStartTrackingTouch(SeekBar seekBar) {
    			
    		}

    		@Override
    		public void onStopTrackingTouch(SeekBar seekBar) {
    	
    		}};

    		
    		/** Called when the activity is first created. */ 
    		@Override 
    		public void onCreate(Bundle savedInstanceState) { 
    		
    			super.onCreate(savedInstanceState); 
    			setContentView(R.layout.activity_main);
    		
    			//BIND VIEW VARIABLES
    		    seekBar1 = (SeekBar) findViewById(R.id.seekBar1);
    		    seekBar2 = (SeekBar) findViewById(R.id.seekBar2);
    		    seekBar3 = (SeekBar) findViewById(R.id.seekBar3);
    		    seekBar4 = (SeekBar) findViewById(R.id.seekBar4);
    		    seekBar5 = (SeekBar) findViewById(R.id.seekBar5);
    		    serial = (TextView) findViewById(R.id.serial);		 
    		}

    		
    		
    	    //SHOW TOP RIGHT MENU
    	    @Override
    	    public boolean onCreateOptionsMenu(Menu menu) {
    	    	
    	    	MenuInflater inflater = getMenuInflater();
    	    	inflater.inflate(R.menu.instruct, menu);
    	    	return super.onCreateOptionsMenu(menu);    
    	    }
    	    
    		
    	    @Override
    		public boolean onOptionsItemSelected(MenuItem item){
    			

    	    	    switch (item.getItemId()) {           
    	    	        case R.id.help:
    	    	        	//OPEN HELP WINDOW
    	    	        	startActivity(new Intent(this, Instruct.class));
    	    	        	return true;
    	    	        default:
    	    	        	this.finish();
    	    	            return super.onOptionsItemSelected(item);
    	    	    }
    	    }
    		
    		
    		@Override
    		protected void onPause() {
    			
    		        super.onPause();
    		        //unregisterReceiver(receiver);
    		        
    		        
    		        try {
    		        	if(sDriver!=null)
    		        		sDriver.close();
    				} catch (IOException e) {
    			
    					e.printStackTrace();
    				}
    		        
    		        sDriver=null;
    		        stopIoManager();
    
    		}
    	    
    		public void onResume(){
    			super.onResume();
    			connectArduino();

    		}
    		
    		public void connect (View v){
    			
    			connectArduino();
    		}
    		
    		public void connectArduino (){
    			
    			
    			//SystemClock.sleep(500);
    			Log.d("Serial", "Pre Sending");
    			serial.setText("Openingt port");
    			SystemClock.sleep(100);
    			// Get UsbManager from Android.
    			UsbManager manager = (UsbManager) getSystemService(Context.USB_SERVICE);
    			
    			for (final UsbDevice device : manager.getDeviceList().values()) {

    				//GET DEVICE DRIVER
    				serial.setText("Device ID: "+device.getDeviceId());
    	
    				//FIND DRIVER
    	         final List<UsbSerialDriver> drivers = UsbSerialProber.probeSingleDevice(manager, device);
    	         if (drivers.isEmpty()) {
    	             Log.d("usb", "  - No UsbSerialDriver available.");
    	             //result.add(new DeviceEntry(device, null));
    	         } else {
    	             for (UsbSerialDriver driver : drivers) {
    	                 Log.d("usb", "  + " + driver);
    	                 SystemClock.sleep(100);
    	                 serial.setText("Driver found"+driver);
    	                 sDriver = driver;
    
    	             }
    	         }
    			}
    			SystemClock.sleep(200);
    			
    		
    			try {
    				//DRIVER FOUND
    				if(sDriver!=null){
    				
    				sDriver.open();
    				sDriver.setParameters(115200, 8, UsbSerialDriver.STOPBITS_1, UsbSerialDriver.PARITY_NONE);
    				serial.setText("Status: Port opened");
    				
    				SystemClock.sleep(200);
    				onDeviceStateChange();
    				sDriver.setDTR(true);
    				SystemClock.sleep(200);
    				 
    	
    				}else{
    					
    					serial.setText("Device NOT found");
    				}
    					
    			} catch (IOException e) {
    	
    				serial.setText("FATAL ERROR"+e.toString());
    				e.printStackTrace();
    			}
    	 
    		}
    		
    	 private void onDeviceStateChange() {
    	     stopIoManager();
    	     startIoManager();
    	 }
    	 
    	 private void stopIoManager() {
    	     if (mSerialIoManager != null) {
    	         //Log.i(TAG, "Stopping io manager ..");
    	         mSerialIoManager.stop();
    	         mSerialIoManager = null;
    	         
    	     
    	     }
    	 }

    	 private void startIoManager() {
    	     if (sDriver != null) {
    	         //Log.i(TAG, "Starting io manager ..");
    	         mSerialIoManager = new SerialInputOutputManager(sDriver, mListener);
    	         mExecutor.submit(mSerialIoManager);
    	         
    	         seekBar1.setOnSeekBarChangeListener(seekBarListener);
    	         seekBar2.setOnSeekBarChangeListener(seekBarListener);
    	         seekBar3.setOnSeekBarChangeListener(seekBarListener);
    	         seekBar4.setOnSeekBarChangeListener(seekBarListener);
    	         seekBar5.setOnSeekBarChangeListener(seekBarListener);
    	     }
    	 }
    	 
    		
    	 private void updateReceivedData(byte[] data) {
    	     //final String message = "Read " + data.length + " bytes: \n" + data + "\n " + HexDump.dumpHexString(data) + "\n\n";
    	     String res = HexDump.dumpHexString(data);
    	     
    	     int i1 = res.indexOf("|",0);
    	     int i2 = res.indexOf("|",i1+1);
    	     
    	     if(i1!=-1 && i2!=-1){
    	     	
    	     		res = res.substring(i1+1,i2);
    		        if(res.indexOf("PWM:", 0)!=-1){
    		        	
    		        	serial.setText(res.substring(res.indexOf("PWM:", 0), res.length()));
    		        	return;
    		        }
    		        
    		       
    		        serial.setText(res);
    	     }
    	    // mDumpTextView.append(message);
    	    // mScrollView.smoothScrollTo(0, mDumpTextView.getBottom());
    	 }
    		
    		
    		
}
