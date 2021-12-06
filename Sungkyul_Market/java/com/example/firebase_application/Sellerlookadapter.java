package com.example.firebase_application;

import android.app.AlertDialog;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;

public class Sellerlookadapter extends RecyclerView.Adapter <Sellerlookadapter.CustomViewHolder>{

    private ArrayList<Sellerevaluationinformation> arrayList;
    private Context context;

    public Sellerlookadapter(ArrayList<Sellerevaluationinformation> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.seller_list_item,parent,false);
        CustomViewHolder holder= new CustomViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull final CustomViewHolder holder, final int position) { //뷰홀더가 재활용될 때 실행되는 메서드

        holder.sellerlistitem_tvPost.setText(arrayList.get(position).getPostname());
        holder.sellerlistitem_tvrating.setText(arrayList.get(position).getRating());
        holder.sellerlistitem_tvEmail.setText(arrayList.get(position).getBuyeremail());
        holder.sellerlistitem_tvReview.setText(arrayList.get(position).getReview());

    }

    @Override
    public int getItemCount() { //아이템 개수 조회
        return (arrayList !=null ? arrayList.size():0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder { //seller_list_item과 연결

        ImageView sellerlistitem_iv;
        TextView sellerlistitem_tvPost;
        TextView sellerlistitem_tvrating;
        TextView sellerlistitem_tvEmail;
        TextView sellerlistitem_tvReview;

        public CustomViewHolder(@NonNull final View itemView){
            super(itemView);
            sellerlistitem_iv=itemView.findViewById(R.id.sellerlistitem_iv);
            sellerlistitem_tvPost=itemView.findViewById(R.id.sellerlistitem_tvPost);
            sellerlistitem_tvrating=itemView.findViewById(R.id.sellerlistitem_tvrating);
            sellerlistitem_tvEmail=itemView.findViewById(R.id.sellerlistitem_tvEmail);
            sellerlistitem_tvReview=itemView.findViewById(R.id.sellerlistitem_tvReview);

        }
    }

}
