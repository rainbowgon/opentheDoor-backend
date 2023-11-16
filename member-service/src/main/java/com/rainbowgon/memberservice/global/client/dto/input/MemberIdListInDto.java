package com.rainbowgon.memberservice.global.client.dto.input;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberIdListInDto {

    private List<String> memberIdList;
}
