package com.borya.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract interface Callable {

	Object exec(Connection conn,PreparedStatement pst,ResultSet rs)throws SQLException;

}