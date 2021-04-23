package com.bae.dialogflowbot;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bae.dialogflowbot.models.TestData;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Test extends AppCompatActivity {
    private DatabaseReference mDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Test");
    }

    public void myClick(View view) {

        String itemId = mDatabaseRef.push().getKey();
        TestData T = new TestData("Working");


        mDatabaseRef.child(itemId).setValue(T);

        Toast.makeText(this,"Hello",Toast.LENGTH_SHORT).show();
    }
}