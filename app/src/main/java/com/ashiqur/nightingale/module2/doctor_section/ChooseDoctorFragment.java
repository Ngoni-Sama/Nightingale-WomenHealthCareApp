package com.ashiqur.nightingale.module2.doctor_section;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ashiqur.nightingale.R;
import com.ashiqur.nightingale.data.Doctor;
import com.ashiqur.nightingale.module2.MainActivity;

import java.util.ArrayList;

import hivatec.ir.suradapter.ItemHolder;
import hivatec.ir.suradapter.OnItemClickListener;
import hivatec.ir.suradapter.SURAdapter;


public class ChooseDoctorFragment extends Fragment {

    // TODO: Rename and change types and number of parameters

    private static final String TAG = "CHOOSE-DOC-FRAGMENT";
    private Context context;
    private View view;
    private RecyclerView recyclerView;
    private ArrayList<Doctor> doctorList;
    @Override
    public void onAttach(Context c) {
        super.onAttach(c);

        Activity a;

        context=c;

    }

    public ChooseDoctorFragment() {
        // Required empty public constructor
    }





    private void addHardCodedDoctors()
    {
        doctorList = new ArrayList<>();

        doctorList.add( new Doctor("Dr. Farial Jahan Maisha","MBBS","Sir Salimullah Medical College",R.drawable.doc_farial_jahan));

        doctorList.add( new Doctor("Dr. Nudrat Afrida","MBBS","Ibrahim Medical College-IMC(BIRDEM)",R.drawable.ic_person_colorprimary_24dp));
        doctorList.add( new Doctor("Dr. Humaira amreen chowdhury","MBBS(2018) Sir Salimullah Medical College","SSMC",R.drawable.ic_person_colorprimary_24dp));
        doctorList.add( new Doctor(" Dr. Shameema Nahid","MBBS Shaheed ziaur rahman medical college","MO in Module General Hospital, Paribagh",R.drawable.ic_person_colorprimary_24dp));

        //Collections.sort(doctorList);
        Log.wtf(TAG,"Read "+ doctorList.size()+" Recycler View Logs Successfully!");
        //creating recyclerView recyclerViewAdapter
        SURAdapter s=new SURAdapter(doctorList);
        s.setOnItemClickListener(Doctor.class, new OnItemClickListener<Doctor>() {
            @Override
            public void onItemClicked(Doctor doctor, ItemHolder itemHolder) {
                //TODO:switch Fragment with the selected Doctor Info
                if(doctor.getName().equals("Dr. Farial Jahan Maisha"))
                {
                    Fragment fragment=new DoctorProfileFragment();
                    FragmentTransaction ft = ((MainActivity)context).getSupportFragmentManager().beginTransaction();
                    Bundle b=new Bundle();
                    b.putString("name",doctor.getName());
                    b.putString("qualifications",doctor.getQualifications());
                    b.putString("workplace",doctor.getCurrentWorkPlace());
                    b.putInt("photoresid",doctor.getPhotoImageResId());
                    fragment.setArguments(b);
                    ft.replace(R.id.framelayout_content_main_activity, fragment);
                    ft.addToBackStack(null);
                    ft.commit();
                }


            }
        });
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(s);
        s.notifyDataSetChanged();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_choose_doctor, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeXmlVariables(view);


    }
    private void initializeXmlVariables(View view) {
        this.view=view;
        recyclerView =view.findViewById(R.id.recyclerview_choose_doc);
        addHardCodedDoctors();
    }
}
