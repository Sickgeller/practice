package com.toy.practice.member.mapper;

import com.toy.practice.member.dto.MemberLoginRequest;
import com.toy.practice.member.dto.MemberResponse;
import com.toy.practice.member.dto.MemberSignUpRequest;
import com.toy.practice.member.dto.MemberUpdateRequest;
import com.toy.practice.member.model.Member;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-09T15:15:15+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.13 (Oracle Corporation)"
)
@Component
public class MemberMapperImpl implements MemberMapper {

    @Override
    public MemberResponse toDto(Member member) {
        if ( member == null ) {
            return null;
        }

        MemberResponse.MemberResponseBuilder memberResponse = MemberResponse.builder();

        memberResponse.memberId( member.getMemberId() );
        memberResponse.id( member.getId() );
        memberResponse.email( member.getEmail() );
        memberResponse.name( member.getName() );
        memberResponse.active( member.isActive() );
        memberResponse.emailVerified( member.isEmailVerified() );
        memberResponse.createdAt( member.getCreatedAt() );
        memberResponse.updateAt( member.getUpdateAt() );

        return memberResponse.build();
    }

    @Override
    public Member toEntityFromSignUp(MemberSignUpRequest request) {
        if ( request == null ) {
            return null;
        }

        Member.MemberBuilder member = Member.builder();

        member.id( request.getId() );
        member.name( request.getName() );
        member.password( request.getPassword() );
        member.email( request.getEmail() );

        return member.build();
    }

    @Override
    public Member toEntityFromLogin(MemberLoginRequest request) {
        if ( request == null ) {
            return null;
        }

        Member.MemberBuilder member = Member.builder();

        member.id( request.getId() );
        member.password( request.getPassword() );

        return member.build();
    }

    @Override
    public Member toEntityFromUpdate(MemberUpdateRequest request) {
        if ( request == null ) {
            return null;
        }

        Member.MemberBuilder member = Member.builder();

        member.name( request.getName() );
        member.email( request.getEmail() );

        return member.build();
    }
}
