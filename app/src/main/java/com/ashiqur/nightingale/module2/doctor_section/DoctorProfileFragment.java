package com.ashiqur.nightingale.module2.doctor_section;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ashiqur.nightingale.R;

import java.util.Random;


import static com.ashiqur.nightingale.ashiqur_util.UiUtil.showAlertDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class DoctorProfileFragment extends Fragment {

    private static final String TAG = "Doctor-Profile-Fragment";
    private Context context;
    private TextView tvW;
    private TextView tvName;
    private TextView tvQ;

    public DoctorProfileFragment() {
        // Required empty public constructor
    }
    @Override
    public void onAttach(Context c)
    {
        super.onAttach(c);

        Activity a;

        context=c;


    }

    void initializeXmlVariables(View view)
    {
        Log.wtf(TAG,"1");
        Bundle b=getArguments();
        Log.wtf(TAG,"2");

        tvName= ((TextView)view.findViewById(R.id.tv_doc_name));
        Log.wtf(TAG,b.getString("name"));
        tvName.setText(b.getString("name"));

        tvQ= ((TextView)view.findViewById(R.id.tv_doc_quali));
        Log.wtf(TAG,b.getString("qualifications"));

        tvQ.setText(b.getString("qualifications"));
        tvW= ((TextView)view.findViewById(R.id.tv_doc_work));

        Log.wtf(TAG,b.getString("workplace"));

        tvW.setText(b.getString("workplace"));
       ((ImageView)view.findViewById(R.id.imageView_doc_profile)).setImageResource(b.getInt("photoresid"));
        Log.wtf(TAG,tvQ.getText().toString());
        System.out.println("WWFAFSAFS");

        view.findViewById(R.id.btn_fix_appointment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hr= new Random().nextInt((9 - 7) + 1) + 7;
                int minute= new Random().nextInt((30) + 1);
                int minute2=minute+15;
                int serial=new Random().nextInt((45 - 10) + 1) + 10;
                String msg="\nYour Serial Number:" +  serial
                        +"\n\n" + "Estimated Time :"+
                       hr+"."+minute
                       +" pm"+"-"+hr+"."+minute2+" pm";

                showAlertDialog(context,"Your Appointment Has Been Fixed!",msg,"OK",R.color.colorBackground,R.color.colorPrimaryDark);
            }
        });

    }



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.wtf(TAG,"onCreateView");

        return inflater.inflate(R.layout.fragment_doctor_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.wtf(TAG,"onViewCreated");

        initializeXmlVariables(view);

    }


}
