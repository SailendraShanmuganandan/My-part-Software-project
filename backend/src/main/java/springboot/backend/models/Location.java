package springboot.backend.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long locationId;

    //Normalize this entity
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

    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL ,orphanRemoval = true)
    @JsonManagedReference
    private List<UserLocation> userLocations = new ArrayList<>();
}