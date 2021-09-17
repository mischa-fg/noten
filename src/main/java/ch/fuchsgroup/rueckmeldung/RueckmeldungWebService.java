/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.fuchsgroup.rueckmeldung;

import ch.fuchsgroup.rueckmeldung.viewmodal.KlasseJahr;
import ch.fuchsgroup.rueckmeldung.viewmodal.KlasseViewModal;
import ch.fuchsgroup.notentool.FileLesen;
import ch.fuchsgroup.notentool.Klasse;
import com.sun.jersey.multipart.FormDataParam;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author misch
 */
@Path("/rueckmeldung")
public class RueckmeldungWebService {
    @GET
    @Path("test")
    @Produces(MediaType.TEXT_HTML)
    public String test() {
        return "Hallo Welt";
    }
    @POST
    @Path("readRueckmeldung")
    @Produces(MediaType.TEXT_HTML)
    @Consumes("multipart/form-data")
    public String readRueckmeldung(@FormDataParam("rueckmeldung") File file) throws IOException {
        byte[] text = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
        //äöü
        String str = new String(text, "UTF-8");
        //System.out.println(str);
        RueckmeldungBearbeiten rb = new RueckmeldungBearbeiten();
        String output = rb.rueckmeldungLesen(str);
        return output;
    }
    
    //Statistiken
    @GET
    @Path("klassen")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getKlassen() {
        EntityManagerStatistiken ems = new EntityManagerStatistiken();
        List<KlasseViewModal> k = ems.getKlassen();
        if(k!=null){
            return Response.status(Response.Status.OK).entity(k).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
    
    @GET
    @Path("klassenUebersicht")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getKlassenUebersicht(@QueryParam("klasse") int klasse, @QueryParam("jahr") int jahr) {
        /*Statistiken s = new Statistiken();
        s.getKlassenUebersicht(klasse, jahr);*/
        EntityManagerStatistiken ems = new EntityManagerStatistiken();
        List<KlasseJahr> kjl = ems.getKlasseJahrStimmung(klasse, jahr);
        return Response.status(Response.Status.OK).entity(kjl).build();
    }
    
    @GET
    @Path("klasseJahr")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getKlasseJahr(@QueryParam("klasse") int klasse) {
        EntityManagerStatistiken ems = new EntityManagerStatistiken();
        List<Integer> l = ems.getKlassenJahr(klasse);
        return Response.status(Response.Status.OK).entity(l).build();
    }
}
