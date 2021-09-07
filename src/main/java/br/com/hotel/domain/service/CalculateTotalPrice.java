package br.com.hotel.domain.service;

import br.com.hotel.http.dto.CityDTO;
import br.com.hotel.http.dto.RoomDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@RequiredArgsConstructor
@Component
public class CalculateTotalPrice {

    public void execute(CityDTO dto, LocalDate checkInDate, LocalDate checkOutDate, Integer numberOfAdults, Integer numberOfChildren) {
        final long dias = checkInDate.until(checkOutDate, ChronoUnit.DAYS) + 1;
        dto.getRooms().forEach(room -> room.setTotalPrice(getTotal(
                dias,
                getAdultValue(numberOfAdults, room),
                getChildrenValue(numberOfChildren, room)
        )));
    }

    private BigDecimal getTotal(long dias, BigDecimal adultTotalValue, BigDecimal childrenTotalValue) {
        return ((adultTotalValue.add(childrenTotalValue))
                .divide(BigDecimal.valueOf(0.7), 2, RoundingMode.HALF_UP))
                .multiply(BigDecimal.valueOf(dias))
                .setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal getChildrenValue(Integer numberOfChildren, RoomDTO room) {
        return BigDecimal.valueOf(room.getPrice().getChild()).multiply(BigDecimal.valueOf(numberOfChildren));
    }

    private BigDecimal getAdultValue(Integer numberOfAdults, RoomDTO room) {
        return BigDecimal.valueOf(room.getPrice().getAdult()).multiply(BigDecimal.valueOf(numberOfAdults));
    }

}
