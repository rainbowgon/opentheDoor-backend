package com.rainbowgon.searchservice.domain.theme.model.entry;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PriceEntry {
    private Integer headcount;
    private Integer price;
}
