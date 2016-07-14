package org.agp8x.android.logging.myloggingapplication;

import android.content.Context;
import android.support.annotation.NonNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.FileAppender;
import ch.qos.logback.core.joran.spi.JoranException;

/**
 * Created by agp8x on 14.07.16.
 */
@SuppressWarnings("deprecation")
public class LogUtil {
    private static boolean _configured;

    private static void configLog() {
        if (_configured) {
            return;
        }
        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        lc.reset();
        String pattern = "%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n";
        ConsoleAppender<ILoggingEvent> a = buildConsoleAppender(lc, pattern);
        FileAppender<ILoggingEvent> f = buildFileAppender(lc, pattern);
        ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        root.addAppender(a);
        root.addAppender(f);
        root.setLevel(Level.ERROR);
        _configured = true;
    }

    private static FileAppender<ILoggingEvent> buildFileAppender(LoggerContext lc, String pattern) {
        FileAppender<ILoggingEvent> f = new FileAppender<>();
        f.setContext(lc);
        f.setFile("logs/myLog.log");
        f.setEncoder(buildEncoder(lc, pattern));
        f.start();
        return f;
    }

    @NonNull
    private static ConsoleAppender<ILoggingEvent> buildConsoleAppender(LoggerContext lc, String pattern) {
        ConsoleAppender<ILoggingEvent> a = new ConsoleAppender<>();
        a.setContext(lc);
        a.setEncoder(buildEncoder(lc, pattern));
        a.start();
        return a;
    }

    @NonNull
    private static PatternLayoutEncoder buildEncoder(LoggerContext lc, String pattern) {
        PatternLayoutEncoder enc = new PatternLayoutEncoder();
        enc.setContext(lc);
        enc.setPattern(pattern);
        enc.start();
        return enc;
    }

    private static void configLogByFiles(Context context) {
        if (_configured) {
            return;
        }
        InputStream is;
        try {
            if (context == null) {
                is = new FileInputStream("src/test/resources/logback-test.xml");
            } else {
                is = context.getAssets().open("logback.xml");
            }
        } catch (IOException e) {
            _configured=true;
            System.err.println(e.toString());
            return;
        }
        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        lc.reset();
        JoranConfigurator conf = new JoranConfigurator();
        conf.setContext(lc);
        try {
            conf.doConfigure(is);
        } catch (JoranException e) {
            e.printStackTrace();
        }
        _configured = true;
    }

    public static Logger getLog(Context context) {
        configLogByFiles(context);
        return LoggerFactory.getLogger(context.getClass());
    }

    public static Logger getLog(Class cls) {
        configLog();
        return LoggerFactory.getLogger(cls);
    }
}
