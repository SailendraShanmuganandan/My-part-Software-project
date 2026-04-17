package springboot.backend.models;

import jakarta.persistence.*;
import lombok.*;
import springboot.backend.models.ModuleType;

@Entity
@Table(name = "functions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SystemFunction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "module", nullable = false)
    private ModuleType module;

    // Stored in UPPERCASE; unique across all records
    @Column(name = "function_id", nullable = false, unique = true)
    private String functionId;

    @Column(name = "function_description", nullable = false)
    private String functionDescription;

    // Must be >= 1
    @Column(name = "function_level", nullable = false)
    private Integer functionLevel;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;
}
