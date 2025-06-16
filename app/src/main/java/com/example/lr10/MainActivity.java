package com.example.lr10;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {

    private TextInputLayout emailInputLayout;
    private TextInputEditText emailEditText;
    private TextInputLayout passwordInputLayout;
    private TextInputEditText passwordEditText;
    private Button submitButton;
    private TextView resultTextView;

    private boolean checkEmail = false;
    private boolean checkPassword = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setupEmailValidation();
        setupPasswordValidation();
        setupSubmitButton();
    }

    private void initViews() {
        emailInputLayout = findViewById(R.id.emailInputLayout);
        emailEditText = findViewById(R.id.emailEditText);
        passwordInputLayout = findViewById(R.id.passwordInputLayout);
        passwordEditText = findViewById(R.id.passwordEditText);
        submitButton = findViewById(R.id.submitButton);
        resultTextView = findViewById(R.id.resultTextView);
    }

    private void setupEmailValidation() {
        emailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String email = s.toString().trim();
                if (email.isEmpty()) {
                    emailInputLayout.setErrorEnabled(false);
                    checkEmail = false;
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    emailInputLayout.setError("Некорректный email");
                    checkEmail = false;
                } else {
                    emailInputLayout.setError(null);
                    checkEmail = true;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 20) {
                    emailInputLayout.setError("Email слишком длинный");
                    checkEmail = false;
                }
            }
        });

        emailEditText.setOnEditorActionListener((v, actionId, event) -> {
            validateFields();
            return false;
        });
    }

    private void setupPasswordValidation() {
        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String password = s.toString().trim();
                if (password.isEmpty()) {
                    passwordInputLayout.setErrorEnabled(false);
                    checkPassword = false;
                } else if (password.length() < 6) {
                    passwordInputLayout.setError("Пароль слишком короткий");
                    checkPassword = false;
                } else {
                    passwordInputLayout.setError(null);
                    checkPassword = true;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        passwordEditText.setOnEditorActionListener((v, actionId, event) -> {
            validateFields();
            return false;
        });
    }

    private void setupSubmitButton() {
        submitButton.setOnClickListener(v -> validateFields());
    }

    private void validateFields() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (email.isEmpty()) {
            emailInputLayout.setError("Введите email");
            checkEmail = false;
        }

        if (password.isEmpty()) {
            passwordInputLayout.setError("Введите пароль");
            checkPassword = false;
        }

        if (checkEmail && checkPassword) {
            resultTextView.setText("Валидация завершена");
        } else {
            resultTextView.setText("Исправьте ошибки в форме");
        }
    }
}
