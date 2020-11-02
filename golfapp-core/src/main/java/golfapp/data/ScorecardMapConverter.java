package golfapp.data;

import com.fasterxml.jackson.databind.util.StdConverter;
import golfapp.core.User;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScorecardMapConverter extends
    StdConverter<List<ScorecardEntry>, Map<User, List<Integer>>> {

  @Override
  public Map<User, List<Integer>> convert(List<ScorecardEntry> scorecardEntries) {
    var map = new HashMap<User, List<Integer>>();
    for (var e : scorecardEntries) {
      map.put(e.user, e.score);
    }
    return map;
  }
}
