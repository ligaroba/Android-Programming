package com.telosoft.mazingira.customs;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


public final class Constants {
    // Defines a custom Intent action
    public static final String BROADCAST_ACTION =
        "com.telosoft.mtokamanager.BROADCAST";

    // Defines the key for the status "extra" in an Intent
    public static final String EXTENDED_DATA_STATUS =
        "com.telosoft.mtokamanager.STATUS";
    public static final String EXTRA_PARAMS = "params";

    public static final String EXTRA_BACKGROUND_STATUS="done";

	public static final String EXTRA_START = "start";

		
		// Shared pref mode
		public static int PRIVATE_MODE = 0;

		public static boolean running=false;
		public static final String KEY_SUCCESS = "success";
		
		// Sharedpref file name
		public static final String PREF_NAME = "mtokaLogin";
		
		// All Shared Preferences Keys
		public  static final String LOGIN_IN = "LoggedIn";
		public  static final String LOGGED_OUT = "LoggedOUt";
		
		// User name (make variable public to access from outside)
		public static final String KEY_TEL = "tel";
		public static final String KEY_ASSETID = "Id";
		public static final String PREFS_NAME = "loginPrefs";
		public static final String PREFS_ASSET = "assetPrefs";
		public static final String IS_LOGGED_IN = "isLoggedIn";
		public static final String PREFS_USERID = "telno";
		public static final int PERIODIC_EVENT_TIMEOUT = 30000;
		public static int RESPONSE=0;
		public static final String TABLE_PPERFOMANCE ="perfomance";
	
}
