package com.ashiqur.nightingale.module2;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.ashiqur.nightingale.ashiqur_util.recyclerViewUtil.SimpleListViewAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import com.ashiqur.nightingale.R;
/**
*A simple {@link Fragment} subclass.
*/
public class FaqFragment extends Fragment {


    private static final String TAG = "FAQ-FRAGMENT";
    private Context context;
    private View view;
    private ListView listViewFaq;
    private HashMap<String,String> xlsFaqData;
    private String filename="faq_premens.xls";
    private ArrayList<String> questionList;
    private ArrayList<String> answerList;
    @Override
    public void onAttach(Context c) {
        super.onAttach(c);

        Activity a;

        context=c;


    }

    public FaqFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(this.getArguments()!=null)
        {
            filename=this.getArguments().getString("filename").trim();
            Log.w(TAG,"Opening File : "+filename);
        }
        return inflater.inflate(R.layout.fragment_faq, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeXmlVariables(view);
        initializeJavaVariables();

    }
    private void updateListView()
    {

        xlsFaqData=((MainActivity)context).getFileUtil().readExcelFileFromAssets(((MainActivity)context),filename);
        //initializing the itemList
        questionList = new ArrayList<>();

        //adding some items to our list
        for(int i=0;;i++)
        {
            String ques=xlsFaqData.get("["+i+"]"+"[0]");
            String answer=xlsFaqData.get("["+i+"]"+"[1]");

            Log.wtf(TAG,"Data Read :"+ques +" "+answer);
            if(ques==null || ques.equals("") )break;
            questionList.add(ques);
            answerList.add(answer);
        }
        //Collections.sort(questionList);
        Log.wtf(TAG,"Read "+questionList.size()+" Recycler View Logs Successfully!");
        //creating recyclerView recyclerViewAdapter

        listViewFaq.setAdapter( new SimpleListViewAdapter(context,questionList) );
    }

    private void initializeJavaVariables() {

        if(filename.equalsIgnoreCase("faq_premens.xls")) {new CustomDialogClass(((MainActivity)context)).show();}
        answerList=new ArrayList<>();
        questionList=new ArrayList<>();
        updateListView();
        listViewFaq.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //UiUtil.showSnackBar(view,answerList.get(position), Snackbar.LENGTH_LONG);

                final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                alertDialog.setTitle("Answer");
                alertDialog.setMessage(answerList.get(position));

                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",getResources().getDrawable(R.drawable.ic_arrow_back_accent_24dp),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                Log.wtf(TAG,"Clicked Dialog Button");
                            }
                        });
                alertDialog.setOnShowListener( new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface arg0) {
                        alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    }
                });
                alertDialog.show();

            }
        });
    }
    private void initializeXmlVariables(View view) {
        ((MainActivity)context).getBottomNavigationView().setVisibility(View.VISIBLE);

        this.view=view;
        listViewFaq=view.findViewById(R.id.listview_faq);
    }


    class CustomDialogClass extends Dialog implements
            android.view.View.OnClickListener {

        Activity c;
        private Button btnConfirm;


        public CustomDialogClass(Activity a) {
            super(a);
            // TODO Auto-generated constructor stub
            this.c = a;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.custom_dialog_faq_intro_premens);
            btnConfirm = findViewById(R.id.dialog_btn_next_faq_premens);
            btnConfirm.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            dismiss();
        }
    }




}
