package com.relaxedsoul.projectskeleton.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.relaxedsoul.projectskeleton.annotation.FragmentDef;
import com.relaxedsoul.projectskeleton.event.BaseEvent;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Base fragment class with basic methods
 * <p>
 * Created by RelaxedSoul on 27.07.2015.
 */
@FragmentDef
public class BaseAbstractFragment extends Fragment {

    protected final static String EXTRA_STATE = "EXTRA_STATE";
    protected final static String EXTRA_ID = "EXTRA_ID";
    private FragmentDef fragmentDef;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentDef = getClass().getAnnotation(FragmentDef.class);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return fragmentDef != null && fragmentDef.resource() != 0
                ? inflater.inflate(fragmentDef.resource(), container, false)
                : super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (fragmentDef.knifeEnabled()) ButterKnife.inject(this, view);
        if (fragmentDef.busEnabled()) EventBus.getDefault().register(this);
    }

    public void onEventMainThread(BaseEvent unused) {
        // do nothing. for EventBus, if it would be registered
    }

    @Override
    public void onDestroyView() {
        if (fragmentDef.busEnabled()) EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }
}
