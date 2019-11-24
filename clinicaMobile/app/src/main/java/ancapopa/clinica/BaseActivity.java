package ancapopa.clinica;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.squareup.otto.Bus;

import ancapopa.clinica.activityhelper.AuthActivityHelper;
import ancapopa.clinica.services.AuthService;


/**
 * Created by anca.popa on 26/07/2017.
 */

public class BaseActivity extends AppCompatActivity {
    private Bus eventBus;
    private AuthActivityHelper authActivityHelper;

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        eventBus = ((ClinicaApplication) getApplication()).getEventBus();
        eventBus.register(this);

        authActivityHelper = new AuthActivityHelper(this);
        eventBus.register(authActivityHelper);
    }

    protected Bus getEventBus() {
        return eventBus;
    }

    public void forceLogin() {
        AuthService authService = getAuthService();
        if (!authService.isLogedIn()) {

            startActivity(new Intent(this,LoginActivity.class));
            finish();
        }
    }

    protected AuthService getAuthService() {
        return new AuthService(getApplicationContext(),getEventBus());
    }

    @Override
    public void onDestroy () {
        eventBus.unregister(this);
        eventBus.unregister(authActivityHelper);
        eventBus = null;
        authActivityHelper = null;
        super.onDestroy();
    }
}
