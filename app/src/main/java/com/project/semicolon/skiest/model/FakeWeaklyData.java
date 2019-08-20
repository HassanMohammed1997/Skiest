package com.project.semicolon.skiest.model;

public class FakeWeaklyData {

    private String date;
    private String min;
    private String max;

    public FakeWeaklyData() {
    }

    public FakeWeaklyData(String date, String min, String max) {
        this.date = date;
        this.min = min;
        this.max = max;
    }

    public String getDate() {
        return date;
    }

    public String getMin() {
        return min;
    }

    public String getMax() {
        return max;
    }
}
