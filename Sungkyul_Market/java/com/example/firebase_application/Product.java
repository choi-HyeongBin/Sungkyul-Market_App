package com.example.firebase_application;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;


public class Product extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Productinformation> arrayList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    SearchView searchView;

    private ImageButton productbtn;


    @Override
    public void onStart() {
        super.onStart();

        if(searchView!=null){
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    search(newText);
                    return true;
                }
            });
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {


        ViewGroup viewGroup;
        viewGroup = (ViewGroup) inflater.inflate(R.layout.product,container,false);


        recyclerView=viewGroup.findViewById(R.id.recyclerview_fragment);
        recyclerView.setHasFixedSize(true);//리사이클러뷰 기존성능 강화
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), 1));


        productbtn = (ImageButton)viewGroup.findViewById(R.id.fragement_recycerview_ibtn1);

        productbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final CharSequence[] items = {"전체","교재","패션의류","명품","노트북/데스크탑","카메라/캠코더","리빙/생활","게임","기타"};

                AlertDialog.Builder alertDialogBuilder =new AlertDialog.Builder(getActivity());
                alertDialogBuilder.setTitle("카테고리 선택");
                alertDialogBuilder.setItems(items, new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        ArrayList<Productinformation> myList=new ArrayList<>();

                        for(Productinformation object: arrayList){

                            if(object.getCategory().contains(items[id])){
                                myList.add(object);

                            }

                            if (items[id] == "전체"){
                                    myList.add(object);
                            }
                        }

                        adapter=new Productadapter(myList,getActivity());
                        recyclerView.setAdapter(adapter);//리사이클러뷰에 어댑터 연결*/


                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog =alertDialogBuilder.create();
                alertDialog.show();
            }
        });


        layoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        arrayList=new ArrayList<>(); //User객체를 담을 어레이 리스트 (어댑터쪽으로)

        searchView=viewGroup.findViewById(R.id.fragement_recycerview_edt1);

        database=FirebaseDatabase.getInstance();//파이어베이스 데이터베이스 연동

        databaseReference=database.getReference("Product2"); //DB테이블 연결

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //파이어베이스 데이터베이스의 데이터를 받아오는 곳
                arrayList.clear();//기존 배열리스트가 존재하지 않게 초기화
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){//반복문으로 데이터 List를 추출해냄
                    Productinformation user=snapshot.getValue(Productinformation.class); //만들어뒀던 User객체에 데이터를 담는다.
                    arrayList.add(user); //담은 데이터들을 배열리스트에 넣고 리사이클러뷰로 보낼준비

                }
                Collections.reverse(arrayList);

                adapter.notifyDataSetChanged();//리스트 저장및 새로고침

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //디비를 가져오던중 에러 발생시
                Log.e("recycerview.java", String.valueOf(databaseError.toException())); //에러문 출력
            }

        });

        adapter=new Productadapter(arrayList,getActivity());
        recyclerView.setAdapter(adapter);//리사이클러뷰에 어댑터 연결


        return viewGroup;
    }



    private void search(String s){

        ArrayList<Productinformation> myList=new ArrayList<>();

        for(Productinformation object: arrayList){
            if(object.getName().contains(s)){
                myList.add(object);
            }
        }

        adapter=new Productadapter(myList,getActivity());
        recyclerView.setAdapter(adapter);//리사이클러뷰에 어댑터 연결*/

    }

}