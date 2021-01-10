package mas.ssatr.bodea.ciprian;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.Random;

public class PetriNetLoader {

    public PetriNetModel getPetriNetModel() {
        return petriNetModel;
    }

    private PetriNetModel petriNetModel = new PetriNetModel();

    public void readInputFile(String inputFile) {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(inputFile));
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray places = (JSONArray) jsonObject.get("places");
            JSONArray transitions = (JSONArray) jsonObject.get("transitions");

            // https://www.geeksforgeeks.org/for-each-loop-in-java/

            for (int i = 0; i < places.size(); i++) {
                Object place = places.get(i);

                if (place instanceof JSONObject) {
                    parsePlacesObject((JSONObject) place); //force "places" to become "jsonobjects" and sent them further to the parse function
                }
            }

            for (int i = 0; i < transitions.size(); ++i) {
                Object transition = transitions.get(i);

                if (transition instanceof JSONObject) {
                    parseTransitionsObject((JSONObject) transition);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int getTime(String time, String time2) {
        if (time2 != null) {
            return computeTime(time, time2);
        } else {
            return Integer.parseInt(time);
        }
    }

    private int computeTime(String time, String time2) {
        int t1 = Integer.parseInt(time);
        int t2 = Integer.parseInt(time2);
        Random r = new Random();
        return r.nextInt(t2 - t1) + t1;
    }

    private void parsePlacesObject(JSONObject places) {
        // https://www.tutorialspoint.com/java/number_parseint.htm

        String name = places.get("name").toString();
        String tokens = places.get("tokens").toString();
        Place place = new Place(name, Integer.parseInt(tokens));
        this.petriNetModel.Places.add(place);
    }

    private void parseTransitionsObject(JSONObject transitions) {
        JSONArray previousPlaces = (JSONArray) transitions.get("previousPlaces");
        JSONArray nextPlaces = (JSONArray) transitions.get("nextPlaces");
        String name = transitions.get("name").toString();

        String time;
        try {
            time = transitions.get("time").toString();
        } catch (Exception e) {
            time = "0";
        }

        String time2;
        try {
            time2 = transitions.get("time2").toString();
        } catch (Exception e) {
            time2 = null;
        }

        int timeInt = getTime(time, time2);

        Transition transition = new Transition(name);
        transition.setTime(timeInt);

        for (int i = 0; i < previousPlaces.size(); i++) {
            Object previousPlace = previousPlaces.get(i);
            JSONObject previousPlaceAsJSONObject = (JSONObject) previousPlace;

            var placeName = previousPlaceAsJSONObject.get("name").toString();
            for (var p : this.petriNetModel.Places) {
                if (p.getName().equalsIgnoreCase(placeName)) {
                    transition.AddPreviousPlace(p);
                }
            }
        }

        for (int i = 0; i < nextPlaces.size(); i++) {
            Object nextPlace = nextPlaces.get(i);
            JSONObject nextPlaceAsJSONObject = (JSONObject) nextPlace; // Manual casting: force conversion (https://www.w3schools.com/java/java_type_casting.asp)

            var placeName = nextPlaceAsJSONObject.get("name").toString();
            for (var p : this.petriNetModel.Places) {
                if (p.getName().equalsIgnoreCase(placeName)) {
                    transition.AddNextPlace(p);
                }
            }
        }

        this.petriNetModel.Transitions.add(transition);
    }
}
