package nl.hu.v1wac.thirdapp.persistence;

import javax.naming.*;
import javax.sql.*;
import java.sql.*;

public class BaseDAO {
	
	protected final Connection getConnection(){
		try
        {
            Context ctx = new InitialContext();
            DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/PostgresDS");
            Connection conn = ds.getConnection();
            return conn;
        }catch(Exception e){
        	System.out.println(e);
        	return null;
        }
	}
	
}
