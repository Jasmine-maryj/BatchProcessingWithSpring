package com.dev.springbatch.partition;

import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;

import java.util.HashMap;
import java.util.Map;

public class DataPartition implements Partitioner {
    @Override
    public Map<String, ExecutionContext> partition(int size) {
        int min = 1;
        int max = 49;
        int targetSize = (max - min) /size + 1;
        System.out.println("target size: "+targetSize);
        Map<String, ExecutionContext> result = new HashMap<>();

        int number = 0;
        int start = min;
        int end = start + targetSize - 1;

        while(start <= end){
            ExecutionContext value = new ExecutionContext();
            result.put("partition"+ number, value);

            if(end >= max){
                end = max;
            }
            value.putInt("minValue", start);
            value.putInt("maxValue", end);
            start += targetSize;
            end +=targetSize;
            number++;
        }

        return result;
    }
}
