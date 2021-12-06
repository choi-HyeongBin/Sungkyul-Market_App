package com.example.firebase_application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class Productlook2 extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private FirebaseAuth auth=FirebaseAuth.getInstance();
    private FirebaseUser user;
    private  String uid;
    public static Context CONTEXT;//새로고침 위한 전역변수

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.productlook2);

        CONTEXT = this;//새로고침할 액티비티 지정

        TextView tv1=findViewById(R.id.productlook2_name);//게시글
        final TextView tv2=findViewById(R.id.productlook2_useremail);//판매자이메일
        ImageView iv1=findViewById(R.id.productlook2_imv1);//이미지
        TextView tv3=findViewById(R.id.productlook2_state);//상태
        TextView tv4=findViewById(R.id.productlook2_text);//설명
        TextView tv5=findViewById(R.id.productlook2_money);//가격
        TextView tv6=findViewById(R.id.productlook2_category);//카테고리


        Intent intent=getIntent();
        tv1.setText(intent.getStringExtra("name"));
        tv2.setText(intent.getStringExtra("useremail"));

        String image=intent.getStringExtra("imv");
        Glide.with(this)
                .load(image)
                .into(iv1);
        tv3.setText(intent.getStringExtra("state"));
        tv4.setText(intent.getStringExtra("text"));
        tv5.setText(intent.getStringExtra("money")+"  (원)");
        tv6.setText(intent.getStringExtra("category"));

        final String Postname1 = intent.getStringExtra("name");
        final String useremail = intent.getStringExtra("useremail");
        final String Quality1 = intent.getStringExtra("state");
        final String Money1 = intent.getStringExtra("money");
        final String Explanation1 = intent.getStringExtra("text");
        final String category1 = intent.getStringExtra("category");
        final String Date1 = intent.getStringExtra("date");
        final String image1=intent.getStringExtra("imv");

    }


}
