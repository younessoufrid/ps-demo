package v1;

import com.demo.portailsaisie.backend.core.domain.Client;
import com.demo.portailsaisie.backend.core.mapping.ClientMapper;
import com.demo.portailsaisie.backend.core.repository.ClientRepository;
import com.demo.portailsaisie.backend.core.service.interfaces.ClientService;
import com.demo.portailsaisie.backend.core.service.v1.ClientServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import v1.config.TestConfiguration;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = TestConfiguration.class)
public class ClientServiceTest {

    private ClientService clientService;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientMapper clientMapper;

    private final String EXISTING_CLIENT_REFERENCE = "client_ref_1";
    private final String NON_EXISTING_CLIENT_REFERENCE = "client_ref_2";
    private final Long ID = 1000L;


    @BeforeAll
    public void init() {
        clientService = new ClientServiceImpl(clientRepository, clientMapper);

        Client existingClient = Client.builder()
                .id(ID)
                .reference(EXISTING_CLIENT_REFERENCE)
                .build();

        when(clientRepository.findByReference(EXISTING_CLIENT_REFERENCE))
                .thenReturn(Optional.of(existingClient));
        when(clientRepository.findByReference(NON_EXISTING_CLIENT_REFERENCE))
                .thenReturn(Optional.empty());
        when(clientRepository.save(any(Client.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    void saveIfNotExistWithClientExists() {
        Client client = Client.builder()
                .reference(EXISTING_CLIENT_REFERENCE)
                .build();
        Client savedClient = clientService.saveIfNotExist(client);

        assertEquals(client.getReference(), savedClient.getReference());
    }

    @Test
    void saveIfNotExistWithClientNotExists() {
        Client client = Client.builder()
                .reference(NON_EXISTING_CLIENT_REFERENCE)
                .build();
        Client savedClient = clientService.saveIfNotExist(client);

        assertEquals(client, savedClient);
    }

}
