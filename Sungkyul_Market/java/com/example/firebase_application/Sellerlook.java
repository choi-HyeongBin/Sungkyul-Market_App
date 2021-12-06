package com.example.firebase_application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class
Sellerlook extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private ArrayList<Sellerevaluationinformation> arrayList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    ArrayList<Sellerevaluationinformation> myList=new ArrayList<>();//물품객체를 담을 어레이 리스트 (어댑터쪽으로);
    private String Selleremail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sellerlook);


        recyclerView=findViewById(R.id.sellerlook_rv);

        //리사이클러뷰 구분선
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), 1));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);//리사이클러뷰 기존성능 강화

        arrayList=new ArrayList<Sellerevaluationinformation>(); //User객체를 담을 어레이 리스트 (어댑터쪽으로)

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        //이메일 확인하기 위해
        Intent intent =getIntent();

        Selleremail=intent.getStringExtra("Selleremail");

        database = FirebaseDatabase.getInstance();//파이어베이스 데이터베이스 연동

        databaseReference = database.getReference("Review"); //DB테이블 연결

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.clear();//기존 배열리스트가 존재하지 않게 초기화

                for(DataSnapshot snapshot: dataSnapshot.getChildren()){//반복문으로 데이터 List를 추출해냄
                    Sellerevaluationinformation user=snapshot.getValue(Sellerevaluationinformation.class); //만들어뒀던 User객체에 데이터를 담는다.
                    arrayList.add(user); //담은 데이터들을 배열리스트에 넣고 리사이클러뷰로 보낼준비

                }

                for(Sellerevaluationinformation object: arrayList){
                    try {
                        if(object.getSelleremail().contains(Selleremail)){
                            myList.add(object);
                        }
                    }catch (Exception e){

                    }
                }
                adapter.notifyDataSetChanged();//리스트 저장및 새로고침
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //디비를 가져오던중 에러 발생시
                Log.e("recycerview.java", String.valueOf(databaseError.toException())); //에러문 출력
            }
        });

        adapter=new Sellerlookadapter(myList,this);
        recyclerView.setAdapter(adapter);//리사이클러뷰에 어댑터 연결
    }
}
