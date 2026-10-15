package com.mycompany.hospitalmetropolitanocrud.dao;

import com.mycompany.hospitalmetropolitanocrud.modelo.CitaMedica;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

public class CitaMedicaDAO {

    // Fábrica que lee la configuración de persistence.xml
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("HospitalMetropolitanoPU");

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    // 1. CREATE: Guardar una nueva cita médica
    public void crear(CitaMedica cita) {
        try (EntityManager em = getEntityManager()) {
            em.getTransaction().begin();
            em.persist(cita);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw e;
        }
    }

    // 2. READ: Consultar todas las citas registradas
    public List<CitaMedica> listarTodas() {
        try (EntityManager em = getEntityManager()) {
            return em.createQuery("SELECT c FROM CitaMedica c ORDER BY c.id ASC", CitaMedica.class).getResultList();
        }
    }

    // 3. UPDATE: Actualizar una cita existente
    public void actualizar(CitaMedica cita) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(cita);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    // 4. DELETE: Eliminar una cita por su ID
    public void eliminar(int id) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            CitaMedica cita = em.find(CitaMedica.class, id);
            if (cita != null) {
                em.remove(cita);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }
}