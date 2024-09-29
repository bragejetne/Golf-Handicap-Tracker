package mittprosjekt;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Queue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

public class GolfTest {

    private HandicapPlayedTo handicapPlayedTo;
    private TotalHandicap totalHandicap;
    private final String fileName = "Handicapstorer.txt";

    @BeforeEach
    void setUp() {
        handicapPlayedTo = new HandicapPlayedTo();
        totalHandicap = new TotalHandicap();
        handicapPlayedTo.localCourses();
        handicapPlayedTo.whoPlayed("John");
        // Slett testfilen før hver test hvis den eksisterer for å sikre at hver test er isolert
        try {
            Files.deleteIfExists(Paths.get(fileName));
        } catch (IOException e) {
            fail("Kunne ikke slette testfilen før testing.");
        }
    }

    @Test
    void testHandicapSpiltTil() {
        double handicap = handicapPlayedTo.handicapSpiltTil(70, "Bærum");
        assertEquals(4.6, handicap, 0.01, "Handicap ble ikke korrekt beregnet.");
    }

    @Test
    void testHandicapWithInvalidCourse() {
        assertThrows(IllegalArgumentException.class, () -> {
            handicapPlayedTo.handicapSpiltTil(70, "Invalid Course");
        }, "Forventet IllegalArgumentException for ugyldig bane.");
    }

    @Test
    void testTotalHandicapCalculation() {
        String totalHandicapString = totalHandicap.yourTotalHandicap();
        assertTrue(totalHandicapString.contains("John's handicap at"), "Totalt handicap ble ikke korrekt formatert.");
    }

    @Test
    void testCourseInfo() {
        Courseinfo course = new Courseinfo(134, 71);
        assertEquals(134, course.getSlope(), "Banens slope ble ikke korrekt returnert.");
        assertEquals(71, course.getRating(), "Banens rating ble ikke korrekt returnert.");
    }

    @Test
    void testLoadTwentyLastRounds() {
        Queue<Double> rounds = HandicapPlayedTo.loadTwentyLastRounds();
        assertNotNull(rounds, "Kunne ikke laste de siste tjue rundene.");
    }

    @Test
    void testWriteToFile() {
        // Anta at denne metoden skriver til fil
        HandicapHistory handicapHistory = new HandicapHistory();
        handicapHistory.writeToFile(totalHandicap);

        // Les filen for å verifisere at skrivingen var vellykket
        try {
            List<String> lines = Files.readAllLines(Paths.get(fileName));
            assertFalse(lines.isEmpty(), "Ingen data ble skrevet til filen.");
            assertTrue(lines.stream().anyMatch(line -> line.contains("John's rounds: ")), "Filinnholdet matcher ikke forventet format.");
        } catch (IOException e) {
            fail("Kunne ikke lese filen etter skriving.");
        }
    }

    @AfterEach
    void tearDown() {
        // Slett testfilen etter hver test for å unngå forurensning mellom tester
        try {
            Files.deleteIfExists(Paths.get(fileName));
        } catch (IOException e) {
            fail("Kunne ikke slette testfilen etter testing.");
        }
    }
}

