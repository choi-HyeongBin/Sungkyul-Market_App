package com.example.firebase_application;

import android.Manifest;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.session.MediaSession;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;


import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;


import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Registration extends Fragment {


    private ImageView imv;
    private Button registration_btn2;
    private EditText registration_edt1;
    private EditText registration_edt2;
    private EditText registration_edt3;
    private Spinner spinner2;
    private CheckBox registration_checkbox;
    private RadioGroup registration_rdogroup;
    private RadioButton registration_rdbtn1;
    private RadioButton registration_rdbtn2;
    private RadioButton registration_rdbtn3;
    private RadioButton registration_rdbtn4;
    private Button registration_btn3;
    private TextView txt;
    int REQUEST_IMAGE_CODE = 1001;
    int REQUEST_EXTERNAL_STORAGE_PERMISSION = 1002;
    private FirebaseAuth mAuth;
    private FirebaseStorage storage;
    String time2;
    private FirebaseUser user;



    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup viewGroup;
        viewGroup = (ViewGroup) inflater.inflate(R.layout.registration, container, false);
        super.onCreate(savedInstanceState);
        txt = (TextView) viewGroup.findViewById(R.id.registration_txt);
        storage = FirebaseStorage.getInstance();




        registration_edt1 = (EditText) viewGroup.findViewById(R.id.registration_edt1);
        registration_edt2 = (EditText) viewGroup.findViewById(R.id.registration_edt2);
        registration_edt3 = (EditText) viewGroup.findViewById(R.id.registration_edt3);
        spinner2 = (Spinner) viewGroup.findViewById(R.id.registration_sp2);


        registration_btn2 = (Button) viewGroup.findViewById(R.id.registration_btn2);
        registration_btn3 = (Button) viewGroup.findViewById(R.id.registration_btn3);
        registration_checkbox = (CheckBox) viewGroup.findViewById(R.id.registration_checkbox);
        registration_rdogroup = (RadioGroup) viewGroup.findViewById(R.id.registration_rdogroup);
        registration_rdbtn1 = (RadioButton) viewGroup.findViewById(R.id.registration_rdbtn1);

        registration_rdbtn2 = (RadioButton) viewGroup.findViewById(R.id.registration_rdbtn2);
        registration_rdbtn3 = (RadioButton) viewGroup.findViewById(R.id.registration_rdbtn3);
        registration_rdbtn4 = (RadioButton) viewGroup.findViewById(R.id.registration_rdbtn4);



        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_EXTERNAL_STORAGE_PERMISSION);
            }
        }

        imv = viewGroup.findViewById(R.id.registration_imv1);

        imv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQUEST_IMAGE_CODE);




            }
        });



        registration_checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
            }
        });

        registration_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = FirebaseAuth.getInstance().getCurrentUser();

                if (user == null){
                    Toast.makeText(getActivity(),"로그인을 해주세요",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), Login.class);
                    startActivity(intent);

                }
                else {
                    upload();
                    finish();

                }


            }
        });

        registration_checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (registration_checkbox.isChecked() == true) {
                    registration_edt2.setVisibility(View.GONE);
                }else {
                    registration_edt2.setVisibility(View.VISIBLE);

                }
            }
        });

        registration_btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Main.class);
                startActivity(intent);
            }
        });


        return viewGroup;

    }






    private void upload() {


        FirebaseDatabase database = FirebaseDatabase.getInstance();

        //회원가입때 닉네임 받아오기

        //회원정보 다 입력했을떄
        Productinformation product = new Productinformation(); //유저정보 클래스 사용
        product.name = registration_edt1.getText().toString();
        product.text = registration_edt3.getText().toString();
        product.category = spinner2.getSelectedItem().toString();


        boolean test2 = registration_edt1.getText().toString().contains(".");
        boolean test3 = registration_edt1.getText().toString().contains("#");
        boolean test4 = registration_edt1.getText().toString().contains("$");
        boolean test5 = registration_edt1.getText().toString().contains("[");
        boolean test6 = registration_edt1.getText().toString().contains("]");




        if(test2 == true) {
            Toast.makeText(getActivity(), ".을 사용하실수 없습니다.", Toast.LENGTH_SHORT).show();

        }else {
            product.name = registration_edt1.getText().toString();

        }


        if (registration_checkbox.isChecked() == true) {
            product.money = registration_checkbox.getText().toString();
        }else{
            product.money =registration_edt2.getText().toString();
        }

        String money2 = product.money;

        int id = registration_rdogroup.getCheckedRadioButtonId();

        if (id == R.id.registration_rdbtn1) {
            product.state = registration_rdbtn1.getText().toString();
        } else if (id == R.id.registration_rdbtn2) {
            product.state = registration_rdbtn2.getText().toString();
        } else if (id == R.id.registration_rdbtn3) {
            product.state = registration_rdbtn3.getText().toString();
        }else if (id == R.id.registration_rdbtn4) {
            product.state = registration_rdbtn4.getText().toString();
        }

        int state2 =registration_rdogroup.getCheckedRadioButtonId();

        final StorageReference storageRef = storage.getReference();
        storageRef.child(product.useremail+product.name + "image.jpeg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {

            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'
            }
        }).addOnFailureListener(new OnFailureListener() {
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });


        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        product.useremail =user.getEmail();

        product.date = time2;
        String test = "https://firebasestorage.googleapis.com/v0/b/fir-listexample-24adb.appspot.com/o/"+product.useremail+time2+"image.jpeg?alt=media";

        product.imv =test;






        if (registration_edt1.length() <= 0 || registration_edt3.length() <= 0 || money2.length() <= 0 || state2 <= 0 || time2 == null){
            Toast.makeText(getActivity(), "모든 값을 입력해주세요.", Toast.LENGTH_SHORT).show();


        }else if(test2 ==true || test3 == true || test4 == true || test5 == true || test6 ==true){
            Toast.makeText(getActivity(), "'.'  ,'#'  ,'$'  ,'['  ,']'를 사용하실수 없습니다.", Toast.LENGTH_SHORT).show();
        }
        else {
            database.getReference().child("Product2").push().setValue(product);

            Intent intent = new Intent(getActivity(), Main.class);
            startActivity(intent);

        }



    }




    private String getTime(){

        long mNow;
        Date mDate;
        SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        time2 = mFormat.format(mDate);
        return time2;

    }


    private void finish() {

    }








    public void onActivityResult(int requestCode, int resultCode,  @Nullable Intent data) {

        if (requestCode == REQUEST_IMAGE_CODE) {
            if (data != null){
            Uri image = data.getData();
            mAuth = FirebaseAuth.getInstance();
            FirebaseUser user = mAuth.getCurrentUser();
            StorageReference storageRef = storage.getReference();


            Productinformation product = new Productinformation();
            product.name = registration_edt1.getText().toString();
            product.money = registration_edt2.getText().toString();
            product.text = registration_edt3.getText().toString();
            product.useremail =user.getEmail() ;

            product.date = getTime();


            StorageReference riversRef = storageRef.child( product.useremail+time2+ "image.jpeg");
            UploadTask uploadTask = riversRef.putFile(image);
            riversRef.getDownloadUrl();



            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), image);
                imv.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            } }
        }else {
            return;

        }

    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }








}









