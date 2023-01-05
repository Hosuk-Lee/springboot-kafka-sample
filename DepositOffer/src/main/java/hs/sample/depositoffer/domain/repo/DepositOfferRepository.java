package hs.sample.depositoffer.domain.repo;

import hs.sample.depositoffer.domain.entity.DepositOfferEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepositOfferRepository extends JpaRepository<DepositOfferEntity,Long> {
    Optional<DepositOfferEntity> findByCustomerId(String customerId);
}
