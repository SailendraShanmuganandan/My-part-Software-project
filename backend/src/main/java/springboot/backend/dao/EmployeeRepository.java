package springboot.backend.dao;

import lombok.NonNull;
import org.springframework.stereotype.Repository;
import springboot.backend.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Repository
public interface EmployeeRepository  extends JpaRepository<@NonNull Employee, @NonNull Long>
{
    @Override
    @NonNull
    Optional<@NonNull Employee> findById(@NonNull Long aLong);

}