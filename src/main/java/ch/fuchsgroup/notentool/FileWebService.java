/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.fuchsgroup.notentool;

import com.sun.jersey.multipart.FormDataParam;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author misch
 */
@Path("/file")
public class FileWebService {

    @POST
    @Path("readFile")
    @Produces(MediaType.TEXT_HTML)
    public String readFile(String file) throws IOException {
        /*System.out.println(file.getName());
        String[] a = file.list();
        System.out.println(a.length);*/
        FileLesen f = new FileLesen();
        String s = f.leseFile(file);
        return s;
    }

    @GET
    @Path("bildAktualisieren")
    @Produces(MediaType.TEXT_HTML)
    public String bildAktualisieren() {
        BilderLesen b = new BilderLesen();
        b.leseBilder();
        return "Ok";
    }

    @POST
    @Path("readFileFormData")
    @Produces(MediaType.TEXT_HTML)
    @Consumes("multipart/form-data")
    public String readFileFormData(@FormDataParam("test") File file) throws IOException {
        System.out.println(file.getName());
        System.out.println(file.isFile());
        System.out.println(Paths.get(file.getAbsolutePath()));
        byte[] text = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
        //äöü
        String srr = new String(text);
        FileLesen f = new FileLesen();
        String s = f.leseFile(srr);
        return s;
    }
    @GET
    @Path("helloWorld")
    @Produces(MediaType.TEXT_HTML)
    public String HelloWorld() {
        //BilderLesen b = new BilderLesen();
        //b.leseBilder();
        return "Ok";
    }
}
