<%@ page import="java.sql.*"%>
<%
	String uname = request.getParameter("username");
	String pswd = request.getParameter("password");
	try
	{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection conn = DriverManager.getConnection(""jdbc:mysql://localhost:3306/manoj", "test", "12345");
		Statement stmt = conn.createStatement();
		String query = "Select * from USERS where USERNAME=\'"+uname+"\' and PASSWORD=\'"+pswd+"\'";
		ResultSet rs = stmt.executeQuery(query);
		if (rs.next())
			response.sendRedirect("q3_welcome.html");
		else
			out.println("Incorrect username or password");
		stmt.close();
		conn.close();
	}
	catch(Exception E)
	{
		out.println(E);
	}
%>