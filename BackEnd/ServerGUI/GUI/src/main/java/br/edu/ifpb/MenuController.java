package br.edu.ifpb;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.BindException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Map;

public class MenuController {

    private ServerAdm_IF server;

    @FXML
    private javafx.scene.control.ProgressBar ProgressBar;

    @FXML
    private TableView<Group> score_table;

    @FXML
    private TableColumn<Group, String> col_Grupo;

    @FXML
    private TableColumn<Group, String> col_ponto;

    @FXML
    private TextField AnularQTXT, NQTXT;

    @FXML
    private Button AnulaQButton, QOkQuestao, NquestionButton, NQOKButton;

    @FXML
    protected Label status;



    public MenuController() throws RemoteException {
        boolean serverOffline = true;
        for(int port = 1099; port < 10000; port++){
            try {
                server = new RMIConnection(port).getServerAdm();
                System.out.println(port);
                serverOffline = false;
                break;
            }catch (RemoteException | NotBoundException ignored){
            }
        }
        if(serverOffline){
            System.out.println("Talvez o servidor esteja desligado!");
            System.exit(0);
        }

        //initTable();
        //Updatecols();

    }

    @FXML
    private void StartGame(ActionEvent event) throws RemoteException {
        server.startGame();
        status.setText("The game has started");
    }

    @FXML
    private void FinishGame(ActionEvent event) throws RemoteException {
        try { server.FinishServer(); }
        catch (Exception e){ System.exit(0); }
    }

    @FXML
    private void RestartGame(ActionEvent event) throws RemoteException {
        server.restartGame();
    }

    @FXML
    private void changeAmount(ActionEvent event){
        NquestionButton.setVisible(false);
        NQOKButton.setVisible(true);
        NQTXT.setVisible(true);
    }

    @FXML
    private void changeAmountReverse() throws RemoteException {
        NquestionButton.setVisible(true);
        NQOKButton.setVisible(false);
        NQTXT.setVisible(false);
        server.changeAmount(Integer.parseInt(NQTXT.getCharacters().toString()));
    }

    @FXML
    private void ResetPoints(ActionEvent event) throws RemoteException {
        server.resetPontos();
    }

    @FXML
    private void cancelQuestion() {
        AnulaQButton.setVisible(false);
        AnularQTXT.setVisible(true);
        QOkQuestao.setVisible(true);
    }

    @FXML
    private void AnularOKButton() throws RemoteException {
        QOkQuestao.setVisible(false);
        AnularQTXT.setVisible(false);
        AnulaQButton.setVisible(true);
        server.cancelQuestion(AnularQTXT.getCharacters().toString());
    }

    @FXML
    private void AddTestGroup() throws RemoteException {
        server.addTestGroup();
    }

    @FXML
    private void RemoveTestGroup() throws RemoteException {
        server.removeTestGroup();
    }

    @FXML
    private void cleanGroups() throws RemoteException{
        server.cleanGroups();
    }

    @FXML
    private void ResetQuestions() throws RemoteException {
        server.resetQuestions();
    }

    public void initTable(){
        initCols();
    }

    public void initCols(){
        col_Grupo.setCellValueFactory(new PropertyValueFactory<>("nome"));
        col_ponto.setCellValueFactory(new PropertyValueFactory<>("pontos"));
        editCols();
    }

    public void editCols(){
        col_Grupo.setCellFactory(TextFieldTableCell.forTableColumn());
        col_Grupo.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).getName();
        });

        col_ponto.setCellFactory(TextFieldTableCell.forTableColumn());
        col_ponto.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).getName();
        });
    }

    public void Updatecols() throws RemoteException {
        ObservableList<Group> table_data = FXCollections.observableArrayList();

        Map<String, Integer> groups = server.getGroupScores();

        for (String s : groups.keySet()) {
            table_data.add(new Group(s, String.valueOf(groups.get(s))));
        }

        score_table.setItems(table_data);
    }

}
