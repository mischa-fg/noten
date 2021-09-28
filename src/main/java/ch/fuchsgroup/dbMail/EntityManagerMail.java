/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.fuchsgroup.dbMail;

import ch.fuchsgroup.notentool.Klasse;
import ch.fuchsgroup.notentool.Kurse;
import ch.fuchsgroup.notentool.Module;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author mischa
 */
public class EntityManagerMail {
    private EntityManagerFactory entityManagerFactory;

    protected void setUp() throws Exception {
        // like discussed with regards to SessionFactory, an EntityManagerFactory is set up once for an application
        // 		IMPORTANT: notice how the name here matches the name we gave the persistence-unit in persistence.xml!
        entityManagerFactory = Persistence.createEntityManagerFactory("notentool.jpa");
    }

    protected void tearDown() throws Exception {
        entityManagerFactory.close();
    }
    
    public List<Kurse> getKurse(){
        try {
            setUp();
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            Date d = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            System.out.println(formatter.format(d));
            Query q = entityManager.createQuery("SELECT k FROM Kurse k where k.datumbis >= :date");
            q.setParameter("date",d);
            List<Kurse> kl = q.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return kl;
        } catch (Exception ex) {
            Logger.getLogger(EntityManagerMail.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public List<Klasse2teilnehmer> getTeilnehmer2klasse(Klasse k){
        try {
            setUp();
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            Query q = entityManager.createQuery("SELECT k FROM Klasse2teilnehmer k where k.klasseFK = :klasse");
            q.setParameter("klasse", k);
            List<Klasse2teilnehmer> kl = q.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return kl;
        } catch (Exception ex) {
            Logger.getLogger(EntityManagerMail.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
