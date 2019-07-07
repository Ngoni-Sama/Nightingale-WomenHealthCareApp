package com.ashiqur.nightingale.module2;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ashiqur.nightingale.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class UnavailableFeatureFragment extends Fragment {
    private Context context;

    @Override
    public void onAttach(Context c) {
        super.onAttach(c);

        Activity a;

        context=c;


    }

    public UnavailableFeatureFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.unavailable_feature, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity)context).getBottomNavigationView().setVisibility(View.INVISIBLE );

    }
}
