package com.ashiqur.nightingale.module2.preg_section;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.ashiqur.nightingale.module2.BlogFragment;
import com.ashiqur.nightingale.module2.FaqFragment;
import com.ashiqur.nightingale.module2.MainActivity;
import com.ashiqur.nightingale.module2.UnavailableFeatureFragment;


import com.ashiqur.nightingale.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PregFragment extends Fragment implements BottomNavigationView.OnNavigationItemSelectedListener{

    private static final String TAG = "PREG-FRAGMENT";
    private Context context;
    private MainActivity activity;
    private View view;

    @Override
    public void onAttach(Context c) {
        super.onAttach(c);

        Activity a;

        context=c;
        if (c instanceof Activity){
            a=(Activity) c;
            if(a instanceof MainActivity)
                activity=(MainActivity) a;
        }

    }

    public PregFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_preg, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(false);
        initializeXmlVariables(view);
        initializeJavaVariables();
        switchFragment(R.id.item_bottomnav_my_log_frag_preg);
    }

    private void initializeJavaVariables() {

    }

    private void initializeXmlVariables(View view) {
        this.view=view;
        BottomNavigationView bottomNavigationView=activity.getBottomNavigationView();
        bottomNavigationView.getMenu().clear();
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.inflateMenu(R.menu.menu_bottom_navigation_frag_preg);
        bottomNavigationView.setVisibility(View.VISIBLE);
    }

    public void switchFragment(int id)
    {
        Bundle b;
        Fragment fragment=null;
        switch (id)
        {
            case R.id.item_bottomnav_my_log_frag_preg:
                fragment=new MyPregLogFragment();
                break;
            case R.id.item_bottomnav_blog_frag_preg:
                fragment=new BlogFragment();
                 b=new Bundle();
                b.putString("url home","www.babygaga.com");
                fragment.setArguments(b);
                break;
            case R.id.item_bottomnav_livechat_frag_preg:
                fragment=new UnavailableFeatureFragment();
                break;
            case R.id.item_bottomnav_q_n_a_frag_preg:
                fragment=new FaqFragment();
                b=new Bundle();
                b.putString("filename","faq_preg.xls");
                fragment.setArguments(b);
                break;
        }
        if(fragment!=null) {
            FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.framelayout_frag_preg,fragment,((MainActivity)context).getSupportFragmentManager().getBackStackEntryCount()+"");
            ft.commit();
            //ft.addToBackStack(null);
        }

    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item){
        switch (item.getItemId()) {
            case R.id.item_bottomnav_my_log_frag_preg:
                Log.wtf(TAG,"My Log");
                switchFragment(R.id.item_bottomnav_my_log_frag_preg);
                return true;
            case R.id.item_bottomnav_blog_frag_preg:
                switchFragment(R.id.item_bottomnav_blog_frag_preg);
                return true;
            case R.id.item_bottomnav_livechat_frag_preg:
                Log.wtf(TAG,"Live Chat");
                switchFragment(R.id.item_bottomnav_livechat_frag_preg);
                return true;
            case R.id.item_bottomnav_q_n_a_frag_preg:
                switchFragment(R.id.item_bottomnav_q_n_a_frag_preg);
                return true;
        }
        return false;
    }
}
