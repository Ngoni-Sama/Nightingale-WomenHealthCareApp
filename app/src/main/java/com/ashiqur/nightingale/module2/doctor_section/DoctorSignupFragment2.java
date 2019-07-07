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

import com.ashiqur.nightingale.module1.LoginActivity;
import com.ashiqur.nightingale.module2.MainActivity;

import com.ashiqur.nightingale.R;
import static com.ashiqur.nightingale.ashiqur_util.UiUtil.showAlertDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class DoctorSignupFragment2 extends Fragment {
    private Context context;
    private MainActivity mainActivity;
    private LoginActivity loginActivity;


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


    public DoctorSignupFragment2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doctor_signup_fragment2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(getArguments()!=null)
        {
            Log.wtf("Signup Done",getArguments().getStringArrayList("sectors").toString());
        }
        view.findViewById(R.id.btn_continue_doc_signup).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showAlertDialog(context,"All Done,Thank You !","We'll Get Back to You As soon as we verify you","Ok",R.color.colorPrimaryDark,R.color.colorBackground);
            }
        });
    }
}
