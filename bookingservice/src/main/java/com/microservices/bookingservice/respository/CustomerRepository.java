package com.microservices.bookingservice.respository;

import com.microservices.bookingservice.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
