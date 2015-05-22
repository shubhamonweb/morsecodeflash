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


public class MainActivity extends ActionBarActivity {
	 private Camera camera;
	 private Parameters params;
	 private final Context context = this;
	 private boolean isflashon = false;
	 private long timeUnit = 60;

    @SuppressWarnings("deprecation")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final PackageManager pm = context.getPackageManager();
        boolean hasFlash = getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
         
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

    public static String encode (String toEncode) {
        String morse = toEncode;
 
        if (toEncode.equalsIgnoreCase("a"))
            morse = ".-";
        if (toEncode.equalsIgnoreCase("b"))
            morse = "-...";
        if (toEncode.equalsIgnoreCase("c"))
            morse = "-.-.";
        if (toEncode.equalsIgnoreCase("d"))
            morse = "-..";
        if (toEncode.equalsIgnoreCase("e"))
            morse = ".";
        if (toEncode.equalsIgnoreCase("f"))
            morse = "..-.";
        if (toEncode.equalsIgnoreCase("g"))
            morse = "--.";
        if (toEncode.equalsIgnoreCase("h"))
            morse = "....";
        if (toEncode.equalsIgnoreCase("i"))
            morse = "..";
        if (toEncode.equalsIgnoreCase("j"))
            morse = ".---";
        if (toEncode.equalsIgnoreCase("k"))
            morse = "-.-";
        if (toEncode.equalsIgnoreCase("l"))
            morse = ".-..";
        if (toEncode.equalsIgnoreCase("m"))
            morse = "--";
        if (toEncode.equalsIgnoreCase("n"))
            morse = "-.";
        if (toEncode.equalsIgnoreCase("o"))
            morse = "---";
        if (toEncode.equalsIgnoreCase("p"))
            morse = ".--.";
        if (toEncode.equalsIgnoreCase("q"))
            morse = "--.-";
        if (toEncode.equalsIgnoreCase("r"))
            morse = ".-.";
        if (toEncode.equalsIgnoreCase("s"))
            morse = "...";
        if (toEncode.equalsIgnoreCase("t"))
            morse = "-";
        if (toEncode.equalsIgnoreCase("u"))
            morse = "..-";
        if (toEncode.equalsIgnoreCase("v"))
            morse = "...-";
        if (toEncode.equalsIgnoreCase("w"))
            morse = ".--";
        if (toEncode.equalsIgnoreCase("x"))
            morse = "-..-";
        if (toEncode.equalsIgnoreCase("y"))
            morse = "-.--";
        if (toEncode.equalsIgnoreCase("z"))
            morse = "--..";
        if (toEncode.equalsIgnoreCase("0"))
            morse = "-----";
        if (toEncode.equalsIgnoreCase("1"))
            morse = ".----";
        if (toEncode.equalsIgnoreCase("2"))
            morse = "..---";
        if (toEncode.equalsIgnoreCase("3"))
            morse = "...--";
        if (toEncode.equalsIgnoreCase("4"))
            morse = "....-";
        if (toEncode.equalsIgnoreCase("5"))
            morse = ".....";
        if (toEncode.equalsIgnoreCase("6"))
            morse = "-....";
        if (toEncode.equalsIgnoreCase("7"))
            morse = "--...";
        if (toEncode.equalsIgnoreCase("8"))
            morse = "---..";
        if (toEncode.equalsIgnoreCase("9"))
            morse = "----.";
        if (toEncode.equalsIgnoreCase("."))
            morse = ".-.-";
        if (toEncode.equalsIgnoreCase(","))
            morse = "--..--";
        if (toEncode.equalsIgnoreCase("?"))
            morse = "..--..";
 
        return morse;
    }

	public static String decode (String toEncode) {
	    String morse = toEncode;
	
	    if (toEncode.equalsIgnoreCase(".- "))
	        morse = "a";
	    if (toEncode.equalsIgnoreCase("-..."))
	        morse = "b";
	    if (toEncode.equalsIgnoreCase("-.-."))
	        morse = "c";
	    if (toEncode.equalsIgnoreCase("-.."))
	        morse = "d";
	    if (toEncode.equalsIgnoreCase("."))
	        morse = "e";
	    if (toEncode.equalsIgnoreCase("..-."))
	        morse = "f";
	    if (toEncode.equalsIgnoreCase("--."))
	        morse = "g";
	    if (toEncode.equalsIgnoreCase("...."))
	        morse = "h";
	    if (toEncode.equalsIgnoreCase(".."))
	        morse = "i";
	    if (toEncode.equalsIgnoreCase(".---"))
	        morse = "j";
	    if (toEncode.equalsIgnoreCase("-.-"))
	        morse = "k";
	    if (toEncode.equalsIgnoreCase(".-.."))
	        morse = "l";
	    if (toEncode.equalsIgnoreCase("--"))
	        morse = "m";
	    if (toEncode.equalsIgnoreCase("-."))
	        morse = "n";
	    if (toEncode.equalsIgnoreCase("---"))
	        morse = "o";
	    if (toEncode.equalsIgnoreCase(".--."))
	        morse = "p";
	    if (toEncode.equalsIgnoreCase("--.-"))
	        morse = "q";
	    if (toEncode.equalsIgnoreCase(".-."))
	        morse = "r";
	    if (toEncode.equalsIgnoreCase("..."))
	        morse = "s";
	    if (toEncode.equalsIgnoreCase("-"))
	        morse = "t";
	    if (toEncode.equalsIgnoreCase("..-"))
	        morse = "u";
	    if (toEncode.equalsIgnoreCase("...-"))
	        morse = "v";
	    if (toEncode.equalsIgnoreCase(".--"))
	        morse = "w";
	    if (toEncode.equalsIgnoreCase("-..-"))
	        morse = "x";
	    if (toEncode.equalsIgnoreCase("-.--"))
	        morse = "y";
	    if (toEncode.equalsIgnoreCase("--.."))
	        morse = "z";
	    if (toEncode.equalsIgnoreCase("-----"))
	        morse = "0";
	    if (toEncode.equalsIgnoreCase(".----"))
	        morse = "1";
	    if (toEncode.equalsIgnoreCase("..---"))
	        morse = "2";
	    if (toEncode.equalsIgnoreCase("...--"))
	        morse = "3";
	    if (toEncode.equalsIgnoreCase("....-"))
	        morse = "4";
	    if (toEncode.equalsIgnoreCase("....."))
	        morse = "5";
	    if (toEncode.equalsIgnoreCase("-...."))
	        morse = "6";
	    if (toEncode.equalsIgnoreCase("--..."))
	        morse = "7";
	    if (toEncode.equalsIgnoreCase("---.."))
	        morse = "8";
	    if (toEncode.equalsIgnoreCase("----."))
	        morse = "9";
	    if (toEncode.equalsIgnoreCase(" "))
	        morse = "";
	
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
