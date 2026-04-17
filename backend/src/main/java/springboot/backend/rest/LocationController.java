package springboot.backend.rest;

import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.backend.dto.LocationRequestDto;
import springboot.backend.dto.LocationResponseDto;
import springboot.backend.service.LocationService;

import java.util.List;

@RestController
@RequestMapping("/location")
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @PostMapping("/create")
    public ResponseEntity<@NonNull String> createLocation(@RequestBody LocationRequestDto dto) {
        locationService.createLocation(dto);
        return ResponseEntity.ok("Location created successfully");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<@NonNull String> updateLocation(@PathVariable Long id,
                                                          @RequestBody LocationRequestDto dto) {
        locationService.updateLocation(id, dto);
        return ResponseEntity.ok("Location updated successfully");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<@NonNull String> deleteLocation(@PathVariable Long id) {
        locationService.deleteLocation(id);
        return ResponseEntity.ok("Location deactivated successfully");
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<LocationResponseDto> getLocationById(@PathVariable Long id) {
        return ResponseEntity.ok(locationService.getLocationById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<LocationResponseDto>> getAllLocations() {
        return ResponseEntity.ok(locationService.getAllLocations());
    }

    @GetMapping("/search")
    public ResponseEntity<List<LocationResponseDto>> searchLocations(
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long type,
            @RequestParam(required = false) String status) {
        return ResponseEntity.ok(locationService.searchLocations(code, name, type, status));
    }
}