package springboot.backend.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import springboot.backend.models.UserLocation;
import springboot.backend.models.embeddable.UserLocationId;

public interface UserLocationRepository extends JpaRepository<UserLocation, UserLocationId> {

}