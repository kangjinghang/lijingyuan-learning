package top.lijingyuan.springboot.test.service;

import org.springframework.boot.test.context.TestConfiguration;

/**
 * {@code @TestConfiguration} 注解是在执行单元和集成测试时提供特定于测试的配置和bean的非常有用的方法
 * <p>
 * 最好的事情是这些配置不会自动成为应用程序主配置的一部分。仅可使用 @Import 注解或static nested classes按需使用它们
 * <p>
 */
@TestConfiguration
public class MyTestConfiguration {

    //tests specific beans
//    @Bean
//    DataSource createDataSource(){
//        //
//    }
}