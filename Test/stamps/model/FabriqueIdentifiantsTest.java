package stamps.model;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FabriqueIdentifiantsTest {

    @Test
    void getIdentifiant() {
        FabriqueIdentifiants.getInstance().reset();
        assertEquals("0",FabriqueIdentifiants.getInstance().getIdentifiant());
        assertEquals("1",FabriqueIdentifiants.getInstance().getIdentifiant());
    }

    @Test
    void reset() {
        FabriqueIdentifiants.getInstance().reset();
        assertEquals("0",FabriqueIdentifiants.getInstance().getIdentifiant());
        assertEquals("1",FabriqueIdentifiants.getInstance().getIdentifiant());
        FabriqueIdentifiants.getInstance().reset();
        assertEquals("0",FabriqueIdentifiants.getInstance().getIdentifiant());
    }
}