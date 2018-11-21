package com.github.brunoabdon.planinha.planinhacore.heroku;

import java.net.URI;

import org.jboss.logging.Logger;
import org.jboss.logging.Logger.Level;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.datasources.DatasourceArchive;

public class HerokuThornTailBootstrapper  {

    private static final String ENV_VARIABLE_DATABASE_URL = "DATABASE_URL";
    private static final String POSTGRESQL_JDBC_PREFIX = "jdbc:postgresql://";
    private static final String POSTGRESQL_JDBC_DRIVER_NAME = "org.postgresql";
    private static final String POSTGRESQL_DATASOURCE_NAME = "planinhaDS";

    //só pode inicializar depois de startar o swarm.
    private static Logger logger;
    
    public static void main(String[] args) throws Exception {
    	
    	final Swarm swarm = new Swarm(args);

    	swarm.start();

        inicializaLogger();
    	
    	logger.log(Level.INFO, "Aplicação iniciada.");

        //ensure the Heroku datasource is deployed before the JEE 
        //application, so that the latter's persistence unit will be able 
        //to find a suitable datasource
        final String envVar = System.getenv(ENV_VARIABLE_DATABASE_URL);
        
        logger.logv(
    		Level.INFO, "Lendo environment variable {0}.", 
    		ENV_VARIABLE_DATABASE_URL 
		);
            
        if(envVar == null) {
        	throw new Exception(
    			"Configure a env var " + ENV_VARIABLE_DATABASE_URL
			);
        }

        logger.log(Level.DEBUG, "Deployando datasource.");
		swarm.deploy(buildDatasourceArchive(envVar));

		logger.log(Level.DEBUG, "Deployando o resto.");
        swarm.deploy(swarm.createDefaultDeployment());        
    }

	private static void inicializaLogger() {
		logger = 
    		Logger.getLogger(HerokuThornTailBootstrapper.class.getName());
	}
    
    /**
     * Creates a {@link DatasourceArchive} instance pointing to the Heroku 
     * managed PostgreSQL database.
     *
     * @param herokuDatabaseUrl The URL pointing to the Heroku managed 
     * PostgreSQL database.
     * @throws Exception se a string estiver mal formada de alguma forma.
     */
    private static DatasourceArchive buildDatasourceArchive(
    		final String herokuDatabaseUrl) throws Exception {
        
        logger.log(Level.TRACE, "Criando URI da envvar");
    	final URI dbUri = new URI(herokuDatabaseUrl);
    	
    	
        logger.log(Level.TRACE, "Lendo usuário e senha da envvar");
    	final String userInfo = dbUri.getUserInfo();

    	if(userInfo == null) {
    		logger.log(
				Level.ERROR, 
				"Envvar de acesso ao banco não parece conter usuário e senha. "
				+ "Duvido que vá funcionar. Abortando."
			);
    		throw new Exception("Envvar de acesso ao banco malformada");
    	}
    	
    	
        final String query = dbUri.getQuery();
		final String dbUrl =
            POSTGRESQL_JDBC_PREFIX
            + dbUri.getHost()
            + ":" + dbUri.getPort()
            + dbUri.getPath()
            + (query != null ? "?" + query : "");

		logger.logv(Level.INFO, "JDBC URL: {0}.", dbUrl);
		
        final DatasourceArchive datasourceArchive = 
    		ShrinkWrap.create(DatasourceArchive.class,"datasourcePraHeroku")
           .dataSource(
    		   POSTGRESQL_DATASOURCE_NAME, 
    		   dataSource -> {
    			   dataSource.connectionUrl(dbUrl);
    			   dataSource.driverName(POSTGRESQL_JDBC_DRIVER_NAME);

		            if (userInfo != null) {
		                final String[] splitted = userInfo.split(":");
		                final String username = splitted[0];
		                logger.logv(
	                		Level.DEBUG,"Usuário: {0}. Senha: *****",
	                		username
                		);

		                dataSource.userName(username);
		                dataSource.password(splitted[1]);
		
		            }
    		   }
		   );

        return datasourceArchive;
    }
}
