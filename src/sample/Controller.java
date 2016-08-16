package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import sample.company.Client;
import sample.company.TCPSocketClient;

public class Controller {

    public Button clearButton;
    private TCPSocketClient client = new TCPSocketClient(this);

    @FXML
    public TextArea messageArea;
    @FXML
    public TextArea textArea;
    @FXML
    public Button sendButton;

    public Controller() {
        messageArea = new TextArea();
    }

    private void changeColor(String color) {
        if (color.equals("red")) {
            messageArea.setStyle("-fx-text-fill: red ;");
        } else {
            messageArea.setStyle("-fx-text-fill:  chartreuse ;");
        }
    }

    public void setSendButton() {
        client.communicate();
    }

    public void sendMessage(String message, String color) {
        changeColor(color);
        message = message.replaceAll("\t", "\\\\t");
        messageArea.appendText(message + "\n");
    }

    public void sendLog(String log) {
        textArea.appendText(log + "\n");
    }

    public void clearTextArea(ActionEvent actionEvent) {
        textArea.clear();
    }
}

