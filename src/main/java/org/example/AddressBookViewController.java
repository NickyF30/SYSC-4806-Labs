package org.example;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AddressBookViewController {

    private final AddressBookRepository addressBookRepo;

    public AddressBookViewController(AddressBookRepository addressBookRepo) {
        this.addressBookRepo = addressBookRepo;
    }

    @GetMapping("/addressbook/{id}")
    public String viewAddressBook(@PathVariable Integer id, Model model) {
        model.addAttribute("id", id);
        model.addAttribute("addressBook", addressBookRepo.findById(id).orElse(null));
        return "addressbook";
    }
}