/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.fuchsgroup.rueckmeldung;

import ch.fuchsgroup.rueckmeldung.viewmodal.KlasseLehrerJahr;
import ch.fuchsgroup.rueckmeldung.viewmodal.KlasseViewModal;
import ch.fuchsgroup.rueckmeldung.Model.Frage;
import ch.fuchsgroup.rueckmeldung.Model.Rueckmeldung;
import ch.fuchsgroup.notentool.Klasse;
import ch.fuchsgroup.notentool.Kursleiter;
import ch.fuchsgroup.notentool.Module;
import ch.fuchsgroup.notentool.Teilnehmer;
import ch.fuchsgroup.rueckmeldung.viewmodal.KritikLernende;
import ch.fuchsgroup.rueckmeldung.viewmodal.KursleiterViewModal;
import ch.fuchsgroup.rueckmeldung.viewmodal.WerteKreisStatistik;
import ch.fuchsgroup.rueckmeldung.viewmodal.LehrerKlasse;
import ch.fuchsgroup.rueckmeldung.viewmodal.LehrerModul;
import ch.fuchsgroup.rueckmeldung.viewmodal.ModuleViewModal;
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

    //Klassen Stimung
    public List<KlasseViewModal> getKlassen() {
        try {
            setUp();
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            List<Klasse> k = entityManager.createQuery("SELECT DISTINCT r.klasseFK FROM Rueckmeldung r ORDER BY r.klasseFK.klassenname", Klasse.class).getResultList();
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
            for (Object y : jahre) {
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

    public List<KlasseLehrerJahr> getKlasseJahrStimmung(int kid, int jahr) {
        try {
            setUp();
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            Query q = entityManager.createNativeQuery("SELECT month(r.datumabgeschlossen), avg(rf.antwortZahl) from rueckmeldung r join rueckmeldung2frage rf on r.id = rf.Rueckmeldung_FK where rf.antwortZahl is not null and year(r.datumabgeschlossen) = ? and r.Klasse_FK = ? and rf.antwortZahl != ? group by month(r.datumabgeschlossen)");
            q.setParameter(1, jahr);
            q.setParameter(2, kid);
            q.setParameter(3, 0);
            List<Object[]> kj = q.getResultList();
            List<KlasseLehrerJahr> kjl = new ArrayList();
            for (Object[] j : kj) {
                KlasseLehrerJahr k = new KlasseLehrerJahr();
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

    //Lehrer bei Klasse angekommen
    public List<KursleiterViewModal> getLeherer() {
        try {
            setUp();
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            List<Kursleiter> k = entityManager.createQuery("SELECT DISTINCT r.kursleiterFK FROM Rueckmeldung r", Kursleiter.class).getResultList();
            if (k.size() > 0) {
                List<KursleiterViewModal> kvml = new ArrayList();
                for (Kursleiter k1 : k) {
                    KursleiterViewModal kvm = new KursleiterViewModal();
                    kvm.setId(k1.getId());
                    kvm.setName(k1.getName());
                    kvm.setVorname(k1.getVorname());
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

    public List<LehrerKlasse> getKlassenLehrer(int idLehrer) {
        try {
            setUp();
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            Query q = entityManager.createNativeQuery("Select k.klassenname, avg(rf.antwortZahl) from rueckmeldung r join klasse k on k.id = r.klasse_fk join  rueckmeldung2frage rf on r.id = rf.Rueckmeldung_FK join frage f on f.id = rf.Frage_FK where rf.antwortZahl is not null and f.Frage like ? and r.Kursleiter_FK = ? group by r.klasse_fk ORDER BY k.klassenname;");
            q.setParameter(1, "%Dozent%");
            q.setParameter(2, idLehrer);

            List<Object[]> result = q.getResultList();
            List<LehrerKlasse> lkl = new ArrayList();
            for (Object[] o : result) {
                LehrerKlasse l = new LehrerKlasse();
                l.setKlassenname((String) o[0]);
                l.setDurchschnitt((BigDecimal) o[1]);
                lkl.add(l);
            }
            entityManager.getTransaction().commit();
            entityManager.close();
            return lkl;
        } catch (Exception ex) {
            Logger.getLogger(EntityManagerRueckmeldung.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    //Lehrer was kann er besser machen?
    public List<ModuleViewModal> getLehrerModule(int lid) {
        try {
            setUp();
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            Query q = entityManager.createQuery("SELECT DISTINCT r.moduleFK FROM Rueckmeldung r where r.kursleiterFK.id = :id order by r.moduleFK.id", Module.class);
            q.setParameter("id", lid);
            List<Module> result = q.getResultList();
            List<ModuleViewModal> mvml = new ArrayList();
            for (Module m : result) {
                ModuleViewModal mvm = new ModuleViewModal();
                mvm.setBeschreibung(m.getBeschreibung());
                mvm.setBezeichnung(m.getBezeichnung());
                mvm.setModulnummer(m.getModulnummer());
                mvm.setId(m.getId());
                mvml.add(mvm);
            }
            entityManager.getTransaction().commit();
            entityManager.close();
            return mvml;
        } catch (Exception ex) {
            Logger.getLogger(EntityManagerStatistiken.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<LehrerModul> getLehrerModulVerbesserung(int lid, int mid) {
        try {
            setUp();
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            Query q = entityManager.createNativeQuery("Select f.frage, avg(rf.antwortZahl) from frage f join rueckmeldung2frage rf on f.id = rf.frage_fk join rueckmeldung r on r.id = rf.rueckmeldung_fk where rf.antwortZahl is not null and f.Frage like ? and r.kursleiter_fk = ? and r.module_fk = ?  group by rf.frage_fk;");
            q.setParameter(1, "%Dozent%");
            q.setParameter(2, lid);
            q.setParameter(3, mid);
            List<Object[]> result = q.getResultList();
            List<LehrerModul> lml = new ArrayList();
            for (Object[] o : result) {
                BigDecimal durch = (BigDecimal) o[1];
                if (durch.doubleValue() <= 7.0) {
                    LehrerModul lm = new LehrerModul();
                    lm.setFrage((String) o[0]);
                    lm.setDurchschnitt(durch);
                    lml.add(lm);
                }
            }
            entityManager.getTransaction().commit();
            entityManager.close();
            return lml;
        } catch (Exception ex) {
            Logger.getLogger(EntityManagerRueckmeldung.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    //Kritik Lehrer
    public List<KritikLernende> getKritikLernende(int lid, int mid) {
        try {
            setUp();
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            Query q = entityManager.createNativeQuery("Select r.teilnehmer_fk,f.frage, rf.antwortText from frage f join rueckmeldung2frage rf on f.id = rf.frage_fk join rueckmeldung r on r.id = rf.rueckmeldung_fk where rf.antwortText is not null and r.kursleiter_fk = ? and r.module_fk = ? order by f.frage;");
            q.setParameter(1, lid);
            q.setParameter(2, mid);
            List<Object[]> result = q.getResultList();
            List<KritikLernende> kll = new ArrayList();
            for (Object[] o : result) {
                String antwort = (String) o[2];
                if (antwort.length() > 3) {
                    if (o[0] == null) {
                        KritikLernende kl = new KritikLernende();
                        kl.setVorname("Anonym");
                        kl.setNachname("");
                        kl.setFrage((String) o[1]);
                        kl.setAntwort(antwort);
                        kll.add(kl);
                    } else {
                        System.out.println(o[0]);
                        Query ql = entityManager.createNativeQuery("Select t.vorname, t.name from teilnehmer t where t.id = ?;");
                        ql.setParameter(1, o[0]);
                        Object[] lernende = (Object[]) ql.getSingleResult();
                        KritikLernende kl = new KritikLernende();
                        kl.setVorname((String) lernende[0]);
                        kl.setNachname((String) lernende[1]);
                        kl.setFrage((String) o[1]);
                        kl.setAntwort(antwort);
                        kll.add(kl);
                    }
                }
            }
            entityManager.getTransaction().commit();
            entityManager.close();
            return kll;
        } catch (Exception ex) {
            Logger.getLogger(EntityManagerRueckmeldung.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    //LearningViewStatistiken
    public List<WerteKreisStatistik> getAlleLvStats() {
        List<WerteKreisStatistik> lvs = new ArrayList();
        //LearningViewStatistiken mit der Fragen id
        lvs.add(getLvStats(8));
        //lvs.add(getLvStats(9));
        lvs.add(getLvStats(10));
        return lvs;
    }

    public WerteKreisStatistik getLvStats(int idf) {
        try {
            setUp();
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            //Frage1
            Query q = entityManager.createNativeQuery("Select f.frage, rf.antwortZahl, count(rf.antwortZahl) from frage f join rueckmeldung2frage rf on f.id = rf.frage_fk where rf.frage_fk = ? and rf.antwortZahl is not null and rf.antwortZahl > ? group by rf.antwortZahl;");
            q.setParameter(1, idf);
            q.setParameter(2, 0);
            WerteKreisStatistik lvs = new WerteKreisStatistik();
            List<Object[]> resultFrage1 = q.getResultList();
            List<Integer> wert = new ArrayList();
            List<Long> wertAnzahl = new ArrayList();
            for (Object[] o : resultFrage1) {
                lvs.setFrage((String) o[0]);
                wert.add((Integer) o[1]);
                wertAnzahl.add((Long) o[2]);
            }
            lvs.setWert(wert);
            lvs.setWertAnzahl(wertAnzahl);
            entityManager.getTransaction().commit();
            entityManager.close();
            return lvs;
        } catch (Exception ex) {
            Logger.getLogger(EntityManagerRueckmeldung.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    //LehrerJahr
    public List<Integer> getLehrerJahr(int lid) {
        try {
            setUp();
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            Query q = entityManager.createNativeQuery("SELECT year(datumabgeschlossen) from rueckmeldung where kursleiter_FK = ? group by year(datumabgeschlossen)");
            q.setParameter(1, lid);
            List<Object[]> jahre = q.getResultList();
            //System.out.println(jahre.size());
            List<Integer> ausgabe = new ArrayList();
            for (Object y : jahre) {
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

    public List<KlasseLehrerJahr> getLehrerJahrLeistung(int did, int jahr) {
        try {
            setUp();
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            Query q = entityManager.createNativeQuery("Select month(r.datumabgeschlossen), avg(rf.antwortZahl) from rueckmeldung r join rueckmeldung2frage rf on r.id = rf.Rueckmeldung_FK join frage f on f.id = rf.frage_fk where year(r.datumabgeschlossen) = ? and r.kursleiter_FK = ? and rf.antwortZahl is not null and f.Frage like ? group by month(r.datumabgeschlossen);");
            q.setParameter(1, jahr);
            q.setParameter(2, did);
            q.setParameter(3, "%Dozent%");
            List<Object[]> kj = q.getResultList();
            List<KlasseLehrerJahr> kjl = new ArrayList();
            for (Object[] j : kj) {
                KlasseLehrerJahr k = new KlasseLehrerJahr();
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

    //Kursqualität
    public List<KlasseViewModal> getLehrerKlassen(int did) {
        try {
            setUp();
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            Query q = entityManager.createNativeQuery("Select DISTINCT k.id, k.klassenname from klasse k join rueckmeldung r on k.id = r.klasse_FK where r.kursleiter_FK = ?;");
            q.setParameter(1, did);
            List<Object[]> result = q.getResultList();
            List<KlasseViewModal> kvml = new ArrayList();
            for (Object[] o : result) {
                KlasseViewModal kvm = new KlasseViewModal();
                int s = (int) o[0];
                kvm.setId(s);
                kvm.setKlassenname((String) o[1]);
                kvml.add(kvm);
            }
            entityManager.getTransaction().commit();
            entityManager.close();
            return kvml;
        } catch (Exception ex) {
            Logger.getLogger(EntityManagerRueckmeldung.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<ModuleViewModal> getLehrerKlassenModule(int did, int kid) {
        try {
            setUp();
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            Query q = entityManager.createNativeQuery("Select DISTINCT m.id, m.bezeichnung from module m join rueckmeldung r on m.id = r.module_fk where r.kursleiter_FK = ? AND r.klasse_FK = ?;");
            q.setParameter(1, did);
            q.setParameter(2, kid);
            List<Object[]> result = q.getResultList();
            List<ModuleViewModal> mvml = new ArrayList();
            for (Object[] o : result) {
                ModuleViewModal mvm = new ModuleViewModal();
                mvm.setId((int) o[0]);
                mvm.setBezeichnung((String) o[1]);
                mvml.add(mvm);
            }
            entityManager.getTransaction().commit();
            entityManager.close();
            return mvml;
        } catch (Exception ex) {
            Logger.getLogger(EntityManagerRueckmeldung.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<WerteKreisStatistik> getAlleLKMStats(int did, int kid, int mid) {
        List<WerteKreisStatistik> lvs = new ArrayList();
        List<Frage> fl = getMultiFragen();
        //List füllen mit den Antworten der Fragen
        for(Frage f : fl){
            lvs.add(getLKMStats(did,kid,mid,f.getId()));
        }
        return lvs;
    }

    public WerteKreisStatistik getLKMStats(int did, int kid, int mid, int idf) {
        try {
            setUp();
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            //Frage1
            Query q = entityManager.createNativeQuery("Select f.frage, rf.antwortZahl, count(rf.antwortZahl) from frage f join rueckmeldung2frage rf on f.id = rf.frage_fk join rueckmeldung r on rf.rueckmeldung_fk = r.id where rf.antwortZahl is not null and r.kursleiter_FK = ? and r.klasse_FK = ? and r.module_fk = ? and rf.frage_fk = ? group by rf.antwortZahl;");
            q.setParameter(1, did);
            q.setParameter(2, kid);
            q.setParameter(3, mid);
            q.setParameter(4, idf);
            WerteKreisStatistik lvs = new WerteKreisStatistik();
            List<Object[]> resultFrage1 = q.getResultList();
            List<Integer> wert = new ArrayList();
            List<Long> wertAnzahl = new ArrayList();
            for (Object[] o : resultFrage1) {
                lvs.setFrage((String) o[0]);
                wert.add((Integer) o[1]);
                wertAnzahl.add((Long) o[2]);
            }
            lvs.setWert(wert);
            lvs.setWertAnzahl(wertAnzahl);
            entityManager.getTransaction().commit();
            entityManager.close();
            return lvs;
        } catch (Exception ex) {
            Logger.getLogger(EntityManagerRueckmeldung.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    //Alle Fragen
     public List<Frage> getMultiFragen() {
        try {
            setUp();
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            Query q = entityManager.createQuery("SELECT f FROM Frage f where f.multiple = 1 and f.id != 9 and f.frage not like :problem", Frage.class);
            q.setParameter("problem", "%LearningView%");
            List<Frage> f = q.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return f;
        } catch (Exception ex) {
            Logger.getLogger(EntityManagerRueckmeldung.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
