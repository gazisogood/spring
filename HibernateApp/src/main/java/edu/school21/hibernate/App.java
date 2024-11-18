package edu.school21.hibernate;

import edu.school21.hibernate.model.Item;
import edu.school21.hibernate.model.Passport;
import edu.school21.hibernate.model.Person;
import edu.school21.hibernate.modelActorMovie.Actor;
import edu.school21.hibernate.modelActorMovie.Movie;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;


public class App {
    public static void main(String[] args) {
        Configuration configuration = new Configuration()
                .addAnnotatedClass(Person.class)
                .addAnnotatedClass(Item.class);
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        try (sessionFactory){
            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();



            /////////// ONE TO ONE //////////
            Person person = new Person("Tim", 69);
            person.setPassport(new Passport(61233123));
            session.save(person);

//            Person person = session.get(Person.class, 1);
//            person.getPassport().setPassportNumber(2221323);

//            Passport passport = session.get(Passport.class, 1);
//            System.out.println(passport.getPerson().getName());

//            Person person = session.get(Person.class, 1);
//            System.out.println(person.getPassport().getPassportNumber());

//            Person person = new Person("Test", 50);
//            Passport passport = new Passport(643536);
//            person.setPassport(passport);
//            session.save(person);


            ///////// ONE TO MANY, MANY TO ONE //////////
//            Person person = session.get(Person.class, 4);
//            Item item = session.get(Item.class, 1);
//            item.getOwner().getItems().remove(item);
//            item.setOwner(person);
//            person.getItems().add(item);


//            Person person = session.get(Person.class, 2);
//            session.remove(person);
//            person.getItems().forEach(item -> item.setOwner(null));

//            Person person = session.get(Person.class, 3);
//            List<Item> personItems = person.getItems();
//            for(Item i : personItems){
//                session.delete(i);
//            }
//            person.getItems().clear();

//            Person person = new Person("Test person", 12);
//            Item newItem = new Item("TestItem", person);
//            person.setItems(new ArrayList<>(Collections.singletonList(newItem)));
//            session.save(person);
//            session.save(newItem);

//            Person person = session.get(Person.class, 2);
//            Item newItem = new Item("Xbox", person);
//            person.getItems().add(newItem);
//            session.save(newItem);

//            Item item = session.get(Item.class, 5);
//            System.out.println(item);
//            Person person = item.getOwner();
//            System.out.println(person);


//            Person person = session.get(Person.class, 3);
//            List<Item> items = person.getItems();
//            System.out.println(items);

//            List<Person> people = session.createQuery("from Person").getResultList();
//            for(Person p: people){
//                System.out.println(p);
//            }

            session.getTransaction().commit();
        }
    }
}
