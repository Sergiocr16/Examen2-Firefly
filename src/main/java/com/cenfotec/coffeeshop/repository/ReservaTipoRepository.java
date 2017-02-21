package com.cenfotec.coffeeshop.repository;

import com.cenfotec.coffeeshop.domain.ReservaTipo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.List;
import java.util.Set;

/**
 * Spring Data JPA repository for the ReservaTipo entity.
 */
@SuppressWarnings("unused")
public interface ReservaTipoRepository extends JpaRepository<ReservaTipo,Long> {
    List<ReservaTipo> findByReservaId(Long id);
}
