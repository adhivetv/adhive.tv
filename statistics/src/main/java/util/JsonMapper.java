package util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class JsonMapper {

    private static final Logger log = LoggerFactory.getLogger(JsonMapper.class);

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private JsonMapper() {
    }

    /**
     * Converts java object into JSON
     *
     * @param obj java object
     * @return json string
     */
    public static String toJson(Object obj) {
        try {
            return MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("error while parsing to json: e = {}", e);
        }

        return StringUtils.EMPTY;
    }

    /**
     * Converts json string object into java object
     *
     * @param json string object
     * @param cl   class type
     * @param <T>  type
     * @return java object
     */
    public static <T> T fromJson(String json, Class<T> cl) {
        try {
            return MAPPER.readValue(json, cl);
        } catch (IOException e) {
            log.error("error while parsing from json: e = {}", e);
        }

        return null;
    }
}
