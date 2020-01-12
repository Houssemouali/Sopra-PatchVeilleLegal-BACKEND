package com.sopra.patch.dao.Repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sopra.patch.model.FileP;


@Repository
public interface FileRepository extends JpaRepository<FileP, Long>{
	
	/*
	@Query("select files.name, nom_rubrique, donnee from files, block, inf_dzd, partie_donndzd where files.id= block.file and block.id_bloc=inf_dzd.block and inf_dzd.id_dzd= partie_donndzd.dzd")
	List<PartieDonnDZD> getDataByNameFile();*/
//	@Query("SELECT a FROM Absence a filep")
//    List<User> findUserByConnected (@Param("username")String username);
}
