package be.virtualmem.presentation.gui.controller;

import be.virtualmem.logic.System;
import be.virtualmem.logic.statistics.Statistics;
import be.virtualmem.presentation.gui.view.tabs.StatisticsTab;

import java.util.Map;

public class StatisticsTabController {
    private System system;
    private StatisticsTab tab;

    public StatisticsTabController(StatisticsTab statisticsTab, System system) {
        this.system = system;
        this.tab = statisticsTab;
    }

    public void update() {
        Map<String, Integer> data = Statistics.getInstance().map();

        tab.pageInLabel.setText("# page ins: " + data.get("pageInCount"));
        tab.pageEvictionLabel.setText("# page evictions: " + data.get("pageEvictionCount"));
        tab.pageOutLabel.setText("# page outs: " + data.get("pageOutCount"));
        tab.rwLabel.setText("# read-write actions: " + data.get("rwCount"));
    }
}
