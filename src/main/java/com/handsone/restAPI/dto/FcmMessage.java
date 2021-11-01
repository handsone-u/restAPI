package com.handsone.restAPI.dto;

import com.google.firebase.messaging.Message;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class FcmMessage {
    private boolean validateOnly;
    private Message message;

    @Builder @AllArgsConstructor
    static class Notification{
        private String title;
        private String body;
        private String image;
    }
}
