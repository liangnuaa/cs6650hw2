package com.liang;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Latency {
    private int meanThroughput;
    private int nintyNinePercentile;
    private int nintyFivePercentile;

    private List<Double> latencyList;
    private List<Long> requestTimeList;
    private int totalRequestNum;

    public Latency(int totalRequestNum, List<Double> latencyList, List<Long> requestTimeList) {
        this.totalRequestNum = totalRequestNum;
        this.latencyList = latencyList;
        this.requestTimeList = requestTimeList;
    }

    public void processStatistic() {
        if (requestTimeList == null || requestTimeList.size() == 0) {
            throw new IllegalArgumentException("No request to process.");
        }

        long minTime = Long.MAX_VALUE;
        Map<Long, Integer> requestMap = new HashMap<>();

        for (Long requestTime : requestTimeList) {
            requestMap.put(requestTime, requestMap.getOrDefault(requestTime, 0) + 1);
            minTime = minTime < requestTime ? minTime : requestTime;
        }

        List<Long> keyList = new ArrayList<>(requestMap.keySet());
        Collections.sort(keyList);

        CsvFileWriter csvFileWriter = new CsvFileWriter();
        csvFileWriter.writeToCSVFile(requestMap, keyList, minTime);


        int totalTime = requestMap.size();
        int nintyNineTotal = (int) (totalTime * 0.01);
        int nintyFiveTotal = (int) (totalTime * 0.05);

        List<Integer> throughputList = new ArrayList<>();
        int sumThroughtput = 0;
        for (long key : keyList) {
            throughputList.add(requestMap.get(key));
            sumThroughtput += requestMap.get(key);
        }
        Collections.sort(throughputList);
        meanThroughput = sumThroughtput / totalTime;
        nintyFivePercentile = throughputList.get(nintyFiveTotal);
        nintyNinePercentile = throughputList.get(nintyNineTotal);
    }

    public int getMeanThroughput() {
        return meanThroughput;
    }

    public int getNintyNinePercentile() {
        return nintyNinePercentile;
    }

    public int getNintyFivePercentile() {
        return nintyFivePercentile;
    }
}
