package cdu.lft;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages ={"cdu.lft.dao"})
public class SpringbootwarehouseApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootwarehouseApplication.class, args);
    }

}
