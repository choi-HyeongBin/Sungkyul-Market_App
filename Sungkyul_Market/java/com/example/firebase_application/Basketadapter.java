package com.example.firebase_application;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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

import java.util.ArrayList;

public class Basketadapter extends RecyclerView.Adapter<Basketadapter.CustomViewHolder>  {
    private ArrayList<Productinformation>arrayList3;

    private Context context;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private FirebaseUser user;
    private  String uid;



    public Basketadapter(ArrayList<Productinformation> arrayList, Context context) {
        this.arrayList3 = arrayList;
        this.context = context;
    }
    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.basket_list_item,parent,false);
        CustomViewHolder holder= new CustomViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(@NonNull final CustomViewHolder holder, final int position) {
        Glide.with(holder.itemView)
                .load(arrayList3.get(position).getImv())
                .into(holder.iv_image);
        holder.tv_name.setText(arrayList3.get(position).getName());
        holder.tv_useremail.setText(arrayList3.get(position).getUseremail());
        holder.tv_money.setText(String.valueOf(arrayList3.get(position).getMoney()));
        holder.tv_category2.setText(arrayList3.get(position).getCategory());

        final String name= (String) holder.tv_name.getText();
        user = FirebaseAuth.getInstance().getCurrentUser();
        final String date1=arrayList3.get(position).getDate();
        holder.customadapter2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database=FirebaseDatabase.getInstance();//파이어베이스 데이터베이스 연동

                uid = user.getUid();//해당 유저 UID 받아옴
                databaseReference = database.getReference("User").child(uid).child("basket").child(name+date1); //DB테이블 연결
                databaseReference.setValue(null);

            }
        });

        final String image1= arrayList3.get(position).getImv();

        final String Postname1= (String) holder.tv_name.getText();
        final String Money1= (String) holder.tv_money.getText();
        final String Explanation1=arrayList3.get(position).getText();
        final String Quality1=arrayList3.get(position).getState();
        final String useremail1=arrayList3.get(position).getUseremail();
        final String Date1=arrayList3.get(position).getDate();
        final String category1= arrayList3.get(position).getCategory();

        holder.customadapter3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (user != null ) {

                    String Selleragree="0";
                    String Buyeragree="0";

                    uid = user.getUid();//해당 유저 UID 받아옴
                    String email=user.getEmail();

                    Conditioninformation product = new Conditioninformation();
                    product.selleragree=Selleragree;
                    product.buyeragree=Buyeragree;
                    product.useremail = useremail1;
                    product.buyeremail = email;
                    product.date = Date1;
                    product.money = Money1;
                    product.state = Quality1;
                    product.name = Postname1;
                    product.imv = image1;
                    product.text = Explanation1;
                    product.category=category1;

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    database.getReference().child("Trade state").push().setValue(product);

                    Intent intent=new Intent(context,Messagesend.class);
                    intent.putExtra("tv2",useremail1);

                    context.startActivity(intent);


                    database=FirebaseDatabase.getInstance();//파이어베이스 데이터베이스 연동

                    uid = user.getUid();//해당 유저 UID 받아옴
                    databaseReference = database.getReference("User").child(uid).child("basket").child(name+date1); //DB테이블 연결
                    databaseReference.setValue(null);


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

                }
            }
        });


        final String imv= arrayList3.get(position).getImv();
        final String money= (String) holder.tv_money.getText();
        final String text=arrayList3.get(position).getText();
        final String state=arrayList3.get(position).getState();
        final String useremail=arrayList3.get(position).getUseremail();
        final String date=arrayList3.get(position).getDate();
        final String category= arrayList3.get(position).getCategory();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, Basketlook.class);
                intent.putExtra("imv", imv);
                intent.putExtra("name",name);
                intent.putExtra("money",money);
                intent.putExtra("text",text);
                intent.putExtra("state",state);
                intent.putExtra("useremail",useremail);
                intent.putExtra("date",date);
                intent.putExtra("category",category);

                context.startActivity(intent);
            }
        });

    }
    @Override
    public int getItemCount() {
        //삼항 연산자
        return (arrayList3 !=null ? arrayList3.size():0);
    }
    public class CustomViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_image;
        TextView tv_name;
        TextView tv_useremail;
        TextView tv_money;
        TextView tv_category2;
        Button customadapter2;
        Button customadapter3;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.iv_image=itemView.findViewById(R.id.iv_image2);
            this.tv_name=itemView.findViewById(R.id.tv_name2);
            this.tv_useremail=itemView.findViewById(R.id.tv_id2);
            this.tv_money=itemView.findViewById(R.id.tv_money2);
            this.tv_category2=itemView.findViewById(R.id.tv_category2);
            this.customadapter2=itemView.findViewById(R.id.activity_list2_btn);
            this.customadapter3=itemView.findViewById(R.id.activity_list2_btn1);




        }


    }
}
