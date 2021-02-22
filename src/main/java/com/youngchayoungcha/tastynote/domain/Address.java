package com.youngchayoungcha.tastynote.domain;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {

    // TODO 위도와 경도
    private String formattedAddress;
    private Double latitude;
    private Double longitude;
}
