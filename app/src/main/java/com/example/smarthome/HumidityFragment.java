package com.example.smarthome;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.smarthome.databinding.FragmentHumidityBinding;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HumidityFragment} factory method to
 * create an instance of this fragment.
 */
public class HumidityFragment extends Fragment {

    private FragmentHumidityBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentHumidityBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //无数据时显示
        binding.chart.setNoDataText("没有获取到数据哦~");
        binding.chart.setNoDataTextColor(Color.parseColor("#00bcef"));
        //缩放
        binding.chart.setScaleEnabled(true);
        //显示高亮
        binding.chart.setHighlightPerTapEnabled(true);
        //显示description
        binding.chart.getDescription().setEnabled(true);
        //显示边界
        binding.chart.setDrawBorders(true);
        //不显示图例
        binding.chart.getLegend().setEnabled(false);
        binding.chart.setHighlightPerDragEnabled(false);
        binding.chart.setExtraBottomOffset(10f);
        //获取X轴
        XAxis xAxis = binding.chart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(new String[]{"7s前", "6s前", "5s前", "4s前", "3s前", "2s前", "1s前", "现在"}));
        xAxis.setDrawGridLines(true);
        xAxis.setDrawAxisLine(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(16f);
        YAxis leftYAxis = binding.chart.getAxisLeft();
        leftYAxis.setAxisMinimum(0);
        leftYAxis.setEnabled(true);
        YAxis rightYAxis = binding.chart.getAxisRight();
        rightYAxis.setEnabled(false);
        //初始化显示数据
        List<Float> floats = new ArrayList<>();
        floats.add(6f);
        floats.add(7f);
        floats.add(1f);
        floats.add(4f);
        floats.add(8f);
        floats.add(4f);
        floats.add(6f);
        floats.add(2f);
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < floats.size(); i++) {
            entries.add(new Entry(i, floats.get(i)));
        }
        //将数据赋给数据集,一个数据集表示一条线
        LineDataSet lineDataSet = new LineDataSet(entries, "");
        //线颜色
        lineDataSet.setColor(Color.parseColor("#FFA2A2"));
        //线宽度
        lineDataSet.setLineWidth(1.0f);
        //显示圆点
        lineDataSet.setDrawCircles(true);
        //设置圆点颜色(外圈)
        lineDataSet.setCircleColor(Color.parseColor("#008CFF"));
        //设置圆点填充颜色
        lineDataSet.setCircleHoleColor(Color.parseColor("#008CFF"));
        //设置线条为平滑曲线
        lineDataSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        //设置直线图填充
        lineDataSet.setDrawFilled(true);
        //设置填充颜色
        lineDataSet.setFillColor(Color.parseColor("#FFA2A2"));
        LineData lineData = new LineData(lineDataSet);
        //不显示曲线点的具体数值
//        lineData.setDrawValues(false);
        binding.chart.setData(lineData);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}