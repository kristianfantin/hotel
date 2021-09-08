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

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(NON_NULL)
public class PriceDTO implements Serializable {

    @ApiModelProperty(notes = "Adult Price")
    private Double adult;

    @ApiModelProperty(notes = "Child Price")
    private Double child;
}
