package com.sopra.patch.dao.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sopra.patch.model.NudossModel;

@Repository
public interface NudossRepository extends  JpaRepository<NudossModel, Long> {

}
