package org.example;

import java.util.Objects;

public class BuddyInfo {
    private String name;
    private String address;

    public BuddyInfo(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        BuddyInfo other = (BuddyInfo) obj;
        return Objects.equals(name, other.name) && Objects.equals(address, other.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address);
    }

    @Override
    public String toString() {
        return "BuddyInfo: " + "name: " + name + ", address: " + address + " ";
    }
}