
package com.t2.sd;

import org.springframework.data.repository.CrudRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface ChipRepository extends CrudRepository<Chip, Integer> {

    Chip findByChipId2(int id);
}
