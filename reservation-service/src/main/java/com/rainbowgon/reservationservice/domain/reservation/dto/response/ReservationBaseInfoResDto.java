package com.rainbowgon.reservationservice.domain.reservation.dto.response;

import com.rainbowgon.reservationservice.domain.reservation.vo.PriceVO;
import com.rainbowgon.reservationservice.domain.reservation.vo.TimeSlotVO;
import com.rainbowgon.reservationservice.global.client.dto.input.MemberBriefInfoInDto;
import com.rainbowgon.reservationservice.global.client.dto.input.ThemeBriefInfoInDto;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ReservationBaseInfoResDto {

    private String bookerName;
    private String bookerPhoneNumber;
    private List<TimeSlotVO> timeSlotList;
    private String themeId;
    private String poster;
    private String title;
    private String venue;
    private String location;
    private List<String> genre;
    private List<PriceVO> priceList;
    private String siteToS;
    private String venueToS;

    // TODO timeSlotList 넣기
    public static ReservationBaseInfoResDto from(String themeId, MemberBriefInfoInDto memberDto,
                                                 ThemeBriefInfoInDto themeDto) {
        return ReservationBaseInfoResDto.builder()
                .bookerName(memberDto.getBookerName())
                .bookerPhoneNumber(memberDto.getBookerPhoneNumber())
                .themeId(themeId)
                .poster(themeDto.getPoster())
                .title(themeDto.getTitle())
                .venue(themeDto.getVenue())
                .location(themeDto.getLocation())
                .genre(themeDto.getGenre())
                .priceList(themeDto.getPriceList())
                .siteToS(themeDto.getSiteToS())
                .venueToS(themeDto.getVenueToS())
                .build();

    }

}
