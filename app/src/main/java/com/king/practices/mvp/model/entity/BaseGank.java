package com.king.practices.mvp.model.entity;

/**
 * Created by Administrator on 2017/9/7.
 */

public class BaseGank<T> {
    private Boolean error;
    private T results;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public T getResults() {
        return results;
    }

    public void setResults(T results) {
        this.results = results;
    }

    public boolean isSuccess() {
        return !error;
    }
}
