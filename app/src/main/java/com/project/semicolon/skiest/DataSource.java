package com.project.semicolon.skiest;

import com.project.semicolon.skiest.model.FakeWeaklyData;

import java.util.ArrayList;
import java.util.List;

public class DataSource {

    public static List<FakeWeaklyData> createFakeWeaklyData() {
        List<FakeWeaklyData> fakeWeaklyData = new ArrayList<>();
        fakeWeaklyData.add(new FakeWeaklyData("Wed Jun 9", "15", "20"));
        fakeWeaklyData.add(new FakeWeaklyData("Thu Jun 10", "20", "25"));
        fakeWeaklyData.add(new FakeWeaklyData("Fri Jun 11", "14", "18"));
        fakeWeaklyData.add(new FakeWeaklyData("Sat Jun 12", "25", "28"));
        fakeWeaklyData.add(new FakeWeaklyData("Sun Jun 13", "20", "21"));
        fakeWeaklyData.add(new FakeWeaklyData("Mon Jun 14", "10", "12"));

        return fakeWeaklyData;
    }
}
