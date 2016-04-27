package com.relaxedsoul.projectskeleton.ui.main;

import android.os.Bundle;

import com.relaxedsoul.projectskeleton.R;
import com.relaxedsoul.projectskeleton.annotation.ActivityRef;
import com.relaxedsoul.projectskeleton.ui.BaseAbstractActivity;

@ActivityRef(resource = R.layout.activity_main)
public class MainActivity extends BaseAbstractActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
