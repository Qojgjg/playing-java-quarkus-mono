package org.acme.entity;

import java.util.List;

import org.acme.entity.enums.CartStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface CartRepository extends JpaRepository<Cart, Long> {
     List<Cart> findByStatus(CartStatus status);

     List<Cart> findByStatusAndCustomerId(CartStatus status, Long customerId);

}