package com.yangnk.others.myTest.proxy;

/**
 * @author yangningkai
 * @create 2019-01-05 15:40
 **/

public class KinstonUSBFactory implements IUSBSeller {
    int price = 25;

    @Override
    public int sell(int count) {
        int result = count * price;
        System.out.printf("===KinstonUSBFactory result:%d===\n",result);
        return result;
    }
}
