package com.seda.library.Presentation;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.seda.library.DataAccess.Entites.User;
import com.seda.library.DataAccess.Manager.DatabaseManager;
import com.seda.library.DataAccess.Repository.Concrete.JDBC.UserDAL;
import com.seda.library.R;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;
import com.yarolegovich.lovelydialog.LovelyTextInputDialog;

import java.util.Objects;

public class Utility {
    private static final String UserTAG = "UserCredit";
    public static User _currentUser;

    public static User GetSavedUser(Context activity) {
        SharedPreferences sharedPref = activity.getSharedPreferences(UserTAG, Context.MODE_PRIVATE);
        int keepLogged = sharedPref.getInt("keepLogged", 0);
        if (keepLogged == 0) return null;
        User p = new User();
        p.TCKimlik = sharedPref.getString("LoginUserName", "");
        p.Password = sharedPref.getString("LoginPassword", "");
        return p;
    }

    public static void SaveUser(Context activity, User p) {
        SharedPreferences sharedPref = activity.getSharedPreferences(UserTAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("keepLogged", 1);
        editor.putString("LoginUserName", p.TCKimlik);
        editor.putString("LoginPassword", p.Password);
        editor.apply();
    }

    public static void ClearSavedUser(Context activity) {
        SharedPreferences sharedPref = activity.getSharedPreferences(UserTAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("keepLogged",1);
        editor.clear();
        editor.apply();
    }

    public static void ShowToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    public static LovelyTextInputDialog InputDialog(Context context) {
        LovelyTextInputDialog dialog = new LovelyTextInputDialog(context)
                .setTopColorRes(R.color.colorPrimary)
                .setMessage("Hoşgeldin, Hakkında birkaç bilgiye ihtiyacımız var!")
                .setConfirmButton("Kaydet", text -> {
                    Utility.ShowToast(context, text);
                });

        return dialog;
    }

    public static Dialog UpdateDialog(Context context, boolean isCancelable) {
        Dialog dialog = new Dialog(context);
        dialog.setCancelable(isCancelable);
        dialog.setContentView(R.layout.dialog_update_user);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        dialog.getWindow().setAttributes(lp);

        EditText etName = dialog.findViewById(R.id.et_update_user_first_name),
                etSurname = dialog.findViewById(R.id.et_update_user_last_name),
                etStudentNumber = dialog.findViewById(R.id.et_update_user_student),
                etPhone = dialog.findViewById(R.id.et_update_user_phone);
        Button btnSave = dialog.findViewById(R.id.btn_update_user_save);

        if (!TextUtils.isEmpty(Utility._currentUser.Name))
            etName.setText(Utility._currentUser.Name);
        if (!TextUtils.isEmpty(Utility._currentUser.Surname))
            etSurname.setText(Utility._currentUser.Surname);
        if (!TextUtils.isEmpty(Utility._currentUser.Number))
            etStudentNumber.setText(Utility._currentUser.Number);
        if (!TextUtils.isEmpty(Utility._currentUser.Phone))
            etName.setText(Utility._currentUser.Phone);

        btnSave.setOnClickListener(v -> {
            _currentUser.Name = TextUtils.isEmpty(etName.getText()) ? _currentUser.Name : etName.getText().toString();
            _currentUser.Surname = TextUtils.isEmpty(etSurname.getText()) ? _currentUser.Surname : etSurname.getText().toString();
            _currentUser.Number = TextUtils.isEmpty(etStudentNumber.getText()) ? _currentUser.Number : etStudentNumber.getText().toString();
            _currentUser.Phone = TextUtils.isEmpty(etPhone.getText()) ? _currentUser.Phone : etPhone.getText().toString();
            _currentUser = new UserDAL(new DatabaseManager()).Update(_currentUser);
            if (_currentUser != null && _currentUser.Id > 0) {
                ShowToast(context, "Bilgilerin Güncellendi!");
            } else {
                ShowToast(context, "Bilgilerin Güncellenirken Bir Hata Oluştu!");
            }
            dialog.dismiss();
        });

        return dialog;
    }

    public static void ShowExitDialog(Activity context) {
        LovelyStandardDialog dialog = new LovelyStandardDialog(context)
                .setTopColorRes(R.color.colorPrimary)
                .setIcon(R.drawable.ic_error)
                .setMessage("Oturumun Açık Kalsın Mı ?")
                .setPositiveButton("Evet", v -> {
                    context.finish();
                    context.finishAffinity();
                    System.exit(0);
                }).setNegativeButton("Hayır", v -> {
                    ClearSavedUser(context);
                    context.finish();
                    context.finishAffinity();
                    System.exit(0);
                });

        dialog.show();
    }
}
