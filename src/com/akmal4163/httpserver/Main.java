package com.akmal4163.httpserver;
import java.util.logging.Logger;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    private static int port;

    public static void main(String[] args) {
        int port = 8000;
        LOGGER.info("server start at port: " + port);

        Listener listener = new Listener(port);
        listener.run();
        Thread listenerthread = new Thread(listener);
        listenerthread.start();
    }
}
