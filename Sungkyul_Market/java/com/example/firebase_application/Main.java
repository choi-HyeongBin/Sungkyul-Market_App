package com.example.firebase_application;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class Main extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Product fragment1;
    Basket fragment2;
    Registration fragment3;
    Mypage fragment5;
    private ArrayList<Productinformation> arrayList;
    private FirebaseUser user;
    SearchView searchView;
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerView;

    public static Context CONTEXT;//새로고침 위한 전역변수


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);//기본 제목을 없애줍니다.


        CONTEXT = this;//새로고침할 액티비티 지정


        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        fragment1 = new Product();
        fragment2=new Basket();
        fragment3=new Registration();
        fragment5=new Mypage();

        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, fragment1).commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    //menu_bottom.xml에서 지정해줬던 아이디 값을 받아와서 각 아이디값마다 다른 이벤트를 발생시킵니다.
                    case R.id.home: {
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, fragment1).commitAllowingStateLoss();
                        return true;
                    }

                    case R.id.basket:{ getSupportFragmentManager().beginTransaction() .replace(R.id.main_layout,fragment2).commitAllowingStateLoss();
                        return true;
                    }
                    case R.id.registration:{ getSupportFragmentManager().beginTransaction() .replace(R.id.main_layout,fragment3).commitAllowingStateLoss();
                        return true;
                    }
                    case R.id.report: {

                        user = FirebaseAuth.getInstance().getCurrentUser();
                        Intent email = new Intent(Intent.ACTION_SEND);
                        if(user!=null) {
                            String[] address = {"chb7444@naver.com"};

                            email.setType("plain/text");
                            email.putExtra(Intent.EXTRA_EMAIL, address);
                            email.putExtra(Intent.EXTRA_SUBJECT, "<신고합니다>");
                            email.putExtra(Intent.EXTRA_TEXT, "제목:" + "\n신고할 아이디:\n내용:\n");
                            startActivity(Intent.createChooser(email, "Send mail..."));

                        }else{
                            Toast.makeText(Main.this, "로그인을 해주세요", Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    }

                    case R.id.mypage: {
                        user = FirebaseAuth.getInstance().getCurrentUser();
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, fragment5).commitAllowingStateLoss();
                        return true;

                    }

                    default:
                        return false;
                }
            }
        });
    }



    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {//지금 로그인된 유저가 있다면
            menu.findItem(R.id.action_logout).setVisible(true);
            menu.findItem(R.id.action_login).setVisible(false);

        }
        else {
            menu.findItem(R.id.action_logout).setVisible(false);
            menu.findItem(R.id.action_login).setVisible(true);

        }
        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater =getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);



        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_login:


            case R.id.action_logout:
                if (FirebaseAuth.getInstance().getCurrentUser() == null) {//만약 사용자가 없다면 로그인화면으로 /있다면 로그아웃을
                    Intent intent = new Intent(getApplicationContext(), Login.class);
                    startActivity(intent);
                    onBackPressed();
                } else {
                    LogoutAsk();
                }
                return true;

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
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() { //새로고침
        super.onResume();
    }


    public void onBackPressed(){

    }


}