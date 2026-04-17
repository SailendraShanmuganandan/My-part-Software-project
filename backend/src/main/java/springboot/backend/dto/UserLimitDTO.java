package springboot.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLimitDTO {

    Long userId;
    Long locationId;

    Double creditLimit;
    Double debitLimit;
    Double loanLimit;
    Double loanReleaseAmount;

}
