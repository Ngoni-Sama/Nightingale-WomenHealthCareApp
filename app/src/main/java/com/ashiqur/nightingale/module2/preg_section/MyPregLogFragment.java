package com.ashiqur.nightingale.module2.preg_section;


import android.app.Activity;
import android.app.Dialog;
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
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.ashiqur.nightingale.data.ThreeItemDataModel;
import com.ashiqur.nightingale.module2.MainActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import hivatec.ir.suradapter.SURAdapter;

import com.ashiqur.nightingale.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyPregLogFragment extends Fragment {

    private static final String TAG = "MY_PREG_LOGFRAGMENT";
    private static String filename="pregnancy_log.xls";
    private Context context;
    private MainActivity activity;
    private View view;
    private HashMap<String,String> xlsLogData;

    private ArrayList<ThreeItemDataModel> itemList;
    private SURAdapter recyclerViewAdapter;
    RecyclerView recyclerView;
    Button buttonAddNewLog;


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

    public MyPregLogFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_preg_log, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeXmlVariables(view);
        initializeJavaVariables();

    }
    private void updateLog()
    {
        try {
            xlsLogData= activity.getFileUtil().readAllDataFromXls(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //initializing the itemList
        itemList = new ArrayList<>();
        //adding some items to our list
        for(int i=1;;i++)//should start from row 1 for appending
        {
            String date=xlsLogData.get("["+i+"]"+"[0]");
            String symptom=xlsLogData.get("["+i+"]"+"[1]");
            String moreInfo=xlsLogData.get("["+i+"]"+"[2]");
            Log.wtf(TAG,"Data Read :"+date +" "+symptom+ ""+moreInfo);
            if(date==null || date.equals("") )break;
            itemList.add(new ThreeItemDataModel(date,symptom,moreInfo));
        }
        Collections.sort(itemList);
        Log.wtf(TAG,"Read "+itemList.size()+" Recycler View Logs Successfully!");
        //creating recyclerView recyclerViewAdapter
        recyclerViewAdapter = new SURAdapter(itemList);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.notifyDataSetChanged();

    }
    private void initializeJavaVariables() {


        try {
            activity.getFileUtil().createXlsFile(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
        updateLog();
        buttonAddNewLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyPregLogFragment.CustomDialogClass cdd=new MyPregLogFragment.CustomDialogClass(activity);
                cdd.show();

            }
        });
    }

    private void initializeXmlVariables(View view) {
        this.view=view;
        recyclerView = view.findViewById(R.id.recyclerview_my_preg_log);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        buttonAddNewLog=view.findViewById(R.id.btn_addlog_frag_my_preg_log);

    }

    public void switchFragment(int id)
    {
        Fragment fragment=null;
        switch (id)
        {



        }
        if(fragment!=null) {
            FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.framelayout_content_main_activity, fragment);
            ft.commit();
        }

    }

    class CustomDialogClass extends Dialog implements
            android.view.View.OnClickListener {

        Activity c;
        public Dialog d;
        Button btnConfirm;
        EditText eTSymptom,eTDetails;
        DatePicker datePicker;

        public CustomDialogClass(Activity a) {
            super(a);
            // TODO Auto-generated constructor stub
            this.c = a;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.custom_dialog_addnewlog_preg_frag);

            btnConfirm = findViewById(R.id.dialog_btn_confirm_preg_addlog);
            eTSymptom=findViewById(R.id.dialog_et_symptom_preg_addlog);
            eTDetails=findViewById(R.id.dialog_et_details_preg_addlog);
            datePicker=findViewById(R.id.dialog_datepicker_preg_frag);
            btnConfirm.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            String[] data=new String[3];
            int month=(datePicker.getMonth() +1);

            data[0]= datePicker.getDayOfMonth()+"-" + month +"-"+datePicker.getYear();
            data[1]=eTSymptom.getText().toString().trim();
            data[2]=eTDetails.getText().toString().trim();

            if(!data[1].equals(""))
            {
                activity.getFileUtil().appendToXls(filename,data);
                updateLog();
                dismiss();
            }
            else
            {
                eTSymptom.requestFocus();
                eTSymptom.setError("Mandatory Field Empty !");
            }

        }
    }

}
