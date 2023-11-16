package com.rainbowgon.reservationservice.global.client.dto.output;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class MemberIdOutDto {

    private String memberId;

    public static MemberIdOutDto from(String memberId) {
        return MemberIdOutDto.builder()
                .memberId(memberId)
                .build();
    }
}
