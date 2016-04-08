package com.relaxedsoul.projectskeleton.ui;

import android.app.ProgressDialog;
import android.app.Service;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.relaxedsoul.projectskeleton.annotation.ActivityRef;
import com.relaxedsoul.projectskeleton.event.BaseEvent;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

// TODO add navigation support
@ActivityRef
public class BaseAbstractActivity extends AppCompatActivity {

    protected static final String EXTRA_ID = "EXTRA_ID";
    private ProgressDialog mProgressDialog;

    private ActivityRef activityRef = getClass().getAnnotation(ActivityRef.class);
    private Toolbar mToolbar;

    protected void showProgressDialog(boolean show) {
        if (mProgressDialog != null) mProgressDialog.dismiss();
        if (!show) return;
        hideKeyboard();
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (activityRef.resource() != 0) {
            setContentView(activityRef.resource());
            if (activityRef.toolbarResId() != 0) {
                mToolbar = (Toolbar) findViewById(activityRef.toolbarResId());
                setSupportActionBar(mToolbar);
            }
            if (activityRef.knifeEnabled()) ButterKnife.inject(this);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (activityRef.busEnabled() && !EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onStop() {
        if (activityRef.busEnabled() && EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean result = super.onCreateOptionsMenu(menu);
        restoreActionBar();
        return result;
    }

    protected void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) return;
        actionBar.setDisplayShowHomeEnabled(activityRef.navigation() || activityRef.isHomeAsUp());
        if (activityRef.isHomeAsUp()) actionBar.setDisplayHomeAsUpEnabled(true);
    }

    protected void hideKeyboard() {
        View v = getCurrentFocus();
        if (v != null) {
            ((InputMethodManager) getSystemService(Service.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    public void onEventMainThread(BaseEvent unused) {
        // do nothing. for EventBus, if it would be registered
    }
}
