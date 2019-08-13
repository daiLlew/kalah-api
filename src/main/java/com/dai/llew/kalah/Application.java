package com.dai.llew.kalah;

import com.github.onsdigital.logging.v2.DPLogger;
import com.github.onsdigital.logging.v2.Logger;
import com.github.onsdigital.logging.v2.LoggerImpl;
import com.github.onsdigital.logging.v2.config.Builder;
import com.github.onsdigital.logging.v2.config.LogConfig;
import com.github.onsdigital.logging.v2.serializer.JacksonLogSerialiser;
import com.github.onsdigital.logging.v2.serializer.LogSerialiser;
import com.github.onsdigital.logging.v2.storage.LogStore;
import com.github.onsdigital.logging.v2.storage.MDCLogStore;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        DPLogger.init(getLogConfig());
        SpringApplication.run(Application.class, args);
    }

    private static LogConfig getLogConfig() {
        LogConfig config = null;
        try {
            LogSerialiser serialiser = new JacksonLogSerialiser(true);
            LogStore logStore = new MDCLogStore(serialiser);
            Logger logger = new LoggerImpl("kalah-game-api");
            config = new Builder()
                    .logStore(logStore)
                    .logger(logger)
                    .serialiser(serialiser)
                    .create();
        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(1);
        }
        return config;
    }
}

