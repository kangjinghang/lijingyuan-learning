package top.lijingyuan.springboot.test.model;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "employees")
@Data
public class EmployeeListVO {

    private List<EmployeeVO> employees = new ArrayList<>();

}