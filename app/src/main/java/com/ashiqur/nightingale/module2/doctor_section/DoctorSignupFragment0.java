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
import android.widget.CheckBox;

import com.ashiqur.nightingale.data.Doctor;
import com.ashiqur.nightingale.module1.LoginActivity;
import com.ashiqur.nightingale.module2.MainActivity;

import java.util.ArrayList;

import com.ashiqur.nightingale.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DoctorSignupFragment0 extends Fragment {

    private Context context;
    private MainActivity mainActivity;
    private LoginActivity loginActivity;
    //xml vars
    private CheckBox[] cbSectors;
    private int[] cbIDs={R.id.cb_sectors_doc_signup_0,R.id.cb_sectors_doc_signup_1,R.id.cb_sectors_doc_signup_2,R.id.cb_sectors_doc_signup_3};


    public DoctorSignupFragment0() {
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
        return inflater.inflate(R.layout.fragment_choose_doc_sector, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeXmlVariables(view);
        initializeJavaVariables();
    }

    private void initializeJavaVariables() {

    }

    private void initializeXmlVariables(View view) {

       try {
           ((MainActivity) context).getBottomNavigationView().getMenu().clear();
           ((MainActivity) context).getBottomNavigationView().setVisibility(View.INVISIBLE);
       }catch (ClassCastException ignored){}

       cbSectors =new CheckBox[Doctor.sectors.length];
        for (int i = 0; i < Doctor.sectors.length; i++) {
            cbSectors[i]=view.findViewById(cbIDs[i]);
            cbSectors[i].setText(Doctor.sectors[i]);

        }
        view.findViewById(R.id.btn_next_doc_signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Fragment fragment =new DoctorSignupFragment1();

                ArrayList<String> selected=new ArrayList<>();

                for (CheckBox c :
                        cbSectors) {
                    if(c.isChecked())
                    {
                     selected.add(c.getText().toString());
                    }

                }
                Bundle b=new Bundle();
                b.putStringArrayList("sectors",selected);

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
        });
    }



}
