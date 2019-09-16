package com.naya.spring_starter;

import com.naya.spring_starter.annotations.AutowiredBroadcastBeanPostProcessor;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SQLContext;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(SparkProperties.class)
public class SparkConfiguration {

    private final SparkProperties sparkProperties;

    public SparkConfiguration(SparkProperties sparkProperties) {
        this.sparkProperties = sparkProperties;
    }

    @Bean
    @ConditionalOnMissingBean
    public JavaSparkContext sparkConfiguration(SparkConf sparkConf) {
        return new JavaSparkContext(sparkConf);
    }

    @Bean
    @ConditionalOnMissingBean
    public SparkConf sparkConf() {
        SparkConf sparkConf = new SparkConf();

        String master = sparkProperties == null
                ? System.getProperty("spark.master")
                : sparkProperties.getMaster();

        String appName = sparkProperties == null
                ? System.getProperty("spark.app.name")
                : sparkProperties.getAppName();

        sparkConf.setMaster(master);
        sparkConf.setAppName(appName);
        return sparkConf;
    }

    @Bean
    @ConditionalOnMissingBean
    public SQLContext sqlContext(JavaSparkContext sc) {
        return new SQLContext(sc);
    }

    @Bean
    public AutowiredBroadcastBeanPostProcessor autowiredBroadcastBeanPostProcessor(JavaSparkContext sparkContext,
                                                                                   ApplicationContext applicationContext) {
        return new AutowiredBroadcastBeanPostProcessor(sparkContext, applicationContext);
    }
}
