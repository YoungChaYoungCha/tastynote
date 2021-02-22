package com.youngchayoungcha.tastynote.web.dto;

import com.youngchayoungcha.tastynote.domain.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantDTO {

    private String placeId;
    private String name;
    private String formattedAddress;
    private Double latitude;
    private Double longitude;

    public static RestaurantDTO fromEntity(Restaurant restaurant){
        RestaurantDTO dto = new RestaurantDTO();
        dto.placeId = restaurant.getId();
        dto.name = restaurant.getName();
        dto.formattedAddress = restaurant.getAddress().getFormattedAddress();
        dto.latitude = restaurant.getAddress().getLatitude();
        dto.longitude = restaurant.getAddress().getLongitude();
        return dto;
    }
}
