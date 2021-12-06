package com.example.firebase_application;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Tradeinformationadapter2 extends RecyclerView.Adapter<Tradeinformationadapter2.CustomViewHolder> {

    private ArrayList<Conditioninformation> arrayList;
    private Context context;


    String savemyemail;

    private RecyclerView.Adapter adapter;



    public Tradeinformationadapter2(ArrayList<Conditioninformation> arrayList, Context context){
        this.arrayList=arrayList;
        this.context=context;
    }



    @NonNull
    @Override
    public Tradeinformationadapter2.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.sell_list_item2,parent,false);
        Tradeinformationadapter2.CustomViewHolder holder= new Tradeinformationadapter2.CustomViewHolder(view);

        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull final CustomViewHolder holder, final int position) {
        Glide.with(holder.itemView.getContext())
                .load(arrayList.get(position).getImv())
                .into(holder.condition2_imv);
        holder.condition2_postname.setText(arrayList.get(position).getName());
        holder.condition2_money.setText(arrayList.get(position).getMoney());
        holder.condition2_date.setText(arrayList.get(position).getDate());
        holder.condition2_useremail.setText(arrayList.get(position).getBuyeremail());

        FirebaseUser useremail = FirebaseAuth.getInstance().getCurrentUser();

        savemyemail = useremail.getEmail();





    }

    @Override
    public int getItemCount() {
        return (arrayList !=null ? arrayList.size():0);

    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        ImageView condition2_imv;
        TextView condition2_postname;
        TextView condition2_money;
        TextView condition2_date;
        TextView condition2_useremail;

        public CustomViewHolder(@NonNull  View itemView) {
            super(itemView);

            this.condition2_imv=itemView.findViewById(R.id.selllistitem2_imv1);
            this.condition2_postname=itemView.findViewById(R.id.selllistitem2_tv1);
            this.condition2_money=itemView.findViewById(R.id.selllistitem2_tv4);
            this.condition2_date=itemView.findViewById(R.id.selllistitem2_tv3);
            this.condition2_useremail =itemView.findViewById(R.id.selllistitem2_tv2);


        }

    }
}
