package springboot.backend.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "location_id", nullable = false)
    private Long locationId;  // 0 = ALL LOCATION

    @Column(name = "role_code", nullable = false)
    private String roleCode;

    @Column(name = "role_description", nullable = false)
    private String roleDescription;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;
}
