package com.kmu.blockscheduling;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class CurrentFragment extends Fragment{
    private ListView myListView;
    ArrayAdapter adapter;
    DBHandler handler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup contatiner, Bundle savedInstanceStace) {
        ArrayList arrayList = null;
        handler = null;
        myListView = null;
        adapter = null;
        View v = inflater.inflate(R.layout.current_fragment, contatiner, false);

        handler = new DBHandler(getContext());
        arrayList = handler.getAllSchedules();

        adapter = new ArrayAdapter(this.getContext(), R.layout.list_item, arrayList);
        myListView = (ListView) v.findViewById(R.id.current_list);
        myListView.setAdapter(adapter);

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long arg4) {
                String item = (String) ((ListView) parent).getItemAtPosition(position);
                String[] strArray = item.split(" ");
                int id=Integer.parseInt(strArray[0]);
                Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", id);
                Intent intent = new Intent(getContext(), ScheduleActivity.class);
                intent.putExtras(dataBundle);
                startActivity(intent);
            }
        });

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
