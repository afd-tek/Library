package com.seda.library.Presentation.Screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toolbar;

import com.seda.library.DataAccess.Entites.Table;
import com.seda.library.DataAccess.Manager.DatabaseManager;
import com.seda.library.DataAccess.Repository.Concrete.JDBC.TableDAL;
import com.seda.library.Presentation.Adapters.DeskAdapter;
import com.seda.library.Presentation.Utility;
import com.seda.library.R;
import com.yarolegovich.lovelydialog.LovelyProgressDialog;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class MainScreen extends AppCompatActivity {

    Toolbar toolbar;
    TextView tbUserName;
    ImageButton btnLogout, btnFilter;
    RecyclerView rvDesks;
    EditText etSearch;
    TableDAL _tableDal;
    List<Table> tables;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_main);

        if (TextUtils.isEmpty(Utility._currentUser.Name) ||
                TextUtils.isEmpty(Utility._currentUser.Surname) ||
                TextUtils.isEmpty(Utility._currentUser.Phone) ||
                TextUtils.isEmpty(Utility._currentUser.Number)) {
            Utility.UpdateDialog(this, false).show();
        }

        toolbar = findViewById(R.id.tb_main);
        tbUserName = toolbar.findViewById(R.id.tv_toolbar_main_user_name);
        tbUserName.setText(String.format("%s %s", Utility._currentUser.Name, Utility._currentUser.Surname));
        btnLogout = toolbar.findViewById(R.id.btn_toolbar_main_logout);
        btnLogout.setOnClickListener(v -> {
            Utility.ShowExitDialog(MainScreen.this);
        });

        _tableDal = new TableDAL(new DatabaseManager());

        rvDesks = findViewById(R.id.rv_main_desks);
        btnFilter = findViewById(R.id.ibtn_main_filter);
        btnFilter.setOnClickListener(this::ShowMulti);

        tables = _tableDal.GetList(null);
        DeskAdapter adapter = new DeskAdapter(tables, this);
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        rvDesks.setAdapter(adapter);
        rvDesks.setLayoutManager(manager);

        etSearch = findViewById(R.id.et_main_search);
        etSearch.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                if (!TextUtils.isEmpty(etSearch.getText())) {
                    LovelyProgressDialog progressDialog = new LovelyProgressDialog(this)
                            .setIcon(R.drawable.ic_sync_light)
                            .setMessage("Sonuçlar filtreleniyor...")
                            .setTopColorRes(R.color.colorPrimaryDark);
                    progressDialog.show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (!TextUtils.isEmpty(etSearch.getText()) && tables != null) {
                                tables = tables.stream().filter(x -> x.TableName.contains(etSearch.getText())).collect(Collectors.toList());
                                DeskAdapter adapterDesk = new DeskAdapter(tables, MainScreen.this);
                                GridLayoutManager managerDesk = new GridLayoutManager(MainScreen.this, 2);
                                rvDesks.setAdapter(adapterDesk);
                                rvDesks.setLayoutManager(managerDesk);
                                if(tables.size() == 0) {
                                    tables = _tableDal.GetList(null);
                                }
                            }else{
                                tables = _tableDal.GetList(null);
                            }
                            progressDialog.dismiss();
                        }
                    },500);
                }
            }
            return true;
        });
    }

    private void ShowMulti(View v) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_select_room);
        RadioGroup rgDialogRoom = dialog.findViewById(R.id.rg_dialog_room);
        Button btnSubmit = dialog.findViewById(R.id.btn_dialog_room_submit);
        btnSubmit.setOnClickListener(v1 -> {
            etSearch.setText("");
            switch (rgDialogRoom.getCheckedRadioButtonId()) {
                case R.id.rb_dialog_room_all:
                    tables = _tableDal.GetList(null);
                    break;
                case R.id.rb_dialog_room_1:
                    tables = _tableDal.GetList(x -> x.RoomId == 0);
                    break;
                case R.id.rb_dialog_room_2:
                    tables = _tableDal.GetList(x -> x.RoomId == 1);
                    break;
                case R.id.rb_dialog_room_3:
                    tables = _tableDal.GetList(x -> x.RoomId == 2);
                    break;
                case R.id.rb_dialog_room_4:
                    tables = _tableDal.GetList(x -> x.RoomId == 3);
                    break;
                case R.id.rb_dialog_room_5:
                    tables = _tableDal.GetList(x -> x.RoomId == 4);
                    break;
                case R.id.rb_dialog_room_6:
                    tables = _tableDal.GetList(x -> x.RoomId == 5);
                    break;

            }
            DeskAdapter adapter = new DeskAdapter(tables, this);
            GridLayoutManager manager = new GridLayoutManager(this, 2);
            rvDesks.setAdapter(adapter);
            rvDesks.setLayoutManager(manager);
            dialog.dismiss();
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        dialog.getWindow().setAttributes(lp);
        dialog.show();
    }
}
