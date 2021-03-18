package first.first.Configuration;

import first.first.Aspect.WebSocketInterceptor;
import first.first.Handles.MyHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @创建人 weizc
 * @创建时间 2018/10/31 8:31
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(myHandler(), "/customer/webSocket").addInterceptors(new WebSocketInterceptor());
    }

    @Bean
    public WebSocketHandler myHandler() {
        return new MyHandler();
    }

}
