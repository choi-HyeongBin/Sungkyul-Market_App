package com.example.firebase_application;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Tradestateadapter extends RecyclerView.Adapter<Tradestateadapter.CustomViewHolder> {

    private ArrayList<Conditioninformation> arrayList;
    private Context context;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private FirebaseUser user;

    public Tradestateadapter(ArrayList<Conditioninformation> arrayList, Context context){
        this.arrayList=arrayList;
        this.context=context;

        database = FirebaseDatabase.getInstance();
    }



    @NonNull
    @Override


    public Tradestateadapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.tradestate_list_item,parent,false);
        Tradestateadapter.CustomViewHolder holder= new Tradestateadapter.CustomViewHolder(view);

        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull final CustomViewHolder holder, final int position) {
        Glide.with(holder.itemView.getContext())
                .load(arrayList.get(position).getImv())
                .into(holder.condition_imv);
        holder.condition_postname.setText(arrayList.get(position).getName());
        holder.condition_money.setText(arrayList.get(position).getMoney());

        holder.condition_useremail.setText(arrayList.get(position).getUseremail());

        final String tv2=arrayList.get(position).getBuyeremail();//판매자이메일
        final String category2=arrayList.get(position).getCategory();
        final String name2 = arrayList.get(position).getName();
        final String buyerid2= arrayList.get(position).getBuyeremail();
        final String useremail2= arrayList.get(position).getUseremail();
        final String image2= arrayList.get(position).getImv();
        final String money2= arrayList.get(position).getMoney();
        final String text2=arrayList.get(position).getText();
        final String state2=arrayList.get(position).getState();
        final String date2=arrayList.get(position).getDate();
        final String buyeragree=arrayList.get(position).getBuyeragree();
        final String selleragree=arrayList.get(position).getSelleragree();


        holder.btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                AlertDialog.Builder dlg = new AlertDialog.Builder(holder.itemView.getContext());
                dlg.setTitle("알림");
                dlg.setMessage("거래를 완료 하겠습니까?");
                dlg.setIcon(R.mipmap.ic_launcher);
                dlg.setNegativeButton("취소", null);

                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        final Map<String, Object> hopperUpdates = new HashMap<>();
                        hopperUpdates.put("buyeragree","1");

                        user = FirebaseAuth.getInstance().getCurrentUser();
                        final String email = user.getEmail();
                        databaseReference = FirebaseDatabase.getInstance().getReference().child("Trade state");
                        Query mmQuery=databaseReference.orderByChild("name").equalTo(name2);
                        if (email.equals(buyerid2)){
                            mmQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for(DataSnapshot ds: snapshot.getChildren()){
                                        databaseReference = FirebaseDatabase.getInstance().getReference().child("Trade state");
                                        Query mmquery=databaseReference.orderByChild("date").equalTo(date2);
                                        mmquery.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                for (DataSnapshot ds : snapshot.getChildren()) {

                                                    ds.getRef().updateChildren(hopperUpdates);
                                                    Intent intent = new Intent(context, Tradestate.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                    context.startActivity(intent);

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
                            });}

                        if(selleragree.equals("1")) {
                            Conditioninformation product2 = new Conditioninformation();
                            product2.useremail = useremail2;
                            product2.buyeremail = buyerid2;
                            product2.date = holder.getTime();
                            product2.money = money2;
                            product2.state = state2;
                            product2.name = name2;
                            product2.imv = image2;
                            product2.text = text2;
                            product2.category = category2;

                            database.getReference().child("Trade information").push().setValue(product2);
                            database.getReference().child("Buy Product Review").push().setValue(product2);


                            databaseReference = FirebaseDatabase.getInstance().getReference().child("Trade state");
                            Query mQuery = databaseReference.orderByChild("name").equalTo(name2);
                            if (email.equals(buyerid2)) {
                                mQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for (DataSnapshot ds : snapshot.getChildren()) {
                                            databaseReference = FirebaseDatabase.getInstance().getReference().child("Trade state");
                                            Query mmquery=databaseReference.orderByChild("date").equalTo(date2);
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
                            Intent intent = new Intent(context, Tradestate.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            context.startActivity(intent);



                        }else{
                            Intent intent = new Intent(context, Tradestate.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            context.startActivity(intent);
                            Toast.makeText(context,"판매자측 동의 안됨.",Toast.LENGTH_SHORT).show();
                        }


                    }
                });

                dlg.show();


            }
        });



        holder.btn2.setOnClickListener(new View.OnClickListener() {
            //상품관리 삭제 버튼 클릭시
            @Override

            public void onClick(View v) {

                AlertDialog.Builder dlg = new AlertDialog.Builder(holder.itemView.getContext());
                dlg.setTitle("알림");
                dlg.setMessage("거래를 취소 하겠습니까?");
                dlg.setIcon(R.mipmap.ic_launcher);
                dlg.setNegativeButton("취소", null);

                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        database=FirebaseDatabase.getInstance();
                        user = FirebaseAuth.getInstance().getCurrentUser();
                        final String email = user.getEmail();


                        databaseReference = FirebaseDatabase.getInstance().getReference().child("Trade state");
                        Query mQuery=databaseReference.orderByChild("name").equalTo(name2);
                        if (email.equals(buyerid2)){
                            mQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for(DataSnapshot ds: snapshot.getChildren()){

                                        databaseReference = FirebaseDatabase.getInstance().getReference().child("Trade state");
                                        Query mmquery=databaseReference.orderByChild("date").equalTo(date2);
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
                            });}

                        Conditioninformation product=new Conditioninformation();
                        product.useremail=useremail2;
                        product.category=category2;
                        product.date=date2;
                        product.money= money2;
                        product.state=state2;
                        product.name=name2;
                        product.imv=image2;
                        product.text=text2;

                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        database.getReference().child("Product2").push().setValue(product);

                        Intent intent=new Intent(context,Tradestate.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(intent);

                    }
                });

                dlg.show();

            }
        });

        holder.btn3.setOnClickListener(new View.OnClickListener() {
            //상품관리 삭제 버튼 클릭시
            @Override

            public void onClick(View v) {

                AlertDialog.Builder dlg = new AlertDialog.Builder(holder.itemView.getContext());
                dlg.setTitle("알림");
                dlg.setMessage("쪽지를 보내겠습니까?");
                dlg.setIcon(R.mipmap.ic_launcher);
                dlg.setNegativeButton("취소", null);

                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        database=FirebaseDatabase.getInstance();
                        user = FirebaseAuth.getInstance().getCurrentUser();
                        final String email = user.getEmail();


                        databaseReference = FirebaseDatabase.getInstance().getReference().child("Trade state");

                        Intent intent=new Intent(context,Messagesend.class);

                        intent.putExtra("tv1",tv2);
                        intent.putExtra("tv2",useremail2);

                        Conditioninformation product=new Conditioninformation();
                        product.useremail=useremail2;
                        product.date=date2;
                        product.text=text2;


                        context.startActivity(intent);

                    }
                });

                dlg.show();

            }
        });


        holder.itemView.setOnClickListener(new View.OnClickListener() { //각 아이템이 클릭되었을때
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(context, Productlook2.class);
                intent.putExtra("name",name2);
                intent.putExtra("useremail",useremail2);
                intent.putExtra("imv", image2);
                intent.putExtra("money",money2);
                intent.putExtra("text",text2);
                intent.putExtra("state",state2);
                intent.putExtra("date",date2);
                intent.putExtra("category",category2);

                context.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return (arrayList !=null ? arrayList.size():0);

    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        ImageView condition_imv;
        TextView condition_postname;
        TextView condition_money;

        TextView condition_useremail;
        String uid;
        Button btn1,btn2,btn3;




        public CustomViewHolder(@NonNull  View itemView) {
            super(itemView);

            this.condition_imv=itemView.findViewById(R.id.conditionlistitem_imv1);
            this.condition_postname=itemView.findViewById(R.id.conditionlistitem_tv3);
            this.condition_money=itemView.findViewById(R.id.conditionlistitem_tv5);
            this.btn3=itemView.findViewById(R.id.conditionlistitem_btn3);
            this.condition_useremail =itemView.findViewById(R.id.conditionlistitem_tv4);
            this.btn1=itemView.findViewById(R.id.conditionlistitem_btn1);
            this.btn2=itemView.findViewById(R.id.conditionlistitem_btn2);

        }

        private String getTime(){

            long mNow;
            Date mDate;
            SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

            mNow = System.currentTimeMillis();
            mDate = new Date(mNow);
            return mFormat.format(mDate);
        }

    }
}
