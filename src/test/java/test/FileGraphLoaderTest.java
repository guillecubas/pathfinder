package test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import software.ulpgc.pathfinder.FileGraphLoader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FileGraphLoaderTest {

    @TempDir
    Path tempDir;

    @Test
    void testLoadGraphFromValidFile() throws IOException {
        File file = tempDir.resolve("graph.csv").toFile();

        Files.write(file.toPath(), List.of(
                "A,B,1.0",
                "B,C,2.0",
                "A,D,4.0",
                "C,D,1.0"
        ));

        FileGraphLoader loader = new FileGraphLoader(file);

        var graphContainer = loader.load();

        var path = graphContainer.shortestPathBetween("A", "C");

        assertEquals(List.of("A", "B", "C"), path);
        assertEquals(3.0, graphContainer.pathWeightBetween("A", "C"));
    }

    @Test
    void testInvalidLinesAreIgnored() throws IOException {
        File file = tempDir.resolve("graph-invalid.csv").toFile();

        Files.write(file.toPath(), List.of(
                "A,B,1.0",
                "linea_invalida",
                "B,C,2.0",
                "C,D,no_es_numero"
        ));

        FileGraphLoader loader = new FileGraphLoader(file);

        var graphContainer = loader.load();

        var path = graphContainer.shortestPathBetween("A", "C");

        assertEquals(List.of("A", "B", "C"), path);
        assertEquals(3.0, graphContainer.pathWeightBetween("A", "C"));
    }

    @Test
    void testFileDoesNotExistThrowsException() {
        File file = tempDir.resolve("no-existe.csv").toFile();

        FileGraphLoader loader = new FileGraphLoader(file);

        assertThrows(IOException.class, loader::load);
    }
}