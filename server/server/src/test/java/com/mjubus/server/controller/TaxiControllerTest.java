package com.mjubus.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mjubus.server.domain.TaxiParty;
import com.mjubus.server.dto.request.TaxiPartyCreateRequest;
import com.mjubus.server.dto.request.TaxiPartyJoinRequest;
import com.mjubus.server.repository.TaxiPartyMembersRepository;
import com.mjubus.server.repository.TaxiPartyRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.web.servlet.function.ServerResponse.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class TaxiControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    private TaxiPartyRepository taxiPartyRepository;

    private final TaxiPartyMembersRepository partyMembersRepository;

    @Autowired
    public TaxiControllerTest(MockMvc mockMvc, ObjectMapper objectMapper, TaxiPartyRepository taxiPartyRepository, TaxiPartyMembersRepository partyMembersRepository) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.taxiPartyRepository = taxiPartyRepository;
        this.partyMembersRepository = partyMembersRepository;
    }

    @Test
    @Transactional
    public void 새_파티_추가_성공() throws Exception {
        Long testId = 2L;

        TaxiPartyCreateRequest createRequest = TaxiPartyCreateRequest.builder()
                .administer(testId)
                .min(1L)
                .max(4L)
                .meetingLatitude(1234D)
                .meetingLongitude(5678D)
                .taxiDestinationId(1L)
                .endAt(LocalDateTime.now())
                .build();

        String content = objectMapper.writeValueAsString(createRequest);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        String url = "/taxi/create";

        mockMvc.perform(post(url).content(content).headers(headers))
                .andExpect(status().isOk())
                .andReturn();

        Optional<TaxiParty> byId = taxiPartyRepository.findById(7L);

        assertNotNull(byId);
        assertNotEquals(taxiPartyRepository.count(), 1L);
    }

    @Test
    @Transactional
    public void 새_파티_추가_실패_존재하는_파티() throws Exception {
        Long testId = 1L;

        TaxiPartyCreateRequest createRequest = TaxiPartyCreateRequest.builder()
                .administer(testId)
                .min(1L)
                .max(4L)
                .meetingLatitude(1234D)
                .meetingLongitude(5678D)
                .taxiDestinationId(1L)
                .endAt(LocalDateTime.now())
                .build();

        String content = objectMapper.writeValueAsString(createRequest);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        String url = "/taxi/create";

        mockMvc.perform(post(url).content(content).headers(headers))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    @Transactional
    public void 새_파티_추가_실패_택시_도착지_없() throws Exception {
        Long testId = 2L;
        Long taxiDest = 999L;

        TaxiPartyCreateRequest createRequest = TaxiPartyCreateRequest.builder()
                .administer(testId)
                .min(1L)
                .max(4L)
                .meetingLatitude(1234D)
                .meetingLongitude(5678D)
                .taxiDestinationId(taxiDest)
                .endAt(LocalDateTime.now())
                .build();

        String content = objectMapper.writeValueAsString(createRequest);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        String url = "/taxi/create";

        mockMvc.perform(post(url).content(content).headers(headers))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    @Transactional
    public void 새_멤버_추가_성공() throws Exception {
        Long groupId = 6L;
        Long memberId = 13L;

        String url = "/taxi/list/" + groupId + "/members/new";

        TaxiPartyJoinRequest request = TaxiPartyJoinRequest.of(memberId);

        String content = objectMapper.writeValueAsString(request);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        mockMvc.perform(post(url).content(content).headers(headers))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        assertEquals(partyMembersRepository.count(), 4L);
    }


    @Test
    @Transactional
    public void 새_멤버_추가_실패_없는파티() throws Exception {
        Long groupId = 999L;
        Long memberId = 13L;

        String url = "/taxi/list/" + groupId + "/members/new";

        TaxiPartyJoinRequest request = TaxiPartyJoinRequest.of(memberId);

        String content = objectMapper.writeValueAsString(request);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        mockMvc.perform(post(url).content(content).headers(headers))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void 새_멤버_추가_실_이미_존재하는멤버() throws Exception {
        Long groupId = 6L;
        Long memberId = 1L;

        String url = "/taxi/list/" + groupId + "/members/new";

        TaxiPartyJoinRequest request = TaxiPartyJoinRequest.of(memberId);

        String content = objectMapper.writeValueAsString(request);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        mockMvc.perform(post(url).content(content).headers(headers))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    public void 새_멤버_추가_실패_해당_멤버없음() throws Exception {
        Long groupId = 6L;
        Long memberId = 999L;

        String url = "/taxi/list/" + groupId + "/members/new";

        TaxiPartyJoinRequest request = TaxiPartyJoinRequest.of(memberId);

        String content = objectMapper.writeValueAsString(request);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        mockMvc.perform(post(url).content(content).headers(headers))
                .andExpect(status().isNotFound());
    }
}
