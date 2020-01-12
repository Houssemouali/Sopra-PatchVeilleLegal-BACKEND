package com.sopra.patch.Controller;




import java.util.List;
import javax.validation.Valid;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.hraccess.openhr.IHRConversation;
import com.hraccess.openhr.UpdateMode;
import com.hraccess.openhr.beans.HRDataSourceParameters;
import com.hraccess.openhr.dossier.HRDossier;
import com.hraccess.openhr.dossier.HRDossierCollection;
import com.hraccess.openhr.dossier.HRDossierCollectionCommitException;
import com.hraccess.openhr.dossier.HRDossierCollectionException;
import com.hraccess.openhr.dossier.HRDossierCollectionParameters;
import com.hraccess.openhr.dossier.HRDossierFactory;
import com.hraccess.openhr.dossier.HROccur;
import com.hraccess.openhr.dossier.ICommitResult;
import com.hraccess.openhr.dossier.IHRDossierFactory;


import com.sopra.patch.Exceptions.ResourceNotFoundException;
import com.sopra.patch.dao.Repository.DZDRepository;
import com.sopra.patch.dao.Repository.HistoriqueRepository;
import com.sopra.patch.dao.Repository.NudossRepository;
import com.sopra.patch.dao.Repository.PartieDonnDZDRepository;
import com.sopra.patch.dao.Repository.compteRenduRepository;
import com.sopra.patch.model.DZD;
import com.sopra.patch.model.Historique;
import com.sopra.patch.model.NudossModel;
import com.sopra.patch.model.PartieDonnDZD;
import com.sopra.patch.model.compteRendu;



@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/dataParts")
public class PartieDonnDZDController {


	
	@Autowired
	private PartieDonnDZDRepository PartDonnRepository;
	
	@Autowired
	private DZDRepository dzdRepository;
	
	@Autowired
	private NudossRepository nudossRepository;
	
	@Autowired
	private compteRenduRepository compteRenduRepositry;
	
	@Autowired
	private HistoriqueRepository histRepository;
	
	@Autowired
	private AuthRestAPIs auth;
	long IDPatch;
	
	//méthode qui permet d'afficher tous les Données existantes dans la base;
		 @GetMapping(value="/PartDonn",produces=MediaType.APPLICATION_JSON_VALUE)
		 @CrossOrigin("*")
		    public List<Object> getAllData() {
		        return PartDonnRepository.getData();
		        
		    }
		 //*******************************************************//
		 @GetMapping(value="/PartD",produces=MediaType.APPLICATION_JSON_VALUE)
		 @CrossOrigin("*")
		    public    JSONArray getfullData() {
			 List<PartieDonnDZD> data = (List<PartieDonnDZD>) PartDonnRepository.findAll();
				JSONArray arr= new JSONArray();
				JSONObject o = new JSONObject();
				List<DZD> dzd = dzdRepository.findAll();
				List<NudossModel> nud= nudossRepository.findAll();
				
				for(int i=0;i<nud.size();i++) {
					for(int j=0;j<dzd.size();j++) {
						for(int k=0;k<data.size();k++) {
							if(nud.get(i).getIdNudoss()==dzd.get(j).getNud().getIdNudoss() && dzd.get(j).getIdDzd()==data.get(k).getDzd().getIdDzd()) {
							
									
									o.put(data.get(k).getNomRubrq(), data.get(k).getDonnee());
								
							}
						}
					}
					o.put("id",nud.get(i).getIdNudoss());
					arr.add(o);
					o = new JSONObject();
				}
				
				
				return arr;	
			
		 }
		 
		 //ajouter une donnée ;
		 /*******************************/
		 @PostMapping("/ajoutDonn")
		 public PartieDonnDZD createData(@Valid @RequestBody PartieDonnDZD Data) {
			
			 
		     return PartDonnRepository.save(Data);
		 }
		 /**************************************/
		
		 
		 
		 /*********************************/
//		 //ajouter une donnée dans un compte Rendu aprés une modification;
//		 @PostMapping("/ModifData")
//		 public compteRendu createRenduModif(@RequestBody long d ) {
//			 //ajout de la modif au niveau de la bd locale;
//			 DZD dzd= new DZD();
//			 String statut= "modification";
//			 List<DZD> data = dzdRepository.findAll();
//			 for(int i=0;i< data.size();i++) {
//					 if((data.get(i).getIdDzd()==d)  ) {
//						 dzd = data.get(i);
//					 }
//			 }
//			 compteRendu c=new compteRendu(statut,dzd);
//			 //La modification au niveau de la bd hra;
//			 
//			
//			 return compteRenduRepositry.save(c);
//			 
//		 }
//		 /******************************************/
//		// Affichage du compte Rendu
//		 
//		 @SuppressWarnings("unchecked")
//		 @GetMapping(value="/compteRendu",produces=MediaType.APPLICATION_JSON_VALUE)
//		 public   JSONArray  getAllRendu() {
//			 List<PartieDonnDZD> data = (List<PartieDonnDZD>) PartDonnRepository.findAll();
//			 List<compteRendu> c = (List<compteRendu>) compteRenduRepositry.findAll();
//			JSONArray arr= new JSONArray();
//			JSONObject o = new JSONObject();
//			
//			
//			for(int j=0;j<c.size();j++) {
//			 for(int i=0;i<data.size();i++) {
//			 if(c.get(j).getDzd().getIdDzd() == data.get(i).getDzd().getIdDzd()) {
//				 o.put(data.get(i).getNomRubrq(), data.get(i).getDonnee());
//				 o.put("id",data.get(i).getDzd().getIdDzd());
//				 o.put("statut",c.get(j).getStatut());
//				
//				 }
//			 }
//			 arr.add(o);
//			 o = new JSONObject();
//			}
//			//arr.add(o);
//			//System.out.println(arr);
//			return arr;
//			
//		 }
			
			@PostMapping("/ModifData")
			 public compteRendu createRenduModif(@RequestBody long d) { /// d = idnudoss;  // la liste des données modifiées.a partir de la partie cliente.
				 //ajout de la modif au niveau de la bd locale;
				 //ajout au niveau de la base locale;
					NudossModel nudoss= new NudossModel();
					 String statut= "Modification";
					// List<DZD> data = dzdRepository.findAll();
					 List<NudossModel> nudLi=nudossRepository.findAll();
					 System.out.println("***************"+d);
					// List<PartieDonnDZD> partdzd = (List<PartieDonnDZD>) PartDonnRepository.findAll();
					 for(int i=0;i< nudLi.size();i++) {
							 if((nudLi.get(i).getIdNudoss()==d)) {
								 nudoss = nudLi.get(i);
							 }
					 }
					 compteRendu c= new compteRendu(statut,nudoss);
					return compteRenduRepositry.save(c);
					 
				 //La modification au niveau de la bd hra;
			
					
				
				 //return compteRenduRepositry.save(c);
				 
			 }
			 /******************************************/
			// Affichage du compte Rendu
			 
			 @SuppressWarnings("unchecked")
			 @GetMapping(value="/compteRendu",produces=MediaType.APPLICATION_JSON_VALUE)
			 public   JSONArray  getAllRendu() {
				 List<PartieDonnDZD> data = (List<PartieDonnDZD>) PartDonnRepository.findAll();
				 List<compteRendu> c = (List<compteRendu>) compteRenduRepositry.findAll();
				JSONArray arr= new JSONArray();
				JSONObject o = new JSONObject();
				List<DZD> dzd = dzdRepository.findAll();
				List<NudossModel> nud= nudossRepository.findAll();
				
				
				for(int a=0;a<c.size();a++) {
				for(int i=0;i<nud.size();i++) {
					if(c.get(a).getNud().getIdNudoss()==nud.get(i).getIdNudoss()){
					for(int j=0;j<dzd.size();j++) {
						for(int k=0;k<data.size();k++) {
							if(nud.get(i).getIdNudoss()==dzd.get(j).getNud().getIdNudoss() && dzd.get(j).getIdDzd()==data.get(k).getDzd().getIdDzd()) {
							
									
									o.put(data.get(k).getNomRubrq(), data.get(k).getDonnee());
								
							}
						}
					}
					o.put("id",nud.get(i).getIdNudoss());
					o.put("statut",c.get(a).getStatut());
					arr.add(o);
					o = new JSONObject();}}
				}
				
				//arr.add(o);
				//System.out.println(arr);
				return arr;
			 }
			 /**********************************************/
			 
			 @GetMapping(value="/Historique",produces=MediaType.APPLICATION_JSON_VALUE)
			 @CrossOrigin("*")
			 public   List< Historique> getHistorique() {
				return histRepository.findAll();
			 }
}


