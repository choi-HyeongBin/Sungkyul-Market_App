package com.example.firebase_application;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class Messagesend extends AppCompatActivity {
    Button btn1,btn2;
    TextView txt1;
    EditText edt1;

    private FirebaseAuth mAuth;


    private FirebaseDatabase database;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        setContentView(R.layout.messagesend);

        btn1 = findViewById(R.id.message_send_btn1);
        btn2 = findViewById(R.id.message_send_btn2);
        txt1 = findViewById(R.id.message_send_tv1);
        edt1 = findViewById(R.id.message_send_edt1);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        Intent intent=getIntent();
        Productinformation productinformation =new Productinformation();
        productinformation.setUseremail(intent.getStringExtra("tv2"));


        String test = intent.getStringExtra("tv2");

        txt1.setText(test);


        if (user != null) {//지금 로그인된 유저가 있다면
            final String uid = user.getUid();//해당 유저 UID 받아옴
            database = FirebaseDatabase.getInstance();//파이어베이스 데이터베이스 연동

            databaseReference = database.getReference("product2"); //DB테이블 연결
            mAuth = FirebaseAuth.getInstance();






            databaseReference.child(uid).child("product2").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {//반복문으로 데이터 List를 추출해냄
                        Productinformation user = snapshot.getValue(Productinformation.class); //만들어뒀던 User객체에 데이터를 담는다.
                        databaseReference = database.getReference("product2"); //DB테이블 연결
                        databaseReference.child("product2").child("useremail");

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    //디비를 가져오던중 에러 발생시
                    Log.e("recycerview.java", String.valueOf(databaseError.toException())); //에러문 출력
                }
            });
        } else {
            Toast.makeText(Messagesend.this, "로그인을 해야합니다.", Toast.LENGTH_SHORT).show();


        }


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send();
                Intent intent = new Intent(getApplication(), Main.class);
                startActivity(intent);
                finish();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), Main.class);
                startActivity(intent);
                finish();
            }
        });


    }

    private String getTime(){

        long mNow;
        Date mDate;
        SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        return mFormat.format(mDate);
    }


    private void send() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        Productinformation message = new Productinformation();

        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user1 = mAuth.getCurrentUser();



        message.sendemail = user1.getEmail();
        message.text = edt1.getText().toString();
        message.receiveemail = txt1.getText().toString();
        message.date = getTime();

        database.getReference().child("Messagesend").push().setValue(message);
        database.getReference().child("Messagereceive").push().setValue(message);








    }




}
