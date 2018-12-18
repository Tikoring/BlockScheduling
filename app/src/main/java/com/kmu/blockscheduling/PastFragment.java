package com.kmu.blockscheduling;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class PastFragment extends Fragment{
    private ListView myListView;
    ArrayAdapter adapter;
    DBHandler handler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup contatiner, Bundle savedInstanceStace) {
        handler = null;
        ArrayList arrayList = null;
        myListView = null;
        adapter = null;

        View v = inflater.inflate(R.layout.past_fragment, contatiner, false);
        handler = new DBHandler(getContext());
        arrayList = handler.getAllSchedules();

        adapter = new ArrayAdapter(this.getContext(), R.layout.list_item, arrayList);
        myListView = (ListView) v.findViewById(R.id.past_list);
        myListView.setAdapter(adapter);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.clear();
        adapter.addAll(handler.getAllSchedules());
        adapter.notifyDataSetChanged();
    }
}
