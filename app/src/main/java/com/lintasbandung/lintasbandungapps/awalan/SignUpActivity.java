package com.lintasbandung.lintasbandungapps.awalan;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.lintasbandung.lintasbandungapps.R;
import com.lintasbandung.lintasbandungapps.data.AppState;
import com.lintasbandung.lintasbandungapps.models.Status;
import com.lintasbandung.lintasbandungapps.network.ApiService;
import com.lintasbandung.lintasbandungapps.utils.ApiUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    private GoogleSignInClient googleSignInClient;
    private EditText firstName, lastName, email, numbPhone, password;
    private Button submit;
    private ApiService apiService;
    private AppState appState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        apiService = ApiUtils.getApiSerives();
        appState = AppState.getInstance();

        firstName = findViewById(R.id.signUp_firstName);
        lastName = findViewById(R.id.signUp_lastName);
        email = findViewById(R.id.signUp_email);
        numbPhone = findViewById(R.id.signUp_phone);
        password = findViewById(R.id.signUp_password);
        submit = findViewById(R.id.signUp_button);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(SignUpActivity.this);
        if (acct != null) {
            firstName.setText(acct.getGivenName());
            lastName.setText(acct.getFamilyName());
            email.setText(acct.getEmail());

            firstName.setEnabled(false);
            lastName.setEnabled(false);
            email.setEnabled(false);

            numbPhone.requestFocus();
        } else {
            finish();
            Toast.makeText(SignUpActivity.this, "Tidak ada data akun Google", Toast.LENGTH_LONG).show();
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
    }

    private void signUp() {
        String string_firstName = firstName.getText().toString();
        String string_lastName = lastName.getText().toString();
        String string_email = email.getText().toString();
        String string_phone = numbPhone.getText().toString();
        String string_password = password.getText().toString();

        Call<Status> createUser = apiService.createUser(string_firstName, string_lastName, string_email, string_phone, string_password);
        createUser.enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equals("sukses")) {
                        finish();
                        signOut();
                    } else if (response.body().getStatus().equals("gagal")) {
                        Toast.makeText(SignUpActivity.this, "Gagal Mendaftarkan Akun", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(SignUpActivity.this, "Terjadi Error", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(SignUpActivity.this, response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {
                Toast.makeText(SignUpActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    private void signOut() {
        googleSignInClient.signOut().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
//                Toast.makeText(SignUpActivity.this, "Gagal Membuat Akun", Toast.LENGTH_LONG).show();
            }
        });
    }
}
