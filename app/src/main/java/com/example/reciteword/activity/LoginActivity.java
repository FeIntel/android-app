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
import com.example.reciteword.pojo.User;

/**
 * @author Fe
 * @version 1.0
 * @create 2024-11-30 12:58
 * @describe describe...
 */
public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private TextView tvError, tvToRegister;
    private Button btnLogin;
    private DatabaseHelper databaseHelper = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        tvError = findViewById(R.id.tvError);
        btnLogin = findViewById(R.id.btnLogin);
        tvToRegister = findViewById(R.id.tvToRegister);

        btnLogin.setOnClickListener(v -> {
            String username = etUsername.getText().toString();
            String password = etPassword.getText().toString();

            if (validateInput(username, password)) {
                User user = databaseHelper.checkUser(username);
                if (null != user && user.getPassword().equals(password)) {
                    Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, InterfaceActivity.class));
                } else {
                    tvError.setText("用户名或密码错误");
                }
            } else {
                tvError.setText("用户名或密码错误");
            }
        });

        tvToRegister.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });
    }

    private boolean validateInput(String username, String password) {
        if (null == username || null == password || username.length() < 5 || password.length() < 5) {
            tvError.setText("用户名和密码必须至少5个字符");
            return false;
        }
        return true;
    }

}