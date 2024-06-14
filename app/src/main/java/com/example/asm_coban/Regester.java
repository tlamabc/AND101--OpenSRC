package com.example.asm_coban;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.asm_coban.model.Account;
import com.example.asm_coban.service.MyFile;
import com.example.asm_coban.R;

import java.util.ArrayList;
import java.util.List;

public class Regester extends AppCompatActivity {

    List<Account> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regester);

        list = MyFile.readFileAcc(this, MyFile.FileAcc);
        if(list.isEmpty()){
            list = new ArrayList<>();
            Toast.makeText(this, "Check", Toast.LENGTH_SHORT).show();
        }

        Button btnHadAcc = findViewById(R.id.btnHadAcc);
        Button btnRegester = findViewById(R.id.btnRegester);
        EditText edtUsername2 = findViewById(R.id.edtUsername2);
        EditText edtPassword2 = findViewById(R.id.edtPassword2);
        EditText edtConfirmPass = findViewById(R.id.edtConfirmPass);

        btnHadAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Regester.this, Login.class);
                startActivity(intent);
            }
        });

        btnRegester.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                

                String username = edtUsername2.getText().toString();
                String password = edtPassword2.getText().toString();
                String confirm = edtConfirmPass.getText().toString();

                for(Account acc : list){
                    if(acc.getUsername().equals(username)){
                        Toast.makeText(Regester.this, "Tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                if (username.trim().equals("")) {
                    edtUsername2.setError("Tên đăng nhập không được để trống");
                    return;
                }
                if (password.trim().equals("")) {
                    edtPassword2.setError("Mật khẩu không được để trống");
                    return;
                }

                if (!username.matches("[\\w]+")) {
                    edtUsername2.setError("Tên đăng nhập không hợp lệ");
                    return;
                }
                if (!password.matches("[\\w]+") && password.trim().equals("")) {
                    edtPassword2.setError("Mật khẩu không hợp lệ");
                    return;
                }
                if (!password.equals(confirm)) {
                    edtConfirmPass.setError("Mật khẩu không trùng khớp");
                    return;
                }


                list.add(new Account(username, password));

                MyFile.writeFileAcc(Regester.this, MyFile.FileAcc, list);

                Intent intent = new Intent(Regester.this, Login.class);
                Toast.makeText(Regester.this, "Đăng ký thành công, đăng nhập để tiếp tục", Toast.LENGTH_SHORT).show();
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }
}