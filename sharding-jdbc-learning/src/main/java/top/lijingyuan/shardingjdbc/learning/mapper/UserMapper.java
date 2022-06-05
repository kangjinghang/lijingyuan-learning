package top.lijingyuan.shardingjdbc.learning.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import top.lijingyuan.shardingjdbc.learning.entity.User;

@Repository
public interface UserMapper extends BaseMapper<User> {
}
