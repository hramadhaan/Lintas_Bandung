package com.lintasbandung.lintasbandungapps.awalan;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.maps.errors.ApiException;
import com.lintasbandung.lintasbandungapps.R;
import com.lintasbandung.lintasbandungapps.dashboard.DashboardActivity;
import com.lintasbandung.lintasbandungapps.data.AppState;
import com.lintasbandung.lintasbandungapps.models.DataUser;
import com.lintasbandung.lintasbandungapps.models.Token;
import com.lintasbandung.lintasbandungapps.network.ApiService;
import com.lintasbandung.lintasbandungapps.utils.ApiUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 1;
    private GoogleSignInClient googleSignInClient;
    private Button button, signIn;
    private AppState appState;
    private ApiService apiService;
    private EditText email, password;
    private RelativeLayout login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        apiService = ApiUtils.getApiSerives();
        appState = AppState.getInstance();

        login = findViewById(R.id.login);

        button = findViewById(R.id.login_button);
        email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });

        signIn = findViewById(R.id.login_signIn);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

    }

    private void signIn() {

        signIn.setEnabled(false);

        String string_email = email.getText().toString();
        String string_password = password.getText().toString();

        if (string_email.matches("")) {
            Toast.makeText(LoginActivity.this, "Masukkan E-Mail Anda !", Toast.LENGTH_LONG).show();
            signIn.setEnabled(true);
        } else if (string_password.matches("")) {
            Toast.makeText(LoginActivity.this, "Masukkan Password Anda !", Toast.LENGTH_LONG).show();
            signIn.setEnabled(true);
        } else if (string_email.matches("") && string_password.matches("")) {
            Toast.makeText(LoginActivity.this, "Masukkan E-Mail dan Password Anda !", Toast.LENGTH_LONG).show();
            signIn.setEnabled(true);
        } else {
            closeKeyboard();
            Call<Token> tokenCall = apiService.getToken(string_email, string_password);
            tokenCall.enqueue(new Callback<Token>() {
                @Override
                public void onResponse(Call<Token> call, Response<Token> response) {
                    if (response.isSuccessful()) {
                        String hasilToken = response.body().getToken();
                        if (hasilToken.equals("false")) {
//                        Toast.makeText(LoginActivity.this, "E-Mail atau Password tidak ada", Toast.LENGTH_LONG).show();
                            Snackbar snackbar = Snackbar.make(login, "E-Mail atau Password belum terdaftar", Snackbar.LENGTH_LONG);
                            snackbar.show();
                            signIn.setEnabled(true);
                        } else {
                            appState.setToken(hasilToken);
                            appState.setIsLoggedIn(true);
                            getUser();
                            signOut();
                            signIn.setEnabled(true);
                        }
                    } else {

                    }
                }

                @Override
                public void onFailure(Call<Token> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void getUser() {
        apiService.getUser().enqueue(new Callback<DataUser>() {
            @Override
            public void onResponse(Call<DataUser> call, Response<DataUser> response) {
                if (response.isSuccessful()) {
                    appState.saveUser(response.body());
                    Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<DataUser> call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void signUp() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignUpResult(task);
        }
    }

    private void handleSignUpResult(Task<GoogleSignInAccount> task) {
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            if (account == null) {
                Toast.makeText(LoginActivity.this, "Account Tidak Ada", Toast.LENGTH_LONG).show();
            } else {
//                Toast.makeText(LoginActivity.this, account.getGivenName() + " " + account.getFamilyName(), Toast.LENGTH_LONG).show();
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        } catch (ApiException e) {
            e.printStackTrace();
            Log.e("handleSignUpResult", e.toString());
        }
    }

    private void signOut() {
        googleSignInClient.signOut().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.d("Keluar", "Revoke Account");
            }
        });
    }
}
