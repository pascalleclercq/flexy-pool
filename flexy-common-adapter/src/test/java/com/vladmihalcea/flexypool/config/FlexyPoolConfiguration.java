package com.vladmihalcea.flexypool.config;

import com.vladmihalcea.flexypool.FlexyPoolDataSource;
import com.vladmihalcea.flexypool.adaptor.DataSourcePoolAdapter;
import org.hsqldb.jdbc.JDBCDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

/**
 * FlexyDataSourceConfiguration - Configuration for flexypool data source
 *
 * @author Vlad Mihalcea
 */
@org.springframework.context.annotation.Configuration
public class FlexyPoolConfiguration {

    @Autowired
    private JDBCDataSource jdbcDataSource;

    @Value("${flexy.pool.uniqueId}")
    private String uniqueId;

    @Bean
    public Configuration<DataSource> configuration() {
        return new Configuration.Builder<DataSource>(
                uniqueId,
                jdbcDataSource,
                DataSourcePoolAdapter.FACTORY
        ).build();
    }

    @Bean(initMethod = "start", destroyMethod = "stop")
    public FlexyPoolDataSource dataSource() {
        Configuration<DataSource> configuration = configuration();
        return new FlexyPoolDataSource<DataSource>(configuration);
    }
}
