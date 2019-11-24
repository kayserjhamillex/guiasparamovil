package ancapopa.clinica.activityhelper;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.widget.Toast;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import ancapopa.clinica.BaseActivity;
import ancapopa.clinica.LoginActivity;
import ancapopa.clinica.MainActivity;
import ancapopa.clinica.R;
import ancapopa.clinica.events.UserAuthEvent;

/**
 * Created by anca.popa on 27/07/2017.
 */

public class AuthActivityHelper {
    protected BaseActivity mActivity;
    public AuthActivityHelper(BaseActivity activity) {
        mActivity = activity;
    }

    @Subscribe
    public void onAuthEvent(UserAuthEvent event) {
        Log.d("Clinica","Event");
        if (event.getLogin()) {
            mActivity.startActivity(new Intent(mActivity,MainActivity.class));
            mActivity.finish();
        } else {
            mActivity.startActivity(new Intent(mActivity,LoginActivity.class));
            mActivity.finish();
            Toast.makeText(mActivity, R.string.prompt_logout_success, Toast.LENGTH_LONG).show();
        }
    }
}
