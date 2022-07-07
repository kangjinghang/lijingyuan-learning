package top.lijingyuan.shardingjdbc.learning;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.lijingyuan.shardingjdbc.learning.entity.Course;
import top.lijingyuan.shardingjdbc.learning.mapper.CourseMapper;

/**
 * ShardingJdbcApplicationTest
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2022-05-20
 * @since 1.0.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ShardingJdbcApplicationTest {

    @Autowired
    private CourseMapper courseMapper;

    @Test
    public void contextLoads() {
        System.out.println("load ok");
    }

    // =======================测试水平分表===================
    // 添加课程的方法
    @Test
    public void addCourse() {
        for (int i = 1; i <= 10; i++) {
            Course course = new Course();
            course.setCname("java" + i);
            course.setUserId(100L);
            course.setCstatus("Normal" + i);
            courseMapper.insert(course);
        }
    }

    // 查询课程的方法
    @Test
    public void findCourse() {
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        wrapper.eq("cid", 465114666322886656L);
        Course course = courseMapper.selectOne(wrapper);
        System.out.println(course);
    }

}