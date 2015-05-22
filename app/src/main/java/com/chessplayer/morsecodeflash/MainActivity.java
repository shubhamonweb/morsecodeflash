package com.chessplayer.morsecodeflash;
import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.media.AudioTrack;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.Hashtable;



public class MainActivity extends ActionBarActivity {
	 private Camera camera;
	 private Parameters params;
	 private final Context context = this;
	 private boolean isflashon = false;
	 private long timeUnit = 60;
    /**
     * this is for converting characters to morse
     */
     public Hashtable<String,String> charLookup;




    @SuppressWarnings("deprecation")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final PackageManager pm = context.getPackageManager();
        boolean hasFlash = getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH); //checks if it has camera
         
        if (!hasFlash) {
            // device doesn't support flash
            // Show alert message and close the application
            AlertDialog alert = new AlertDialog.Builder(MainActivity.this)
                    .create();
            alert.setTitle("Error");
            alert.setMessage("Sorry, your device doesn't support flash light!");
            alert.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // closing the application
                    finish();
                }
            });
            alert.show();
            return;
        }
//        if (camera == null) {
//            try {
//                camera = Camera.open();
//                params = camera.getParameters();
//            } catch (RuntimeException e) {
//                Log.e("Camera Error. Failed to Open. Error: ", e.getMessage());
//            }
//        }
        final TextView text = (TextView) findViewById(R.id.text);
        final EditText editText = (EditText) findViewById(R.id.edit_message);
        final Button button = (Button) findViewById(R.id.button_send);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String ddd = stringToMorse(editText.getText().toString());
                text.setText(ddd);
//                if (!isflashon) {
//                    setFlashlightOn();
//                }
//                else {
//                	setFlashlightOff();
//                }
                letsFlash(ddd);
            }
        });
    }
    
    public void letsFlash(final String text) {
    	new Thread(new Runnable() {
			
			@Override
			public void run() {
		         String selectedChar;
		         String convertedChar;
		         for (int i = 0; i < text.length(); i++)
		         {
		  
		             //Select the next character
		             convertedChar = text.charAt(i) + "";
		  

		             if (convertedChar.equals(".")) {
		                 if (!isflashon) {
		                	 setFlashlightOn();
		                 }
		            	 try {
					//		wait(timeUnit);
							Thread.sleep(timeUnit);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		             }
		             if (convertedChar.equals("-")) {
		                 if (!isflashon) {
		                	 setFlashlightOn();
		                 }
		            	 try {
					//		wait(timeUnit * 3);
		            		 Thread.sleep(timeUnit * 3);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		             }
		             if (convertedChar.equals(" ")) {
		                 if (isflashon) {
		                	 setFlashlightOff();
		                 }
		            	 try {
					//		wait(timeUnit * 2);
		            		 Thread.sleep(timeUnit);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		             }
		  
		             if (convertedChar.equals("|")) {
		                 if (isflashon) {
		                	 setFlashlightOff();
		                 }
		            	 try {
					//		wait(timeUnit * 5);
		            		 Thread.sleep(timeUnit * 2);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		             }
		             if (isflashon) {
	                	 setFlashlightOff();
	                 }
	            	 try {
				//		wait(timeUnit);
	            		 Thread.sleep(timeUnit);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		             
		         }
			}
		}).start();
    }

    @SuppressWarnings("deprecation")
	private void setFlashlightOn() {
        params = camera.getParameters();
        params.setFlashMode(Parameters.FLASH_MODE_TORCH);
        camera.setParameters(params);
        camera.startPreview();

        isflashon = true;


     }

     @SuppressWarnings("deprecation")
	private void setFlashlightOff() {
    	 params = camera.getParameters();
         params.setFlashMode(Parameters.FLASH_MODE_OFF);
         camera.setParameters(params);
         camera.stopPreview();
         isflashon = false;
     }
    @Override
    public void onPause() {
    	super.onPause();
      // camera.setPreviewCallback(null);
    	camera.release();
    }
    @Override
    public void onResume() {
    	super.onResume();
        if (camera == null) {
            try {
                camera = Camera.open();
                params = camera.getParameters();
            } catch (RuntimeException e) {
                Log.e("Camera failed to Open. ", e.getMessage());
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //we should use a hashtable on  this





    public static String encode (String toEncode) {
        String morse = toEncode;
        Decoder decode=new Decoder();


 
        return morse;
    }

	public static String decode (String toEncode) {
	    String morse = toEncode;
	

	
	    return morse;
	}

	public static String stringToMorse( String text ) {
 
        String newText = "";
        String selectedChar;
        String convertedChar;
        for (int i = 0; i < text.length(); i++)
        {
 
            //Select the next character
            selectedChar = text.charAt(i) + "";
 
            // Convert the character
            convertedChar = encode(selectedChar);
 
            if (convertedChar.equals(" ")) // "|" separates each word represented in morse code
            {
                newText = newText + "| ";
            }
            // Add the converted text, and add a space
            else
            {
                newText = newText + convertedChar;
                if (!convertedChar.equals(" "))
                {
                    newText = newText + " ";
                }
            }
        }
 
        return newText;
    }
}
