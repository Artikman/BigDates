package org.example;

import eu.bitwalker.useragentutils.UserAgent;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import scala.Tuple2;

public class LogOutput {
    private String pathToFile;
    private static JavaSparkContext sc;
    private JavaRDD<String> data;

    public LogOutput(String pathToFile) {
        this.pathToFile = pathToFile;

        SparkConf conf = new SparkConf().setAppName("Request reader")
                .setMaster("local[*]").set("spark.driver.bindAddress", "127.0.0.1");

        if (sc == null) {
            sc = new JavaSparkContext(conf);
            sc.setLogLevel("ERROR");
        }
    }

    public JavaRDD<RequestData> getRequestsInfo() {
        JavaRDD<RequestData> requests = getData().map((Function<String, RequestData>) RequestData::new);
        JavaPairRDD<Long, RequestData> requestsData = requests.mapToPair(t -> new Tuple2<>(t.getId(), t))
                .reduceByKey((x, y) -> {
                    x.addToSum(y.getByteSum());
                    return x;
                });

        return requestsData.map(t -> t._2);
    }

    public JavaPairRDD<String, Integer> getBrowserInfo() {
        JavaRDD<UserAgent> browsers = getData().map((Function<String, UserAgent>) UserAgent::parseUserAgentString);
        return browsers.mapToPair(t -> new Tuple2<>(t.getBrowser().getName(), 1))
                .reduceByKey(Integer::sum);
    }

    public JavaRDD<String> getData() {
        if (data == null) {
            data = sc.textFile(pathToFile);
        }
        return data;
    }
}