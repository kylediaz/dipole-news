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

import org.json.JSONArray;

import java.util.ArrayList;

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

        TextWatcher buttonUpdater = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateButtonState();
            }
            @Override
            public void afterTextChanged(Editable s) { }
        };

        for (EditText et : new EditText[] {etSignUpUsername, etSignUpPassword, etSignUpPasswordConfirmation}) {
            et.addTextChangedListener(buttonUpdater);
        }

        btnSignUp.setOnClickListener(arg0 -> onSignUpButtonClick());

        tvSwitchToLogIn.setOnClickListener(arg0 -> onBackPressed());
    }

    private void onSignUpButtonClick() {
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
                finish();
            } else {
                Toast.makeText(this, "Error: " + e, Toast.LENGTH_LONG)
                        .show();
            }
        });
    }

    private void updateButtonState() {
        btnSignUp.setEnabled(!aFieldIsBlank());
    }
    private boolean aFieldIsBlank() {
        return etSignUpUsername.getText().length() == 0
                || etSignUpPassword.getText().length() == 0
                || etSignUpPasswordConfirmation.getText().length() == 0;
    }
}