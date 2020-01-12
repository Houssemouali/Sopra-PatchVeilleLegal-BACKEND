package com.sopra.patch;


import javax.annotation.Resource;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.hraccess.openhr.HRApplication;
import com.hraccess.openhr.HRSessionFactory;
import com.hraccess.openhr.IHRDataStructure;
import com.hraccess.openhr.IHRDictionary;
import com.hraccess.openhr.IHRSession;
import com.hraccess.openhr.dossier.HRDossierFactory;
import com.hraccess.openhr.dossier.IHRDossierFactory;



@SpringBootApplication
@ComponentScan ({"com.sopra.patch"})
public class PatchApplication implements CommandLineRunner {

	public static  IHRSession session = null; 

	public static void main(String[] args) {
		SpringApplication.run(PatchApplication.class, args);
		
	}
	@Override
	public void run(String... arg) throws Exception {
		
		HRApplication.configureLogs("C:\\openhr\\conf\\log4j.properties");
		// Creating from given OpenHR configuration file and connecting session to HR Access server
		
		try {
			session = HRSessionFactory.getFactory().createSession(
					new PropertiesConfiguration("C:\\Users\\houali\\Desktop\\Backend\\SpringBootJwtAuthentication\\openhr.properties"));
			IHRDictionary dictionary = getSession().getDictionary();
			System.out.println("Data structure <ZD> exists ? " + dictionary.hasDataStructure("ZD"));
			IHRDataStructure dataStructure = dictionary.getDataStructureByName("ZD");
			IHRDossierFactory dfactory=new HRDossierFactory(HRDossierFactory.TYPE_DOSSIER);
			if (dataStructure == null) {
			System.err.println("Unable to find data structure <ZD>");
			} else {
			System.out.println("Found data structure <ZD> " + dataStructure.hasDossierType("DF1"));
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public  static IHRSession getSession() {
		return session;
	}
	
		
	@Bean
	public WebMvcConfigurer corsConfigurer() {
	    return new WebMvcConfigurerAdapter() {
	        @Override
	        public void addCorsMappings(CorsRegistry registry) {
	            registry.addMapping("/**").allowedOrigins("http://localhost:4200");
	        }
	    };
	
	}}


