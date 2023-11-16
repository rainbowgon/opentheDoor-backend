package com.rainbowgon.memberservice.domain.oauth.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class KakaoUserInfoDto {

    private String id;
    private String connectedAt;
    private Properties properties;
    private KakaoAccount kakaoAccount;

    @Getter
    public static class Properties {

        private String nickname;
        private String profileImage;
        private String thumbnailImage;
    }

    @Getter
    public static class KakaoAccount {

        private Boolean profileNicknameNeedsAgreement;
        private Boolean profileImageNeedsAgreement;
        private Profile profile;

        @Getter
        public static class Profile {

            private String nickname;
            private String thumbnailImageUrl;
            private String profileImageUrl;
            private Boolean isDefaultImage;
        }
    }

}
