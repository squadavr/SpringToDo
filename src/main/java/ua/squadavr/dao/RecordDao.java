package ua.squadavr.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.squadavr.config.ApplicationContextProvider;
import ua.squadavr.entity.Record;
import ua.squadavr.entity.RecordStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class RecordDao {
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    public RecordDao(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }



    public List<Record> findAllRecords() {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            Query query = entityManager.createQuery("FROM Record");
            return query.getResultList();
        }
    }

//    public List<Record> findActiveRecords(){
//        List<Record> result = records.stream()
//                .filter(record -> record.getStatus() == RecordStatus.ACTIVE).toList();
//        return new ArrayList<>(result);
//    }
//    public List<Record> findDoneRecords(){
//        List<Record> result = records.stream()
//                .filter(record -> record.getStatus() == RecordStatus.DONE).toList();
//        return new ArrayList<>(result);
//    }


    public void saveRecord(Record record) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(record);

            entityManager.getTransaction().commit();
        } catch (Exception e){
            System.out.println("Exception caught, executing rollback");
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        } finally {
            entityManager.close();
        }
    }

    public void updateRecordStatus(int id, RecordStatus newStatus) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Record foundRecord = entityManager.find(Record.class, id);
            if (foundRecord != null) {
                foundRecord.setStatus(newStatus);
                entityManager.merge(foundRecord);
            }
            entityManager.getTransaction().commit();
        } catch (Exception e){
            System.out.println("Exception caught, executing rollback");
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        } finally {
            entityManager.close();
        }
    }

    public void deleteRecord(int id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Record foundRecord = entityManager.find(Record.class, id);
            if (foundRecord != null) {
                entityManager.remove(foundRecord);
            }
            entityManager.getTransaction().commit();
        } catch (Exception e){
            System.out.println("Exception caught, executing rollback");
            e.printStackTrace();
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        } finally {
            entityManager.close();
        }
    }
}

/*
public void templateWithTransactions(Record record){
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    try{
        entityManager.getTransaction().begin();

        entityManager.getTransaction().commit();
    } catch (Exception e){
        System.out.println("Exception caught, executing rollback");
        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().rollback();
        }
    } finally {
        entityManager.close();
    }
}
 */