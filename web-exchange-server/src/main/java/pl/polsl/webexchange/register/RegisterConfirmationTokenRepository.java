package pl.polsl.webexchange.register;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RegisterConfirmationTokenRepository extends CrudRepository<RegisterConfirmationToken, Long> {
    Optional<RegisterConfirmationToken> findByToken(String token);
}
