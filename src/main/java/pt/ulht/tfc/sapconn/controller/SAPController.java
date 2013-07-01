package pt.ulht.tfc.sapconn.controller;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.AbapException;
import com.sap.conn.jco.JCo;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoStructure;
import com.sap.conn.jco.ext.DataProviderException;
import com.sap.conn.jco.ext.DestinationDataEventListener;
import com.sap.conn.jco.ext.DestinationDataProvider;

import pt.ist.fenixframework.pstm.AbstractDomainObject;
//import pt.ulht.tfc.sapconn.controller.MyDestinationDataProvider;
import pt.ulht.tfc.sapconn.domain.Recipe;
import pt.ulht.tfc.sapconn.domain.SapConnManager;

@Controller
public class SAPController {
  
//    @RequestMapping(method=RequestMethod.GET, value="/recipes")
//    public String listRecipes(Model model) {
//
//       List<Recipe> recipes = new ArrayList<Recipe>(SapConnManager.getInstance().getRecipeSet());
//       Collections.sort(recipes);
//       model.addAttribute("recipes", recipes);
//       return "listRecipes";
//    }
    static class MyDestinationDataProvider implements DestinationDataProvider
    {
        private DestinationDataEventListener eL;
        private HashMap<String, Properties> secureDBStorage = new HashMap<String, Properties>();
        
        public Properties getDestinationProperties(String destinationName)
        {
            try
            {
                //read the destination from DB
                Properties p = secureDBStorage.get(destinationName);

                if(p!=null)
                {
                    //check if all is correct, for example
                    if(p.isEmpty())
                        throw new DataProviderException(DataProviderException.Reason.INVALID_CONFIGURATION, "destination configuration is incorrect", null);

                    return p;
                }
                
                return null;
            }
            catch(RuntimeException re)
            {
                throw new DataProviderException(DataProviderException.Reason.INTERNAL_ERROR, re);
            }
        }

        //An implementation supporting events has to retain the eventListener instance provided
        //by the JCo runtime. This listener instance shall be used to notify the JCo runtime
        //about all changes in destination configurations.
        public void setDestinationDataEventListener(DestinationDataEventListener eventListener)
        {
            this.eL = eventListener;
        }

        public boolean supportsEvents()
        {
            return true;
        }

        //implementation that saves the properties in a very secure way
        void changeProperties(String destName, Properties properties)
        {
            synchronized(secureDBStorage)
            {
                if(properties==null)
                {
                    if(secureDBStorage.remove(destName)!=null)
                        eL.deleted(destName);
                }
                else 
                {
                    secureDBStorage.put(destName, properties);
                    eL.updated(destName); // create or updated
                }
            }
        }
    } // end of MyDestinationDataProvider
    
    //business logic
    void executeCalls(String destName) throws JCoException
    {
        JCoDestination dest = null;
        try
        {
            dest = JCoDestinationManager.getDestination(destName);
            dest.ping();
            System.out.println("Destination " + destName + " works");
        }
        catch(JCoException e)
        {
            e.printStackTrace();
            System.out.println("Execution on destination " + destName+ " failed");
        }
        
        // ... it always has a reference to a metadata repository
        JCoFunction function = dest.getRepository().getFunction("BAPI_COMPANYCODE_GETLIST");
        if(function == null)
            throw new RuntimeException("BAPI_COMPANYCODE_GETLIST not found in SAP.");

        //JCoFunction is container for function values. Each function contains separate
        //containers for import, export, changing and table parameters.
        //To set or get the parameters use the APIS setValue() and getXXX(). 
        //function.getImportParameterList().setValue("REQUTEXT", "Helxxxlo SAP");
        
        try
        {
            //execute, i.e. send the function to the ABAP system addressed 
            //by the specified destination, which then returns the function result.
            //All necessary conversions between Java and ABAP data types
            //are done automatically.
            function.execute(dest);
        }
        catch(AbapException e)
        {
            System.out.println(e.toString());
            return;
        }
        
        System.out.println("STFC_CONNECTION finished:");
        System.out.println(" Echo: " + function.getExportParameterList().getString("RETURN"));
        System.out.println(" Response: " + function.getTableParameterList().getTable("COMPANYCODE_LIST").toXML());
        //System.out.println(" Response: " + function.getTableParameterList().getTable("COMPANYCODE_LIST").toString());
        System.out.println();
 
        function = null;
        //BAPI_CUSTOMER_GETLIST        
        function = dest.getRepository().getFunction("ZTFC_CUSTOMER_GETLIST");
        if(function == null)
            throw new RuntimeException("ZTFC_CUSTOMER_GETLIST not found in SAP.");

        //JCoFunction is container for function values. Each function contains separate
        //containers for import, export, changing and table parameters.
        //To set or get the parameters use the APIS setValue() and getXXX(). 
        //function.getImportParameterList().setValue("SALES_ORGANIZATION", "1000");
        
        try
        {
            //execute, i.e. send the function to the ABAP system addressed 
            //by the specified destination, which then returns the function result.
            //All necessary conversions between Java and ABAP data types
            //are done automatically.
            function.execute(dest);
        }
        catch(AbapException e)
        {
            System.out.println(e.toString());
            return;
        }
        
        System.out.println("STFC_CONNECTION finished:");
        System.out.println(" Echo: " + function.getExportParameterList().getString("RETURN"));
        System.out.println(" Response: " + function.getTableParameterList().getTable("ADDRESSDATA").toXML());
        System.out.println(" Response: " + function.getTableParameterList().getTable("ADDRESSDATA").toString());
        System.out.println();
        
        function = null;
        //BAPI_SALESORDER_GETLIST        
        function = dest.getRepository().getFunction("BAPI_SALESORDER_GETLIST");
        if(function == null)
            throw new RuntimeException("BAPI_SALESORDER_GETLIST not found in SAP.");

        //JCoFunction is container for function values. Each function contains separate
        //containers for import, export, changing and table parameters.
        //To set or get the parameters use the APIS setValue() and getXXX(). 
        function.getImportParameterList().setValue("SALES_ORGANIZATION", "1000");
        
        try
        {
            //execute, i.e. send the function to the ABAP system addressed 
            //by the specified destination, which then returns the function result.
            //All necessary conversions between Java and ABAP data types
            //are done automatically.
            function.execute(dest);
        }
        catch(AbapException e)
        {
            System.out.println(e.toString());
            return;
        }
        
        System.out.println("STFC_CONNECTION finished:");
        System.out.println(" Echo: " + function.getExportParameterList().getString("RETURN"));
        System.out.println(" Response: " + function.getTableParameterList().getTable("SALES_ORDERS").toXML());
        System.out.println(" Response: " + function.getTableParameterList().getTable("SALES_ORDERS").toString());
        System.out.println();

        
    }

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
    
    
    @RequestMapping(method=RequestMethod.GET, value="/SAP/Clientes")
    public String getClients(Model model) {
        System.out.println("/SAP/Clientes 1");
    	MyDestinationDataProvider myProvider = new MyDestinationDataProvider();
        System.out.println("/SAP/Clientes 2");
        //register the provider with the JCo environment;
        //catch IllegalStateException if an instance is already registered
        try
        {
            com.sap.conn.jco.ext.Environment.registerDestinationDataProvider(myProvider);
        }
        catch(IllegalStateException providerAlreadyRegisteredException)
        {
            //somebody else registered its implementation, 
            //stop the execution
            throw new Error(providerAlreadyRegisteredException);
        }
        System.out.println("/SAP/Clientes 3");
        String destName = "SOFTINSA_DEV";
        SAPController destination = new SAPController();
        
        //set properties for the destination and ...
        myProvider.changeProperties(destName, getDestinationPropertiesFromUI());
        //... work with it
        System.out.println("Step 1");
        try {
			destination.executeCalls(destName);
		} catch (JCoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println("Step 2");    	
    	return "listClients";
    }
    
    
//    @RequestMapping(method=RequestMethod.GET, value="/recipes/{id}")
//    public String showRecipe(Model model, @PathVariable String id) {
//
//        Recipe recipe = AbstractDomainObject.fromExternalId(id);
//        if(recipe != null){
//        	model.addAttribute("recipe", recipe);
//        	return "detailedRecipe";
//        } else {
//        	return "recipeNotFound";
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
//    }
    
    
}