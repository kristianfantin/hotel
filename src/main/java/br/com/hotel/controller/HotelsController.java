package br.com.hotel.controller;

import br.com.hotel.http.dto.CityDTO;
import br.com.hotel.http.gateway.HotelGateway;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/hotels")
public class HotelsController {

    private final HotelGateway gateway;

    @ApiOperation(value = "Hotels search by ID Hotel")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CityDTO getHotel(HttpServletRequest request, @RequestParam Long hotelId) {
        return gateway.findByHotel(hotelId);
    }

    @ApiOperation(value = "Hotels search by ID City")
    @GetMapping(value = "/avail")
    @ResponseStatus(HttpStatus.OK)
    public List<CityDTO> getAvailCity(HttpServletRequest request, @RequestParam Long cityId) {
        return gateway.findByCity(cityId);
    }

}
