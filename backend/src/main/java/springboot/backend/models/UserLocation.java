package springboot.backend.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import springboot.backend.models.embeddable.UserLocationId;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserLocation {
    @EmbeddedId
    private UserLocationId id=new UserLocationId();

    @Column(name = "user_location_id", insertable = false, updatable = false)
    private Long userLocationId;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_Id")
    @JsonBackReference
    private UserAccount userAccount;

    @ManyToOne
    @MapsId("locationId")
    @JoinColumn(name = "location_Id")
    @JsonBackReference
    private Location location;

    @OneToOne
    @JoinColumn(name = "user_limit_id" ,referencedColumnName = "id")
    @JsonBackReference
    private UserLimit userLimit;

    @OneToOne
    @JoinColumn(name = "assigned_role_id" ,referencedColumnName = "id")
    @JsonBackReference
    private AssignedRole assignedRole;

    //    @ManyToOne
//    @JoinColumn(name = "role_id" ,referencedColumnName = "id")
//    @JsonBackReference
//    private Role role;
//
    //role_id
    private Long roleId;

}


