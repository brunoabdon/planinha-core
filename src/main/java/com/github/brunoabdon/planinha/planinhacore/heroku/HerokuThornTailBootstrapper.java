package com.github.brunoabdon.planinha.planinhacore.heroku;

import java.net.URI;
import java.net.URISyntaxException;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.datasources.DatasourceArchive;

public class HerokuThornTailBootstrapper  {

    private static final String ENV_VARIABLE_DATABASE_URL = "DATABASE_URL";
    private static final String POSTGRESQL_JDBC_PREFIX = "jdbc:postgresql://";
    private static final String POSTGRESQL_JDBC_DRIVER_NAME = "org.postgresql";
    private static final String POSTGRESQL_DATASOURCE_NAME = "planinhaDS";
    
    public static void main(String[] args) throws Exception {
          final Swarm container = new Swarm(args);
            container.start();

            //ensure the Heroku datasource is deployed before the JEE 
            //application, so that the latter's persistence unit will be able 
            //to find a suitable datasource
            container.deploy(
        		buildDatasourceArchive(
    				System.getenv(ENV_VARIABLE_DATABASE_URL)
				)
    		);

            //deploy the JEE 7 application
            container.deploy(container.createDefaultDeployment());        
    }
    
    /**
     * Creates a {@link DatasourceArchive} instance pointing to the Heroku 
     * managed PostgreSQL database.
     *
     * @param herokuDatabaseUrl The URL pointing to the Heroku managed 
     * PostgreSQL database.
     */
    private static DatasourceArchive buildDatasourceArchive(
    		final String herokuDatabaseUrl) throws URISyntaxException {
        
    	final URI dbUri = new URI(herokuDatabaseUrl);
        String userInfo = dbUri.getUserInfo();

        final String query = dbUri.getQuery();
		final String dbUrl =
            POSTGRESQL_JDBC_PREFIX
            + dbUri.getHost()
            + ":" + dbUri.getPort()
            + dbUri.getPath()
            + (query != null ? "?" + query : "");

        final DatasourceArchive datasourceArchive = 
    		ShrinkWrap.create(DatasourceArchive.class)
           .dataSource(
    		   POSTGRESQL_DATASOURCE_NAME, 
    		   dataSource -> {
    			   dataSource.connectionUrl(dbUrl);
    			   dataSource.driverName(POSTGRESQL_JDBC_DRIVER_NAME);

		            if (userInfo != null) {
		                final String[] splitted = userInfo.split(":");
		                dataSource.userName(splitted[0]);
		                dataSource.password(splitted[1]);
		
		            }
    		   }
		   );

        return datasourceArchive;
    }
    
}
