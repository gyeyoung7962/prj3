package com.backend.controller.theater;

import com.backend.domain.theater.Theater;
import com.backend.service.theater.TheaterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/theater")
@RequiredArgsConstructor
public class TheaterController {

    private final TheaterService service;

    @PostMapping("add")
    public void addTheater(@RequestBody Theater theater) {
        if (service.validate(theater)) {
            service.add(theater);
        }
    }

    @GetMapping("{city}")
    public List<Theater> getCity(@PathVariable String city) {
        return service.get(city);
    }
}
