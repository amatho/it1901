module golfapp.core {
  requires com.google.gson;

  exports golfapp.core;
  opens golfapp.core to com.google.gson;
}
