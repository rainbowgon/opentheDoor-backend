package com.rainbowgon.searchservice.domain.theme.service;

import com.rainbowgon.searchservice.domain.theme.dto.response.ThemeDetailResDto;
import com.rainbowgon.searchservice.domain.theme.dto.response.ThemeSimpleResDto;
import com.rainbowgon.searchservice.domain.theme.model.Theme;
import com.rainbowgon.searchservice.domain.theme.model.entry.PriceEntry;
import com.rainbowgon.searchservice.domain.theme.repository.ThemeRepository;
import com.rainbowgon.searchservice.global.client.ReservationServiceClient;
import com.rainbowgon.searchservice.global.client.dto.input.BookmarkInDtoList;
import com.rainbowgon.searchservice.global.client.dto.input.ReservationInDto;
import com.rainbowgon.searchservice.global.client.dto.input.ReservationInDtoList;
import com.rainbowgon.searchservice.global.client.dto.input.entry.TimeEntry;
import com.rainbowgon.searchservice.global.client.dto.output.BookmarkDetailOutDto;
import com.rainbowgon.searchservice.global.client.dto.output.BookmarkSimpleOutDto;
import com.rainbowgon.searchservice.global.client.dto.output.ReservationDetailOutDto;
import com.rainbowgon.searchservice.global.client.dto.output.ReservationOriginalOutDto;
import com.rainbowgon.searchservice.global.error.exception.PriceNotFoundException;
import com.rainbowgon.searchservice.global.error.exception.ThemeNotFoundException;
import com.rainbowgon.searchservice.global.utils.RedisKeyBuilder;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class ThemeServiceImpl implements ThemeService {

    private final ThemeRepository themeRepository;
    private final ReservationServiceClient reservationServiceClient;

    @Qualifier("sortingRedisStringTemplate")
    private final RedisTemplate<String, String> sortingRedisStringTemplate;

    @Qualifier("cacheRedisThemeTemplate")
    private final RedisTemplate<String, Theme> cacheRedisThemeTemplate;

    // Haversine 공식을 사용하여 거리를 계산하는 메소드
    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // 지구의 반경(km)

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c; // 단위: km
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ThemeSimpleResDto> searchThemes(String keyword, Double latitude, Double longitude,
                                                Integer headcount, List<String> region,
                                                Integer page, Integer size) {

        List<Theme> themeList = search(keyword);

        // 레디스에 저장할 키를 생성
        String sortingKey = RedisKeyBuilder.buildKey("BOOKMARK", keyword);
        String reviewKey = RedisKeyBuilder.buildKey("REVIEW", keyword);
        String recommendKey = RedisKeyBuilder.buildKey("RECOMMEND", keyword);

        boolean keyExists = cacheRedisThemeTemplate.hasKey(sortingKey);
        if (!keyExists) {
            // 여기서 기존의 점수가 있는지 체크하고, 없으면 0으로 초기화(기본 북마크)
            for (Theme theme : themeList) {
                createZSET(sortingKey, reviewKey, recommendKey, theme);
            }
        }

        // 레디스에서 전체 정렬된 결과를 가져옵니다.
        List<Theme> allSortedThemes =
                new ArrayList<>(cacheRedisThemeTemplate.opsForZSet().reverseRange(recommendKey, 0, -1));

        // 필터링 로직 적용
        List<Theme> filteredThemes;
        if ((region != null && !region.isEmpty()) || (headcount != null && headcount > 0)) {
            filteredThemes = allSortedThemes.stream()
                    .filter(theme -> {
                        boolean regionMatch = true;
                        boolean headcountMatch = true;

                        // 지역 필터링
                        if (region != null && !region.isEmpty()) {
                            String locationFirstPart = theme.getLocation().split("\\s+")[0];
                            regionMatch = region.contains(locationFirstPart);
                        }

                        // 인원수 필터링
                        if (headcount != null && headcount > 0) {
                            headcountMatch =
                                    theme.getMinHeadcount() <= headcount && headcount <= theme.getMaxHeadcount();
                        }
                        return regionMatch && headcountMatch;
                    })
                    .collect(Collectors.toList());
        } else {
            filteredThemes = new ArrayList<>(allSortedThemes);
        }

        // 수동 페이지네이션 적용
        int start = page * size;
        int end = Math.min((page + 1) * size, filteredThemes.size());


        List<ThemeSimpleResDto> content = filteredThemes.subList(start, end)
                .stream()
                .map(theme -> ThemeSimpleResDto.from(theme, getScore(theme, "BOOKMARK").intValue(),
                                                     getScore(theme, "REVIEW").intValue(), getScore(theme,
                                                                                                    "RATING"
                        )))
                .collect(Collectors.toList());


        // Page 객체를 생성하고 반환합니다.
        return new PageImpl<>(content, PageRequest.of(page, size), filteredThemes.size());
    }

    //Zset에 거리 정렬 기준으로 넣는 함수
    private void createZSETforDistance(String keyword, Theme theme, Double latitude, Double longitude) {
        String distanceKey = RedisKeyBuilder.buildKey("DISTANCE", keyword);

        Double distanceScore = calculateDistance(theme.getLatitude(), theme.getLongitude(), latitude,
                                                 longitude);
        cacheRedisThemeTemplate.opsForZSet().add(distanceKey, theme, distanceScore);
        cacheRedisThemeTemplate.expire(distanceKey, Duration.ofMinutes(3));
    }

    //Zset에 예약 시간 정렬 기준으로 넣는 함수
    private void createZSETforTime(String keyword, Theme theme, List<ReservationInDto> timeSlots) {
        String timeKey = RedisKeyBuilder.buildKey("TIME", keyword);

        // 현재 시간
        LocalDateTime now = LocalDateTime.now();

        // JSON 구조 파싱 및 처리
        for (ReservationInDto reservationInDto : timeSlots) {
            for (TimeEntry timeEntry : reservationInDto.getTimeList()) {
                if ("AVAILABLE".equals(timeEntry.getIsAvailable())) {
                    LocalTime slotTime = timeEntry.getTime();

                    // 현재 시간과의 차이 계산
                    Duration duration = Duration.between(now.toLocalTime(), slotTime);
                    long minutes = duration.toMinutes();

                    // Redis ZSET에 추가
                    cacheRedisThemeTemplate.opsForZSet().add(timeKey, theme, minutes);
                    return;
                }
            }
        }

        cacheRedisThemeTemplate.expire(timeKey, Duration.ofMinutes(3));
    }

    //Zset에 각 정렬 기준별로 넣는 함수
    private void createZSET(String sortingKey, String reviewKey, String recommendKey, Theme theme) {
        //검색 결과로 나온 테마의 북마크 수
        Double bookmarkScore = getScore(theme, "BOOKMARK");
        Double reviewScore = getScore(theme, "REVIEW");
        Double ratingScore = getScore(theme, "RATING");

        //검색 결과로 나온 테마의 조회 수
        Double viewScore = getScore(theme, "VIEW");

        Double interest = 0.4 * reviewScore + 0.3 * viewScore + 0.3 * bookmarkScore;

        Double finalRatingScore = ratingScore - (ratingScore - 0.5) * Math.pow(2, -Math.log(interest + 1));
        cacheRedisThemeTemplate.opsForZSet().add(sortingKey, theme, bookmarkScore);
        cacheRedisThemeTemplate.expire(sortingKey, Duration.ofMinutes(20));
        cacheRedisThemeTemplate.opsForZSet().add(reviewKey, theme, reviewScore);
        cacheRedisThemeTemplate.expire(reviewKey, Duration.ofMinutes(20));
        cacheRedisThemeTemplate.opsForZSet().add(recommendKey, theme, finalRatingScore);
        cacheRedisThemeTemplate.expire(recommendKey, Duration.ofMinutes(20));
    }


    //ZSet Score 값을 get하는 함수
    @NotNull
    private Double getScore(Theme theme, String key) {

        return Optional.ofNullable(sortingRedisStringTemplate.opsForZSet().score(key, theme.getThemeId()))
                .orElse(0.0);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Theme> search(String keyword) {
        List<Theme> themeList = null;
        keyword = (keyword.equals("")) ? null : keyword;

        if (keyword != null) {
            themeList = themeRepository.searchByKeyword(keyword);

        } else if (keyword == null) {
            Iterable<Theme> themes = themeRepository.findAll();
            themeList = StreamSupport.stream(themes.spliterator(), false) // ElasticsearchRepository는
                    // findAll이 List가 아닌 Iterable가 나와서 변환 필요
                    .collect(Collectors.toList());
        }

        return themeList;
    }

    @Override
    @Transactional(readOnly = true)
    public ThemeDetailResDto selectOneThemeById(String themeId) {
        Theme theme = themeRepository.findById(themeId).orElseThrow(ThemeNotFoundException::new);
        sortingRedisStringTemplate.opsForZSet().incrementScore("VIEW", themeId, 1);
        Double bookmarkCount = getScore(theme, "BOOKMARK");
        Double reviewCount = getScore(theme, "REVIEW");
        Double ratingScore = getScore(theme, "RATING");

        ReservationInDtoList timeslot = reservationServiceClient.getTimeslot(themeId);

        return ThemeDetailResDto.from(theme, bookmarkCount.intValue(), reviewCount.intValue(), ratingScore,
                                      timeslot.getTimeSlotList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookmarkDetailOutDto> selectDetailThemesById(BookmarkInDtoList themeIdList) {
        List<BookmarkDetailOutDto> themeDetailResDtoList = new ArrayList<>();
        for (String themeId : themeIdList.getThemeIdList()) {
            Theme theme = themeRepository.findById(themeId).orElseThrow(ThemeNotFoundException::new);
            themeDetailResDtoList.add(BookmarkDetailOutDto.from(theme, getScore(theme, "BOOKMARK").intValue(),
                                                                getScore(theme, "REVIEW").intValue(),
                                                                getScore(theme, "RATING")));

        }
        return themeDetailResDtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookmarkSimpleOutDto> selectSimpleThemesById(BookmarkInDtoList bookmarkInDtoList) {
        List<BookmarkSimpleOutDto> themeSimpleResDtoList = new ArrayList<>();
        for (String themeId : bookmarkInDtoList.getThemeIdList()) {
            Theme theme = themeRepository.findById(themeId).orElseThrow(ThemeNotFoundException::new);
            themeSimpleResDtoList.add(BookmarkSimpleOutDto.from(theme, getScore(theme, "REVIEW").intValue(),
                                                                getScore(theme, "RATING")));
        }

        return themeSimpleResDtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ThemeSimpleResDto> sort(String keyword, String sortBy, Double latitude, Double longitude,
                                        Integer headcount, List<String> region,
                                        Integer page, Integer size) {

        String redisKey = RedisKeyBuilder.buildKey(sortBy, keyword);

        int start = page * size; // 페이지 계산에 따른 시작 인덱스
        int end = (page + 1) * size - 1; // 페이지 계산에 따른 끝 인덱스

        Set<Theme> sortedThemeIds;

        // 레디스에서 정렬된 결과를 가져와서 DTO로 변환
        if (sortBy.equals("TIME")) {
            List<Theme> themeList = search(keyword);
            for (Theme theme : themeList) {
                ReservationInDtoList timeSlots = reservationServiceClient.getTimeslot(theme.getThemeId());
                createZSETforTime(keyword, theme, timeSlots.getTimeSlotList());
            }
            sortedThemeIds = cacheRedisThemeTemplate.opsForZSet().reverseRange(redisKey, 0,
                                                                               -1);
        } else if (sortBy.equals("DISTANCE")) {
            List<Theme> themeList = search(keyword);
            for (Theme theme : themeList) {
                createZSETforDistance(keyword, theme, latitude, longitude);
            }
            sortedThemeIds = cacheRedisThemeTemplate.opsForZSet().reverseRange(redisKey, 0,
                                                                               -1);
        } else {

            boolean keyExists = cacheRedisThemeTemplate.hasKey(redisKey);
            if (!keyExists) {
                // 키가 존재하지 않으면, searchThemes 메서드를 실행
                Page<ThemeSimpleResDto> searchResult = searchThemes(keyword, latitude, longitude, headcount,
                                                                    region, page, size);
            }
            sortedThemeIds = cacheRedisThemeTemplate.opsForZSet().range(redisKey, 0, -1);
        }

        // 먼저 Set을 List로 변환합니다. 이때, 원래의 순서가 유지됩니다.
        List<Theme> sortedThemeList = new ArrayList<>(sortedThemeIds);

        // 조건에 따라 필터링을 수행하고 결과를 List로 변환합니다.
        List<Theme> filteredSortedThemes;
        if ((region != null && !region.isEmpty()) || (headcount != null && headcount > 0)) {
            filteredSortedThemes = sortedThemeList.stream()
                    .filter(theme -> {
                        boolean regionMatch = true;
                        boolean headcountMatch = true;

                        // 지역 필터링
                        if (region != null && !region.isEmpty()) {
                            String locationFirstPart = theme.getLocation().split("\\s+")[0];
                            regionMatch = region.contains(locationFirstPart);
                        }

                        // 인원수 필터링
                        if (headcount != null && headcount > 0) {
                            headcountMatch =
                                    theme.getMinHeadcount() <= headcount && headcount <= theme.getMaxHeadcount();
                        }
                        return regionMatch && headcountMatch;
                    })
                    .collect(Collectors.toList());
        } else {
            // 필터링 조건이 없는 경우, 전체 리스트를 사용합니다.
            filteredSortedThemes = sortedThemeList;
        }

        // 필터링된 결과를 이용하여 DTO 리스트를 생성합니다.
        List<ThemeSimpleResDto> content = filteredSortedThemes.stream()
                .map(theme -> ThemeSimpleResDto.from(theme, getScore(theme, "BOOKMARK").intValue(),
                                                     getScore(theme, "REVIEW").intValue(), getScore(theme,
                                                                                                    "RATING"
                        )))
                .collect(Collectors.toList());

        // 페이징 처리를 위해 sublist를 사용합니다. 이는 인덱스 범위에 유의해야 합니다.
        int startIndex = Math.min(start, content.size());
        int endIndex = Math.min(content.size(), end + 1);
        List<ThemeSimpleResDto> pagedContent = content.subList(startIndex, endIndex);

        // 최종적으로 페이징된 결과를 반환합니다.
        return new PageImpl<>(pagedContent, PageRequest.of(page, size), filteredSortedThemes.size());
    }


    @Scheduled(cron = "0 0 0 * * SUN") // 매주 일요일 자정에 실행
    @Override
    public void setRanks() {
        List<Theme> themeList = search("");
        String rankingKey = "RANKING";

        boolean keyExists = cacheRedisThemeTemplate.hasKey(rankingKey);
        if (keyExists) {
            // 키가 존재하면 삭제합니다.
            cacheRedisThemeTemplate.delete(rankingKey);
        }
// 이제 존재하지 않으므로, 새로운 데이터를 추가합니다.
        for (Theme theme : themeList) {
            Double bookmarkScore = getScore(theme, "BOOKMARK");
            Double reviewScore = getScore(theme, "REVIEW");
            Double ratingScore = getScore(theme, "RATING");

            String lastViewKey = "LASTVIEW:" + theme.getThemeId();
            // Redis에서 지난 조회 수를 가져옵니다. 값이 없다면 0으로 초기화합니다.
            Double lastViewScore =
                    Optional.ofNullable(getScore(theme, "LASTVIEW")).orElse(0.0);

            // 검색 결과로 나온 테마의 조회 수
            Double currentViewScore =
                    Optional.ofNullable(getScore(theme, "VIEW")).orElse(0.0);

            Double viewScore = lastViewScore + currentViewScore;

            sortingRedisStringTemplate.opsForZSet().add("LASTVIEW", theme.getThemeId(), viewScore);

            sortingRedisStringTemplate.opsForZSet().add("VIEW", theme.getThemeId(), 0.0);

            Double interest = 0.4 * reviewScore + 0.3 * currentViewScore + 0.3 * bookmarkScore;

            Double finalRatingScore = ratingScore - (ratingScore - 0.5) * Math.pow(2,
                                                                                   -Math.log(interest + 1));

            cacheRedisThemeTemplate.opsForZSet().add(rankingKey, theme, finalRatingScore);
        }
        cacheRedisThemeTemplate.expire(rankingKey, Duration.ofDays(7).plusHours(1));

    }

    @Override
    @Transactional(readOnly = true)
    public List<ThemeSimpleResDto> getRanks() {
        String rankingKey = "RANKING";
        // RANKING 키로 정렬된 데이터에서 상위 10개를 불러오는 로직
        Set<Theme> rankedThemes = cacheRedisThemeTemplate.opsForZSet().reverseRange(rankingKey, 0, 9);

        if (rankedThemes == null || rankedThemes.isEmpty()) {
            setRanks();  // setRanks 함수 호출
            rankedThemes = cacheRedisThemeTemplate.opsForZSet().reverseRange(rankingKey, 0, 9); // 데이터를 다시 로드
        }

        // Theme 객체를 ThemeSimpleResDto로 변환
        List<ThemeSimpleResDto> ranks = rankedThemes.stream()
                .map(theme -> ThemeSimpleResDto.from(theme, getScore(theme, "BOOKMARK").intValue(),
                                                     getScore(theme, "REVIEW").intValue(), getScore(theme,
                                                                                                    "RATING"
                        ))).collect(Collectors.toList());

        return ranks;
    }

    @Override
    @Transactional(readOnly = true)
    public Integer getPrice(String themeId, Integer headcount) {
        Theme theme = themeRepository.findById(themeId).orElseThrow(ThemeNotFoundException::new);

        for (PriceEntry priceEntry : theme.getPriceList()) {
            if (priceEntry.getHeadcount().equals(headcount)) {
                return priceEntry.getPrice() * headcount;
            }
        }
        throw new PriceNotFoundException();
    }

    @Override
    @Transactional(readOnly = true)
    public ReservationDetailOutDto getThemeForReservation(String themeId) {
        Theme theme = themeRepository.findById(themeId).orElseThrow(ThemeNotFoundException::new);

        ReservationDetailOutDto reservationDetailOutDto = ReservationDetailOutDto.from(theme);

        return reservationDetailOutDto;
    }

    @Override
    @Transactional(readOnly = true)
    public ReservationOriginalOutDto getOriginalForReservation(String themeId) {

        Theme theme = themeRepository.findById(themeId).orElseThrow(ThemeNotFoundException::new);

        ReservationOriginalOutDto reservationOriginalOutDto = ReservationOriginalOutDto.from(theme);

        return reservationOriginalOutDto;

    }

    @Override
    @Transactional(readOnly = true)
    public Boolean checkTheme(String themeId) {

        return themeRepository.existsById(themeId);

    }

    @Scheduled(fixedDelay = 45000, initialDelay = 10000)
    public void keepConnectionAlive() {
        try {
            search("");
        } catch (Exception e) {

        }
    }
}
