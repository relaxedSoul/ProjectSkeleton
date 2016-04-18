package com.relaxedsoul.projectskeleton.ui.adapter;

import android.support.annotation.NonNull;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by RelaxedSoul on 18.04.2016.
 */
public abstract class TypedBaseAdapter extends BaseAdapter {

    protected final List<TypedData> items;
    private final SparseArray<Class<? extends TypedViewHolder>> clazzArray;
    private final SparseArray<Method> methodArray;

    protected TypedBaseAdapter(@NonNull SparseArray<Class<? extends TypedViewHolder>> clazzArray,
                               @NonNull List<TypedData> dataList) {
        this.clazzArray = clazzArray;
        this.items = dataList;
        if (clazzArray.size() == 0) {
            throw new InvalidParameterException("Holders array must contain at least one item");
        }
        methodArray = getAnnotatedMethods();
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return items.get(position).getAdapterItemId();
    }

    @Override
    public int getViewTypeCount() {
        return clazzArray.size();
    }

    @Override
    public int getItemViewType(int position) {
        return ((TypedData) getItem(position)).getViewTypeOrdinal();
    }

    public void notifyDataSetChanged(List<TypedData> newData) {
        items.clear();
        items.addAll(newData);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TypedData data = (TypedData) getItem(position);
        if (convertView == null) {
            convertView = TypedViewHolder.createView(parent.getContext(), clazzArray.get(data.getViewTypeOrdinal()));
        }
        if (convertView == null) return null;
        fillView(data.getViewTypeOrdinal(), (TypedViewHolder) convertView.getTag(), data.getTypedData());
        return convertView;
    }

    private void fillView(int viewTypeOrdinal, TypedViewHolder holder, Object data) {
        Method method = methodArray.get(viewTypeOrdinal);
        try {
            if (method != null) {
                method.invoke(this, holder, data);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private SparseArray<Method> getAnnotatedMethods() {
        final SparseArray<Method> methods = new SparseArray<>();
        Class<?> clazz = this.getClass();
        while (clazz != Object.class) {
            for (final Method method : new ArrayList<>(Arrays.asList(clazz.getDeclaredMethods()))) {
                if (method.isAnnotationPresent(TypedViewFill.class)) {
                    TypedViewFill annotInstance = method.getAnnotation(TypedViewFill.class);
                    methods.put(annotInstance.viewType(), method);
                }
            }
            clazz = clazz.getSuperclass();
        }
        return methods;
    }

    public abstract void setData(List<Object> list);
}