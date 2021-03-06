package maxwainer.college.gui.common;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import org.jetbrains.annotations.NotNull;

public final class Alerts {

  private Alerts() {
    MoreExceptions.instantiationError();
  }

  public static void showException(final @NotNull Throwable throwable) {
    showError("Error in application!", buildError(throwable));

    AppLogger.LOGGER.log(Level.SEVERE, throwable, () -> "An exception acquired while");
  }

  public static Alert showConfirmation(
      final @NotNull String title,
      final @NotNull String message) {
    final var alert = new Alert(AlertType.CONFIRMATION);

    alert.setTitle(title);
    alert.setContentText(message);

    return alert;
  }

  public static void showInfo(
      final @NotNull String title,
      final @NotNull String errorMessage) {
    showAlert(title, errorMessage, AlertType.INFORMATION);
  }

  public static void showError(
      final @NotNull String title,
      final @NotNull String errorMessage) {
    showAlert(title, errorMessage, AlertType.ERROR);
  }

  private static void showAlert(
      final @NotNull String title,
      final @NotNull String errorMessage,
      final @NotNull AlertType alertType) {
    final var alert = new Alert(alertType);

    alert.setTitle(title);
    alert.setContentText(errorMessage);

    alert.show();
  }

  private static @NotNull String buildError(final @NotNull Throwable throwable) {
    final var builder = new StringBuilder();

    builder
        .append(throwable.getClass().getName()).append(": ")
        .append(throwable.getMessage()).append('\n');

    for (final var stackTraceElement : sanitizeStackTrace(
        throwable.getStackTrace())) {
      builder.append(stackTraceElement.toString()).append('\n');
    }

    return builder.toString();
  }

  private static List<StackTraceElement> sanitizeStackTrace(
      final @NotNull StackTraceElement[] elements) {
    final var output = new ArrayList<StackTraceElement>();

    for (final var element : elements) {
      if (!element.getClassName().contains("javafx")) {
        output.add(element);
      }
    }

    return output;
  }

}
