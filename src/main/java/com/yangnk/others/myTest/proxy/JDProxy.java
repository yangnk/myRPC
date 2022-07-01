package com.yangnk.others.myTest.proxy;

/**
 * @author yangningkai
 * @create 2022-06-30 15:41
 **/

public class JDProxy implements IUSBSeller{
    IUSBSeller seller = new KinstonUSBFactory();
    int fee = 11;

    @Override
    public int sell(int count) {
        int price = seller.sell(count);
        int result = price + fee;
        System.out.printf("===JDProxy result:%d===\n",result);
        return result;
    }
}
