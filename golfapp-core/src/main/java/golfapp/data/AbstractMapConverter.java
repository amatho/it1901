package golfapp.data;

import com.fasterxml.jackson.databind.util.StdConverter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

abstract class AbstractMapConverter<K, V> extends
    StdConverter<List<MapEntry<K, V>>, Map<K, V>> {

  @Override
  public Map<K, V> convert(List<MapEntry<K, V>> entries) {
    var map = new HashMap<K, V>();
    for (var e : entries) {
      map.put(e.key, e.value);
    }
    return map;
  }
}
