package com.ashiqur.nightingale.module2.mens_section;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ashiqur.nightingale.ashiqur_util.SectionsPageAdapter;
import com.ashiqur.nightingale.module2.MainActivity;

import com.ashiqur.nightingale.R;
/**
 * A simple {@link Fragment} subclass.
 */
public class MensLogFragment extends Fragment {


    private static final String TAG = "Mens-Log-Fragment";
    private SectionsPageAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;
    private Context context;
    private TabLayout tabLayout;

    public MensLogFragment() {
        // Required empty public constructor
    }
    @Override
    public void onAttach(Context c) {
        super.onAttach(c);
        Activity a;
        context=c;

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.wtf(TAG,"Inside onCreateView");
        return inflater.inflate(R.layout.fragment_mens_log, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSectionsPageAdapter = new SectionsPageAdapter(((MainActivity)context).getSupportFragmentManager());

        mViewPager = ((MainActivity)context).findViewById(R.id.tabslayout_container);
        setupViewPager(mViewPager);
        tabLayout =  ((MainActivity)context).findViewById(R.id.tabslayout);

        tabLayout.setupWithViewPager(mViewPager);
        selectPage(1);
        Log.wtf(TAG,"Inside onViewCreated");
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getChildFragmentManager());
        adapter.addFragment(new MensCycleLogFragment() , "CYCLE");
        adapter.addFragment(new MensSymptomsLogFragment(), "SYMPTOMS");
        viewPager.setAdapter(adapter);
    }
    void selectPage(int pageIndex){
        Log.wtf(TAG,"Selecting From :"+mViewPager.getCurrentItem());
        tabLayout.setScrollPosition(pageIndex,0f,true);
        mViewPager.setCurrentItem(pageIndex);
    }

}
