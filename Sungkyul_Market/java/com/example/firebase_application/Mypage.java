package com.example.firebase_application;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Mypage extends Fragment {
     Button btn2,btn4,btn5,btn6,btn7,btn8,btn9,btn10;
    Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup viewGroup;
        viewGroup = (ViewGroup) inflater.inflate(R.layout.mypage,container,false);
        btn4=viewGroup.findViewById(R.id.mypage_btn4);
        btn2 =viewGroup.findViewById(R.id.mypage_btn2);
        btn5=viewGroup.findViewById(R.id.mypage_btn5);
        btn6=viewGroup.findViewById(R.id.mypage_btn6);
        btn7=viewGroup.findViewById(R.id.mypage_btn7);
        btn8=viewGroup.findViewById(R.id.mypage_btn8);
        btn9=viewGroup.findViewById(R.id.mypage_btn9);
        btn10=viewGroup.findViewById(R.id.mypage_btn10);




            btn4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), Myinform.class);
                        startActivity(intent);


                }
            });


            btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), Productcontrol.class);
                    startActivity(intent);

                }
            });


            btn5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), Tradeinformation.class);
                    startActivity(intent);

                }
            });

            btn6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), Tradeinformation2.class);
                    startActivity(intent);

                }
            });
            btn7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), Tradestate.class);
                    startActivity(intent);

                }
            });
            btn8.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), Tradestate2.class);
                    startActivity(intent);

                }
            });
            btn9.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), Messagehomesend.class);
                    startActivity(intent);

                }
            });
            btn10.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), Messagehomereceive.class);
                    startActivity(intent);

                }
            });




        return  viewGroup;

    }
}
