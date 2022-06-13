/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

/**
 *
 * @author jorgedelgado
 */
@WebServlet(urlPatterns = {"/API"})
public class API extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet API</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet API at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
        
        //Metodo POST
        
        //CORS (Cross Origin Resource Sharing)
        
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Methods", "GET,HEAD,OPTIONS,POST,PUT");
        response.setHeader("Access-Control-Allow-Headers", "Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");

        ConnectionDataBase conx = new ConnectionDataBase(
            ConnectionDataBase.TipoBaseDatos.Postgresql, 
            "localhost", 
            "postgres", 
            "12345", 
            "Taller", 
            "5432",
            ConnectionDataBase.AutoCommit.False
        );
        
        try(PrintWriter out = response.getWriter()){
            
            JSONObject msjs = new JSONObject();
            
            try{
                
                String jsonBody = new BufferedReader(new InputStreamReader(request.getInputStream())).lines().collect(Collectors.joining("\n"));
                
                if(jsonBody == null || jsonBody.trim().length() == 0)
                    throw new Exception("El JSON esta vacio");
                
                JSONObject jObj = new JSONObject(jsonBody);
                
                String Nombre = jObj.get("nombre").toString();
                String Apellido = jObj.get("apellido").toString();
                String Email = jObj.get("email").toString();
                String Passw = jObj.get("passw").toString();
                String Direccion = jObj.get("direccion").toString();
                String Ciudad = jObj.get("ciudad").toString();
                String Estado = jObj.get("estado").toString();
                String CP = jObj.get("codigoPostal").toString();
                
                conx.OpenConnection();
                
                if(conx.getConexion()){
                    
                    String query = String.format(
                            "INSERT INTO TCLIENTES (nombre, apellido, email, passw, direccion, ciudad, estado, cp) " + 
                            "values ('%s','%s','%s','%s','%s','%s','%s','%s')", 
                            Nombre, Apellido, Email, Passw, Direccion, Ciudad, Estado, CP);
                    
                    String res = conx.executeQuery(query);
                    
                    if(!res.equals("1"))
                        throw new Exception(res);
                    
                    conx.Commit();
                    
                    msjs.put("status", 200);
                    msjs.put("msj", "Se creó correctamente!!!");
                    out.println(msjs.toString());
                }
                else
                    throw new Exception("La base de datos no se abrio...");
                
                
                conx.CloseConnection();
                
            }catch(Exception ex){
                
                if(conx.getConexion())
                    conx.Rollback();
                
                msjs.put("status", 400);
                msjs.put("msj", ex.getMessage());
                out.println(msjs.toString());
                
            }
            
        }catch(Exception ex){
            
        }
        
        
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
