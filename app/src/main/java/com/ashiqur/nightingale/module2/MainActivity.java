package com.ashiqur.nightingale.module2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.ashiqur.nightingale.ashiqur_util.FileUtil;
import com.ashiqur.nightingale.module2.doctor_section.ChooseSectorFragment;
import com.ashiqur.nightingale.module2.doctor_section.DoctorSignupFragment0;
import com.ashiqur.nightingale.module2.mens_section.MensFragment;
import com.ashiqur.nightingale.module2.preg_section.PregFragment;
import com.ashiqur.nightingale.module2.premens_section.PreMensFragment;

import com.ashiqur.nightingale.R;
import s.ashiqur.lady.data.User;

import static com.ashiqur.nightingale.ashiqur_util.UiUtil.showSnackBar;
import static com.ashiqur.nightingale.ashiqur_util.UiUtil.showToast;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private static final String TAG = "MainActivity";
    private FileUtil fileUtil;

    public User getThisUser() {
        return thisUser;
    }

    public void setThisUser(User thisUser) {
        this.thisUser = thisUser;
    }

    private User thisUser;

    private NavigationView navigationView;

    public NavigationView getNavigationView() {
        return navigationView;
    }

    public FileUtil getFileUtil() {
        return fileUtil;
    }

    public BottomNavigationView getBottomNavigationView() {
        return bottomNavigationView;
    }

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeXmlVariables();
        initializeJavaVariables();
        showToast(getApplicationContext(),"LOGGED IN SUCCESSFULLY" +getIntent().getStringExtra("new user phone"), Toast.LENGTH_LONG);
        switchFragment(R.id.navdrawer_myinfo_section);

    }

    private void initializeJavaVariables(){
        FileUtil.verifyStoragePermissions(this);
        fileUtil=new FileUtil(getResources().getString(R.string.app_name));
    }

    private void initializeXmlVariables()
    {
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        toolbar.setTitleTextColor(getResources().getColor(R.color.colorBackground));

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();

        findViewById(R.id.btn_nav_drawer_toggle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                }else drawer.openDrawer(GravityCompat.START);
            }
        });
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        bottomNavigationView=findViewById(R.id.bottom_navigation);


    }

    private int backPressCount=0;
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
            {
                if(getSupportFragmentManager().getBackStackEntryCount()<=2)
                {
                    backPressCount++;
                    if(backPressCount!=2)showToast(getApplicationContext(),"Press Back Again to Exit",Toast.LENGTH_SHORT);
                    else finish();
                }
                else
                    {
                        backPressCount=0;
                        //Log.wtf(TAG,getCurrentFragment().getClass().getName()+"");
                        getSupportFragmentManager().popBackStackImmediate();
//                        String fNext=getCurrentFragment().getClass().getName()+"";
//                        fNext=fNext.replace("s.ashiqur.lady.module2.","");
//
                    }
            }

    }

    public void switchFragment(int id)
    {
        Fragment fragment=null;
        FragmentTransaction ft=null;
        switch (id)
        {
            case R.id.navdrawer_myinfo_section:
                fragment=new UserInfoFragment();
                Bundle bundle = new Bundle();
                String myMessage = getIntent().getStringExtra("new user phone");
                bundle.putString("new user phone", myMessage );
                fragment.setArguments(bundle);
                ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.framelayout_content_main_activity, fragment,getSupportFragmentManager().getBackStackEntryCount()+"");
                ft.commit();
                ft.addToBackStack(null);

                break;
            case R.id.navdrawer_premens_section:
                fragment=new PreMensFragment();
                ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.framelayout_content_main_activity, fragment,getSupportFragmentManager().getBackStackEntryCount()+"");
                ft.commit();

                //Toast.makeText(getApplicationContext(),"Premens",Toast.LENGTH_SHORT).show();
                break;
            case R.id.navdrawer_mens_section:
                fragment=new MensFragment();
                ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.framelayout_content_main_activity, fragment,getSupportFragmentManager().getBackStackEntryCount()+"");
                ft.commit();

                break;
            case R.id.navdrawer_pregnancy_section:
                fragment=new PregFragment();
                ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.framelayout_content_main_activity, fragment,getSupportFragmentManager().getBackStackEntryCount()+"");
                ft.commit();

                break;
            case R.id.navdrawer_live_medic:
                fragment=new ChooseSectorFragment();
                ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.framelayout_content_main_activity, fragment,getSupportFragmentManager().getBackStackEntryCount()+"");
                ft.commit();
                ft.addToBackStack(null);

                break;
            case R.id.navdrawer_ifyouareadoc_contactus_section:
                fragment=new DoctorSignupFragment0();
                ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.framelayout_content_main_activity, fragment,getSupportFragmentManager().getBackStackEntryCount()+"");
                ft.commit();
                ft.addToBackStack(null);

                break;
        }
        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }
    private Fragment getFragmentAt(int index) {
        return getSupportFragmentManager().getBackStackEntryCount() > 0 ? getSupportFragmentManager().findFragmentByTag(Integer.toString(index)) : null;
    }
    protected Fragment getCurrentFragment() {
        return getFragmentAt(getSupportFragmentManager().getBackStackEntryCount() - 1);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item){
        // Handle side navigation item clicks here.
        int id = item.getItemId();
       switchFragment(id);
        return true;
    }

    private boolean isSectionSwitchValid(int id)
    {
        if(thisUser==null)
        {
            showSnackBar(navigationView, "Cant Load user Info", Snackbar.LENGTH_LONG, "Update", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switchFragment(R.id.navdrawer_myinfo_section);
                }
            });
        }
        switch (id)
        {
            case R.id.navdrawer_premens_section:
                if(!thisUser.isStartedMenstruating() && !thisUser.isPregnant() && !thisUser.isPregnantBefore() )return true;

                else
                    {
                        showSnackBar(navigationView,"Sorry,This Section is Only for Those Who didnt have their pregnancy Section",Snackbar.LENGTH_SHORT);
                        return false;
                    }

            case R.id.navdrawer_mens_section:
                if(thisUser.isPregnant())
                {
                    showSnackBar(navigationView,"Please Use Our Pregnancy Section",Snackbar.LENGTH_SHORT);
                    return false;
                }
                if(!thisUser.isStartedMenstruating())
                {
                    showSnackBar(navigationView,"Please Use Our Pre-Menstruation Section",Snackbar.LENGTH_SHORT);
                    return false;
                }
                break;
            case R.id.navdrawer_pregnancy_section:
                if(!thisUser.isStartedMenstruating())
                {
                    showSnackBar(navigationView,"Please Use Our Pre-Menstruation Section",Snackbar.LENGTH_SHORT);
                    return false;
                }
                if(!thisUser.isPregnant())
                {
                    showSnackBar(navigationView,"If You Are not Pregnant,Please Use Our Menstruation Section",Snackbar.LENGTH_SHORT);
                    return false;
                }
                break;


        }
        return true;

    }
}
