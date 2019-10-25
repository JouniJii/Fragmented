package com.example.fragmented;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;


public class playerAdapter extends ArrayAdapter<playerObj> {

    private int line_layout;
    private LayoutInflater inflater;
    private ArrayList<playerObj> players;

    public playerAdapter(@NonNull Context context, int resource, ArrayList<playerObj> objects) {
        super(context, resource, objects);

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        line_layout = resource;
        this.players = objects;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        playerObj po = players.get(position);

        if (convertView == null) {
            convertView = inflater.inflate(line_layout, parent, false);
        }
        TextView textView1 = convertView.findViewById(R.id.textView1);
        TextView textView2 = convertView.findViewById(R.id.textView2);

        textView1.setText(po.getName());
        textView2.setText(po.getDateDisplay());

        return convertView;
    }
}