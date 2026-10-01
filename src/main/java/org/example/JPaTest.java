package org.example;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

public class JPaTest {

    public void performJPA() {

        // Creating objects representing BuddyInfos
        BuddyInfo buddy1 = new BuddyInfo();
        buddy1.setId(1);
        buddy1.setName("Nicky Fang");
        buddy1.setAddress("613-555-0101");

        BuddyInfo buddy2 = new BuddyInfo();
        buddy2.setId(2);
        buddy2.setName("Nicky Fang 2");
        buddy2.setAddress("613-555-0202");

        // Creating an address book and adding the buddies
        AddressBook addressBook = new AddressBook();
        addressBook.setId(1);
        addressBook.addBuddy(buddy1);
        addressBook.addBuddy(buddy2);

        // Connecting to the database through EntityManagerFactory
        // connection details loaded from persistence.xml
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("addressbook-buddyinfo");

        EntityManager em = emf.createEntityManager();

        // Creating a new transaction
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        // Persisting the buddy entity objects and address book
        em.persist(buddy1);
        em.persist(buddy2);
        em.persist(addressBook);

        tx.commit();

        // Querying the contents of the database using JPQL query
        Query q = em.createQuery("SELECT b FROM BuddyInfo b");

        @SuppressWarnings("unchecked")
        List<BuddyInfo> results = q.getResultList();

        System.out.println("List of buddies\n----------------");

        for (BuddyInfo b : results) {

            System.out.println(b.getName() + " (id=" + b.getId() + ")");
        }

        // Addrtessbook queries.
        Query addressBookQuery = em.createQuery("SELECT a FROM AddressBook a");

        @SuppressWarnings("unchecked")
        List<AddressBook> addressBookResults = addressBookQuery.getResultList();

        System.out.println("\nAddress books\n-------------");

        for (AddressBook a : addressBookResults) {

            System.out.println("AddressBook (id=" + a.getId() + ") contains:");

            for (BuddyInfo b : a.getBuddies()) {

                System.out.println("  " + b.getName() + " (id=" + b.getId() + ")");
            }
        }

        // Closing connection
        em.close();

        emf.close();
    }

    public static void main(String[] args) {

        new JPaTest().performJPA();
    }
}