package com.relaxedsoul.projectskeleton.ui.main;

import android.os.Bundle;

import com.relaxedsoul.projectskeleton.R;
import com.relaxedsoul.projectskeleton.ui.BaseAbstractActivity;

public class MainActivity extends BaseAbstractActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
