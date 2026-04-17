package springboot.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import springboot.backend.models.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LocationRequestDto {

    private String locationCode;
    private String locationName;
    private String locationType;
    private String locationLevel;
    private String abbreviation;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String province;
    private String district;
    private String officeTelephone;
    private String mobile;
    private String email;
    private String status;

    @JsonIgnore
    public Location getEntity(EntityManager em) {
        Location location = new Location();
        location.setLocationId(null);
        BeanUtils.copyProperties(this, location);  // copies matching fields: locationCode, locationName, abbreviation, addressLine1, addressLine2, city, officeTelephone, mobile, email, status


        return location;
    }
}