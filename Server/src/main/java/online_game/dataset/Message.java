package online_game.dataset;

import com.google.gson.Gson;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Message {
    private static final String DATASET_PACKAGE = "online_game.dataset.";
    private String className;

    public Message() {
        this.className = this.getClass().getName();
    }

    public static String toJson(Object obj) {
        return new Gson().toJson(obj);
    }

    public static Message fromJson(String json) {
        try {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(json);
            String className = DATASET_PACKAGE + jsonObject.get("className");
            Class<?> msgClass = Class.forName(className);
            return (Message) new Gson().fromJson(json, msgClass);
        } catch (ClassNotFoundException | ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
