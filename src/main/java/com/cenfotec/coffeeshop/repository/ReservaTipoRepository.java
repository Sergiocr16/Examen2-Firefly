package com.cenfotec.coffeeshop.repository;

import com.cenfotec.coffeeshop.domain.ReservaTipo;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ReservaTipo entity.
 */
@SuppressWarnings("unused")
public interface ReservaTipoRepository extends JpaRepository<ReservaTipo,Long> {

}
