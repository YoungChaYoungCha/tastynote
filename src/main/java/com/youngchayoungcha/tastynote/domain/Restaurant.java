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

    private float averageScore;

    @OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY)
    private List<Post> posts = new ArrayList<>();

    public static Restaurant createRestaurant(String placeId, String name, String formattedAddress, Double latitude, Double longitude, float score){
        Restaurant restaurant = new Restaurant();
        restaurant.id = placeId;
        restaurant.address = new Address(formattedAddress, latitude, longitude);
        restaurant.name = name;
        restaurant.averageScore = score;
        return restaurant;
    }

    public void addPost(Post post){
        this.posts.add(post);
    }

    public void setAverageScore(float scoreSum){
        if (posts.size() == 0){
            this.averageScore = 0;
        }else{
            this.averageScore = scoreSum / posts.size();
        }
    }

    public void removePost(Post post) {
        this.posts.remove(post);
    }
}
