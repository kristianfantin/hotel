package br.com.hotel.aspect;

import br.com.hotel.utils.ServiceExceptionBuilder;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

@RequiredArgsConstructor
@Aspect
@Component
public class AspectValidateCheckIn {

    @Before("@annotation(br.com.hotel.aspect.ValidateCheckIn) " +
            "&& args(request, cityId, checkInDate, checkOutDate, numberOfAdults, numberOfChildren)")
    public void validateCheckIn(HttpServletRequest request,
                                Long cityId,
                                LocalDate checkInDate,
                                LocalDate checkOutDate,
                                Integer numberOfAdults,
                                Integer numberOfChildren) {

        validateCity(cityId);
        validateCheckInOut(checkInDate, checkOutDate);
        validateAdults(numberOfAdults);
        validateChildren(numberOfChildren);

    }

    private void validateChildren(Integer numberOfChildren) {
        if (numberOfChildren < 0) {
            throw ServiceExceptionBuilder
                    .throwInvalidParameterException("Number of children must be positive or equal to 0: " + numberOfChildren);
        }
    }

    private void validateAdults(Integer numberOfAdults) {
        if (numberOfAdults < 1) {
            throw ServiceExceptionBuilder
                    .throwInvalidParameterException("Number of adults must be greater then 0: " + numberOfAdults);
        }
    }

    private void validateCheckInOut(LocalDate checkInDate, LocalDate checkOutDate) {
        if (checkOutDate.isBefore(checkInDate)) {
            throw ServiceExceptionBuilder
                    .throwInvalidParameterException("CheckOut Date must be greater or equal than CheckIn Date");
        }
    }

    private void validateCity(Long cityId) {
        if (cityId < 0) {
            throw ServiceExceptionBuilder
                    .throwInvalidParameterException("City Id must be positive: " + cityId);
        }
    }

}
