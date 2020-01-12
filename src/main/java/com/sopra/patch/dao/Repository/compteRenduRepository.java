package com.sopra.patch.dao.Repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sopra.patch.model.compteRendu;

@Repository
public interface compteRenduRepository extends CrudRepository<compteRendu, Long >{

	
	
}
