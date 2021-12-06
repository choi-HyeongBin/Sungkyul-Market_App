package com.example.firebase_application;

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

public class TradeinformationReviewWrite extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TradeinformationReviewWriteAdapter adapter;

    String email;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sell_review_write);

        recyclerView=findViewById(R.id.seller_review_write_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //리사이클러뷰 구분선
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), 1));

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        email = user.getEmail();


        if (user != null) {//지금 로그인된 유저가 있다면
            FirebaseRecyclerOptions<Conditioninformation> options =
                    new FirebaseRecyclerOptions.Builder<Conditioninformation>()
                            .setQuery(FirebaseDatabase.getInstance().getReference().child("Buy Product Review").orderByChild("buyeremail").equalTo(email), Conditioninformation.class).build();
            adapter=new TradeinformationReviewWriteAdapter(options);
            recyclerView.setAdapter(adapter);//리사이클러뷰에 어댑터 연결

        }
        else{
            Toast.makeText(getApplicationContext(),"로그인을 해야합니다.",Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();

    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.startListening();
    }
}
