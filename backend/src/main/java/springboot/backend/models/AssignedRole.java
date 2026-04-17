package springboot.backend.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AssignedRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roleCode;
    private String description;
    private String roleStatus;
    private LocalDate startDate;
    private LocalDate endDate;


    @OneToOne(mappedBy = "assignedRole")
    @JsonManagedReference
    private UserLocation userLocation;
}
