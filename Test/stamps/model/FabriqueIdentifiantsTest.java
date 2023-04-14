package stamps.model;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FabriqueIdentifiantsTest {

    @Test
    void getIdentifiant() {
        assertEquals("0",FabriqueIdentifiants.getInstance().getIdentifiant());
        assertEquals("1",FabriqueIdentifiants.getInstance().getIdentifiant());
    }

    @Test
    void reset() {
    }
}