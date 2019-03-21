package com.example.applico;



import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.w3c.dom.Text;

import java.util.concurrent.TimeUnit;

public class Login extends AppCompatActivity {
    EditText phone,otp;
    Button sendotp,signin;
    FirebaseAuth mauth;
    String verificationcode;
AlphaAnimation gulpha;
    String phoneno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        gulpha=new AlphaAnimation(1.0f,0.0f);
TextView heading=findViewById(R.id.heading);
Typeface myfont=Typeface.createFromAsset(this.getAssets(),"fonts/MotionPicture_PersonalUseOnly.ttf");
heading.setTypeface(myfont);
        phone=findViewById(R.id.textView);
       // progressBar=findViewById(R.id.progressBar);
        //progressBar.setVisibility(View.GONE);
        otp=findViewById(R.id.textView2);
        mauth=FirebaseAuth.getInstance();
        signin=findViewById(R.id.button2);
        sendotp=findViewById(R.id.button);
        sendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setAnimation(gulpha);
                phoneno=phone.getText().toString();
                if (phoneno.isEmpty()||phoneno.length()<10)
                {
                    phone.setError("Enter a valid phone number");
                    phone.requestFocus();
                    return;
                }
               // progressBar.setVisibility(View.VISIBLE);
otp.requestFocus();
                sendotp(phoneno);
            }
        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setAnimation(gulpha);
                String x=otp.getText().toString();
                if(x.isEmpty()||x.length()<6)
                {  otp.setError("Enter a valid otp");
                    otp.requestFocus();
                    return;}
                verifycode(x);
            }
        });

    }

    public void sendotp(String phoneno)
    {
        PhoneAuthProvider.getInstance().verifyPhoneNumber("+91"+phoneno,60, TimeUnit.SECONDS, TaskExecutors.MAIN_THREAD,mcallbacks);

    }
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mcallbacks =new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
//progressBar.setVisibility(View.GONE);
            signinwithauthcredential(phoneAuthCredential);
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
           /// progressBar.setVisibility(View.GONE);
            Toast.makeText(Login.this,"Verification failed . Make sure you have an active internet connection",Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationcode=s;
           // progressBar.setVisibility(View.GONE);
        }

    };
    public void verifycode(String code)
    {
        PhoneAuthCredential credential=PhoneAuthProvider.getCredential(verificationcode,code);
        signinwithauthcredential(credential);


    }

    public void signinwithauthcredential(PhoneAuthCredential credential)
    {
        mauth.signInWithCredential(credential).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    SharedPreferences sp= getSharedPreferences("mobileno", Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit=sp.edit();
                    edit.putString("phonenumber",phoneno);
                    edit.apply();
                    Intent intent =new Intent(Login.this,MainActivity.class);

                    startActivity(intent);
                    finish();
                }
                else {

                    //verification unsuccessful.. display an error message

                    String message = "Somthing is wrong, we will fix it soon...";

                    if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                        message = "Invalid code entered...";
                    }
                }
            }
        });
    }




}
