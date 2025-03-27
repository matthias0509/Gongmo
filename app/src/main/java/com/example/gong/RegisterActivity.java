package com.example.gong;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    EditText nameInput, emailInput, passwordInput;
    Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register); // XML 연결

        // XML 요소와 연결
        nameInput = findViewById(R.id.editTextName);
        emailInput = findViewById(R.id.editTextEmail);
        passwordInput = findViewById(R.id.editTextPassword);
        registerButton = findViewById(R.id.buttonRegister);


        // XML 요소와 연결
        nameInput = findViewById(R.id.editTextName);
        emailInput = findViewById(R.id.editTextEmail);
        passwordInput = findViewById(R.id.editTextPassword);
        registerButton = findViewById(R.id.buttonRegister);

// ✅ 스페이스 제거용 TextWatcher 추가
        nameInput.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().contains(" ")) {
                    nameInput.setText(s.toString().replace(" ", ""));
                    nameInput.setSelection(nameInput.length()); // 커서 맨 끝으로
                }
            }

            @Override
            public void afterTextChanged(android.text.Editable s) {}
        });

        // 버튼 클릭 시 서버에 회원가입 요청 보내기
        registerButton.setOnClickListener(v -> {
            String name = nameInput.getText().toString();
            String email = emailInput.getText().toString();
            String password = passwordInput.getText().toString();

            ApiService api = RetrofitClient.getApiService();
            api.registerUser(name, email, password).enqueue(new Callback<ServerResponse>() {
                @Override
                public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().status.equals("success")) {
                            Toast.makeText(RegisterActivity.this, "회원가입 성공!", Toast.LENGTH_SHORT).show();
                            finish(); // 로그인 화면으로 돌아가기
                        } else {
                            Toast.makeText(RegisterActivity.this, "회원가입 실패: " + response.body().message, Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ServerResponse> call, Throwable t) {
                    Toast.makeText(RegisterActivity.this, "네트워크 오류: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}
