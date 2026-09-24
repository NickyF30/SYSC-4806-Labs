package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;

public class AddressBookTest {

    private AddressBook addressBook;
    private BuddyInfo buddy;

    @BeforeEach
    public void setUp() {
        addressBook = new AddressBook();
        buddy = new BuddyInfo("Test Name", "123-456-7890");
    }

    @Test
    public void testAddBuddy() {
        addressBook.addBuddy(buddy);
        BuddyInfo expected = new BuddyInfo("Test Name", "123-456-7890");
        assertNotSame(expected, addressBook.getBuddy(0));
        assertEquals(expected, addressBook.getBuddy(0));
    }

    @Test
    public void testGetBuddy() {
        BuddyInfo buddy2 = new BuddyInfo("Test Name", "123-456-7890");
        addressBook.addBuddy(buddy);
        assertEquals(buddy2, addressBook.getBuddy(0));
        assertNull(addressBook.getBuddy(1));
    }

    @Test
    public void testGetBuddyOutOfBounds() {
        assertNull(addressBook.getBuddy(0));
        assertNull(addressBook.getBuddy(-1));
    }

    @Test
    public void testRemoveBuddy() {
        addressBook.addBuddy(buddy);
        addressBook.removeBuddy(buddy);
        assertNull(addressBook.getBuddy(0));
    }

    @Test
    public void testRemoveBuddyByEqualsNotReference() {
        addressBook.addBuddy(buddy);
        BuddyInfo equalBuddy = new BuddyInfo("Test Name", "123-456-7890");
        addressBook.removeBuddy(equalBuddy);
        assertNull(addressBook.getBuddy(0));
    }

    @Test
    public void testAddressBookEquals() {
        AddressBook other = new AddressBook();
        addressBook.addBuddy(buddy);
        other.addBuddy(new BuddyInfo("Test Name", "123-456-7890"));
        assertEquals(addressBook, other);
    }
}