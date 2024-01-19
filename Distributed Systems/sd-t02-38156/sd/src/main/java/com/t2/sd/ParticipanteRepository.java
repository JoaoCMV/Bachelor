
package com.t2.sd;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface ParticipanteRepository extends CrudRepository<Participante, Integer> {

    List<Participante> findByidE(int id);
    int countByIdE(int id);
}
