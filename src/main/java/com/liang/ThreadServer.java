package com.liang;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ThreadServer {
    private int maxThreadNum;
    private int userNum;
    private int dayNum;
    private int testNum;

    private WebClient webClient;

    /**
     * Constructor of ThreadServer
     */
    public ThreadServer(String url, int maxThreadNum, int userNum, int dayNum, int testNum) {
        this.maxThreadNum = maxThreadNum;
        this.userNum = userNum;
        this.dayNum = dayNum;
        this.testNum = testNum;
        webClient = new WebClient(url);
    }

    /**
     * Start execute the ThreadServer
     */
    public void start() {
        webClient.deleteTable();

        Timestamp startWallTime = new Timestamp(System.currentTimeMillis());

        Phase warmUp = new Phase("Warmup", webClient, maxThreadNum, userNum, dayNum, testNum);
        warmUp.run();
        Phase loading = new Phase("Loading", webClient, maxThreadNum, userNum, dayNum, testNum);
        loading.run();
        Phase peak = new Phase("Peak", webClient, maxThreadNum, userNum, dayNum, testNum);
        peak.run();
        Phase cooldown = new Phase("Cooldown", webClient, maxThreadNum, userNum, dayNum, testNum);
        cooldown.run();

        Timestamp endWallTime = new Timestamp(System.currentTimeMillis());

        int totalRequest = warmUp.getTotalRequest() + loading.getTotalRequest() + peak.getTotalRequest() + cooldown.getTotalRequest();
        List<Double> totalLatency = new ArrayList<>();
        totalLatency.addAll(warmUp.getLatencyList());
        totalLatency.addAll(loading.getLatencyList());
        totalLatency.addAll(peak.getLatencyList());
        totalLatency.addAll(cooldown.getLatencyList());

        List<Long> requestList = new ArrayList<>();
        requestList.addAll(warmUp.getRequestTimeList());
        requestList.addAll(loading.getRequestTimeList());
        requestList.addAll(peak.getRequestTimeList());
        requestList.addAll(cooldown.getRequestTimeList());

        // Calculate latency
        Latency latency = new Latency(totalRequest, totalLatency, requestList);
        latency.processStatistic();

        int meanThroughput = latency.getMeanThroughput();
        int nintyFivePercentileThroughput = latency.getNintyFivePercentile();
        int nintyNinePercentileThroughput = latency.getNintyNinePercentile();

        double totalWallTime = (endWallTime.getTime() - startWallTime.getTime()) / 1000.0;

        System.out.println("===================================================");
        System.out.println("Total run time (wall time) for all threads to complete: " + totalWallTime + " seconds");
        System.out.println("Total number of requests sent: " + totalRequest);
        System.out.println("Mean throughput is: " + meanThroughput);
        System.out.println("95th percentile throughput is: " + nintyFivePercentileThroughput);
        System.out.println("99th percentile throughput is: " + nintyNinePercentileThroughput);
    }
}
