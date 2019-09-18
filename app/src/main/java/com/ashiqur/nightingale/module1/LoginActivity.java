package com.ashiqur.nightingale.module1;


import android.content.Intent;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ashiqur.nightingale.ashiqur_util.FirebaseUtil;
import com.ashiqur.nightingale.module2.MainActivity;
import com.ashiqur.nightingale.module2.doctor_section.DoctorSignupFragment0;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import com.ashiqur.nightingale.R;
import static com.ashiqur.nightingale.ashiqur_util.UiUtil.showToast;


public class LoginActivity extends AppCompatActivity  {

    private static final String TAG = "LoginActivity";
    //XML Variables
    private Button buttonLogin;
    private Button buttonSignup;
    private Button buttonForgotPass;
    private EditText editTextPhone;
    private EditText editTextPassword;
    private ProgressBar progressBarLogin;
    //Java Variables
    private View.OnClickListener handleClick;
    private FirebaseUtil firebaseUtil;

    private String [] passwordRetrieved=new String [1];
    private EditText editTextCountryCode;
    private Button buttonIamADoctor;
    private boolean isLoginOverride = true;

    private void addOnClickListeners()
    {

        buttonLogin.setOnClickListener(handleClick);
        buttonSignup.setOnClickListener(handleClick);
        buttonForgotPass.setOnClickListener(handleClick);
        buttonIamADoctor.setOnClickListener(handleClick);
    }
    private void initializeXmlVariables()
    {

        editTextCountryCode=findViewById(R.id.et_countrycode_login);
        progressBarLogin=findViewById(R.id.progressbar_login);
        buttonLogin= findViewById(R.id.btn_login);
        buttonSignup=findViewById(R.id.btn_signup);
        buttonIamADoctor=findViewById(R.id.btn_iam_a_doc_frag_login);
        buttonForgotPass=findViewById(R.id.btn_forgot_pass);
        editTextPhone=findViewById(R.id.et_phone_login);
        editTextPassword=findViewById(R.id.et_password_login);


    }
    private void initializeJavaVariables()
    {
        firebaseUtil=new FirebaseUtil(getApplicationContext());
        firebaseUtil.setFireBaseDatabaseRoot(FirebaseUtil.getReferenceToKey(firebaseUtil.root,"Users"));

        handleClick=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId())
                {
                    case R.id.btn_iam_a_doc_frag_login:
                        switchFragment(R.layout.fragment_choose_doc_sector);
                        break;
                    case R.id.btn_login:
                    {

                        String pass=editTextPassword.getText().toString().trim();
                        String phn=editTextPhone.getText().toString().trim();

                        if(!checkUserInputError(phn,pass))return;

                        userLogin(pass,"+"+editTextCountryCode.getText().toString().trim()+phn);
                        break;
                    }
                    case R.id.btn_signup:
                    {
                        //TODO: 1.Verify Using Phone Number 2.Create a New User after phone Verification and store it's data
                        switchFragment(R.layout.fragment_signup);

                        break;
                    }

                }
            }
        };
    }

    private void userLogin(final String pass, final String phn) {

        DatabaseReference userPhoneRef = FirebaseUtil.getReferenceToKey(firebaseUtil.root,phn);

        Log.wtf(TAG,"New User DataBase Ref :"+userPhoneRef);

        progressBarLogin.setVisibility(View.VISIBLE);
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    //check user password

                    userloginUtil(pass,phn);
                }
                else
                {

                    showToast(getApplicationContext(),"User Doesnt Exist,please Sign Up",Toast.LENGTH_LONG);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {
                showToast(getApplicationContext(),databaseError.getMessage(),Toast.LENGTH_SHORT);
            }
        };


        userPhoneRef.addListenerForSingleValueEvent(eventListener);

    }


    boolean checkUserInputError(String phn,String pass)
    {

        Log.wtf(TAG,"User Inputs : "+phn+" "+pass);
        if(pass.isEmpty())
        {
            editTextPassword.setError("Enter Password !");
            editTextPassword.requestFocus();
            return false;
        }
        if(phn.isEmpty() )
        {
            editTextPhone.setError("Enter Phone Number !");
            editTextPhone.requestFocus();
            return false;
        }

        return true;
    }
    public void switchFragment(int id)
    {
        Fragment fragment=null;
        switch (id)
        {
            case R.layout.fragment_signup:
                fragment=new SignUpFragment();
                break;
            case R.layout.fragment_choose_doc_sector:
                fragment=new DoctorSignupFragment0();
                break;
        }
        if(fragment!=null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

            ft.add(R.id.fragment_container_login_activity,fragment);
            ft.addToBackStack(null);
            ft.commit();

        }

    }
    private synchronized void userloginUtil(final String pass, final String phn)
    {
        final Runnable onTaskFinished=new Runnable() {
            @Override
            public void run() {
                progressBarLogin.setVisibility(View.INVISIBLE);
                System.out.println("Pass Retrieved "+passwordRetrieved[0] +" Pass Entered "+pass);

                if(passwordRetrieved[0].trim().equals(pass))
                {
                    //TODO: Switch To Activity 2
                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    //FileUtil.verifyStoragePermissions(LoginActivity.this);
                    //FileUtil.AppendToFile(phn);
                    i.putExtra("new user phone", phn);
                    startActivity(i);
                    finish();
                }
                else
                {
                    progressBarLogin.setVisibility(View.INVISIBLE);
                    editTextPassword.requestFocus();
                    editTextPassword.setError("Wrong Password !");
                }
            }
        };
        final Runnable onTaskStart =  new Runnable() {

            @Override
            public void run() {
                // Stuff that updates the UI
                progressBarLogin.setVisibility(View.VISIBLE);
            }
        };

        firebaseUtil.retrieveDataFromFirebase(FirebaseUtil.getReferenceToKey(firebaseUtil.root.child(phn),"pass"),passwordRetrieved);
        new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(onTaskStart);
                while (passwordRetrieved[0]==null)
                {}
                runOnUiThread(onTaskFinished);

            }
        }).start();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initializeXmlVariables();
        initializeJavaVariables();
        if(isLoginOverride)userLogin("admin","+8801687226064");
        addOnClickListeners();

    }
}
