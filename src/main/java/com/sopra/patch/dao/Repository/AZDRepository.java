package com.sopra.patch.dao.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sopra.patch.model.AZD;


@Repository
public interface AZDRepository extends JpaRepository<AZD, Long>{

}
