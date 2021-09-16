/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.fuchsgroup.excel;

import ch.fuchsgroup.notentool.Teilnehmer;
import com.google.gson.*;
import com.sun.jersey.multipart.FormDataParam;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author misch
 */
@Path("/excel")
public class ExcelWebService {
    @Context private HttpServletRequest request;
    
    @GET
    @Path("teilnehmer")
    @Produces(MediaType.APPLICATION_JSON)
    public List<TeilnehmerViewModal> alleTeilnehmer() {
        EnitiyManagerExcel em = new EnitiyManagerExcel();
        List<TeilnehmerViewModal> t= em.getAlleTeilnehmer();
        System.out.println(t.size());
        return t;
    }
    
    @GET
    @Path("test")
    @Produces(MediaType.TEXT_HTML)
    public String test(@QueryParam("uname") String uname, @QueryParam("key") String key) {
        return uname + " " + " " + key;
    }
    @GET
    @Path("teilnehmerNoten")
    @Produces(MediaType.APPLICATION_JSON)
    public Response alleTeilnehmerNoten(@QueryParam("uname") String uname, @QueryParam("key") String key) {
        System.out.println(key);
        EnitiyManagerExcel em = new EnitiyManagerExcel();
        boolean ok = em.ueberpruefeSchluessel(key,uname);
        if(ok){
            List<TeilnehmerNotenViewModal> t= em.getTeilnehmerNoten();
            return Response.status(Response.Status.OK).entity(t).build();
        }
        //ich System.out.println(t.size());
        return Response.status(Response.Status.NOT_FOUND).build();
    }
    
    @GET
    @Path("teilnehmerUngNoten")
    @Produces(MediaType.APPLICATION_JSON)
    public Response alleTeilnehmerUngNoten(@QueryParam("uname") String uname, @QueryParam("key") String key) {
        System.out.println(key);
        EnitiyManagerExcel em = new EnitiyManagerExcel();
        boolean ok = em.ueberpruefeSchluessel(key, uname);
        if(ok){
            List<TeilnehmerNotenViewModal> t= em.getTeilnehmerUngNoten();
            return Response.status(Response.Status.OK).entity(t).build();
        }
        //ich System.out.println(t.size());
        return Response.status(Response.Status.NOT_FOUND).build();
    }
    @GET
    @Path("fehlendeNoten")
    @Produces(MediaType.APPLICATION_JSON)
    public Response alleFehlendeUngNoten(@QueryParam("uname") String uname, @QueryParam("key") String key) {
        System.out.println(key);
        EnitiyManagerExcel em = new EnitiyManagerExcel();
        boolean ok = em.ueberpruefeSchluessel(key,uname);
        if(ok){
            List<TeilnehmerNotenViewModal> t= em.fehlendeNoten();
            return Response.status(Response.Status.OK).entity(t).build();
        }
        //ich System.out.println(t.size());
        return Response.status(Response.Status.NOT_FOUND).build();
    }
    
    @POST
    @Path("testPost")
    @Produces(MediaType.TEXT_HTML)
    public String testPost() {
        //http://localhost:8080/Notentool/api/excel/helloWorld?id=1
        return "Ok";
    }
    
    @POST
    @Path("registrierung")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registrieren(User u){
        //System.out.println(u.getUsername());
        LoginRegi l = new LoginRegi();
        String ok = l.registrieren(u);
        if(ok!=null){
            System.out.println(ok);
            return Response.status(200).entity(ok).build();
        }
        
        return Response.status(200).entity("").build();
    }
    
    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(User u){
        //System.out.println(u.getUsername());
        LoginRegi l = new LoginRegi();
        String k = l.login(u);
        request.getSession().setAttribute("Username", k);
        //String uname = (String) request.getSession().getAttribute("Username");
        //System.out.println(uname);
        if(k!=null){
            return Response.status(200).build();
        }else{
            return Response.status(404).build();
        }
        
       // return Response.status(200).entity(u.getUsername()).build();
    }
    
    @GET
    @Path("username")
    @Produces(MediaType.APPLICATION_JSON)
    public String Username() {
        String uname = (String) request.getSession().getAttribute("Username");
        System.out.println(uname);
        return uname;
    }
    
   
}
