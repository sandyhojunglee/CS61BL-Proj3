public class UnionFind {

    private int[] id;
    private int[] size;

    /* Creates a UnionFind data structure holding N vertices. Initially, all
       vertices are in disjoint sets. */
    public UnionFind(int N) {
        id = new int[N];
        size = new int[N];
        for (int i = 0; i < N; i++) {
            id[i] = -1;
        }
        for (int i = 0; i < N; i++) {
            size[i] = 1;
        }
    }

    /* Returns the parent of V. If V is the root of a tree, returns the
       negative size of the tree for which V is the root. */
    public int parent(int v) {
        //is this finding the root or just the parent above it
        return id[v];
    }

    private int root(int v) {
        while (id[v] >= 0) {
            int temp = id[v];
            v = id[v];
            if (v < 0) {
                return temp;
            }
        }
        return v;
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. If invalid vertices are passed into this
       function, throw an IllegalArgumentException. */
    public int find(int v) {
        if (v > id.length) {
            throw new IllegalArgumentException();
        }
        int root = id[v];
        int temp = id[v];
        while (root >= 0) {
            temp = root;
            root = id[root];
        } //path compression
        while (root(v) != v) {
            int newPoint = id[v];
            id[v] = root(v);
            v = newPoint;
        }

        if (temp < 0) {
            return v;
        }
        return temp;
    }

    /* Connects two elements V1 and V2 together. V1 and V2 can be any element,
       and a union-by-size heuristic is used. If the sizes of the sets are
       equal, tie break by choosing V1 to be the new root. Union-ing a vertex
       with itself or vertices that are already connected should not change the
       structure. */
    public void union(int v1, int v2) {
        int v1Root = find(v1);
        int v2Root = find(v2);

        if (v1Root == v2Root) {
            return;
        }
        if (size[v1Root] < size[v2Root]) {
            id[v1Root] = v2Root;
            size[v2Root] += size[v1Root];
            id[v2Root] = -(size[v2Root]);
        } else {
            id[v2Root] = v1Root;
            size[v1Root] += size[v2Root];
            id[v1Root] = -(size[v1Root]);
        }
        //change all entries with wqupc[v1] to wqupc[v2]
        //set ID of v1's root to the id of v2's root
    } // every union calls find

    /* Returns the size of the set V belongs to. */
    public int sizeOf(int v) {
        int root = root(v);
        return -(id[root]);
    }

    /* Returns true if nodes V1 and V2 are connected. */
    public boolean connected(int v1, int v2) {
        return find(v1) == find(v2);
        //check if v1 and v2 have the same root
        //check if v1 and v2 have the same root/ "parent" ?
    }
}
