package com.kujakunote.urlconnection.client.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.time.Duration;

@Configuration
public class WebConfig {

    @Bean
    public RestTemplate restTemplate(
            final RestTemplateBuilder builder,
            @Value("${rest-template.connect-timeout}") final int connectionTimeout,
            @Value("${rest-template.read-timeout}") final int readTimeout) {
        return builder
                .setConnectTimeout(Duration.ofSeconds(connectionTimeout))
                .setReadTimeout(Duration.ofSeconds(readTimeout))
                .build();
    }

    @Bean
    @Profile("!prod")
    public CommandLineRunner disableCertValidation() {
        return args -> {
            final TrustManager[] trustManagers = {
                    new X509TrustManager() {
                        public void checkClientTrusted(X509Certificate[] arg0, String arg1) {
                        }

                        public void checkServerTrusted(X509Certificate[] arg0, String arg1) {
                        }

                        public X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }
                    }
            };

            final SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustManagers, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());

            HttpsURLConnection.setDefaultHostnameVerifier((str, sslSession) -> true);
        };
    }
}
