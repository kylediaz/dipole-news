package com.kylediaz.fbu.dipole_news.activities.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kylediaz.fbu.dipole_news.R;
import com.kylediaz.fbu.dipole_news.activities.MainActivity;
import com.parse.ParseUser;

import org.json.JSONArray;

public class SignUpActivity extends AppCompatActivity {

    private EditText etSignUpUsername;
    private EditText etSignUpPassword;
    private EditText etSignUpPasswordConfirmation;

    private Button btnSignUp;

    private TextView tvSwitchToLogIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etSignUpUsername = findViewById(R.id.etSignUpUsername);
        etSignUpPassword = findViewById(R.id.etSignUpPassword);
        etSignUpPasswordConfirmation = findViewById(R.id.etSignUpPasswordConfirmation);

        btnSignUp = findViewById(R.id.btnSignUp);

        tvSwitchToLogIn = findViewById(R.id.tvSwitchToLogIn);

        btnSignUp.setOnClickListener(arg0 -> {
            if (etSignUpUsername.getText().toString().isEmpty()) {
                Toast.makeText(this, "Please enter a username", Toast.LENGTH_LONG)
                        .show();
            } else if (etSignUpPassword.getText().toString().isEmpty()) {
                Toast.makeText(this, "Please enter a password", Toast.LENGTH_LONG)
                        .show();
            } else if (!etSignUpPassword.getText().toString()
                    .equals(etSignUpPasswordConfirmation.getText().toString())) {
                Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_LONG)
                        .show();
            } else {
                signUp(etSignUpUsername.getText().toString(), etSignUpPassword.getText().toString());
            }
        });
    }

    private void signUp(String username, String password) {
        ParseUser user = new ParseUser();
        user.setUsername(username);
        user.setPassword(password);
        user.put("bookmarks", new JSONArray());
        user.signUpInBackground(e -> {
            if (e == null) {
                Intent i = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(i);
            } else {
                Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG)
                        .show();
            }
        });
    }
}