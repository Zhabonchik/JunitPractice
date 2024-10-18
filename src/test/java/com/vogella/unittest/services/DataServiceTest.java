package com.vogella.unittest.services;

import static com.vogella.unittest.model.Race.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.util.List;

import com.vogella.unittest.model.Movie;
import org.junit.jupiter.api.*;

import com.vogella.unittest.model.TolkienCharacter;

class DataServiceTest {

    DataService dataService;

    @BeforeEach
    void setUp() {
        dataService = new DataService();
    }

    @Test
    void ensureThatInitializationOfTolkeinCharactorsWorks() {
        TolkienCharacter frodo = new TolkienCharacter("Frodo", 33, HOBBIT);
        assertAll(
                ()->assertEquals(33, frodo.getAge()),
                ()->assertEquals("Frodo", frodo.getName()),
                ()->assertNotEquals("Frodon", frodo.getName()));
    }

    @Test
    void ensureThatEqualsWorksForCharaters() {
        Object jake = new TolkienCharacter("Jake", 43, HOBBIT);
        Object sameJake = jake;
        Object jakeClone = new TolkienCharacter("Jake", 12, HOBBIT);
        // TODO check that:
        assertAll(()->assertEquals(jake, sameJake), ()->assertNotEquals(jake, jakeClone));
    }

    @Test
    void checkInheritance() {
        TolkienCharacter tolkienCharacter = dataService.getFellowship().get(0);
        assertNotEquals(tolkienCharacter.getClass(), Movie.class, "tolkienCharacter is of Movie class");
    }

    @Test
    void ensureFellowShipCharacterAccessByNameReturnsNullForUnknownCharacter() {
        TolkienCharacter fellowshipCharacter = dataService.getFellowshipCharacter("Lars");
        assertNull(fellowshipCharacter);
    }

    @Test
    void ensureFellowShipCharacterAccessByNameWorksGivenCorrectNameIsGiven() {
        TolkienCharacter fellowshipCharacter = dataService.getFellowshipCharacter("Frodo");
        assertNotNull(fellowshipCharacter);
    }


    @Test
    void ensureThatFrodoAndGandalfArePartOfTheFellowship() {

        List<TolkienCharacter> fellowship = dataService.getFellowship();
        assertAll(
                () -> assertTrue(fellowship.stream().anyMatch(c ->c.getName().equals("Frodo")), "Frodo not present"),
                () -> assertTrue(fellowship.stream().anyMatch(c ->c.getName().equals("Gandalf")), "Gandalf Not present"));
    }

    @Test
    void ensureThatOneRingBearerIsPartOfTheFellowship() {

        List<TolkienCharacter> fellowship = dataService.getFellowship();

        assertTrue(fellowship.stream().anyMatch(c -> dataService.getRingBearers().containsValue(c)));
    }

    // TODO Use @RepeatedTest(int) to execute this test 1000 times
    @Tag("slow")
    @DisplayName("Minimal stress testing: run this test 1000 times to ")
    @RepeatedTest(1000)
    void ensureThatWeCanRetrieveFellowshipMultipleTimes() {
        dataService = new DataService();
        assertNotNull(dataService.getFellowship());
    }

    @Test
    void ensureOrdering() {
        List<TolkienCharacter> fellowship = dataService.getFellowship();
        assertAll(
                () -> assertEquals(dataService.getFellowshipCharacter("Frodo"), fellowship.get(0)),
                () -> assertEquals(dataService.getFellowshipCharacter("Sam"), fellowship.get(1)),
                () -> assertEquals(dataService.getFellowshipCharacter("Merry"), fellowship.get(2)),
                () -> assertEquals(dataService.getFellowshipCharacter("Pippin"), fellowship.get(3)),
                () -> assertEquals(dataService.getFellowshipCharacter("Gandalf"), fellowship.get(4)),
                () -> assertEquals(dataService.getFellowshipCharacter("Legolas"), fellowship.get(5)),
                () -> assertEquals(dataService.getFellowshipCharacter("Gimli"), fellowship.get(6)),
                () -> assertEquals(dataService.getFellowshipCharacter("Aragorn"), fellowship.get(7)),
                () -> assertEquals(dataService.getFellowshipCharacter("Boromir"), fellowship.get(8)));
    }

    @Test
    void ensureAge() {
        List<TolkienCharacter> fellowship = dataService.getFellowship();
        // TODO test ensure that all hobbits and men are younger than 100 years
        assertTrue(fellowship.stream().filter(c -> c.getRace().equals(HOBBIT)).allMatch(c -> c.getAge() < 100));
        // TODO also ensure that the elfs, dwars the maia are all older than 100 years
        assertTrue(fellowship.stream().filter(c -> c.getRace().equals(ELF) || c.getRace().equals(DWARF)).allMatch(c ->c.getAge() > 100));
    }

    @Test
    void ensureThatFellowsStayASmallGroup() {

        List<TolkienCharacter> fellowship = dataService.getFellowship();

        // TODO Write a test to get the 20 element from the fellowship throws an
        // IndexOutOfBoundsException
        assertThrows(IndexOutOfBoundsException.class, () -> fellowship.get(20));
    }

    @Test
    void ensureRunsLessThanThreeSeconds() {
        assertTimeout(Duration.ofSeconds(3000), () -> dataService.update());
    }

}