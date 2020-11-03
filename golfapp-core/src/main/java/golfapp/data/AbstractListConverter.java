package golfapp.data;

import com.fasterxml.jackson.databind.util.StdConverter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

abstract class AbstractListConverter<K, V> extends
    StdConverter<Map<K, V>, List<MapEntry<K, V>>> {

  @Override
  public List<MapEntry<K, V>> convert(Map<K, V> map) {
    var entries = map.entrySet().stream().map(e -> {
      var entry = new MapEntry<K, V>();
      entry.key = e.getKey();
      entry.value = e.getValue();
      return entry;
    });
    var list = new ArrayList<MapEntry<K, V>>();
    entries.forEach(list::add);
    return list;
  }
}
