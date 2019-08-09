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
 * @author Alan Yao, Josh Hug, ________
 */
public class AugmentedStreetMapGraph extends StreetMapGraph {
    WeirdPointSet weird;
    Map<Point, Node> nodePoint;
    MyTrieSet locations;
    Map<String, List<String>> cleanToUnclean;
    List<String> listOfNames;
    Map<String, List<Node>> nameNode;


    public AugmentedStreetMapGraph(String dbPath) {
        super(dbPath);
        // You might find it helpful to uncomment the line below:
        List<Node> nodes = this.getNodes();

        List<Point> points = new ArrayList<>();
        nodePoint = new HashMap<>();

        locations = new MyTrieSet();
        cleanToUnclean = new HashMap<>();
        listOfNames = new ArrayList<>();
        nameNode = new HashMap<>();


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


                List<String> value = cleanToUnclean.get(cleanedName);

                if (value == null) {
                    value = new ArrayList<>();
                    value.add(name);
                    cleanToUnclean.put(cleanedName, value);
                } else {
                    if (!value.contains(name)) {
                        value.add(name);
                    }
                }

                List<Node> value1 = nameNode.get(cleanedName);

                if (value1 == null) {
                    value1 = new ArrayList<>();
                    value1.add(node);
                    nameNode.put(cleanedName, value1);
                } else {
                    if (!value1.contains(cleanedName)) {
                        value1.add(node);
                    }
                }

            }
        }

        weird = new WeirdPointSet(points);

    }


    /**
     * For Project Part II
     * Returns the vertex closest to the given longitude and latitude.
     *
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
     *
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

            List<String> uncleaned = cleanToUnclean.get(name);
            for (String hi : uncleaned) {
                result.add(hi);
            }
        }
        return result;
    }


    /**
     * For Project Part III (extra credit)
     * Collect all locations that match a cleaned <code>locationName</code>, and return
     * information about each node that matches.
     *
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
        List<String> cleaned = locations.keysWithPrefix(cleanedLocationName);



        for (String location : cleaned) {
            //List<String> loc = cleanToUnclean.get(location);
            //for (String name : loc) {
                List<Node> nodes = nameNode.get(location);
                for(Node node : nodes) {
                    HashMap<String, Object> info = new HashMap<>();
                    info.put("lat", node.lat());
                    info.put("lon", node.lon());
                    info.put("name", node.name());
                    info.put("id", node.id());
                    result.add(info);
                }
            //}
        }

        return result;
    }
//    expected:<[{name=Shattuck Av & Cedar St, lon=-122.2691869, id=2504241145, lat=37.8785771}, {name=Shattuck Av & Cedar St, lon=-122.2690415, id=2504241144, lat=37.8783204}, {name=Shattuck Av & Cedar St, lon=-122.269245, id=348192497, lat=37.878581}, {name=Shattuck Av & Cedar St, lon=-122.268971, id=348192496, lat=37.878326}]>
//    but was:<[{name=Shattuck Av & Cedar St, lon=-122.2690415, id=2504241144, lat=37.8783204}]>
//    <[{name=Martin Luther King Jr Way & Cedar Street, lon=-122.273618, id=427901410, lat=37.878018}, {name=Martin Luther King Jr Way & Cedar Street, lon=-122.273781, id=1710879770, lat=37.877671}]>
//    <[{name=Martin Luther King Jr Way & Cedar Street, lon=-122.273781, id=1710879770, lat=37.877671}]>


    /**
     * Useful for Part III. Do not modify.
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     *
     * @param s Input string.
     * @return Cleaned string.
     */
    private static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

}
