package com.demo;

import org.apache.shardingsphere.sharding.api.sharding.hint.HintShardingAlgorithm;
import org.apache.shardingsphere.sharding.api.sharding.hint.HintShardingValue;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

public class HintAlgorithm implements HintShardingAlgorithm<Integer> {
    @Override
    public Collection<String> doSharding(Collection<String> collection, HintShardingValue<Integer> hintShardingValue) {
        int tableSuffix = hintShardingValue.getValues().iterator().next();
        Iterator<String> iter = collection.iterator();
        String tableName;
        do {
            if (!iter.hasNext()) {
                throw new RuntimeException();
            } else {
                tableName = iter.next();
            }
        } while (!tableName.endsWith("_" + tableSuffix));
        return Collections.singleton(tableName);
    }
}
