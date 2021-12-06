package com.example.firebase_application;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Collections;

public class Tradeinformation extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Tradeinformationadapter adapter;

    String email;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sell);


        recyclerView=findViewById(R.id.information_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), 1));

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        Button btnReviewWrie=findViewById(R.id.btnReviewWrite);

        btnReviewWrie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user!=null){
                    Intent intent=new Intent(getApplicationContext(),TradeinformationReviewWrite.class);
                    startActivity(intent);}
                else{
                    Toast.makeText(Tradeinformation.this, "로그인을 해야합니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        if (user != null) {//지금 로그인된 유저가 있다면
            email=user.getEmail();
            FirebaseRecyclerOptions<Conditioninformation> options =
                    new FirebaseRecyclerOptions.Builder<Conditioninformation>()
                            .setQuery(FirebaseDatabase.getInstance().getReference().child("Trade information").orderByChild("buyeremail").equalTo(email), Conditioninformation.class).build();
            adapter=new Tradeinformationadapter(options);
            recyclerView.setAdapter(adapter);//리사이클러뷰에 어댑터 연결
            adapter.startListening();
        }
        else{
            Toast.makeText(getApplicationContext(),"로그인을 해야합니다.",Toast.LENGTH_SHORT).show();
        }

    }

}
