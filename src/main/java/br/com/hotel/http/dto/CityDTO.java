package br.com.hotel.http.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
@JsonInclude(NON_NULL)
public class CityDTO implements Serializable {

    @ApiModelProperty(notes = "Hotel ID")
    private Long id;

    @ApiModelProperty(notes = "Name of Hotel")
    private String name;

    @ApiModelProperty(notes = "Code of City")
    private Long cityCode;

    @ApiModelProperty(notes = "Name of City")
    private String cityName;

    @ApiModelProperty(notes = "Rooms")
    private List<RoomDTO> rooms;
    
}
