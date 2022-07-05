package com.yangnk.others.myTest.JdkDynastyProxy;

/**
 * @author yangningkai
 * @create 2019-01-05 16:07
 **/

public class KinstonUSBFactory implements IUSBFactory {
    int price = 25;

    @Override
    public int sell(int count) {
        int result = count * price;
        System.out.printf("===KinstonUSBFactory result:%d===\n",result);
        return result;
    }
}
