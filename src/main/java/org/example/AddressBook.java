package org.example;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class AddressBook {

    @Id
    private Integer id = null;

    @OneToMany
    private List<BuddyInfo> buddies;

    public AddressBook() {
        this.buddies = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<BuddyInfo> getBuddies() {
        return buddies;
    }

    public void setBuddies(List<BuddyInfo> buddies) {
        this.buddies = buddies;
    }

    public void addBuddy(BuddyInfo buddy) {
        if (buddy != null) {
            buddies.add(buddy);
        }
    }

    public BuddyInfo getBuddy(int index) {
        if (index >= 0 && index < buddies.size()) {
            return buddies.get(index);
        }
        return null;
    }

    public void removeBuddy(BuddyInfo buddy) {
        buddies.remove(buddy);
    }

    public void printBuddies() {
        for (BuddyInfo buddy : buddies) {
            System.out.println(buddy.toString());
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        AddressBook other = (AddressBook) obj;
        return Objects.equals(buddies, other.buddies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(buddies);
    }

    public static void main(String[] args) {
        AddressBook addressBook = new AddressBook();

        BuddyInfo buddy1 = new BuddyInfo("Nicky Fang", "613-555-0101");
        BuddyInfo buddy2 = new BuddyInfo("Nicky Fang 2", "613-555-0202");

        addressBook.addBuddy(buddy1);
        addressBook.addBuddy(buddy2);

        System.out.println("Address Book Contents:");
        addressBook.printBuddies();
    }
}