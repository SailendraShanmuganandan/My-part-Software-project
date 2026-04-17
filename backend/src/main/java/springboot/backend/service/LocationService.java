package springboot.backend.service;

import springboot.backend.dto.LocationRequestDto;
import springboot.backend.dto.LocationResponseDto;

import java.util.List;

public interface LocationService {

    void createLocation(LocationRequestDto dto);

    void updateLocation(Long locationId, LocationRequestDto dto);

    void deleteLocation(Long locationId);

    LocationResponseDto getLocationById(Long locationId);

    List<LocationResponseDto> getAllLocations();

    List<LocationResponseDto> searchLocations(String locationCode, String locationName,
                                              Long locationTypeId, String status);
}