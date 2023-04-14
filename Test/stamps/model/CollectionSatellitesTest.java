package stamps.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CollectionSatellitesTest {

    @Test
    void ajouter() {
        CollectionSatellites collectionSatellites = new CollectionSatellites();
        assertEquals(0,collectionSatellites.nbSatellites());
        collectionSatellites.ajouter("satellite");
        assertEquals(1,collectionSatellites.nbSatellites());
        FabriqueIdentifiants.getInstance().reset();
    }

    @Test
    void trierDate() {
        CollectionSatellites collectionSatellites = new CollectionSatellites();
        collectionSatellites.ajouter("satellite1");
        collectionSatellites.ajouter("satellite2");
        collectionSatellites.trierDate();
        assertEquals("satellite1-satellite2-",collectionSatellites.toString());
        collectionSatellites.getSatellite(0).setDate(3);
        collectionSatellites.getSatellite(1).setDate(1);
        collectionSatellites.trierDate();
        assertEquals("satellite2-satellite1-",collectionSatellites.toString());
        collectionSatellites.getSatellite(0).setDate(3);
        collectionSatellites.getSatellite(1).setDate(1);
        collectionSatellites.trierDate();
        assertEquals("satellite1-satellite2-",collectionSatellites.toString());
        FabriqueIdentifiants.getInstance().reset();
    }

    @Test
    void trierApparition() {
        CollectionSatellites collectionSatellites = new CollectionSatellites();
        collectionSatellites.ajouter("satellite1");
        collectionSatellites.ajouter("satellite2");
        collectionSatellites.trierApparition();
        assertEquals("satellite1-satellite2-",collectionSatellites.toString());
        collectionSatellites.getSatellite(0).setDate(3);
        collectionSatellites.getSatellite(1).setDate(1);
        collectionSatellites.trierDate();
        collectionSatellites.trierApparition();
        assertEquals("satellite1-satellite2-",collectionSatellites.toString());
        FabriqueIdentifiants.getInstance().reset();
    }

    @Test
    void trierNom() {
        CollectionSatellites collectionSatellites = new CollectionSatellites();
        collectionSatellites.ajouter("A");
        collectionSatellites.ajouter("P");
        collectionSatellites.trierNom();
        assertEquals("A-P-",collectionSatellites.toString());
        collectionSatellites.getSatellite(0).setNom("P");
        collectionSatellites.getSatellite(1).setNom("A");
        assertEquals("P-A-",collectionSatellites.toString());
    }
}