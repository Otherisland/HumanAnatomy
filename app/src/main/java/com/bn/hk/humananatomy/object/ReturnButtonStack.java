package com.bn.hk.humananatomy.object;

import java.util.List;

public class ReturnButtonStack {
    private int function;
    private List<TouchableObject> alist;
    public ReturnButtonStack(int function, List<TouchableObject> alist)
    {
        this.function=function;
        this.alist=alist;
    }
    public int getFunction()
    {
        return function;
    }
    public List<TouchableObject> getAlist()
    {
        return alist;
    }
}
