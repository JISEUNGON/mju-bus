package com.mjubus.server.service.taxiParty;

import com.mjubus.server.domain.Member;
import com.mjubus.server.dto.request.TaxiPartyCreateRequest;
import com.mjubus.server.dto.request.TaxiPartyRequest;
import com.mjubus.server.dto.response.TaxiPartyResponse;
import com.mjubus.server.enums.MemberRole;
import com.mjubus.server.exception.TaxiParty.TaxiPartyNotFoundException;
import com.mjubus.server.repository.MemberRepository;
import com.mjubus.server.util.DateHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@EnableJpaRepositories(basePackages = "com.mjubus.server.repository")
public class TaxiPartyServiceTest {

    @Autowired
    private TaxiPartyServiceImpl taxiPartyService;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeAll
    public static void setUp(@Autowired MemberRepository memberRepository, @Autowired TaxiPartyServiceImpl taxiPartyService) {
        // Member 10명 생성
//        for (int i = 0; i < 10; i++) {
//            Member member = Member.builder()
//                    .name("test-" + i)
//                    .profileImageUrl("test-" + i)
//                    .role(MemberRole.USER)
//                    .refreshToken("test-" + i)
//                    .refreshTokenExpiredAt(DateHandler.getToday().plusYears(1))
//                    .status(true)
//                    .build();
//            memberRepository.save(member);
//        }
//
//        // TaxiParty 10개 생성
//        for (int i = 0; i < 5; i++) {
//            TaxiPartyCreateRequest request = TaxiPartyCreateRequest.builder()
//                    .administer((long) i + 1)
//                    .min(2L)
//                    .max(3L)
//                    .taxiDestinationId((long) i + 1)
//                    .build();
//            taxiPartyService.createTaxiParty(request);
//        }
    }

    @Test
    @DisplayName("[/taxiParty] 택시파티 조회 성공 테스트")
    public void findTaxiParty() {
        // given
//        TaxiPartyCreateRequest taxiPartyRequest = TaxiPartyCreateRequest.builder()
//                .build();
//        taxiPartyService.createTaxiParty(taxiPartyRequest);
//
//        TaxiPartyRequest request = TaxiPartyRequest.builder()
//                .id(1L)
//                .build();
//
//        // when
////        TaxiPartyResponse taxiPartyResponse = taxiPartyService.findTaxiParty(request);
//
//        // then
//        assertNotNull(taxiPartyResponse);
//        assertEquals(1L, taxiPartyResponse.getId());
    }

    @Test
    @DisplayName("[/taxiParty] 택시파티 조회 실패 테스트")
    public void findTaxiParty2() {
        // given
//        TaxiPartyRequest request = TaxiPartyRequest.builder()
//                .id(999L)
//                .build();
//        // when & then
//        assertThrows(TaxiPartyNotFoundException.class, () -> taxiPartyService.findTaxiParty(request));
    }

    @Test
    public void findTaxiPartyList() {
    }

    @Test
    public void createTaxiParty() {
    }

    @Test
    public void addNewMember() {
    }

    @Test
    public void removeMember() {
    }

    @Test
    public void deleteParty() {
    }

    @Test
    public void hasActiveParty() {
    }

    @Test
    public void findTaxiPartyById() {
    }
}