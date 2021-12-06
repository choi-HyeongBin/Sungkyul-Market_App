package com.example.firebase_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.DialogInterface;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class Myinformchange extends AppCompatActivity {
    Button btn1;
    EditText edt1,edt2,edt3,edt4;
    TextView tv1;
    String uid = null;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.myinformchange);
        btn1=findViewById(R.id.myinformchange_btn1);
        edt1=findViewById(R.id.myinformchange_edt1);
        edt2=findViewById(R.id.myinformchange_edt2);
        edt3=findViewById(R.id.myinformchange_edt3);
        edt4=findViewById(R.id.myinformchange_edt4);


        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();




        if (user != null) {
            uid = user.getUid();//해당 유저 UID 받아옴
            database = FirebaseDatabase.getInstance();//파이어베이스 데이터베이스 연동


            databaseReference = database.getReference("User_information"); //DB테이블 연결
            databaseReference.child(uid).child("name").addValueEventListener(new ValueEventListener() {//해당유저의 닉네임을 받아오는 메소드
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String str = snapshot.getValue(String.class);
                    //해당 유저 닉네임을 받아서 보여줌
                    if (str != null) {
                        edt1.setText(str);

                    } else {


                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            databaseReference.child(uid).child("school").addValueEventListener(new ValueEventListener() {//해당유저의 닉네임을 받아오는 메소드
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String str2 = snapshot.getValue(String.class);
                    //해당 유저 닉네임을 받아서 보여줌
                    if (str2 != null) {
                        edt2.setText(str2);



                    } else {


                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            databaseReference.child(uid).child("major").addValueEventListener(new ValueEventListener() {//해당유저의 닉네임을 받아오는 메소드
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String str3 = snapshot.getValue(String.class);
                    //해당 유저 닉네임을 받아서 보여줌
                    if (str3 != null) {
                        edt3.setText(str3);



                    } else {


                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            databaseReference.child(uid).child("number").addValueEventListener(new ValueEventListener() {//해당유저의 닉네임을 받아오는 메소드
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String str4 = snapshot.getValue(String.class);
                    //해당 유저 닉네임을 받아서 보여줌
                    if (str4 != null) {
                        edt4.setText(str4);



                    } else {


                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });



        }



        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = edt1.getText().toString();
                final String school = edt2.getText().toString();
                final String major = edt3.getText().toString();
                final String number = edt4.getText().toString();

                AlertDialog.Builder dlg = new AlertDialog.Builder(Myinformchange.this);
                dlg.setTitle("알림");
                dlg.setMessage("수정을 완료 하시겠습니까?");
                dlg.setIcon(R.mipmap.ic_launcher);
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(name.equals("")||school.equals("")||major.equals("")||number.equals("")) {
                            Toast.makeText(Myinformchange.this, "모든 값을 입력해야 합니다.", Toast.LENGTH_SHORT).show();
                        }else{
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            String name2 = edt1.getText().toString();
                            String school2 = edt2.getText().toString();
                            String major2 = edt3.getText().toString();
                            String number2 = edt4.getText().toString();
                            uid = user.getUid();//해당 유저 UID 받아옴
                            database = FirebaseDatabase.getInstance();
                            databaseReference = database.getReference("User_information");

                            Map<String, Object> hopperUpdates = new HashMap<>();
                            hopperUpdates.put("name", name2);
                            hopperUpdates.put("school", school2);
                            hopperUpdates.put("major", major2);
                            hopperUpdates.put("number", number2);
                            databaseReference.child(uid).updateChildren(hopperUpdates);


                            Intent intent = new Intent(getApplicationContext(), Myinform.class);
                            startActivity(intent);
                            finish();

                        }

                    }

                });
                dlg.setNegativeButton("취소", null);
                dlg.show();




            }


        });
    }

}