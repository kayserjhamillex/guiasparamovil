package ancapopa.clinica;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import ancapopa.clinica.fragment.AppointmentsFragment;
import ancapopa.clinica.fragment.CreateAppointmentFragment;
import ancapopa.clinica.fragment.RecordsFragment;
import ancapopa.clinica.services.DialogService;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().show();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        fab.setVisibility(View.GONE);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        FragmentManager fragmentManager = getSupportFragmentManager();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Fragment oldFragment = fragmentManager.findFragmentByTag("main");
        if (oldFragment == null) {
            //init state
            navigationView.setCheckedItem(R.id.nav_old_appointments);

            setTitle(navigationView.getMenu().findItem(R.id.nav_old_appointments).getTitle());
            fragmentManager.beginTransaction().replace(R.id.frameContent, new AppointmentsFragment(),"main").commit();
        }
    }

    public void goToAppointments() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frameContent, new AppointmentsFragment(),"main").commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Class fragmentClass = null;
        Fragment fragment = null;

        if (id == R.id.nav_appointment) {
            fragmentClass = CreateAppointmentFragment.class;
        }
        else if (id == R.id.nav_old_appointments) {
            fragmentClass = AppointmentsFragment.class;
        }
        else if(id == R.id.nav_records) {
            fragmentClass = RecordsFragment.class;
        }
        else if (id == R.id.nav_logout) {
            DialogService.buildLogoutDialog(this, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    getSharedPreferences("login",0).edit().clear().commit();
                    startActivity(new Intent(MainActivity.this,LoginActivity.class));
                    finish();
                }
            }).show();
        }

        if (fragmentClass != null) {


            // Insert the fragment by replacing any existing fragment
            FragmentManager fragmentManager = getSupportFragmentManager();
            Fragment existingFragment = fragmentManager.findFragmentByTag("main");
            if (existingFragment != null && fragmentClass.equals(existingFragment.getClass())) {
                return false;
            }

            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }

            fragmentManager.beginTransaction().replace(R.id.frameContent, fragment,"main").commit();

            // Highlight the selected item has been done by NavigationView
            item.setChecked(true);
            // Set action bar title
            setTitle(item.getTitle());


            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        } else {
            return false;
        }
    }
}
