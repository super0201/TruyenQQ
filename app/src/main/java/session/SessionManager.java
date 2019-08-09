package session;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "Pref";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // password (make variable public to access from outside)
    public static final String KEY_PASS = "pass";

    // username (make variable public to access from outside)
    public static final String KEY_USER = "user";
    private static SessionManager instance;

    // Constructor
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    //Create login session

    public void createLoginSession(String pass, String user){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing pass in pref
        editor.putString(KEY_PASS, pass);

        // Storing username in pref
        editor.putString(KEY_USER, user);

        // commit changes
        editor.commit();
    }

    //Check login method wil check user login status if false it will redirect user to login page else won't do anything

//    public void checkLogin(){
//        // Check login status
//        if(!this.isLoggedIn()){
//            // user is not logged in redirect him to Login Activity
//            Intent i = icon_new Intent(_context, LoginActivity.class);
//            // Closing all the Activities
//            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//            // Add icon_new Flag to start icon_new Activity
//            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//            // Staring Login Activity
//            _context.startActivity(i);
//        }
//    }

    //Get stored session data

    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(KEY_PASS, pref.getString(KEY_PASS, null));

        // user email id
        user.put(KEY_USER, pref.getString(KEY_USER, null));

        // return user
        return user;
    }

//    public void logoutUser(){
//        // Clearing all data from Shared Preferences
//        editor.clear();
//        editor.commit();
//
//        // After logout redirect user to Loing Activity
//        Intent i = icon_new Intent(_context, LoginActivity.class);
//        // Closing all the Activities
//        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//        // Add icon_new Flag to start icon_new Activity
//        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//        // Staring Login Activity
//        _context.startActivity(i);
//    }

    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }

    public static SessionManager getInstance(Context context){
        if (instance == null){
            instance = new SessionManager(context);
        }
        return instance;
    }
}


