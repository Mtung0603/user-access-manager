package com.r2s.util;

import org.slf4j.LoggerFactory;

import org.slf4j.Logger;

public class LoggerUtil {
    public static Logger getLogger(Class<?> clazz) {
          return LoggerFactory.getLogger(clazz);
    }
}
