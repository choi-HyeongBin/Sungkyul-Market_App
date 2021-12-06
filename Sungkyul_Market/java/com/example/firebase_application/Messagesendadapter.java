package com.example.firebase_application;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import android.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import com.orhanobut.dialogplus.ViewHolder;
import com.orhanobut.dialogplus.DialogPlus;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class Messagesendadapter extends FirebaseRecyclerAdapter<Productinformation,Messagesendadapter.CustomViewHolder> {

    private DatabaseReference databaseReference;

    private Context context;







    public Messagesendadapter(FirebaseRecyclerOptions<Productinformation> options, Context context) {
        super(options);
        this.context = context;


    }


    @Override
    protected void onBindViewHolder(@NonNull final CustomViewHolder holder,final int position, @NonNull final Productinformation model) {




        holder.text.setText(model.getText());
        holder.receiveemail.setText(model.getReceiveemail());
        holder.date.setText(model.getDate());

        final String text2= (String) holder.text.getText();

        final String date2=(String) holder.date.getText();
        final String receiveemail2=(String) holder.receiveemail.getText();





        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent=new Intent(context, Messagesendlook.class);

                intent.putExtra("receiveemail",receiveemail2);
                intent.putExtra("date",date2);
                intent.putExtra("text",text2);

                context.startActivity(intent.addFlags(FLAG_ACTIVITY_NEW_TASK));

            }
        });



        holder.delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                AlertDialog.Builder dlg = new AlertDialog.Builder(holder.itemView.getContext());
                dlg.setTitle("알림");
                dlg.setMessage("쪽지를 삭제 하겠습니까?");
                dlg.setIcon(R.mipmap.ic_launcher);
                dlg.setNegativeButton("취소", null);

                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        FirebaseUser user;
                        user = FirebaseAuth.getInstance().getCurrentUser();

                        holder.date.setText(model.getDate());



                        final String email = user.getEmail();


                        databaseReference = FirebaseDatabase.getInstance().getReference().child("Messagesend");
                        Query mQuery=databaseReference.orderByChild("date").equalTo(date2);
                        if (email.equals(email)){
                            mQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for(DataSnapshot ds: snapshot.getChildren()){
                                        ds.getRef().removeValue();}
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });}



                    }
                });

                dlg.show();


            }
        });
    }


    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.send_list_item,parent,false);
        return new CustomViewHolder(view);

    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView text;
        TextView date;
        TextView receiveemail;
        Button delete;


        public CustomViewHolder(@NonNull  View itemView) {
            super(itemView);
            receiveemail=itemView.findViewById(R.id.messagesendlistitem_tv1);
            date=itemView.findViewById(R.id.messagesendlistitem_tv2);
            text=itemView.findViewById(R.id.messagesendlistitem_tv3);
            this.delete=itemView.findViewById(R.id.messagesendlistitem_btn1);


        }

    }
}
