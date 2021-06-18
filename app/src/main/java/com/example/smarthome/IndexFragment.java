package com.example.smarthome;

import android.app.AlertDialog;
import android.content.Context;
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

import com.example.smarthome.databinding.FragmentIndexBinding;

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
                try {
                    Thread.sleep(900);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
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
            float[] data = new float[7];
            bundle.putFloatArray("data", data);
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
                        DataFresh.getInstance().getConnect("8.136.16.198", 8848);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    Log.i("info", e.toString());
                    Toast.makeText(getContext(), "连接异常", Toast.LENGTH_SHORT).show();
                }
                System.out.println(thread.getState());
                thread.start();
                binding.connectInfo.setClickable(false);
            });
            builder.setNegativeButton("取消", (dialog, which) -> Log.i("info", "onClick: "));
            builder.create().show();
        });
    }

    private void onInitData() {

    }

    @Override
    public void onStop() {
        if (thread.isAlive()) thread.interrupt();
        Log.i("TAG", "onStop: ");
        super.onStop();
    }

    @Override
    public void onStart() {
        Log.i("TAG", "onStart: " + thread.getState());
        super.onStart();
        System.out.println(thread.getState() == Thread.State.TERMINATED);
        if (thread.getState() == Thread.State.TERMINATED) {
            thread = new Thread(() -> {
                while (true) {
                    if (thread.isInterrupted()) break;
                    try {
                        Thread.sleep(900);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        break;
                    }
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
                }
            });
        }
    }

    @Override
    public void onDestroyView() {
        Log.i("TAG", "onDestroyView: ");
        super.onDestroyView();
        binding = null;
    }
}