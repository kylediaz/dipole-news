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

public class LoginActivity extends AppCompatActivity {

    private EditText etLogInUsername;
    private EditText etLogInPassword;

    private Button btnLogIn;

    private TextView tvSwitchToSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etLogInUsername = findViewById(R.id.etSignUpUsername);
        etLogInPassword = findViewById(R.id.etSignUpPassword);
        btnLogIn = findViewById(R.id.btnLogIn);
        tvSwitchToSignUp = findViewById(R.id.tvSwitchToSignUp);

        btnLogIn.setOnClickListener(arg0 -> attemptLogIn());

        tvSwitchToSignUp.setOnClickListener(arg0 -> {
            Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(i);
        });
    }

    private void attemptLogIn() {
        ParseUser.logInInBackground(etLogInUsername.getText().toString(), etLogInPassword.getText().toString(),
                (user, e) -> {
                    if (user != null) {
                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(i);
                    } else {
                        Toast.makeText(this, "Error logging in: " + e.getMessage(), Toast.LENGTH_LONG)
                                .show();
                    }
                }
        );
    }
}