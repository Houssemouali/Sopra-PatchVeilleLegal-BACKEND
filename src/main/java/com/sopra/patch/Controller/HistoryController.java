package com.sopra.patch.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sopra.patch.dao.Repository.FileRepository;
import com.sopra.patch.dao.Repository.HistoriqueRepository;
import com.sopra.patch.model.FileP;
import com.sopra.patch.model.Historique;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/files")
public class HistoryController {
	
	@Autowired
	FileRepository er;
	@Autowired
	HistoriqueRepository hr;
	
	
	@GetMapping("/history")
	public Iterable<Historique> demandehistory() {
		return  hr.findAll();
	}
}
