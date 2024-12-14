package com.anon.backend.controller;

import com.anon.backend.common.constant.MessageEnum;
import com.anon.backend.common.constant.StatusCodeEnum;
import com.anon.backend.common.req.PageReq;
import com.anon.backend.common.resp.RestResp;
import com.anon.backend.map.MessageMap;
import com.anon.backend.dto.message.MessageHistoryDto;
import com.anon.backend.dto.message.MessageReceiveDto;
import com.anon.backend.dto.message.MessageSendDto;
import com.anon.backend.model.message.MessageSendModel;
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
      @PathVariable int id, @PathVariable int to, @Valid @RequestBody MessageSendDto dto) {
    if (id == to) {
      return RestResp.fail(StatusCodeEnum.VALIDATION_ERROR).setMsg(MessageEnum.CONTACT_SELF_FORBID);
    }
    MessageSendModel model = MessageMap.INSTANCE.sendVo2sendDto(dto);
    model.setSender(id);
    model.setReceiver(to);
    messageService.create(model);
    return RestResp.success();
  }

  @Operation(summary = "receive message regardless sender")
  @GetMapping("/receive")
  public RestResp<?> receive(@PathVariable int id, @RequestParam int all, PageReq pageReq) {
    List<MessageReceiveDto> receiveVoList = messageService.read(id, all != 0, pageReq);
    return RestResp.allowNull(receiveVoList, MessageEnum.NO_MESSAGE_RECEIVED);
  }

  @Operation(summary = "fetch history")
  @GetMapping("/history/{with}")
  public RestResp<?> history(@PathVariable int id, @PathVariable int with, PageReq pageReq) {
    if (id == with) {
      return RestResp.fail(StatusCodeEnum.VALIDATION_ERROR).setMsg(MessageEnum.CONTACT_SELF_FORBID);
    }
    List<MessageHistoryDto> historyVoList = messageService.history(id, with, pageReq);
    return RestResp.allowNull(historyVoList, MessageEnum.NO_MESSAGE_HISTORY);
  }

  @Operation(summary = "get symmetric key for the conversation")
  @GetMapping("/symmetric_key/{to}")
  public RestResp<?> fetchPubKey(@PathVariable int id, @PathVariable int to) {
    if (id == to) {
      return RestResp.fail(StatusCodeEnum.VALIDATION_ERROR).setMsg(MessageEnum.CONTACT_SELF_FORBID);
    }
    String pubKey = messageService.readRecipientKey(id, to);
    return RestResp.success(pubKey);
  }
}
