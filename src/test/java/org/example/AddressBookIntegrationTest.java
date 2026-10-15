package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.client.RestTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureRestTestClient
class AddressBookIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private RestTestClient restTestClient;

    @Autowired
    private AddressBookRepository addressBookRepository;

    @Autowired
    private BuddyInfoRepository buddyInfoRepository;

    private String url(String path) {
        return "http://localhost:%d%s".formatted(port, path);
    }

    @BeforeEach
    void cleanDatabase() {
        addressBookRepository.deleteAll();
        buddyInfoRepository.deleteAll();
    }

    @Test
    void postAddressBookReturnsCreated() {
        restTestClient.post()
                .uri(url("/api/addressbooks"))
                .contentType(MediaType.APPLICATION_JSON)
                .body("{}")
                .exchange()
                .expectStatus().isCreated();

        assertThat(addressBookRepository.count()).isEqualTo(1);
    }

    @Test
    void postBuddyReturnsCreatedAndEchoesFields() {
        restTestClient.post()
                .uri(url("/api/buddies"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body("{\"name\": \"Alice Smith\", \"address\": \"613-555-0202\"}")
                .exchange()
                .expectStatus().isCreated()
                .expectBody(String.class)
                .value(body -> assertThat(body)
                        .contains("Alice Smith")
                        .contains("613-555-0202"));

        assertThat(buddyInfoRepository.findByName("Alice Smith")).hasSize(1);
    }

    @Test
    void linkingBuddyToAddressBookShowsUpInBuddiesList() {
        AddressBook book = addressBookRepository.save(new AddressBook());
        BuddyInfo buddy = buddyInfoRepository.save(new BuddyInfo("Bob Jones", "613-555-0303"));

        restTestClient.post()
                .uri(url("/api/addressbooks/" + book.getId() + "/buddies"))
                .contentType(MediaType.parseMediaType("text/uri-list"))
                .body(url("/api/buddies/" + buddy.getId()))
                .exchange()
                .expectStatus().is2xxSuccessful();

        restTestClient.get()
                .uri(url("/api/addressbooks/" + book.getId() + "/buddies"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(body -> assertThat(body).contains("Bob Jones"));
    }

    @Test
    void viewPageListsBuddiesOfAddressBook() {
        AddressBook book = new AddressBook();
        book.addBuddy(new BuddyInfo("Nicky Fang", "613-555-0101"));
        book = addressBookRepository.save(book);

        restTestClient.get()
                .uri(url("/addressbook/" + book.getId()))
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(body -> assertThat(body)
                        .contains("Nicky Fang")
                        .contains("613-555-0101"));
    }
}