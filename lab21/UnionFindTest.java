import org.junit.Test;
import static org.junit.Assert.*;

public class UnionFindTest {

    @Test
    public void testSmallExample() {
        UnionFind uf = new UnionFind(6);
        uf.union(0, 1);
        uf.union(4, 0);
        uf.union(3, 5);
        assertEquals(0, uf.find(4));
        assertTrue(uf.connected(3, 5));
        uf.union(4, 3);
        assertEquals(5, uf.sizeOf(0));
        assertEquals(5, uf.sizeOf(4));
        assertEquals(5, uf.sizeOf(5));
        assertEquals(1, uf.sizeOf(2));
        assertFalse(uf.connected(2, 5));
    }

    @Test
    public void testSmallExample1() {
        UnionFind uf = new UnionFind(9);
        uf.union(2, 3);
        uf.union(5, 7);
        uf.union(3, 5);
        assertEquals(2, uf.find(3));
        uf.union(1, 8);
        uf.union(7, 1);
        uf.union(0, 6);
        uf.union(6, 4);
        assertEquals(2, uf.find(8));
        assertEquals(0, uf.find(6));

    }
}
