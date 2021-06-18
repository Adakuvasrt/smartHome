package com.example.smarthome;

import com.github.mikephil.charting.data.Entry;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

/**
 * 获取Socket通信数据 的单例类
 *
 * @auther: ChenYiXing
 * @date 2021/6/18/018 8:53
 **/
public class DataFresh {
    public Socket socket;
    public InputStream inputStream;

    public float temperature;
    public float humility;
    public float light;
    public float electricity;
    public float machine;
    public float xAxis;
    public float yAxis;
    public float zAxis;

    List<Float> temFloats;
    List<Float> humFloats;
    List<Float> lightFloats;
    List<Float> eleFloats;
    List<Float> machineFloats;
    List<Float> xFloats;
    List<Float> yFloats;
    List<Float> zFloats;


    List<Entry> tementries;
    List<Entry> humentries;
    List<Entry> lightentries;
    List<Entry> eleentries;
    List<Entry> machineentries;
    List<Entry> xentries;
    List<Entry> yentries;
    List<Entry> zentries;

    private DataFresh() {
        temFloats = new ArrayList<>();
        humFloats = new ArrayList<>();
        lightFloats = new ArrayList<>();
        eleFloats = new ArrayList<>();
        machineFloats = new ArrayList<>();
        xFloats = new ArrayList<>();
        yFloats = new ArrayList<>();
        zFloats = new ArrayList<>();


        tementries = new ArrayList<>();
        humentries = new ArrayList<>();
        lightentries = new ArrayList<>();
        eleentries = new ArrayList<>();
        machineentries = new ArrayList<>();
        xentries = new ArrayList<>();
        yentries = new ArrayList<>();
        zentries = new ArrayList<>();

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
        new Thread(() -> {
            try {
                socket = new Socket(ip, port);
                inputStream = socket.getInputStream();
            } catch (Exception e) {
                e.printStackTrace();
            }
            byte[] bytes = new byte[30];
            while (true) {
                //读取服务器数据
                try {
                    inputStream.read(bytes);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //准备一个结构体类
                ConverEnvInfo cei = new ConverEnvInfo();
                //把b转换为cei的数据
                cei.setByteBuffer(ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN), 0);
                //拿到八个数据
                temperature = cei.temperature.get();
                humility = cei.humidity.get();
                light = cei.ill.get();
                electricity = cei.bet.get();
                machine = cei.adc.get();
                xAxis = cei.x.get();
                yAxis = cei.y.get();
                zAxis = cei.z.get();
                freshData();
                System.out.println(temperature);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }

    private void freshData() {
//        if (temFloats.size() < 8) temFloats.add(temperature);
//        if (humFloats.size() < 8) humFloats.add(humility);
//        if (lightFloats.size() < 8) lightFloats.add(light);
//        if (eleFloats.size() < 8) eleFloats.add(electricity);
//        if (machineFloats.size() < 8) machineFloats.add(machine);
//        if (xFloats.size() < 8) xFloats.add(xAxis);
//        if (yFloats.size() < 8) yFloats.add(yAxis);
//        if (zFloats.size() < 8) zFloats.add(zAxis);
//        else resetArray();
    }

    private void resetArray() {
        for (int i = 1; i < 8; i++) {
            temFloats.set(i - 1, temFloats.get(i));
        }
        temFloats.add(temperature);

        for (int i = 0; i < 7; i++) {
            temFloats.set(i + 1, temFloats.get(i));
        }

        for (int i = 0; i < 7; i++) {
            temFloats.set(i + 1, temFloats.get(i));
        }

        for (int i = 0; i < 7; i++) {
            temFloats.set(i + 1, temFloats.get(i));
        }

        for (int i = 0; i < 7; i++) {
            temFloats.set(i + 1, temFloats.get(i));
        }

        for (int i = 0; i < 7; i++) {
            temFloats.set(i + 1, temFloats.get(i));
        }

        for (int i = 0; i < 7; i++) {
            temFloats.set(i + 1, temFloats.get(i));
        }

        for (int i = 0; i < 7; i++) {
            temFloats.set(i + 1, temFloats.get(i));
        }

    }

}
