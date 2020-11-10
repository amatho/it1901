package golfapp.gui;

import java.util.function.Function;

public interface ViewChangeRequestListener {

  void viewChangeRequested(String fxmlName, Function<AppManager, Object> controllerFactory);
}
