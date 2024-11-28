package com.anon.backend.mapper;

import com.anon.backend.entity.Tag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * tag can be filters and traced, so it is required to be a independent table Mapper Interface
 *
 * @author anon
 * @since 2024-10-24
 */
public interface TagMapper extends BaseMapper<Tag> {

}
