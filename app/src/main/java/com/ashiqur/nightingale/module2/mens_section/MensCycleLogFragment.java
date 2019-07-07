package com.ashiqur.nightingale.module2.mens_section;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ashiqur.nightingale.data.TwoItemDataModel;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.ashiqur.nightingale.module2.MainActivity;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import hivatec.ir.suradapter.SURAdapter;

import com.ashiqur.nightingale.R;
import static com.ashiqur.nightingale.ashiqur_util.UiUtil.showSnackBar;

/**
 * A simple {@link Fragment} subclass.
 */
public class MensCycleLogFragment extends Fragment {

    private static final String TAG = "Mens-Cycle-Log-Fragment";
    public static final String Period_Start="Start";
    public static final String Period_End="End";

    private static String filename="menstruation_cycle_log.xls";

    private Context context;
    private View view;
    private HashMap<String,String> xlsLogData;

    private SURAdapter recyclerViewAdapter;
    private ArrayList<TwoItemDataModel> itemList;
    private ArrayList<TwoItemDataModel> sortedItemList;

    //xml
    private RecyclerView recyclerView;
    Button buttonAddNewStartDate,buttonAddNewEndDate;
    private TextView tvNextPeriod;


    public void onAttach(Context c) {
        super.onAttach(c);

        Activity a;

        context=c;

    }
    public MensCycleLogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.wtf(TAG,"Inside onCreateView");
        return inflater.inflate(R.layout.fragment_mens_cycle, container, false);
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
        sortedItemList=new ArrayList<>();
        //adding some items to our list
        for(int i=1;;i++)//should start from row 1 for appending
        {
            String event=xlsLogData.get("["+i+"]"+"[0]");
            String date=xlsLogData.get("["+i+"]"+"[1]");

            Log.wtf(TAG,"Data Read :"+event +" "+date );
            if(event==null || event.equals("") )break;

            itemList.add(new TwoItemDataModel(event,date));
            sortedItemList.add(new TwoItemDataModel(event,date));
        }
        Collections.sort(sortedItemList);
        Log.wtf(TAG,"Read "+itemList.size()+" Recycler View Logs Successfully!");
        //creating recyclerView recyclerViewAdapter
        recyclerViewAdapter = new SURAdapter(itemList);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.notifyDataSetChanged();

        if(itemList.size()!=0)predictNextDate();
        else tvNextPeriod.setVisibility(View.INVISIBLE);


    }

    private void predictNextDate() {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.CANADA);

        try {

            String toyBornTime="";
            if(itemList.get(itemList.size()-1).details.trim().equals(Period_Start))
                toyBornTime=itemList.get(itemList.size()-1).date.trim();
            else
                toyBornTime=itemList.get(itemList.size()-2).date.trim();

            Date oldDate = dateFormat.parse(toyBornTime);
            System.out.println(oldDate);

            Date currentDate = new Date();

            long diff = currentDate.getTime() - oldDate.getTime();
            long seconds = diff / 1000;
            long minutes = seconds / 60;
            long hours = minutes / 60;
            long days = hours / 24;

            tvNextPeriod.setVisibility(View.VISIBLE);
            tvNextPeriod.setText("You are "+ (28-days)+" days away from your next period");
            if (oldDate.before(currentDate)) {

                Log.e("oldDate", "is previous date");
                Log.e("Difference: ", " seconds: " + seconds + " minutes: " + minutes
                        + " hours: " + hours + " days: " + days);

            }

            // Log.e("toyBornTime", "" + toyBornTime);

        } catch (ParseException e) {

            e.printStackTrace();
        }
    }

    private void initializeJavaVariables() {
        try {
            ((MainActivity)context).getFileUtil().createXlsFile(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
        updateLog();
        buttonAddNewStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MensCycleLogFragment.CustomDialogClass cdd=new MensCycleLogFragment.CustomDialogClass(((MainActivity)context),true);
                cdd.show();

            }
        });
        buttonAddNewEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MensCycleLogFragment.CustomDialogClass cdd=new MensCycleLogFragment.CustomDialogClass(((MainActivity)context),false);
                cdd.show();

            }
        });
    }

    private void initializeXmlVariables(View view) {
        this.view=view;
        ((MainActivity)context).getBottomNavigationView().setVisibility(View.VISIBLE);

        recyclerView = view.findViewById(R.id.recyclerview_mylog);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        buttonAddNewStartDate =view.findViewById(R.id.btn_set_startdate);
        buttonAddNewEndDate=view.findViewById(R.id.btn_set_enddate);
        tvNextPeriod=view.findViewById(R.id.tv_next_period);
    }


    class CustomDialogClass extends Dialog implements
            android.view.View.OnClickListener {


        Boolean isStart;
        Activity c;


        public Dialog d;
        Button btnConfirm;
        DatePicker datePicker;

        public CustomDialogClass(Activity a,Boolean isStart) {
            super(a);
            // TODO Auto-generated constructor stub
            this.c = a;
            this.isStart=isStart;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.custom_dialog_addnewlog_cycle);

            btnConfirm = findViewById(R.id.dialog_btn_confirm_cycle_addlog);

            btnConfirm.setOnClickListener(this);

            datePicker = findViewById(R.id.dialog_datepicker_cycle_frag); // initiate a date picker


        }

        @Override
        public void onClick(View v) {
            String[] data=new String[3];
            data[0]=isStart?Period_Start:Period_End;
            int month=(datePicker.getMonth() +1);
            data[1]= datePicker.getDayOfMonth()+"-" +month  +"-"+datePicker.getYear();


            if(itemList.size()==0 && !isStart)
            {
                showSnackBar(view,"Please Start a period Cycle,in order to end it",Snackbar.LENGTH_SHORT);
                Log.wtf(TAG,"Please Start a period Cycle,in order to end it");
                dismiss();
                return;
            }
            else if(itemList.size()!=0 && itemList.get(itemList.size()-1).details.trim().equals(Period_Start) && isStart) {
                Log.wtf(TAG,"Current Period Cycle has not ended yet !");
                showSnackBar(view,"Current Period Cycle has not ended yet !", Snackbar.LENGTH_SHORT);
                dismiss();
                return;
            }
            else if(itemList.size()!=0 && itemList.get(itemList.size()-1).details.trim().equals(Period_End) && !isStart) {
                showSnackBar(view,"Please Start a period Cycle,in order to end it",Snackbar.LENGTH_SHORT);
                Log.wtf(TAG,"Please Start a period Cycle,in order to end it");
                dismiss();
                return;
            }

            if(!data[1].equals(""))
            {
                ((MainActivity)context).getFileUtil().appendToXls(filename,data);
                updateLog();
                dismiss();
            }


        }
    }




}
