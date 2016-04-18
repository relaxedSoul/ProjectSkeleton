package com.relaxedsoul.projectskeleton.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import java.lang.reflect.InvocationTargetException;

import butterknife.ButterKnife;

/**
 * Created by RelaxedSoul on 18.04.2016.
 */
@TypedHolderRef
public abstract class TypedViewHolder {
    protected final int contentViewResId;

    public TypedViewHolder() {
        TypedHolderRef ref = getClass().getAnnotation(TypedHolderRef.class);
        contentViewResId = ref.resource();
    }

    public static View createView(Context context, Class<? extends TypedViewHolder> holderClazz) {
        TypedViewHolder holder = null;
        try {
            holder = holderClazz.getConstructor().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        if (holder == null) return null;
        View result = LayoutInflater.from(context).inflate(holder.getViewResId(), null);
        ButterKnife.inject(holder, result);
        result.setTag(holder);
        return result;
    }

    private int getViewResId() {
        return contentViewResId;
    }

}