package com.cenah.smarthome.holders;

import android.view.ContextMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cenah.smarthome.R;

public class NottificationViewHolder extends RecyclerView.ViewHolder  {

    public TextView tx_desc;
    public ImageView image;



    public NottificationViewHolder(@NonNull View itemView) {
        super(itemView);

        tx_desc = itemView.findViewById(R.id.tx_desc);
        image = itemView.findViewById(R.id.image);

    }


}
