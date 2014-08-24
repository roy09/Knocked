package gmail.nextgenancestor.knocked;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

public class SmsListener extends BroadcastReceiver {

	public String msgBody;
	public String msg_from;
	public static boolean status = false;
	SharedPreferences prefs;

	@Override
	public void onReceive(Context context, Intent intent) {
		prefs = context.getSharedPreferences("gmail.nextgenancestor.knocked",
				Context.MODE_PRIVATE);
		if(prefs.getString("status", "true").equals("true")){
			status = true;
		}
		if (intent.getAction()
				.equals("android.provider.Telephony.SMS_RECEIVED")) {
			Bundle bundle = intent.getExtras(); // ---get the SMS message
												// passed
												// in---
			SmsMessage[] msgs = null;

			if (bundle != null) {
				// ---retrieve the SMS message received---
				try {
					Object[] pdus = (Object[]) bundle.get("pdus");
					msgs = new SmsMessage[pdus.length];
					for (int i = 0; i < msgs.length; i++) {
						msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
						msg_from = msgs[i].getOriginatingAddress();
						msgBody = msgs[i].getMessageBody();
					}
				} catch (Exception e) {
					// Log.d("Exception caught",e.getMessage());
				}
			}
		}

		Log.d("msg_from", msg_from + " " + status);
		Log.d("password", gmail.nextgenancestor.knocked.MainActivity.password);
		if (msgBody.equals(prefs.getString("password", "random8450"))) {
			if (status) {
				changeSound(context);
			}

		} else {
			Log.d("result", "Continue");

		}
	}

	public void changeSound(Context context) {
		AudioManager aManager = (AudioManager) context
				.getSystemService(context.AUDIO_SERVICE);
		// need to check the current status of ringer mode
		switch (aManager.getRingerMode()) {
		case AudioManager.RINGER_MODE_NORMAL:
			break;
		case AudioManager.RINGER_MODE_SILENT:
			aManager.setRingerMode(aManager.RINGER_MODE_NORMAL);

		case AudioManager.RINGER_MODE_VIBRATE:
			aManager.setRingerMode(aManager.RINGER_MODE_NORMAL);

		}
	}

}