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
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private TextView Banner, Forgot_Password, Register;
    private EditText Email, Password;
    private Button Login;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Banner = (TextView) findViewById(R.id.banner);
        Banner.setOnClickListener(this);

        Forgot_Password = (TextView) findViewById(R.id.forgot_password);
        Forgot_Password.setOnClickListener(this);

        Register = (TextView) findViewById(R.id.register);
        Register.setOnClickListener(this);

        Email = (EditText) findViewById(R.id.email);

        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        Password = (EditText) findViewById(R.id.password);

        Login = (Button) findViewById(R.id.login);
        Login.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.banner:
                startActivity(new Intent(this, Login.class));
                break;

            case R.id.forgot_password:
                startActivity(new Intent(this, com.bae.dialogflowbot.Forgot_Password.class));
                break;

            case R.id.register:
                startActivity(new Intent(this, com.bae.dialogflowbot.Register.class));
                break;

            case R.id.login:
                UserLogin();

        }

    }

    private void UserLogin() {

        String email = Email.getText().toString().trim();
        String password = Password.getText().toString().trim();

        if(email.isEmpty()){
            Email.setError("Email is Required");
            Email.requestFocus();
            return;

        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Email.setError("Provide Proper Email Address");
            Email.requestFocus();
            return;

        }

        if(password.isEmpty()){
            Password.setError("Password is Required");
            Password.requestFocus();
            return;

        }

        if(password.length()<6){
            Password.setError("Minimum password length is 6");
            Password.requestFocus();
            return;
        }


        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    if(user.isEmailVerified()){
                        startActivity(new Intent(Login.this, MainActivity.class));


                    }else{
                        user.sendEmailVerification();
                        Toast.makeText(Login.this,"check your email to verify your Account", Toast.LENGTH_LONG).show();

                    }


                }else {
                    Toast.makeText(Login.this,"Failed to Login check your details",Toast.LENGTH_LONG).show();


                }
            }
        });


    }
}