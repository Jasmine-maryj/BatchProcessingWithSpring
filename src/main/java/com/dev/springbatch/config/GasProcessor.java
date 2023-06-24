package com.dev.springbatch.config;

import com.dev.springbatch.entity.Gas;
import org.springframework.batch.item.ItemProcessor;

public class GasProcessor implements ItemProcessor<Gas, Gas> {

    @Override
    public Gas process(Gas gas) throws Exception {
//        if(gas.getStatus().equals("Good")) {
//            return gas;
//        }else{
//            return null;
//        }

        return gas;
    }
}
