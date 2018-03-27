package rest.context;

/**
 * This interface specify behavior of JsonParses.
 * 
 * @author Pavel Matyáš (matyapav@fel.cvut.cz)
 * 
 * @since 1.0.0.
 */
public interface JSONParser {

    /**
     * This method parse JSON string.
     * 
     * @param jsonString
     * @return data of document. Typically as object of generic type.
     */
    public <T> T parse(String jsonString);

}
