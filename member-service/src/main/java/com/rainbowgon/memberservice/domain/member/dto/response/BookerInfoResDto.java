package com.rainbowgon.memberservice.domain.member.dto.response;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class BookerInfoResDto {

    private String bookerName;
    private String bookerPhoneNumber;

    public static BookerInfoResDto of(String name, String phoneNumber) {
        return BookerInfoResDto.builder()
                .bookerName(name)
                .bookerPhoneNumber(phoneNumber)
                .build();
    }

}
