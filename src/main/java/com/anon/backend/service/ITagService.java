package com.anon.backend.service;

import com.anon.backend.entity.Tag;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * tag can be filters and traced, so it is required to be a independent table Service
 *
 * @author anon
 * @since 2024-10-22
 */
public interface ITagService extends IService<Tag> {

}
