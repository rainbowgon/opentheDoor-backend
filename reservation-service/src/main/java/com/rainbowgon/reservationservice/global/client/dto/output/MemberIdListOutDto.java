package com.rainbowgon.reservationservice.global.client.dto.output;

import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class MemberIdListOutDto {

    private List<String> memberIdList;

    public static MemberIdListOutDto from(List<String> memberIdList) {
        return MemberIdListOutDto.builder()
                .memberIdList(memberIdList)
                .build();
    }

    public void add(String memberId) {
        this.memberIdList.add(memberId);
    }
}
