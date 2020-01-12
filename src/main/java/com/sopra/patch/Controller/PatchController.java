	package com.sopra.patch.Controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hraccess.datasource.TDataNode;
import com.hraccess.datasource.TDataNode.Column;
import com.hraccess.openhr.HRApplication;
import com.hraccess.openhr.HRLoggingSystem;
import com.hraccess.openhr.HRSessionFactory;
import com.hraccess.openhr.IHRConversation;
import com.hraccess.openhr.IHRRole;
import com.hraccess.openhr.IHRSession;
import com.hraccess.openhr.IHRSessionUser;
import com.hraccess.openhr.IHRUser;
import com.hraccess.openhr.UpdateMode;
import com.hraccess.openhr.beans.HRDataSourceParameters;
import com.hraccess.openhr.beans.HRExtractionSource;
import com.hraccess.openhr.dossier.HRDataSect;
import com.hraccess.openhr.dossier.HRDossier;
import com.hraccess.openhr.dossier.HRDossierCollection;
import com.hraccess.openhr.dossier.HRDossierCollectionCommitException;
import com.hraccess.openhr.dossier.HRDossierCollectionException;
import com.hraccess.openhr.dossier.HRDossierCollectionParameters;
import com.hraccess.openhr.dossier.HRDossierFactory;
import com.hraccess.openhr.dossier.HRDossierListIterator;
import com.hraccess.openhr.dossier.HROccur;
import com.hraccess.openhr.dossier.HROccurListIterator;
import com.hraccess.openhr.dossier.HRValuesMap;
import com.hraccess.openhr.dossier.ICommitResult;
import com.hraccess.openhr.dossier.IHRDossierFactory;
import com.sopra.patch.PatchApplication;
import com.sopra.patch.dao.Repository.DZDRepository;
import com.sopra.patch.dao.Repository.HistoriqueRepository;
import com.sopra.patch.dao.Repository.NudossRepository;
import com.sopra.patch.dao.Repository.PartieDonnDZDRepository;
import com.sopra.patch.dao.Repository.RoleRepository;
import com.sopra.patch.dao.Repository.compteRenduRepository;
import com.sopra.patch.model.AxeFormation;
import com.sopra.patch.model.DZD;
import com.sopra.patch.model.NudossModel;
import com.sopra.patch.model.PartieDonnDZD;
import com.sopra.patch.model.Role;
import com.sopra.patch.model.RoleName;
import com.sopra.patch.model.compteRendu;
import com.sopra.patch.security.jwt.JwtAuthTokenFilter;
import com.sopra.patch.security.jwt.JwtProvider;



@CrossOrigin(value = "*",allowCredentials = "true")
	@RestController
	@RequestMapping("/patchzd")
public class PatchController {

	@Autowired
	private JwtProvider tokenProvider;
	@Autowired 
	private JwtAuthTokenFilter jwtAuthTokenFilter; 
	@Autowired
    private HttpSession httpSession;
    @Autowired
    private HttpServletRequest httpRequest;
    @Autowired
    private HttpServletResponse httpResponse;
    @Autowired
    NudossRepository nudRep; 
    //@Autowired
    //OpenHRConnection openhr;
    @Autowired
	private RoleRepository roleRepository ; 
	@Autowired
	AuthRestAPIs auth;
    
	@Autowired
	private DZDRepository dzd;

	
	/***Ajout hra declaration***/
	
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
	
	
	long IDPatch;
	
	
	
  	IHRSession session=PatchApplication.session;
	
//  	
//  	 @RequestMapping("/getModels")
// 	public ArrayList<HashMap<String,String>> getModels(){
// 				// Retrieving a connected user (not detailed here)
// 		ArrayList<HashMap<String,String>> axes = new ArrayList<HashMap<String,String>>();
// 		IHRUser user = (IHRUser) httpSession.getAttribute("user");
// 		Set<Role> roles = new HashSet<>();
//
// 		// Retrieving a role to read the records (not detailed here)
// 		List<GrantedAuthority> authorities = roles.stream().map(role -> new SimpleGrantedAuthority(role.getName().name())
// 				).collect(Collectors.toList()); 
// 		IHRRole role =(IHRRole) httpSession.getAttribute("role");
// 		
// 		HRExtractionSource extractionSource = new HRExtractionSource(user.getMainConversation(), role);
// 	
// 		extractionSource.setMaxRowCount(99999);
// 		// Setting SQL statement to perform
// 		extractionSource.setSQLExtraction("SELECT LIBLON FROM ZD01");
// 		// Connecting extraction source
// 		extractionSource.setActive(true);
// 		
// 		try {
// 		// Retrieving the result set as a TDataNode
// 		TDataNode node = extractionSource.getDataNode();
// 		// Looping over the returned rows
// 		if (node.first()) {
// 		int row=0;
// 		do {
// 			HashMap<String, String> axe = new HashMap<String,String>();
// 		System.out.println("Dumping record #" + (row+1));
// 		// Looping over the columns
// 		for (int i=0; i<node.getColumnCount(); i++) {
// 			
// 		Column column = node.getColumn(i);
// 		
// 		
// 		axe.put(column.getName(),node.getValueAsString(i));
// 		System.out.println("Column <" + column.getName() + "> = <" + node.getValue(i));
// 	
// 		}
// 		axes.add(axe);
// 		
// 		row++;
// 		} while (node.next());
// 		
// 		}
// 		} finally {
// 		if ((extractionSource != null) && extractionSource.isActive()) {
// 		// Disconnecting extraction source
// 		extractionSource.setActive(false);
// 		}
// 		}
// 		return axes;
// 	}
//     
     
     @GetMapping(value = "/getOne/{dataStructure}/{dataSection}/{process}")

     public ResponseEntity<?> getUnite(@PathVariable("dataStructure") String dataStructureParam, @PathVariable(value = "dataSection") String dataSectionsParam,
    		 @PathVariable("process") String processParam
             , @RequestParam("NUDOSS") int NUDOSS) throws Exception {

         HRApplication.setLoggingSystem(HRLoggingSystem.LOG4J);


         IHRDossierFactory dossierFactory = new HRDossierFactory(HRDossierFactory.TYPE_DOSSIER);
         //ZE// 136
         HRDossierCollectionParameters dossierCollectionParameters = new HRDossierCollectionParameters();
         dossierCollectionParameters.setType(HRDossierCollectionParameters.TYPE_NORMAL);
         dossierCollectionParameters.setDataStructureName(dataStructureParam);
         dossierCollectionParameters.addDataSection(new HRDataSourceParameters.DataSection(dataSectionsParam));
         dossierCollectionParameters.setProcessName(processParam);
         dossierCollectionParameters.setUpdateMode(UpdateMode.NORMAL);
         dossierCollectionParameters.setIgnoreSeriousWarnings(true);

        // IHRSession session=openHR.getSession();
         IHRUser user =session.getSessionUser();
         IHRConversation conversation = user.getMainConversation();
         IHRRole role =((IHRSessionUser) user).getRole();

         HRDossierCollection hrDossierCollection = new HRDossierCollection(dossierCollectionParameters,
                 conversation, role, dossierFactory);
         ;

         AxeFormation uniteOrgaZE = new AxeFormation();
         String liblon = uniteOrgaZE.getLIBLON();
         String libabr = uniteOrgaZE.getLIBABR();
         
         HRDossier hrDossiers = hrDossierCollection.loadDossier(NUDOSS);
         HROccur occurrenceZe00 = hrDossiers.getDataSectionByName("01").getOccur();
         liblon = occurrenceZe00.getString("LIBLON");
         libabr = occurrenceZe00.getString("LIBABR");
//         dtef00 =occurrenceZe00.getDate("DTEF00");
//         idos00 = occurrenceZe00.getString("IDOS00");
         HROccur occurrenceZe01 = hrDossiers.getDataSectionByName("01").getOccur();
//         lboulg = occurrenceZe01.getString("LBOULG");
//         lboush = occurrenceZe01.getString("LBOUSH");

         return ResponseEntity.status(HttpStatus.OK).body(new AxeFormation(liblon, libabr,String.valueOf(hrDossiers.getNudoss())));
     }

  	 
     
     @GetMapping(value = "/getAll/{dataStructure}/{process}")

     public ResponseEntity<HashMap<String, HRValuesMap>> getUnites(@PathVariable("dataStructure") String dataStructureParam, @PathVariable("process") String processParam) throws Exception {

         HRApplication.setLoggingSystem(HRLoggingSystem.LOG4J);

        // IHRSession session = openHR.getSession();


         IHRDossierFactory dossierFactory = new HRDossierFactory(HRDossierFactory.TYPE_DOSSIER); // 136
         HRDossierCollectionParameters dossierCollectionParameters = new HRDossierCollectionParameters();
         dossierCollectionParameters.setType(HRDossierCollectionParameters.TYPE_NORMAL);
         dossierCollectionParameters.setDataStructureName(dataStructureParam);
         dossierCollectionParameters.addDataSection(new HRDataSourceParameters.DataSection("01"));
         dossierCollectionParameters.setProcessName(processParam);
         dossierCollectionParameters.setUpdateMode(UpdateMode.NORMAL);
         dossierCollectionParameters.setIgnoreSeriousWarnings(true);

         //IHRSession session=openHR.getSession();
         IHRUser user =session.getSessionUser();
         IHRConversation conversation = user.getMainConversation();
         IHRRole role =((IHRSessionUser) user).getRole();

         System.out.println(user.getVirtualSessionId());
         HRDossierCollection hrDossierCollection = new HRDossierCollection(dossierCollectionParameters,
                 conversation,role , dossierFactory);

         HRDossierListIterator hrDossiers = hrDossierCollection.loadDossiers("SELECT A.NUDOSS FROM ZD01 A  ");
         ArrayList<HRValuesMap> result = new ArrayList<>();
         HashMap<String,HRValuesMap>result1=new HashMap<>();

         while (hrDossiers.hasNext()) {

             HRDossier dossier = hrDossiers.next();


             System.out.println("=============qsdqsdqsdqsdsqd" + dossier.getNudoss());
             System.out.println("==================NUDOSS"+user.getDescription().getUserDossierNudoss());


             List<HRDataSect> infosList = dossier.getDataSections();
             List<HRDataSect>infolist1=dossier.getDataSections();

             HRValuesMap record = new HRValuesMap();

             for (HRDataSect info : infolist1) {


                 if (!info.isMultiple()) {
                 record.putAll(info.getOccur().getValues());
                     result1.put(String.valueOf(dossier.getNudoss()),record);
                 } else {

                     HROccurListIterator occurrencesList = info.getOccurs();


                     while (occurrencesList.hasNext()) {
                         HRValuesMap occurrenceValues = occurrencesList.next().getValues();
                         occurrenceValues.putAll(record);
                         result1.put(String.valueOf(dossier.getNudoss()),occurrenceValues);
                     }
                 }
             }

       }

         return ResponseEntity.status(HttpStatus.OK).body(result1);

     }

     
     
//     @RequestMapping("/getModels")
//     public ArrayList<HashMap<String,String>> getModelzd(){
//               // Retrieving a connected user (not detailed here)
//                           ArrayList<HashMap<String,String>> axes = new ArrayList<HashMap<String,String>>();
//                         //  IHRUser user = (IHRUser) httpSession.getAttribute("user");
//                           Set<Role> roles = new HashSet<>();
//                           
//                   		Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
//                   				.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
//                   		roles.add(adminRole);
//                   		List<GrantedAuthority> authorities = roles.stream().map(role -> new SimpleGrantedAuthority(role.getName().name())
//                   		).collect(Collectors.toList()); 
//                           // Retrieving a role to read the records (not detailed here)
//                          // List<GrantedAuthority> authorities = roles.stream().map(role -> new SimpleGrantedAuthority(role.getName().name())
//                                                      //     ).collect(Collectors.toList()); 
//                           IHRRole role =(IHRRole) httpSession.getAttribute("role");
//                           HRExtractionSource extractionSource = new HRExtractionSource(authcontroller.userhr.getMainConversation(), authcontroller.userhr.getRole("ALLHRLO(FR)"));
//                           extractionSource.setMaxRowCount(99999);
//                           
//                           /*******************/  // parcourir la table des nudoss et extraire tous les nudoss dans une liste;
//                           List<NudossModel > nudLi = nudRep.findAll();
//                           for(int j=0;j<nudLi.size();j++) {
//                         	  
//                           
//                           
//                           // Setting SQL statement to perform
//                           extractionSource.setSQLExtraction("SELECT * FROM ZD"+nudLi.get(j).getDzd().getCodSegAcc()+" WHERE NUDOSS ="+nudLi.get(j).getName());
//                           System.out.println("nudoss===> "+nudLi.get(j).getName());
//                           //extractionSource.setSQLExtraction("SELECT * FROM ZD00 WHERE NUDOSS =445267");
//                           // Connecting extraction source
//                           extractionSource.setActive(true);
//                           
//                           try {
//                           // Retrieving the result set as a TDataNode
//                           TDataNode node = extractionSource.getDataNode();
//                           // Looping over the returned rows
//                           if (node.first()) {
//                           int row=0;
//                           do {
//                           HashMap<String, String> axe = new HashMap<String,String>();
//                           System.out.println("Dumping record #" + (row+1));
//                           // Looping over the columns
//                            for (int i=0; i<node.getColumnCount(); i++) {
//                                           
//                           Column column = node.getColumn(i);
//                           
//                           
//                           axe.put(column.getName(),node.getValueAsString(i));
//                           System.out.println("Column <" + column.getName() + "> = <" + node.getValue(i));
//            
//                           }
//                           axes.add(axe);
//                           
//                           row++;
//                           } while (node.next());
//                           
//                           }
//                           } finally {
//                           if ((extractionSource != null) && extractionSource.isActive()) {
//                           // Disconnecting extraction source
//                           extractionSource.setActive(false);
//                           }
//                           }
//                           /// fermeture du boucle for;
//                           }
//                           return axes;
//            } 
     
   //cette fonction de recherche CDCODE fonctionne ;		
     @RequestMapping("/getModels")
     public ArrayList<HashMap<String,String>> getModels(){
               // Retrieving a connected user (not detailed here)
                           ArrayList<HashMap<String,String>> axes = new ArrayList<HashMap<String,String>>();
                         //  IHRUser user = (IHRUser) httpSession.getAttribute("user");
                           Set<Role> roles = new HashSet<>();
                           
                   		Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
                   				.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
                   		roles.add(adminRole);
                   		//List<GrantedAuthority> authorities = roles.stream().map(role -> new SimpleGrantedAuthority(role.getName().name())
                   		//).collect(Collectors.toList()); 
                           // Retrieving a role to read the records (not detailed here)
                          // List<GrantedAuthority> authorities = roles.stream().map(role -> new SimpleGrantedAuthority(role.getName().name())
                          //     ).collect(Collectors.toList()); 
                         //  IHRRole role =(IHRRole) httpSession.getAttribute("role");
                           System.out.println("houuuuuuss"+auth.userhr.getUserId());
                           HRExtractionSource extractionSource = new HRExtractionSource(auth.userhr.getMainConversation(), auth.userhr.getRole("ALLHRLO(FR)"));
            
                         //  extractionSource.setMaxRowCount(99999);
                           
                           /*******************/  // parcourir la table des nudoss et extraire tous les nudoss dans une liste;
                          List<NudossModel > nudLi = nudRep.findAll();
                           List<DZD> dzdLi = dzd.findAll();
                          //parcourir la table Nudoss;
                           for(int j=0;j<nudLi.size();j++) {
                         	  String sql="";
                         	  String sql1="";
                         	  String sql2="";
                         	  List<String> CodSegAccLi= new ArrayList<String>();
                         	
                         	  //parcourir la table DZD;
                         	  for(int n=0;n<dzdLi.size();n++) {
                         		  
                           if( nudLi.get(j).getIdNudoss()==dzdLi.get(n).getNud().getIdNudoss()&& dzdLi.get(n).getCodSegAcc().compareTo("*1")!=0 && dzdLi.get(n).getCodSegAcc().compareTo("*2")!=0) {
                         	
                         	  
                         	  //remplir sql par bloc;
                         	  //les tables;
                         	  sql=sql+"ZD"+dzdLi.get(n).getCodSegAcc()+" a"+n+" , "; CodSegAccLi.add("a"+n);// CodSegAccLi contient les ai correspondantes aux tables ZDXX;
                         	  System.out.println(CodSegAccLi);
                         	  //la jointure;
                           }}
                         	  for(int m=0;m<CodSegAccLi.size()-1;m++) {
                         		  
                         		  sql1=sql1+CodSegAccLi.get(m)+".NUDOSS = "+CodSegAccLi.get(m+1)+".NUDOSS"+" AND ";
                         	  }
                         	
                         	  sql2=" AND "+CodSegAccLi.get(0)+".NUDOSS = ";
                         	 
                         	sql1=sql1.substring(0,sql1.length()-5);
                         	  
                          sql= sql.substring(0, sql.length()-2);
                       
                           
                          System.out.println(sql+"-----------------"+"\n"+sql1+"\n"+"++++++++++++++++++");
                         	  extractionSource.setMaxRowCount(99999);
                           // Setting SQL statement to perform
                            //extractionSource.setSQLExtraction("SELECT CDCODE, CDREGL, CDSTCO, LIBLON, LIBABR FROM "+sql+" WHERE  "+sql1+sql2+"<QB> "+nudLi.get(j).getName()+" <QE>");
                          //extractionSource.setSQLExtraction("SELECT CDCODE, CDREGL , CDSTCO, LIBLON, LIBABR FROM ZD00 A , ZD01 B, ZDAW C WHERE   A.NUDOSS = B.NUDOSS and  A.NUDOSS = C.NUDOSS");
                          extractionSource.setSQLExtraction("SELECT CDCODE, CDREGL FROM ZD00 WHERE NUDOSS = 000219847");
                        //  Connecting extraction source
                           extractionSource.setActive(true);
                           
                           try {
                           // Retrieving the result set as a TDataNode
                           TDataNode node = extractionSource.getDataNode();
                           // Looping over the returned rows
                           if (node.first()) {
                           int row=0;
                           do {
                           HashMap<String, String> axe = new HashMap<String,String>();
                           System.out.println("Dumping record #" + (row+1));
                           // Looping over the columns
                            for (int i=0; i<node.getColumnCount(); i++) {
                                           
                           Column column = node.getColumn(i);
                           
                           
                           axe.put(column.getName(),node.getValueAsString(i));
                           System.out.println("Column <" + column.getName() + "> = <" + node.getValue(i));
            
                           }
                           axes.add(axe);
                           
                           row++;
                           } while (node.next());
                           
                           }
                           } finally {
                           if ((extractionSource != null) && extractionSource.isActive()) {
                           // Disconnecting extraction source
                           extractionSource.setActive(false);
                           }
                           }
                           /// fermeture du boucle for;
                           } //}//}
                           return axes;
            }
     
     @CrossOrigin("*")
 	@RequestMapping("/getAxe")
 	public HashMap<String,String> getAxe(@RequestParam(value="nuDoss") String nuDoss ){
 		// Retrieving a connected user (not detailed here)
 		HashMap<String,String> axe = new HashMap<String,String>();
 		IHRUser user = (IHRUser) httpSession.getAttribute("user");
 		// Retrieving a role to read the records (not detailed here)
 		IHRRole role =(IHRRole) httpSession.getAttribute("role");
 		
 		HRExtractionSource extractionSource = new HRExtractionSource(user.getMainConversation(), role);
 	
 		extractionSource.setMaxRowCount(99999);
 		// Setting SQL statement to perform
 		extractionSource.setSQLExtraction("SELECT CDCODE, LIBLON, LIBABR  FROM ZD00 A , ZD01 B ,ZD21 C WHERE  A.NUDOSS=B.NUDOSS AND C.NUDOSS=B.NUDOSS AND A.CDCODE=<QB>W10<QE>");
 		// Connecting extraction source
 		extractionSource.setActive(true);
 		
 		try {
 		// Retrieving the result set as a TDataNode
 		TDataNode node = extractionSource.getDataNode();
 		// Looping over the returned rows
 		
 		System.out.println("Dumping record #" + (1));
 		// Looping over the columns
 		for (int i=0; i<node.getColumnCount(); i++) {
 			
 		Column column = node.getColumn(i);
 		axe.put(column.getName(), node.getValueAsString(i));
 		System.out.println("Column <" + column.getName() + "> = <" + node.getValue(i));
 	
 		}
 		
 		
 		
 		
 		} finally {
 		if ((extractionSource != null) && extractionSource.isActive()) {
 		// Disconnecting extraction source
 		extractionSource.setActive(false);
 		}
 		}
 		return axe;
 	}
     
     @CrossOrigin("*")
     @GetMapping("/addAxe")
     public ResponseEntity<?> ajouterAxe(HttpServletRequest request)  {
         HRApplication.setLoggingSystem(HRLoggingSystem.LOG4J);
         
         
         
         File file =new File("C:\\Users\\houali\\Desktop\\Backend\\SpringBootJwtAuthentication\\openhr.properties");
         Configuration configuration =new PropertiesConfiguration();
         configuration.setProperty("session.languages","f");
         configuration.setProperty("session.process_list","FS001,FS000,AD9TA,AS906,AT901,FD9TA,AS611 ");
         configuration.setProperty("session.login_module_hint","standardLoginModule");
         configuration.setProperty("session.work_directory","C:\\openhr\\work"); 
//         configuration.setProperty("session.work_directory","D:\\work");
         configuration.setProperty("openhr_server.server","frsopslapp038-vip.soprahronline.sopra");
         configuration.setProperty("shared_configuration.port","36112");
         configuration.setProperty("normal_message_sender.security","disabled");
         configuration.setProperty("sensitive_message_sender.security","disabled");
         configuration.setProperty("privilegied_message_sender.security","disabled"); 
         IHRSession session = null;

       /*  try {session = HRSessionFactory.getFactory().createSession(new PropertiesConfiguration(file));} catch (Exception e) {
        e.printStackTrace();
         } */
         try {
         	session=HRSessionFactory.getFactory().createSession(new PropertiesConfiguration(file));
         }catch(Exception e) {
         	e.printStackTrace();
         }
         
         
         
     IHRDossierFactory dossierFactory = new HRDossierFactory(HRDossierFactory.TYPE_DOSSIER);
     	
     HRDossierCollectionParameters parameters = new HRDossierCollectionParameters();
     	parameters.setType(HRDossierCollectionParameters.TYPE_NORMAL);
     	parameters.setProcessName("AT901");
     	parameters.setDataStructureName("ZD");
     	parameters.addDataSection(new HRDataSourceParameters.DataSection("00"));
     	parameters.addDataSection(new HRDataSourceParameters.DataSection("01"));
    	parameters.addDataSection(new HRDataSourceParameters.DataSection("HP"));
     	parameters.setUpdateMode(UpdateMode.NORMAL);
     
     	
     	String jwt = this.jwtAuthTokenFilter.getJwt(request);
 		String virtualSessionId = tokenProvider.getVirtualSessionIdFromJwtToken(jwt);
 		IHRUser user = PatchApplication.session.retrieveUser(virtualSessionId);
 		
 		Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)						
 				.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
 		Set<Role> roles = new HashSet<>();
 		IHRUser user1=session.getSessionUser();
 		IHRConversation conversation=user1.getMainConversation();
 		//roles.add(adminRole);
 		//users.setRoles(roles);
 		IHRRole role = ((IHRSessionUser)user1).getRole();
 	//	httpSession.setAttribute("user", user);
 		//httpSession.setAttribute("role",role);
 		//IHRRole role=user.getSession().retrieveUser(virtualSessionId).getRole(adminRole);
 		//userhr = (IHRUser) session.connectUser(userID.getUsername(),userID.getPassword());

     	//System.out.println("aaaaaa"+user);
     	//System.out.println("rolerole"+role);
     	//IHRConversation conversation = user.getMainConversation();
     	System.out.println("hhhhhhhhhhhhhhhhhhhh"+conversation.getUser());
     	//IHRRole role=(IHRRole) httpSession.getAttribute();
     	System.out.println("rolerolepatch "+role);
     	HRDossierCollection dossierCollection = new HRDossierCollection(parameters, conversation, role, dossierFactory);
     try {
     	//HRKey key = new HRKey(500000, "FNW", "HRKAIS");
     		HRDossier dossier = dossierCollection.createDossier();
     		System.out.println(dossier.getNudoss()+"dsddsfermqeldkeldkqzeldkqzel");
     		HROccur occurrenceZD00 = dossier.getDataSectionByName("00").getOccur();
     		occurrenceZD00.setString("CDREGL","000");
     		occurrenceZD00.setString("CDCODE","Houss");
     		occurrenceZD00.setString("CDSTCO","FNW");
     		HROccur occurrenceZD01 = dossier.getDataSectionByName("01").createOccur();
     		occurrenceZD01.setString("LIBABR","MONAXEDEFORMATION2");
     		occurrenceZD01.setString("LIBLON","MONAXE2");
     		HROccur occurrenceZDHP = dossier.getDataSectionByName("HP").createOccur();
     		occurrenceZDHP.setString("TXCOMM", "Test du formation");
     		ICommitResult result = dossierCollection.commitDossier(dossier);
     		HRDossierCollection.CommitResult parameterCommitResult = result.getParameterCommitResult();
     }
     		
     catch(HRDossierCollectionCommitException | HRDossierCollectionException e) {
     return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
     }
 return ResponseEntity.status(HttpStatus.ACCEPTED).build();
     
     }
   //ajouter une donnée dans un compte Rendu;
	 @CrossOrigin(origins= "http://localhost:4200")
	 @GetMapping("/ajoutData/{id}")
	 public compteRendu createRendu(@PathVariable long id ) {
		 //ajout au niveau de la base locale;
		 IDPatch=id;
		 NudossModel nudoss= new NudossModel();
		 String statut= "Creation";
		 List<DZD> data = dzdRepository.findAll();
		 List<NudossModel> nudLi=nudossRepository.findAll();
		 System.out.println("***************"+id);
		 List<PartieDonnDZD> partdzd = (List<PartieDonnDZD>) PartDonnRepository.findAll();
		 for(int i=0;i< nudLi.size();i++) {
				 if((nudLi.get(i).getIdNudoss()==id)) {
					 nudoss = nudLi.get(i);
				 }
		 }
		compteRendu c= new compteRendu(statut,nudoss);
		String bool= addPatchHra(id);
		if(bool.equals("success")) {
			return  compteRenduRepositry.save(c);
			}
		else {
			System.out.println("impossible d'ajouter le patch ");
		return  null;
		}
		//return  compteRenduRepositry.save(c);
	 }
	 
	 /**********Service d'ajout dans HRA a deplacer vers couche service***************/
	//ajout hra;
		public String addPatchHra(long id) {
		String val="";
		String val1="";
		 List<DZD> data = dzdRepository.findAll();
		 List<NudossModel> nudLi=nudossRepository.findAll();
		 System.out.println("***************"+id);
		 List<PartieDonnDZD> partdzd = (List<PartieDonnDZD>) PartDonnRepository.findAll();
		
			IHRDossierFactory dossierFactory = new HRDossierFactory(HRDossierFactory.TYPE_DOSSIER);
			HRDossierCollectionParameters parameters = new HRDossierCollectionParameters();
			parameters.setType(HRDossierCollectionParameters.TYPE_NORMAL);
			parameters.setProcessName("FS901");
			parameters.setDataStructureName("ZD");
			for(int j=0;j<nudLi.size();j++) {
				if((nudLi.get(j).getIdNudoss()==id)) {
					
					for(int k=0;k<data.size();k++) {
						if(nudLi.get(j).getIdNudoss()==data.get(k).getNud().getIdNudoss()) {
							if(data.get(k).getCodSegAcc().compareTo("*2")!=0 && data.get(k).getCodSegAcc().compareTo("*1")!=0) {
								// ajouter une condition sur la redondance de code segAcc;
								if(data.get(k).getCodSegAcc().compareTo("00")==0) {
									val1=data.get(k).getCodSegAcc();
									parameters.addDataSection(new HRDataSourceParameters.DataSection(data.get(k).getCodSegAcc()));
								}
								else {val1=data.get(k-1).getCodSegAcc();
								if(data.get(k).getCodSegAcc().equals(val1)) {}
								else {
									parameters.addDataSection(new HRDataSourceParameters.DataSection(data.get(k).getCodSegAcc()));
								}
								}
						}
						}
					}
				}
				
			}
			parameters.setUpdateMode(UpdateMode.NORMAL);
			parameters.setActivity("TRALST01");
			//IHRUser user = (IHRUser) httpSession.getAttribute("user");
			IHRConversation conversation = auth.userhr.getMainConversation();
			//IHRRole role=(IHRRole) httpSession.getAttribute("role");
			HRDossierCollection dossierCollection = new HRDossierCollection(parameters, conversation, auth.userhr.getRole("ALLHRLO(FR)"), dossierFactory);
			
			try {
				HRDossier dossier = dossierCollection.createDossier();
				System.out.println("NUDOSSSS ***********   "+dossier.getNudoss()+"   NUDOSSSS ***********");
				for(int j=0;j<nudLi.size();j++) { /// boucles sur les nudoss Model;
					if((nudLi.get(j).getIdNudoss()==id)) {
						
						for(int k=0;k<data.size();k++) {// boucle sur les lignes DZD;	
							if(nudLi.get(j).getIdNudoss()==data.get(k).getNud().getIdNudoss()) {
								if(data.get(k).getCodSegAcc().compareTo("*2")!=0 && data.get(k).getCodSegAcc().compareTo("*1")!=0) {
								if(data.get(k).getCodSegAcc().compareTo("00")==0) {
									HROccur occurrenceZD00 = dossier.getDataSectionByName("00").getOccur();
									for(int h=0; h<partdzd.size();h++) {
										if(data.get(k).getIdDzd()==partdzd.get(h).getDzd().getIdDzd()) {
											if(partdzd.get(h).getNomRubrq().compareTo("CDCODE")==0 || partdzd.get(h).getNomRubrq().compareTo("CDREGL")==0 || partdzd.get(h).getNomRubrq().compareTo("CDSTCO")==0 && partdzd.get(h).getNomRubrq().compareTo("IDGPRG")!=0) {
										occurrenceZD00.setString(partdzd.get(h).getNomRubrq(),partdzd.get(h).getDonnee());
											}
									}
									}
								}
								else {
									//traitement sur la condition de la redondance du code SegAcc;
									val=data.get(k-1).getCodSegAcc();
									if(data.get(k).getCodSegAcc().equals(val)) {}
									else {
									
									//String occ = "occurrenceZD"+data.get(j).getCodSegAcc();
									HROccur occ = dossier.getDataSectionByName(data.get(k).getCodSegAcc()).createOccur();
									for(int h=0;h<partdzd.size();h++) {//boucle les données
										if(data.get(k).getIdDzd()==partdzd.get(h).getDzd().getIdDzd()) {  	
											
												if(partdzd.get(h).getNomRubrq().equals("PGPDOS")){
													for(int w=h;w<h+2;w++) {
														if(partdzd.get(w).getNomRubrq().compareTo("NUORRI")!=0 && partdzd.get(w).getNomRubrq().compareTo("NBHEUR")!=0 && partdzd.get(w).getNomRubrq().compareTo("NBHEBD")!=0 ) {
															
															occ.setString(partdzd.get(w).getNomRubrq(),partdzd.get(w).getDonnee());
													}
													}
												}
											//	occ.setString(partdzd.get(h).getNomRubrq(),partdzd.get(h).getDonnee());
											}}
									
									
									}}
								
								}}}}
								}
				
				ICommitResult result = dossierCollection.commitAllDossiers();
				HRDossierCollection.CommitResult parameterCommitResult = result.getParameterCommitResult();
				if (dossier.isReal()) {
				return "success";  
				}
				else  return "failure";
				} catch (HRDossierCollectionException e) {
				System.out.println("failure" + ""+e);
				return "failure";
				}
		catch(HRDossierCollectionCommitException e) {
			return"failure";
		}
			
		 }
	 
  	
  	
  	
}
