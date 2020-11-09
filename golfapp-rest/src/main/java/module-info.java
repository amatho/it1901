open module golfapp.rest {
  requires golfapp.core;
  requires hk2.api;
  requires jersey.media.json.jackson;
  requires jersey.common;
  requires jersey.server;
  requires java.ws.rs;
  requires com.fasterxml.jackson.databind;
  requires javax.inject;

  exports golfapp.rest.server;
  exports golfapp.rest.api;
}
