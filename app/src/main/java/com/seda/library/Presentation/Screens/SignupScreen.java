package com.seda.library.Presentation.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.seda.library.DataAccess.Entites.User;
import com.seda.library.DataAccess.Manager.DatabaseManager;
import com.seda.library.DataAccess.Repository.Concrete.JDBC.UserDAL;
import com.seda.library.Presentation.Utility;
import com.seda.library.R;

public class SignupScreen extends AppCompatActivity {

    EditText etUserName,etEmail,etPassword,etPasswordConfirm;
    UserDAL _userDal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_signup);
        etUserName = findViewById(R.id.et_sign_up_username);
        etEmail = findViewById(R.id.et_sign_up_email);
        etPassword = findViewById(R.id.et_sign_up_password);
        etPasswordConfirm = findViewById(R.id.et_sign_up_password_confirm);
        _userDal = new UserDAL(new DatabaseManager());
    }

    public void onClickSignUp(View v){
        switch (v.getId()){
            case R.id.btn_sign_up_enter:
                SignUp();
                break;
            case R.id.btn_sign_up_login:
                startActivity(new Intent(this,LoginScreen.class));
                break;
        }
    }

    private void SignUp() {
        if(!TextUtils.isEmpty(etUserName.getText()) && !TextUtils.isEmpty(etEmail.getText()) && !TextUtils.isEmpty(etPassword.getText()) && !TextUtils.isEmpty(etPasswordConfirm.getText())){
            if(etPassword.getText().toString().equals(etPasswordConfirm.getText().toString())){
                User u = new User();
                u.TCKimlik = etUserName.getText().toString();
                u.Email = etEmail.getText().toString();
                u.Password = etPassword.getText().toString();
                u.Name = "";
                u.Surname = "";
                u.Number = "";
                u.Phone = "";
                u = _userDal.Add(u);
                if(u != null && u.Id > 0){
                    Utility.ShowToast(this,"Harika! Artık Giriş Yapabilirsin!");
                    startActivity(new Intent(this,LoginScreen.class));
                }else {
                    Utility.ShowToast(this,"Kaydolurken Bir Hata Oluştu. Lütfen Tekrar Dene!");
                }
            }else {
                Utility.ShowToast(this,"Şifreler Eşleşmiyor!");
            }
        }else{
            Utility.ShowToast(this,"Lütfen Bilgilerini Eksiksiz Doldur!");
        }
    }
}
