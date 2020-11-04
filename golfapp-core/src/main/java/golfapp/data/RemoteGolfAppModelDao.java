package golfapp.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import golfapp.core.BookingSystem;
import golfapp.core.Course;
import golfapp.core.GolfAppModel;
import golfapp.core.User;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandler;
import java.net.http.HttpResponse.BodySubscribers;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class RemoteGolfAppModelDao implements GolfAppModelDao {

  private final URI endpoint;
  private final HttpClient client;
  private final ObjectMapper mapper;

  public RemoteGolfAppModelDao(URI endpoint) {
    this.endpoint = endpoint;
    client = HttpClient.newHttpClient();
    mapper = new CustomObjectMapper();
  }

  private static String uriEncode(String s) {
    return URLEncoder.encode(s, StandardCharsets.UTF_8);
  }

  /**
   * Tests if the remote service is available.
   *
   * @return {@code true} if the service is available, {@code false} otherwise
   */
  public boolean isAvailable() {
    var request = HttpRequest.newBuilder(endpoint).GET().build();
    try {
      return client
          .send(request,
              responseInfo -> BodySubscribers.replacing(responseInfo.statusCode() == 200))
          .body();
    } catch (IOException | InterruptedException e) {
      return false;
    }
  }

  @Override
  public GolfAppModel getModel() {
    return get("", j -> {
      try {
        return mapper.readValue(j, GolfAppModel.class);
      } catch (JsonProcessingException e) {
        throw new UncheckedIOException("Could not read model JSON", e);
      }
    }).body();
  }

  @Override
  public Set<User> getUsers() {
    return get("users", j -> {
      try {
        return new HashSet<>(mapper.readerFor(User.class).<User>readValues(j).readAll());
      } catch (IOException e) {
        throw new UncheckedIOException("Could not read users JSON", e);
      }
    }).body();
  }

  @Override
  public void addUser(User u) {
    put("users", u, j -> j);
  }

  @Override
  public boolean updateUser(User u) {
    return post("users/" + uriEncode(u.getId().toString()), u, j -> {
      try {
        return mapper.readValue(j, Boolean.class);
      } catch (JsonProcessingException e) {
        throw new UncheckedIOException("Could not read update user JSON", e);
      }
    }).body();
  }

  @Override
  public void deleteUser(User u) {
    delete("users/" + uriEncode(u.getId().toString()), j -> j);
  }

  @Override
  public Set<Course> getCourses() {
    return get("courses", j -> {
      try {
        return new HashSet<>(mapper.readerFor(Course.class).<Course>readValues(j).readAll());
      } catch (IOException e) {
        throw new UncheckedIOException("Could not read courses JSON", e);
      }
    }).body();
  }

  @Override
  public Map<Course, BookingSystem> getBookingSystems() {
    var converter = new BookingSystemsMapConverter();

    return get("bookingsystems", j -> {
      try {
        var entries = mapper.readerFor(new TypeReference<MapEntry<Course, BookingSystem>>() {
        }).<MapEntry<Course, BookingSystem>>readValues(j).readAll();
        return converter.convert(entries);
      } catch (IOException e) {
        throw new UncheckedIOException("Could not read booking systems JSON", e);
      }
    }).body();
  }

  @Override
  public void updateBookingSystem(Course c, BookingSystem b) {
    post("bookingsystems/" + uriEncode(c.getId().toString()), b, j -> j);
  }

  private <T> HttpResponse<T> get(String path, Function<String, T> jsonCallback) {
    var request = HttpRequest.newBuilder(endpoint.resolve(path))
        .header("Accept", "application/json")
        .GET()
        .build();
    return sendRequest(request, jsonCallback);
  }

  private <T, U> HttpResponse<T> put(String path, U body, Function<String, T> jsonCallback) {
    HttpRequest request = null;
    try {
      request = HttpRequest.newBuilder(endpoint.resolve(path))
          .header("Accept", "application/json")
          .header("Content-Type", "application/json")
          .PUT(BodyPublishers.ofByteArray(mapper.writeValueAsBytes(body)))
          .build();
    } catch (JsonProcessingException e) {
      throw new UncheckedIOException("Could not write value as JSON", e);
    }

    return sendRequest(request, jsonCallback);
  }

  private <T, U> HttpResponse<T> post(String path, U body, Function<String, T> jsonCallback) {
    HttpRequest request;
    try {
      request = HttpRequest.newBuilder(endpoint.resolve(path))
          .header("Accept", "application/json")
          .header("Content-Type", "application/json")
          .POST(BodyPublishers.ofByteArray(mapper.writeValueAsBytes(body)))
          .build();
    } catch (JsonProcessingException e) {
      throw new UncheckedIOException("Could not write value as JSON", e);
    }

    return sendRequest(request, jsonCallback);
  }

  private <T> HttpResponse<T> delete(String path, Function<String, T> jsonCallback) {
    var request = HttpRequest.newBuilder(endpoint.resolve(path))
        .header("Accept", "application/json")
        .DELETE()
        .build();
    return sendRequest(request, jsonCallback);
  }

  private <T> HttpResponse<T> sendRequest(HttpRequest request, Function<String, T> jsonCallback) {
    try {
      return client.send(request, createBodyHandler(jsonCallback));
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException("HTTP Request failed (URI: " + request.uri() + ")", e);
    }
  }

  private <T> BodyHandler<T> createBodyHandler(Function<String, T> jsonCallback) {
    return responseInfo -> {
      if (responseInfo.statusCode() < 300 && responseInfo.statusCode() >= 200) {
        return BodySubscribers
            .mapping(BodySubscribers.ofString(StandardCharsets.UTF_8), jsonCallback);
      }

      throw new RuntimeException("Received HTTP error: " + responseInfo.statusCode());
    };
  }
}
