package com.example.firebase_application;

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

public class Tradestate extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Conditioninformation> arrayList1=new ArrayList<>();
    ArrayList<Conditioninformation> myList=new ArrayList<>();//물품객체를 담을 어레이 리스트 (어댑터쪽으로);
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private String id;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tradestate);

        recyclerView=findViewById(R.id.condition_rv);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), 1));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setHasFixedSize(true);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {//지금 로그인된 유저가 있다면
            id=user.getEmail();
            database = FirebaseDatabase.getInstance();//파이어베이스 데이터베이스 연동

            databaseReference = database.getReference("Trade state"); //DB테이블 연결
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    arrayList1.clear();//기존 배열리스트가 존재하지 않게 초기화
                    myList.clear();
                    for(DataSnapshot snapshot: dataSnapshot.getChildren()){//반복문으로 데이터 List를 추출해냄
                        Conditioninformation user=snapshot.getValue(Conditioninformation.class); //만들어뒀던 User객체에 데이터를 담는다.
                        arrayList1.add(user); //담은 데이터들을 배열리스트에 넣고 리사이클러뷰로 보낼준

                    }
                    for(Conditioninformation object: arrayList1){
                        if(object.getBuyeremail().contains(id)){
                            myList.add(object);
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
        }else{
            Toast.makeText(getApplicationContext(),"로그인을 해야합니다.",Toast.LENGTH_SHORT).show();

        }




        adapter=new Tradestateadapter(myList,this);
        recyclerView.setAdapter(adapter);//리사이클러뷰에 어댑터 연결
    }



}




