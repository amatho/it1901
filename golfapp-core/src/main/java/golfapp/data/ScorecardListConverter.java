package golfapp.data;

import com.fasterxml.jackson.databind.util.StdConverter;
import golfapp.core.User;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ScorecardListConverter extends
    StdConverter<Map<User, List<Integer>>, List<ScorecardEntry>> {

  @Override
  public List<ScorecardEntry> convert(Map<User, List<Integer>> userListMap) {
    return userListMap.entrySet().stream().map(e -> {
      var entry = new ScorecardEntry();
      entry.user = e.getKey();
      entry.score = e.getValue();
      return entry;
    }).collect(Collectors.toList());
  }
}
