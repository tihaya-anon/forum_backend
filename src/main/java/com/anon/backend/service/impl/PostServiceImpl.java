package com.anon.backend.service.impl;

import com.anon.backend.payload.dto.post.PostPublishDto;
import com.anon.backend.payload.dto.post.PostTagDto;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.anon.backend.common.constant.CURD;
import com.anon.backend.common.req.PageReq;
import com.anon.backend.entity.Post;
import com.anon.backend.entity.RefPostTag;
import com.anon.backend.map.PostMap;
import com.anon.backend.mapper.PostMapper;
import com.anon.backend.payload.vo.post.PostPersistVo;
import com.anon.backend.service.IPostService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.anon.backend.service.IRefPostTagService;
import com.anon.backend.service.util.DBOperation;
import com.anon.backend.service.util.PageOperation;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
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
@AllArgsConstructor
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements IPostService {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  private final DBOperation dbOperation = new DBOperation(logger);
  private final PageOperation<Post> pageOperation = new PageOperation<>(logger, baseMapper);
  private final IRefPostTagService refPostTagService;
  private final StreamBridge bridge;

  @Override
  public void create(@NotNull PostPublishDto dto) {
    Post post = PostMap.INSTANCE.publishDto2Post(dto);
    dbOperation.performWithCheck(CURD.CREATE, () -> this.save(post));
    PostTagDto postTagDto = PostMap.INSTANCE.publishDto2TagDto(dto);
    postTagDto.setId(post.getId());
    bridge.send("tagAdd-out-0", postTagDto);
  }

  @Override
  @Transactional
  public void delete(long id) {
    dbOperation.performWithCheck(CURD.DELETE, () -> this.removeById(id));
    QueryWrapper<RefPostTag> queryWrapper = new QueryWrapper<RefPostTag>().eq("post_id", id);
    BaseMapper<RefPostTag> refPostTagBaseMapper = refPostTagService.getBaseMapper();
    List<RefPostTag> refPostTagList =
        dbOperation.perform(CURD.READ, () -> refPostTagBaseMapper.selectList(queryWrapper));
    ArrayList<Long> refPostTagIds = new ArrayList<>();
    refPostTagList.forEach(refPostTag -> refPostTagIds.add(refPostTag.getId()));
    dbOperation.performWithCheck(CURD.DELETE, () -> refPostTagService.removeByIds(refPostTagIds));
  }

  @Override
  public List<PostPersistVo> filterByAuthor(long author, @NotNull PageReq pageReq) {
    QueryWrapper<Post> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("author", author).eq("is_anonymous", 0).orderByDesc("create_at");
    List<Post> postList = pageOperation.paginate(pageReq, queryWrapper);
    if (postList.isEmpty()) {
      return null;
    }
    return postList.stream().map(PostMap.INSTANCE::post2PersistVo).toList();
  }

  @Override
  public List<PostPersistVo> filterByTag(String tagContent, @NotNull PageReq pageReq) {
    Page<Post> page = new Page<>(pageReq.getPageIdx(), pageReq.getPageSize());
    List<Post> postList =
        dbOperation
            .perform(CURD.READ, () -> baseMapper.readPostByTag(page, tagContent))
            .getRecords();
    if (postList.isEmpty()) {
      return null;
    }
    return postList.stream().map(PostMap.INSTANCE::post2PersistVo).toList();
  }

  public @NotNull List<PostPersistVo> listRecent(@NotNull PageReq pageReq) {
    QueryWrapper<Post> queryWrapper = new QueryWrapper<>();
    queryWrapper.orderByDesc("create_at");
    List<Post> postList = pageOperation.paginate(pageReq, queryWrapper);
    return postList.stream()
        .peek(
            post -> {
              if (post.getIsAnonymous()) {
                post.setAuthor(null);
              }
            })
        .map(PostMap.INSTANCE::post2PersistVo)
        .toList();
  }
}
