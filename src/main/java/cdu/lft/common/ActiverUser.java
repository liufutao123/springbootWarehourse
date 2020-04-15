package cdu.lft.common;

import cdu.lft.sys.bean.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author 刘 福桃
 * TODO：
 * date 2020-03-28 23:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActiverUser {

        private User user;
        private List<String> roles;
        private List<String> permissions;
}
