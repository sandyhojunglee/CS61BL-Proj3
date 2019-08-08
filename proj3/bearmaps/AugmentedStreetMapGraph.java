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
    WeirdPointSet weird;
    Map<Point,Node> nodePoint;
    MyTrieSet locations;
    Map<String, String>  cleanToUnclean;


    public AugmentedStreetMapGraph(String dbPath) {
        super(dbPath);
        // You might find it helpful to uncomment the line below:
        List<Node> nodes = this.getNodes();

        List<Point> points = new ArrayList<>();

        locations = new MyTrieSet();
        cleanToUnclean = new HashMap<>();


        nodePoint = new HashMap<>();

        for (Node node : nodes) {
            Point point = new Point(node.lon(), node.lat());
            nodePoint.put(point, node);

            if (neighbors(node.id()).size() != 0) {
                points.add(point);
            }

            if (node.name() != null) {
                String name = node.name();
                String cleanedName = cleanString(name);
                locations.add(cleanedName);
                cleanToUnclean.put(name, cleanedName);
            }
        }

        weird = new WeirdPointSet(points);

    }


    /**
     * For Project Part II
     * Returns the vertex closest to the given longitude and latitude.
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    public long closest(double lon, double lat) {

        //should only consider verticies that have neighbors when calculating closest

        Point point1 = weird.nearest(lon, lat);
        return nodePoint.get(point1).id();

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
        List<String> result = new ArrayList<>();

        String cleanedPrefix = cleanString(prefix);
        List<String> cleaned = locations.keysWithPrefix(cleanedPrefix);

        for (String name : cleaned) {
            String uncleaned = cleanToUnclean.get(name);
            result.add(uncleaned);
        }
        return result;
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
        List<Map<String, Object>> result = new ArrayList<>();

        String cleanedLocationName = cleanString(locationName);

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
