/**
 * Defines the core module for the Moon Bot application.
 *
 * <p>This module provides the foundational classes and interfaces that constitute the central
 * framework of the application. It is designed to support plugin-style additions, allowing for
 * extensibility and modular feature integration.
 *
 * <p>Future modules can depend on this core module to build additional features and plugins for the
 * Moon Bot application.
 *
 * @author Drake - <a href="https://github.com/metorrite">GitHub</a>
 * @since 11.11.2023
 */
module com.lunova.moonbot.core {
    exports com.lunova.moonbot.core.plugin;
    exports com.lunova.moonbot.core.services.plugin;
    exports com.lunova.moonbot.core.exceptions;
    requires net.dv8tion.jda;
    requires spark.core;
    requires com.google.gson;
    requires jakarta.xml.bind;
    requires jakarta.activation;
    opens com.lunova.moonbot.core.services.plugin to com.google.gson;
    opens com.lunova.moonbot.core.plugin to jakarta.xml.bind;

    requires org.slf4j;
    requires ch.qos.logback.classic;
    requires ch.qos.logback.core;
    requires com.google.common;
    requires annotations;
}
