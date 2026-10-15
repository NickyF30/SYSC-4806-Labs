package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.client.RestTestClient;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Lighter version of the integration tests: @SpringBootTest keeps its default
 * (mock web environment), so no real server is started, but requests still go
 * through Spring's full MVC stack.
 */

@SpringBootTest
@AutoConfigureRestTestClient
class AddressBookMockTest {

    @Autowired
    private RestTestClient restTestClient;

    @Autowired
    private AddressBookRepository addressBookRepository;

    @Autowired
    private BuddyInfoRepository buddyInfoRepository;

    @BeforeEach
    void cleanDatabase() {
        addressBookRepository.deleteAll();
        buddyInfoRepository.deleteAll();
    }

    @Test
    void postBuddyReturnsCreated() {
        restTestClient.post()
                .uri("/api/buddies")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body("{\"name\": \"Mock Buddy\", \"address\": \"1 Mock Street\"}")
                .exchange()
                .expectStatus().isCreated()
                .expectBody(String.class)
                .value(body -> assertThat(body).contains("Mock Buddy"));
    }

    @Test
    void viewPageShowsBuddyName() {
        AddressBook book = new AddressBook();
        book.addBuddy(new BuddyInfo("Mock Buddy", "1 Mock Street"));
        book = addressBookRepository.save(book);

        restTestClient.get()
                .uri("/addressbook/" + book.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(body -> assertThat(body)
                        .contains("Mock Buddy")
                        .contains("1 Mock Street"));
    }

    @Test
    void viewPageForMissingAddressBookShowsNotFoundMessage() {
        restTestClient.get()
                .uri("/addressbook/9999")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(body -> assertThat(body).contains("No address book found with id 9999"));
    }
}