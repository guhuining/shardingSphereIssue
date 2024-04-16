package com.demo;

import org.apache.shardingsphere.sharding.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.RangeShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.StandardShardingAlgorithm;

import java.util.Collection;
import java.util.Iterator;

public class ModAlgorithm implements StandardShardingAlgorithm<Integer> {
    private static int TABLE_COUNT = 5;

    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Integer> preciseShardingValue) {
        int value = preciseShardingValue.getValue();
        int tableSuffix = value % TABLE_COUNT;
        Iterator<String> iter = collection.iterator();
        String tableName;
        do {
            if (!iter.hasNext()) {
                throw new RuntimeException();
            } else {
                tableName = iter.next();
            }
        } while (!tableName.endsWith("_" + tableSuffix));
        return tableName;
    }

    @Override
    public Collection<String> doSharding(Collection<String> collection, RangeShardingValue<Integer> rangeShardingValue) {
        return null;
    }
}
