package com.example.test.util;

import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.yandex.clickhouse.ClickHouseConnection;
import ru.yandex.clickhouse.ClickHouseDataSource;
import ru.yandex.clickhouse.settings.ClickHouseProperties;

import javax.annotation.PostConstruct;
import java.sql.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * clickhouse操作工具类
 *
 * @author wangxiaolei
 * @date 2020/3/23 17:00
 */
@Component
public class ClickHouseUtil {
	private static Logger log = LoggerFactory.getLogger(ClickHouseUtil.class);

	//	private static String clickhouseAddress;
	private static String proxyWithCacheAddress;
	private static String proxyNoCacheAddress;
	private static String clickhouseUsername;
	private static String clickhousePassword;
	private static String clickhouseDistributeDB;

	private static Integer clickhouseSocketTimeout;

	private volatile static ClickHouseDataSource clickHouseNoCacheSource;
	private volatile static ClickHouseDataSource clickHouseWithCacheSource;

	@Autowired
	public static ClickHouseUtil clickHouseUtil;

	@PostConstruct
	public void init() {
		clickHouseUtil = this;
	}

//	@Value("${clickhouse.address}")
//	public void setClickhouseAddress(String address) {
//		ClickHouseUtil.clickhouseAddress = address;
//	}
	@Value("${clickhouse.proxyWithCacheAddress}")
	public void setProxyWithCacheAddress(String proxyWithCacheAddress) {
		ClickHouseUtil.proxyWithCacheAddress = proxyWithCacheAddress;
	}

	@Value("${clickhouse.proxyNoCacheAddress}")
	public void setProxyNoCacheAddress(String proxyNoCacheAddress) {
		ClickHouseUtil.proxyNoCacheAddress = proxyNoCacheAddress;
	}
	@Value("${clickhouse.username}")
	public void setClickhouseUsername(String username) {
		ClickHouseUtil.clickhouseUsername = username;
	}

	@Value("${clickhouse.password}")
	public void setClickhousePassword(String password) {
		ClickHouseUtil.clickhousePassword = password;
	}

	@Value("${clickhouse.distribute.db}")
	public void setClickhouseDistributeDB(String clickhouseDistributeDB) {
		ClickHouseUtil.clickhouseDistributeDB = clickhouseDistributeDB;
	}

	@Value("${clickhouse.socketTimeout}")
	public void setClickhouseSocketTimeout(Integer socketTimeout) {
		ClickHouseUtil.clickhouseSocketTimeout = socketTimeout;
	}

	static ClickHouseProperties defaultPropertises;

	@PostConstruct
	public void postConstruct() {
		ClickHouseProperties properties = new ClickHouseProperties();
		properties.setUser(clickhouseUsername);
		properties.setPassword(clickhousePassword);
		properties.setSocketTimeout(clickhouseSocketTimeout);
		properties.setDatabase(clickhouseDistributeDB);
		defaultPropertises = properties;
		clickHouseWithCacheSource = new ClickHouseDataSource(proxyWithCacheAddress, properties);
		clickHouseNoCacheSource = new ClickHouseDataSource(proxyNoCacheAddress, properties);
	}
//	/**
//	 * 获取连接池
//	 *
//	 * @return ru.yandex.clickhouse.ClickHouseDataSource
//	 * @author wangxiaolei
//	 * @date 2020/3/23 17:11
//	 */
//	private static ClickHouseDataSource getClickHouseNotCacheSource(String clickHouseAddress) {
//		if (clickHouseNotCacheSource == null) {
//			synchronized (ClickHouseUtil.class) {
//				if (clickHouseNotCacheSource == null) {
//					ClickHouseProperties properties = defaultPropertises;
//					properties.setDatabase(clickhouseDistributeDB);
//					clickHouseNotCacheSource = new ClickHouseDataSource(clickHouseAddress, properties);
//				}
//			}
//		}
//		return clickHouseNotCacheSource;
//	}
//
//	private static ClickHouseDataSource getClickHouseWithCacheSource(String clickHouseAddress) {
//		if (clickHouseWithCacheSource == null) {
//			synchronized (ClickHouseUtil.class) {
//				if (clickHouseWithCacheSource == null) {
//					ClickHouseProperties properties = defaultPropertises;
//					properties.setDatabase(clickhouseDistributeDB);
//					clickHouseWithCacheSource = new ClickHouseDataSource(clickHouseAddress, properties);
//				}
//			}
//		}
//		return clickHouseWithCacheSource;
//	}

//	/**
//	 * 获取链接
//	 *
//	 * @return java.sql.Connection
//	 * @author wangxiaolei
//	 * @date 2020/3/23 17:11
//	 */
//	public static Connection getConn(String address) throws SQLException {
//		ClickHouseConnection conn = null;
//		try {
//			conn = getDistributeDateSource(address).getConnection();
//			return conn;
//		} catch (SQLException e) {
//			log.error(e.getMessage(), e);
//			throw e;
//		}
//	}
	public static List<JsonObject> exeSqlNotToDay(String sql) throws SQLException {
		ClickHouseConnection connection = clickHouseWithCacheSource.getConnection();
		return exeAllSql("cache", connection, sql);
	}
	public static List<JsonObject> exeSql(String sql) throws SQLException {
		ClickHouseConnection connection = clickHouseNoCacheSource.getConnection();
		return exeAllSql("realtime", connection, sql);
	}

	public static List<Map<String, Object>> exeSqlWithCacheToMap(String sql) throws SQLException {
		ClickHouseConnection connection = clickHouseWithCacheSource.getConnection();
		return exeSqlToMap("cache", connection, sql);
	}
	public static List<Map<String, Object>> exeSqlWithNoCacheToMap(String sql) throws SQLException {
		ClickHouseConnection connection = clickHouseNoCacheSource.getConnection();
		return exeSqlToMap("realtime", connection, sql);
	}

	public static List<JsonObject> exeAllSql(String type, Connection connection ,String sql) throws SQLException {
		log.info(type + "===clickhouse 准备执行sql：" + sql);
		long startTime = Instant.now().toEpochMilli();
		try (Connection conn = connection;
				Statement statement = conn.createStatement();
		     ResultSet results = statement.executeQuery(sql)) {
			List<JsonObject> list = new ArrayList();
			if (results == null) {
				long endTime = Instant.now().toEpochMilli();
				log.info(type + "===clickhouse 执行sql耗时：" + (endTime - startTime) / 1000.0 + " 秒。" + sql);
				insertToSqlDuration(sql, endTime - startTime);
				return list;
			}
			ResultSetMetaData rsmd = results.getMetaData();
			while (results.next()) {
				JsonObject row = new JsonObject();
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					row.addProperty(rsmd.getColumnName(i), results.getString(rsmd.getColumnName(i)));
				}
				list.add(row);
			}
			long endTime = Instant.now().toEpochMilli();
			log.info(type + "===clickhouse 执行sql耗时：" + (endTime - startTime) / 1000.0 + " 秒。" + sql);
			insertToSqlDuration(sql, endTime - startTime);
			return list;
		} catch (SQLException e) {
			log.error("SQL 是： {}", sql);
			log.error(type + "===ClickHouseUtil.exeSql, 报错信息：{}\n,{}", e.getMessage(), e);
			throw e;
		}
	}
	public static List<Map<String, Object>> exeSqlToMap(String type,Connection connection, String sql) throws SQLException {
		log.info(type + "===clickhouse 准备执行sql：" + sql);
		long startTime = Instant.now().toEpochMilli();

		try (Connection conn = connection;
		     Statement statement = conn.createStatement();
		     ResultSet results = statement.executeQuery(sql)) {
			ResultSetMetaData rsmd = results.getMetaData();
			List<Map<String, Object>> list = new ArrayList();
			while (results.next()) {
				Map<String, Object> row = new HashMap<>();
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					row.put(rsmd.getColumnName(i), results.getString(rsmd.getColumnName(i)));
				}
				list.add(row);
			}
			long endTime = Instant.now().toEpochMilli();
			log.info(type + "===clickhouse 执行sql耗时：" + (endTime - startTime) / 1000.0 + " 秒。" + sql);
			insertToSqlDuration(sql, endTime - startTime);
			return list;
		} catch (SQLException e) {
			log.error(type + "===ClickHouseUtil.exeSql, 报错信息：{}\n,{}", e.getMessage(), e);
			throw e;
		}
	}

	private static void insertToSqlDuration(String sql, long duration) {
		try {
			/*ZoneId zoneId = ZoneId.systemDefault();
			SqlDuration sqlDuration = new SqlDuration();
			sqlDuration.setDuration(duration);
			sqlDuration.setType("clickhouse");
			sqlDuration.setQuerySql(sql);
			sqlDuration.setTimestamp(LocalDateTime.now(zoneId));
			clickHouseUtil.sqlDurationService.save(sqlDuration);*/
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
}
