package com.borya.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.logicalcobwebs.proxool.ConnectionPoolDefinitionIF;
import org.logicalcobwebs.proxool.ProxoolException;
import org.logicalcobwebs.proxool.ProxoolFacade;
import org.logicalcobwebs.proxool.admin.SnapshotIF;
import com.borya.util.prop.DBProperties;

/**
 * Default Jive connection provider, which uses an internal connection pool.<p>
 *
 * @author Mayjean
 */
public class DefaultConnectionProvider implements ConnectionProvider {

	private Logger log = Logger.getLogger(getClass());

    private Properties settings;
    private String driver;
    private String serverURL;
    private String proxoolURL;
    private String username;
    private String password;
    private int minConnections = 3;
    private int maxConnections = 10;
    private int activeTimeout = 900000; // 15 minutes in milliseconds
    private String testSQL = "";
    private Boolean testBeforeUse = true;
    private Boolean testAfterUse = true;

    private String APPNAME = "BT";
    
    /**
     * Maximum time a connection can be open before it's reopened (in days)
     */
    private double connectionTimeout = 0.5;

    /**
     * MySQL doesn't currently support Unicode. However, a workaround is
     * implemented in the mm.mysql JDBC driver. Setting the Jive property
     * database.mysql.useUnicode to true will turn this feature on.
     */
    private boolean mysqlUseUnicode;

    /**
     * Creates a new DefaultConnectionProvider.
     */
    public DefaultConnectionProvider() {
        loadProperties();

//        System.setProperty("org.apache.commons.logging.LogFactory",
//        		"org.jivesoftware.util.log.util.CommonsLogFactory");
    }

    public boolean isPooled() {
        return true;
    }

    public final Connection getConnection() throws SQLException {
        try {
            Class.forName("org.logicalcobwebs.proxool.ProxoolDriver");
            return DriverManager.getConnection(proxoolURL, settings);
        }
        catch (Exception e) {
        	log.error(e.getMessage(),e);
            throw new SQLException("DbConnectionProvider: Unable to find driver: "+e);
        }
    }

    public void start() {
        proxoolURL = "proxool."+APPNAME+":"+getDriver()+":"+getServerURL();
        settings = new Properties();
        settings.setProperty("proxool.maximum-activetime", Integer.toString(activeTimeout));
        settings.setProperty("proxool.maximum-connection-count", 
        		Integer.toString(getMaxConnections()));
        settings.setProperty("proxool.minimum-connection-count", 
        		Integer.toString(getMinConnections()));
        settings.setProperty("proxool.maximum-connection-lifetime", 
        		Integer.toString((int)(86400000 * getConnectionTimeout())));
        settings.setProperty("proxool.test-before-use", testBeforeUse.toString());
        settings.setProperty("proxool.test-after-use", testAfterUse.toString());
        settings.setProperty("proxool.house-keeping-test-sql", testSQL);
        settings.setProperty("user", getUsername());
        settings.setProperty("password", (getPassword() != null ? getPassword() : ""));
    }

    public void restart() {
    }

    public void destroy() {
        settings = null;
    }

    /**
     * Returns the JDBC driver classname used to make database connections.
     * For example: com.mysql.jdbc.Driver
     *
     * @return the JDBC driver classname.
     */
    public String getDriver() {
        return driver;
    }

    /**
     * Returns the JDBC connection URL used to make database connections.
     *
     * @return the JDBC connection URL.
     */
    public String getServerURL() {
        return serverURL;
    }

    /**
     * Returns the username used to connect to the database. In some cases,
     * a username is not needed so this method will return null.
     *
     * @return the username used to connect to the datbase.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the password used to connect to the database. In some cases,
     * a password is not needed so this method will return null.
     *
     * @return the password used to connect to the database.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Returns the minimum number of connections that the pool will use. This
     * should probably be at least three.
     *
     * @return the minimum number of connections in the pool.
     */
    public int getMinConnections() {
        return minConnections;
    }

    /**
     * Returns the maximum number of connections that the pool will use. The
     * actual number of connections in the pool will vary between this value
     * and the minimum based on the current load.
     *
     * @return the max possible number of connections in the pool.
     */
    public int getMaxConnections() {
        return maxConnections;
    }

    /**
     * Returns the amount of time between connection recycles in days. For
     * example, a value of .5 would correspond to recycling the connections
     * in the pool once every half day.
     *
     * @return the amount of time in days between connection recycles.
     */
    public double getConnectionTimeout() {
        return connectionTimeout;
    }

    /**
     * Returns the SQL statement used to test if a connection is valid.
     *
     * @return the SQL statement that will be run to test a connection.
     */
    public String getTestSQL() {
        return testSQL;
    }

    /**
     * Sets the SQL statement used to test if a connection is valid.  House keeping
     * and before/after connection tests make use of this.  This
     * should be something that causes the minimal amount of work by the database
     * server and is as quick as possible.
     *
     * @param testSQL the SQL statement that will be run to test a connection.
     */
    public void setTestSQL(String testSQL) {
        this.testSQL = testSQL;
    }

    /**
     * Returns whether returned connections will be tested before being handed over
     * to be used.
     *
     * @return True if connections are tested before use.
     */
    public Boolean getTestBeforeUse() {
        return testBeforeUse;
    }

    /**
     * Sets whether connections will be tested before being handed over to be used.
     *
     * @param testBeforeUse True or false if connections are to be tested before use.
     */
    public void setTestBeforeUse(Boolean testBeforeUse) {
        this.testBeforeUse = testBeforeUse;
    }

    /**
     * Returns whether returned connections will be tested after being returned to
     * the pool.
     *
     * @return True if connections are tested after use.
     */
    public Boolean getTestAfterUse() {
        return testAfterUse;
    }

    /**
     * Sets whether connections will be tested after being returned to the pool.
     *
     * @param testAfterUse True or false if connections are to be tested after use.
     */
    public void setTestAfterUse(Boolean testAfterUse) {
        this.testAfterUse = testAfterUse;
    }

    public boolean isMysqlUseUnicode() {
        return mysqlUseUnicode;
    }

    private String getProperty(String propertyName){
    	return DBProperties.getInstance().getProperty(propertyName);
    }
    /**
     * Load properties that already exist from Jive properties.
     */
    private void loadProperties() {
        driver = getProperty("jdbc.proxool.driver");
        serverURL = getProperty("jdbc.proxool.serverURL");
        username = getProperty("jdbc.proxool.username");
        password = getProperty("jdbc.proxool.password");
        String minCons = getProperty("jdbc.proxool.minConnections");
        String maxCons = getProperty("jdbc.proxool.maxConnections");
        String conTimeout = getProperty("jdbc.proxool.connectionTimeout");
        
        //DbConnectionManager.getTestSQL(driver);
        testSQL = getProperty("jdbc.proxool.testSQL");
        testBeforeUse = true;
        testAfterUse = true;

        // See if we should use Unicode under MySQL
        mysqlUseUnicode = Boolean.valueOf(getProperty("database.mysql.useUnicode"));
        try {
            if (minCons != null) {
                minConnections = Integer.parseInt(minCons);
            }
            if (maxCons != null) {
                maxConnections = Integer.parseInt(maxCons);
            }
            if (conTimeout != null) {
                connectionTimeout = Double.parseDouble(conTimeout);
            }
        }
        catch (Exception e) {
            log.error("Error: could not parse default pool properties. " +
                    "Make sure the values exist and are correct."+e.getMessage(), e);
        }
    }

    
	public String toString() {
        try {
            ConnectionPoolDefinitionIF poolDef = 
            		ProxoolFacade.getConnectionPoolDefinition(APPNAME);
            
            SnapshotIF poolStats = ProxoolFacade.getSnapshot(APPNAME, true);
            return poolDef.getMinimumConnectionCount() + ","
            		+ poolDef.getMaximumConnectionCount() + ","
                    + poolStats.getAvailableConnectionCount() + ","
            		+ poolStats.getActiveConnectionCount();
        }catch (ProxoolException e) {
            return "Default Connection Provider";
        }
    }
}
