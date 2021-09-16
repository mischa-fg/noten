/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.fuchsgroup.rueckmeldung;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author misch
 */
public class SingletonEntityManager {
    private static  SingletonEntityManager instance = new SingletonEntityManager();
    private EntityManager em;

    private  SingletonEntityManager() {
    }
    
    public static SingletonEntityManager getInstance(){
        return instance;
    }
    
    public EntityManager verbindung(EntityManagerFactory emf){
        if(em == null || !em.isOpen()){
            em = emf.createEntityManager();
        }
        return em;
    }
    
    public void close(){
        if(em != null && em.isOpen()){
            em.close();
        }
        
    }
    
}
