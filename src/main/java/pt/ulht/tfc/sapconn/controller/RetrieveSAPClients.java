package pt.ulht.tfc.sapconn.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Properties;
 
import com.sap.conn.jco.AbapException;
import com.sap.conn.jco.JCo;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoStructure;
import com.sap.conn.jco.ext.DataProviderException;
import com.sap.conn.jco.ext.DestinationDataEventListener;
import com.sap.conn.jco.ext.DestinationDataProvider;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import pt.ist.fenixframework.pstm.AbstractDomainObject;
import pt.ulht.tfc.sapconn.domain.Recipe;
import pt.ulht.tfc.sapconn.domain.SapConnManager;

@Controller
public class RetrieveSAPClients
{
    //The custom destination data provider implements DestinationDataProvider and
    //provides an implementation for at least getDestinationProperties(String).
    //Whenever possible the implementation should support events and notify the JCo runtime
    //if a destination is being created, changed, or deleted. Otherwise JCo runtime
    //will check regularly if a cached destination configuration is still valid which incurs
    //a performance penalty.
        
    static Properties getDestinationPropertiesFromUI()
    {
        //adapt parameters in order to configure a valid destination
        Properties connectProperties = new Properties();
        connectProperties.setProperty(DestinationDataProvider.JCO_ASHOST, "172.31.10.29");
        connectProperties.setProperty(DestinationDataProvider.JCO_SYSNR,  "00");
        connectProperties.setProperty(DestinationDataProvider.JCO_CLIENT, "200");
        connectProperties.setProperty(DestinationDataProvider.JCO_USER,   "0100543");
        connectProperties.setProperty(DestinationDataProvider.JCO_PASSWD, "softinsa2012");
        connectProperties.setProperty(DestinationDataProvider.JCO_LANG,   "pt");
        return connectProperties;
    }

//    @RequestMapping(method=RequestMethod.GET, value="/recipes")
//    public String listRecipes(Model model) {
//
//       List<Recipe> recipes = new ArrayList<Recipe>(SapConnManager.getInstance().getRecipeSet());
//       Collections.sort(recipes);
//       model.addAttribute("recipes", recipes);
//       return "listRecipes";
//    }
    
    
    @RequestMapping(method=RequestMethod.GET, value="/SAP/Clientes1")
    public String getClients1(Model model) {
//    	MyDestinationDataProvider myProvider = new MyDestinationDataProvider();
//        System.out.println("/SAP/Clientes 1");
//        
//        //register the provider with the JCo environment;
//        //catch IllegalStateException if an instance is already registered
//        try
//        {
//            com.sap.conn.jco.ext.Environment.registerDestinationDataProvider(myProvider);
//        }
//        catch(IllegalStateException providerAlreadyRegisteredException)
//        {
//            //somebody else registered its implementation, 
//            //stop the execution
//            throw new Error(providerAlreadyRegisteredException);
//        }
//        
//        System.out.println("/SAP/Clientes 2");
//        String destName = "SOFTINSA_DEV";
//        RetrieveSAPClients destination = new RetrieveSAPClients();
//        
//        //set properties for the destination and ...
//        myProvider.changeProperties(destName, getDestinationPropertiesFromUI());
//        //... work with it
//        System.out.println("Step 1");
//        try {
//			destination.executeCalls(destName);
//		} catch (JCoException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//        System.out.println("Step 2");    	
//    	return "listClients";
//    }
    
    
//    @RequestMapping(method=RequestMethod.GET, value="/recipes/{id}")
//    public String showRecipe(Model model, @PathVariable String id) {
//
//        Recipe recipe = AbstractDomainObject.fromExternalId(id);
//        if(recipe != null){
//        	model.addAttribute("recipe", recipe);
//        	return "detailedRecipe";
//        } else {
        	return "recipeNotFound";
//        }
//    }
         
//    @RequestMapping(method=RequestMethod.POST, value="/recipes")
//    public String createRecipe(@RequestParam Map<String,String> params){
//    	
//    	String titulo = params.get("titulo");
//    	String problema = params.get("problema");
//    	String solucao = params.get("solucao");
//    	String autor = params.get("autor");
//    	String tags = params.get("tags");
//    	
//    	Recipe recipe=new Recipe(titulo,problema,solucao,autor,tags);
//
//    	return "redirect:/recipes/"+recipe.getExternalId();    	
    }
    
    
}