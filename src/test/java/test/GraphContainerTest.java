package test;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import software.ulpgc.pathfinder.GraphContainer;

import static org.junit.jupiter.api.Assertions.*;

public class GraphContainerTest {

    private GraphContainer graphContainer;

    @BeforeEach
    void setUp() {
        graphContainer = new GraphContainer(mockGraph());
    }

    @Test
    void testShortestPathExists() {
        var path = graphContainer.shortestPathBetween("A", "C");

        assertNotNull(path, "The shortest path should not be null");
        assertEquals(3, path.size(), "The path should have 3 nodes");
        assertEquals("A", path.get(0), "The first node should be A");
        assertEquals("B", path.get(1), "The second node should be B");
        assertEquals("C", path.get(2), "The third node should be C");
    }

    @Test
    void testShortestPathWeight() {
        var weight = graphContainer.pathWeightBetween("A", "C");

        assertEquals(3.0, weight, "The path weight should be 3.0");
    }

    @Test
    void testMissingVertexInShortestPathThrowsException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> graphContainer.shortestPathBetween("A", "Z")
        );
    }

    @Test
    void testMissingVertexInPathWeightThrowsException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> graphContainer.pathWeightBetween("A", "Z")
        );
    }

    @Test
    void testNoPathExistsBetweenDisconnectedVertices() {
        assertThrows(
                IllegalArgumentException.class,
                () -> graphContainer.shortestPathBetween("A", "E")
        );
    }

    @Test
    void testNoPathWeightBetweenDisconnectedVerticesThrowsException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> graphContainer.pathWeightBetween("A", "E")
        );
    }

    private Graph<String, DefaultEdge> mockGraph() {
        Graph<String, DefaultEdge> graph = new SimpleWeightedGraph<>(DefaultEdge.class);

        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addVertex("D");
        graph.addVertex("E"); // isolated vertex, used to test no path

        DefaultEdge ab = graph.addEdge("A", "B");
        DefaultEdge ad = graph.addEdge("A", "D");
        DefaultEdge bc = graph.addEdge("B", "C");
        DefaultEdge cd = graph.addEdge("C", "D");

        graph.setEdgeWeight(ab, 1.0);
        graph.setEdgeWeight(bc, 2.0);
        graph.setEdgeWeight(ad, 4.0);
        graph.setEdgeWeight(cd, 1.0);

        return graph;
    }
@Test
void testPathWeightWithMissingVertexThrowsException() {
    assertThrows(
            IllegalArgumentException.class,
            () -> graphContainer.pathWeightBetween("A", "Z")
    );
}
}