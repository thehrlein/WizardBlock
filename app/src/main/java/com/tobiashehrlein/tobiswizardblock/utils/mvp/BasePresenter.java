package com.tobiashehrlein.tobiswizardblock.utils.mvp;

import java.lang.ref.WeakReference;


/**
 * Created by Tobias Hehrlein on 23.10.2017.
 */

public abstract class BasePresenter<V extends BaseView> implements BaseMvpPresenter<V> {

    private WeakReference<V> view;

    public BasePresenter() {

    }

    @Override
    public void attach(V view) {
        this.view = new WeakReference(view);
    }

    @Override
    public void detach() {
        this.view.clear();
    }

    @Override
    public boolean isAttached() {
        return this.view != null && this.view.get() != null;
    }

    @Override
    public V getView() {
        return view.get();
    }
}
