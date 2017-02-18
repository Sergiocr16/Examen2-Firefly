package com.cenfotec.coffeeshop.repository;

import com.cenfotec.coffeeshop.domain.Reserva;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;


import java.util.List;

/**
 * Spring Data JPA repository for the Reserva entity.
 */
@SuppressWarnings("unused")
public interface ReservaRepository extends JpaRepository<Reserva,Long> {

    @Query("select reserva from Reserva reserva where reserva.usuario.login = ?#{principal.username}")
    Page<Reserva> findByUsuarioIsCurrentUser(Pageable pageable);

}
