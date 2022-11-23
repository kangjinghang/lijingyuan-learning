package top.lijingyuan.webflux.learning.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Customer
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2022-10-30
 * @since 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    private int id;
    private String name;

}
