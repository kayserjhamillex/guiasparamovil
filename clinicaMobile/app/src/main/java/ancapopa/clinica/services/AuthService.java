package ancapopa.clinica.services;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.squareup.otto.Bus;

import ancapopa.clinica.events.UserAuthEvent;


/**
 * Created by anca.popa on 26/07/2017.
 */

public class AuthService {
    private Context mContext;
    private Bus mEventBus;

    private final String PREFS_NAME = "UserAuth";
    private final String KEY_USER_ID = "UserId";
    private final String KEY_USER_NAME = "UserName";
    private final String KEY_USER_DATA = "UserData";

    public AuthService(Context context, Bus eventBus) {
        mContext = context;
        mEventBus = eventBus;
    }

    public boolean isLogedIn() {

        SharedPreferences userAuthPrefs = getSharedPrefs();

        return (userAuthPrefs.contains(KEY_USER_ID) && !userAuthPrefs.getString(KEY_USER_ID,"").isEmpty());
    }

    private SharedPreferences getSharedPrefs() {
        return mContext.getSharedPreferences(PREFS_NAME, 0);
    }

    public void loginApi(String user, String password) {
        onLoginSuccess();

    }

    private void onLoginSuccess() {
        Log.d("Clinica","On Login Success");
        getSharedPrefs().edit().putString(KEY_USER_ID,"666").commit();

        mEventBus.post(new UserAuthEvent(true));
    }

    public void logout() {
        Log.d("Clinica","Logout");

        getSharedPrefs().edit().remove(KEY_USER_ID).commit();

        mEventBus.post(new UserAuthEvent(false));
    }
}
