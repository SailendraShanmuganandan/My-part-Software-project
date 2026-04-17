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
public class UserAccount {

    @Id
    private Long userId;

    private String username;
    private String epfNo;//change this to number
    private String idNumber;
    private String designation;
    private String userLevel;

    //one user account for the one user
    @OneToOne(cascade = CascadeType.ALL)
    @JsonManagedReference
    private Employee employee;

    @OneToMany(mappedBy = "userAccount", cascade = CascadeType.ALL , orphanRemoval = true)
    @JsonManagedReference
    private List<UserLocation> userLocations = new ArrayList<>();


}
