package com.niftyengineer.niftymeals.controller;

import com.niftyengineer.niftymeals.dto.requestmodels.AdminQuestionRequest;
import com.niftyengineer.niftymeals.entity.Message;
import com.niftyengineer.niftymeals.service.MessagesService;
import com.niftyengineer.niftymeals.utils.JWTExtraction;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("https://localhost:3000")
@RestController
@RequestMapping("/api/messages")
public class MessagesController {

    private MessagesService messagesService;

    public MessagesController(MessagesService messagesService) {
        this.messagesService = messagesService;
    }

    @PostMapping("/member/add/message")
    public void postMessage(@RequestHeader(value="Authorization") String token,
                            @RequestBody Message messageRequest) {
        String userEmail = JWTExtraction.extractJWTPayload(token, "\"sub\"");
        messagesService.postMessage(messageRequest, userEmail);
    }

    @PutMapping("/member/admin/message")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void putMessage(@RequestHeader(value="Authorization") String token,
                           @RequestBody AdminQuestionRequest adminQuestionRequest) throws Exception {
        String userEmail = JWTExtraction.extractJWTPayload(token, "\"sub\"");
        messagesService.putMessage(adminQuestionRequest, userEmail);
    }
}