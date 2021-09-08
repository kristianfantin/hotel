package br.com.hotel.controller;

import br.com.hotel.domain.service.RedisService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/cache")
public class CacheController {

    private final RedisService redisService;

    @ApiOperation(value = "Resetting cache")
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cleanCache(HttpServletRequest request) {
        redisService.clean();
    }
}
