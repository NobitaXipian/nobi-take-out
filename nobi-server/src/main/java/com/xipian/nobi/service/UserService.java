package com.xipian.nobi.service;

import com.xipian.nobi.dto.UserLoginDTO;
import com.xipian.nobi.entity.User;

/**
 * @author xipian
 * @date 2023/9/26
 */
public interface UserService {

    /**
     * 微信登录
     * @param userLoginDTO
     * @return
     */
    User wxLogin(UserLoginDTO userLoginDTO);
}
