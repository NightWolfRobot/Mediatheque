/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mediatheque;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author antho
 */
public class FXMLMediaManagerController extends ControlledScreen implements Initializable {

    @FXML
    private Label tempUserLabel;
    @FXML
    private Label tempMediasLabel;
    @FXML
    private ImageView iv_home;
    @FXML
    private TextField tf_search;
    @FXML
    private TableView<Media> tableMedias;
    @FXML
    private TableColumn<Media, String> typeCol;
    @FXML
    private TableColumn<Media, String> titleCol;
    @FXML
    private TableColumn<Media, String> authorCol;
    @FXML
    private TableColumn<Media, Date> yearCol;
    @FXML
    private TableColumn<Media, Integer> nbAvailableCol;
    @FXML
    private Button btn;
    @FXML
    private ImageView profilPicture;
    @FXML
    private Label l_type;
    @FXML
    private Label l_title;
    @FXML
    private Label l_author;
    @FXML
    private Label l_date;
    @FXML
    private TextArea ta_infos;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorCol.setCellValueFactory(new PropertyValueFactory<>("author"));
        yearCol.setCellValueFactory(new PropertyValueFactory<>("year"));
        nbAvailableCol.setCellValueFactory(new PropertyValueFactory<>("nbDispo"));
        profilPicture.setImage(new Image("file:img/profil2.png"));
        iv_home.setImage(new Image("file:img/home.png"));
    }    

    @FXML
    protected void goToScreenHome(ActionEvent event){
       sm.setScreen(App.screenHomeID);
       sm.getController(App.screenHomeID).updateDatas();
    }

    @FXML
    private void handleResearchAction(KeyEvent event){
        String str = tf_search.getText();
        ObservableList<Media> tempList = FXCollections.observableArrayList();
        
        
        //Load if function of str
        if(!str.isEmpty()){
            tableMedias.setItems(mediatheque.getMediasList());
            for(int i=0; i<tableMedias.getItems().size(); i++){
                if(tableMedias.getItems().get(i).getTitle().toLowerCase().startsWith(str.toLowerCase()) || tableMedias.getItems().get(i).getAuthor().toLowerCase().startsWith(str.toLowerCase())){
                    tempList.add(tableMedias.getItems().get(i));
                }
            }
            tableMedias.setItems(tempList);
        } else{
            tableMedias.setItems(mediatheque.getMediasList());
        }
        
        //System.out.println(str);
    }

    @FXML
    private void handleAddtoCartAction(ActionEvent event) {
        Media m = tableMedias.getSelectionModel().getSelectedItem();
        mediatheque.getTempCart().getMedias().add(m);
        tempUserLabel.setText(mediatheque.getTempCart().getClient().getFirstName()+" "+mediatheque.getTempCart().getClient().getLastName());
        tempMediasLabel.setText(mediatheque.getTempCart().getMedias().size()+"");
    }

    @FXML
    private void handleNewMediaAction(ActionEvent event) {
    }

    @FXML
    private void handleEditMediaAction(ActionEvent event) {
    }

    @FXML
    private void handleRemoveAction(ActionEvent event) {
        Media m = tableMedias.getSelectionModel().getSelectedItem();
        mediatheque.getMediasList().remove(m);
    }

    @Override
    public void updateAfterLoadingScreen() {
        tableMedias.setItems(mediatheque.getMediasList());
        tableMedias.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            //Check whether item is selected and set value of selected item to Label
            if (tableMedias.getSelectionModel().getSelectedItem() != null) {
                l_title.setText(tableMedias.getSelectionModel().getSelectedItem().getTitle());
                l_author.setText(tableMedias.getSelectionModel().getSelectedItem().getAuthor());
                l_type.setText(tableMedias.getSelectionModel().getSelectedItem().getType());
                l_date.setText(tableMedias.getSelectionModel().getSelectedItem().getYear().toString());
                ta_infos.setText(tableMedias.getSelectionModel().getSelectedItem().toString());
                //profilPicture.setImage(new Image(tableMedias.getSelectionModel().getSelectedItem().getImg()));
            }
        });
    }

    @Override
    public void updateDatas() {
        tempUserLabel.setText(mediatheque.getTempCart().getClient().getFirstName()+" "+mediatheque.getTempCart().getClient().getLastName());
        tempMediasLabel.setText(mediatheque.getTempCart().getMedias().size()+"");
    }
    
}