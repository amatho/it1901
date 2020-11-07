open module golfapp.core {
  requires com.fasterxml.jackson.databind;
  requires com.fasterxml.jackson.datatype.jsr310;
  requires java.net.http;

  exports golfapp.core;
  exports golfapp.data;
}
