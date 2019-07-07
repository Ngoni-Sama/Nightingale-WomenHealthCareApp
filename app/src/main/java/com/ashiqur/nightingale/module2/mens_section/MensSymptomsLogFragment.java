package com.ashiqur.nightingale.module2.mens_section;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ashiqur.nightingale.data.ThreeItemDataModel;
import com.google.android.material.snackbar.Snackbar;
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
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;

import com.ashiqur.nightingale.module2.MainActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import hivatec.ir.suradapter.SURAdapter;
import com.ashiqur.nightingale.R;

import static com.ashiqur.nightingale.ashiqur_util.UiUtil.showSnackBar;

/*** A simple {@link Fragment} subclass. */

public class MensSymptomsLogFragment extends Fragment {

    private static final String TAG = "MENS_SYMPTOMS_LOG_FRAGMENT";
    private static String filename="menstruation_log.xls";

    private Context context;
    private View view;
    private HashMap<String,String> xlsLogData;

    private SURAdapter recyclerViewAdapter;
    private ArrayList<ThreeItemDataModel> itemList;
    //xml
    RecyclerView recyclerView;
    Button buttonAddNewLog;


    public void onAttach(Context c) {
        super.onAttach(c);

        Activity a;

        context=c;

    }

    public MensSymptomsLogFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.wtf(TAG,"Inside onCreateView");
        return inflater.inflate(R.layout.fragment_mens_symptoms, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeXmlVariables(view);
        initializeJavaVariables();
        Log.wtf(TAG,"Inside onViewCreated");

    }
    private void updateLog()
    {
        try {
            xlsLogData= ((MainActivity)context).getFileUtil().readAllDataFromXls(filename);
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
            ((MainActivity)context).getFileUtil().createXlsFile(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
        updateLog();
        buttonAddNewLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialogClass cdd=new CustomDialogClass(((MainActivity)context));
                cdd.show();

            }
        });
    }

    private void initializeXmlVariables(View view) {
        this.view=view;
        recyclerView = view.findViewById(R.id.recyclerview_mylog);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        buttonAddNewLog=view.findViewById(R.id.btn_addlog_frag_my_log);

    }
    public void switchFragment(int id)
    {
        Fragment fragment=null;
        switch (id)
        {



        }
        if(fragment!=null) {
            FragmentTransaction ft = ((MainActivity)context).getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.framelayout_content_main_activity, fragment);
            ft.commit();
        }

    }

    class CustomDialogClass extends Dialog implements
            android.view.View.OnClickListener {

        private static final int TOTAL_SYMPTOMS = 9;
        private final int[] cbIDS = new int[] {
               R.id.cb,
               R.id.cb2,
               R.id.cb3,
               R.id.cb4,
               R.id.cb5,
               R.id.cb6,
               R.id.cb7,
               R.id.cb8,
               R.id.cb9,

        };
        Activity c;
        public Dialog d;
        Button btnConfirm;
        DatePicker datePicker;
        CheckBox[] checkBoxes;
        EditText eTdetails;

        public CustomDialogClass(Activity a) {
            super(a);
            // TODO Auto-generated constructor stub
            this.c = a;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.custom_dialog_addnewlog_mens_frag);
            btnConfirm = findViewById(R.id.dialog_btn_confirm_mens_addlog);
            btnConfirm.setOnClickListener(this);
            datePicker = findViewById(R.id.dialog_datepicker_mens_frag); // initiate a date picker
            checkBoxes=new CheckBox[TOTAL_SYMPTOMS];
            for (int i = 0; i <TOTAL_SYMPTOMS ; i++) {
                checkBoxes[i]=findViewById(cbIDS[i]);
            }
            eTdetails=findViewById(R.id.et_extra_note);

        }

        @Override
        public void onClick(View v) {
            String[] data=new String[3];
            int month=(datePicker.getMonth() +1);

            data[0]= datePicker.getDayOfMonth()+"-" + month +"-"+datePicker.getYear();
            data[2]= eTdetails.getText().toString().trim();

            StringBuffer str=new StringBuffer();
            for (CheckBox c:
                 checkBoxes) {
                if(c.isChecked())str.append(c.getText().toString().trim()+"\n");
            }
            data[1]=str.toString();
            if(!data[1].equals("") || !data[2].equals(""))
            {
                if(data[1].equals(""))data[1]="-";
                if(data[2].equals(""))data[2]="-";
                ((MainActivity)context).getFileUtil().appendToXls(filename,data);
                updateLog();
                dismiss();
            }
            else
                {
                    showSnackBar(view,"Please Select Atleast One Symptom or Add some notes", Snackbar.LENGTH_SHORT);
                    dismiss();
                }

        }
    }
}
