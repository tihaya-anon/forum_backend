package com.anon.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.anon.backend.entity.Message;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * Mapper Interface
 *
 * @author anon
 * @since 2024-10-24
 */
public interface MessageMapper extends BaseMapper<Message> {
  @Select(
      "SELECT user_a, user_b, key_a, key_b FROM util_session_key WHERE user_a = #{userA} AND user_b = #{userB}")
  Map<String, Object> readRecipientKey(@Param("userA") long userA, @Param("userB") long userB);

  @Insert(
      "INSERT INTO util_session_key (user_a, user_b, key_a, key_b) VALUES (#{userA}, #{userB}, #{keyA}, #{keyB})")
  int createRecipientKey(
      @Param("userA") long userA,
      @Param("userB") long userB,
      @Param("keyA") String keyA,
      @Param("keyB") String keyB);

  IPage<Message> readRecipientMessage(
      Page<Message> page, @Param("userA") long userA, @Param("userB") long userB);
}
