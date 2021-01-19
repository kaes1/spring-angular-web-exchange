package pl.polsl.webexchange.operation;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.polsl.webexchange.user.User;

import java.time.LocalDateTime;
import java.util.List;

public interface OperationRepository extends JpaRepository<Operation, Long> {
    List<Operation> findTop20ByUserOrderByDateTimeDesc(User user);
    List<Operation> findAllByUserAndDateTimeBetweenOrderByDateTimeDesc(User user, LocalDateTime dateFrom, LocalDateTime dateTo);
}
