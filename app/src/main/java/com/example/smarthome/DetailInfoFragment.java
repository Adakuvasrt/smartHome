package com.example.smarthome;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.smarthome.databinding.FragmentDetailInfoBinding;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailInfoFragment} factory method to
 * create an instance of this fragment.
 */
public class DetailInfoFragment extends Fragment {


    private FragmentDetailInfoBinding binding;
    private Thread thread;
    String info;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentDetailInfoBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle arguments = getArguments();
        assert arguments != null;
        info = arguments.getString("info");
        System.out.println(info);
        binding.lineChart.setNoDataText("没有获取到数据哦~");
        binding.lineChart.setNoDataTextColor(Color.parseColor("#487cfb"));
        //缩放
        binding.lineChart.setScaleEnabled(true);
        //显示高亮
        binding.lineChart.setHighlightPerTapEnabled(true);
        //显示边界
        binding.lineChart.setDrawBorders(true);
        //不显示图例
        binding.lineChart.getLegend().setEnabled(false);
        binding.lineChart.setHighlightPerDragEnabled(true);
        binding.lineChart.setExtraBottomOffset(10f);
        // 把这个设为true来绘制网格背景，如果false则不绘制
        binding.lineChart.setDrawGridBackground(true);

        binding.lineChart.setBackgroundColor(Color.parseColor("#ffffff"));
        binding.lineChart.setGridBackgroundColor(Color.parseColor("#ffffff"));

        binding.lineChart.setTouchEnabled(true); // 设置是否可以触摸
        binding.lineChart.setDragEnabled(true);// 是否可以拖拽
        binding.lineChart.setScaleEnabled(true);// 是否可以缩放
        // 把这个设置为false，禁用所有手势和图表上的触摸，默认：true
        binding.lineChart.setTouchEnabled(true);
        binding.lineChart.animateY(1000, Easing.EaseInOutQuart);//设置动画
        // 设置图标拖动为允许
        binding.lineChart.setDragEnabled(true);
        // 设置图表缩放为允许
        binding.lineChart.setScaleEnabled(true);
        binding.lineChart.setScaleXEnabled(true);
        binding.lineChart.setScaleYEnabled(true);
        Description description = new Description();
        description.setText(info);
        description.setTextSize(18f);
        description.setYOffset(10f);
        description.setXOffset(10f);
        binding.lineChart.setDescription(description);
        //显示description
        binding.lineChart.getDescription().setEnabled(true);
        // 挤压缩放设置为允许，即X轴和Y轴会成比例缩放，如果设置为false，则变成单独缩放
        binding.lineChart.setPinchZoom(false);//设置是否启用y轴自动缩放的标志。如果启用，y轴将自动调整到当前x轴范围的最小和最大y值，只要视图改变。这对于显示金融数据的图表尤其有用
        binding.lineChart.setAutoScaleMinMaxEnabled(true);
        //获取X轴
//        XAxis xAxis = binding.lineChart.getXAxis();
//        xAxis.setValueFormatter(new IndexAxisValueFormatter(new String[]{"7s前", "6s前", "5s前", "4s前", "3s前", "2s前", "1s前", "现在"}));
//        xAxis.setDrawGridLines(true);
//        xAxis.setDrawAxisLine(false);
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setTextSize(17f);
        YAxis leftYAxis = binding.lineChart.getAxisLeft();
        leftYAxis.setAxisMinimum(0);
        leftYAxis.setEnabled(true);
        YAxis rightYAxis = binding.lineChart.getAxisRight();
        rightYAxis.setEnabled(false);
        //初始化显示数据
        List<Float> floats = List.of(0f);
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < floats.size(); i++) {
            entries.add(new Entry(i, floats.get(i)));
        }
        //将数据赋给数据集,一个数据集表示一条线
        LineDataSet lineDataSet = new LineDataSet(entries, "");
        //线颜色
        lineDataSet.setColor(Color.parseColor("#487cfb"));
        //线宽度
        lineDataSet.setLineWidth(1.0f);
        //数值上的字体大小
        lineDataSet.setValueTextSize(14f);
        //显示圆点
        lineDataSet.setDrawCircles(true);
        lineDataSet.setHighLightColor(Color.parseColor("#487cfb")); // 设置点击某个点时，横竖两条线的颜色
        lineDataSet.setHighlightLineWidth(1.2f); //设置高亮线宽度
        //设置圆点颜色(外圈)
        lineDataSet.setCircleColor(Color.parseColor("#008CFF"));
        //设置圆点填充颜色
        lineDataSet.setCircleHoleColor(Color.parseColor("#008CFF"));
        //设置线条为平滑曲线
        lineDataSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        //设置直线图填充
        lineDataSet.setDrawFilled(true);
        //设置填充颜色
        lineDataSet.setFillColor(Color.parseColor("#487cfb"));
        LineData lineData = new LineData(lineDataSet);
        //不显示曲线点的具体数值
//        lineData.setDrawValues(false);
        binding.lineChart.setData(lineData);
        thread = new Thread(() -> {
            int x = 0;
            while (!thread.isInterrupted()) {

                LineData lineData1 = binding.lineChart.getLineData();
                XAxis xxAxis = binding.lineChart.getXAxis();
                final ILineDataSet dataSetByIndex = lineData.getDataSetByIndex(0);
                if (dataSetByIndex.getEntryCount() > 7) {
//                    dataSetByIndex.removeEntry(0);
                    binding.lineChart.setVisibleXRangeMaximum(8);
                    binding.lineChart.moveViewToX(dataSetByIndex.getEntryCount() - 8);
                    switch (info) {
                        case "Temperature":
                            dataSetByIndex.addEntry(new Entry(x++, DataFresh.getInstance().temperature));
                            break;
                        case "Humidity":
                            dataSetByIndex.addEntry(new Entry(x++, DataFresh.getInstance().humility));
                            break;
                        case "Light":
                            dataSetByIndex.addEntry(new Entry(x++, DataFresh.getInstance().light));
                            break;
                        case "Electricity":
                            dataSetByIndex.addEntry(new Entry(x++, DataFresh.getInstance().electricity));
                            break;
                        case "Machine":
                            dataSetByIndex.addEntry(new Entry(x++, DataFresh.getInstance().machine));
                            break;
                        case "XAxis":
                            dataSetByIndex.addEntry(new Entry(x++, DataFresh.getInstance().xAxis));
                            break;
                        case "YAxis":
                            dataSetByIndex.addEntry(new Entry(x++, DataFresh.getInstance().yAxis));
                            break;
                        case "ZAxis":
                            dataSetByIndex.addEntry(new Entry(x++, DataFresh.getInstance().zAxis));
                            break;
                    }

                } else {
                    switch (info) {
                        case "Temperature":
                            dataSetByIndex.addEntry(new Entry(x++, DataFresh.getInstance().temperature));
                            break;
                        case "Humidity":
                            dataSetByIndex.addEntry(new Entry(x++, DataFresh.getInstance().humility));
                            break;
                        case "Light":
                            dataSetByIndex.addEntry(new Entry(x++, DataFresh.getInstance().light));
                            break;
                        case "Electricity":
                            dataSetByIndex.addEntry(new Entry(x++, DataFresh.getInstance().electricity));
                            break;
                        case "Machine":
                            dataSetByIndex.addEntry(new Entry(x++, DataFresh.getInstance().machine));
                            break;
                        case "XAxis":
                            dataSetByIndex.addEntry(new Entry(x++, DataFresh.getInstance().xAxis));
                            break;
                        case "YAxis":
                            dataSetByIndex.addEntry(new Entry(x++, DataFresh.getInstance().yAxis));
                            break;
                        case "ZAxis":
                            dataSetByIndex.addEntry(new Entry(x++, DataFresh.getInstance().zAxis));
                            break;
                    }
                }
                binding.lineChart.notifyDataSetChanged();
                binding.lineChart.invalidate();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return;
                }
            }

        });
        thread.start();
//        binding.lineChart.invalidate(); // 刷新
    }

    @Override
    public void onDestroyView() {
        thread.interrupt();
        binding = null;
        super.onDestroyView();

    }
}