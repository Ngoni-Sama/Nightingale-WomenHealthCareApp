package com.ashiqur.nightingale.module1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ashiqur.nightingale.R;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

import com.ashiqur.nightingale.ashiqur_util.FirebaseUtil;
import com.ashiqur.nightingale.ashiqur_util.UiUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import s.ashiqur.lady.data.User;

import static com.ashiqur.nightingale.ashiqur_util.UiUtil.showSnackBar;
import static com.ashiqur.nightingale.ashiqur_util.UiUtil.showToast;


public class SignUpFragment extends Fragment implements FirebaseUtil.FirebaseUtilPhoneAuthProvider{


    private static final String TAG = "SignUpFragment";
    private Activity activity;

    private Context context;
    private View view;
    //XML variables
    private EditText editTextConfirmPass, editTextAge,editTextEmail,editTextPassword,editTextCellPhone,editTextEnterVerificationCode,editTextCountryCode;
    private Button buttonTermsAndConditions,buttonGetVerCode,buttonContinue,buttonVerify;
    private CheckBox checkBoxiAgree;
    private Switch switchIsMarried,switchIsPregnant,switchIsStartedMenstruatingYet,switchIsPregnantBefore,switchIsPlanningToHaveKids;

    private ProgressBar progressBarVerification;
    //JAVA variables
    private View.OnClickListener handleClick;
    private FirebaseUtil firebaseUtil;
    //Auth
    private FirebaseAuth mAuth;
    private String mVerificationId;

    private boolean [] isSignUpSuccessFul;

    public SignUpFragment() {
        // Required empty public constructor
    }
    @Override
    public void onAttach(Context c) {
        super.onAttach(c);

        Activity a;

        context=c;
        if (c instanceof Activity){
            a=(Activity) c;
            activity=a;
        }

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeXmlVariables(view);
        initializeJavaVariables();
        addOnClickListeners();
    }

    private void addOnClickListeners() {
        buttonTermsAndConditions.setOnClickListener(handleClick);
        buttonGetVerCode.setOnClickListener(handleClick);
        buttonContinue.setOnClickListener(handleClick);
        buttonVerify.setOnClickListener(handleClick);
    }
    private void initializeClickListeners()
    {
        handleClick=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId())
                {
                    case R.id.btn_verify:
                    {
                        String code = editTextEnterVerificationCode.getText().toString().trim();
                        if (code.isEmpty() || code.length() < 6) {
                            editTextEnterVerificationCode.setError("Enter valid code");
                            editTextEnterVerificationCode.requestFocus();
                            return;
                        }

                        verifyVerificationCode(code);
                        break;
                    }
                    case R.id.btn_continue:
                    {
                        if(buttonContinue.getText().toString().trim().equalsIgnoreCase("Login")) {
                            Intent i = new Intent(activity, LoginActivity.class);
                            startActivity(i);
                            activity.finish();
                            return;
                        }

                        if(!checkUserInputError())return;

                        int age= Integer.parseInt(editTextAge.getText().toString().trim());
                        String email=editTextEmail.getText().toString().trim();
                        String pass=editTextPassword.getText().toString().trim();
                        String cell=editTextCellPhone.getText().toString().trim();


                        User newUser=new User(age,email,pass,"+"+editTextCountryCode.getText().toString().trim()+cell,switchIsMarried.isChecked(),switchIsPregnant.isChecked(),switchIsPregnantBefore.isChecked(),switchIsStartedMenstruatingYet.isChecked(),switchIsPlanningToHaveKids.isChecked());

                        signUpUtil(newUser);

                        break;
                        //firebaseUtil.checkIfPrimaryKeyExistsAndAddToFirebase(firebaseUtil.root,editTextEmail.getText().toString(),newUser);
                    }
                    case R.id.btn_terms_and_conditions:
                    {
                        //TODO: Discuss Terms And Conditions And Show That page
                        showSnackBar(view,"Not Available for prototype",Snackbar.LENGTH_SHORT);
                        break;
                    }
                    case R.id.btn_getverification_code:
                    {
                        if(!checkUserInputError())return;

                        String mobile = editTextCellPhone.getText().toString().trim();
                        if(mobile.isEmpty() ){
                            editTextCellPhone.requestFocus();
                            editTextCellPhone.setError("Enter a valid mobile");

                            return;
                        }
                        sendVerificationCode(mobile,mCallbacks);
                        break;
                        //firebaseUtil.checkIfPrimaryKeyExistsAndAddToFirebase(firebaseUtil.root,editTextEmail.getText().toString(),newUser);
                    }

                }
            }
        };
    }
    private void initializeJavaVariables() {

        isSignUpSuccessFul=new boolean[1];

        firebaseUtil=new FirebaseUtil(context);
        firebaseUtil.setFireBaseDatabaseRoot(FirebaseUtil.getReferenceToKey(firebaseUtil.root,"Users"));
        mAuth = FirebaseAuth.getInstance();

        initializeClickListeners();

    }
    private void initializeXmlVariables(View view) {
        this.view=view;
        editTextCountryCode=view.findViewById(R.id.et_countrycode_signup);
        switchIsMarried=view.findViewById(R.id.switch_married_frag_signup);
        switchIsPregnant=view.findViewById(R.id.switch_areupregnant_frag_signup);
        switchIsPregnantBefore=view.findViewById(R.id.switch_beenpregbefore_frag_signup);
        switchIsStartedMenstruatingYet=view.findViewById(R.id.switch_startedmens_frag_signup);
        switchIsPlanningToHaveKids=view.findViewById(R.id.switch_plantohavekids_frag_signup);

        editTextAge=view.findViewById(R.id.et_age_frag_signup);
        editTextCellPhone=view.findViewById(R.id.et_phone_signup);
        editTextEmail=view.findViewById(R.id.et_email_signup);
        editTextConfirmPass=view.findViewById(R.id.et_confirm_pass_frag_signup);
        editTextPassword=view.findViewById(R.id.et_password_signup);
        editTextEnterVerificationCode=view.findViewById(R.id.et_verification_code);

        buttonContinue=view.findViewById(R.id.btn_continue);
        buttonContinue.setEnabled(false);
        buttonGetVerCode=view.findViewById(R.id.btn_getverification_code);
        buttonVerify=view.findViewById(R.id.btn_verify);
        buttonTermsAndConditions=view.findViewById(R.id.btn_terms_and_conditions);

        checkBoxiAgree =view.findViewById(R.id.checkbox_igree);
        progressBarVerification =view.findViewById(R.id.progressbar_fragment_signup);
        progressBarVerification.setProgress(100);

    }

    //the method is sending verification code
    //the country id is concatenated
    //you can take the country id as user input as well
    @Override
    public void sendVerificationCode(String mobile,  PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks) {
        Log.d("SMS being sent to","+"+editTextCountryCode.getText().toString().trim()+mobile);
        progressBarVerification.setVisibility(View.VISIBLE);

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+"+editTextCountryCode.getText().toString().trim() + mobile,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                callbacks);
    }

    private synchronized void signUpUtil(User newUser)
    {
        final Runnable onTaskFinished=new Runnable() {
            @Override
            public void run() {
              progressBarVerification.setVisibility(View.INVISIBLE);
              buttonContinue.setText(R.string.Login);
            }
        };
        final Runnable onTaskStart =  new Runnable() {

            @Override
            public void run() {
                // Stuff that updates the UI
               progressBarVerification.setVisibility(View.VISIBLE);
            }
        };

        firebaseUtil.checkIfPrimaryKeyExistsAndAddToFirebase(firebaseUtil.root,"+"+editTextCountryCode.getText().toString().trim()+editTextCellPhone.getText().toString().trim(),newUser,isSignUpSuccessFul);
        new Thread(new Runnable() {
            @Override
            public void run(){
                activity.runOnUiThread(onTaskStart);
                while (!isSignUpSuccessFul[0]){}
                activity.runOnUiThread(onTaskFinished);
            }
        }).start();
    }

    //the callback to detect the verification status
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            //Getting the code sent by SMS
            String code = phoneAuthCredential.getSmsCode();
            progressBarVerification.setVisibility(View.INVISIBLE);
            //sometime the code is not detected automatically
            //in this case the code will be null
            //so user has to manually enter the code
            if (code != null) {
                editTextEnterVerificationCode.setText(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            showToast(context, e.getMessage(), Toast.LENGTH_LONG);
        }

        @Override
        public void onCodeAutoRetrievalTimeOut(String s) {
            showSnackBar(view,s,Snackbar.LENGTH_SHORT);
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            //storing the verification id that is sent to the user
            mVerificationId = s;
            Log.wtf(TAG,"Inside On CodeSent :"+mVerificationId);
        }
    };
    @Override
    public void verifyVerificationCode(String code) {
        //creating the credential
        progressBarVerification.setVisibility(View.VISIBLE);
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);

        //signing the user
        signUpWithPhoneAuthCredential(credential, onCompleteListener);
    }

    OnCompleteListener<AuthResult> onCompleteListener = new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()) {
                //verification successful we will start the profile activity
                buttonContinue.setEnabled(true);
                progressBarVerification.setVisibility(View.INVISIBLE);
                showToast(activity.getApplicationContext(),"Verification Successful",Toast.LENGTH_LONG);
                buttonVerify.setEnabled(false);

            } else {
                //verification unsuccessful.. display an error message
                String message = "Something is wrong, we will fix it soon...";
                if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                    message = "Invalid code entered...";
                }

                showToast(activity.getApplicationContext(),message,Toast.LENGTH_LONG);

            }
        }
    };
    @Override
    public void signUpWithPhoneAuthCredential(PhoneAuthCredential credential,OnCompleteListener<AuthResult> onCompleteListener) {
        mAuth.signInWithCredential(credential).addOnCompleteListener(activity,  onCompleteListener);
    }

    boolean checkUserInputError()
    {
        String age= editTextAge.getText().toString().trim();
        String email=editTextEmail.getText().toString().trim();
        String password=editTextPassword.getText().toString().trim();
        String confirmPass=editTextConfirmPass.getText().toString().trim();
        String phn=editTextCellPhone.getText().toString().trim();
        boolean isOK=false;

        if(!checkBoxiAgree.isChecked())
        {
            checkBoxiAgree.requestFocus();
            checkBoxiAgree.setError("!!");

            return isOK;
        }
        if(age.isEmpty() || email.isEmpty() || password.isEmpty() || phn.isEmpty() || confirmPass.isEmpty())
        {
            showSnackBar(view,"One Or More Empty Fields", Snackbar.LENGTH_SHORT,"DISMISS", UiUtil.DO_NOTHING);
            return isOK;
        }
        try
        {
           int a =Integer.parseInt(age);
           if(a<9)
           {
               Log.wtf(TAG,"Age :"+a);
               editTextAge.requestFocus();

               editTextAge.setError("Inappropriate Age");
               return isOK;

           }
        }catch (NumberFormatException nfe){Log.wtf(TAG,nfe.toString());}

        if(!password.equals(confirmPass))
        {
            Log.wtf(TAG,"Pass :"+password+" Confirmed Pass:"+confirmPass);
            editTextConfirmPass.requestFocus();
            editTextConfirmPass.setError("Passwords Dont Match");
            return isOK;
        }
        if (!email.matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+")) {
            editTextEmail.requestFocus();
            editTextEmail.setError("Invalid Email Address");

            return isOK;
        }

//        if(phn.length() != 11){
//            editTextCellPhone.requestFocus();
//            editTextCellPhone.setError("Enter a valid mobile");
//
//            return isOK;
//        }
        if(!passwordValidation(password))
        {
            editTextPassword.requestFocus();
            editTextPassword.setError("Invalid Pass+\n+should be atleast of length 8 and must contain atleast one number(0-9)+\n+" +
                    "atleast one special character+\n+" +
                    "and letters");

            return isOK;
        }
        if(!switchIsStartedMenstruatingYet.isChecked() && ( switchIsPregnantBefore.isChecked() || switchIsPregnant.isChecked() ) ) //3 out of 8 input combinations invalid
        {
            switchIsStartedMenstruatingYet.requestFocus();
            switchIsStartedMenstruatingYet.setError("Contradicting Info! ");

            showSnackBar(view,"Contradicting Info !",Snackbar.LENGTH_SHORT);
            return isOK;
        }


        isOK=true;
        return isOK;
    }
    public static boolean passwordValidation(String password)
    {

        if(password.length()>=8)
        {
            Pattern letter = Pattern.compile("[a-zA-z]");
            Pattern digit = Pattern.compile("[0-9]");
            Pattern special = Pattern.compile ("[!@#$%&*()_+=|<>?{}\\[\\]~-]");
            //Pattern eight = Pattern.compile (".{8}");


            Matcher hasLetter = letter.matcher(password);
            Matcher hasDigit = digit.matcher(password);
            Matcher hasSpecial = special.matcher(password);

            return hasLetter.find() && hasDigit.find() && hasSpecial.find();

        }
        else
            return false;

    }
}
