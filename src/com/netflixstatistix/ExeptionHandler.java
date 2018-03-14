package com.netflixstatistix;

public final class ExeptionHandler implements Thread.UncaughtExceptionHandler {

        @Override
        public void uncaughtException(Thread t, Throwable e) {
            System.out.printf("exception caught:" + e);
        }
    }
