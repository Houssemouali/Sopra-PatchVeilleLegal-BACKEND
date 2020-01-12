package com.sopra.patch.Controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hraccess.openhr.IHRUser;
import com.sopra.patch.Exceptions.ResourceNotFoundException;
import com.sopra.patch.dao.Repository.AZDRepository;
import com.sopra.patch.dao.Repository.BlockRepository;
import com.sopra.patch.dao.Repository.DZDRepository;
import com.sopra.patch.dao.Repository.FileRepository;
import com.sopra.patch.dao.Repository.NudossRepository;
import com.sopra.patch.dao.Repository.PartieDonnDZDRepository;
import com.sopra.patch.model.AZD;
import com.sopra.patch.model.Block;
import com.sopra.patch.model.DZD;
import com.sopra.patch.model.FileP;
import com.sopra.patch.model.NudossModel;
import com.sopra.patch.model.PartieDonnDZD;
import com.sopra.patch.model.dataParts;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/file")

public class FileController {
	
	@Autowired
	private FileRepository fileRepositry;
	
	@Autowired
	private BlockRepository blocRepository;

	@Autowired
	private AZDRepository azdRepository;
	
	@Autowired
	private DZDRepository dzdRepository;
	
	@Autowired
	private PartieDonnDZDRepository partDataRepository;
	
	@Autowired
	private NudossRepository nudossRepository;
	
	IHRUser userhr;

	private long idFich;
	
	private static String UPLOAD_DIR = "uploads" ;
/**************************************************************************/
	
	// Imporatation du fichier;
	
		//  Traitement sur le fichier;
	//lire le contenu du fichier;
	
	/**** Existe un problème de path avec cette methode */ // C'est fait ok;
	
	@RequestMapping(value = "generFile/{fileName}", method = RequestMethod.GET , produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<Integer, String> generateFile(@PathVariable("fileName") String fileName){
		
		Map<Integer, String> lignes =new HashMap<Integer, String>();
		
		try {
			//System.out.println(path);
			InputStream ips = new FileInputStream("src/main/webapp/"+UPLOAD_DIR +"/"+ fileName);
			
			InputStreamReader ipsr = new InputStreamReader(ips); 
			BufferedReader br = new BufferedReader(ipsr); 	
			String ligne; 
			Integer id= new Integer(0);
			while ((ligne = br.readLine()) != null) {
				
				lignes.put(id, ligne);
				id=id+1;
				
			}
			br.close();
			return lignes;
		}
		catch(Exception e) {
			return lignes;
		}
		 
	}
	
	/****************************************************/
	//méthode qui permet d'afficher tous les fichiers existants dans la base;
	 @GetMapping("/files")
	    public List<FileP> getAllFiles() {
	        return fileRepositry.findAll();
	    }
	
	 /***********************************/
	 //ajouter un fichier;
	 @PostMapping("/files")
	 public FileP createFile(@Valid @RequestBody FileP file) {
		 
	     return fileRepositry.save(file);
	 }
	 /**************************************/
	 //récupérer un fichier
	 @GetMapping("/files/{id}")
	 public FileP getFileById(@PathVariable(value = "id") Long fileId) {
	     return fileRepositry.findById(fileId)
	             .orElseThrow(() -> new ResourceNotFoundException("file", "id", fileId));
	 }
	 /**************************************/
	// Supprimer un fichier
	 @DeleteMapping("/files/{id}")
	 public ResponseEntity<?> deleteFile(@PathVariable(value = "id") Long fileId) {
	     FileP note = fileRepositry.findById(fileId)
	             .orElseThrow(() -> new ResourceNotFoundException("file", "id", fileId));

	     fileRepositry.delete(note);

	     return ResponseEntity.ok().build();
	 }



	


/****************************/
	// la méthode qui permet le transfert des données vers le frontend ;
	 @RequestMapping(value = "getDataByFileName", method = RequestMethod.GET )
	 @CrossOrigin("*")
	 public    JSONArray  getfullData() {
		 List<PartieDonnDZD> data = (List<PartieDonnDZD>) partDataRepository.findAll();
		JSONArray arr= new JSONArray();
		JSONObject o = new JSONObject();
		//List<NudossModel> nudoss= nudossRepository.findAll();
		
		 for(int i=0;i< data.size();i++) {
			 if(data.get(i).getFich().getId()==idFich) {
			o.put(data.get(i).getNomRubrq(), data.get(i).getDonnee());
			 if(((data.get(i).getNomRubrq()).equals("NUDOSS")&& i!=0) || i==data.size()-1 ) {
				 o.put("id",data.get(i).getDzd().getIdDzd());
				arr.add(o);
				 o = new JSONObject();
			 }
		 }
		 }
		 //System.out.println(arr.get(1));
		return arr;
		
	 }

		
		
}
