/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.fuchsgroup.notentool;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author misch
 */
public class EntityManagerNotentool {

    private EntityManagerFactory entityManagerFactory;

    protected void setUp() throws Exception {
        // like discussed with regards to SessionFactory, an EntityManagerFactory is set up once for an application
        // 		IMPORTANT: notice how the name here matches the name we gave the persistence-unit in persistence.xml!
        entityManagerFactory = Persistence.createEntityManagerFactory("notentool.jpa");
    }

    protected void tearDown() throws Exception {
        entityManagerFactory.close();
    }

    public boolean teilnehmerSpeichern(Teilnehmer t) {
        try {
            setUp();
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            Teilnehmer t1 = entityManager.merge(t);
            entityManager.persist(t1);
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (Exception ex) {
            Logger.getLogger(EntityManagerNotentool.class.getName()).log(Level.SEVERE, null, ex);
            return false;

        }
        return true;
    }

    public boolean kursSpeichern(Kurse k) {
        try {
            setUp();
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            Kurse k1 = entityManager.merge(k);
            entityManager.persist(k1);
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (Exception ex) {
            Logger.getLogger(EntityManagerNotentool.class.getName()).log(Level.SEVERE, null, ex);
            return false;

        }
        return true;
    }

    public Kursleiter getLeiter(String name) {
        Kursleiter l = null;
        try {
            setUp();
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            List<Kursleiter> k = entityManager.createQuery("SELECT k FROM Kursleiter k", Kursleiter.class).getResultList();
            for (Kursleiter kl : k) {
                String nameLeiter = kl.getVorname() + " " + kl.getName();
                //System.out.println(nameLeiter + " " + name);
                if (nameLeiter.equals(name)) {
                    l = kl;
                }
            }
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (Exception ex) {
            Logger.getLogger(EntityManagerNotentool.class.getName()).log(Level.SEVERE, null, ex);
        }
        return l;
    }

    public Module getModul(String modulNummer) {
        Module moutput = null;
        try {
            setUp();
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            List<Module> m = entityManager.createQuery("SELECT m FROM Module m", Module.class).getResultList();
            for (Module ml : m) {
                if (ml.getModulnummer().equals(modulNummer)) {
                    moutput = ml;
                }
            }

            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (Exception ex) {
            Logger.getLogger(EntityManagerNotentool.class.getName()).log(Level.SEVERE, null, ex);
        }
        return moutput;
    }

    public Klasse getKlasse(String name) {
        Klasse koutput = null;
        try {
            setUp();
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            List<Klasse> k = entityManager.createQuery("SELECT k FROM Klasse k", Klasse.class).getResultList();
            for (Klasse kl : k) {
                if (kl.getKlassenname().equals(name)) {
                    koutput = kl;
                }
            }

            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (Exception ex) {
            Logger.getLogger(EntityManagerNotentool.class.getName()).log(Level.SEVERE, null, ex);
        }
        return koutput;
    }

    public Teilnehmer isTeilnehmer(Teilnehmer t) {
        Teilnehmer output = null;
        try {
            setUp();
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            Query query = entityManager.createQuery("SELECT t FROM Teilnehmer t WHERE t.name = :name AND t.vorname = :vorname");
            query.setParameter("name", t.getName());
            query.setParameter("vorname", t.getVorname());

            List<Teilnehmer> tList = query.getResultList();
            if (tList.size() >= 1) {
                output = tList.get(0);
            }

            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (Exception ex) {
            Logger.getLogger(EntityManagerNotentool.class.getName()).log(Level.SEVERE, null, ex);
        }
        return output;
    }

    public Kurse isKurs(Kurse k) {
        Kurse output = null;
        try {
            setUp();
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            Query query = entityManager.createQuery("SELECT k FROM Kurse k WHERE k.klasseFK = :klasse  AND k.moduleFK = :module");
            query.setParameter("klasse", k.getKlasseFK());
            query.setParameter("module", k.getModuleFK());
            List<Kurse> kList = query.getResultList();
            if (kList.size() >= 1) {
                output = kList.get(0);
            }

            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (Exception ex) {
            Logger.getLogger(EntityManagerNotentool.class.getName()).log(Level.SEVERE, null, ex);
        }
        return output;
    }

    public List<Teilnehmer> teilnehmerOhneBild() {
        List<Teilnehmer> output = new ArrayList();
        try {
            setUp();
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            Query query = entityManager.createQuery("SELECT t FROM Teilnehmer t WHERE t.foto IS NULL");
            output = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (Exception ex) {
            Logger.getLogger(EntityManagerNotentool.class.getName()).log(Level.SEVERE, null, ex);
        }
        return output;
    }

    //ExcelDaten
}
