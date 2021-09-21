/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.fuchsgroup.rueckmeldung;

import ch.fuchsgroup.rueckmeldung.viewmodal.KlasseLehrerJahr;
import ch.fuchsgroup.rueckmeldung.viewmodal.KlasseViewModal;
import ch.fuchsgroup.notentool.FileLesen;
import ch.fuchsgroup.notentool.Klasse;
import ch.fuchsgroup.rueckmeldung.viewmodal.KritikLernende;
import ch.fuchsgroup.rueckmeldung.viewmodal.KursleiterViewModal;
import ch.fuchsgroup.rueckmeldung.viewmodal.WerteKreisStatistik;
import ch.fuchsgroup.rueckmeldung.viewmodal.LehrerKlasse;
import ch.fuchsgroup.rueckmeldung.viewmodal.LehrerModul;
import ch.fuchsgroup.rueckmeldung.viewmodal.ModuleViewModal;
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
        List<KlasseLehrerJahr> kjl = ems.getKlasseJahrStimmung(klasse, jahr);
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
    //Lehrer Übersicht mit den Klassen
    
    @GET
    @Path("dozenten")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDozente() {
        EntityManagerStatistiken ems = new EntityManagerStatistiken();
        List<KursleiterViewModal> l = ems.getLeherer();
        return Response.status(Response.Status.OK).entity(l).build();
    }
    @GET
    @Path("lehrerKlasse")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLehrerKlasse(@QueryParam("dozent") int lehrer) {
        EntityManagerStatistiken ems = new EntityManagerStatistiken();
        List<LehrerKlasse> l = ems.getKlassenLehrer(lehrer);
        return Response.status(Response.Status.OK).entity(l).build();
    }
    
    //Lehrer Übersicht, was kann er besser machen
    @GET
    @Path("lehrerModule")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLehrerModule(@QueryParam("dozent") int lehrer) {
        EntityManagerStatistiken ems = new EntityManagerStatistiken();
        List<ModuleViewModal> l = ems.getLehrerModule(lehrer);
        return Response.status(Response.Status.OK).entity(l).build();
    }
    @GET
    @Path("lehrerModuleWertung")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLehrerModuleWertung(@QueryParam("dozent") int lehrer, @QueryParam("modul") int modul) {
        EntityManagerStatistiken ems = new EntityManagerStatistiken();
        List<LehrerModul> l = ems.getLehrerModulVerbesserung(lehrer, modul);
        return Response.status(Response.Status.OK).entity(l).build();
    }
    
    @GET
    @Path("kritikLernende")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getKriktikLernende(@QueryParam("dozent") int lehrer, @QueryParam("modul") int modul) {
        EntityManagerStatistiken ems = new EntityManagerStatistiken();
        List<KritikLernende> l = ems.getKritikLernende(lehrer, modul);
        return Response.status(Response.Status.OK).entity(l).build();
    }
    
    @GET
    @Path("learningView")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLearningView() {
        EntityManagerStatistiken ems = new EntityManagerStatistiken();
        List<WerteKreisStatistik> l = ems.getAlleLvStats();
        return Response.status(Response.Status.OK).entity(l).build();
    }
    //Leher Jahr
    @GET
    @Path("lehrerJahr")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLehrerJahr(@QueryParam("dozent") int dozent) {
        EntityManagerStatistiken ems = new EntityManagerStatistiken();
        List<Integer> l = ems.getLehrerJahr(dozent);
        return Response.status(Response.Status.OK).entity(l).build();
    }
    @GET
    @Path("lehrerJahrUebersicht")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLehrerJahrUebersicht(@QueryParam("dozent") int dozent, @QueryParam("jahr") int jahr) {
        /*Statistiken s = new Statistiken();
        s.getKlassenUebersicht(klasse, jahr);*/
        EntityManagerStatistiken ems = new EntityManagerStatistiken();
        List<KlasseLehrerJahr> kjl = ems.getLehrerJahrLeistung(dozent, jahr);
        return Response.status(Response.Status.OK).entity(kjl).build();
    }
    //Kursqualität
    @GET
    @Path("lehrerKlassen")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLehrerKlassen(@QueryParam("dozent") int dozent) {
        EntityManagerStatistiken ems = new EntityManagerStatistiken();
        List<KlasseViewModal> kvml = ems.getLehrerKlassen(dozent);
        return Response.status(Response.Status.OK).entity(kvml).build();
    }
    @GET
    @Path("lehrerKlassenModule")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLehrerKlassenModule(@QueryParam("dozent") int dozent, @QueryParam("klasse") int kid) {
        EntityManagerStatistiken ems = new EntityManagerStatistiken();
        List<ModuleViewModal> mvml = ems.getLehrerKlassenModule(dozent, kid);
        return Response.status(Response.Status.OK).entity(mvml).build();
    }
    @GET
    @Path("lehrerKlasseModuleStats")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLehrerKlassenModuleStats(@QueryParam("dozent") int dozent, @QueryParam("klasse") int kid, @QueryParam("modul") int mid) {
        EntityManagerStatistiken ems = new EntityManagerStatistiken();
        List<WerteKreisStatistik> mvml = ems.getAlleLKMStats(dozent, kid, mid);
        return Response.status(Response.Status.OK).entity(mvml).build();
    }
    
}
