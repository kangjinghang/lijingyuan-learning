package top.lijingyuan.springboot.test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import top.lijingyuan.springboot.test.model.EmployeeVO;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * TestEmployeeRESTController
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2022-06-25
 * @since 1.0.0
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest(EmployeeController.class)
class TestEmployeeRESTController {

    @Autowired
    private MockMvc mvc;

    @Test
    void getAllEmployeesAPI() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get("/employees")
                        .header("X-COM-PERSIST", "x_com_value")
                        .header("X-COM-LOCATION", "US")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees[*].id").isNotEmpty());
    }

    @Test
    void getEmployeeByIdAPI() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get("/employees/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
    }

    @Test
    void createEmployeeAPI() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .post("/employees")
                        .content(asJsonString(new EmployeeVO(null, "firstName4", "lastName4", "email4@mail.com")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}