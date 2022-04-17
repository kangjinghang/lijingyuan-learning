package top.lijingyuan.guava.learning.eventbus;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class UserThread extends Thread {

    private Socket connection;
    private EventBus eventBus;
    private BufferedReader in;
    private PrintWriter out;

    public UserThread(Socket connection, EventBus eventBus) {
        this.connection = connection;
        this.eventBus = eventBus;
        try {
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            out = new PrintWriter(connection.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
    // 订阅消息
    @Subscribe
    public void receiveMessage(String message) {
        if (out != null) {
            // 向所有客户端广播消息
            out.println(message);
            System.out.println("receiveMessage:" + message);
        }
    }

    @Override
    public void run() {
        try {
            String input;
            while ((input = in.readLine()) != null) {
                // 发布消息
                eventBus.post(input);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //reached eof
        eventBus.unregister(this);
        try {
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        in = null;
        out = null;
    }
}