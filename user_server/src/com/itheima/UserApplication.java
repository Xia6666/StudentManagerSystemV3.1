package com.itheima;

import com.itheima.service.UserService;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class UserApplication {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(2121);
             ThreadPoolExecutor executor = new ThreadPoolExecutor(200, 500, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(200), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());) {
            while (true) {
                Socket socket = serverSocket.accept();
                executor.execute(new UserService(socket));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
