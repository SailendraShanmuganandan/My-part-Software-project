package springboot.backend.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springboot.backend.dao.LocationRepository;
import springboot.backend.dto.LocationRequestDto;
import springboot.backend.dto.LocationResponseDto;
import springboot.backend.models.Location;
import springboot.backend.service.LocationService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationDao;

    @PersistenceContext
    private EntityManager entityManager;

    public LocationServiceImpl(LocationRepository locationDao) {
        this.locationDao = locationDao;
    }

    @Override
    public void createLocation(LocationRequestDto dto) {
        locationDao.save(dto.getEntity(entityManager));
    }

    @Override
    public void updateLocation(Long locationId, LocationRequestDto dto) {
        Location existing = locationDao.findById(locationId)
                .orElseThrow(() -> new EntityNotFoundException("Location not found: " + locationId));
        Location updated = dto.getEntity(entityManager);
        updated.setLocationId(existing.getLocationId());
        locationDao.save(updated);
    }

    @Override
    public void deleteLocation(Long LocationId) {
        if (!locationDao.existsById(LocationId)) {
            throw new RuntimeException("Location not found with id: " + LocationId);
        }
        locationDao.deleteById(LocationId);
    }


    @Override
    public LocationResponseDto getLocationById(Long locationId) {
        Location location = locationDao.findById(locationId)
                .orElseThrow(() -> new EntityNotFoundException("Location not found: " + locationId));
        return toDto(location);
    }

    @Override
    public List<LocationResponseDto> getAllLocations() {
        return locationDao.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<LocationResponseDto> searchLocations(String locationCode, String locationName,
                                                     Long locationTypeId, String status) {
        return locationDao.findAll((root, query, cb) -> {
            List<jakarta.persistence.criteria.Predicate> predicates = new ArrayList<>();

            if (locationCode != null && !locationCode.isEmpty())
                predicates.add(cb.like(cb.lower(root.get("locationCode")), "%" + locationCode.toLowerCase() + "%"));

            if (locationName != null && !locationName.isEmpty())
                predicates.add(cb.like(cb.lower(root.get("locationName")), "%" + locationName.toLowerCase() + "%"));


            if (status != null && !status.isEmpty())
                predicates.add(cb.equal(root.get("status"), status.toUpperCase()));

            return cb.and(predicates.toArray(new jakarta.persistence.criteria.Predicate[0]));
        }).stream().map(this::toDto).collect(Collectors.toList());
    }

    //mapper
    private LocationResponseDto toDto(Location location) {
        LocationResponseDto dto = new LocationResponseDto();
        BeanUtils.copyProperties(location, dto);

        return dto;
    }
}