package com.example.firebase_application;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Login extends AppCompatActivity {


    private static final String TAG="LoginActivity";
    private FirebaseAuth mAuth;

    private FirebaseDatabase database= FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.login_btn1).setOnClickListener(onClickListener);
        findViewById(R.id.login_btn2).setOnClickListener(onClickListener);


    }
    @Override
    public void onStart(){
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
    }

    View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.login_btn1:
                    login();
                    break;
                case R.id.login_btn2:
                    Intent intent=new Intent(getApplicationContext(), Signup1.class);
                    startActivity(intent);
                    finish();
                    break;
            }
        }
    };

    private void login(){
        String email=((EditText)findViewById(R.id.login_edt1)).getText().toString().trim()+"@sungkyul.ac.kr";
        String password=((EditText)findViewById(R.id.login_edt2)).getText().toString();

        if(email.length()>0 && password.length()>0){
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                if(mAuth.getCurrentUser().isEmailVerified()){
                                    FirebaseUser user = mAuth.getCurrentUser();

                                    tostmsg("로그인에 성공하였습니다.");
                                    final String user_uid=user.getUid();
                                    FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
                                    mUser.getIdToken(true)
                                            .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                                                public void onComplete(@NonNull Task<GetTokenResult> task) {
                                                    if (task.isSuccessful()) {
                                                        Userinformation userinformation=new Userinformation();
                                                        Intent intent=getIntent();
                                                        userinformation.setName(intent.getStringExtra("name"));
                                                        userinformation.setSchool(intent.getStringExtra("school"));
                                                        userinformation.setMajor(intent.getStringExtra("major"));
                                                        userinformation.setNumber(intent.getStringExtra("number"));
                                                        database.getReference("User_information").child(user_uid).setValue(userinformation);

                                                    } else {

                                                        // Handle error -> task.getException();
                                                    }
                                                }
                                            });


                                    Intent Login_intent=new Intent(getApplicationContext(),Main.class);
                                    Login_intent.putExtra("user_uid",user_uid);

                                    Intent intent=new Intent(getApplicationContext(),Main.class);
                                    startActivity(intent);

                                    finish();
                                }
                                else{
                                    Toast.makeText(getApplicationContext(), "이메일에서 인증을 해주세요", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                if(task.getException()!=null){
                                    tostmsg("이메일 또는 비밀번호가 맞지않습니다");
                                }
                            }
                        }
                    });
        }else{
            tostmsg("이메일 또는 비밀번호를 입력해주세요.");
        }

    }
    private void tostmsg(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

    }

}