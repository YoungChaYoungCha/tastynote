package com.youngchayoungcha.tastynote.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Restaurant {

    @Id
    @Column(name = "restaurant_id")
    private String id;

    private String name;

    @Embedded
    private Address address;

    private Double averageScore;

    @OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY)
    private List<Post> posts = new ArrayList<>();

    public static Restaurant createRestaurant(String placeId, String name, String formattedAddress, Double latitude, Double longitude){
        Restaurant restaurant = new Restaurant();
        restaurant.id = placeId;
        restaurant.address = new Address(formattedAddress, latitude, longitude);
        restaurant.name = name;
        return restaurant;
    }

    public void addPost(Post post){
        this.posts.add(post);
    }

}
