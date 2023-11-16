package com.rainbowgon.reservationservice.global.client.dto.output;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class MemberIdDto {

    private String memberId;

    public static MemberIdDto from(String memberId) {
        return MemberIdDto.builder()
                .memberId(memberId)
                .build();
    }
}
