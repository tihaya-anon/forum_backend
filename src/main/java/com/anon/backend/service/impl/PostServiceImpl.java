package com.anon.backend.service.impl;

import com.anon.backend.dto.post.PostUpdateDto;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.anon.backend.common.constant.CURD;
import com.anon.backend.common.req.PageReq;
import com.anon.backend.entity.Post;
import com.anon.backend.entity.RefPostTag;
import com.anon.backend.entity.Tag;
import com.anon.backend.map.PostMap;
import com.anon.backend.mapper.PostMapper;
import com.anon.backend.dto.post.PostPersistDto;
import com.anon.backend.dto.post.PostPublishDto;
import com.anon.backend.service.IPostService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.anon.backend.service.IRefPostTagService;
import com.anon.backend.service.ITagService;
import com.anon.backend.service.util.DBOperation;
import com.anon.backend.service.util.PageOperation;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * post is different from comment, for it is required to search posts fast, but it doesn't matter if
 * user wait when loading the posts' details Service Implement
 *
 * @author anon
 * @since 2024-10-22
 */
@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements IPostService {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  private final ITagService tagService;
  private final IRefPostTagService refPostTagService;

  public PostServiceImpl(ITagService tagService, IRefPostTagService refPostTagService) {
    this.tagService = tagService;
    this.refPostTagService = refPostTagService;
  }

  @Override
  @Transactional
  public void create(int author, @NotNull PostPublishDto dto) {
    String[] tagContents = dto.getTags();
    Post post = PostMap.INSTANCE.publishDto2Post(dto);
    post.setAuthor(author);
    DBOperation.performWithCheck(logger, CURD.CREATE, () -> this.save(post));
    for (String tagContent : tagContents) {
      Tag tag = tagService.getOne(new QueryWrapper<Tag>().eq("content", tagContent));
      if (tag == null) {
        tag = new Tag();
        tag.setContent(tagContent);
        Tag finalTag = tag;
        DBOperation.performWithCheck(logger, CURD.CREATE, () -> tagService.save(finalTag));
      }
      RefPostTag refPostTag = new RefPostTag();
      refPostTag.setPostId(post.getId());
      refPostTag.setTagId(tag.getId());
      DBOperation.performWithCheck(logger, CURD.CREATE, () -> refPostTagService.save(refPostTag));
    }
  }

  @Override
  @Transactional
  public void delete(int id) {
    DBOperation.performWithCheck(logger, CURD.DELETE, () -> this.removeById(id));

    QueryWrapper<RefPostTag> queryWrapper = new QueryWrapper<RefPostTag>().eq("post_id", id);
    BaseMapper<RefPostTag> refPostTagBaseMapper = refPostTagService.getBaseMapper();
    List<RefPostTag> refPostTagList =
        DBOperation.perform(logger, CURD.READ, () -> refPostTagBaseMapper.selectList(queryWrapper));
    ArrayList<Integer> refPostTagIds = new ArrayList<>();
    refPostTagList.forEach(refPostTag -> refPostTagIds.add(refPostTag.getId()));
    DBOperation.performWithCheck(
        logger, CURD.DELETE, () -> refPostTagService.removeByIds(refPostTagIds));
  }

  @Override
  public void update(PostUpdateDto dto) {
    Post post = PostMap.INSTANCE.updateDto2Post(dto);
    DBOperation.performWithCheck(logger, CURD.UPDATE, ()->this.updateById(post));
  }

  @Override
  public List<PostPersistDto> filterByAuthor(int author, PageReq pageReq) {
    QueryWrapper<Post> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("author", author).orderByDesc("create_at");
    List<Post> postList = PageOperation.paginate(logger, pageReq, null, baseMapper);
    if (postList.isEmpty()) {
      return null;
    }
    return addTags(postList);
  }

  @Override
  public List<PostPersistDto> filterByTag(String tagContent, @NotNull PageReq pageReq) {
    Page<Post> page = new Page<>(pageReq.getPageIdx(), pageReq.getPageSize());
    List<Post> postList =
        DBOperation.perform(logger, CURD.READ, () -> baseMapper.readPostByTag(page, tagContent))
            .getRecords();
    if (postList.isEmpty()) {
      return null;
    }
    return addTags(postList);
  }

  private @NotNull List<PostPersistDto> addTags(@NotNull List<Post> postList) {
    List<PostPersistDto> postPersistVoList = new ArrayList<>();
    for (Post post : postList) {
      List<String> tagContentList =
          DBOperation.perform(logger, CURD.READ, () -> baseMapper.readPostTags(post.getId()));
      PostPersistDto postPersistVo = PostMap.INSTANCE.post2PersistVo(post);
      postPersistVo.setTagList(tagContentList);
      postPersistVoList.add(postPersistVo);
    }
    return postPersistVoList;
  }
}
