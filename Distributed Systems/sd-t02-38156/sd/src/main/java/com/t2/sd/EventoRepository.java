
package com.t2.sd;

import java.util.Date;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface EventoRepository extends CrudRepository<Evento, Integer> {

    List<Evento> findByData(Date data);
    Evento findByNome(String nome);
}
