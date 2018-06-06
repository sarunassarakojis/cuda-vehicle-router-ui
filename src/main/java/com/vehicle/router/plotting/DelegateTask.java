package com.vehicle.router.plotting;

import javafx.concurrent.Task;

import java.util.concurrent.Callable;

public class DelegateTask<T> extends Task<T> {

    private final Callable<T> delegate;

    public DelegateTask(Callable<T> delegate) {
        this.delegate = delegate;
    }

    @Override
    protected T call() throws Exception {
        return delegate.call();
    }
}
