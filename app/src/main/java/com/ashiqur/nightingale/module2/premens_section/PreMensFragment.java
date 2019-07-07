package com.ashiqur.nightingale.module2.premens_section;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.ashiqur.nightingale.module2.BlogFragment;
import com.ashiqur.nightingale.module2.FaqFragment;
import com.ashiqur.nightingale.module2.MainActivity;

import com.ashiqur.nightingale.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PreMensFragment extends Fragment implements BottomNavigationView.OnNavigationItemSelectedListener{


    private static final String TAG = "PREMENS-FRAGMENT";
    private Context context;
    private View view;

    @Override
    public void onAttach(Context c) {
        super.onAttach(c);

        Activity a;

        context=c;


    }

    public PreMensFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_premens, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeXmlVariables(view);
        initializeJavaVariables();
        setHasOptionsMenu(false);
        switchFragment(R.id.item_bottomnav_blog_frag_premens);
    }

    private void initializeJavaVariables(){
    }

    private void initializeXmlVariables(View view) {
        this.view=view;
        BottomNavigationView bottomNavigationView=((MainActivity)context).getBottomNavigationView();
        bottomNavigationView.getMenu().clear();
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.inflateMenu(R.menu.menu_bottom_navigation_frag_premens);
        bottomNavigationView.setVisibility(View.VISIBLE);

    }

    public void switchFragment(int id)
    {
        Bundle b;
        Fragment fragment=null;
        switch (id)
        {


            case R.id.item_bottomnav_blog_frag_premens:
                fragment=new BlogFragment();
                b=new Bundle();
                b.putString("url home","www.kidshealth.org");
                fragment.setArguments(b);
                break;

            case R.id.item_bottomnav_q_n_a_frag_premens:
                fragment=new FaqFragment();
                b=new Bundle();
                b.putString("filename","faq_premens.xls");
                fragment.setArguments(b);
                break;


        }
        if(fragment!=null) {
            FragmentTransaction ft = ((MainActivity)context).getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.framelayout_frag_premens,fragment,((MainActivity)context).getSupportFragmentManager().getBackStackEntryCount()+"");
            ft.commit();
            //ft.addToBackStack(null);
        }

    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.item_bottomnav_blog_frag_premens:
                switchFragment(R.id.item_bottomnav_blog_frag_premens);
                return true;

            case R.id.item_bottomnav_q_n_a_frag_premens:
                switchFragment(R.id.item_bottomnav_q_n_a_frag_premens);
                return true;


        }
        return false;
    }
}
