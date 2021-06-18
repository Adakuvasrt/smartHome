package com.example.smarthome;

import java.net.Socket;

/**
 * 获取Socket通信数据 的单例类
 *
 * @auther: ChenYiXing
 * @date 2021/6/18/018 8:53
 **/
public class DataFresh {
    public Socket socket;


    private DataFresh() {
    }

    //静态内部类在使用时加载,并且是线程安全的
    private static class DataFreshInstance {
        private static final DataFresh INSTANCE = new DataFresh();
    }

    /**
     * 获取单例类
     *
     * @return com.example.smarthome.DataFresh
     * @Author: ChenYiXing
     * @date 2021/6/18/018 9:07
     **/
    public static synchronized DataFresh getInstance() {

        return DataFreshInstance.INSTANCE;
    }

    /**
     * 获取Socket连接
     *
     * @param ip   IP地址
     * @param port 端口号
     * @author: ChenYiXing
     * @date 2021/6/18/018 8:58
     **/
    public void getConnect(final String ip, final int port) throws Exception {

        socket = new Socket(ip, port);

    }
}
