package com.example.twerpapps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        final EditText textMail = findViewById(R.id.email);
        final EditText textPassword = findViewById(R.id.password);


        ImageButton btnSignIn = findViewById(R.id.signin);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userMail = textMail.getText().toString().trim();
                final String userPass = textPassword.getText().toString().trim();

                if (userMail.isEmpty()) {
                    textMail.setError("Email tidak boleh kosong");
                } else if (userPass.isEmpty()) {
                    textPassword.setError("Password kosong");
                } else {
                    mAuth.signInWithEmailAndPassword(userMail, userPass).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "Gagal login karena " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            } else {
                                Bundle bundle = new Bundle();
                                bundle.putString("email", userMail);
                                bundle.putString("pass", userPass);
                                startActivity(new Intent(LoginActivity.this, HomeActivity.class).putExtra("emailpass", bundle));
                                finish();
                            }
                        }
                    });
                }
            }
        });

        TextView btnSignUp = findViewById(R.id.txtSignup);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

    }
}
