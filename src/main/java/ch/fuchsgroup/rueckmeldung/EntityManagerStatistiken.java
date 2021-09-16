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
import java.math.BigDecimal;
import java.util.ArrayList;
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
 * @author misch
 */
public class EntityManagerStatistiken {

    private EntityManagerFactory entityManagerFactory;

    protected void setUp() throws Exception {
        // like discussed with regards to SessionFactory, an EntityManagerFactory is set up once for an application
        // 		IMPORTANT: notice how the name here matches the name we gave the persistence-unit in persistence.xml!
        entityManagerFactory = Persistence.createEntityManagerFactory("notentool.jpa");
    }

    protected void tearDown() throws Exception {
        entityManagerFactory.close();
    }

    public List<KlasseViewModal> getKlassen() {
        try {
            setUp();
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            List<Klasse> k = entityManager.createQuery("SELECT DISTINCT r.klasseFK FROM Rueckmeldung r", Klasse.class).getResultList();
            if (k.size() > 0) {
                List<KlasseViewModal> kvml = new ArrayList();
                for (Klasse k1 : k) {
                    KlasseViewModal kvm = new KlasseViewModal();
                    kvm.setId(k1.getId());
                    kvm.setKlassenname(k1.getKlassenname());
                    kvml.add(kvm);
                }
                return kvml;
            }
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (Exception ex) {
            Logger.getLogger(EntityManagerRueckmeldung.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<Integer> getKlassenJahr(int kid) {
        try {
            setUp();
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            Query q = entityManager.createNativeQuery("SELECT year(datumabgeschlossen) from rueckmeldung where klasse_FK = ? group by year(datumabgeschlossen)");
            q.setParameter(1, kid);
            List<Object[]> jahre = q.getResultList();
            //System.out.println(jahre.size());
            List<Integer> ausgabe = new ArrayList();
            for(Object y : jahre){
                ausgabe.add((Integer) y);
            }
            entityManager.getTransaction().commit();
            entityManager.close();
            return ausgabe;
        } catch (Exception ex) {
            Logger.getLogger(EntityManagerRueckmeldung.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public List<KlasseJahr> getKlasseJahrStimmung(int kid, int jahr){
        try {
            setUp();
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            Query q = entityManager.createNativeQuery("SELECT month(r.datumabgeschlossen), avg(rf.antwortZahl) from rueckmeldung r join rueckmeldung2frage rf on r.id = rf.Rueckmeldung_FK where rf.antwortZahl is not null and year(r.datumabgeschlossen) = ? and r.Klasse_FK = ? group by month(r.datumabgeschlossen)");
            q.setParameter(1, jahr);
            q.setParameter(2, kid);
            List<Object[]> kj = q.getResultList();
            List<KlasseJahr> kjl = new ArrayList();
            for(Object[] j : kj){
                KlasseJahr k = new KlasseJahr();
                k.setMonat((int) j[0]);
                k.setDurchschnitt((BigDecimal) j[1]);
                kjl.add(k);
                //System.out.println(j[0] + " " + j[1]);
            }
            entityManager.getTransaction().commit();
            entityManager.close();
           return kjl;
        } catch (Exception ex) {
            Logger.getLogger(EntityManagerRueckmeldung.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void getKlassenUebersicht(Date jahrStart, Date jahrEnde, int k) {
        List<Frage> fl = getAlleFragen(k);
        System.out.println(fl.size() + " frage Anzahl");
        Frage f = fl.get(0);
        //Funktioniert nicht!!
        System.out.println(f.getFrage());
        for (int i = 10; i >= 0; i++) {
            System.out.println(i + " : " + getAnzahlAntworten(f, jahrStart, jahrEnde, k, i));
        }
        /*List<Rueckmeldung> r = getAlleRueckmelungenKlasse(jahrStart, jahrEnde, k);
        System.out.println(r.size());*/

    }

    public List<Frage> getAlleFragen(int k) {
        try {
            setUp();
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            Query q = entityManager.createQuery("SELECT f FROM Frage f where  f.multiple = :true");
            q.setParameter("true", true);
            List<Frage> f = q.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return f;
        } catch (Exception ex) {
            Logger.getLogger(EntityManagerRueckmeldung.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<Rueckmeldung> getAlleRueckmeldungenKlasse(Date jahrStart, Date jahrEnde, int k) {
        try {
            setUp();
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            Query q = entityManager.createQuery("SELECT r FROM Rueckmeldung r where r.klasseFK.id = :klasse AND r.datumAbgeschlossen BETWEEN :start and :ende");
            q.setParameter("klasse", k);
            q.setParameter("start", jahrStart);
            q.setParameter("ende", jahrEnde);
            List<Rueckmeldung> r = q.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return r;
        } catch (Exception ex) {
            Logger.getLogger(EntityManagerRueckmeldung.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public int getAnzahlAntworten(Frage f, Date jahrStart, Date jahrEnde, int k, int zahl) {
        try {
            setUp();
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            Query q = entityManager.createQuery("SELECT SUM(r.antwortZahl) FROM Rueckmeldung2frage r where r.antwortZahl = :zahl and r.rueckmeldungFK.klasseFK.id = :klasse AND r.rueckmeldungFK.datumAbgeschlossen BETWEEN :start and :ende AND r.frageFK.id = :frageid AND r.antwortZahl IS NOT NULL GROUP BY r.antwortZahl");
            q.setParameter("klasse", k);
            q.setParameter("start", jahrStart);
            q.setParameter("ende", jahrEnde);
            q.setParameter("zahl", zahl);
            q.setParameter("frageid", f.getId());
            int r = (int) q.getSingleResult();
            entityManager.getTransaction().commit();
            entityManager.close();
            return r;
        } catch (Exception ex) {
            Logger.getLogger(EntityManagerRueckmeldung.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public void getResultate(Frage f, Date jahrStart, Date jahrEnde, int k) {

    }

}