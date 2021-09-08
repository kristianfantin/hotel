package br.com.hotel.controller;

import br.com.hotel.aspect.ValidateCheckIn;
import br.com.hotel.http.dto.CityDTO;
import br.com.hotel.http.gateway.HotelGateway;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/hotels/check-in")
public class CheckInController {

    private final HotelGateway gateway;

    @ValidateCheckIn
    @Cacheable("check-in")
    @ApiOperation(value = "Find Hotels By City ID and Calculated the Total Price by Hotel agree with Parameters")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CityDTO> findByCity(HttpServletRequest request,
                              @RequestParam
                                      @Parameter(description = "Id of City", example = "0")
                                      Long cityId,
                              @RequestParam
                                      @Parameter(description = "Date of check-in", example = "yyyy-MM-dd")
                                      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                      LocalDate checkInDate,
                              @RequestParam
                                      @Parameter(description = "Date of check-out", example = "yyyy-MM-dd")
                                      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                      LocalDate checkOutDate,
                              @RequestParam
                                      @Parameter(description = "Number of Adults", example = "1")
                                      Integer numberOfAdults,
                              @RequestParam
                                      @Parameter(description = "Number of Children", example = "0")
                                      Integer numberOfChildren) {

        return gateway.find(cityId, checkInDate, checkOutDate, numberOfAdults, numberOfChildren);
    }

}
