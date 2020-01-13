package com.example.twerpapps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();


        final EditText email = findViewById(R.id.email);
        final EditText nama = findViewById(R.id.name);
        final EditText pass = findViewById(R.id.password);
        TextView btnSignIn = findViewById(R.id.txtSignin);
        ImageButton btnSignUp = findViewById(R.id.signup);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailUser = email.getText().toString().trim();
                String name = nama.getText().toString().trim();
                String pswd = pass.getText().toString();

                if (emailUser.isEmpty()) {
                    email.setError("Email tidak boleh kosong");
                } else if (!Patterns.EMAIL_ADDRESS.matcher(emailUser).matches()) {
                    email.setError("Email tidak valid");
                } else if (pswd.isEmpty()) {
                    pass.setError("Password tidak boleh kosong");
                } else if (pswd.length() < 8) {
                    Toast.makeText(getApplicationContext(), "Password minimal 8 karakter", Toast.LENGTH_LONG).show();
                } else {
                    mAuth.createUserWithEmailAndPassword(emailUser, pswd).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            jika gagal register
                            if (!task.isSuccessful()) {
                                Toast.makeText(RegisterActivity.this, "Register gagal karena " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            } else {
//                                jika sukses
                                startActivity(new Intent(RegisterActivity.this, HomeActivity.class));
                            }
                        }
                    });
                }
            }
        });
    }
}
