package com.itheima;

import com.itheima.service.StudentService;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class StuServerApplication {
    public static void main(String[] args) throws Exception {

        try (ThreadPoolExecutor executor = new ThreadPoolExecutor(200, 500, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(200), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
             ServerSocket serverSocket = new ServerSocket(2020)) {
            while (true) {
                Socket socket = serverSocket.accept();
                executor.execute(new StudentService(socket));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
