package br.com.hotel.http.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
@JsonInclude(NON_NULL)
public class RoomDTO {

    @ApiModelProperty(notes = "Room Id")
    private Long roomID;

    @ApiModelProperty(notes = "Name of Category")
    private String categoryName;

    @ApiModelProperty(notes = "Price")
    private PriceDTO price;

}
