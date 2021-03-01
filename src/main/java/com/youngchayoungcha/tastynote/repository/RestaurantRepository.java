package com.youngchayoungcha.tastynote.repository;

import com.youngchayoungcha.tastynote.domain.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, String> {

}
