package gmail.nextgenancestor.knocked;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

	Button button;
	boolean status = false;
	String password;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
	}

	public void turnOn(View view) {
		status = !status;
		
		//Need to change status to another boolean to pass only through texts
		AudioManager aManager = (AudioManager) getSystemService(AUDIO_SERVICE);
		if (status){
			aManager.setRingerMode(aManager.RINGER_MODE_SILENT);
			
		} else {
			aManager.setRingerMode(aManager.RINGER_MODE_NORMAL);
			
		}
	}

	
	public void changePassword(View view) {
		
		LayoutInflater li = LayoutInflater.from(this);
		View promptsView = li.inflate(R.layout.prompt, null);

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder.setView(promptsView);

		final EditText userInput = (EditText) promptsView.findViewById(R.id.editTextDialogUserInput);

		alertDialogBuilder
				.setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						password = userInput.getText().toString();
						
						EditText sth = (EditText) findViewById(R.id.cpwd);
						sth.setText(password);
					}
				})
				.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();

					}
				});
		
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
