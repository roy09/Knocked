package gmail.nextgenancestor.knocked;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

	Button button;
	boolean status = false;
	static boolean smsCheck = false;
	static String password = "EMERGENCY";
	
	ArrayList whitelist;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		SharedPreferences prefs = this.getSharedPreferences(
				"gmail.nextgenancestor.knocked", Context.MODE_PRIVATE);
		password = prefs.getString("password", password);
		
		
		
		
		EditText sth = (EditText) findViewById(R.id.cpwd);
		sth.setText(password);
	}

	public void turnOn(View view) {
		status = !status;

		// Need to change status to another boolean val to pass only through
		// texts with the passcode
		AudioManager aManager = (AudioManager) getSystemService(AUDIO_SERVICE);
		// need to check the current status of ringer mode
		switch (aManager.getRingerMode()) {
		case AudioManager.RINGER_MODE_NORMAL:
			break;
		case AudioManager.RINGER_MODE_SILENT:
			if (status & smsCheck) {
				aManager.setRingerMode(aManager.RINGER_MODE_SILENT);
			}
		case AudioManager.RINGER_MODE_VIBRATE:
			if (status & smsCheck) {
				aManager.setRingerMode(aManager.RINGER_MODE_SILENT);
			}
		}

		Log.d("status", status + " " + smsCheck);
		// changing the smsCheck to false to reset
		smsCheck = false;
	}

	public void addNumber(View view) {
		LayoutInflater li = LayoutInflater.from(this);
		View promptsView = li.inflate(R.layout.prompt, null);

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder.setView(promptsView);

		final EditText userInput = (EditText) promptsView
				.findViewById(R.id.editTextDialogUserInput);
		alertDialogBuilder
				.setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						
					}
				})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();

							}
						});

		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}

	public void changePassword(View view) {

		LayoutInflater li = LayoutInflater.from(this);
		View promptsView = li.inflate(R.layout.prompt, null);

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder.setView(promptsView);

		final EditText userInput = (EditText) promptsView
				.findViewById(R.id.editTextDialogUserInput);
		alertDialogBuilder
				.setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						password = userInput.getText().toString();
						EditText sth = (EditText) findViewById(R.id.cpwd);
						sth.setText(password);
						// I could try hashing
					}
				})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();

							}
						});

		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();

		// Log.d("pref", prefs.getString("password", "none"));

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


	protected void onPause() {
		
		SharedPreferences prefs = this.getSharedPreferences(
		        "gmail.nextgenancestor.knocked", Context.MODE_PRIVATE);
		Editor editor = prefs.edit();
		editor.putString("password", password);
		editor.commit();
		
		Log.d("password", password);
		Log.d("goodbye", prefs.getString("password", "not found"));
		
		super.onPause();

		
	}
}
