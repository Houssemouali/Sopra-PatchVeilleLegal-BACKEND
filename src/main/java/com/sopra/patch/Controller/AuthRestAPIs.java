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
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hraccess.datasource.TDataNode;
import com.hraccess.datasource.TDataNode.Column;
import com.hraccess.openhr.IHRRole;
import com.hraccess.openhr.IHRSession;
import com.hraccess.openhr.IHRUser;
import com.hraccess.openhr.beans.HRExtractionSource;
import com.hraccess.openhr.exception.UserConnectionException;
import com.sopra.patch.PatchApplication;
import com.sopra.patch.dao.Repository.AZDRepository;
import com.sopra.patch.dao.Repository.BlockRepository;
import com.sopra.patch.dao.Repository.DZDRepository;
import com.sopra.patch.dao.Repository.FileRepository;
import com.sopra.patch.dao.Repository.HistoriqueRepository;
import com.sopra.patch.dao.Repository.NudossRepository;
import com.sopra.patch.dao.Repository.PartieDonnDZDRepository;
import com.sopra.patch.dao.Repository.RoleRepository;
import com.sopra.patch.dao.Repository.UserRepository;
import com.sopra.patch.dao.Repository.compteRenduRepository;
import com.sopra.patch.message.request.FileForm;
import com.sopra.patch.message.request.LoginForm;
import com.sopra.patch.message.response.JwtResponse;
import com.sopra.patch.model.AZD;
import com.sopra.patch.model.Block;
import com.sopra.patch.model.DZD;
import com.sopra.patch.model.FileP;
import com.sopra.patch.model.Historique;
import com.sopra.patch.model.NudossModel;
import com.sopra.patch.model.PartieDonnDZD;
import com.sopra.patch.model.Role;
import com.sopra.patch.model.RoleName;
import com.sopra.patch.model.User;
import com.sopra.patch.model.dataParts;
import com.sopra.patch.model.historyType;
import com.sopra.patch.security.jwt.JwtProvider;
import com.sopra.patch.security.service.UserPrinciple;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class AuthRestAPIs {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtProvider jwtProvider;

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
    private HttpSession httpSession;
	@Autowired
	private NudossRepository nudossRepository;
	 @Autowired
	    private HttpServletRequest httpRequest;
	    @Autowired
	    private HttpServletResponse httpResponse;
	    @Autowired
	    NudossRepository nudRep; 
	    
	    @Autowired
	    HistoriqueRepository historyRep;
	
	    long idFich;
		 NudossModel nud = new NudossModel();
		private static String UPLOAD_DIR = "uploads" ;
	IHRUser userhr;
	IHRUser usera = null;
	public static IHRSession session = null;

	

	
	
	
	@PostMapping("/login")
	
	public ResponseEntity<?> login(@RequestBody LoginForm userID,
			@ModelAttribute("connected_user") final IHRUser connected_user,
			final RedirectAttributes redirectAttributes) throws com.hraccess.openhr.exception.AuthenticationException {
		
		IHRSession session=null;
		String jwt ; 
		//final static String conn_user;
		Set<Role> roles = new HashSet<>();
		Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
				.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
		roles.add(adminRole);
		List<GrantedAuthority> authorities = roles.stream().map(role -> new SimpleGrantedAuthority(role.getName().name())
		).collect(Collectors.toList()); 
	
		try {
			session = PatchApplication.session;
			UserPrinciple user = new UserPrinciple(userID.getUsername(), userID.getPassword(), authorities);
			//Date dat = new Date();
//			User user1 = new User(userID.getUsername(), userID.getPassword(),roles);
//			userRepository.save(user1);
			userhr = (IHRUser) session.connectUser(userID.getUsername(),userID.getPassword());
			jwt = jwtProvider.generateJwtToken(user,userhr.getVirtualSessionId());
			redirectAttributes.addFlashAttribute("connected_user",userhr.getLabel());
			System.out.println("USER"+userhr.isConnected()+"*********"+userhr.getLabel());
			System.out.println("****"+userhr.getRoleCount()+userhr.getSession());
		 } catch (AuthenticationException | UserConnectionException | IllegalStateException e) {
			 return null;
		}
		

		return ResponseEntity.ok(new JwtResponse(jwt,userhr.getLabel(),authorities));
	}
	
	
	
	
	@RequestMapping(value = "upload", method = RequestMethod.POST )
	public void upload(
			@RequestParam("file") MultipartFile file,@RequestParam("name") String namee,
			HttpServletRequest request ) {
		
		
	 
	 
	FileP filep=new FileP();
	 String  fileName = file.getOriginalFilename();
	try {
		//String fileName= file.getOriginalFilename();
		String path = request.getServletContext().getRealPath("") + UPLOAD_DIR + File.separator + fileName;
	
		saveFile(file.getInputStream(),path);
		//generateFile(fileName);
		String name=fileName;
		String Path=path;
	
		double size = file.getSize();
		
		
		 Date dat= new Date();
			String connectedUser=userhr.getLabel();

		   
		    filep = new FileP(namee,Path,size,dat,connectedUser);
		    
		 fileRepositry.save(filep);
		 
		 Historique hist = new Historique(namee,dat,userhr.getLabel(),historyType.Upload_file);
		 
		 historyRep.save(hist);
		 idFich= filep.getId();
		System.out.println( filep.getId());
	}
	
	catch(Exception e){
		System.out.println(e);}
	

		
			// LinkedHashMap<Integer, ArrayList<dataParts>> Res = new LinkedHashMap<Integer, ArrayList<dataParts>>();
	     	
			// Integer id=new Integer(0);
				String ComptEng, TypEnrg = "", NomStrDonn,NuDoss ="",Enreg, CodeSegAcc, NumOrdCol, NomInfor,NomRubrq, NomCol,FormatCol,
				FormatRubrq, NbrDec, LongExtRubq, DeplRubq, TypeStrucDonn,TemoinRubqArgTri, TemoinRubqCodSoc, 
				TemoinRubqStrucDonn, TemoinMovFic,PartDonn;
				
				String Donn;
				 
				
				try { 
				
					
					//String Path="http://localhost:4200/files/"+" ";
					InputStream ips = new FileInputStream("src/main/webapp/"+UPLOAD_DIR +"/"+ fileName); 
					
					InputStreamReader ipsr = new InputStreamReader(ips); 
					BufferedReader br = new BufferedReader(ipsr); 
					String ligne; 
					//// initialisation des blocs;
					
					Block bloc= new Block(); 
					//blocRepository.save(bloc);
					//int q=0;
					while ((ligne = br.readLine()) != null) { 
					
						////////////////////////////////////////////////
						ComptEng=ligne.substring(0, 4);
						TypEnrg = ligne.substring(4,5);
						NomStrDonn=ligne.substring(5, 7);
						NuDoss=ligne.substring(7, 16); 
						Enreg=ligne.substring(16, 17);//typeInf
						CodeSegAcc=ligne.substring(17, 19);
						NumOrdCol=ligne.substring(19, 22);//System.out.println(NumOrdCol);//y
						
						
						//traitement de la ligne A;
						
						
						if(TypEnrg.equals("A")) {
							if(CodeSegAcc.compareTo("*2")!=0 && CodeSegAcc.compareTo("*1")!=0 && CodeSegAcc.compareTo("*7")!=0) {
								 bloc= new Block("block de l'information"+CodeSegAcc,filep,CodeSegAcc); 
									blocRepository.save(bloc);
							//if(Z==y) {y=0;Z=-1;i=0;
							//for(int j=0;j<LongExtA.length;j++) {
							
								//LongExtA[j]=0;	
								
						//}
							//NomRub.clear();v=0;NomDonn.clear();k=0;
							
							//// Blockk (id,description)  // transfert des blocks;
							
							
							
							//}
							//Res.put(id, fichline);id++;
							//y++; 
						NomInfor=ligne.substring(22, 24);
						NomRubrq=ligne.substring(24, 30); //NomRub.put(v,NomRubrq);//v++;
						NomCol=ligne.substring(30, 60);
						FormatCol=ligne.substring(60, 69);
						FormatRubrq=ligne.substring(69, 70);
						NbrDec=ligne.substring(70, 71);
						LongExtRubq=ligne.substring(71, 75);
						DeplRubq=ligne.substring(75, 79);
						TypeStrucDonn=ligne.substring(79, 80);
						TemoinRubqArgTri=ligne.substring(80, 81);
						TemoinRubqCodSoc=ligne.substring(81, 82);
						TemoinRubqStrucDonn=ligne.substring(82, 83);
						TemoinMovFic=ligne.substring(83, 84);
						System.out.print(ComptEng+"  "+TypEnrg+"  "+NomStrDonn+"  "+NuDoss+"  "+Enreg+"  "+CodeSegAcc+"  "+NumOrdCol+"  "+NomInfor+"  "+
								NomRubrq+"  "+NomCol+"  "+FormatCol+"  "+FormatRubrq+"  "+NbrDec+"  "+LongExtRubq+"  "+DeplRubq+"  "+
								TypeStrucDonn+"  "+TemoinRubqArgTri+"  "+TemoinRubqCodSoc+"  "+TemoinRubqStrucDonn+"  "+TemoinMovFic);
						
						// traitement du LongExt;
						
						///////traitement du block 
						
						
						
						// AZD (kolchay ken id + block nefsou);
						AZD azd = new AZD(ComptEng, TypEnrg, NomStrDonn, NuDoss, Enreg, CodeSegAcc, NumOrdCol, NomInfor, 
								NomRubrq, NomCol, FormatCol, FormatRubrq, NbrDec, LongExtRubq, DeplRubq, 
								TypeStrucDonn, TemoinRubqArgTri, TemoinRubqCodSoc, TemoinRubqStrucDonn, TemoinMovFic,bloc);
						
						azdRepository.save(azd);
						
						}
						}
						//traitement de la ligne D dans le fichier plat;
						
						
						else if(TypEnrg.equals("D")) {
							
							if(CodeSegAcc.compareTo("00")==0) {
							nud = new NudossModel(NuDoss,filep);
							nudossRepository.save(nud);}
							
							int q=0;// initialisation partieDonnee
							PartDonn=ligne.substring(22);
							System.out.print( ComptEng+"  "+TypEnrg+"  "+NomStrDonn+"  "+NuDoss+"  "+Enreg+"  "+CodeSegAcc+"  "
									+NumOrdCol+"  ");
							DZD dzd = new DZD( ComptEng, TypEnrg, NomStrDonn, NuDoss, Enreg, CodeSegAcc, 
									NumOrdCol, PartDonn,bloc,nud );
							dzdRepository.save(dzd);
							if(CodeSegAcc.compareTo("*2")!=0 && CodeSegAcc.compareTo("*1")!=0 ) {
							
							List<Block> Blist= blocRepository.findAll();
							List<AZD>azdList=azdRepository.findAll();
							
							for(int a=0;a<Blist.size();a++) {
								
								if(CodeSegAcc.equals(Blist.get(a).getCode())) {
									for(int x=0; x<azdList.size();x++) {
										if(Blist.get(a).getFile().getId()==idFich) {
										if(Blist.get(a).getIdBloc()==azdList.get(x).getBloc().getIdBloc()) {
											Integer w = Integer.parseInt((azdList.get(x).getDepRubriq()));
											Donn=PartDonn.substring(q,q+w);
											//System.out.println(azdList.get(x).getNomCol()+"*******"+Donn+"*******");
											q=q+w;
											
											PartieDonnDZD partData = new PartieDonnDZD(azdList.get(x).getNomCol(),Donn,dzd,filep);
											
											partDataRepository.save(partData);
											System.out.print("  "+Donn+"  ");
								
								}
								
							}
							
						}}}}
						//	NudossModel nud = new NudossModel(NuDoss,filep);
							
							
							
							
						}
						System.out.println("\n"+"*********************************");
						
				} 
					
					//System.out.println(line);
					br.close(); 
					 
					} catch (Exception e) { 
					       System.out.println( e.toString()); 
					
				}
					
					 
			
		}
	
	
	
	private void saveFile(InputStream inputStream,String path) {
		
		try {
			OutputStream outputStream = new FileOutputStream(new File(path));
			
			int read=0;
			byte[] bytes = new byte[1024];
			while((read = inputStream.read(bytes))!= -1) {
				outputStream.write(bytes,0,read);
			}
			
			outputStream.flush();
			outputStream.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	/********************************************/

	
}
