/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.fuchsgroup.excel;

import ch.fuchsgroup.notentool.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author misch
 */
public class EnitiyManagerExcel {

    private EntityManagerFactory entityManagerFactory;

    protected void setUp() throws Exception {
        // like discussed with regards to SessionFactory, an EntityManagerFactory is set up once for an application
        // 		IMPORTANT: notice how the name here matches the name we gave the persistence-unit in persistence.xml!
        entityManagerFactory = Persistence.createEntityManagerFactory("notentool.jpa");
    }

    protected void tearDown() throws Exception {
        entityManagerFactory.close();
    }

    public List<TeilnehmerViewModal> getAlleTeilnehmer() {
        try {
            setUp();
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            List<Teilnehmer> t = entityManager.createQuery("SELECT t FROM Teilnehmer t", Teilnehmer.class).getResultList();
            List<TeilnehmerViewModal> tvml = new ArrayList();
            for (Teilnehmer te : t) {
                TeilnehmerViewModal tvm = new TeilnehmerViewModal();
                tvm.setName(te.getName());
                tvm.setVorname(te.getVorname());
                tvm.setId(te.getId());
                tvml.add(tvm);
            }
            entityManager.getTransaction().commit();
            entityManager.close();
            return tvml;
        } catch (Exception ex) {
            Logger.getLogger(EnitiyManagerExcel.class.getName()).log(Level.SEVERE, null, ex);
            //return false;

        }
        //return true;
        return null;
    }

    //
    public List<TeilnehmerNotenViewModal> getTeilnehmerNoten() {
        List<TeilnehmerViewModal> tvm = getAlleTeilnehmer();
        List<TeilnehmerNotenViewModal> tnvml = new ArrayList();
        for (TeilnehmerViewModal t : tvm) {
            //Select on note where note.teilnehmerFK == t.id
            List<Note> note = getNotenTeilnehmer(t.getId());
            for (Note n : note) {
                Pruefung p = getPruefung(n.getPruefungFK().getId());
                Schulfach s = getSchulfach(p.getSchulfachFK().getId());
                TeilnehmerNotenViewModal tnvm = new TeilnehmerNotenViewModal();
                tnvm.setFachname(s.getFachname());
                tnvm.setName(t.getName());
                tnvm.setNote(n.getNote());
                tnvm.setTitelPruefung(p.getTitel());
                tnvm.setVorname(t.getVorname());
                tnvml.add(tnvm);
            }
        }
        return tnvml;
    }

    private List<Note> getNotenTeilnehmer(int idT) {
        try {
            setUp();
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            Query q = entityManager.createQuery("SELECT n FROM Note n WHERE n.teilnehmerFK.id =:id");
            q.setParameter("id", idT);
            List<Note> n = q.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return n;
        } catch (Exception ex) {
            Logger.getLogger(EnitiyManagerExcel.class.getName()).log(Level.SEVERE, null, ex);
            //return false;

        }
        return null;
    }

    private Pruefung getPruefung(int idN) {
        try {
            setUp();
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            Query q = entityManager.createQuery("SELECT p FROM Pruefung p WHERE p.id =:id");
            q.setParameter("id", idN);
            Pruefung p = (Pruefung) q.getSingleResult();
            entityManager.getTransaction().commit();
            entityManager.close();
            return p;
        } catch (Exception ex) {
            Logger.getLogger(EnitiyManagerExcel.class.getName()).log(Level.SEVERE, null, ex);
            //return false;

        }
        return null;
    }

    private Schulfach getSchulfach(int idP) {
        try {
            setUp();
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            Query q = entityManager.createQuery("SELECT s FROM Schulfach s WHERE s.id =:id");
            q.setParameter("id", idP);
            Schulfach s = (Schulfach) q.getSingleResult();
            entityManager.getTransaction().commit();
            entityManager.close();
            return s;
        } catch (Exception ex) {
            Logger.getLogger(EnitiyManagerExcel.class.getName()).log(Level.SEVERE, null, ex);
            //return false;

        }
        return null;
    }

    public boolean usernameFrei(Keyuser k) {
        try {
            setUp();
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            List<Keyuser> kl = entityManager.createQuery("SELECT k FROM Keyuser k", Keyuser.class).getResultList();
            if (kl.size() > 0) {
                for (Keyuser ku : kl) {
                    if (ku.getUsername().equals(k.getUsername())) {
                        return false;
                    }
                }
            } else {
                return true;
            }
            entityManager.getTransaction().commit();
            entityManager.close();
            return true;
        } catch (Exception ex) {
            Logger.getLogger(EnitiyManagerExcel.class.getName()).log(Level.SEVERE, null, ex);
            //return false;

        }
        //return true;
        return false;
    }

    public boolean keyUserHinzufuegen(Keyuser k) {
        try {
            setUp();
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            Keyuser k1 = entityManager.merge(k);
            entityManager.persist(k1);
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (Exception ex) {
            Logger.getLogger(EnitiyManagerExcel.class.getName()).log(Level.SEVERE, null, ex);
            return false;

        }
        return true;
    }

    public boolean userLogin(User u) {
        try {
            setUp();
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            List<Keyuser> kl = entityManager.createQuery("SELECT k FROM Keyuser k", Keyuser.class).getResultList();
            for (Keyuser k : kl) {
                if (k.getUsername().equals(u.getUsername()) && BCrypt.checkpw(u.getPasswort(), k.getPasswort())) {
                    return true;
                }
            }
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (Exception ex) {
            Logger.getLogger(EnitiyManagerExcel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean ueberpruefeSchluessel(String schluessel, String uname) {
        try {
            setUp();
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
           List<Keyuser> kl = entityManager.createQuery("SELECT k FROM Keyuser k", Keyuser.class).getResultList();
           for(Keyuser k : kl){
               if (BCrypt.checkpw(schluessel, k.getApikey()) && k.getUsername().equals(uname)) {
                    return true;
                }
           }
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (Exception ex) {
            Logger.getLogger(EnitiyManagerExcel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    //Ungenügende Noten
    public List<TeilnehmerNotenViewModal> getTeilnehmerUngNoten() {
        List<TeilnehmerViewModal> tvm = getAlleTeilnehmer();
        List<TeilnehmerNotenViewModal> tnvml = new ArrayList();
        for (TeilnehmerViewModal t : tvm) {
            //Select on note where note.teilnehmerFK == t.id
            List<Note> note = getNotenUngTeilnehmer(t.getId());
            for (Note n : note) {
                Pruefung p = getPruefung(n.getPruefungFK().getId());
                Schulfach s = getSchulfach(p.getSchulfachFK().getId());
                TeilnehmerNotenViewModal tnvm = new TeilnehmerNotenViewModal();
                tnvm.setFachname(s.getFachname());
                tnvm.setName(t.getName());
                tnvm.setNote(n.getNote());
                tnvm.setTitelPruefung(p.getTitel());
                tnvm.setVorname(t.getVorname());
                tnvml.add(tnvm);
                //System.out.println(p.getTitel() + " "+ n.getNote() + " " +t.getVorname() + " " + s.getFachname());
            }
            //Select on fach
            //String fachname;
            //String pruefung;
        }
        return tnvml;
    }

    private List<Note> getNotenUngTeilnehmer(int idT) {
        try {
            setUp();
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            Query q = entityManager.createQuery("SELECT n FROM Note n WHERE n.teilnehmerFK.id =:id AND n.note<4");
            q.setParameter("id", idT);
            List<Note> n = q.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return n;
        } catch (Exception ex) {
            Logger.getLogger(EnitiyManagerExcel.class.getName()).log(Level.SEVERE, null, ex);
            //return false;

        }
        return null;
    }

    public List<TeilnehmerNotenViewModal> fehlendeNoten() {
        List<TeilnehmerViewModal> tvm = getAlleTeilnehmer();
        List<TeilnehmerNotenViewModal> tnvml = new ArrayList();
        for (TeilnehmerViewModal t : tvm) {
            //Select on note where note.teilnehmerFK == t.id
            List<Note> note = getNotenTeilnehmer(t.getId());
            List<Pruefung> pruefungen = allePruefungen();
            for (Pruefung p : pruefungen) {
                boolean pruefungGeschrieben = false;
                for (Note n : note) {
                    if (n.getPruefungFK().getId() == p.getId()) {
                        pruefungGeschrieben = true;
                        //break;
                    }
                }
                if(!pruefungGeschrieben){
                    Schulfach s = getSchulfach(p.getSchulfachFK().getId());
                    TeilnehmerNotenViewModal tnvm = new TeilnehmerNotenViewModal();
                    tnvm.setFachname(s.getFachname());
                    tnvm.setName(t.getName());
                    tnvm.setTitelPruefung(p.getTitel());
                    tnvm.setVorname(t.getVorname());
                    tnvml.add(tnvm);
                }
            }

        }
        if(tnvml.size() < 1){
            return null;
        }
        return tnvml;
    }

    private List<Pruefung> allePruefungen() {
        try {
            setUp();
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            List<Pruefung> pl = entityManager.createQuery("SELECT p FROM Pruefung p", Pruefung.class).getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return pl;
        } catch (Exception ex) {
            Logger.getLogger(EnitiyManagerExcel.class.getName()).log(Level.SEVERE, null, ex);
            //return false;

        }
        //return true;
        return null;
    }
}
