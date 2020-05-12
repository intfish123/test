package com.example.test;

import com.example.test.util.ClickHouseUtil;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

@SpringBootTest
class TestApplicationTests {

    @Autowired
    private ThreadPoolTaskExecutor executor;

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Test
    void contextLoads() {
        int maxTasks = 3000;
        try {
            List<Future<List<JsonObject>>> futureList = new ArrayList<>();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            int topThread = 0;
            for (int i = 0; i < maxTasks; i++) {
                long time = Instant.now().toEpochMilli();
                String entTimeStr = sdf.format(new Date(time));
                /*String sql = "select province, count(1) as pv,count(distinct onlyid) as uv  " +
                        "from DBService.distribute_log_ck_logs  where ck_date in  ( '2020-05-09' )  " +
                        "and ck_datetime_timestamp >='2020-05-09 00:00:00' and ck_datetime_timestamp<='" +entTimeStr+"' "+
                        " and kind = 'app_time'   group by province  order by uv desc";*/
                String sql = "select sum(if(level >= 1, number, 0)) as level___number___1, sum(if(level >= 2, number, 0)) as level___number___2 " +
                        "from (select level, count() as number " +
                        "      from (select onlyid, windowFunnel(3600000)(ck_datetime_timestamp, kind = 'play_game', kind = 'app_time') as level " +
                        "            FROM DBService.distribute_log_ck_logs " +
                        "            where ck_date in ('2020-05-09') " +
                        "              and ck_datetime_timestamp >='2020-05-09 00:00:00'  " +
                        "              and ck_datetime_timestamp<='" +entTimeStr+"'  "+
                        "              and kind in ('app_time', 'play_game') " +
                        "              and onlyid is not null " +
                        "              and selfpackagename = 'com.meta.box' " +
                        "            group by onlyid) " +
                        "      where level <> 0 " +
                        "      group by level)";
//                System.out.println(sql);
                futureList.add(executor.submit(() -> {
                    try {
                        return ClickHouseUtil.exeSql(sql);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return new ArrayList<>();
                }));

            }
            int sum = 1;
            for (Future<List<JsonObject>> future : futureList) {
                List<JsonObject> jsonObjects = future.get();
                System.out.println(sum++);
                log.info("当前活跃线程："+ executor.getActiveCount()+"，最大线程："+ Math.max(topThread, executor.getActiveCount()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
