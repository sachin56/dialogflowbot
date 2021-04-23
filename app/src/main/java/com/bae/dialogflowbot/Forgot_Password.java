package com.bae.dialogflowbot;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Forgot_Password extends AppCompatActivity implements View.OnClickListener {

    private TextView Banner;
    private EditText Email;
    private Button Reset;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot__password);

        Banner = (TextView) findViewById(R.id.banner);
        Banner.setOnClickListener(this);

        Email = (EditText) findViewById(R.id.email);

        Reset = (Button) findViewById(R.id.reset);
        Reset.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();



    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.banner:
                startActivity(new Intent(this, Forgot_Password.class));
                break;

            case R.id.reset:
                PasswordReset();
        }

    }

    private void PasswordReset() {
        String email = Email.getText().toString().trim();

        if(email.isEmpty()){
            Email.setError("Email Address is Required");
            Email.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Email.setError("Provide Proper Email Address");
            Email.requestFocus();
            return;
        }

        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Forgot_Password.this,"Check Your Email to Reset your Password",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(Forgot_Password.this,"Failed to reset Your Password",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}