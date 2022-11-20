package com.akmal4163.httpserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;


public class Listener implements Runnable {
    private static final Logger LOGGER = Logger.getLogger(Listener.class.getName());
    int port;
    private ServerSocket serversocket;

    public Listener(int port) {
        this.port = port;
        try {
            this.serversocket = new ServerSocket(this.port);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
         try {
             while(serversocket.isBound() && !serversocket.isClosed()) {
                 Socket socket = serversocket.accept();
                 LOGGER.info("accepted at:" + socket.getInetAddress());

                 HttpThread hthread = new HttpThread(socket);
                 Thread thread = new Thread(hthread);
                 thread.start();
             }
         } catch (IOException e) {
             LOGGER.severe("problem with sockets");
             e.printStackTrace();
         }

         if (serversocket != null) {
             try {
                 serversocket.close();
             } catch (IOException e) {
                     e.printStackTrace();
             }
         }

    }
}
