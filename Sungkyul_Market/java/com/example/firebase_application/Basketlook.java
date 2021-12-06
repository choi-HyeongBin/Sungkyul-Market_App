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

public class Basketlook extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private FirebaseAuth auth=FirebaseAuth.getInstance();
    private FirebaseUser user;
    private  String uid;
    public static Context CONTEXT;//새로고침 위한 전역변수

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basketlook);


        TextView tv1=findViewById(R.id.productlook1_name);//게시글
        final TextView tv2=findViewById(R.id.productlook1_useremail);//판매자이메일
        ImageView iv1=findViewById(R.id.productlook1_imv1);//이미지
        TextView tv3=findViewById(R.id.productcontrol_state);//상태
        TextView tv4=findViewById(R.id.productlook1_text);//설명
        TextView tv5=findViewById(R.id.productlook1_money);//가격
        TextView tv6=findViewById(R.id.productcontrol_category);//카테고리
        Button btnReview=findViewById(R.id.productlook1_btnReview); //리뷰 보기 버튼

        Intent intent=getIntent();
        tv1.setText(intent.getStringExtra("name"));
        tv2.setText(intent.getStringExtra("useremail"));
        btnReview.setOnClickListener(new View.OnClickListener() { //판매자 이메일 클릭후 평가보기
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Sellerlook.class);
                intent.putExtra("Selleremail",tv2.getText());
                startActivity(intent);
            }
        });
        String image=intent.getStringExtra("imv");
        Glide.with(this)
                .load(image)
                .into(iv1);
        tv3.setText(intent.getStringExtra("state"));
        tv4.setText(intent.getStringExtra("text"));
        tv5.setText(intent.getStringExtra("money")+"  (원)");
        tv6.setText(intent.getStringExtra("category"));

        final String Postname1 = intent.getStringExtra("name"); //여기서부터<>
        final String useremail = intent.getStringExtra("useremail");
        final String Quality1 = intent.getStringExtra("state");
        final String Money1 = intent.getStringExtra("money");
        final String Explanation1 = intent.getStringExtra("text");
        final String category1 = intent.getStringExtra("category");
        final String Date1 = intent.getStringExtra("date");
        final String image1=intent.getStringExtra("imv");
        Button btn2=findViewById(R.id.productlook1_btn2);//구매하기 버튼


        user = FirebaseAuth.getInstance().getCurrentUser();

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//<>
                if (user != null ) {//지금 로그인된 유저가 있다면
                    uid = user.getUid();//해당 유저 UID 받아옴
                    String email=user.getEmail();
                    String Selleragree="0";
                    String Buyeragree="0";
                    Conditioninformation product = new Conditioninformation();

                    product.selleragree=Selleragree;
                    product.buyeragree=Buyeragree;
                    product.useremail = useremail;
                    product.buyeremail = email;
                    product.date = Date1;
                    product.money = Money1;
                    product.state = Quality1;
                    product.name = Postname1;
                    product.imv = image1;
                    product.text = Explanation1;
                    product.category=category1;

                    if (email.equals(useremail)){
                        Toast.makeText(getApplicationContext(),"자신이 올린 물건은 구매 하실 수 없습니다.",Toast.LENGTH_SHORT).show();
                    }else {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        database.getReference().child("Trade state").push().setValue(product);
                        Intent intent=new Intent(getApplication(),Messagesend.class);
                        intent.putExtra("tv1",tv2.getText().toString());
                        intent.putExtra("tv2",useremail);

                        databaseReference = FirebaseDatabase.getInstance().getReference().child("Product2");
                        Query mQuery = databaseReference.orderByChild("name").equalTo(Postname1);
                        mQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot ds : snapshot.getChildren()) {
                                    databaseReference = FirebaseDatabase.getInstance().getReference().child("Product2");
                                    Query mmquery=databaseReference.orderByChild("date").equalTo(Date1);
                                    mmquery.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            for (DataSnapshot ds : snapshot.getChildren()) {
                                                ds.getRef().removeValue();
                                            }
                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {
                                        }
                                    });
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                        startActivity(intent);
                        finish();
                    }}
                else {
                    Toast.makeText(getApplicationContext(),"로그인을 해주세요",Toast.LENGTH_SHORT).show();
                }

            }
        });//<>


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {//지금 로그인된 유저가 있다면
            menu.findItem(R.id.action_logout).setVisible(true);
            menu.findItem(R.id.action_login).setVisible(false);

        } else {
            menu.findItem(R.id.action_logout).setVisible(false);
            menu.findItem(R.id.action_login).setVisible(true);

        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_logout:

            case R.id.action_login:
                if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                    Intent intent = new Intent(getApplicationContext(), Login.class);
                    startActivity(intent);
                } else {
                    LogoutAsk();

                }
                return true;
            // ...
            // ...
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void LogoutAsk() {
        AlertDialog.Builder dlg = new AlertDialog.Builder(this);
        dlg.setTitle("알림");
        dlg.setMessage("로그아웃을 하시겠습니까?");
        dlg.setIcon(R.mipmap.ic_launcher);
        dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FirebaseAuth.getInstance().signOut();
                onRestart();
                invalidateOptionsMenu();
                tostmsg();

            }
        });
        dlg.setNegativeButton("취소", null);
        dlg.show();
    }

    public void tostmsg() {
        Toast.makeText(this, "로그아웃이 되었습니다.", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() { //새로고침
        super.onResume();
    }



}
