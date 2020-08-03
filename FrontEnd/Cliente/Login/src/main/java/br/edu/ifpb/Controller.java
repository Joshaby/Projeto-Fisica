package br.edu.ifpb;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.*;

public class Controller {

    private final List<Anos> listaAnos = new ArrayList<>();
    private final List<players> playersList = new ArrayList<>();
    private ObservableList<Anos> OBSanos;
    private ObservableList<players> OBSplayers;


    private User_IF rmiConnection;

    @FXML
    private Pane painelDeFundo;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Anos> CBano;

    @FXML
    private ComboBox<players> integrantes;

    @FXML
    private TextField nomeJogador1;

    @FXML
    private TextField nomeJogador2;

    @FXML
    private TextField nomeJogador3;

    @FXML
    private TextField nomeJogador4;

    @FXML
    private TextField nomeJogador5;

    @FXML
    private Button OK;

    @FXML
    private TextField GroupName;

    public Controller() throws RemoteException, NotBoundException {
    }


    @FXML
    void initialize() throws RemoteException, NotBoundException {
        assert nomeJogador1 != null : "fx:id=\"nomeJogador\" was not injected: check your FXML file 'Login.fxml'.";
        assert CBano != null : "fx:id=\"ano\" was not injected: check your FXML file 'Login.fxml'.";
        assert integrantes != null : "fx:id=\"integrantes\" was not injected: check your FXML file 'Login.fxml'.";
        CarregarAnos();
        Carregarplayer();
        rmiConnection = new RMIConnection(1400).getUser_if();
        painelDeFundo.setStyle("-fx-background-color:  #19751f; -fx-background-radius: 7px");
        CBano.setStyle("-fx-background-color: aliceblue;");
        integrantes.setStyle("-fx-background-color: aliceblue;");
        painelDeFundo.setPrefHeight(2);
    }

    public void CarregarAnos() {
        Anos ano1 = new Anos(1, "1º Ano");
        Anos ano2 = new Anos(2, "2º Ano");
        Anos ano3 = new Anos(3, "3º Ano");
        listaAnos.add(ano1);
        listaAnos.add(ano2);
        listaAnos.add(ano3);
        OBSanos = FXCollections.observableArrayList(listaAnos);
        CBano.setItems(OBSanos);
    }

    public void Carregarplayer() {
        players player1 = new players(1, "1 Jogador");
        players player2 = new players(2, "2 Jogadores");
        players player3 = new players(3, "3 Jogadores");
        players player4 = new players(4, "4 Jogadores");
        players player5 = new players(5, "5 Jogadores");
        playersList.add(player1);
        playersList.add(player2);
        playersList.add(player3);
        playersList.add(player4);
        playersList.add(player5);
        OBSplayers = FXCollections.observableArrayList(playersList);
        integrantes.setItems(OBSplayers);


    }

    @FXML
    public void setVisible() {

        players playerSelect = integrantes.getSelectionModel().getSelectedItem();
        if (playerSelect.getId()==1){
            painelDeFundo.setPrefHeight(140);
            painelDeFundo.setVisible(true);
            nomeJogador2.setVisible(false);
            nomeJogador3.setVisible(false);
            nomeJogador4.setVisible(false);
            nomeJogador5.setVisible(false);
        }
        else if (playerSelect.getId()==2){
            painelDeFundo.setPrefHeight(170);
            nomeJogador1.setVisible(true);
            nomeJogador2.setVisible(true);
            nomeJogador3.setVisible(false);
            nomeJogador4.setVisible(false);
            nomeJogador5.setVisible(false);
        }
        else if (playerSelect.getId()==3){
            painelDeFundo.setPrefHeight(200);
            nomeJogador1.setVisible(true);
            nomeJogador2.setVisible(true);
            nomeJogador3.setVisible(true);
            nomeJogador4.setVisible(false);
            nomeJogador5.setVisible(false);
        }
        else if (playerSelect.getId()==4){
            painelDeFundo.setPrefHeight(230);
            nomeJogador1.setVisible(true);
            nomeJogador2.setVisible(true);
            nomeJogador3.setVisible(true);
            nomeJogador4.setVisible(true);
            nomeJogador5.setVisible(false);
        }
        else if (playerSelect.getId()==5){
            painelDeFundo.setPrefHeight(270);
            nomeJogador1.setVisible(true);
            nomeJogador2.setVisible(true);
            nomeJogador3.setVisible(true);
            nomeJogador4.setVisible(true);
            nomeJogador5.setVisible(true);
        }
        else {
            nomeJogador1.setVisible(false);
            nomeJogador2.setVisible(false);
            nomeJogador3.setVisible(false);
            nomeJogador4.setVisible(false);
            nomeJogador5.setVisible(false);
        }

    }
    public void SaveMembers() throws RemoteException, NotBoundException {
        List<String> playersNames = new ArrayList<>();
        Anos yearSelect = CBano.getSelectionModel().getSelectedItem();


        String groupName = GroupName.getCharacters().toString();


        playersNames.add(nomeJogador1.getCharacters().toString());
        playersNames.add(nomeJogador2.getCharacters().toString());
        playersNames.add(nomeJogador3.getCharacters().toString());
        playersNames.add(nomeJogador4.getCharacters().toString());
        playersNames.add(nomeJogador5.getCharacters().toString());
        System.out.println("playersNames = " + playersNames);
        rmiConnection.registerSingleGroup(groupName,playersNames,yearSelect.getId());
    }

    public void printGroups() throws RemoteException {
        System.out.println(rmiConnection.getGroupsMAP());
    }

}

