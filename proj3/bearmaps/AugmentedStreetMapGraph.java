package bearmaps;

import bearmaps.utils.graph.streetmap.Node;
import bearmaps.utils.graph.streetmap.StreetMapGraph;
import bearmaps.utils.ps.MyTrieSet;
import bearmaps.utils.ps.Point;
import bearmaps.utils.ps.WeirdPointSet;

import java.util.*;

/**
 * An augmented graph that is more powerful that a standard StreetMapGraph.
 * Specifically, it supports the following additional operations:
 *
 *
 * @author Alan Yao, Josh Hug, ________
 */
public class AugmentedStreetMapGraph extends StreetMapGraph {



    public AugmentedStreetMapGraph(String dbPath) {
        super(dbPath);
        // You might find it helpful to uncomment the line below:
        List<Node> nodes = this.getNodes();

        Map<Node,Long> nodePoint = new HashMap<>();

        for (Node node : nodes) {
            nodePoint.put(node, node.id());
        }
        WeirdPointSet weird = new WeirdPointSet(nodes);

    }


    /**
     * For Project Part II
     * Returns the vertex closest to the given longitude and latitude.
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    public long closest(double lon, double lat) {

        Point nearest = weird.nearest(lon, lat);


        //should only consider verticies that have neighbors when calculating closest


        return WeirdPointSet.nearest(lon, lat);
    }


    /**
     * For Project Part III (extra credit)
     * In linear time, collect all the names of OSM locations that prefix-match the query string.
     * @param prefix Prefix string to be searched for. Could be any case, with our without
     *               punctuation.
     * @return A <code>List</code> of the full names of locations whose cleaned name matches the
     * cleaned <code>prefix</code>.
     */
    public List<String> getLocationsByPrefix(String prefix) {
        MyTrieSet hi = new MyTrieSet();

        List<String> result = new ArrayList<>();

        Node curr = root;

        for (int i = 0; i < prefix.length(); i++) {
            curr = curr.map.get(prefix.charAt(i));
            if (curr == null) {
                return result;
            }
        }

        return prefixHelper(curr, result, prefix);
    }

    public List<String> prefixHelper(Node current,
                                     List<String> wordList, String prefixSoFar) {
        if (current.isKey) {
            wordList.add(prefixSoFar);
        }
        for (Character key : current.map.keySet()) {
            Node children = current.map.get(key);
            prefixHelper(children, wordList, prefixSoFar + key);
        }
        return wordList;


    }

    /**
     * For Project Part III (extra credit)
     * Collect all locations that match a cleaned <code>locationName</code>, and return
     * information about each node that matches.
     * @param locationName A full name of a location searched for.
     * @return A list of locations whose cleaned name matches the
     * cleaned <code>locationName</code>, and each location is a map of parameters for the Json
     * response as specified: <br>
     * "lat" -> Number, The latitude of the node. <br>
     * "lon" -> Number, The longitude of the node. <br>
     * "name" -> String, The actual name of the node. <br>
     * "id" -> Number, The id of the node. <br>
     */
    public List<Map<String, Object>> getLocations(String locationName) {
        return new LinkedList<>();
    }


    /**
     * Useful for Part III. Do not modify.
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     * @param s Input string.
     * @return Cleaned string.
     */
    private static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

}
