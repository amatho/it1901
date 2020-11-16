package golfapp.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
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
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RemoteGolfAppModelDao implements GolfAppModelDao {

  private final URI endpoint;
  private final HttpClient client;
  private final ObjectMapper mapper;

  /**
   * Create a {@link GolfAppModelDao} which uses a REST API.
   *
   * @param endpoint the endpoint of the REST API
   */
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

  /**
   * {@inheritDoc}
   */
  @Override
  public GolfAppModel getModel() {
    return this.<GolfAppModel>get("", mapper.readerFor(GolfAppModel.class)).body();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Set<User> getUsers() {
    return new HashSet<>(this.<List<User>>get("users", mapper.readerForListOf(User.class)).body());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean addUser(User u) {
    return this.<Boolean, User>put("users", u, mapper.readerFor(Boolean.class)).body();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean updateUser(User u) {
    return this.<Boolean, User>post("users/" + uriEncode(u.getId().toString()), u,
        mapper.readerFor(Boolean.class)).body();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean deleteUser(User u) {
    return this.<Boolean>delete("users/" + uriEncode(u.getId().toString()),
        mapper.readerFor(Boolean.class)).body();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Set<Course> getCourses() {
    return new HashSet<>(
        this.<List<Course>>get("courses", mapper.readerForListOf(Course.class)).body());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Map<Course, BookingSystem> getBookingSystems() {
    var converter = new BookingSystemsMapConverter();
    var bookingSystemList = this.<List<MapEntry<Course, BookingSystem>>>get("bookingsystems",
        mapper.readerFor(new TypeReference<List<MapEntry<Course, BookingSystem>>>() {
        }))
        .body();
    return converter.convert(bookingSystemList);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean updateBookingSystem(Course c, BookingSystem b) {
    return this.<Boolean, BookingSystem>post("bookingsystems/" + uriEncode(c.getId().toString()), b,
        mapper.readerFor(Boolean.class)).body();
  }

  private <R> HttpResponse<R> get(String path, ObjectReader objectReader) {
    var request = HttpRequest.newBuilder(endpoint.resolve(path))
        .header("Accept", "application/json")
        .GET()
        .build();
    return sendRequest(request, objectReader);
  }

  private <ResponseT, BodyT> HttpResponse<ResponseT> put(String path, BodyT body,
      ObjectReader objectReader) {
    HttpRequest request;
    try {
      request = HttpRequest.newBuilder(endpoint.resolve(path))
          .header("Accept", "application/json")
          .header("Content-Type", "application/json")
          .PUT(BodyPublishers.ofByteArray(mapper.writeValueAsBytes(body)))
          .build();
    } catch (JsonProcessingException e) {
      throw new UncheckedIOException("Could not write value as JSON", e);
    }

    return sendRequest(request, objectReader);
  }

  private <ResponseT, BodyT> HttpResponse<ResponseT> post(String path, BodyT body,
      ObjectReader objectReader) {
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

    return sendRequest(request, objectReader);
  }

  private <R> HttpResponse<R> delete(String path, ObjectReader objectReader) {
    var request = HttpRequest.newBuilder(endpoint.resolve(path))
        .header("Accept", "application/json")
        .DELETE()
        .build();
    return sendRequest(request, objectReader);
  }

  private <R> HttpResponse<R> sendRequest(HttpRequest request, ObjectReader objectReader) {
    try {
      return client.send(request, createBodyHandler(objectReader));
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException("HTTP Request failed (URI: " + request.uri() + ")", e);
    }
  }

  private <T> BodyHandler<T> createBodyHandler(ObjectReader objectReader) {
    return responseInfo -> {
      if (responseInfo.statusCode() < 300 && responseInfo.statusCode() >= 200) {
        return BodySubscribers
            .mapping(BodySubscribers.ofString(StandardCharsets.UTF_8), json -> {
              if (json.isBlank()) {
                return null;
              }
              try {
                return objectReader.readValue(json);
              } catch (JsonProcessingException e) {
                throw new RuntimeException("Could not read JSON from body", e);
              }
            });
      }

      throw new RuntimeException("Received HTTP error: " + responseInfo.statusCode());
    };
  }
}
