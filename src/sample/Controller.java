package sample;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField main_field;

    @FXML
    private Button add_task;

    @FXML
    private VBox all_tasks;

    // Объект на основен нашего класса для работы с БД
    DB db = null;

    @FXML
    void initialize() {

        db = new DB();

        add_task.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent mouseEvent) {
                try {

                    if(!main_field.getText().trim().equals("")) {

                        db.insertTask(main_field.getText());
                        loadInfo();
                        main_field.setText("");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        // Метод для подгрузки списков БД внутрь программы
        loadInfo();
    }

    void loadInfo() {
        try {
            // очистка прошлых значений
            all_tasks.getChildren().clear();

            // Получаем все переменных из БД
            ArrayList<String> tasks = db.getTasks();
            for(int i = 0; i < tasks.size(); i++) // Перебираем их через цикл
                // Добавляем каждое задание в объект VBox (all_tasks)
                all_tasks.getChildren().add( new Label( tasks.get(i)));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
