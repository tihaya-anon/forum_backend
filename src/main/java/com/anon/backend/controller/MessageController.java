package com.anon.backend.controller;

import com.anon.backend.common.constant.MessageEnum;
import com.anon.backend.common.constant.StatusCodeEnum;
import com.anon.backend.common.req.PageReq;
import com.anon.backend.common.resp.RestResp;
import com.anon.backend.map.MessageMap;
import com.anon.backend.payload.dto.message.MessageSendDto;
import com.anon.backend.payload.vo.message.MessageHistoryVo;
import com.anon.backend.payload.vo.message.MessageReceiveVo;
import com.anon.backend.payload.vo.message.MessageSendVo;
import com.anon.backend.service.IMessageService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller
 *
 * @author anon
 * @since 2024-10-22
 */
@RestController
@RequestMapping("/message/{id}")
public class MessageController {
  private final IMessageService messageService;

  public MessageController(IMessageService messageService) {
    this.messageService = messageService;
  }

  @Operation(summary = "send message to")
  @PostMapping("/send/{to}")
  public RestResp<Void> send(
      @PathVariable long id, @PathVariable long to, @Valid @RequestBody MessageSendVo vo) {
    if (id == to) {
      return RestResp.fail(StatusCodeEnum.VALIDATION_ERROR).setMsg(MessageEnum.CONTACT_SELF_FORBID);
    }
    MessageSendDto dto = MessageMap.INSTANCE.sendVo2SendDto(vo);
    dto.setSender(id);
    dto.setReceiver(to);
    messageService.create(dto);
    return RestResp.success();
  }

  @Operation(summary = "receive message regardless sender")
  @GetMapping("/receive")
  public RestResp<?> receive(@PathVariable long id, @RequestParam int all, PageReq pageReq) {
    List<MessageReceiveVo> receiveVoList = messageService.read(id, all != 0, pageReq);
    return RestResp.allowNull(receiveVoList, MessageEnum.NO_MESSAGE_RECEIVED);
  }

  @Operation(summary = "fetch history")
  @GetMapping("/history/{with}")
  public RestResp<?> history(@PathVariable long id, @PathVariable long with, PageReq pageReq) {
    if (id == with) {
      return RestResp.fail(StatusCodeEnum.VALIDATION_ERROR).setMsg(MessageEnum.CONTACT_SELF_FORBID);
    }
    List<MessageHistoryVo> historyVoList = messageService.history(id, with, pageReq);
    return RestResp.allowNull(historyVoList, MessageEnum.NO_MESSAGE_HISTORY);
  }

  @Operation(summary = "get symmetric key for the conversation")
  @GetMapping("/symmetric_key/{to}")
  public RestResp<?> fetchPubKey(@PathVariable long id, @PathVariable long to) {
    if (id == to) {
      return RestResp.fail(StatusCodeEnum.VALIDATION_ERROR).setMsg(MessageEnum.CONTACT_SELF_FORBID);
    }
    String pubKey = messageService.readRecipientKey(id, to);
    return RestResp.success(pubKey);
  }
}
