package com.cenah.smarthome.fragments;


import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cenah.smarthome.R;
import com.cenah.smarthome.helpers.PrograssBarDialog;
import com.cenah.smarthome.holders.NottificationViewHolder;
import com.cenah.smarthome.models.Notification;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragment extends Fragment {


    private RecyclerView recyclerView;
    private PrograssBarDialog prograssBarDialog;
    private View rootView;
    private Activity activity;

    private FirebaseRecyclerAdapter<Notification, NottificationViewHolder> adapter;
    private DatabaseReference list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_notification, container, false);
        activity = getActivity();

        recyclerView = rootView.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);


        FirebaseDatabase db = FirebaseDatabase.getInstance();
        list = db.getReference("Notification");
        fetch();
        return rootView;
    }

    private void fetch() {
        adapter = new FirebaseRecyclerAdapter<Notification, NottificationViewHolder>
                (Notification.class, R.layout.notificaiton_item, NottificationViewHolder.class,
                        list) {
            @Override
            protected void populateViewHolder(NottificationViewHolder viewHolder, Notification model, int position) {
                viewHolder.tx_desc.setText(model.getDescription().trim());
                Picasso.get().load(model.getImage()).into(viewHolder.image);

            }
        };
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }

}
