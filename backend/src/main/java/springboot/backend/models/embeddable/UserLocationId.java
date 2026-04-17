package springboot.backend.models.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

//use for this class: generate composite key for the UserLocation Table (UserID+LocationID)
@Getter
@Setter
@Embeddable
public class UserLocationId implements Serializable {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "location_id")
    private Long locationId;


    public UserLocationId(){}

    public UserLocationId(Long userId, Long locationId) {
        this.userId = userId;
        this.locationId = locationId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserLocationId)) return false;
        UserLocationId that = (UserLocationId) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(locationId, that.locationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, locationId);
    }
}
