package com.handsone.restAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class RequestAI {
    String fileUri;
    Long dogId;
    String dogBreed;
    String dogBoard;
}
