package top.lijingyuan.websocket.learning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * TomcatWebsocketApplication
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2022-01-06
 * @since 1.0.0
 */
@SpringBootApplication
@ServletComponentScan
public class TomcatWebsocketApplication {

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

    public static void main(String[] args) {
        SpringApplication.run(TomcatWebsocketApplication.class, args);
    }

}
