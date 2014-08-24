package gmail.nextgenancestor.knocked;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

	Button button;
	static boolean smsCheck = false;
	static String password = "EMERGENCY";

	ArrayList whitelist;

	TextView btn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		btn = (TextView) findViewById(R.id.btnTurnOn);
		if (gmail.nextgenancestor.knocked.SmsListener.status) {
			btn.setText("Turn Off");
		}

		SharedPreferences prefs = this.getSharedPreferences(
				"gmail.nextgenancestor.knocked", Context.MODE_PRIVATE);
		password = prefs.getString("password", password);
	}

	public void turnOn(View view) {
		gmail.nextgenancestor.knocked.SmsListener.status = !gmail.nextgenancestor.knocked.SmsListener.status;
		
		SharedPreferences prefs = this.getSharedPreferences(
				"gmail.nextgenancestor.knocked", Context.MODE_PRIVATE);
		Editor editor = prefs.edit();

		if (gmail.nextgenancestor.knocked.SmsListener.status) {
			editor.putString("status", "true");
		} else {
			editor.putString("status", "false");
		}
		editor.commit();

		
		if (gmail.nextgenancestor.knocked.SmsListener.status) {
			btn.setText("Turn Off");
			// use this to start and trigger a service
			Intent i = new Intent(this, LocalService.class);
			this.startService(i);
		} else {
			btn.setText("Turn On");
		}
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

	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.action_change_pass:
			// Single menu item is selected do something
			// Ex: launching new activity/screen or show alert message
			changePassword(this.findViewById(android.R.id.content));
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

	}

	protected void onPause() {

		SharedPreferences prefs = this.getSharedPreferences(
				"gmail.nextgenancestor.knocked", Context.MODE_PRIVATE);
		Editor editor = prefs.edit();
		editor.putString("password", password);

		if (btn.getText().toString().equals("Turn Off")) {
			editor.putString("status", "true");
		} else {
			editor.putString("status", "false");
		}

		editor.commit();

		Log.d("password", password);
		Log.d("goodbye", prefs.getString("password", "not found"));

		super.onPause();

	}
}

// Whitlisting
// public void addNumber(View view) {
// LayoutInflater li = LayoutInflater.from(this);
// View promptsView = li.inflate(R.layout.prompt, null);
//
// AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
// alertDialogBuilder.setView(promptsView);
//
// final EditText userInput = (EditText) promptsView
// .findViewById(R.id.editTextDialogUserInput);
// alertDialogBuilder
// .setCancelable(false)
// .setPositiveButton("OK", new DialogInterface.OnClickListener() {
// public void onClick(DialogInterface dialog, int id) {
//
// }
// })
// .setNegativeButton("Cancel",
// new DialogInterface.OnClickListener() {
// public void onClick(DialogInterface dialog, int id) {
// dialog.cancel();
//
// }
// });
//
// AlertDialog alertDialog = alertDialogBuilder.create();
// alertDialog.show();
// }
