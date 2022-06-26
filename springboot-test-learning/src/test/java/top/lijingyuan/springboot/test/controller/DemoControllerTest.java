package top.lijingyuan.springboot.test.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import top.lijingyuan.springboot.test.repository.SubAccountRepository;

import javax.annotation.Resource;

/**
 * unit test for {@link DemoController}
 * <p>
 * 我们还可以使用nested @Configuration class或显式的@TestConfiguration类提供特定于测试的bean配置。
 * <p>
 * 它还为不同的webEnvironment模式提供支持，并在定义的或随机的端口上侦听正在运行的Web服务器。 它还注册了TestRestTemplate和/或WebTestClient bean，用于Web测试。
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2022-06-25
 * @since 1.0.0
 */
//@ExtendWith(SpringExtension.class) // 仅仅是 spring framework 的做法：将 Spring TestContext Framework集成到JUnit 5的Jupiter编程模型中。
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // 注解里带了 @ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
/**
 * @SpringBootTest 注解
 * <ul>
 *   <li>当不使用nested @Configuration class ，并且未指定显式类时，自动搜索@SpringBootConfiguration 。
 *   <li>允许使用properties属性定义custom environment properties属性。
 *   <li>提供对不同的webEnvironment模式的支持，包括启动在定义的或随机端口上侦听的完全运行的Web服务器的功能。
 *   <li>注册一个TestRestTemplate和/或WebTestClient bean，以在使用完全运行的Web服务器的Web测试中使用。
 * <ul>
 */
// 方式三：
//@ExtendWith(MockitoExtension.class)
// 方式四：
//@WebMvcTest(DemoController.class) //  它禁用完全auto-configuration ，而是仅应用与MVC测试相关的配置。它还会自动配置MockMvc实例。 通过将.class作为注释属性，我们只能初始化一个Web控制器。
// 方式五：
//@WebFluxTest // 此批注将禁用完全自动配置，而仅应用与WebFlux测试相关的配置。 默认情况下，用@WebFluxTest注释的测试还将自动配置WebTestClient
// 通常， @Import @MockBean与@MockBean或@MockBean结合使用以创建控制器bean所需的任何协作者。
        // @WebFluxTest(controllers = EmployeeController.class)
        //@Import(EmployeeService.class)
        //public class EmployeeControllerTest
        //{
        //    @MockBean
        //    EmployeeRepository repository;
        //
        //    @Autowired
        //    private WebTestClient webClient;
        //
        //    //tests
        //}


class DemoControllerTest {

    @Autowired
    private MockMvc mvc;

    @Resource
    private DemoController demoController;

    @MockBean
    private SubAccountRepository accountRepository;

    /**
     * @SpringBootTest 注解
     * <ul>
     *   <li>当不使用nested @Configuration class ，并且未指定显式类时，自动搜索@SpringBootConfiguration 。<li/>
     *   <li>允许使用properties属性定义custom environment properties属性。
     *   <li>提供对不同的webEnvironment模式的支持，包括启动在定义的或随机端口上侦听的完全运行的Web服务器的功能。
     *   <li>注册一个TestRestTemplate和/或WebTestClient bean，以在使用完全运行的Web服务器的Web测试中使用。
     * <ul>
     */
    @Test
    void testHello() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/demo/hello"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("hello world"));
    }

}