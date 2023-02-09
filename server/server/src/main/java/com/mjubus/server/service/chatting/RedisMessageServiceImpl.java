package com.mjubus.server.service.chatting;

import com.mjubus.server.domain.TaxiParty;
import com.mjubus.server.domain.TaxiPartyMembers;
import com.mjubus.server.dto.request.TaxiPartyDeleteRequest;
import com.mjubus.server.dto.request.TaxiPartyQuitRequest;
import com.mjubus.server.exception.chatting.RoomIdNotFoundExcption;
import com.mjubus.server.exception.chatting.SessionIdNotFoundExcption;
import com.mjubus.server.repository.TaxiPartyMembersRepository;
import com.mjubus.server.repository.TaxiPartyRepository;
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
    private final TaxiPartyMembersRepository partyMembersRepository;

    public RedisMessageServiceImpl(RedisTemplate<String, Object> redisTemplate, TaxiPartyRepository taxiPartyRepository, TaxiPartyMembersRepository partyMembersRepository) {
        this.redisTemplate = redisTemplate;
        this.taxiPartyRepository = taxiPartyRepository;
        this.partyMembersRepository = partyMembersRepository;
    }

    @Transactional
    @Override
    public void chattingRoomQuit(Long groupId, TaxiPartyQuitRequest taxiPartyQuitRequest) {
        String sessionMatchingKey = "sub-" + taxiPartyQuitRequest.getMemberId();
        Optional<Object> sessionIdGet = Optional.ofNullable(redisTemplate.opsForHash().get("session-matching", sessionMatchingKey));
        if (sessionIdGet.isEmpty()) throw new SessionIdNotFoundExcption("해당하는 hash key가 존재하지 않습니다.");
        String sessionId = (String) sessionIdGet.get();

        String hashName = "room-" + groupId + "-subscription";
        if (!redisTemplate.hasKey(hashName)) throw new RoomIdNotFoundExcption("해당하는 roomId가 존재하지 않습니다.");

        redisTemplate.opsForHash().delete(hashName, sessionId);
        redisTemplate.opsForHash().delete("session-matching", sessionMatchingKey);
    }

    @Transactional
    @Override
    public void chattingRoomAndSessionDelete(TaxiPartyDeleteRequest taxiPartyDeleteRequest) {
        Optional<TaxiParty> taxiParty = taxiPartyRepository.findById(taxiPartyDeleteRequest.getGroupId());
        if (taxiParty.isEmpty()) throw new IllegalArgumentException("해당하는 파티가 없습니다.");
        redisTemplate.delete("room-" + taxiPartyDeleteRequest.getGroupId() + "-subscription");

        List<TaxiPartyMembers> partyMembers = partyMembersRepository.findTaxiPartyMembersByTaxiParty_Id(taxiPartyDeleteRequest.getGroupId());
        partyMembers.forEach((partyMember -> {
            redisTemplate.opsForHash().delete("session-matching", "sub-" + partyMember.getMember().getId());
        }));
    }
}
