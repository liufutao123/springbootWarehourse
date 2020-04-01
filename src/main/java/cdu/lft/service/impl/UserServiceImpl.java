package cdu.lft.service.impl;

import cdu.lft.bean.Permission;
import cdu.lft.bean.User;
import cdu.lft.dao.UserMapper;
import cdu.lft.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 刘福桃
 * @since 2020-03-28
 */
@Service
@Transactional
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
