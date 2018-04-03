package com.yunus.remember.activity.mine;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.yunus.activity.BaseActivity;
import com.yunus.remember.R;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.view.LineChartView;

public class ProgressActivity extends BaseActivity {
    private LineChartView lineChart;
    private LineChartData lineData;
    private int numOfLine;
    private int numOfPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        lineChart = (LineChartView) findViewById(R.id.line_chart);
        lineChart.setZoomEnabled(false);
        lineChart.setInteractive(false);
        lineChart.setValueSelectionEnabled(true);
        lineChart.setLineChartData(lineData);

        //设置节点、X、Y轴属性及添加数据：
        List<PointValue> linePointValues = new ArrayList<>();// 节点数据结合
        Axis axisY = new Axis().setHasLines(true);// Y轴属性
        Axis axisX = new Axis();// X轴属性
        ArrayList<AxisValue> axisValuesX = new ArrayList<>();//定义X轴刻度值的数据集合
        ArrayList<AxisValue> axisValuesY = new ArrayList<>();//定义Y轴刻度值的数据集合
        axisX.setValues(axisValuesX);//为X轴显示的刻度值设置数据集合
        axisX.setLineColor(Color.BLACK);// 设置X轴轴线颜色
        axisY.setLineColor(Color.BLACK);// 设置Y轴轴线颜色
        axisX.setHasLines(true);// 是否显示X轴网格线
        axisY.setHasLines(true);// 是否显示Y轴网格线
        axisX.setHasSeparationLine(true);// 设置是否有分割线
        axisX.setInside(true);// 设置X轴文字是否在X轴内部
        axisY.setInside(true);
        for (int j = 0; j < 5; j++) {//循环为节点、X、Y轴添加数据
            linePointValues.add(new PointValue(j, j * 10));// 添加节点数据
            axisValuesY.add(new AxisValue(j).setValue(j));// 添加Y轴显示的刻度值
            axisValuesX.add(new AxisValue(j).setValue(j).setLabel(""));// 添加X轴显示的刻度值

        }

        //设置折线Line的属性：
        List<Line> lines = new ArrayList<>();//定义线的集合
        Line line = new Line(linePointValues);//将值设置给折线
        line.setColor(Color.GREEN);// 设置折线颜色
        line.setStrokeWidth(1);// 设置折线宽度
        line.setFilled(true);// 设置折线覆盖区域是否填充
        line.setCubic(false);// 是否设置为立体的
        line.setPointColor(Color.BLUE);// 设置节点颜色
        line.setPointRadius(2);// 设置节点半径
        line.setHasLabels(false);// 是否显示节点数据
        line.setHasLines(true);// 是否显示折线
        line.setHasPoints(true);// 是否显示节点
        line.setShape(ValueShape.CIRCLE);// 节点图形样式 DIAMOND菱形、SQUARE方形、CIRCLE圆形
        line.setHasLabelsOnlyForSelected(true);// 隐藏数据，触摸可以显示
        lines.add(line);// 将数据集合添加线

        //设置LineChartData属性及为chart设置数据：
        LineChartData chartData = new LineChartData(lines);//将线的集合设置为折线图的数据
        chartData.setAxisYLeft(axisY);// 将Y轴属性设置到左边
        chartData.setAxisXBottom(axisX);// 将X轴属性设置到底部
        chartData.setBaseValue(20);// 设置反向覆盖区域颜色
        chartData.setValueLabelBackgroundAuto(false);// 设置数据背景是否跟随节点颜色
        chartData.setValueLabelBackgroundColor(Color.YELLOW);// 设置数据背景颜色
        chartData.setValueLabelBackgroundEnabled(false);// 设置是否有数据背景
        chartData.setValueLabelsTextColor(Color.BLACK);// 设置数据文字颜色
        chartData.setValueLabelTextSize(15);// 设置数据文字大小
        chartData.setValueLabelTypeface(Typeface.MONOSPACE);// 设置数据文字样式
        lineChart.setLineChartData(chartData);//最后为图表设置数据，数据类型为LineChartData
    }
}
