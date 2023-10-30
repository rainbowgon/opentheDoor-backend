package com.rainbowgon.member.domain.member.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.Getter;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class KakaoProfileResDto {

    private String id;
    private String connectedAt;
    private Properties properties;
    private KakaoAccount kakaoAccount;

    @Data
    public static class Properties {

        private String nickname;
        private String profileImage;
        private String thumbnailImage;
    }

    @Data
    public static class KakaoAccount {

        private Boolean profileNicknameNeedsAgreement;
        private Boolean profileImageNeedsAgreement;
        private Profile profile;

        @Data
        public static class Profile {

            private String nickname;
            private String thumbnailImageUrl;
            private String profileImageUrl;
            private Boolean isDefaultImage;
        }
    }

}
