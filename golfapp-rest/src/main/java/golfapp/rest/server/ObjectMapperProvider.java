package golfapp.rest.server;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javax.ws.rs.ext.ContextResolver;

public class ObjectMapperProvider implements ContextResolver<ObjectMapper> {

  private final ObjectMapper objectMapper;

  public ObjectMapperProvider() {
    objectMapper = new ObjectMapper()
        .registerModule(new JavaTimeModule())
        .setVisibility(PropertyAccessor.ALL, Visibility.NONE)
        .setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
  }


  @Override
  public ObjectMapper getContext(Class<?> type) {
    return objectMapper;
  }
}
