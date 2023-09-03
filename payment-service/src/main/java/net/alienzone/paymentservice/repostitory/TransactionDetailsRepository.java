package net.alienzone.paymentservice.repostitory;

import net.alienzone.paymentservice.entiry.TransactionDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionDetailsRepository extends JpaRepository<TransactionDetails, Long> {
    TransactionDetails findByOrderId(Long orderId);
}
