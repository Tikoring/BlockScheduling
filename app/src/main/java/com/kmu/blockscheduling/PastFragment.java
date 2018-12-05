package com.kmu.blockscheduling;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PastFragment extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup contatiner, Bundle savedInstanceStace) {
        return inflater.inflate(R.layout.current_fragment, contatiner, false);
    }
}
