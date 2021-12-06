package com.example.firebase_application;

import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
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

public class Productcontrol extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Productcontroladapter adapter;
    FirebaseUser user;
    FirebaseUser useremail;

    String savemyemail;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.productcontrol);


        recyclerView=findViewById(R.id.productcontrol_lv1);
        
        //리사이클러뷰 구분선
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), 1));
        
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        user = FirebaseAuth.getInstance().getCurrentUser();
        useremail = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {//지금 로그인된 유저가 있다면
            savemyemail = useremail.getEmail();
            FirebaseRecyclerOptions<Productinformation> options =
                    new FirebaseRecyclerOptions.Builder<Productinformation>()
                            .setQuery(FirebaseDatabase.getInstance().getReference().child("Product2").orderByChild("useremail").equalTo(savemyemail),Productinformation.class)
                            .build();
            adapter=new Productcontroladapter(options);
            recyclerView.setAdapter(adapter);//리사이클러뷰에 어댑터 연결
        }
        else{
            Toast.makeText(getApplicationContext(),"로그인을 해야합니다.",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (user != null) {
            adapter.startListening();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (user != null) {
        adapter.startListening();
        }
    }
}
