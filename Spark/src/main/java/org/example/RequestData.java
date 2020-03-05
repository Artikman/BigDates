package org.example;

import java.io.Serializable;

public class RequestData implements Serializable {
    private long id;
    private long count;
    private long byteSum;
    private float byteAverage;

    public RequestData(String input) {
        String[] inputs = input.split(" ");
        this.id = Long.parseLong(inputs[0].substring(2));
        if (inputs[9].equals(" -- ")) {
            this.byteSum = 0;
        }
        else {
            this.byteSum = Long.parseLong(inputs[9]);
        }
        count = 1;
        this.byteAverage = this.byteSum;
    }

    @Override
    public String toString() {
        return id + " " + byteAverage + " " + byteSum;
    }

    public void addToSum(long byteCount) {
        byteSum += byteCount;
        count++;
        byteAverage = (float) byteCount / count;
    }

    public long getId() {
        return id;
    }

    public long getByteSum() {
        return byteSum;
    }
}