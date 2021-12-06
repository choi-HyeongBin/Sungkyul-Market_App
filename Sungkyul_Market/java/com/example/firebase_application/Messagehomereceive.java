package com.example.firebase_application;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class Messagehomereceive extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Messagereceiveadapter adapter;
    private RecyclerView.LayoutManager layoutManager;


    String email;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.receive);


        recyclerView=findViewById(R.id.receive_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setHasFixedSize(true);//리사이클러뷰 기존성능 강화

        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), 1));


        layoutManager=new LinearLayoutManager(getApplication());
        recyclerView.setLayoutManager(layoutManager);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        if (user != null) {//지금 로그인된 유저가 있다면
            email = user.getEmail();

            FirebaseRecyclerOptions<Productinformation> options =
                    new FirebaseRecyclerOptions.Builder<Productinformation>()
                            .setQuery(FirebaseDatabase.getInstance().getReference().child("Messagereceive").orderByChild("receiveemail").equalTo(email),
                                    Productinformation.class).build();

            adapter =new Messagereceiveadapter(options, getApplicationContext());
            recyclerView.setAdapter(adapter);//리사이클러뷰에 어댑터 연결
            adapter.startListening();
        }
        else{
            Toast.makeText(getApplicationContext(),"로그인을 해야합니다.",Toast.LENGTH_SHORT).show();

        }
    }


}
