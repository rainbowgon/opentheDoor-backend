package com.rainbowgon.reservationservice.domain.reservation.dto.response;

import com.rainbowgon.reservationservice.global.client.dto.input.MemberBriefInfoInDto;
import com.rainbowgon.reservationservice.global.client.dto.input.ThemeBriefInfoInDto;
import com.rainbowgon.reservationservice.global.vo.PriceVO;
import com.rainbowgon.reservationservice.global.vo.TimeSlotVO;
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
    private String siteTos;
    private String venueTos;

    // TODO timeSlotList 넣기
    public static ReservationBaseInfoResDto from(String themeId, MemberBriefInfoInDto memberDto,
                                                 ThemeBriefInfoInDto themeDto) {
        return ReservationBaseInfoResDto.builder()
                .bookerName(memberDto.getBookerName())
                .bookerPhoneNumber(memberDto.getBookerPhoneNumber())
                .timeSlotList(null)
                .themeId(themeId)
                .poster(themeDto.getPoster())
                .title(themeDto.getTitle())
                .venue(themeDto.getVenue())
                .location(themeDto.getLocation())
                .genre(themeDto.getGenre())
                .priceList(themeDto.getPriceList())
                .siteTos(themeDto.getSiteTos())
                .venueTos(themeDto.getVenueTos())
                .build();

    }

    // TODO timeSlotList 넣기
    public static ReservationBaseInfoResDto from(String themeId,
                                                 ThemeBriefInfoInDto themeDto) {
        return ReservationBaseInfoResDto.builder()
                .bookerName(null)
                .bookerPhoneNumber(null)
                .timeSlotList(null)
                .themeId(themeId)
                .poster(themeDto.getPoster())
                .title(themeDto.getTitle())
                .venue(themeDto.getVenue())
                .location(themeDto.getLocation())
                .genre(themeDto.getGenre())
                .priceList(themeDto.getPriceList())
                .siteTos(themeDto.getSiteTos())
                .venueTos(themeDto.getVenueTos())
                .build();

    }

}
