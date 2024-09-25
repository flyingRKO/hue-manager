package com.rko.huemanager.repository;

import com.rko.huemanager.domain.Employee;
import com.rko.huemanager.domain.ItemRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRequestRepository extends JpaRepository<ItemRequest, Long> {
    Page<ItemRequest> findAllByEmployee(Employee employee, Pageable pageable);
}
