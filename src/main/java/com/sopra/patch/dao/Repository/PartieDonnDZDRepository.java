package com.sopra.patch.dao.Repository;




import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.sopra.patch.model.PartieDonnDZD;



public interface PartieDonnDZDRepository extends CrudRepository<PartieDonnDZD, Long> {
	
	@Autowired
	@Query(value="select files.date, nom_rubrique, donnee from partie_donndzd, files, block, inf_dzd where files.id = block.file and block.id_bloc= inf_dzd.block and inf_dzd.id_dzd = partie_donndzd.dzd and files.date <= ", nativeQuery = true)
	List<Object> getData();
	

	
}
