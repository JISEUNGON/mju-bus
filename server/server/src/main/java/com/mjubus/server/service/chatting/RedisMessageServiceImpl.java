package com.mjubus.server.service.chatting;

import com.mjubus.server.domain.Member;
import com.mjubus.server.domain.TaxiParty;
import com.mjubus.server.domain.TaxiPartyMembers;
import com.mjubus.server.dto.request.TaxiPartyDeleteRequest;
import com.mjubus.server.dto.request.TaxiPartyQuitRequest;
import com.mjubus.server.exception.TaxiParty.TaxiPartyNotFoundException;
import com.mjubus.server.exception.chatting.RoomIdNotFoundExcption;
import com.mjubus.server.exception.chatting.SessionIdNotFoundExcption;
import com.mjubus.server.repository.TaxiPartyMembersRepository;
import com.mjubus.server.repository.TaxiPartyRepository;
import com.mjubus.server.service.taxiPartyMembers.TaxiPartyMembersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class RedisMessageServiceImpl implements RedisMessageService {

    private RedisTemplate<String, Object> redisTemplate;
    private final TaxiPartyRepository taxiPartyRepository;
    private final TaxiPartyMembersService taxiPartyMembersService;

    public RedisMessageServiceImpl(RedisTemplate<String, Object> redisTemplate, TaxiPartyRepository taxiPartyRepository, TaxiPartyMembersService taxiPartyMembersService) {
        this.redisTemplate = redisTemplate;
        this.taxiPartyRepository = taxiPartyRepository;
        this.taxiPartyMembersService = taxiPartyMembersService;
    }

    @Transactional
    @Override
    public void chattingRoomQuit(Long groupId, Member member) {
        String subscriptionId = "sub-" + member.getId();

        String hashName = "room-" + groupId + "-subscription";
        if (Boolean.FALSE.equals(redisTemplate.hasKey(hashName))) {
            throw new RoomIdNotFoundExcption("해당하는 roomId가 존재하지 않습니다.");
        }

        redisTemplate.opsForHash().delete(hashName, subscriptionId);
    }

    @Transactional
    @Override
    public void chattingRoomDelete(TaxiPartyDeleteRequest taxiPartyDeleteRequest) {
        Optional<TaxiParty> taxiParty = taxiPartyRepository.findById(taxiPartyDeleteRequest.getGroupId());
        taxiParty.orElseThrow(() -> new TaxiPartyNotFoundException(taxiPartyDeleteRequest.getGroupId()));
        redisTemplate.delete("room-" + taxiPartyDeleteRequest.getGroupId() + "-subscription");
    }
}
