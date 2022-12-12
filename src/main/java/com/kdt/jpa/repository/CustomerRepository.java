package com.kdt.jpa.repository;


import com.kdt.jpa.entity.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class CustomerRepository {

    @Autowired
    EntityManagerFactory emf;

    public void createCustomer(Customer customer) {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.persist(customer);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException();
        }
    }

    public long updateCustomer(Customer customer) {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            Customer targetCustomer = entityManager.find(Customer.class, customer.getId());
            targetCustomer.changeFirstName(customer.getFirstName());
            transaction.commit();
            return customer.getId();
        } catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException();
        }
    }

    public Customer findById(long customerId) {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            Customer customer = entityManager.find(Customer.class, customerId);
            transaction.commit();
            return customer;
        } catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException();
        }
    }

    public void delete(Customer customer) {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        Customer targetCustomer = entityManager.find(Customer.class, customer.getId());
        entityManager.remove(targetCustomer);
        transaction.commit();
    }

    public List<Customer> findAll() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        List<Customer> customerList = entityManager.createQuery("select c from Customer c", Customer.class)
                .getResultList();

        transaction.commit();

        return customerList;
    }

}
