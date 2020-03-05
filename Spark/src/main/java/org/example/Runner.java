package org.example;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;

public class Runner {

    public static void main(String[] args) {

        String path = "input/sample.txt";

        LogOutput outputClass = new LogOutput(path);

        JavaRDD<RequestData> requestsData = outputClass.getRequestsInfo();
        JavaPairRDD<String, Integer> countData = outputClass.getBrowserInfo();

        countData.foreach(data1 -> System.out.println("Browser: "+ data1._1() + " count: " + data1._2()));

        try {
            requestsData.coalesce(1).saveAsTextFile("Requests data");
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}