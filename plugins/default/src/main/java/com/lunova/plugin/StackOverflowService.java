package com.lunova.plugin;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException; 


import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.lunova.moonbot.core.Constants;
import com.lunova.moonbot.core.service.Service;
import com.lunova.moonbot.core.service.ServiceInfo;
import com.lunova.moonbot.core.service.executors.DefaultUncaughtExceptionHandler;
import com.lunova.moonbot.core.service.executors.ServiceExecutor;
import com.lunova.moonbot.core.service.executors.ServiceExecutor;
import com.lunova.moonbot.core.service.executors.ThreadFactoryConfig;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.io.IOException;
import java.util.Optional;

@ServiceInfo(name = "StackOverflowService", critical = false, disabled = false)
@ThreadFactoryConfig(nameFormat = "Stack-Overflow-Service")
public class StackOverflowService extends Service<ServiceExecutor> {
    


    private static final Logger LOGGER = LoggerFactory.getLogger(StackOverflowService.class);

    private static final String API_KEY_ENV_NAME = "STACKAPPS_API_KEY";

    private final Optional<String> apiKey;

    public StackOverflowService(String name, boolean critical, ServiceExecutor executor) {
        super(name, critical, executor);
        this.apiKey = getApiKey();
    }

    @Override
    protected void initialize() {
        if (apiKey.isEmpty()) {
            LOGGER.error("Stack Overflow API key not found. Service will not be fully functional.");
        } else {
            LOGGER.info("Stack Overflow API key found Service is ready.");
            // Initialize the service with the API key
        }
    }
    private Optional<String> getApiKey() {
        String key = System.getenv(API_KEY_ENV_NAME);
        if (key== null || key.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(key);
    }
       public Optional<String> getQuestions(String query) {
        if (apiKey.isEmpty()) {
            LOGGER.warn("API key is not available. Cannot retrieve questions.");
            return Optional.empty();
        }
        if (query == null || query.trim().isEmpty()) {
            LOGGER.warn("Query is empty. Cannot retrieve questions.");
            return Optional.empty();
        }
        try {
           HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.STACK_EXCHANGE_API_BASE_URL + "/questions").newBuilder();
            urlBuilder.addQueryParameter("order", "desc");
            urlBuilder.addQueryParameter("sort", "votes");
            urlBuilder.addQueryParameter("tagged", query); // Use the user's query here
            urlBuilder.addQueryParameter("site", "stackoverflow");
            urlBuilder.addQueryParameter("key", apiKey.get()); 

            Request request = new Request.Builder()
                .url(urlBuilder.build())
                .build();

            OkHttpClient client = new OkHttpClient();
            Response response = client.newCall(request).execute();

            if (!response.isSuccessful()) {
                throw new IOException("Unexpected response code: " + response);
            }

            return Optional.of(response.body().string());
        } catch (IOException e) {
            LOGGER.error("Failed to retrieve questions from Stack Overflow.", e);
            return Optional.empty();
        }
       } //add methods to interact with Stack Overflow API
        //retrieve questions, answers, etc.
    }