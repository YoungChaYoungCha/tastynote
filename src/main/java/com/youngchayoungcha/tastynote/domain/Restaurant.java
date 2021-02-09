package com.youngchayoungcha.tastynote.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Restaurant {

    @Id @GeneratedValue
    @Column(name = "restaurant_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    private float averageScore;


}
