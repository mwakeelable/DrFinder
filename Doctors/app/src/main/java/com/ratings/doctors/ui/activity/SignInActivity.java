package com.ratings.doctors.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.ratings.doctors.R;

public class SignInActivity extends BaseActivity implements View.OnClickListener {
    private EditText emailEditText, passwordEditText;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button signInButton = (Button) findViewById(R.id.custom_signin_button);
        Button signUpButton = (Button) findViewById(R.id.custom_signup_button);
        LoginButton facebookLoginButton = (LoginButton) findViewById(R.id.facebook_login_button);
        facebookLoginButton.setVisibility(View.GONE);
        emailEditText = (EditText) findViewById(R.id.email_edittext);
        passwordEditText = (EditText) findViewById(R.id.password_edittext);
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code

                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });
        // Click listeners
        signInButton.setOnClickListener(this);
        signUpButton.setOnClickListener(this);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.login_activity;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void signIn() {
        if (!validateForm()) {
            return;
        }
        showProgressDialog();
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        hideProgressDialog();

                        if (task.isSuccessful()) {
                            onAuthSuccess(task.getResult().getUser());
                        } else {
                            generateDialogMessage(
                                    SignInActivity.this
                                    , getResources().getString(R.string.txt_error)
                                    , getResources().getString(R.string.signIn_failed_msg),
                                    getResources().getString(R.string.txt_positive_btn));
                        }
                    }
                });
    }

    private void signUp() {
        if (!validateForm()) {
            return;
        }
        showProgressDialog();
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        hideProgressDialog();
                        if (task.isSuccessful()) {
                            onAuthSuccess(task.getResult().getUser());
                        } else {
                            generateDialogMessage(
                                    SignInActivity.this
                                    , getResources().getString(R.string.txt_error)
                                    , getResources().getString(R.string.signUp_failed_msg),
                                    getResources().getString(R.string.txt_positive_btn));
                        }
                    }
                });
    }

    private boolean validateForm() {
        boolean result = true;
        String emailPattern = "([a-zA-Z0-9_-]+\\.)*[a-zA-Z0-9_-]+@[a-z]+(\\.[a-z]+)+";
        if (TextUtils.isEmpty(emailEditText.getText().toString()) ||
                !emailEditText.getEditableText().toString().trim().matches(emailPattern)) {
            emailEditText.setError(getResources().getString(R.string.txt_email_validation));
            result = false;
        } else {
            emailEditText.setError(null);
        }
        if (TextUtils.isEmpty(passwordEditText.getText().toString())) {
            passwordEditText.setError(getResources().getString(R.string.txt_password_validation));
            result = false;
        } else {
            passwordEditText.setError(null);
        }

        return result;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.custom_signin_button) {
            signIn();
        } else if (i == R.id.custom_signup_button) {
            signUp();
        }
    }
}
