package com.youngchayoungcha.tastynote.web.dto;

import com.youngchayoungcha.tastynote.domain.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantDTO {

    @NotEmpty(message = "장소 Id는 반드시 명시되어야 합니다.")
    private String placeId;

    @NotEmpty(message = "장소 이름은 반드시 필요합니다.")
    private String name;

    @NotEmpty(message = "주소는 반드시 명시되어야 합니다.")
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
