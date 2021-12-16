package com.handsone.restAPI.controller;

import com.handsone.restAPI.domain.DogFound;
import com.handsone.restAPI.domain.DogLost;
import com.handsone.restAPI.dto.RequestAI;
import com.handsone.restAPI.property.AiModelWebProperties;
import com.handsone.restAPI.service.DogFoundService;
import com.handsone.restAPI.service.DogLostService;
import com.handsone.restAPI.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Component @Slf4j
@RequiredArgsConstructor
public class AiModelClient {
    private final AiModelWebProperties aiModelWebProperties;
    private final DogLostService dogLostService;
    private final DogFoundService dogFoundService;
    private final MemberService memberService;

    private UriComponents getUriComponents() {
        return UriComponentsBuilder.newInstance()
                .scheme(aiModelWebProperties.getScheme())
                .host(aiModelWebProperties.getHost())
                .port(aiModelWebProperties.getPort())
                .path(aiModelWebProperties.getBaseUrl())
                .build();
    }

    public void getDogLostAndUpdateDogBreed(RequestAI requestAI) {
        log.debug("DogId=[{}]", requestAI.getDogId());
        log.debug("FileUri=[{}]", requestAI.getFileUri());

        UriComponents baseUri = getUriComponents();
        log.info("requestBaseUri=[{}]", baseUri.toUriString());

        WebClient webClient = WebClient.create(baseUri.toUriString());
        webClient.post()
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(requestAI)
                .retrieve()
                .bodyToMono(RequestAI.class)
                .subscribe(r -> {
                    //DogFound 등록시 dogBreed를 인공지능 모델을 통해서 찾고 그 결과를 db에 update,
                    //그리고 DogLost에 등록된 dogBreed들을 찾아서 결과를 뿌림
                    // *DogFound 현재 MANAGED 상태
                    log.debug("createdDogBreed's EstimatedResult=[{}]", r.getDogBreed());
                    DogFound update = dogFoundService.update(requestAI.getDogId(), r.getDogBreed());
                    log.debug("Result=[{}]", update.getDogBreed());

                    log.info("FOUND! pk=[{}], dogBreed=[{}]", update.getId(), update.getDogBreed());
                    log.info("RedirectUrl=[/dog-found/{}]", update.getId());
                    List<DogLost> result = dogLostService.findAllByDogBreed(update.getDogBreed());
                    for (DogLost dogLost : result) {
                        log.info("LOST pk=[{}]", dogLost.getId());
                    }
                });
    }

    public void getDogFoundByDogBreed(RequestAI requestAI) {
        log.debug("DogId=[{}]", requestAI.getDogId());
        log.debug("FileUri=[{}]", requestAI.getFileUri());

        UriComponents baseUri = getUriComponents();
        log.info("requestBaseUri=[{}]", baseUri.toUriString());

        WebClient webClient = WebClient.create(baseUri.toUriString());
        webClient.post()
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(requestAI)
                .retrieve()
                .bodyToMono(RequestAI.class)
                .subscribe(r -> {
                    //DogLost 등록시 기존 DB 에 있는 dogFound의 dogBreed를 찾아서 결과를 뿌림.
                    log.debug("DogLost.dogBreed=[{}]", r.getDogBreed());
                    DogLost update = dogLostService.update(requestAI.getDogId(), r.getDogBreed());
                    log.debug("Result=[{}]", update.getDogBreed());
                });
    }
}
