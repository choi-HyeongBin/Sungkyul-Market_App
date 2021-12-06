package com.example.firebase_application;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
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
import com.google.firebase.database.FirebaseDatabase;

import com.orhanobut.dialogplus.ViewHolder;
import com.orhanobut.dialogplus.DialogPlus;


import java.util.HashMap;
import java.util.Map;

public class Productcontroladapter extends FirebaseRecyclerAdapter<Productinformation,Productcontroladapter.CustomViewHolder> {


    public Productcontroladapter(@NonNull FirebaseRecyclerOptions<Productinformation> options) { super(options); }

    @Override
    protected void onBindViewHolder(@NonNull final CustomViewHolder holder,final int position, @NonNull final Productinformation model) {

        holder.tv_postname.setText(model.getName());
        holder.tv_postid.setText(model.getUseremail());
        holder.tv_money.setText(String.valueOf(model.getMoney()));

        //이미지 테두리 원형으로 만들기
        GradientDrawable drawable=(GradientDrawable)holder.iv_image.getContext().getDrawable(R.drawable.productcontrol_image);

        holder.iv_image.setBackground(drawable);
        holder.iv_image.setClipToOutline(true);

        Glide.with(holder.iv_image)
                .load(model.getImv())
                .into(holder.iv_image);

        final String name= (String) holder.tv_postname.getText();
        final String money= (String) holder.tv_money.getText();
        final String text=model.getText();

        holder.btnUpdate.setOnClickListener(new View.OnClickListener() { //상품수정 클릭
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus=DialogPlus.newDialog(holder.iv_image.getContext())
                        .setContentHolder(new ViewHolder(R.layout.productupdate))
                        .setExpanded(true, WindowManager.LayoutParams.MATCH_PARENT) //다이어로그 화면 꽉채우기
                        .create();

                View myview=dialogPlus.getHolderView();

                final EditText etPostName= myview.findViewById(R.id.productupdate_etPostName);
                final EditText etContent= myview.findViewById(R.id.productupdate_etContent);
                final EditText etPrice= myview.findViewById(R.id.productupdate_etPrice);
                final ImageView iv=myview.findViewById(R.id.productupdate_imv1);

                final Spinner spinner=myview.findViewById(R.id.productupdate_sp);
                final CheckBox checkBox=myview.findViewById(R.id.productupdate_checkbox);


                //이미지 테두리 원형으로 만들기
                GradientDrawable drawable=(GradientDrawable)iv.getContext().getDrawable(R.drawable.productcontrol_image);

                iv.setBackground(drawable);
                iv.setClipToOutline(true);

                final RadioGroup rdoquality=myview.findViewById(R.id.productupdate_rdogroup);
                final RadioButton rdbtn1=myview.findViewById(R.id.productupdate_rdbtn1);
                final RadioButton rdbtn2=myview.findViewById(R.id.productupdate_rdbtn2);
                final RadioButton rdbtn3=myview.findViewById(R.id.productupdate_rdbtn3);
                final RadioButton rdbtn4=myview.findViewById(R.id.productupdate_rdbtn4);

                final ImageView imv=myview.findViewById(R.id.productupdate_imv1);

                final Button btnComplete=myview.findViewById(R.id.productupdate_btnComplete);
                final Button btnCancle=myview.findViewById(R.id.productupdate_btnCancle);

                String quality=model.getState().toString().trim();// 선택된 물품의 라디오값(state) 가져오기
                String model_spinner=model.getCategory().toString().trim(); //선택된 물품의 카테고리값(category) 가져오기

                getCategory(spinner,model_spinner); //수정클릭시 나오는 spinner 지정
                etPostName.setText(name); //수정클릭시 나오는 게시물 제목 지정
                etContent.setText(text); //수정클릭시 나오는 내용 지정

                //money가 무료나눔일 경우 체크박스 체크
                if(model.getMoney().equalsIgnoreCase("무료나눔")){
                    checkBox.setChecked(true);
                    etPrice.setVisibility(View.GONE);
                }
                else{
                    etPrice.setText(money);//수정클릭시 나오는 가격 지정
                }

                //체크박스 클릭시
                checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (checkBox.isChecked() == true) {
                            etPrice.setVisibility(View.GONE);
                        }else {
                            etPrice.setVisibility(View.VISIBLE);

                        }
                    }
                });

                //수정클릭시 나오는 라디오 버튼 지정
                if (rdbtn1.getText().toString().trim().equals(quality)) {
                    rdbtn1.toggle();
                }
                else if(rdbtn2.getText().toString().trim().equals(quality)){
                    rdbtn2.toggle();
                }
                else if(rdbtn3.getText().equals(quality)){
                    rdbtn3.toggle();
                }
                else if(rdbtn4.getText().equals(quality)){
                    rdbtn4.toggle();
                }

                //수정클릭시 나오는 이미지 지정
                Glide.with(holder.itemView)
                        .load(model.getImv())
                        .into(imv);

                dialogPlus.show();

                btnCancle.setOnClickListener(new View.OnClickListener() { //상품관리의 수정 버튼 클릭 후 취소 버튼 클릭시
                    @Override
                    public void onClick(View v) {
                        dialogPlus.dismiss();
                    }
                });

                btnComplete.setOnClickListener(new View.OnClickListener() { //상품관리의 수정 버튼 클릭 후 수정완료 버튼 클릭시
                    @Override
                    public void onClick(View v) {

                        final String name = etPostName.getText().toString().trim(); //게시글
                        final String text = etContent.getText().toString().trim(); //설명
                        String money ="money";//가격
                        String state =null; //상태

                        //만약 체크박스가 선택되어있을시 DB에 money변수에 저장할 값 저장
                        if (checkBox.isChecked() == true) {
                            money = checkBox.getText().toString();
                        }else{
                            money =etPrice.getText().toString().trim();
                        }

                        //현재 선택되어있는 spinner 값 가져오기
                        String category=spinner.getSelectedItem().toString().trim();

                        //현재 클릭되어있는 라디오 텍스트 값 가져오기
                        int id = rdoquality.getCheckedRadioButtonId();
                        if (id == R.id.productupdate_rdbtn1) {
                            state = rdbtn1.getText().toString().trim();
                        } else if (id == R.id.productupdate_rdbtn2) {
                            state = rdbtn2.getText().toString().trim();
                        } else if (id == R.id.productupdate_rdbtn3) {
                            state = rdbtn3.getText().toString().trim();
                        } else if (id == R.id.productupdate_rdbtn4) {
                            state = rdbtn4.getText().toString().trim();
                        }

                        if(name.length()<=0||text.length()<=0||money.length()<=0){
                            Toast.makeText(btnComplete.getContext(), "값을 모두 입력해주세요", Toast.LENGTH_SHORT).show();
                        }else{
                            Map<String,Object> map=new HashMap<>();

                            map.put("name",name);
                            map.put("text",text);
                            map.put("money",money);
                            map.put("state",state);
                            map.put("category",category);

                            FirebaseDatabase.getInstance().getReference().child("Product2")
                                    .child(getRef(position).getKey()).updateChildren(map)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(btnComplete.getContext(), "수정했습니다.", Toast.LENGTH_SHORT).show();
                                            dialogPlus.dismiss();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            dialogPlus.dismiss();
                                        }
                                    });
                        }
                    }
                });


            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() { //상품관리 삭제 버튼 클릭시

            @Override
            public void onClick(View v) { // 상품관리에서 게시물 삭제 클릭

                final AlertDialog.Builder dlg = new AlertDialog.Builder(holder.itemView.getContext());
                dlg.setTitle("알림");
                dlg.setMessage("삭제하시겠습니까?");
                dlg.setIcon(R.mipmap.ic_launcher);
                dlg.setNegativeButton("취소", null);
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //1. 상품 게시물에 있는 삭제 버튼 클릭시 상품관리의 게시물이 삭제되는 코드
                        FirebaseDatabase.getInstance().getReference().child("Product2")
                                .child(getRef(position).getKey()).removeValue();
                        Toast.makeText(dlg.getContext(), "삭제했습니다.", Toast.LENGTH_SHORT).show();


                        //액티비티 재실행해야 정확하게 삭제됨
                        Intent intent = ((Activity)holder.btnDelete.getContext()).getIntent();
                        ((Activity)holder.btnDelete.getContext()).finish(); //현재 액티비티 종료 실시
                        ((Activity)holder.btnDelete.getContext()).overridePendingTransition(0, 0); //효과 없애기
                        ((Activity)holder.btnDelete.getContext()).startActivity(intent); //현재 액티비티 재실행 실시
                        ((Activity)holder.btnDelete.getContext()).overridePendingTransition(0, 0); //효과 없애기
                    }
                });
                dlg.show();
            }
        });
    }

    //물품등록할때 선택한 카테고리 값을 선택해주는 메소드
    private void getCategory(Spinner spinner,String model_spinner){
        for(int i=0;i<spinner.getCount();i++)
        {
            if(spinner.getItemAtPosition(i).toString().equalsIgnoreCase(model_spinner)){
                spinner.setSelection(i);
            }
        }
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.productcontrol_list_item,parent,false);
        return new CustomViewHolder(view);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder { //productcontrol_list_item과 연결

        ImageView iv_image;
        TextView tv_postname;
        TextView tv_postid;
        TextView tv_money;
        Button btnDelete;
        Button btnUpdate;

        public CustomViewHolder(@NonNull View itemView){
            super(itemView);
            iv_image=itemView.findViewById(R.id.iv_image);
            tv_postname=itemView.findViewById(R.id.tv_name);
            tv_postid=itemView.findViewById(R.id.tv_id);
            tv_money=itemView.findViewById(R.id.tv_money);
            btnDelete=itemView.findViewById(R.id.btnDelete);
            btnUpdate=itemView.findViewById(R.id.btnUpdate);
        }
    }
}
