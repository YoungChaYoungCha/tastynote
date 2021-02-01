package com.youngchayoungcha.tastynote.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
public class Address {

    // TODO 위도와 경도
    private String city;
    private String street;
    private String zipcode;
}
