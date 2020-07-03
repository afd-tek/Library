package com.seda.library.Presentation.Adapters;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.seda.library.DataAccess.Entites.Table;
import com.seda.library.DataAccess.Entites.User;
import com.seda.library.Presentation.Utility;
import com.seda.library.R;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DeskAdapter extends RecyclerView.Adapter<DeskAdapter.ViewHolder>{

    private List<Table> tables;
    private Drawable[] stateImage;
    Context context;
    String[] Rooms;

    private int currentRoom;
    public DeskAdapter(List<Table> tables, Context context) {
        this.tables = tables;
        this.stateImage = new Drawable[]{context.getDrawable(R.drawable.ic_state_on),context.getDrawable(R.drawable.ic_state_off)};
        this.Rooms = new String[]{"Salon A","Salon B","Salon C","Salon D","Salon E","Salon F"};
        this.context = context;
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView tableState;
        TextView tableName;
        RelativeLayout container;
        ViewHolder(@NonNull final View itemView) {
            super(itemView);
            tableName = itemView.findViewById(R.id.tv_item_desk_name);
            tableState = itemView.findViewById(R.id.iv_item_desk_state);
        }

    }

    @NonNull
    @Override
    public DeskAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_desk,parent,false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tableName.setText(String.format("%s\n%s",Rooms[tables.get(position).RoomId],tables.get(position).TableName));
        if(tables.get(position).State){
            holder.tableState.setImageDrawable(stateImage[0]);
        }else{
            holder.tableState.setImageDrawable(stateImage[1]);
        }
    }

    @Override
    public int getItemCount() {
        return tables.size();
    }


}
