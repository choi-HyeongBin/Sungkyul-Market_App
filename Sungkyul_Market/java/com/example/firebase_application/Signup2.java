package com.example.firebase_application;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class Signup2 extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup2);


        findViewById(R.id.signup2_btn1).setOnClickListener(onClickListener);
        findViewById(R.id.signup2_btn2).setOnClickListener(onClickListener);

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.signup2_btn1:
                    information();
                    signout();//회원정보 입력후 로그인을 안하고 뒤로가기하면 메인화면에 닉네임 보여주기 때문에 로그아웃하기
                    break;

                case R.id.signup2_btn2:
                    delete();
                    finish();
                    break;
            }
        }
    };

    private void information() {
        String Signup2_name = ((EditText) findViewById(R.id.signup2_edt1)).getText().toString();
        String Signup2_school = ((EditText) findViewById(R.id.signup2_edt2)).getText().toString();
        String Signup2_major = ((EditText) findViewById(R.id.signup2_edt3)).getText().toString();
        String Signup2_number = ((EditText) findViewById(R.id.signup2_edt4)).getText().toString();

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        //회원가입때 닉네임 받아오기
        Intent Signup2_intent=getIntent();
        String uid=Signup2_intent.getStringExtra("uid");

        if (Signup2_name.length() > 0 && Signup2_school.length() > 0 && Signup2_major.length() > 0 && Signup2_number.length() > 0) {
            //회원정보 다 입력했을떄
            Userinformation userinformation =new Userinformation(); //유저정보 클래스 사용

            userinformation.name=Signup2_name;
            userinformation.school=Signup2_school;
            userinformation.major=Signup2_major;
            userinformation.number=Signup2_number;


            database.getReference("User_information").child(uid).setValue(userinformation);//해당 유저의UID테이블 밑에 데이터 저장


            Intent intent = new Intent(getApplicationContext(), Login.class);
            intent.putExtra("name",Signup2_name);
            intent.putExtra("school",Signup2_school);
            intent.putExtra("major",Signup2_major);
            intent.putExtra("number",Signup2_number);

            startActivity(intent);
            finish();
        }
        else{
            Toast.makeText(this, "정보를 바르게 입력해주세요", Toast.LENGTH_SHORT).show();
        }
    }

    private void delete(){//회원정보 삭제
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user.delete();
    }
    @Override
    public void onBackPressed() {
        /*super.onBackPressed();*/ //뒤로가기 기능삭제
    }
    private void signout(){//로그아웃
        FirebaseAuth.getInstance().signOut();
    }

}
