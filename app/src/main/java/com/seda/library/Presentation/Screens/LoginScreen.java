package com.seda.library.Presentation.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.seda.library.DataAccess.Manager.DatabaseManager;
import com.seda.library.DataAccess.Repository.Concrete.JDBC.UserDAL;
import com.seda.library.Presentation.Utility;
import com.seda.library.R;

public class LoginScreen extends AppCompatActivity {

    EditText etUserName,etPassword;
    CheckBox chbStaySigned;
    UserDAL _userDal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_login);
        etUserName = findViewById(R.id.et_login_username);
        etPassword = findViewById(R.id.et_login_password);
        chbStaySigned = findViewById(R.id.chb_login_stay_signed);
        _userDal = new UserDAL(new DatabaseManager());
    }


    public void onClickLogin(View v){
        switch (v.getId()){
            case R.id.btn_login_enter:
                Login();
                break;
            case R.id.btn_login_sign_up:
                startActivity(new Intent(this,SignupScreen.class));
                break;
        }
    }

    private void Login() {
        if(!TextUtils.isEmpty(etUserName.getText()) && !TextUtils.isEmpty(etPassword.getText())){
            _userDal.GetList(x -> x.TCKimlik.equals(etUserName.getText().toString()) && x.Password.equals(etPassword.getText().toString())).stream().findFirst().ifPresent(u -> Utility._currentUser = u);
            if(Utility._currentUser != null && Utility._currentUser.Id > 0){
                if(TextUtils.isEmpty(Utility._currentUser.Name) ||
                        TextUtils.isEmpty(Utility._currentUser.Surname) ||
                        TextUtils.isEmpty(Utility._currentUser.Phone) ||
                        TextUtils.isEmpty(Utility._currentUser.Number)){
                    Utility.ShowToast(this,"Hoşgeldin, Son Bir Adım Kaldı! Lütfen Formu Eksiksiz Bir Şekilde Doldur!");
                }
                else Utility.ShowToast(this,"Hoşgeldin, "+Utility._currentUser.Name);
                if(chbStaySigned.isChecked()){
                    Utility.SaveUser(this,Utility._currentUser);
                }
                startActivity(new Intent(this,MainScreen.class));
            }else{
                Utility.ShowToast(this,"Girmiş Olduğun Bilgiler Hatalı!");
            }
        }else{
            Utility.ShowToast(this,"Tc Kimlik ve Şifre Alanı Boş Bırakılamaz!");
        }
    }
}
