package com.example.tgbotecho.configuration;

import com.example.tgbotecho.client.EchoBotHttpClient;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.classic.HttpClients;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.client.RestTemplate;

@Configuration
public class EchoBotConfiguration {

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(BasicDataSource basicDataSource) {
        return new NamedParameterJdbcTemplate(basicDataSource);
    }

    @Bean
    public BasicDataSource basicDataSource(
            @Value("${database.url}") String url,
            @Value("${database.username}") String userName,
            @Value("${database.password}") String password,
            @Value("${database.schema}") String schema
    ) {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUrl(url);
        basicDataSource.setDriverClassName("org.postgresql.Driver");
        basicDataSource.setUsername(userName);
        basicDataSource.setPassword(password);
        basicDataSource.setDefaultSchema(schema);
        return basicDataSource;
    }

    @Bean
    public HttpClientBuilder httpClientBuilder(
            @Value("${http.connect.timeout:100}") int connectTimeout,
            @Value("${http.socket.timeout:100}") int socketTimeout
    ) {
        RequestConfig requestConfig = RequestConfig.custom()
                .build();

        return HttpClients.custom().setDefaultRequestConfig(requestConfig);
    }

    @Bean
    public RestTemplate restOperations(
            @Value("${timeout.connect:500}") int connectionTimeout,
            @Value("${timeout.read:1000}") int readTimeout,
            HttpClientBuilder httpClientBuilder
    ) {
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setConnectTimeout(connectionTimeout);
        requestFactory.setReadTimeout(readTimeout);
        requestFactory.setHttpClient(httpClientBuilder.build());


        RestTemplate restTemplate = new RestTemplate(requestFactory);
        return restTemplate;
    }

    @Bean
    public EchoBotHttpClient echoBotHttpClient(
            @Qualifier("restOperations") RestTemplate restTemplate,
            @Value("${url}") String url
    ) {
        return new EchoBotHttpClient(restTemplate, url);
    }

    @Bean
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        return new ThreadPoolTaskExecutor();
    }

}
