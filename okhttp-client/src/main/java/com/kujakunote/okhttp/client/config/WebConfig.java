package com.kujakunote.okhttp.client.config;

import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.time.Duration;

@Configuration
public class WebConfig {

    @Bean
    public RestTemplate restTemplate(
            final RestTemplateBuilder builder,
            @Value("${rest-template.connect-timeout}") final int connectionTimeout,
            @Value("${rest-template.read-timeout}") final int readTimeout,
            final OkHttpClient okHttpClient) {
        return builder
                .setConnectTimeout(Duration.ofSeconds(connectionTimeout))
                .setReadTimeout(Duration.ofSeconds(readTimeout))
                .requestFactory(() -> new OkHttp3ClientHttpRequestFactory(okHttpClient))
                .build();
    }

    @Bean
    @Primary
    @Profile("prod")
    public OkHttpClient prodOkHttpClient() {
        return new OkHttpClient();
    }

    @Bean
    @Profile("!prod")
    public OkHttpClient devOkHttpClient() throws NoSuchAlgorithmException, KeyManagementException {
        final TrustManager[] trustManagers = {
                new X509TrustManager() {
                    public void checkClientTrusted(X509Certificate[] arg0, String arg1) {
                    }

                    public void checkServerTrusted(X509Certificate[] arg0, String arg1) {
                    }

                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }
                }
        };

        final SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustManagers, new SecureRandom());
        return new OkHttpClient.Builder()
                .sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) trustManagers[0])
                .hostnameVerifier((str, sslSession) -> true)
                .build();
    }
}
