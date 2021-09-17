/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.fuchsgroup.rueckmeldung;

import ch.fuchsgroup.notentool.Klasse;
import ch.fuchsgroup.notentool.Kursleiter;
import ch.fuchsgroup.notentool.Module;
import ch.fuchsgroup.notentool.Teilnehmer;
import static java.lang.System.err;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.validation.ConstraintViolationException;

/**
 *
 * @author misch
 */
public class EntityManagerRueckmeldung {

    private EntityManagerFactory entityManagerFactory;
    private SingletonEntityManager sem = SingletonEntityManager.getInstance();

    protected void setUp() throws Exception {
        // like discussed with regards to SessionFactory, an EntityManagerFactory is set up once for an application
        // 		IMPORTANT: notice how the name here matches the name we gave the persistence-unit in persistence.xml!
        if(entityManagerFactory == null){
             entityManagerFactory = Persistence.createEntityManagerFactory("notentool.jpa");
        }
       
    }

    protected void tearDown() throws Exception {
        entityManagerFactory.close();
    }

    public Kursleiter getLeiter(String name) {
        Kursleiter l = null;
        try {
            //System.out.println(entityManagerFactory.isOpen());
            setUp();
            //System.out.println(entityManagerFactory.isOpen());
            EntityManager entityManager = sem.verbindung(entityManagerFactory);
            //EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            List<Kursleiter> k = entityManager.createQuery("SELECT k FROM Kursleiter k", Kursleiter.class).getResultList();
            for (Kursleiter kl : k) {
                //String nameLeiter = kl.getVorname() + " " + kl.getName();
                //System.out.println(nameLeiter + " " + name);
                if (name.contains(kl.getName()) && name.contains(kl.getVorname())) {
                    l = kl;
                    entityManager.getTransaction().commit();
                    return l;
                }
            }
            entityManager.getTransaction().commit();
            //entityManager.close();
        } catch (Exception ex) {
            Logger.getLogger(EntityManagerRueckmeldung.class.getName()).log(Level.SEVERE, null, ex);
        }
        return l;
    }

    public Teilnehmer getTeilnehmer(String name) {
        Teilnehmer l = null;
        try {
            setUp();
            //EntityManager entityManager = entityManagerFactory.createEntityManager();
            EntityManager entityManager = sem.verbindung(entityManagerFactory);
            entityManager.getTransaction().begin();
            List<Teilnehmer> tl = entityManager.createQuery("SELECT t FROM Teilnehmer t", Teilnehmer.class).getResultList();
            for (Teilnehmer t : tl) {
                //String nameLeiter = t.getVorname() + " " + t.getName();
                //System.out.println(nameLeiter + " " + name);
                if (name.contains(t.getName()) && name.contains(t.getVorname())) {
                    l = t;
                    entityManager.getTransaction().commit();
                    return l;
                }
            }
            entityManager.getTransaction().commit();
            //entityManager.close();
        } catch (Exception ex) {
            Logger.getLogger(EntityManagerRueckmeldung.class.getName()).log(Level.SEVERE, null, ex);
        }
        return l;
    }

    public Module getModul(String modul) {
        Module l = null;
        try {
            setUp();
            //EntityManager entityManager = entityManagerFactory.createEntityManager();
            EntityManager entityManager = sem.verbindung(entityManagerFactory);
            entityManager.getTransaction().begin();
            List<Module> ml = entityManager.createQuery("SELECT m FROM Module m", Module.class).getResultList();
            for (Module m : ml) {
                if (m.getBezeichnung().equals(modul)) {
                    l = m;
                    entityManager.getTransaction().commit();
                    return l;
                }
            }
            entityManager.getTransaction().commit();
            //entityManager.close();
        } catch (Exception ex) {
            Logger.getLogger(EntityManagerRueckmeldung.class.getName()).log(Level.SEVERE, null, ex);
        }
        return l;
    }

    public Klasse getKlasse(String klasse) {
        Klasse l = null;
        try {
            setUp();
            //EntityManager entityManager = entityManagerFactory.createEntityManager();
            EntityManager entityManager = sem.verbindung(entityManagerFactory);
            entityManager.getTransaction().begin();
            List<Klasse> kl = entityManager.createQuery("SELECT k FROM Klasse k", Klasse.class).getResultList();
            for (Klasse k : kl) {
                if (k.getKlassenname().equals(klasse)) {
                    l = k;
                    entityManager.getTransaction().commit();
                    return l;
                }
            }
            entityManager.getTransaction().commit();
            //entityManager.close();
        } catch (Exception ex) {
            Logger.getLogger(EntityManagerRueckmeldung.class.getName()).log(Level.SEVERE, null, ex);
        }
        return l;
    }

    public Frage getFrage(String frage) {
        Frage l = null;
        try {
            setUp();
           // EntityManager entityManager = entityManagerFactory.createEntityManager();
            EntityManager entityManager = sem.verbindung(entityManagerFactory);
            entityManager.getTransaction().begin();
            List<Frage> fl = entityManager.createQuery("SELECT f FROM Frage f", Frage.class).getResultList();
            for (Frage f : fl) {
                if (frage.contains(f.getFrage())) {
                    l = f;
                    entityManager.getTransaction().commit();
                    return l;
                }
            }
            entityManager.getTransaction().commit();
            //entityManager.close();
        } catch (Exception ex) {
            Logger.getLogger(EntityManagerRueckmeldung.class.getName()).log(Level.SEVERE, null, ex);
        }
        return l;
    }

    public boolean rueckmeldungUeberpruefen(Rueckmeldung r) {
        try {
            setUp();
            //EntityManager entityManager = entityManagerFactory.createEntityManager();
            EntityManager entityManager = sem.verbindung(entityManagerFactory);
            List<Rueckmeldung> rl = new ArrayList();
            entityManager.getTransaction().begin();
            if (r.getTeilnehmerFK() == null) {
                Query query = entityManager.createQuery("SELECT r FROM Rueckmeldung r WHERE r.klasseFK = :klasse AND r.moduleFK = :modul AND r.kursleiterFK = :leiter AND r.teilnehmerFK IS NULL AND r.datumAbgeschlossen = :datum");
                query.setParameter("klasse", r.getKlasseFK());
                query.setParameter("modul", r.getModuleFK());
                query.setParameter("leiter", r.getKursleiterFK());
                query.setParameter("datum", r.getDatumAbgeschlossen());
                rl = query.getResultList();
            } else {
                Query query = entityManager.createQuery("SELECT r FROM Rueckmeldung r WHERE r.klasseFK = :klasse AND r.moduleFK = :modul AND r.kursleiterFK = :leiter AND r.teilnehmerFK = :teilnehmer AND r.datumAbgeschlossen = :datum");
                query.setParameter("klasse", r.getKlasseFK());
                query.setParameter("modul", r.getModuleFK());
                query.setParameter("leiter", r.getKursleiterFK());
                query.setParameter("teilnehmer", r.getTeilnehmerFK());
                query.setParameter("datum", r.getDatumAbgeschlossen());
                rl = query.getResultList();
            }

            if (rl.size() > 0) {
                entityManager.getTransaction().commit();
                return false;
            }
            entityManager.getTransaction().commit();
            //entityManager.close();
        } catch (Exception ex) {
            Logger.getLogger(EntityManagerRueckmeldung.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    public Rueckmeldung rueckmeldungSpeichern(Rueckmeldung r) {
        try {
            setUp();
            //EntityManager entityManager = entityManagerFactory.createEntityManager();
            EntityManager entityManager = sem.verbindung(entityManagerFactory);
            entityManager.getTransaction().begin();
            Rueckmeldung r1 = entityManager.merge(r);
            entityManager.persist(r1);
            entityManager.getTransaction().commit();
            //entityManager.close();
            return r1;
        } catch (Exception ex) {
            Logger.getLogger(EntityManagerRueckmeldung.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void rueckmeldung2frageSpeichern(Rueckmeldung2frage r) {
        try {
            setUp();
            //EntityManager entityManager = entityManagerFactory.createEntityManager();
            EntityManager entityManager = sem.verbindung(entityManagerFactory);
            entityManager.getTransaction().begin();
            Rueckmeldung2frage k1 = entityManager.merge(r);
            entityManager.persist(k1);
            entityManager.getTransaction().commit();
            //entityManager.close();
        } catch (Exception ex) {
            Logger.getLogger(EntityManagerRueckmeldung.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
