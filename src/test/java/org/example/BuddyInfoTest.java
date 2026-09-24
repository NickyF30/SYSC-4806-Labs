package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BuddyInfoTest {

    @Test
    void testGetName() {
        BuddyInfo buddy = new BuddyInfo("Test Name", "123-456-7890");
        assertEquals("Test Name", buddy.getName());
    }

    @Test
    void testGetAddress() {
        BuddyInfo buddy = new BuddyInfo("Test Name", "123-456-7890");
        assertEquals("123-456-7890", buddy.getAddress());
    }

    @Test
    void testEqualsSameObject() {
        BuddyInfo buddy = new BuddyInfo("Test Name", "123-456-7890");
        assertEquals(buddy, buddy);
    }

    @Test
    void testEqualsDifferentObjectsSameValues() {
        BuddyInfo buddy1 = new BuddyInfo("Test Name", "123-456-7890");
        BuddyInfo buddy2 = new BuddyInfo("Test Name", "123-456-7890");
        assertNotSame(buddy1, buddy2);
        assertEquals(buddy1, buddy2);
    }

    @Test
    void testEqualsDifferentName() {
        BuddyInfo buddy1 = new BuddyInfo("Test Name", "123-456-7890");
        BuddyInfo buddy2 = new BuddyInfo("Other Name", "123-456-7890");
        assertNotEquals(buddy1, buddy2);
    }

    @Test
    void testEqualsDifferentAddress() {
        BuddyInfo buddy1 = new BuddyInfo("Test Name", "123-456-7890");
        BuddyInfo buddy2 = new BuddyInfo("Test Name", "000-000-0000");
        assertNotEquals(buddy1, buddy2);
    }

    @Test
    void testEqualsNull() {
        BuddyInfo buddy = new BuddyInfo("Test Name", "123-456-7890");
        assertNotEquals(null, buddy);
    }

    @Test
    void testEqualsDifferentType() {
        BuddyInfo buddy = new BuddyInfo("Test Name", "123-456-7890");
        assertNotEquals("Test Name", buddy);
    }

    @Test
    void testHashCodeConsistentWithEquals() {
        BuddyInfo buddy1 = new BuddyInfo("Test Name", "123-456-7890");
        BuddyInfo buddy2 = new BuddyInfo("Test Name", "123-456-7890");
        assertEquals(buddy1, buddy2);
        assertEquals(buddy1.hashCode(), buddy2.hashCode());
    }
}