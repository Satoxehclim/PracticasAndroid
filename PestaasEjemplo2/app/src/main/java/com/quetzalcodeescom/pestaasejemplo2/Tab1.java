package com.quetzalcodeescom.pestaasejemplo2;

import android.os.*;
import androidx.fragment.app.Fragment;
import android.view.*;
public class Tab1 extends Fragment {
    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);
    }
    @Override
    public View onCreateView(LayoutInflater li, ViewGroup vg, Bundle bn) {
        return li.inflate(R.layout.tab1, vg, false);
    }
}