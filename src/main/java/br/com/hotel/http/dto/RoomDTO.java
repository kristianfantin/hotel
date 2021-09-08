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
import java.math.BigDecimal;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(NON_NULL)
public class RoomDTO implements Serializable {

    @ApiModelProperty(notes = "Room Id")
    private Long roomID;

    @ApiModelProperty(notes = "Name of Category")
    private String categoryName;

    @ApiModelProperty(notes = "Calculated Total Price")
    private BigDecimal totalPrice;

    @ApiModelProperty(notes = "Price")
    private PriceDTO price;

}
