package com.kylediaz.fbu.dipole_news.activities.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

        etLogInUsername = findViewById(R.id.etLogInUsername);
        etLogInPassword = findViewById(R.id.etLogInPassword);

        btnLogIn = findViewById(R.id.btnLogIn);

        tvSwitchToSignUp = findViewById(R.id.tvSwitchToSignUp);

        for (EditText et : new EditText[] {etLogInUsername, etLogInPassword}) {
            et.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    updateButtonState();
                }
                @Override
                public void afterTextChanged(Editable s) { }
            });
        }

        btnLogIn.setOnClickListener(arg0 -> attemptLogIn());

        tvSwitchToSignUp.setOnClickListener(arg0 -> {
            Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(i);
            finish();
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

    private void updateButtonState() {
        btnLogIn.setEnabled(!aFieldIsBlank());
    }
    private boolean aFieldIsBlank() {
        return etLogInUsername.getText().length() == 0 || etLogInPassword.getText().length() == 0;
    }
}