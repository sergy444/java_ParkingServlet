package com.zubiri.jsp.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.sql.*;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zubiri.parking.Coche;

/**
 * Servlet implementation class Insertarcoche
 */
public class Insertarcoche extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
    
	//private static ArrayList<Coche> listaCoches = new ArrayList<Coche>();
    
    /*public static ArrayList<Coche> getListaCoches() {
		return listaCoches;
	}
	*/
	/*public void setListaCoches(ArrayList<Coche> listaCoches) {
		Insertarcoche.listaCoches = listaCoches;
	}
	*/
	/**
     * @see HttpServlet#HttpServlet()
     */
    public Insertarcoche() {
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
		//recogemos los valores del formulario en sus respectivas variables y
		String matricula = request.getParameter("matricula");
		
		String numRuedas2 = request.getParameter("numRuedas");
		int numRuedas = Integer.parseInt(numRuedas2);
		String motor2 = request.getParameter("motor");
		boolean motor = Boolean.parseBoolean(motor2);
		String marca = request.getParameter("marca");
		String automatico2 = request.getParameter("automatico");
		boolean automatico = Boolean.parseBoolean(automatico2);
		String consumo100km2 = request.getParameter("consumo100km");
		int consumo100km = Integer.parseInt(consumo100km2);
		
		//Coche coche = new Coche(matricula, numRuedas, motor, marca, automatico, consumo100km);

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
	    
	    catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//si no existe creamos la tabla COCHE
	    try{
	    	
	    	Statement st = conexion.createStatement();
	    	st.executeUpdate("CREATE TABLE IF NOT EXISTS COCHE " +
	                   "(matricula VARCHAR(255) not NULL, " +
	                   " numruedas INTEGER, " + 
	                   " motor boolean, " + 
	                   " marca VARCHAR(255), " +
	                   " automatico boolean, " + 
	                   " consumo100km INTEGER, " + 
	                   " PRIMARY KEY ( matricula ))");
	    	System.out.println("Tabla COCHE creada");
	    
	    }
	    catch(Exception e){
	        out.println("error: " + e);
	    }
		
		//si la matricula es incorreta mostramos un mensaje
		if( matricula == null){
			
			out.print("<html>");
			out.print("<head><title></title>");
			out.print("</head>");
			out.print("<body>");
			out.print("no has metido bien la matricula: la matricula son CUATRO NUMEROS+TRES LETRAS");
			out.print("</body>");
			out.print("</html>");
			
		} 
		else {
			
			 //insertamos los datos del formulario en la BD
		    try{
		    	Statement st = conexion.createStatement();	
		    	st.executeUpdate("INSERT INTO COCHE (matricula,numruedas,motor,marca,automatico,consumo100km) "
	                      + "VALUES ('"+matricula+"','"+numRuedas+"','"+motor+"','"+marca+"','"+automatico+"','"+consumo100km+"')");    
			}

			catch(Exception e){
				out.println("Error: " + e);
			}
		    
		    //Diseño de la tabal
			out.print("<html>");
			out.print("<head><title></title>");
			out.print("</head>");
			out.print("<body>");
			out.println("<table align='center' width='40%' border='10' >  ");
			out.println("</tr>");
			out.println("<td> Matricula del coche </td>");
			out.println("<td> Numero de Ruedas </td>");
			out.println("<td> Motor </td>");
			out.println("<td> Marca </td>");
			out.println("<td> Automatico </td>");
			out.println("<td> Consumo 100km </td>");
			out.println("</tr>");
			/*for(int i=0; i< listaCoches.size(); i++){
				coche = listaCoches.get(i); 
				// metodo no puede ser estatico.
			*/

			try{
		    				    
			    //Realizamos la consulta
				Statement st = conexion.createStatement();
			
				ResultSet rs = st.executeQuery("SELECT * FROM coche"); 
				//mientras haya registros en la BD se muestran en la tabla
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
		}
		//boton para volver
		
		//out.print("<input type="button" value="VOLVER" onClick="location.href='index.html'">");
		
		out.print("</body>");
		out.print("</html>");
		
		// Cerramos la conexion a la base de datos. 
		try{
			conexion.close();
		}
		catch(Exception e){
			out.println("Error: " + e);
		}
	}
}