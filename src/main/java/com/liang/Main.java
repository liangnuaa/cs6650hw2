package com.liang;

public class Main {
    private static final int DEFAULT_MAX_THREAD_NUM = 64;
    private static final int DEFAULT_DAY_NUM = 1;
    private static final int DEFAULT_USER_NUM = 100000;
    private static final int DEFAULT_TEST_NUM = 100;

    public static void main(String[] args) {
        String ipAddress = args[0];
        int maxThreadNum = args[1] == null || args[1].length() == 0 ? DEFAULT_MAX_THREAD_NUM :
                Integer.parseInt(args[1]);
        int userNum = args[2] == null || args[2].length() == 0 ? DEFAULT_USER_NUM :
                Integer.parseInt(args[2]);
        int dayNum = args[3] == null || args[3].length() == 0 ? DEFAULT_DAY_NUM :
                Integer.parseInt(args[3]);
        int testNum = args[4] == null || args[4].length() == 0 ? DEFAULT_TEST_NUM :
                Integer.parseInt(args[4]);

        String url = "http://" + ipAddress + ":8080/webapp-server/api/fitbit";

        ThreadServer threadServer = new ThreadServer(url, maxThreadNum, userNum, dayNum, testNum);
        threadServer.start();
    }
}
