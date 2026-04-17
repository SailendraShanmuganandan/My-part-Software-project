package springboot.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LocationResponseDto {

    private Long locationId;
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
}