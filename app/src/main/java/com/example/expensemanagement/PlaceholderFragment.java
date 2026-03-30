package com.example.expensemanagement;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class PlaceholderFragment extends Fragment {

    private static final String ARG_TITLE    = "title";
    private static final String ARG_SUBTITLE = "subtitle";

    public static PlaceholderFragment newInstance(String title, String subtitle) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putString(ARG_SUBTITLE, subtitle);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_placeholder, container, false);
        if (getArguments() != null) {
            ((TextView) view.findViewById(R.id.tvTitle)).setText(getArguments().getString(ARG_TITLE, ""));
            ((TextView) view.findViewById(R.id.tvSubtitle)).setText(getArguments().getString(ARG_SUBTITLE, ""));
        }
        return view;
    }
}