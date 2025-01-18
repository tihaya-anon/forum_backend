package com.anon.backend.listener;

import com.anon.backend.common.constant.CURD;
import com.anon.backend.entity.RefPostTag;
import com.anon.backend.entity.Tag;
import com.anon.backend.payload.dto.post.PostTagDto;
import com.anon.backend.service.IRefPostTagService;
import com.anon.backend.service.ITagService;
import com.anon.backend.service.util.DBOperation;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Component
@AllArgsConstructor
public class TagListener {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  private final ITagService tagService;
  private final IRefPostTagService refPostTagService;
  private final DBOperation dbOperation = new DBOperation(logger);

  @Bean("tagAdd")
  @Transactional
  public Consumer<PostTagDto> create() {
    return dto -> {
      for (String content : dto.getTags()) {
        Tag tag = tagService.getOne(new QueryWrapper<Tag>().eq("content", content));
        if (tag == null) {
          tag = new Tag().setContent(content);
          Tag finalTag = tag;
          dbOperation.performWithCheck(CURD.CREATE, () -> tagService.save(finalTag));
        }
        RefPostTag refPostTag = new RefPostTag().setPostId(dto.getId()).setTagId(tag.getId());
        dbOperation.performWithCheck(CURD.CREATE, () -> refPostTagService.save(refPostTag));
      }
    };
  }

  //  @Bean("tagRemove")
  //  @Transactional
  //  public Consumer<Long> delete() {
  //    return id -> {
  //      QueryWrapper<RefPostTag> queryWrapper = new QueryWrapper<RefPostTag>().eq("post_id", id);
  //      BaseMapper<RefPostTag> refPostTagBaseMapper = refPostTagService.getBaseMapper();
  //      List<RefPostTag> refPostTagList =
  //          dbOperation.perform(CURD.READ, () -> refPostTagBaseMapper.selectList(queryWrapper));
  //      ArrayList<Long> refPostTagIds = new ArrayList<>();
  //      refPostTagList.forEach(refPostTag -> refPostTagIds.add(refPostTag.getId()));
  //      dbOperation.performWithCheck(CURD.DELETE, () ->
  // refPostTagService.removeByIds(refPostTagIds));
  //    };
  //  }
}
