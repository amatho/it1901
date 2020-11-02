package golfapp.rest.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import golfapp.data.CustomObjectMapper;
import javax.ws.rs.ext.ContextResolver;

public class ObjectMapperProvider implements ContextResolver<ObjectMapper> {

  private final ObjectMapper objectMapper;

  public ObjectMapperProvider() {
    objectMapper = new CustomObjectMapper();
  }

  @Override
  public ObjectMapper getContext(Class<?> type) {
    return objectMapper;
  }
}
