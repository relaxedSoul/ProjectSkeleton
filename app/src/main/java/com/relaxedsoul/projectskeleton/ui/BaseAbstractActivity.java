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

/**
 * Base activity implementation. Use this class for auto apply basic functionality
 * <p>
 * Use annotation {@link ActivityRef} for configuring activity workaround.
 * Class is not final and can be modified in project.
 * <pre><code>
 *     {@literal @}ActivityRef(resource = R.layout.activity_main, knifeEnabled = true,
 *     busEnabled = true, isHomeAsUp = true)
 *     public class MainActivity extends BaseAbstractActivity {
 *         ...
 *     }
 * </code></pre>
 * </p>
 * <p>
 * Usually several functions are implemented here:
 * <ul>
 *     <li>{@link #showProgressDialog(boolean)} &ndash; Shows progress dialog without message</li>
 *     <li>{@link #hideKeyboard()} &ndash; Hides keyboard if it was shown</li>
 * </ul>
 * </p>
 * @see ActivityRef
 *
 */
@ActivityRef
public class BaseAbstractActivity extends AppCompatActivity {

    /** Common extra key for activities */
    protected static final String EXTRA_ID = "EXTRA_ID";

    private ProgressDialog mProgressDialog;

    private ActivityRef activityRef = getClass().getAnnotation(ActivityRef.class);
    private Toolbar mToolbar;

    /** Calls dialog with progress bar for blocking UI, while some background task have to be executed */
    protected void showProgressDialog(boolean show) {
        if (mProgressDialog != null) mProgressDialog.dismiss();
        if (!show) return;
        hideKeyboard();
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    /**
     * In OnCreate we have workaround with 3 annotation parameters:
     * <ul>
     *     <li>resource &ndash; for setContentView method</li>
     *     <li>toolbarResId &ndash; for toolbar activation (if it was defined)</li>
     *     <li>knifeEnabled &ndash; for injecting views, if it is set to True</li>
     * </ul>
     */
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

    /** Here we register class in eventBus if needed */
    @Override
    protected void onStart() {
        super.onStart();
        if (activityRef.busEnabled() && !EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    /** Here we unregister class in eventBus if needed */
    @Override
    protected void onStop() {
        if (activityRef.busEnabled() && EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        super.onStop();
    }

    /**
     *
     */
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
