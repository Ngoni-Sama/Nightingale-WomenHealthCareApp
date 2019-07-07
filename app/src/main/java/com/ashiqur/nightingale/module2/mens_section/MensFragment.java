package com.ashiqur.nightingale.module2.mens_section;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.ActionBar;

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
public class MensFragment extends Fragment implements BottomNavigationView.OnNavigationItemSelectedListener{
    private static final String TAG = "MENS-FRAGMENT";
    private Context context;
    private View view;

    @Override
    public void onAttach(Context c) {
        super.onAttach(c);

        Activity a;

        context=c;


    }

    public MensFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mens, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(false);
        initializeXmlVariables(view);
        initializeJavaVariables();
        switchFragment(R.id.item_bottomnav_my_log_frag_mens);
    }
    private void initializeJavaVariables(){
        //mSectionsPageAdapter = new SectionsPageAdapter(activity.getSupportFragmentManager());
    }

    private void initializeXmlVariables(View view) {
        this.view=view;
        // Set up the ViewPager with the sections adapter.

        BottomNavigationView bottomNavigationView=((MainActivity)context).getBottomNavigationView();
        bottomNavigationView.getMenu().clear();
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.inflateMenu(R.menu.menu_bottom_navigation_frag_mens);

        bottomNavigationView.setVisibility(View.VISIBLE);

    }

    public void switchFragment(int id)
    {
        Fragment fragment=null;
        Bundle b;
        switch (id)
        {
            case R.id.item_bottomnav_my_log_frag_mens:
                fragment=new MensLogFragment();
                break;
            case R.id.item_bottomnav_blog_frag_mens:
                fragment=new BlogFragment();
                 b=new Bundle();
                b.putString("url home","www.youngwomenshealth.org");
                fragment.setArguments(b);
                break;
            case R.id.item_bottomnav_livechat_frag_mens:
                fragment=new UnavailableFeatureFragment();
                break;
            case R.id.item_bottomnav_q_n_a_frag_mens:
                fragment=new FaqFragment();
                b=new Bundle();
                b.putString("filename","faq_mens.xls");
                fragment.setArguments(b);
                break;


        }
        if(fragment!=null) {
            FragmentTransaction ft = ((MainActivity)context).getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.framelayout_frag_mens,fragment,((MainActivity)context).getSupportFragmentManager().getBackStackEntryCount()+"");

            ft.commit();
           // ft.addToBackStack(null);
        }

    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.item_bottomnav_my_log_frag_mens:
//                Log.wtf(TAG,"My Log");
//                switchFragment(R.id.item_bottomnav_my_log_frag_mens);
//                return true;
//            case R.id.item_bottomnav_blog_frag_mens:
//                switchFragment(R.id.item_bottomnav_blog_frag_mens);
//                return true;
//            case R.id.item_bottomnav_livechat_frag_mens:
//                Log.wtf(TAG,"Live Chat");
//                switchFragment(R.id.item_bottomnav_livechat_frag_mens);
//                return true;
//            case R.id.item_bottomnav_q_n_a_frag_mens:
//                switchFragment(R.id.item_bottomnav_q_n_a_frag_mens);

//    }
        switchFragment(item.getItemId());
                return true;

    }


}
