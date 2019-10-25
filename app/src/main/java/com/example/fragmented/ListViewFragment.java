package com.example.fragmented;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

interface IlistviewListener {
    void dataToFragment(ArrayList<playerObj> po);
}

public class ListViewFragment extends Fragment implements IlistviewListener {

    private playerAdapter adapter;
    private ArrayList<playerObj> players = new ArrayList<>();
    private ListView listView;
    private IlistviewListener mListener;

    public ListViewFragment(){
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_listview, container, false);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        adapter = new playerAdapter(this.getActivity(), R.layout.fragment_listviewitem, players);
        listView = getView().findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (this instanceof IlistviewListener) {
            mListener = this;
        } else {
            throw new RuntimeException(context.toString() +
                    " must implement IlistviewListener");
        }
    }

    public void dataToFragment (ArrayList<playerObj> p) {
        players = p;
        adapter.clear();
        adapter.addAll(players);
        adapter.notifyDataSetChanged();
        Log.i("OMA", "dataToFragment: list size: " + players.size());
    }

}
