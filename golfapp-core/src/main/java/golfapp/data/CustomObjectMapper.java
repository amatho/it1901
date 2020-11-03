package golfapp.data;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class CustomObjectMapper extends ObjectMapper {

  public static final CustomObjectMapper SINGLETON = new CustomObjectMapper();

  /**
   * Initializes a custom object mapper which only looks for fields for (de)serializing, has support
   * for Java time datastructures, and uses object equality instead of reference equality for object
   * identity when serializing.
   *
   */
  public CustomObjectMapper() {
    registerModule(new JavaTimeModule());
    setVisibility(PropertyAccessor.ALL, Visibility.NONE);
    setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
    configure(SerializationFeature.USE_EQUALITY_FOR_OBJECT_ID, true);
  }
}
