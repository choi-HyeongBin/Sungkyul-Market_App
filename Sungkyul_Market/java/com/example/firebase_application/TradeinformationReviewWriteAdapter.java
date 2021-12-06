package com.example.firebase_application;

import android.app.Activity;
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
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

public class TradeinformationReviewWriteAdapter extends FirebaseRecyclerAdapter<Conditioninformation, TradeinformationReviewWriteAdapter.CustomViewHolder> {

    public TradeinformationReviewWriteAdapter(@NonNull FirebaseRecyclerOptions<Conditioninformation> options) { super(options); }

    @Override
    protected void onBindViewHolder(@NonNull final CustomViewHolder holder,final int position, @NonNull final Conditioninformation model) {

        Glide.with(holder.seller_list_item2_imv.getContext())
                .load(model.getImv())
                .into(holder.seller_list_item2_imv);

        holder.seller_list_item2_postname.setText(model.getName());
        holder.seller_list_item2_useremail.setText(model.getUseremail());
        holder.seller_list_item2_money.setText(model.getMoney());
        holder.seller_list_item2_date.setText(model.getDate());

        holder.seller_list_item2_btn.setOnClickListener(new View.OnClickListener() { //평가 보기 버튼 클릭시
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus=DialogPlus.newDialog(holder.itemView.getContext())
                        .setContentHolder(new ViewHolder(R.layout.seller))
                        .setExpanded(true, WindowManager.LayoutParams.MATCH_PARENT) //다이어로그 화면 꽉채우기
                        .create();

                final View myview=dialogPlus.getHolderView();

                final RatingBar seller_rb1=myview.findViewById(R.id.seller_rb1);
                final EditText seller_edt1=myview.findViewById(R.id.seller_edt1);
                final TextView seller_tv1=myview.findViewById(R.id.seller_tv1);
                seller_tv1.setText(holder.seller_list_item2_postname.getText());
                final Button seller_btn1=myview.findViewById(R.id.seller_btn1);

                dialogPlus.show();

                // 평가 버튼 클릭시 해당 타임스탬프 값 읽어오기 -> 이런식으로도 사용가능
                /*String timekey;
                  timekey =FirebaseDatabase.getInstance().getReference().child("Trade information")
                        .child(getRef(position).getKey()).getKey();*/

                //Toast.makeText(myview.getContext(), timekey, Toast.LENGTH_SHORT).show();

                //평가 완료버튼 클릭시

                seller_btn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String selleremail= (String) holder.seller_list_item2_useremail.getText().toString().trim(); //판매자 이메일
                        String review=seller_edt1.getText().toString().trim(); //리뷰 내용
                        String postname=seller_tv1.getText().toString().trim(); // 게시글
                        String rating=Float.toString(seller_rb1.getRating()); // 별점
                        //현재 나의 이메일 가져오기(구매자)
                        String buyeremail;
                        FirebaseUser useremail = FirebaseAuth.getInstance().getCurrentUser();
                        buyeremail = useremail.getEmail();

                        Map<String,Object> map=new HashMap<>();
                        map.put("postname",postname);
                        map.put("buyeremail",buyeremail);
                        map.put("selleremail",selleremail);
                        map.put("review",review);
                        map.put("rating",rating);

                        //평가 완료 시 Review Table에 값 저장
                        FirebaseDatabase.getInstance().getReference().child("Review").push().setValue(map);

                        // 리뷰작성을 하면 Buy Product 있는 값 삭제
                        FirebaseDatabase.getInstance().getReference().child("Buy Product Review")
                                .child(getRef(position).getKey()).removeValue();

                        Toast.makeText(myview.getContext(), "리뷰를 작성했습니다.", Toast.LENGTH_SHORT).show();

                        //액티비티 재실행해야 정확하게 삭제됨
                        Intent intent = ((Activity)seller_btn1.getContext()).getIntent();
                        ((Activity)seller_btn1.getContext()).finish(); //현재 액티비티 종료 실시
                        ((Activity)seller_btn1.getContext()).overridePendingTransition(0, 0); //효과 없애기
                        ((Activity)seller_btn1.getContext()).startActivity(intent); //현재 액티비티 재실행 실시
                        ((Activity)seller_btn1.getContext()).overridePendingTransition(0, 0); //효과 없애기

                        dialogPlus.dismiss();
                    }
                });

            }
        });


    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.sell_list_item,parent,false);
        return new CustomViewHolder(view);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        ImageView seller_list_item2_imv;
        TextView seller_list_item2_postname;
        TextView seller_list_item2_money;
        TextView seller_list_item2_date;
        TextView seller_list_item2_useremail;
        Button seller_list_item2_btn;

        public CustomViewHolder(@NonNull  View itemView) {
            super(itemView);
            seller_list_item2_imv=itemView.findViewById(R.id.selllistitem_imv1);
            seller_list_item2_postname=itemView.findViewById(R.id.selllistitem_tv1);
            seller_list_item2_money=itemView.findViewById(R.id.selllistitem_tv4);
            seller_list_item2_date=itemView.findViewById(R.id.selllistitem_tv3);
            seller_list_item2_useremail =itemView.findViewById(R.id.selllistitem_tv2);
            seller_list_item2_btn=itemView.findViewById(R.id.sell_list_item_btn1);
        }

    }
}
