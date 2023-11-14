package com.rainbowgon.memberservice.global.client.dto.output;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ReservationOutDto {

    private String bookerName;
    private String bookerPhoneNumber;
}
