package com.akmal4163.httpserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Logger;

import static java.lang.Thread.sleep;

public class HttpThread implements Runnable {

    private static final Logger LOGGER = Logger.getLogger(HttpThread.class.getName());
    private Socket socket;

    public HttpThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = socket.getInputStream();
            os = socket.getOutputStream();

            int _byte;
            while((_byte = is.read()) >= 0) {
                System.out.print((char)_byte);
            }

            String html = Files.readString(Paths.get("./src/index.html"));

            final String CRLF = "\n\r";
            String response = "HTTP/1.1 200 OK" + CRLF + "Content-length: " + html.getBytes().length + CRLF +
                    CRLF + html +
                    CRLF + CRLF;

            os.write(response.getBytes());


            try {
                sleep(6000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LOGGER.info("connection finished...");
        } catch (IOException e) {
            LOGGER.severe("problem with connection");
            e.printStackTrace();
        } finally {
            if(is != null && os != null && socket != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

    }
}
