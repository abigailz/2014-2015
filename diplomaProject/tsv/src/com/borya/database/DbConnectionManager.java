package com.borya.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

public final class DbConnectionManager {

	private static Logger log = Logger.getLogger(DbConnectionManager.class);
	private static final Object providerLock = new Object();
//	private static PooledDataSourceProxy dataSourceProxy;
	private static ConnectionProvider connectionProvider;
	
	// True if connection profiling is turned on. Always false by default.
    private static boolean profilingEnabled = false;

    // True if the database support transactions.
    private static boolean transactionsSupported;
    // True if the database requires large text fields to be streamed.
    private static boolean streamTextRequired;
    /** True if the database supports the Statement.setMaxRows() method. */
    private static boolean maxRowsSupported;
    /** True if the database supports the rs.setFetchSize() method. */
    private static boolean fetchSizeSupported;
    // True if the database supports correlated subqueries.
    private static boolean subqueriesSupported;
    // True if the database supports scroll-insensitive results.
    private static boolean scrollResultsSupported;
    // True if the database supports batch updates.
    private static boolean batchUpdatesSupported;
    /** True if the database supports the Statement.setFetchSize()) method. */
    static boolean pstmt_fetchSizeSupported = true;


    private static DatabaseType databaseType = DatabaseType.unknown;

    private static SchemaManager schemaManager = new SchemaManager();

	
	private DbConnectionManager(){
		// to do nothing
	}
	
	public final static Connection getConnection() {
		if(connectionProvider == null){
			synchronized (providerLock) {
				//dataSourceProxy = PooledDataSourceProxy.getInstance();
				if (connectionProvider == null) {
					String className = "com.borya.database.DefaultConnectionProvider";
					try {
						// Attempt to load the class.
						Class<?> conClass = loadClass(className);
						setConnectionProvider((ConnectionProvider)conClass.newInstance());
					}
					catch (Exception e) {
						log.warn("Failed to create the " +
								"connection provider specified by connection" +
								"Provider.className. Using the default pool.", e);
						setConnectionProvider(new DefaultConnectionProvider());
					}
				} 
			}
		}
		
		// TODO: May want to make these settings configurable
        Integer retryCnt = 0;
        Integer retryMax = 10;
        Integer retryWait = 250; // milliseconds
        Connection con = null;
        SQLException lastException = null;
        do {
            try {
            	con = connectionProvider.getConnection();
                if(con == null){
                	log.warn("DB connection is null.");
                }else{
                	// Got one, lets hand it off.
                	// Usually profiling is not enabled. So we return a normal 
                	// connection unless profiling is enabled. If yes, wrap the
                	// connection with a profiled connection.
                	if (!profilingEnabled) {
                		return con;
                	}
                	
                	// XXX setting databases
                	//return new ProfiledConnection(con);
                	return connectionProvider.getConnection();
                }
            } catch (SQLException e) {
            	// TODO distinguish recoverable from non-recoverable exceptions.
            	lastException = e;
            	log.info("Unable to get a connection from the database pool " +
            			"(attempt "+retryCnt+" out of "+retryMax+").", e);
			}
            try {
                Thread.sleep(retryWait);
            }
            catch (Exception e) {
                // Ignored
            }
            retryCnt++;
        } while (retryCnt <= retryMax);
        
        log.fatal("DB connection exception.",lastException);
        return null;
	}

	/**
	 * Closes a database connection (returning the connection to the connection
	 * pool). Any statements associated with the connection should be closed
	 * before calling this method. This method should be called within the
	 * finally section of your database logic, as in the following example:
	 * <p/>
	 * 
	 * <pre>
	 * Connection con = null;
	 * try {
	 *     con = ConnectionManager.getConnection();
	 *     ....
	 * }
	 * catch (SQLException sqle) {
	 *     Log.error(sqle.getMessage(), sqle);
	 * }
	 * finally {
	 *     DbConnectionManager.closeConnection(con);
	 * }
	 * </pre>
	 * 
	 * @param con
	 *            the connection.
	 */
	public static void close(Connection con) {
		if (con != null) {
			try {
				con.close();
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
	}

	/**
	 * Closes a result set, statement and database connection (returning the
	 * connection to the connection pool). This method should be called within
	 * the finally section of your database logic, as in the following example:
	 * 
	 * <pre>
	 * Connection con = null;
	 * PrepatedStatment pstmt = null;
	 * ResultSet rs = null;
	 * try {
	 *     con = ConnectionManager.getConnection();
	 *     pstmt = con.prepareStatement("select * from blah");
	 *     rs = psmt.executeQuery();
	 *     ....
	 * }
	 * catch (SQLException sqle) {
	 *     Log.error(sqle.getMessage(), sqle);
	 * }
	 * finally {
	 *     ConnectionManager.closeConnection(rs, pstmt, con);
	 * }
	 * </pre>
	 * 
	 * @param rs
	 *            the result set.
	 * @param stmt
	 *            the statement.
	 * @param con
	 *            the connection.
	 */
	public static void close(ResultSet rs, Statement stmt,
			Connection con) {
		close(rs);
		close(stmt);
		close(con);
	}

	/**
	 * Closes a statement and database connection (returning the connection to
	 * the connection pool). This method should be called within the finally
	 * section of your database logic, as in the following example:
	 * <p/>
	 * 
	 * <pre>
	 * Connection con = null;
	 * PrepatedStatment pstmt = null;
	 * try {
	 *     con = ConnectionManager.getConnection();
	 *     pstmt = con.prepareStatement("select * from blah");
	 *     ....
	 * }
	 * catch (SQLException sqle) {
	 *     Log.error(sqle.getMessage(), sqle);
	 * }
	 * finally {
	 *     DbConnectionManager.closeConnection(pstmt, con);
	 * }
	 * </pre>
	 * 
	 * @param stmt
	 *            the statement.
	 * @param con
	 *            the connection.
	 */
	public static void close(Statement stmt, Connection con) {
		close(stmt);
		close(con);
	}

	/**
	 * Closes a result set. This method should be called within the finally
	 * section of your database logic, as in the following example:
	 * 
	 * <pre>
	 *  public void doSomething(Connection con) {
	 *      ResultSet rs = null;
	 *      PreparedStatement pstmt = null;
	 *      try {
	 *          pstmt = con.prepareStatement("select * from blah");
	 *          rs = pstmt.executeQuery();
	 *          ....
	 *      }
	 *      catch (SQLException sqle) {
	 *          Log.error(sqle.getMessage(), sqle);
	 *      }
	 *      finally {
	 *          ConnectionManager.closeResultSet(rs);
	 *          ConnectionManager.closePreparedStatement(pstmt);
	 *      }
	 * }
	 * </pre>
	 * 
	 * @param rs
	 *            the result set to close.
	 */
	public static void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				log.error(e.getMessage(), e);
			}
		}
	}

	/**
	 * Closes a statement and a result set. This method should be called within
	 * the finally section of your database logic, as in the following example:
	 * 
	 * <pre>
	 *  public void doSomething(Connection con) {
	 *      PreparedStatement pstmt = null;
	 *      ResultSet rs = null;
	 *      try {
	 *          pstmt = con.prepareStatement("select * from blah");
	 *          rs = ...
	 *          ....
	 *      }
	 *      catch (SQLException sqle) {
	 *          Log.error(sqle.getMessage(), sqle);
	 *      }
	 *      finally {
	 *          ConnectionManager.closeStatement(rs, pstmt);
	 *      }
	 * }
	 * </pre>
	 * 
	 * @param stmt
	 *            the statement.
	 */
	public static void close(ResultSet rs, Statement stmt) {
		close(rs);
		close(stmt);
	}

	/**
	 * Closes a statement. This method should be called within the finally
	 * section of your database logic, as in the following example:
	 * 
	 * <pre>
	 *  public void doSomething(Connection con) {
	 *      PreparedStatement pstmt = null;
	 *      try {
	 *          pstmt = con.prepareStatement("select * from blah");
	 *          ....
	 *      }
	 *      catch (SQLException sqle) {
	 *          Log.error(sqle.getMessage(), sqle);
	 *      }
	 *      finally {
	 *          ConnectionManager.closeStatement(pstmt);
	 *      }
	 * }
	 * </pre>
	 * 
	 * @param stmt
	 *            the statement.
	 */
	public static void close(Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
	}
	
	public static boolean execute(String sql){
		Connection con = getConnection();
		PreparedStatement preStatement= null;
		try{
			preStatement = con.prepareStatement(sql);
			return preStatement.execute();
		}catch(Exception ex){
			log.error(ex.getMessage(),ex);
			return false;
		}finally{
			close(preStatement, con);
		}
	}
	
	private static Class<?> loadClass(String className) throws ClassNotFoundException {
        Class<?> theClass = null;
        try {
            theClass = Class.forName(className);
        }
        catch (ClassNotFoundException e1) {
            try {
                theClass = Thread.currentThread().getContextClassLoader().loadClass(className);
            }
            catch (ClassNotFoundException e2) {
                theClass = DbConnectionManager.class.getClassLoader().loadClass(className);
            }
        }
        return theClass;
    }
	
	/**
     * Sets the connection provider. The old provider (if it exists) is shut
     * down before the new one is started. A connection provider <b>should
     * not</b> be started before being passed to the connection manager
     * because the manager will call the start() method automatically.
     *
     * @param provider the ConnectionProvider that the manager should obtain
     *                 connections from.
     */
    public static void setConnectionProvider(ConnectionProvider provider) {
        synchronized (providerLock) {
            if (connectionProvider != null) {
                connectionProvider.destroy();
                connectionProvider = null;
            }
            connectionProvider = provider;
            connectionProvider.start();
            // Now, get a connection to determine meta data.
            Connection con = null;
            try {
                con = connectionProvider.getConnection();
                setMetaData(con);

                // Check to see if the database schema needs to be upgraded.
                schemaManager.checkSchema(con);
            }
            catch (Exception e) {
                log.error(e.getMessage(), e);
            }
            finally {
                close(con);
            }
        }
        
        // Remember what connection provider we want to use for restarts.
    }
    
    /**
     * Uses a connection from the database to set meta data information about
     * what different JDBC drivers and databases support.
     *
     * @param con the connection.
     * @throws SQLException if an SQL exception occurs.
     */
    private static void setMetaData(Connection con) throws SQLException {
        DatabaseMetaData metaData = con.getMetaData();
        // Supports transactions?
        transactionsSupported = metaData.supportsTransactions();
        // Supports subqueries?
        subqueriesSupported = metaData.supportsCorrelatedSubqueries();
        // Supports scroll insensitive result sets? Try/catch block is a
        // workaround for DB2 JDBC driver, which throws an exception on
        // the method call.
        try {
            scrollResultsSupported = metaData.supportsResultSetType(
                    ResultSet.TYPE_SCROLL_INSENSITIVE);
        }
        catch (Exception e) {
            scrollResultsSupported = false;
        }
        // Supports batch updates
        batchUpdatesSupported = metaData.supportsBatchUpdates();

        // Set defaults for other meta properties
        streamTextRequired = false;
        maxRowsSupported = true;
        fetchSizeSupported = true;

        // Get the database name so that we can perform meta data settings.
        String dbName = metaData.getDatabaseProductName().toLowerCase();
        String driverName = metaData.getDriverName().toLowerCase();

        // Oracle properties.
        if (dbName.indexOf("oracle") != -1) {
            databaseType = DatabaseType.oracle;
            streamTextRequired = true;
            scrollResultsSupported = false; /* TODO comment and test this, it should be supported since 10g */
            // The i-net AUGURO JDBC driver
            if (driverName.indexOf("auguro") != -1) {
                streamTextRequired = false;
                fetchSizeSupported = true;
                maxRowsSupported = false;
            }
        }
        // Postgres properties
        else if (dbName.indexOf("postgres") != -1) {
            databaseType = DatabaseType.postgresql;
            // Postgres blows, so disable scrolling result sets.
            scrollResultsSupported = false;
            fetchSizeSupported = false;
        }
        // Interbase properties
        else if (dbName.indexOf("interbase") != -1) {
            databaseType = DatabaseType.interbase;
            fetchSizeSupported = false;
            maxRowsSupported = false;
        }
        // SQLServer
        else if (dbName.indexOf("sql server") != -1) {
            databaseType = DatabaseType.sqlserver;
            // JDBC driver i-net UNA properties
            if (driverName.indexOf("una") != -1) {
                fetchSizeSupported = true;
                maxRowsSupported = false;
            }
        }
        // MySQL properties
        else if (dbName.indexOf("mysql") != -1) {
            databaseType = DatabaseType.mysql;
            transactionsSupported = false; /* TODO comment and test this, it should be supported since 5.0 */
        }
        // HSQL properties
        else if (dbName.indexOf("hsql") != -1) {
            databaseType = DatabaseType.hsqldb;
            // scrollResultsSupported = false; /* comment and test this, it should be supported since 1.7.2 */
        }
        // DB2 properties.
        else if (dbName.indexOf("db2") != 1) {
            databaseType = DatabaseType.db2;
        }
    }
    
    public static DatabaseType getDatabaseType() {
        return databaseType;
    }
    /**
     * Returns a SchemaManager instance, which can be used to manage the database
     * schema information for Openfire and plugins.
     *
     * @return a SchemaManager instance.
     */
    public static SchemaManager getSchemaManager() {
        return schemaManager;
    }

    
    /**
     * A class that identifies the type of the database that Jive is connected
     * to. In most cases, we don't want to make any database specific calls
     * and have no need to know the type of database we're using. However,
     * there are certain cases where it's critical to know the database for
     * performance reasons.
     */
    public static enum DatabaseType {

        oracle,

        postgresql,

        mysql,

        hsqldb,

        db2,

        sqlserver,

        interbase,

        unknown;
    }

}
