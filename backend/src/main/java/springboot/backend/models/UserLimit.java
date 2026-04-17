package springboot.backend.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserLimit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Double creditLimit;
    private Double debitLimit;
    private Double loanLimit;
    private Double loanReleaseAmount;

    @OneToOne(mappedBy = "userLimit")
    @JsonManagedReference
    private UserLocation userLocation;

}
