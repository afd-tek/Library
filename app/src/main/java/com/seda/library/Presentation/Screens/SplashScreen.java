package com.seda.library.Presentation.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.seda.library.DataAccess.Entites.Book;
import com.seda.library.DataAccess.Entites.User;
import com.seda.library.DataAccess.Manager.DatabaseManager;
import com.seda.library.DataAccess.Repository.Concrete.JDBC.UserDAL;
import com.seda.library.DataAccess.Repository.Concrete.Retrofit.BookService;
import com.seda.library.Presentation.Utility;
import com.seda.library.R;
import com.yarolegovich.lovelydialog.LovelyProgressDialog;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class SplashScreen extends AppCompatActivity {


    private BookService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_splash);
        GetFromRetrofit();
        /*Utility._currentUser = Utility.GetSavedUser(this);
        if(Utility._currentUser == null){
            new Handler().postDelayed(() -> startActivity(new Intent(SplashScreen.this,LoginScreen.class)),3000);
        }else{
            UserDAL userDAL = new UserDAL(new DatabaseManager());
            User user = userDAL.GetList(x -> x.TCKimlik.equals(Utility._currentUser.TCKimlik) && x.Password.equals(Utility._currentUser.Password)).stream().findFirst().orElse(null);
            if(user != null){
                Utility._currentUser = user;
                Utility.ShowToast(this,"Tekrardan Hoşgeldin "+Utility._currentUser.Name);
                startActivity(new Intent(this,MainScreen.class));
            }else{
                Utility.ClearSavedUser(this);
                Utility.ShowToast(this,"Kayıtlı Kullanıcı Bilgileri Değiştiği İçin Giriş Sayfasına Yönlendirildin!");
                startActivity(new Intent(SplashScreen.this,LoginScreen.class));
            }
        }*/
    }

    private void GetFromRetrofit() {
        LovelyProgressDialog dialog = new LovelyProgressDialog(SplashScreen.this);
        dialog.show();
        service = new BookService();
        service.Get(4).thenAccept((value) -> {
            Toast.makeText(SplashScreen.this, value.Name, Toast.LENGTH_LONG).show();
            dialog.dismiss();
            startActivity(new Intent(this, LoginScreen.class));
        });
    }
}
