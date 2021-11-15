package com.handsone.restAPI.service;

import com.handsone.restAPI.domain.Member;
import com.handsone.restAPI.dto.MemberDto;
import com.handsone.restAPI.error.ErrorCode;
import com.handsone.restAPI.exception.ClientException;
import com.handsone.restAPI.infra.address.Address;
import com.handsone.restAPI.repository.MemberRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.handsone.restAPI.error.ErrorCode.DUPLICATE_RESOURCE;
import static com.handsone.restAPI.error.ErrorCode.NOTFOUND_MEMBER;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("MemberServiceTest-MockRepository")
class MemberServiceMockTest {
    @Mock
    MemberRepository memberRepository;
    MemberService memberService;

    static List<Member> members = new ArrayList<>();
    static Long seq = 0L;

    @BeforeAll
    public static void before() {
        System.out.println("MemberServiceTest.before");
        members.add(new Member( "id1", "pass1", "nick1", new Address("Gu")));
        members.add(new Member( "id2", "pass2", "nick2", new Address("GU")));
        members.add(new Member( "id3", "pass3", "nick3", new Address("gU")));
    }

    @BeforeEach
    public void setUp() {
        memberService = new MemberService(memberRepository);
    }

    @Test
    @DisplayName("[가입] 정상&예외 테스트")
    public void signUp() {
        // given
        Member member0 = members.get(0);
        Member member1 = members.get(1);
        Member resultEntity1 = getResultEntity(member1);

        when(memberRepository.findByUserId(member0.getUserId()))
                .thenThrow(new ClientException(DUPLICATE_RESOURCE));
        when(memberRepository.findByUserId(member1.getUserId()))
                .thenReturn(Optional.empty());
        when(memberRepository.save(member1))
                .thenReturn(resultEntity1);

        // -SUCCESS
        Member success = memberService.signUp(member1);
        assertMember(success, resultEntity1);

        // -FAIL
        assertThatThrownBy(() -> memberService.signUp(member0))
                .isInstanceOf(ClientException.class);

        // then
        verify(memberRepository, times(1)).save(any());
        verify(memberRepository, times(1)).findByUserId(member0.getUserId());
        verify(memberRepository, times(1)).findByUserId(member1.getUserId());
    }

    @Test
    @DisplayName("PK로 Member 조회 테스트")
    public void findById() {
        Member member0 = members.get(0);
        Member member1 = members.get(1);
        Member resultEntity0 = getResultEntity(member0);
        Member resultEntity1 = getResultEntity(member1);

        when(memberRepository.findById(resultEntity0.getId()))
                .thenReturn(Optional.of(resultEntity0));
        when(memberRepository.findById(resultEntity1.getId()))
                .thenReturn(Optional.empty());

        // -SUCCESS
        Member success = memberService.findOne(resultEntity0.getId());
        assertMember(success, resultEntity0);

        // -FAIL
        assertThatThrownBy(() -> memberService.findOne(resultEntity1.getId()))
                .isInstanceOf(ClientException.class);

        // then
        verify(memberRepository, times(2)).findById(any());
    }

    @Test
    public void login() {
        Member member0 = members.get(0);
        Member member1 = members.get(1);
        Member resultEntity0 = getResultEntity(member0);

        when(memberRepository.findByUserIdAndPassword(member0.getUserId(),member0.getPassword()))
                .thenReturn(Optional.of(resultEntity0));
        when(memberRepository.findByUserIdAndPassword(member1.getUserId(), member1.getPassword()))
                .thenReturn(Optional.empty());

        // -SUCCESS
        Member success = memberService.logIn(member0.getUserId(), member0.getPassword());
        assertMember(success, resultEntity0);

        // -FAIL
        assertThatThrownBy(() -> memberService.logIn(member1.getUserId(), member1.getPassword()))
                .isInstanceOf(ClientException.class);

        verify(memberRepository, times(2)).findByUserIdAndPassword(anyString(), anyString());
    }

    private Member getResultEntity(Member member) {
        return new Member(++seq, member.getUserId(), member.getPassword(),
                member.getNickName(), member.getAddress());
    }

    private void assertMember(Member actual, Member expect) {
        assertThat(actual).isNotNull();
        assertThat(expect).isNotNull();
        assertThat(actual.getUserId()).isEqualTo(expect.getUserId());
        assertThat(actual.getAddress().getGu()).isEqualTo(expect.getAddress().getGu());
        assertThat(actual.getPassword()).isEqualTo(expect.getPassword());
        assertThat(actual.getNickName()).isEqualTo(expect.getNickName());
    }
}