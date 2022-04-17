package top.lijingyuan.guava.learning.eventbus;

import com.google.common.eventbus.EventBus;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class EventBusChat {

    public static void main(String[] args) {
        EventBus eventBus = new EventBus();
        ServerSocket socket;
        try {
            // 启动服务端
            socket = new ServerSocket(4444);
            while (true) {
                Socket connection = socket.accept();
                UserThread newUser = new UserThread(connection, eventBus);
                eventBus.register(newUser);
                newUser.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
