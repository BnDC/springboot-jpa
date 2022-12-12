package com.kdt.jpa.repository;

import com.kdt.jpa.entity.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository repository;

    @Autowired
    EntityManagerFactory emf;

    @Test
    void createCustomer_test() {
        Customer customer = new Customer("fn4", "ln4");
        repository.createCustomer(customer);
        Customer findCustomer = repository.findById(customer.getId());
        assertThat(customer, samePropertyValuesAs(findCustomer));
    }

    @Test
    void updateCustomer_test() {

        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Customer customer = new Customer("fn1", "l1");
        entityManager.persist(customer);
        transaction.commit();

        repository.updateCustomer(customer);
        Customer updatedCustomer = repository.findById(customer.getId());
        assertThat(customer, samePropertyValuesAs(updatedCustomer));
    }

    @Test
    void findById_test() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        Customer customer = new Customer("fn1", "l1");
        entityManager.persist(customer);
        transaction.commit();

        Customer findcustomer = repository.findById(customer.getId());
        assertThat(customer, samePropertyValuesAs(findcustomer));
    }

    @Test
    void delete_test() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        Customer customer = new Customer("fn1", "l1");
        entityManager.persist(customer);
        transaction.commit();

        repository.delete(customer);

        List<Customer> all = repository.findAll();
        assertThat(all.size(), is(0));
    }

    @Test
    void findAll_test() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        Customer customer1 = new Customer("fn1", "l1");
        entityManager.persist(customer1);

        Customer customer2 = new Customer("fn2", "ln2");
        entityManager.persist(customer2);

        Customer customer3 = new Customer("fn3", "ln3");
        entityManager.persist(customer3);
        transaction.commit();

        List<Customer> customerList = repository.findAll();
        assertThat(customerList.size(), is(3));
    }
}