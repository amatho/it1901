package golfapp.data;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class MapperInstance {

  private static final ObjectMapper MAPPER = new ObjectMapper()
      .registerModule(new JavaTimeModule())
      .setVisibility(PropertyAccessor.ALL, Visibility.NONE)
      .setVisibility(PropertyAccessor.FIELD, Visibility.ANY);

  private MapperInstance() {
  }

  /**
   * Get an {@code ObjectMapper} instance as a singleton.
   *
   * @return an {@code ObjectMapper} instance
   */
  public static ObjectMapper getInstance() {
    return MAPPER;
  }
}
