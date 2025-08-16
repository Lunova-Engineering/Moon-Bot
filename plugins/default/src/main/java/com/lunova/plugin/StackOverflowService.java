package com.lunova.plugin;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.lunova.moonbot.core.Constants;
import com.lunova.moonbot.core.service.Service;
import com.lunova.moonbot.core.service.ServiceInfo;
import com.lunova.moonbot.core.service.executors.DefaultUncaughtExceptionHandler;
import com.lunova.moonbot.core.service.executors.ServiceExecutor;
import com.lunova.moonbot.core.service.executors.ServiceExecutor;
import com.lunova.moonbot.core.service.executors.ThreadFactoryConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
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
        //add methods to interact with Stack Overflow API
        //retrieve questions, answers, etc.
    }