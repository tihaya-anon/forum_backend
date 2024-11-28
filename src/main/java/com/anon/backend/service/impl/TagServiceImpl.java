package com.anon.backend.service.impl;

import com.anon.backend.entity.Tag;
import com.anon.backend.mapper.TagMapper;
import com.anon.backend.service.ITagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * tag can be filters and traced, so it is required to be a independent table Service Implement
 *
 * @author anon
 * @since 2024-10-22
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements ITagService {

}
