package com.rainbowgon.reservationservice.global.utils;

import com.rainbowgon.reservationservice.global.error.exception.DatetimeInvalidException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DateTimeValidatorTest {


    @Test
    @DisplayName("날짜 : 날짜 범위에 벗어나는 경우")
    void testDate1() throws Exception {

        String date = "2023-12-50";

//        assertThatCode(() -> DateTimeValidator.validateDate(date))
//                .doesNotThrowAnyException();

        assertThatThrownBy(() -> DateTimeValidator.validateDate(date))
                .isInstanceOf(DatetimeInvalidException.class);
    }

    @Test
    @DisplayName("날짜 : 형식에 맞지 않은 경우")
    void testDate2() throws Exception {

        String date = "2023/12/29";

        assertThatThrownBy(() -> DateTimeValidator.validateDate(date))
                .isInstanceOf(DatetimeInvalidException.class);
    }

    @Test
    @DisplayName("시간 : 형식에 맞지 않은 경우")
    void testTime1() throws Exception {

        String time = "11-12";

        assertThatThrownBy(() -> DateTimeValidator.validateTime(time))
                .isInstanceOf(DatetimeInvalidException.class);
    }

    @Test
    @DisplayName("시간 : 시간 범위를 벗어나는 경우")
    void testTime2() throws Exception {

        String time = "11:70";

        assertThatThrownBy(() -> DateTimeValidator.validateTime(time))
                .isInstanceOf(DatetimeInvalidException.class);
    }

    @Test
    @DisplayName("시간 : 숫자가 아닌 경우")
    void testTime3() throws Exception {

        String time = "11:aa";

        assertThatThrownBy(() -> DateTimeValidator.validateTime(time))
                .isInstanceOf(DatetimeInvalidException.class);
    }

    @Test
    @DisplayName("시간 : 범위와 형식에 맞는 경우")
    void testTime4() throws Exception {

        String time = "11:20";

        assertThatCode(() -> DateTimeValidator.validateTime(time))
                .doesNotThrowAnyException();

    }

    @Test
    @DisplayName("시간 : 범위와 형식에 맞는 경우")
    void testTime5() throws Exception {

        String time = "24:20";

        assertThatCode(() -> DateTimeValidator.validateTime(time))
                .doesNotThrowAnyException();

    }
}