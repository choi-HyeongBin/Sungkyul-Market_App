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
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Signup1 extends AppCompatActivity {


    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup1);


        mAuth = FirebaseAuth.getInstance();


        findViewById(R.id.signup_btn1).setOnClickListener(onClickListener);
        findViewById(R.id.signup_btn2).setOnClickListener(onClickListener);

    }
    @Override
    public void onStart(){
        super.onStart();

    }

    View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.signup_btn1:
                    signup();
                    break;

                case R.id.signup_btn2:
                    Intent intent = new Intent(getApplication(),Main.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

            }
        }
    };

    private void signup(){
        final String email=((EditText)findViewById(R.id.signup_edt1)).getText().toString().trim()+"@sungkyul.ac.kr".trim();
        final String password=((EditText)findViewById(R.id.signup_edt3)).getText().toString();
        String passwordCheak=((EditText)findViewById(R.id.signup_edt4)).getText().toString();

        mAuth = FirebaseAuth.getInstance();
        if(email.length()>0 &&password.length()>0 && passwordCheak.length()>0){ //모든 입력이 되고
            if (password.equals(passwordCheak)){//패스워드와 재입력패스워드가 일치시
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    mAuth.getCurrentUser().sendEmailVerification()//이메일 인증 보내기
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        //성공시
                                                        FirebaseUser user = mAuth.getCurrentUser();
                                                        tostmsg("해당 이메일로 인증메일을 보냈습니다.");
                                                        String uid=user.getUid();
                                                        Intent Signup_intent=new Intent(getApplicationContext(), Signup2.class);
                                                        Signup_intent.putExtra("uid",uid);
                                                        startActivity(Signup_intent);
                                                        finish();
                                                    }
                                                    else
                                                    {

                                                        Toast.makeText(getApplicationContext(), "회원가입에 실패하였습니다", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });

                                } else {
                                    //실패시
                                    if(task.getException()!=null){
                                        tostmsg("비밀번호를 6자리 이상 입력해주세요.");
                                    }
                                }
                            }
                        });
            }
            else{
                tostmsg("비밀번호가 일치하지 않습니다");
            }
        }else{
            tostmsg("이메일 또는 비밀번호를 입력해주세요.");
        }

    }
    private void tostmsg(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onBackPressed() {
        /*super.onBackPressed();*/
    }





}