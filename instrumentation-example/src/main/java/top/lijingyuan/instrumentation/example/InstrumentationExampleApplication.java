package top.lijingyuan.instrumentation.example;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@SpringBootApplication
@Controller
public class InstrumentationExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(InstrumentationExampleApplication.class, args);
    }

    @GetMapping(value = "/hello-world")
    @ResponseBody
    public String sayHello() {
        return "hello, world";
    }

    @Bean
    MeterRegistryCustomizer<MeterRegistry> metricsCommonTags() {
        return registry -> registry.config().commonTags("application", "actuator-demo");
    }

}
