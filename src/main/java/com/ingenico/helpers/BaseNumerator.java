package com.ingenico.helpers;

public abstract class BaseNumerator {
    protected long Counter;

    public long getCurrentCounter(){
        return Counter++;
    }
}
