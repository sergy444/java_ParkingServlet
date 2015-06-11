package com.zubiri.jsp.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zubiri.parking.Coche;
import com.zubiri.parking.ParkingVehiculos;

/**
 * Servlet implementation class listacoche
 */
public class listacoche extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public listacoche() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		response.setContentType( "text/html; charset=iso-8859-1" );
		PrintWriter out = response.getWriter();
		
		//ArrayList<Coche>lista = Insertarcoche.getListaCoches();

		//accedemos a la base de datos
		Connection conexion=null;

		try{
	    	//registramos el JDBC Driver
		    Class.forName("com.mysql.jdbc.Driver");

		    //Abrimos la conexion
		    System.out.println("Conectando con la base de datos...");
		    conexion = DriverManager.getConnection("jdbc:mysql://localhost/parking", "root","enplan-87");
		    System.out.println("Conexion establecida...");
		}
		catch(Exception e){
			out.println("Error:" + e);
		}

		
		out.print("<html>");
		out.print("<head><title></title>");
		out.print("</head>");
		out.print("<body>");
		out.println("<table align='center' width='40%' border='10' >  ");
		
		out.println("<td> Matricula del coche </td>");
		out.println("<td> Numero de Ruedas </td>");
		out.println("<td> Motor </td>");
		out.println("<td> Marca </td>");
		out.println("<td> Automatico </td>");
		out.println("<td> Consumo 100km </td>");
		out.println("</tr>");
		/*for(int i=0; i< lista.size(); i++){
			Coche coche = lista.get(i);*/

		try
	    {
	    				    
		    //Realizamos la consulta
			Statement st = conexion.createStatement();
		
			ResultSet rs = st.executeQuery("SELECT * FROM COCHE"); 
			while (rs.next()){
				out.println("<tr>");
				out.println("<td>" + rs.getObject("matricula") + "</td>");
				out.println("<td>" + rs.getObject("numRuedas") + "</td>");
				out.println("<td>" + rs.getObject("motor") + "</td>");
				out.println("<td>" + rs.getObject("marca") + "</td>");
				out.println("<td>" + rs.getObject("automatico") + "</td>");
				out.println("<td>" + rs.getObject("consumo100km") + "</td>");
				out.println("</tr>");
			}

			rs.close();
		}

		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	
		
		
		out.println("</table");

		// Cerramos la conexion a la base de datos. 
		try{
			conexion.close();
		}
		catch(Exception e){
			out.println("Error: " + e);
		}

		//boton para volver
		//<input type="button" value="VOLVER" onClick="location.href='index.html'" />;

		out.print("</body>");
		out.print("</html>");
	}
}
		


