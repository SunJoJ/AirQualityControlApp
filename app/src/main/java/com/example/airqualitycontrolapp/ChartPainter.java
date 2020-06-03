package com.example.airqualitycontrolapp;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.example.airqualitycontrolapp.models.Value;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.List;

public class ChartPainter {

    private static final int S02_VERY_GOOD = 50;
    private static final int S02_GOOD = 100;
    private static final int S02_MODERATE = 200;
    private static final int S02_PASSABLE = 350;
    private static final int S02_BAD = 500;
    private static final int S02_VERY_BAD = 1000;

    private static final int N02_VERY_GOOD = 40;
    private static final int N02_GOOD = 100;
    private static final int N02_MODERATE = 150;
    private static final int N02_PASSABLE = 200;
    private static final int N02_BAD = 400;
    private static final int N02_VERY_BAD = 1000;

    private static final int CO_VERY_GOOD = 2000;
    private static final int CO_GOOD = 6000;
    private static final int CO_MODERATE = 10000;
    private static final int CO_PASSABLE = 14000;
    private static final int CO_BAD = 20000;
    private static final int CO_VERY_BAD = 100000;

    private static final int PM10_VERY_GOOD = 20;
    private static final int PM10_GOOD = 60;
    private static final int PM10_MODERATE = 100;
    private static final int PM10_PASSABLE = 140;
    private static final int PM10_BAD = 200;
    private static final int PM10_VERY_BAD = 1000;

    private static final int PM25_VERY_GOOD = 12;
    private static final int PM25_GOOD = 36;
    private static final int PM25_MODERATE = 60;
    private static final int PM25_PASSABLE = 84;
    private static final int PM25_BAD = 120;
    private static final int PM25_VERY_BAD = 1000;

    private static final int O3_VERY_GOOD = 24;
    private static final int O3_GOOD = 70;
    private static final int O3_MODERATE = 120;
    private static final int O3_PASSABLE = 160;
    private static final int O3_BAD = 240;
    private static final int O3_VERY_BAD = 1000;

    private static final int C6H6_VERY_GOOD = 5;
    private static final int C6H6_GOOD = 10;
    private static final int C6H6_MODERATE = 15;
    private static final int C6H6_PASSABLE = 20;
    private static final int C6H6_BAD = 50;
    private static final int C6H6_VERY_BAD = 1000;



    public static BarData paintChart(List<Value> values, String key, Context context, BarChart barChart) {

        List<IBarDataSet> bars = new ArrayList<IBarDataSet>();
        final String[] time = new String[values.size()];
        for(int i = values.size()-1, j = 0; i > 0; i--, j++) {
            ArrayList<BarEntry> entries = new ArrayList<>();
            int val = (int) values.get(i).getValue();
            entries.add(new BarEntry(j, val));
            time[j] = values.get(i).getDate().split(" ")[1];

            switch (key) {
                case "SO2":
                    if(isBetween(val, 0, S02_VERY_GOOD)) {
                        BarDataSet dataSet = new BarDataSet(entries, "Green");
                        int color = ContextCompat.getColor(context, R.color.darkGreen);
                        dataSet.setColor(color);
                        bars.add(dataSet);
                    } else if(isBetween(val, S02_VERY_GOOD + 1, S02_GOOD )) {
                        BarDataSet dataSet = new BarDataSet(entries, "Green");
                        int color = ContextCompat.getColor(context, R.color.lightGreen);
                        dataSet.setColor(color);
                        bars.add(dataSet);
                    } else if(isBetween(val, S02_GOOD + 1, S02_MODERATE )) {
                        BarDataSet dataSet = new BarDataSet(entries, "Yellow");
                        int color = ContextCompat.getColor(context, R.color.yellow);
                        dataSet.setColor(color);
                        bars.add(dataSet);
                    } else if(isBetween(val, S02_MODERATE + 1, S02_PASSABLE )) {
                        BarDataSet dataSet = new BarDataSet(entries, "Yellow");
                        int color = ContextCompat.getColor(context, R.color.orange);
                        dataSet.setColor(color);
                        bars.add(dataSet);
                    } else if(isBetween(val, S02_PASSABLE + 1, S02_BAD )) {
                        BarDataSet dataSet = new BarDataSet(entries, "Red");
                        int color = ContextCompat.getColor(context, R.color.red);
                        dataSet.setColor(color);
                        bars.add(dataSet);
                    } else if(isBetween(val, S02_BAD + 1, S02_VERY_BAD )) {
                        BarDataSet dataSet = new BarDataSet(entries, "Red");
                        int color = ContextCompat.getColor(context, R.color.darkRed);
                        dataSet.setColor(color);
                        bars.add(dataSet);
                    }
                    break;
                case "NO2":
                    if(isBetween(val, 0, N02_VERY_GOOD)) {
                        BarDataSet dataSet = new BarDataSet(entries, "Green");
                        int color = ContextCompat.getColor(context, R.color.darkGreen);
                        dataSet.setColor(color);
                        bars.add(dataSet);
                    } else if(isBetween(val, N02_VERY_GOOD + 1, N02_GOOD )) {
                        BarDataSet dataSet = new BarDataSet(entries, "Green");
                        int color = ContextCompat.getColor(context, R.color.lightGreen);
                        dataSet.setColor(color);
                        bars.add(dataSet);
                    } else if(isBetween(val, N02_GOOD + 1, N02_MODERATE )) {
                        BarDataSet dataSet = new BarDataSet(entries, "Yellow");
                        int color = ContextCompat.getColor(context, R.color.yellow);
                        dataSet.setColor(color);
                        bars.add(dataSet);
                    } else if(isBetween(val, N02_MODERATE + 1, N02_PASSABLE )) {
                        BarDataSet dataSet = new BarDataSet(entries, "Yellow");
                        int color = ContextCompat.getColor(context, R.color.orange);
                        dataSet.setColor(color);
                        bars.add(dataSet);
                    } else if(isBetween(val, N02_PASSABLE + 1, N02_BAD )) {
                        BarDataSet dataSet = new BarDataSet(entries, "Red");
                        int color = ContextCompat.getColor(context, R.color.red);
                        dataSet.setColor(color);
                        bars.add(dataSet);
                    } else if(isBetween(val, N02_BAD + 1, N02_VERY_BAD )) {
                        BarDataSet dataSet = new BarDataSet(entries, "Red");
                        int color = ContextCompat.getColor(context, R.color.darkRed);
                        dataSet.setColor(color);
                        bars.add(dataSet);
                    }
                    break;
                case "CO":
                    if(isBetween(val, 0, CO_VERY_GOOD)) {
                        BarDataSet dataSet = new BarDataSet(entries, "Green");
                        int color = ContextCompat.getColor(context, R.color.darkGreen);
                        dataSet.setColor(color);
                        bars.add(dataSet);
                    } else if(isBetween(val, CO_VERY_GOOD + 1, CO_GOOD )) {
                        BarDataSet dataSet = new BarDataSet(entries, "Green");
                        int color = ContextCompat.getColor(context, R.color.lightGreen);
                        dataSet.setColor(color);
                        bars.add(dataSet);
                    } else if(isBetween(val, CO_GOOD + 1, CO_MODERATE )) {
                        BarDataSet dataSet = new BarDataSet(entries, "Yellow");
                        int color = ContextCompat.getColor(context, R.color.yellow);
                        dataSet.setColor(color);
                        bars.add(dataSet);
                    } else if(isBetween(val, CO_MODERATE + 1, CO_PASSABLE )) {
                        BarDataSet dataSet = new BarDataSet(entries, "Yellow");
                        int color = ContextCompat.getColor(context, R.color.orange);
                        dataSet.setColor(color);
                        bars.add(dataSet);
                    } else if(isBetween(val, CO_PASSABLE + 1, CO_BAD )) {
                        BarDataSet dataSet = new BarDataSet(entries, "Red");
                        int color = ContextCompat.getColor(context, R.color.red);
                        dataSet.setColor(color);
                        bars.add(dataSet);
                    } else if(isBetween(val, CO_BAD + 1, CO_VERY_BAD )) {
                        BarDataSet dataSet = new BarDataSet(entries, "Red");
                        int color = ContextCompat.getColor(context, R.color.darkRed);
                        dataSet.setColor(color);
                        bars.add(dataSet);
                    }
                    break;
                case "PM10":
                    if(isBetween(val, 0, PM10_VERY_GOOD)) {
                        BarDataSet dataSet = new BarDataSet(entries, "Green");
                        int color = ContextCompat.getColor(context, R.color.darkGreen);
                        dataSet.setColor(color);
                        bars.add(dataSet);
                    } else if(isBetween(val, PM10_VERY_GOOD + 1, PM10_GOOD )) {
                        BarDataSet dataSet = new BarDataSet(entries, "Green");
                        int color = ContextCompat.getColor(context, R.color.lightGreen);
                        dataSet.setColor(color);
                        bars.add(dataSet);
                    } else if(isBetween(val, PM10_GOOD + 1, PM10_MODERATE )) {
                        BarDataSet dataSet = new BarDataSet(entries, "Yellow");
                        int color = ContextCompat.getColor(context, R.color.yellow);
                        dataSet.setColor(color);
                        bars.add(dataSet);
                    } else if(isBetween(val, PM10_MODERATE + 1, PM10_PASSABLE )) {
                        BarDataSet dataSet = new BarDataSet(entries, "Yellow");
                        int color = ContextCompat.getColor(context, R.color.orange);
                        dataSet.setColor(color);
                        bars.add(dataSet);
                    } else if(isBetween(val, PM10_PASSABLE + 1, PM10_BAD )) {
                        BarDataSet dataSet = new BarDataSet(entries, "Red");
                        int color = ContextCompat.getColor(context, R.color.red);
                        dataSet.setColor(color);
                        bars.add(dataSet);
                    } else if(isBetween(val, PM10_BAD + 1, PM10_VERY_BAD )) {
                        BarDataSet dataSet = new BarDataSet(entries, "Red");
                        int color = ContextCompat.getColor(context, R.color.darkRed);
                        dataSet.setColor(color);;
                        bars.add(dataSet);
                    }
                    break;
                case "PM2.5":
                    if(isBetween(val, 0, PM25_VERY_GOOD)) {
                        BarDataSet dataSet = new BarDataSet(entries, "Green");
                        int color = ContextCompat.getColor(context, R.color.darkGreen);
                        dataSet.setColor(color);
                        bars.add(dataSet);
                    } else if(isBetween(val, PM25_VERY_GOOD + 1, PM25_GOOD )) {
                        BarDataSet dataSet = new BarDataSet(entries, "Green");
                        int color = ContextCompat.getColor(context, R.color.lightGreen);
                        dataSet.setColor(color);
                        bars.add(dataSet);
                    } else if(isBetween(val, PM25_GOOD + 1, PM25_MODERATE )) {
                        BarDataSet dataSet = new BarDataSet(entries, "Yellow");
                        int color = ContextCompat.getColor(context, R.color.yellow);
                        dataSet.setColor(color);
                        bars.add(dataSet);
                    } else if(isBetween(val, PM25_MODERATE + 1, PM25_PASSABLE )) {
                        BarDataSet dataSet = new BarDataSet(entries, "Yellow");
                        int color = ContextCompat.getColor(context, R.color.orange);
                        dataSet.setColor(color);
                        bars.add(dataSet);
                    } else if(isBetween(val, PM25_PASSABLE + 1, PM25_BAD )) {
                        BarDataSet dataSet = new BarDataSet(entries, "Red");
                        int color = ContextCompat.getColor(context, R.color.red);
                        dataSet.setColor(color);
                        bars.add(dataSet);
                    } else if(isBetween(val, PM25_BAD + 1, PM25_VERY_BAD )) {
                        BarDataSet dataSet = new BarDataSet(entries, "Red");
                        int color = ContextCompat.getColor(context, R.color.darkRed);
                        dataSet.setColor(color);
                        bars.add(dataSet);
                    }
                    break;
                case "O3":
                    if(isBetween(val, 0, O3_VERY_GOOD)) {
                        BarDataSet dataSet = new BarDataSet(entries, "Green");
                        int color = ContextCompat.getColor(context, R.color.darkGreen);
                        dataSet.setColor(color);
                        bars.add(dataSet);
                    } else if(isBetween(val, O3_VERY_GOOD + 1, O3_GOOD )) {
                        BarDataSet dataSet = new BarDataSet(entries, "Green");
                        int color = ContextCompat.getColor(context, R.color.lightGreen);
                        dataSet.setColor(color);
                        bars.add(dataSet);
                    } else if(isBetween(val, O3_GOOD + 1, O3_MODERATE )) {
                        BarDataSet dataSet = new BarDataSet(entries, "Yellow");
                        int color = ContextCompat.getColor(context, R.color.yellow);
                        dataSet.setColor(color);
                        bars.add(dataSet);
                    } else if(isBetween(val, O3_MODERATE + 1, O3_PASSABLE )) {
                        BarDataSet dataSet = new BarDataSet(entries, "Yellow");
                        int color = ContextCompat.getColor(context, R.color.orange);
                        dataSet.setColor(color);
                        bars.add(dataSet);
                    } else if(isBetween(val, O3_PASSABLE + 1, O3_BAD )) {
                        BarDataSet dataSet = new BarDataSet(entries, "Red");
                        int color = ContextCompat.getColor(context, R.color.red);
                        dataSet.setColor(color);
                        bars.add(dataSet);
                    } else if(isBetween(val, O3_BAD + 1, O3_VERY_BAD )) {
                        BarDataSet dataSet = new BarDataSet(entries, "Red");
                        int color = ContextCompat.getColor(context, R.color.darkRed);
                        dataSet.setColor(color);
                        bars.add(dataSet);
                    }
                    break;
                case "C6H6":
                    if(isBetween(val, 0, C6H6_VERY_GOOD)) {
                        BarDataSet dataSet = new BarDataSet(entries, "Green");
                        int color = ContextCompat.getColor(context, R.color.darkGreen);
                        dataSet.setColor(color);
                        bars.add(dataSet);
                    } else if(isBetween(val, C6H6_VERY_GOOD + 1, C6H6_GOOD )) {
                        BarDataSet dataSet = new BarDataSet(entries, "Green");
                        int color = ContextCompat.getColor(context, R.color.lightGreen);
                        dataSet.setColor(color);
                        bars.add(dataSet);
                    } else if(isBetween(val, C6H6_GOOD + 1, C6H6_MODERATE )) {
                        BarDataSet dataSet = new BarDataSet(entries, "Yellow");
                        int color = ContextCompat.getColor(context, R.color.yellow);
                        dataSet.setColor(color);
                        bars.add(dataSet);
                    } else if(isBetween(val, C6H6_MODERATE + 1, C6H6_PASSABLE )) {
                        BarDataSet dataSet = new BarDataSet(entries, "Yellow");
                        int color = ContextCompat.getColor(context, R.color.orange);
                        dataSet.setColor(color);
                        bars.add(dataSet);
                    } else if(isBetween(val, C6H6_PASSABLE + 1, C6H6_BAD )) {
                        BarDataSet dataSet = new BarDataSet(entries, "Red");
                        int color = ContextCompat.getColor(context, R.color.red);
                        dataSet.setColor(color);
                        bars.add(dataSet);
                    } else if(isBetween(val, C6H6_BAD + 1, C6H6_VERY_BAD )) {
                        BarDataSet dataSet = new BarDataSet(entries, "Red");
                        int color = ContextCompat.getColor(context, R.color.darkRed);
                        dataSet.setColor(color);
                        bars.add(dataSet);
                    }
                    break;

            }

        }
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        IndexAxisValueFormatter formatter = new IndexAxisValueFormatter(time);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(formatter);


        return new BarData(bars);
    }


    public static Integer intervalParameter(String key, int val) {

        switch (key) {
            case "SO2":
                if(isBetween(val, 0, S02_VERY_GOOD)) {
                    return R.color.darkGreen;
                } else if(isBetween(val, S02_VERY_GOOD + 1, S02_GOOD )) {
                    return R.color.lightGreen;
                } else if(isBetween(val, S02_GOOD + 1, S02_MODERATE )) {
                    return R.color.yellow;
                } else if(isBetween(val, S02_MODERATE + 1, S02_PASSABLE )) {
                    return R.color.orange;
                } else if(isBetween(val, S02_PASSABLE + 1, S02_BAD )) {
                    return R.color.red;
                } else if(isBetween(val, S02_BAD + 1, S02_VERY_BAD )) {
                    return R.color.darkRed;
                }
                break;
            case "NO2":
                if(isBetween(val, 0, N02_VERY_GOOD)) {
                    return R.color.darkGreen;
                } else if(isBetween(val, N02_VERY_GOOD + 1, N02_GOOD )) {
                    return R.color.lightGreen;
                } else if(isBetween(val, N02_GOOD + 1, N02_MODERATE )) {
                    return R.color.yellow;
                } else if(isBetween(val, N02_MODERATE + 1, N02_PASSABLE )) {
                    return R.color.orange;
                } else if(isBetween(val, N02_PASSABLE + 1, N02_BAD )) {
                    return R.color.red;
                } else if(isBetween(val, N02_BAD + 1, N02_VERY_BAD )) {
                    return R.color.darkRed;
                }
                break;
            case "CO":
                if(isBetween(val, 0, CO_VERY_GOOD)) {
                    return R.color.darkGreen;
                } else if(isBetween(val, CO_VERY_GOOD + 1, CO_GOOD )) {
                    return R.color.lightGreen;
                } else if(isBetween(val, CO_GOOD + 1, CO_MODERATE )) {
                    return R.color.yellow;
                } else if(isBetween(val, CO_MODERATE + 1, CO_PASSABLE )) {
                    return R.color.orange;
                } else if(isBetween(val, CO_PASSABLE + 1, CO_BAD )) {
                    return R.color.red;
                } else if(isBetween(val, CO_BAD + 1, CO_VERY_BAD )) {
                    return R.color.darkRed;
                }
                break;
            case "PM10":
                if(isBetween(val, 0, PM10_VERY_GOOD)) {
                    return R.color.darkGreen;
                } else if(isBetween(val, PM10_VERY_GOOD + 1, PM10_GOOD )) {
                    return R.color.lightGreen;
                } else if(isBetween(val, PM10_GOOD + 1, PM10_MODERATE )) {
                    return R.color.yellow;
                } else if(isBetween(val, PM10_MODERATE + 1, PM10_PASSABLE )) {
                    return R.color.orange;
                } else if(isBetween(val, PM10_PASSABLE + 1, PM10_BAD )) {
                    return R.color.red;
                } else if(isBetween(val, PM10_BAD + 1, PM10_VERY_BAD )) {
                    return R.color.darkRed;
                }
                break;
            case "PM2.5":
                if(isBetween(val, 0, PM25_VERY_GOOD)) {
                    return R.color.darkGreen;
                } else if(isBetween(val, PM25_VERY_GOOD + 1, PM25_GOOD )) {
                    return R.color.lightGreen;
                } else if(isBetween(val, PM25_GOOD + 1, PM25_MODERATE )) {
                    return R.color.yellow;
                } else if(isBetween(val, PM25_MODERATE + 1, PM25_PASSABLE )) {
                    return R.color.orange;
                } else if(isBetween(val, PM25_PASSABLE + 1, PM25_BAD )) {
                    return R.color.red;
                } else if(isBetween(val, PM25_BAD + 1, PM25_VERY_BAD )) {
                    return R.color.darkRed;
                }
                break;
            case "O3":
                if(isBetween(val, 0, O3_VERY_GOOD)) {
                    return R.color.darkGreen;
                } else if(isBetween(val, O3_VERY_GOOD + 1, O3_GOOD )) {
                    return R.color.lightGreen;
                } else if(isBetween(val, O3_GOOD + 1, O3_MODERATE )) {
                    return R.color.yellow;
                } else if(isBetween(val, O3_MODERATE + 1, O3_PASSABLE )) {
                    return R.color.orange;
                } else if(isBetween(val, O3_PASSABLE + 1, O3_BAD )) {
                    return R.color.red;
                } else if(isBetween(val, O3_BAD + 1, O3_VERY_BAD )) {
                    return R.color.darkRed;
                }
                break;
            case "C6H6":
                if(isBetween(val, 0, C6H6_VERY_GOOD)) {
                    return R.color.darkGreen;
                } else if(isBetween(val, C6H6_VERY_GOOD + 1, C6H6_GOOD )) {
                    return R.color.lightGreen;
                } else if(isBetween(val, C6H6_GOOD + 1, C6H6_MODERATE )) {
                    return R.color.yellow;
                } else if(isBetween(val, C6H6_MODERATE + 1, C6H6_PASSABLE )) {
                    return R.color.orange;
                } else if(isBetween(val, C6H6_PASSABLE + 1, C6H6_BAD )) {
                    return R.color.red;
                } else if(isBetween(val, C6H6_BAD + 1, C6H6_VERY_BAD )) {
                    return R.color.darkRed;
                }
                break;
        }
        return R.color.gray;
    }



    public static boolean isBetween(int x, int lower, int upper) {
        return lower <= x && x <= upper;
    }

    public static boolean isPollutionLevelExceeded(String key, int val) {

        switch (key) {
            case "SO2":
                return val > S02_GOOD;
            case "NO2":
                return val > N02_GOOD;
            case "CO":
                return val > CO_GOOD;
            case "PM10":
                return val > PM10_GOOD;
            case "PM2.5":
                return  val > PM25_GOOD;
            case "O3":
                return  val > O3_GOOD;
            case "C6H6":
                return val > C6H6_GOOD;
        }

        return false;
    }

}
