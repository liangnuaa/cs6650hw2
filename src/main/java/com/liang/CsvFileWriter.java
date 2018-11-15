package com.liang;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class CsvFileWriter {
    private static final String DELIMITER = ",";
    private static final String LINE_SEPARATOR = "\n";

    private static final String HEADER = "time,count";

    private static final String FILE_NAME = "test.csv";

    public void writeToCSVFile(Map<Long, Integer> requestMap, List<Long> keyList, long minTime) {
        FileWriter fileWriter = null;

        try {
            fileWriter = new FileWriter(FILE_NAME);
            fileWriter.append(HEADER);
            fileWriter.append(LINE_SEPARATOR);
            for (long key : keyList) {
                int timeDiff = (int) (key - minTime);
                fileWriter.append(String.valueOf(timeDiff));
                fileWriter.append(DELIMITER);
                fileWriter.append(String.valueOf(requestMap.get(key)));
                fileWriter.append(LINE_SEPARATOR);
            }
        } catch (Exception e) {
            System.out.println("Error in CsvFileWriter !!!");
            e.printStackTrace();
        } finally {
            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                System.out.println("Error while flushing/closing fileWriter !!!");
                e.printStackTrace();
            }
        }
    }
}
