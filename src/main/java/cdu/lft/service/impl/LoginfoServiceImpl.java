package cdu.lft.service.impl;

import cdu.lft.bean.Loginfo;
import cdu.lft.dao.LoginfoMapper;
import cdu.lft.service.LoginfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 刘福桃
 * @since 2020-03-30
 */
@Service
@Transactional
public class LoginfoServiceImpl extends ServiceImpl<LoginfoMapper, Loginfo> implements LoginfoService {

}
