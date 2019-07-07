package com.ashiqur.nightingale.module2.doctor_section;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ashiqur.nightingale.module1.LoginActivity;
import com.ashiqur.nightingale.module2.MainActivity;

import com.ashiqur.nightingale.R;
/**
 * A simple {@link Fragment} subclass.
 */
public class DoctorSignupFragment1 extends Fragment {


    private Context context;

    private MainActivity mainActivity;
    private LoginActivity loginActivity;

    public DoctorSignupFragment1() {
        // Required empty public constructor

    }
    @Override
    public void onAttach(Context c) {
        super.onAttach(c);
        Activity a;
        context=c;
        if (c instanceof Activity){
            a=(Activity) c;
            if(a instanceof MainActivity)
                mainActivity =(MainActivity) a;
            if(a instanceof LoginActivity)
                loginActivity =(LoginActivity) a;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doctor_signup_fragment1, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.btn_signup_doc_signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getArguments()!=null)
                {
                    Fragment fragment=new DoctorSignupFragment2();
                    Bundle b=new Bundle();
                    b.putStringArrayList("sectors",getArguments().getStringArrayList("sectors"));
                    fragment.setArguments(b);
                    if(mainActivity !=null) {
                        FragmentTransaction ft = mainActivity.getSupportFragmentManager().beginTransaction();
                        fragment.setArguments(b);
                        ft.replace(R.id.framelayout_content_main_activity, fragment);
                        ft.addToBackStack(null);
                        ft.commit();
                    }
                    else
                    {
                        FragmentTransaction ft = loginActivity.getSupportFragmentManager().beginTransaction();
                        fragment.setArguments(b);
                        ft.replace(R.id.fragment_container_login_activity, fragment);
                        ft.addToBackStack(null);
                        ft.commit();
                    }
                }
            }
        });
    }
}
