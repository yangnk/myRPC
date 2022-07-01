package com.yangnk.rpc.myRPC.registry;

import java.io.IOException;

/**
 * @author yangningkai
 * @create 2022-07-01 15:40
 **/

public interface Registry {
    public void stop();

    public void start() throws IOException;

    public void register(Class serviceInterface, Class impl);

    public boolean isRunning();

    public int getPort();

}
