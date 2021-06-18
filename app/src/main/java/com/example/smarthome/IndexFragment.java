package com.example.smarthome;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.alibaba.fastjson.JSON;
import com.example.smarthome.databinding.FragmentIndexBinding;
import com.example.smarthome.jsondata.JsonRootBean;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IndexFragment} factory method to
 * create an instance of this fragment.
 */
public class IndexFragment extends Fragment {

    private FragmentIndexBinding binding;

    private EditText ipAddress;
    private EditText portInfo;

    private Thread thread;


    @Override
    public void onAttach(@NonNull Context context) {
        Log.i("TAG", "onAttach: ");
        super.onAttach(context);
        thread = new Thread(() -> {
            while (true) {
                if (thread.isInterrupted()) break;

                getActivity().runOnUiThread(() -> {
                    binding.templateNum.setText(String.valueOf(DataFresh.getInstance().temperature));
                    binding.humidityNum.setText(String.valueOf(DataFresh.getInstance().humility));
                    binding.lightNum.setText(String.valueOf(DataFresh.getInstance().light));
                    binding.electricityNum.setText(String.valueOf(DataFresh.getInstance().electricity));
                    binding.machineNum.setText(String.valueOf(DataFresh.getInstance().machine));
                    binding.xAxisNum.setText(String.valueOf(DataFresh.getInstance().xAxis));
                    binding.yAxisNum.setText(String.valueOf(DataFresh.getInstance().yAxis));
                    binding.zAxisNum.setText(String.valueOf(DataFresh.getInstance().zAxis));
                });
                try {
                    Thread.sleep(900);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
        });
//        onInitBtn();
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        Log.i("TAG", "onCreateView: ");
        binding = FragmentIndexBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ipAddress = getView().findViewById(R.id.ip_address);
        portInfo = getView().findViewById(R.id.port_info);
        Log.i("TAG", "onViewCreated: ");
        onInitBtn();
        onInitData();
        //跳转到具体参数显示页面
        binding.temperatureCard.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(v);
            Bundle bundle = new Bundle();
            bundle.putString("info", "Temperature");
            navController.navigate(R.id.action_indexFragment_to_detailInfoFragment, bundle);
        });
        binding.humidityCard.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(v);
            Bundle bundle = new Bundle();
            bundle.putString("info", "Humidity");
            navController.navigate(R.id.action_indexFragment_to_detailInfoFragment, bundle);

        });
        binding.lightCard.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(v);
            Bundle bundle = new Bundle();
            bundle.putString("info", "Light");
            navController.navigate(R.id.action_indexFragment_to_detailInfoFragment, bundle);
        });
        binding.electricityCard.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(v);
            Bundle bundle = new Bundle();
            bundle.putString("info", "Electricity");
            navController.navigate(R.id.action_indexFragment_to_detailInfoFragment, bundle);
        });
        binding.machineCard.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(v);
            Bundle bundle = new Bundle();
            bundle.putString("info", "Machine");
            navController.navigate(R.id.action_indexFragment_to_detailInfoFragment, bundle);
        });
        binding.xAxisCard.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(v);
            Bundle bundle = new Bundle();
            bundle.putString("info", "XAxis");
            navController.navigate(R.id.action_indexFragment_to_detailInfoFragment, bundle);
        });
        binding.yAxisCard.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(v);
            Bundle bundle = new Bundle();
            bundle.putString("info", "YAxis");
            navController.navigate(R.id.action_indexFragment_to_detailInfoFragment, bundle);
        });
        binding.zAxisCard.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(v);
            Bundle bundle = new Bundle();
            bundle.putString("info", "ZAxis");
            navController.navigate(R.id.action_indexFragment_to_detailInfoFragment, bundle);
        });


    }

    /**
     * 设置按钮的点击事件
     *
     * @author: ChenYiXing
     * @date 2021/6/18/018 11:13
     **/
    private void onInitBtn() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        //连接按钮
        binding.connectInfo.setOnClickListener(v -> {
            builder.setTitle("连接设备");
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View view = inflater.inflate(R.layout.dialog_index, null);
            builder.setView(view);
            builder.setPositiveButton("连接", (dialog, which) -> {
                //刷新界面显示数据
                try {
                    try {
                        DataFresh.getInstance().getConnect("8.136.16.198", 5678);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    Log.i("info", e.toString());
                    Toast.makeText(getContext(), "连接异常", Toast.LENGTH_SHORT).show();
                }
                System.out.println(thread.getState());
//                thread.start();
                binding.connectInfo.setClickable(false);
                binding.connectInfo.setText("~ 连接成功");
                binding.connectInfo.setTextColor(Color.parseColor("#767676"));
            });
            builder.setNegativeButton("取消", (dialog, which) -> Log.i("info", "onClick: "));
            builder.create().show();
        });
    }

    private void onInitData() {
        OkHttpClient httpClient = new OkHttpClient();
        String url = "http://t.weather.itboy.net/api/weather/city/101120801";
        Request getRequest = new Request.Builder()
                .url(url)
                .get()
                .build();

        Call call = httpClient.newCall(getRequest);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //同步请求，要放到子线程执行
                    Response response = call.execute();
                    JsonRootBean jsonRootBean = JSON.parseObject(response.body().string(), JsonRootBean.class);
                    System.out.println(jsonRootBean.toString());
                    getActivity().runOnUiThread(() -> {
                        binding.wendu.setText(jsonRootBean.getData().getForecast().get(0).getLow() + "\n" + jsonRootBean.getData().getForecast().get(0).getHigh());
                        binding.pm25.setText(String.valueOf(jsonRootBean.getData().getPm25()));
                        binding.shidu.setText(jsonRootBean.getData().getShidu());
                        binding.textView3.setText(jsonRootBean.getData().getForecast().get(0).getNotice());
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onStop() {
        if (thread.isAlive()) thread.interrupt();
        Log.i("TAG", "onStop: ");
        super.onStop();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (thread.getState() == Thread.State.TERMINATED | thread.getState() == Thread.State.NEW) {
            thread = new Thread(() -> {
                while (true) {
                    getActivity().runOnUiThread(() -> {
                        binding.templateNum.setText(String.valueOf(DataFresh.getInstance().temperature));
                        binding.humidityNum.setText(String.valueOf(DataFresh.getInstance().humility));
                        binding.lightNum.setText(String.valueOf(DataFresh.getInstance().light));
                        binding.electricityNum.setText(String.valueOf(DataFresh.getInstance().electricity));
                        binding.machineNum.setText(String.valueOf(DataFresh.getInstance().machine));
                        binding.xAxisNum.setText(String.valueOf(DataFresh.getInstance().xAxis));
                        binding.yAxisNum.setText(String.valueOf(DataFresh.getInstance().yAxis));
                        binding.zAxisNum.setText(String.valueOf(DataFresh.getInstance().zAxis));
                    });
                    if (thread.isInterrupted()) break;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        break;
                    }
                }
            });
            thread.start();
        }
    }

    @Override
    public void onDestroyView() {
        Log.i("TAG", "onDestroyView: ");
        super.onDestroyView();
        binding = null;
    }
}