package com.yangnk.rpc.myRPC_v2.registry;

import java.io.Serializable;

/**
 * @author yangningkai
 * @create 2019-01-10 10:12
 **/

public class InvokeProtocal implements Serializable {
    private String className;
    private String methodName;
    private Class<?>[] params;
    private Object[] values;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getParams() {
        return params;
    }

    public void setParams(Class<?>[] params) {
        this.params = params;
    }

    public Object[] getValues() {
        return values;
    }

    public void setValues(Object[] values) {
        this.values = values;
    }
}
