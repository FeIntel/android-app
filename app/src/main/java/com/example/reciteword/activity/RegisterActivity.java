package com.example.reciteword.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.reciteword.R;
import com.example.reciteword.database.DatabaseHelper;

public class RegisterActivity extends AppCompatActivity {

    private EditText etNewUsername, etNewPassword, etConfirmPassword;
    private TextView tvError, tvToLogin;
    private Button btnRegister;
    private DatabaseHelper databaseHelper=new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etNewUsername = findViewById(R.id.etNewUsername);
        etNewPassword = findViewById(R.id.etNewPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        tvError = findViewById(R.id.tvError);
        btnRegister = findViewById(R.id.btnRegister);
        tvToLogin = findViewById(R.id.tvToLogin);

        btnRegister.setOnClickListener(v -> {
            String username = etNewUsername.getText().toString();
            String password = etNewPassword.getText().toString();
            String confirmPassword = etConfirmPassword.getText().toString();

            if (validateInput(username, password, confirmPassword)) {
                //新增用户到数据库
                databaseHelper.addUser(username, password);
                Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, LoginActivity.class));
            }
        });

        tvToLogin.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
        });
    }

    private boolean validateInput(String username, String password, String confirmPassword) {
        if (username.length() < 5 || password.length() < 5) {
            tvError.setText("用户名和密码必须至少5个字符");
            return false;
        }
        if (!password.equals(confirmPassword)) {
            tvError.setText("密码不匹配");
            return false;
        }
        return true;
    }
}