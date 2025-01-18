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
@RequestMapping("/message/{sender}")
public class MessageController {
  private final IMessageService messageService;

  public MessageController(IMessageService messageService) {
    this.messageService = messageService;
  }

  @Operation(summary = "send message to")
  @PostMapping("/send/{receiver}")
  public RestResp<Void> send(
      @PathVariable long sender,
      @PathVariable long receiver,
      @Valid @RequestBody MessageSendVo vo) {
    if (sender == receiver) {
      return RestResp.fail(StatusCodeEnum.VALIDATION_ERROR).setMsg(MessageEnum.CONTACT_SELF_FORBID);
    }
    MessageSendDto dto =
        MessageMap.INSTANCE.sendVo2SendDto(vo).setSender(sender).setReceiver(receiver);
    messageService.create(dto);
    return RestResp.success();
  }

  @Operation(summary = "receive message regardless sender")
  @GetMapping("/receive")
  public RestResp<?> receive(@PathVariable long sender, @RequestParam int all, PageReq pageReq) {
    List<MessageReceiveVo> receiveVoList = messageService.read(sender, all != 0, pageReq);
    return RestResp.allowNull(receiveVoList, MessageEnum.NO_MESSAGE_RECEIVED);
  }

  @Operation(summary = "fetch history")
  @GetMapping("/history/{receiver}")
  public RestResp<?> history(
      @PathVariable long sender, @PathVariable long receiver, PageReq pageReq) {
    if (sender == receiver) {
      return RestResp.fail(StatusCodeEnum.VALIDATION_ERROR).setMsg(MessageEnum.CONTACT_SELF_FORBID);
    }
    List<MessageHistoryVo> historyVoList = messageService.history(sender, receiver, pageReq);
    return RestResp.allowNull(historyVoList, MessageEnum.NO_MESSAGE_HISTORY);
  }

  @Operation(summary = "get symmetric key for the conversation")
  @GetMapping("/symmetric_key/{receiver}")
  public RestResp<?> fetchPubKey(@PathVariable long sender, @PathVariable long receiver) {
    if (sender == receiver) {
      return RestResp.fail(StatusCodeEnum.VALIDATION_ERROR).setMsg(MessageEnum.CONTACT_SELF_FORBID);
    }
    String pubKey = messageService.readRecipientKey(sender, receiver);
    return RestResp.success(pubKey);
  }
}
