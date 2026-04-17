package springboot.backend.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springboot.backend.dao.UserLimitRepository;
import springboot.backend.dao.UserLocationRepository;
import springboot.backend.dto.UserLimitDTO;
import springboot.backend.models.UserLimit;
import springboot.backend.models.UserLocation;
import springboot.backend.models.embeddable.UserLocationId;
import springboot.backend.service.UserLimitService;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class UserLimitServiceImpl implements UserLimitService {

    private final UserLimitRepository userLimitRepository;
    private final UserLocationRepository userLocationRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserLimitServiceImpl.class);

    public UserLimitServiceImpl(UserLimitRepository userLimitRepository,
                                UserLocationRepository userLocationRepository
    ) {
        this.userLimitRepository = userLimitRepository;
        this.userLocationRepository = userLocationRepository;
    }

    @Override
    public void addUserLimit(List<UserLimitDTO> userLimitDTOS) {
        for (UserLimitDTO dto : userLimitDTOS) {

            UserLocationId key = new UserLocationId(dto.getUserId(), dto.getLocationId());
            Optional<UserLocation> optionalUL = userLocationRepository.findById(key);

            if (optionalUL.isPresent()) {
                UserLocation userLocation = optionalUL.get();

                if (userLocation.getUserLimit() != null) {
                    logger.warn("UserLimit already exists for userId={} locationId={}",
                            dto.getUserId(), dto.getLocationId());
                    continue;
                }

                UserLimit userLimit = buildAddUserLimit(dto, userLocation);
                UserLimit savedLimit = userLimitRepository.save(userLimit);

                userLocation.setUserLimit(savedLimit);
                userLocationRepository.save(userLocation);

                logger.info("Saved UserLimit for userId={} locationId={}",
                        dto.getUserId(), dto.getLocationId());

            } else {
                logger.warn("UserLocation not found for userId={} locationId={}",
                        dto.getUserId(), dto.getLocationId());
            }
        }
    }


    private UserLimit buildAddUserLimit(UserLimitDTO dto, UserLocation userLocation) {
        return UserLimit.builder()
                .userId(dto.getUserId())
                .creditLimit(dto.getCreditLimit())
                .debitLimit(dto.getDebitLimit())
                .loanLimit(dto.getLoanLimit())
                .loanReleaseAmount(dto.getLoanReleaseAmount())
                .userLocation(userLocation)
                .build();
    }

    private UserLimit buildupdateUserLimit(UserLimitDTO dto, UserLimit existingLimit) {
        existingLimit.setUserId(dto.getUserId());
        existingLimit.setCreditLimit(dto.getCreditLimit());
        existingLimit.setDebitLimit(dto.getDebitLimit());
        existingLimit.setLoanLimit(dto.getLoanLimit());
        existingLimit.setLoanReleaseAmount(dto.getLoanReleaseAmount());

        return existingLimit;

    }

    //update user limit
    @Override
    public void updateUserLimit(List<UserLimitDTO> userLimitDTOS) {
        for (UserLimitDTO dto : userLimitDTOS) {

            UserLocationId key = new UserLocationId(dto.getUserId(), dto.getLocationId());
            Optional<UserLocation> optionalUL = userLocationRepository.findById(key);

            if (optionalUL.isEmpty()) {
                log.warn("UPDATE - UserLocation not found for userId={} locationId={}",
                        dto.getUserId(), dto.getLocationId());
                continue;
            }

            UserLocation userLocation = optionalUL.get();
            UserLimit existingLimit = userLocation.getUserLimit();

            if (existingLimit == null) {
                log.warn("UPDATE - No existing UserLimit for userId={} locationId={}, creating new.",
                        dto.getUserId(), dto.getLocationId());

                UserLimit newLimit = buildAddUserLimit(dto, userLocation);
                UserLimit savedLimit = userLimitRepository.save(newLimit);

                userLocation.setUserLimit(savedLimit);
                userLocationRepository.save(userLocation);

                log.info("UPDATE - Created new UserLimit for userId={} locationId={}",
                        dto.getUserId(), dto.getLocationId());
            } else {
                userLimitRepository.save(buildupdateUserLimit(dto, existingLimit));

                log.info("UPDATE - Successfully updated UserLimit id={} for userId={} locationId={}",
                        existingLimit.getId(), dto.getUserId(), dto.getLocationId());
            }
        }
    }


    //delete user limit
    @Override
    public void deleteUserLimit(Long userLimitId) {
        if (!userLimitRepository.existsById(userLimitId)) {
            log.warn("DELETE - UserLimit not found for id={}", userLimitId);
            throw new RuntimeException("UserLimit not found for id: " + userLimitId);
        }
        UserLimit userLimit = userLimitRepository.findById(userLimitId).get();
        UserLocation userLocation = userLimit.getUserLocation();

        if (userLocation != null) {
            userLocation.setUserLimit(null);
            userLocationRepository.save(userLocation);
            log.info("DELETE - Cleared UserLimit reference from UserLocation for userLimitId={}", userLimitId);
        }

        userLimitRepository.deleteById(userLimitId);
        log.info("DELETE - Successfully deleted UserLimit id={}", userLimitId);
    }

    @Override
    public List<UserLimit> findUserLimit(Long userId) {
        List<UserLimit> limits = userLimitRepository.findAllByUserId(userId);
        if (limits.isEmpty()) {
            log.warn("FIND - No UserLimits found for userId={}", userId);
        }
        return limits;
    }
}