package com.yunus.remember.activity.mine;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.yunus.activity.BaseActivity;
import com.yunus.remember.R;
import com.yunus.remember.entity.SevenDaysReview;
import com.yunus.remember.entity.Word;
import com.yunus.remember.utils.StorageUtil;

import org.litepal.crud.DataSupport;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.ComboLineColumnChartData;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;
import lecho.lib.hellocharts.view.ComboLineColumnChartView;
import lecho.lib.hellocharts.view.LineChartView;

public class ProgressActivity extends BaseActivity {
    Calendar c;
    List<SevenDaysReview> reviews;
    private int numberOfLines = 1;
    private int maxNumberOfLines = 2;
    private int numberOfPoints = 7;
    float[][] lineNumbers = new float[maxNumberOfLines][numberOfPoints];
    float[][] comboNumbers = new float[maxNumberOfLines][numberOfPoints];
    private boolean hasAxes = true;
    private boolean hasAxesNames = false;
    private boolean hasLines = true;
    private boolean hasPoints = true;
    private ValueShape shape = ValueShape.CIRCLE;
    private boolean isFilled = true;
    private boolean hasLabels = false;
    private boolean isCubic = false;
    private boolean hasLabelForSelected = true;
    private String[] dates = new String[7];
    private LineChartView lineChart;
    private ColumnChartView columnChart;
    private ComboLineColumnChartView comboChart;

    private TextView all;
    private TextView had;
    private TextView studying;
    private TextView newWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        Toolbar toolbar = findViewById(R.id.progress_toolbar);
        toolbar.setTitle("进步曲线");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        all = findViewById(R.id.progress_all);
        had = findViewById(R.id.progress_had);
        studying = findViewById(R.id.progress_ing);
        newWord = findViewById(R.id.progress_new);

        initDates();

        lineChart = (LineChartView) findViewById(R.id.line_chart);
        generateLineValues();
        generateLineData();
        setView();
        resetLineViewport();

        columnChart = (ColumnChartView) findViewById(R.id.column_chart);
        generateColumnLineData();
        resetColumnViewport();

        comboChart = (ComboLineColumnChartView) findViewById(R.id.combo_chart);
        generateComboValues();
        generateComboData();
    }

    private void initDates() {
        c = Calendar.getInstance();
        for (int i = 0; i < numberOfPoints; ++i) {
            SimpleDateFormat sdf = new SimpleDateFormat("MM.dd", Locale.getDefault());
            dates[numberOfPoints - i - 1] = sdf.format(c.getTime());
            c.add(Calendar.DATE, -1);
        }
        reviews = DataSupport.findAll(SevenDaysReview.class);

        int allNum = DataSupport.count(Word.class);
        int hadNum = DataSupport.where("level < 1").count(Word.class);
        all.setText("" + allNum);
        had.setText("" + hadNum);
        studying.setText("" + (allNum - hadNum));
        newWord.setText(StorageUtil.getInt(ProgressActivity.this, StorageUtil
                .TODAY_REAL_NEW_NUM, 0) + "");
    }

    private void generateLineValues() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault());
        c = Calendar.getInstance();
        c.add(Calendar.DATE, -6);
        SevenDaysReview review;
        int i = 0;
        for (int j = 0; j < numberOfPoints; ++j) {
            if (reviews.isEmpty()) {
                lineNumbers[0][j] = 0;
                lineNumbers[1][j] = 0;
            } else {
                if (i < reviews.size()) {
                    review = reviews.get(i);
                    if (review.getTheDate().equals(sdf.format(c.getTime()))) {
                        i++;
                        lineNumbers[0][j] = (float) review.getAllWordsCount();
                        lineNumbers[1][j] = (float) review.getAllHadCount();
                    } else {
                        if (j == 0) {
                            lineNumbers[0][j] = 0;
                            lineNumbers[1][j] = 0;
                        } else {
                            lineNumbers[0][j] = lineNumbers[0][j - 1];
                            lineNumbers[1][j] = lineNumbers[0][j - 1];
                        }
                    }
                } else {
                    lineNumbers[0][j] = lineNumbers[0][j - 1];
                    lineNumbers[1][j] = lineNumbers[0][j - 1];
                }
            }
            c.add(Calendar.DATE, 1);
        }

    }

    private void generateLineData() {

        List<Line> lines = new ArrayList<Line>();
        for (int i = 0; i < maxNumberOfLines; ++i) {

            List<PointValue> values = new ArrayList<PointValue>();
            for (int j = 0; j < numberOfPoints; ++j) {
                values.add(new PointValue(j, lineNumbers[i][j]));
            }

            Line line = new Line(values);
            line.setColor(ChartUtils.COLORS[i]);// 设置折线颜色
            line.setStrokeWidth(1);// 设置折线宽度
            line.setShape(shape);// 节点图形样式 DIAMOND菱形、SQUARE方形、CIRCLE圆形
            line.setCubic(isCubic);// 是否设置为立体的
            line.setFilled(isFilled);// 设置折线覆盖区域是否填充
            line.setPointColor(ChartUtils.COLOR_GREEN);// 设置节点颜色
            line.setPointRadius(2);// 设置节点半径
            line.setHasLabels(hasLabels);// 是否显示节点数据
            line.setHasLabelsOnlyForSelected(hasLabelForSelected);// 隐藏数据，触摸可以显示
            line.setHasLines(hasLines);// 是否显示折线
            line.setHasPoints(hasPoints);// 是否显示节点
            lines.add(line);
        }
        LineChartData lineData = new LineChartData(lines);

        if (hasAxes) {
            Axis axisX = new Axis().setHasLines(true);// 是否显示X轴网格线
            Axis axisY = new Axis().setHasLines(true);
            axisX.setLineColor(Color.BLACK);// 设置X轴轴线颜色
            axisY.setLineColor(Color.BLACK);// 设置Y轴轴线颜色
            axisX.setHasSeparationLine(false);// 设置是否有分割线
            axisY.setHasSeparationLine(false);
            axisX.setInside(false);// 设置X轴文字是否在X轴内部
            axisY.setInside(false);
            ArrayList<AxisValue> axisValuesX = new ArrayList<>();//定义X轴刻度值的数据集合
            for (int j = 0; j < numberOfPoints; j++) {//循环为节点、X、Y轴添加数据
                axisValuesX.add(new AxisValue(j).setLabel(dates[j]));// 添加X轴显示的刻度值
            }
            axisX.setValues(axisValuesX);//为X轴显示的刻度值设置数据集合
            if (hasAxesNames) {
                axisX.setName("");
                axisY.setName("");
            }
            lineData.setAxisXBottom(axisX);
            lineData.setAxisYLeft(axisY);
        } else {
            lineData.setAxisXBottom(null);
            lineData.setAxisYLeft(null);
        }
        lineData.setBaseValue(Float.NEGATIVE_INFINITY);// 设置反向覆盖区域颜色
//        chartData.setValueLabelBackgroundAuto(false);// 设置数据背景是否跟随节点颜色
//        chartData.setValueLabelBackgroundColor(Color.YELLOW);// 设置数据背景颜色
//        chartData.setValueLabelBackgroundEnabled(false);// 设置是否有数据背景
//        chartData.setValueLabelsTextColor(Color.BLACK);// 设置数据文字颜色
//        chartData.setValueLabelTextSize(15);// 设置数据文字大小
//        chartData.setValueLabelTypeface(Typeface.MONOSPACE);// 设置数据文字样式
        lineChart.setLineChartData(lineData);

    }

    private void setView() {
        // Disable viewport recalculations, see toggleCubic() method for more info.
        lineChart.setViewportCalculationEnabled(false);

        lineChart.setZoomEnabled(false);//缩放
        lineChart.setInteractive(true);//交互
        lineChart.setValueSelectionEnabled(false);//值选择

    }

    private void resetLineViewport() {
        // Reset viewport height range to (0,100)
        Viewport v = new Viewport(lineChart.getMaximumViewport());
        v.bottom = 0;
        v.top = StorageUtil.getInt(ProgressActivity.this, StorageUtil.WORDS_NUM, 100) + 20;
        v.left = -0.2f;
        v.right = numberOfPoints - 0.8f;
        lineChart.setMaximumViewport(v);
        lineChart.setCurrentViewport(v);
    }

    private void generateColumnLineData() {
        int numSubcolumns = 1;
        // Column can have many subcolumns, here by default I use 1 subcolumn in each of 8 columns.
        List<Column> columns = new ArrayList<>();
        List<SubcolumnValue> values;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault());
        c = Calendar.getInstance();
        c.add(Calendar.DATE, -6);
        SevenDaysReview review;
        int k = 0;
        int num = 0;
        for (int i = 0; i < numberOfPoints; ++i) {
            if (reviews.isEmpty()) {
                num = 0;
            } else {
                if (k < reviews.size()) {
                    review = reviews.get(k);
                    if (review.getTheDate().equals(sdf.format(c.getTime()))) {
                        k++;
                        num = review.getTodayStudiedCount();
                    } else {
                        if (i == 0) {
                            num = 0;
                        }
                    }
                }
            }
            c.add(Calendar.DATE, 1);
            values = new ArrayList<>();
            for (int j = 0; j < numSubcolumns; ++j) {
                values.add(new SubcolumnValue((float) num, ChartUtils.COLOR_BLUE));
            }
            Column column = new Column(values);
            column.setHasLabels(hasLabels);
            column.setHasLabelsOnlyForSelected(hasLabelForSelected);
            columns.add(column);
        }

        ColumnChartData columnData = new ColumnChartData(columns);

        if (hasAxes) {
            Axis axisX = new Axis();
            Axis axisY = new Axis().setHasLines(true);
            ArrayList<AxisValue> axisValuesX = new ArrayList<>();//定义X轴刻度值的数据集合
            for (int j = 0; j < numberOfPoints; j++) {//循环为节点、X、Y轴添加数据
                axisValuesX.add(new AxisValue(j).setLabel(dates[j]));// 添加X轴显示的刻度值
            }
            axisX.setValues(axisValuesX);//为X轴显示的刻度值设置数据集合
            if (hasAxesNames) {
                axisX.setName("Axis X");
                axisY.setName("Axis Y");
            }
            columnData.setAxisXBottom(axisX);
            columnData.setAxisYLeft(axisY);
        } else {
            columnData.setAxisXBottom(null);
            columnData.setAxisYLeft(null);
        }

        columnChart.setColumnChartData(columnData);
    }

    private void resetColumnViewport() {
        // Reset viewport height range to (0,100)
        Viewport v = new Viewport(columnChart.getMaximumViewport());
        v.bottom = 0;
        v.top = StorageUtil.getInt(ProgressActivity.this, StorageUtil.TODAY_NUM, 100);
        v.left = -0.5f;
        v.right = numberOfPoints - 0.5f;
        columnChart.setMaximumViewport(v);
        columnChart.setCurrentViewport(v);
    }

    private void generateComboValues() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault());
        c = Calendar.getInstance();
        c.add(Calendar.DATE, -6);
        SevenDaysReview review;
        int i = 0;
        for (int j = 0; j < numberOfPoints; ++j) {
            if (reviews.isEmpty()) {
                comboNumbers[0][j] = 0;
            } else {
                if (i < reviews.size()) {
                    review = reviews.get(i);
                    if (review.getTheDate().equals(sdf.format(c.getTime()))) {
                        i++;
                        comboNumbers[0][j] = (float) review.getStudiedTime();
                    } else {
                        if (j == 0) {
                            comboNumbers[0][j] = 0;
                        } else {
                            comboNumbers[0][j] = comboNumbers[0][j - 1];
                        }
                    }
                } else {
                    comboNumbers[0][j] = comboNumbers[0][j - 1];
                }
            }
            c.add(Calendar.DATE, 1);
        }
    }

    private void generateComboData() {
        // Chart looks the best when line data and column data have similar maximum viewports.
        ComboLineColumnChartData comboData = new ComboLineColumnChartData(generateComboColumnData
                (), generateComboLineData());

        if (hasAxes) {
            Axis axisX = new Axis().setHasLines(true);
            Axis axisY = new Axis().setHasLines(true);
            if (hasAxesNames) {
                axisX.setName("Axis X");
                axisY.setName("Axis Y");
            }
            comboData.setAxisXBottom(axisX);
            comboData.setAxisYLeft(axisY);
        } else {
            comboData.setAxisXBottom(null);
            comboData.setAxisYLeft(null);
        }

        comboChart.setComboLineColumnChartData(comboData);
    }

    private LineChartData generateComboLineData() {

        List<Line> lines = new ArrayList<Line>();
        for (int i = 0; i < numberOfLines; ++i) {

            List<PointValue> values = new ArrayList<PointValue>();
            for (int j = 0; j < numberOfPoints; ++j) {
                values.add(new PointValue(j, comboNumbers[i][j]));
            }

            Line line = new Line(values);
            line.setFilled(isFilled);// 设置折线覆盖区域是否填充
            line.setColor(ChartUtils.COLORS[i]);
            line.setCubic(isCubic);
            line.setHasLabels(hasLabels);
            line.setHasLines(hasLines);
            line.setHasPoints(hasPoints);
            lines.add(line);
        }
        return new LineChartData(lines);
    }

    private ColumnChartData generateComboColumnData() {
        int numSubcolumns = 1;
        // Column can have many subcolumns, here by default I use 1 subcolumn in each of 8 columns.
        List<Column> columns = new ArrayList<Column>();
        List<SubcolumnValue> values;
        for (int i = 0; i < numberOfPoints; ++i) {
            values = new ArrayList<SubcolumnValue>();
            for (int j = 0; j < numSubcolumns; ++j) {
                values.add(new SubcolumnValue(comboNumbers[j][i], ChartUtils.COLOR_GREEN));
            }
            columns.add(new Column(values));
        }
        return new ColumnChartData(columns);
    }
}
