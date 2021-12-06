package com.example.firebase_application;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;


public class
Basket extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Productinformation> basketList=new ArrayList<>(); //물품객체를 담을 어레이 리스트 (어댑터쪽으로);
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup viewGroup;
        viewGroup = (ViewGroup) inflater.inflate(R.layout.basket,container,false);


        recyclerView=viewGroup.findViewById(R.id.recyclerview_basket);
        recyclerView.setHasFixedSize(true);//리사이클러뷰 기존성능 강화
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), 1));

        layoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {//지금 로그인된 유저가 있다면
            final String uid = user.getUid();//해당 유저 UID 받아옴
            database = FirebaseDatabase.getInstance();//파이어베이스 데이터베이스 연동

            databaseReference = database.getReference("User"); //DB테이블 연결
            databaseReference.child(uid).child("basket").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    basketList.clear();//기존 배열리스트가 존재하지 않게 초기화

                    for(DataSnapshot snapshot: dataSnapshot.getChildren()){//반복문으로 데이터 List를 추출해냄
                        Productinformation user=snapshot.getValue(Productinformation.class); //만들어뒀던 User객체에 데이터를 담는다.
                        basketList.add(user); //담은 데이터들을 배열리스트에 넣고 리사이클러뷰로 보낼준비
                    }
                    Collections.reverse(basketList);
                    adapter.notifyDataSetChanged();//리스트 저장및 새로고침

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    //디비를 가져오던중 에러 발생시
                    Log.e("recycerview.java", String.valueOf(databaseError.toException())); //에러문 출력
                }
            });
        }else{
            Toast.makeText(getActivity(),"로그인을 해야합니다.",Toast.LENGTH_SHORT).show();

        }
        adapter=new Basketadapter(basketList,getActivity());
        recyclerView.setAdapter(adapter);//리사이클러뷰에 어댑터 연결



        return  viewGroup;
    }

}
