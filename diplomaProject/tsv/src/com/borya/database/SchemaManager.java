package com.borya.database;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.log4j.Logger;

public class SchemaManager {

	private static Logger Log = Logger.getLogger(SchemaManager.class);

    SchemaManager() {

    }

    /**
     * Checks the Openfire database schema to ensure that it's installed and up to date.
     * If the schema isn't present or up to date, an automatic update will be attempted.
     *
     * @param con a connection to the database.
     * @return true if database schema checked out fine, or was automatically installed
     *      or updated successfully.
     */
    public boolean checkSchema(Connection con) {
    	try {
            return checkSchema(con, "PUSH", 21,
            		new ResourceLoader() {
						public InputStream loadResource(String resourceName) {
                            try {
                            	InputStream in = SchemaManager.class.getResourceAsStream(
                            			"resources" + File.separator + "database"+ resourceName);
                                return in;
                            }
                            catch (Exception e) {
                                return null;
                            }
                        }
                    });
        }catch (Exception e) {
            Log.error(e.getMessage(), e);
        }
        return false;
    }

    /**
     * Checks to see if the database needs to be upgraded. This method should be
     * called once every time the application starts up.
     *
     * @param con the database connection to use to check the schema with.
     * @param schemaKey the database schema key (name).
     * @param requiredVersion the version that the schema should be at.
     * @param resourceLoader a resource loader that knows how to load schema files.
     * @throws Exception if an error occured.
     * @return True if the schema update was successful.
     */
    private boolean checkSchema(Connection con, String schemaKey, int requiredVersion,
            ResourceLoader resourceLoader) throws Exception
    {
    	String resourceName = schemaKey + "_" +
    			DbConnectionManager.getDatabaseType() + ".sql";
    	InputStream resource = resourceLoader.loadResource(resourceName);
    	if (resource == null) {
    		return false;
    	}
    	try {
    		//executeSQLScript(con, resource, !schemaKey.equals("ems"));
    		return true;
    	}catch (Exception e) {
    		Log.error(e.getMessage(), e);
    		return false;
    	}
    	finally {
    		try {
    			resource.close();
    		}
    		catch (Exception e) {
    			// Ignore.
    		}
    	}
    }

    /**
     * Executes a SQL script.
     *
     * @param con database connection.
     * @param resource an input stream for the script to execute.
     * @param autoreplace automatically replace jiveVersion with ofVersion
     * @throws IOException if an IOException occurs.
     * @throws SQLException if an SQLException occurs.
     */
    static void executeSQLScript(Connection con, InputStream resource, boolean autoreplace) throws IOException,
            SQLException
    {
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(resource));
            boolean done = false;
            while (!done) {
                StringBuilder command = new StringBuilder();
                while (true) {
                    String line = in.readLine();
                    if (line == null) {
                        done = true;
                        break;
                    }
                    // Ignore comments and blank lines.
                    if (isSQLCommandPart(line)) {
                        command.append(" ").append(line);
                    }
                    if (line.trim().endsWith(";")) {
                        break;
                    }
                }
                // Send command to database.
                if (!done && !command.toString().equals("")) {
                    // Remove last semicolon when using Oracle or DB2 to prevent "invalid character error"
                    if (DbConnectionManager.getDatabaseType() == DbConnectionManager.DatabaseType.oracle ||
                            DbConnectionManager.getDatabaseType() == DbConnectionManager.DatabaseType.db2) {
                        command.deleteCharAt(command.length() - 1);
                    }
                    PreparedStatement pstmt = null;
                    try {
                        String cmdString = command.toString();
                        if (autoreplace)  {
                            cmdString = cmdString.replaceAll("jiveVersion", "ofVersion");
                        }
                        pstmt = con.prepareStatement(cmdString);
                        pstmt.execute();
                    }
                    catch (SQLException e) {
                        // Lets show what failed
                        Log.error("SchemaManager: Failed to execute SQL:\n"+command.toString());
                        throw e;
                    }
                    finally {
                        DbConnectionManager.close(pstmt);
                    }
                }
            }
        }
        finally {
            if (in != null) {
                try {
                    in.close();
                }
                catch (Exception e) {
                    Log.error(e.getMessage(), e);
                }
            }
        }
    }

    private static abstract class ResourceLoader {

        public abstract InputStream loadResource(String resourceName);

    }

    /**
     * Returns true if a line from a SQL schema is a valid command part.
     *
     * @param line the line of the schema.
     * @return true if a valid command part.
     */
    private static boolean isSQLCommandPart(String line) {
        line = line.trim();
        if (line.equals("")) {
            return false;
        }
        // Check to see if the line is a comment. Valid comment types:
        //   "//" is HSQLDB
        //   "--" is DB2 and Postgres
        //   "#" is MySQL
        //   "REM" is Oracle
        //   "/*" is SQLServer
        return !(line.startsWith("//") || line.startsWith("--") || line.startsWith("#") ||
                line.startsWith("REM") || line.startsWith("/*") || line.startsWith("*"));
    }
}