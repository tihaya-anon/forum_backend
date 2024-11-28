package com.anon.backend.mapper;

import com.anon.backend.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * when registering, user must provide its session id. Once session validation passed, use session id to generate a pair of keys Mapper Interface
 *
 * @author anon
 * @since 2024-10-24
 */
public interface UserMapper extends BaseMapper<User> {
}
