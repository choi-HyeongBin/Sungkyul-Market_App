package com.example.firebase_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Repass extends AppCompatActivity {
    Button btn1;
    EditText edt1;
    String useremail;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.repass);

        btn1=findViewById(R.id.repass_btn1);
        edt1=findViewById(R.id.repass_edt1);




        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                FirebaseAuth auth = FirebaseAuth.getInstance();
                if (user != null) {

                    String email = user.getEmail();



                   useremail=email;
                }

                String inputedemail=edt1.getText().toString();

                if(useremail.equals(inputedemail)){
                    auth.sendPasswordResetEmail(useremail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast toast = Toast.makeText(getApplicationContext(), "재설정 이메일을 전송하였습니다.", Toast.LENGTH_LONG);


                                toast.setGravity(Gravity.BOTTOM|Gravity.CENTER, 20, 200);


                                toast.show();

                                Intent intent=new Intent(getApplicationContext(), Myinform.class);
                                startActivity(intent);
                                finish();
                            }



                        }
                    });
                }
                else {               Toast toast = Toast.makeText(getApplicationContext(), "인증하신 이메일과 다른 이메일입니다.", Toast.LENGTH_LONG);


                    toast.setGravity(Gravity.BOTTOM|Gravity.CENTER, 30, 150);


                    toast.show();}








            }
        });
    }

}
