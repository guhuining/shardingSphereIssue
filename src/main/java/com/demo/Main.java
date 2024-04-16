package com.demo;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.shardingsphere.driver.api.yaml.YamlShardingSphereDataSourceFactory;
import org.apache.shardingsphere.infra.hint.HintManager;
import org.mariadb.jdbc.Driver;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws SQLException, IOException {
        Map<String, DataSource> dataSourceMap = new HashMap<>(4);
        dataSourceMap.put("sharding_test", getDataSource());
        File shardingYaml = new File(Main.class.getClassLoader().getResource("sharding.yml").getFile());
        DataSource ds = YamlShardingSphereDataSourceFactory.createDataSource(dataSourceMap, shardingYaml);

        ds.getConnection()
                .prepareStatement("SELECT * FROM test_ WHERE id = 1")
                .executeQuery();

        try (HintManager hintManager = HintManager.getInstance()) {
            hintManager.addTableShardingValue("test_hint_", 2);
            ds.getConnection()
                    .prepareStatement("SELECT * FROM test_hint_")
                    .executeQuery();
        }
    }

    public static DataSource getDataSource() {
        BasicDataSource ds = new BasicDataSource();
        ds.setUrl("jdbc:mysql://127.0.0.1:3306/sharding_test");
        ds.setDriver(new Driver());
        ds.setUsername("root");
        ds.setPassword("123456");
        return ds;
    }
}
