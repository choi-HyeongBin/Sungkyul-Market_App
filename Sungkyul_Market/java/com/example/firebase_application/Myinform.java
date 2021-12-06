package com.example.firebase_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Myinform extends AppCompatActivity {
    Button btn1,btn2,btn3;
    TextView tv1,tv2,tv3,tv4,tv6;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private ArrayList<Conditioninformation> arrayList1=new ArrayList<>();
    private String id;
    private ArrayList<Conditioninformation> myList3=new ArrayList<>();
    private  ArrayList myList4=new ArrayList<>();

    String uid = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.myinform);

        btn1=findViewById(R.id.myinform_btn1);
        btn2=findViewById(R.id.myinform_btn2);
        tv1=findViewById(R.id.myinform_tv2);
        tv2=findViewById(R.id.myinform_tv4);
        tv3=findViewById(R.id.myinform_tv6);
        tv4=findViewById(R.id.myinform_tv8);

        tv6=findViewById(R.id.myinform_tv12);
        btn3=findViewById(R.id.myinform_btn3);



        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();



        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user!=null){
                    Intent intent=new Intent(getApplicationContext(), Myinformchange.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(),"로그인을 해야합니다.",Toast.LENGTH_SHORT).show();

                }

            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user!=null){
                    Intent intent=new Intent(getApplicationContext(), Repass.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(),"로그인을 해야합니다.",Toast.LENGTH_SHORT).show();
                }


            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                {

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user != null) {
                        id = user.getEmail();
                        database = FirebaseDatabase.getInstance();//파이어베이스 데이터베이스 연동
                        databaseReference = database.getReference("Trade state"); //DB테이블 연결
                        databaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                arrayList1.clear();
                                myList3.clear();
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {//반복문으로 데이터 List를 추출해냄
                                    Conditioninformation users = snapshot.getValue(Conditioninformation.class); //만들어뒀던 User객체에 데이터를 담는다.
                                    arrayList1.add(users); //담은 데이터들을 배열리스트에 넣고 리사이클러뷰로 보낼준비

                                }
                                for (Conditioninformation object : arrayList1) {
                                    if (object.getUseremail().contains(id)) {
                                        myList3.add(object);
                                    }
                                }
                                for (Conditioninformation object : arrayList1) {
                                    if (object.getBuyeremail().contains(id)) {
                                        myList4.add(object);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                //디비를 가져오던중 에러 발생시
                                Log.e("recycerview.java", String.valueOf(databaseError.toException())); //에러문 출력
                            }
                        });


                        AlertDialog.Builder dlg = new AlertDialog.Builder(Myinform.this);
                        dlg.setTitle("알림");
                        dlg.setMessage("회원탈퇴를 하시겠습니까?");
                        dlg.setIcon(R.mipmap.ic_launcher);
                        dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                AlertDialog.Builder dlg = new AlertDialog.Builder(Myinform.this);
                                dlg.setTitle("알림");
                                dlg.setMessage("정말로 회원탈퇴를 하시겠습니까?\n등록한 게시물과 회원정보가 삭제되며 복구가 불가능합니다.");

                                dlg.setIcon(R.mipmap.ic_launcher);
                                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {


                                        if (myList3.isEmpty() && myList4.isEmpty()) {

                                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                                            final String email = user.getEmail();
                                            final String uid = user.getUid();

                                            databaseReference = database.getReference("User_information").child(uid); //DB테이블 연결
                                            databaseReference.setValue(null);

                                            databaseReference = database.getReference("User").child(uid); //DB테이블 연결
                                            databaseReference.setValue(null);

                                            databaseReference = FirebaseDatabase.getInstance().getReference().child("Product2");
                                            Query mQuery = databaseReference.orderByChild("useremail").equalTo(email);

                                            mQuery.addListenerForSingleValueEvent(new ValueEventListener() {

                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    for (DataSnapshot ds : snapshot.getChildren()) {
                                                        ds.getRef().removeValue();
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });

                                            databaseReference = FirebaseDatabase.getInstance().getReference().child("Messagesend");
                                            Query mQuery2 = databaseReference.orderByChild("sendemail").equalTo(email);

                                            mQuery2.addListenerForSingleValueEvent(new ValueEventListener() {

                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    for (DataSnapshot ds : snapshot.getChildren()) {
                                                        ds.getRef().removeValue();
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });

                                            databaseReference = FirebaseDatabase.getInstance().getReference().child("Messagereceive");
                                            Query mQuery3 = databaseReference.orderByChild("receiveemail").equalTo(email);

                                            mQuery3.addListenerForSingleValueEvent(new ValueEventListener() {

                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    for (DataSnapshot ds : snapshot.getChildren()) {
                                                        ds.getRef().removeValue();
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });



                                            user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(getApplicationContext(), "성공.", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });


                                            AlertDialog.Builder dlg2 = new AlertDialog.Builder(Myinform.this);
                                            dlg2.setTitle("알림");
                                            dlg2.setMessage("회원탈퇴가 되었습니다.");
                                            dlg2.setIcon(R.mipmap.ic_launcher);
                                            dlg2.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    Intent intent = new Intent(getApplicationContext(), Main.class);
                                                    startActivity(intent);

                                                }
                                            });
                                            dlg2.show();

                                            myList3.clear();
                                            arrayList1.clear();


                                        } else {
                                            AlertDialog.Builder dlg2 = new AlertDialog.Builder(Myinform.this);
                                            dlg2.setTitle("알림");
                                            dlg2.setMessage("거래가 진행중 일때는 회원탈퇴가 불가능합니다.");
                                            dlg2.setIcon(R.mipmap.ic_launcher);
                                            dlg2.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    myList3.clear();
                                                    arrayList1.clear();
                                                    Intent intent = new Intent(getApplicationContext(), Myinform.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                    startActivity(intent);


                                                }
                                            });
                                            dlg2.show();


                                        }


                                    }


                                });
                                dlg.setNegativeButton("취소", null);
                                dlg.show();


                            }


                        });
                        dlg.setNegativeButton("취소", null);
                        dlg.show();
                    } else { Toast.makeText(getApplicationContext(),"로그인을 해야합니다.",Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });



        if (user != null) {

            String email = user.getEmail();
            tv6.setText(email);
        }


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
                        tv1.setText(str);



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
                        tv2.setText(str2);



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
                        tv3.setText(str3);



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
                        tv4.setText(str4);



                    } else {


                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });





        }

    }

}
