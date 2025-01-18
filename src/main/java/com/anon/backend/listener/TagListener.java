package com.anon.backend.listener;

import com.anon.backend.common.constant.CURD;
import com.anon.backend.entity.RefPostTag;
import com.anon.backend.entity.Tag;
import com.anon.backend.payload.dto.post.PostTagDto;
import com.anon.backend.service.IRefPostTagService;
import com.anon.backend.service.ITagService;
import com.anon.backend.service.util.DBOperation;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor
public class TagListener {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  private final ITagService tagService;
  private final IRefPostTagService refPostTagService;
  private final DBOperation dbOperation = new DBOperation(logger);

  @RabbitListener(queues = "tag")
  @Transactional
  public void create(PostTagDto dto) {
    System.out.println("run into listener");
    System.out.println(dto);
    for (String content : dto.getTags()) {
      Tag tag = tagService.getOne(new QueryWrapper<Tag>().eq("content", content));
      if (tag == null) {
        tag = new Tag();
        tag.setContent(content);
        Tag finalTag = tag;
        dbOperation.performWithCheck(CURD.CREATE, () -> tagService.save(finalTag));
      }
      RefPostTag refPostTag = new RefPostTag();
      refPostTag.setPostId(dto.getId());
      refPostTag.setTagId(tag.getId());
      dbOperation.performWithCheck(CURD.CREATE, () -> refPostTagService.save(refPostTag));
    }
  }
}
