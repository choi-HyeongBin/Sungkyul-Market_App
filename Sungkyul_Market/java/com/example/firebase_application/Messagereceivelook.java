
package com.example.firebase_application;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Messagereceivelook extends AppCompatActivity {


    private RecyclerView recyclerView;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference,data2;
    private FirebaseAuth auth=FirebaseAuth.getInstance();
    private FirebaseUser user;
    private  String uid,uid2;
    private RecyclerView.Adapter adapter;
    private ArrayList<Productinformation> messageList=new ArrayList<>(); //물품객체를 담을 어레이 리스트 (어댑터쪽으로);

    public static Context context;//새로고침 위한 전역변수


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.messagereceivelook);

        context = this;//새로고침할 액티비티 지정

        TextView tv1=findViewById(R.id.messagereceivelook_tv1);//상대아이디
        TextView tv2=findViewById(R.id.messagereceivelook_tv2);//날짜
        TextView tv3=findViewById(R.id.messagereceivelook_tv3);//내용


        Button btn1=findViewById(R.id.messagereceivelook_btn1); //답장

        Button btn2=findViewById(R.id.messagereceivelook_btn2); //삭제버튼

        Intent intent=getIntent();
        tv1.setText("아이디: "+intent.getStringExtra("sendemail"));
        tv2.setText(intent.getStringExtra("date"));
        tv3.setText(intent.getStringExtra("text"));

        final String receiveemail1 = intent.getStringExtra("sendemail"); //여기서부터<>
        final String date1 = intent.getStringExtra("date"); //여기서부터<>
        final String text1 = intent.getStringExtra("text"); //여기서부터<>


        user = FirebaseAuth.getInstance().getCurrentUser();
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//<>

                AlertDialog.Builder builder = new AlertDialog.Builder(Messagereceivelook.this);

                builder.setTitle("알림").setMessage("답장 하시겠습니까?");
                builder.setNegativeButton("취소", null);

                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        database=FirebaseDatabase.getInstance();
                        user = FirebaseAuth.getInstance().getCurrentUser();


                        databaseReference = FirebaseDatabase.getInstance().getReference().child("Messagerecieve");

                        Intent intent=new Intent(context,Messagesend.class);

                        intent.putExtra("tv2",receiveemail1);

                        context.startActivity(intent);
                        finish();


                    }
                });


                AlertDialog alertDialog = builder.create();

                alertDialog.show();
            }


        });



        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//<>

                AlertDialog.Builder builder = new AlertDialog.Builder(Messagereceivelook.this);

                builder.setTitle("알림").setMessage("쪽지를 삭제하시겠습니까?");
                builder.setNegativeButton("취소", null);

                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        FirebaseUser user;
                        user = FirebaseAuth.getInstance().getCurrentUser();




                        final String email = user.getEmail();


                        databaseReference = FirebaseDatabase.getInstance().getReference().child("Messagereceive");
                        Query mQuery=databaseReference.orderByChild("date").equalTo(date1);
                        if (email.equals(email)){
                            mQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for(DataSnapshot ds: snapshot.getChildren()){
                                        ds.getRef().removeValue();}
                                    onBackPressed();

                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });}



                    }
                });


                AlertDialog alertDialog = builder.create();

                alertDialog.show();
            }


        });


    }



    @Override
    protected void onResume() { //새로고침
        super.onResume();
    }


}
