package com.ashiqur.nightingale.module2;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import com.ashiqur.nightingale.ashiqur_util.FirebaseUtil;

import java.util.HashMap;


import com.ashiqur.nightingale.R;
import s.ashiqur.lady.data.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserInfoFragment extends Fragment {

    //xml
    private TextView textViewUserAge,textViewUserPhone,textViewUserEmail;
    private Switch switchIsMarried,switchIsPregnant,switchIsStartedMenstruatingYet,switchIsPregnantBefore;
    private ProgressBar progressBar;
    private Button buttonUpdate;
    //java
    private static final String TAG = "USER-INFO-FRAGMENT";


    private Context context;

    private String newUserPhn;
    private HashMap[] thisUser;

    private FirebaseUtil firebaseUtil;
    private View.OnClickListener handleClick;
    private boolean[] isDataUpdateSuccessful;
    private Switch switchIsPlanningToHaveKids;

    public UserInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Context c){
        super.onAttach(c);

        Activity a;

        context=c;


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (this.getArguments() != null) {
            newUserPhn = this.getArguments().getString("new user phone");
            Log.wtf(TAG,"INSIDE USER INFO FRAG" + newUserPhn);
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_userinfo, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(false);

        initializeXmlVariables(view);
        initializeJavaVariables();
        addOnClickListeners();
    }

    private void initializeXmlVariables(View view)
    {
        ((MainActivity)context).getBottomNavigationView().setVisibility(View.INVISIBLE);
        textViewUserAge =view.findViewById(R.id.tv_age_frag_userinfo);
        textViewUserPhone =view.findViewById(R.id.tv_contactno_frag_userinfo);
        textViewUserEmail=view.findViewById(R.id.tv_email_frag_userinfo);

        switchIsMarried=view.findViewById(R.id.switch_married_frag_userinfo);
        switchIsStartedMenstruatingYet=view.findViewById(R.id.switch_startedmens_frag_userinfo);
        switchIsPregnant=view.findViewById(R.id.switch_areupregnant_frag_userinfo);
        switchIsPregnantBefore=view.findViewById(R.id.switch_beenpregbefore_frag_userinfo);
        switchIsPlanningToHaveKids=view.findViewById(R.id.switch_plantohavekids_frag_userinfo);

        buttonUpdate=view.findViewById(R.id.btn_updatedata_frag_userinfo);

        progressBar=view.findViewById(R.id.progressbar_frag_userinfo);



    }
    private void initializeJavaVariables()
    {

        isDataUpdateSuccessful=new boolean[1];
        thisUser=new HashMap[1];
        firebaseUtil=new FirebaseUtil(context);
        firebaseUtil.setFireBaseDatabaseRoot(FirebaseUtil.getReferenceToKey(firebaseUtil.root.child("Users"),newUserPhn));

        loadUserInfoFromDatabase();



    }
    private void addOnClickListeners() {

        handleClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.btn_updatedata_frag_userinfo: {
                        User tUser=((MainActivity)context).getThisUser();

                        updateUserIntoDataBase(tUser);
                        break;
                    }
                }
            }
        };
        buttonUpdate.setOnClickListener(handleClick);

    }
    private void updateUserIntoDataBase(User tUser)
    {


        tUser.setMarried(switchIsMarried.isChecked());
        tUser.setPregnant(switchIsPregnant.isChecked());
        tUser.setPregnantBefore(switchIsPregnantBefore.isChecked());
        tUser.setStartedMenstruating(switchIsStartedMenstruatingYet.isChecked());
        tUser.setPlanningToHaveKids(switchIsPlanningToHaveKids.isChecked());

        final Runnable onTaskFinished=new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.INVISIBLE);
                isDataUpdateSuccessful[0]=false;

            }
        };
        final Runnable onTaskStart =  new Runnable() {

            @Override
            public void run() {
                // Stuff that updates the UI
                progressBar.setVisibility(View.VISIBLE);
                isDataUpdateSuccessful[0]=false;

            }
        };

        firebaseUtil.addDataToFirebase(firebaseUtil.root,tUser, isDataUpdateSuccessful);
        new Thread( new Runnable() {
            @Override
            public void run(){
                ((MainActivity)context).runOnUiThread(onTaskStart);
                while (!isDataUpdateSuccessful[0]){}
                ((MainActivity)context).runOnUiThread(onTaskFinished);
            }
        }).start();




    }
    private synchronized void loadUserInfoFromDatabase()
    {
        thisUser[0]=null;
        firebaseUtil.retrieveDataFromFirebase(firebaseUtil.root, thisUser);
        final Runnable onTaskStop=new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.INVISIBLE);
                isDataUpdateSuccessful[0]=false;
                String age= String.valueOf(thisUser[0].get("age"));
                String email=thisUser[0].get("email").toString();
                String cell=thisUser[0].get("cell").toString();
                boolean isMarried= thisUser[0].get("married").toString().equalsIgnoreCase("true");
                boolean isPregnant=thisUser[0].get("pregnant").toString().equalsIgnoreCase("true");
                boolean isPregnantBefore=thisUser[0].get("pregnantBefore").toString().equalsIgnoreCase("true");
                boolean isStartedMenstruating=thisUser[0].get("startedMenstruating").toString().equalsIgnoreCase("true");
                boolean isPlanningToHaveKids=thisUser[0].get("planningToHaveKids").toString().equalsIgnoreCase("true");


                textViewUserAge.setText(age);
                textViewUserEmail.setText(email);
                textViewUserPhone.setText(cell);
                switchIsMarried.setChecked(isMarried);
                switchIsPregnant.setChecked(isPregnant);
                switchIsPregnantBefore.setChecked(isPregnantBefore);
                switchIsStartedMenstruatingYet.setChecked(isStartedMenstruating);
                if(thisUser[0] !=null)
                {

                    User tUser=new User(Integer.parseInt(age),email,thisUser[0].get("pass").toString(),cell,isMarried,isPregnant,isPregnantBefore,isStartedMenstruating,isPlanningToHaveKids);

                    ((MainActivity)context).setThisUser(tUser);
                    Log.wtf(TAG,"Loaded User :"+tUser);
                }
            }
        };
        final Runnable onTaskStart =  new Runnable() {
            @Override
            public void run() {
                // Stuff that updates the UI
                progressBar.setVisibility(View.VISIBLE);
                isDataUpdateSuccessful[0]=false;

            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                ((MainActivity)context).runOnUiThread(onTaskStart);
                while (thisUser[0]==null){}
                ((MainActivity)context).runOnUiThread(onTaskStop);
            }
        }).start();


    }


}
