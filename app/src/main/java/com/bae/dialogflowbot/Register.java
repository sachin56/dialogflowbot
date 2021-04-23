package com.bae.dialogflowbot;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity implements View.OnClickListener {

    private TextView Banner;
    private EditText Name, Phone, Email, Password;
    private Button Register;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Banner = (TextView) findViewById(R.id.banner);
        Banner.setOnClickListener(this);

        Register = (Button) findViewById(R.id.register);
        Register.setOnClickListener(this);

        progressBar = (ProgressBar)findViewById(R.id.progressBar);


        Name = (EditText) findViewById(R.id.name);
        Phone = (EditText) findViewById(R.id.phone);
        Email = (EditText) findViewById(R.id.email);
        Password = (EditText) findViewById(R.id.password);

        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.banner:
                startActivity(new Intent(this, Register.class));
                break;

            case R.id.register:
                UserRegister();
                break;
        }

    }

    private void UserRegister() {

        final String name = Name.getText().toString().trim();
        final String phone = Phone.getText().toString().trim();
        final String email = Email.getText().toString().trim();
        String password = Password.getText().toString().trim();

        if(name.isEmpty()){
            Name.setError("Name is required");
            Name.requestFocus();
            return;
        }

        if(phone.isEmpty()){
            Phone.setError("Phone Number is required");
            Phone.requestFocus();
            return;
        }

        if(email.isEmpty()){
            Email.setError("Email is required");
            Email.requestFocus();
            return;

        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Email.setError("Provide proper Email Address");
            Email.requestFocus();
            return;

        }

        if(password.isEmpty()){
            Password.setError("Password is required");
            Password.requestFocus();
            return;

        }
        if(password.length()<6){
            Password.setError("Minimum Password Length is 6");
            Password.requestFocus();
            return;
        }

        progressBar.setVisibility(View.INVISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    User user = new User(name,phone,email);
                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){
                                Toast.makeText(Register.this,"Registration successful",Toast.LENGTH_LONG).show();
                                  progressBar.setVisibility(View.VISIBLE);
                                  startActivity(new Intent(Register.this,Login.class));

                            }else{
                                Toast.makeText(Register.this,"Registration Failed",Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                            }

                        }
                    });
                }else{
                    Toast.makeText(Register.this,"Failed to Register",Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }
}