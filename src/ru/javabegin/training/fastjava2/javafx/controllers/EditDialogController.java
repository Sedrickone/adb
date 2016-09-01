package ru.javabegin.training.fastjava2.javafx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ru.javabegin.training.fastjava2.javafx.objects.Person;
import ru.javabegin.training.fastjava2.javafx.utils.DialogManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;


public class EditDialogController implements Initializable{

    @FXML
    private Button btnOk;

    @FXML
    private Button btnCancel;

    @FXML
    private TextField txtFIO;

    @FXML
    private TextField txtPhone;

    @FXML
    private TextField txtEmail;

    private Person person;

    private ResourceBundle resourceBundle;

    private String imagePath="A:\\Java\\Education\\JavaBegin\\Java\\Lessons\\AddressBookFX_4\\src\\ru\\javabegin\\training\\fastjava2\\javafx\\images\\no_photo.png";

    private  File file=new File(imagePath);

    private boolean saveClicked = false;// для определения нажатой кнопки

    @FXML
    private ImageView imgPhoto;

    public void setPerson(Person person) throws MalformedURLException {
        if (person == null){
            return;
        }
        saveClicked = false;
        this.person = person;
        txtFIO.setText(person.getFio());
        txtPhone.setText(person.getPhone());
        txtEmail.setText(person.getEmail());
        setImageView(imagePath);
    }

    public Person getPerson() {
        return person;
    }

    public void actionClose(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.hide();
    }


    public void actionSave(ActionEvent actionEvent) throws IOException {
        if (!checkValues()){
            return;
        }
        person.setFio(txtFIO.getText());
        person.setPhone(txtPhone.getText());
        person.setEmail(txtEmail.getText());
        importImage(file);
        saveClicked = true;
        actionClose(actionEvent);
    }

    private boolean checkValues() {
        if (txtFIO.getText().trim().length()==0 || txtPhone.getText().trim().length()==0||txtPhone.getText().trim().length()==0){
            DialogManager.showInfoDialog(resourceBundle.getString("error"), resourceBundle.getString("fill_field"));
            return false;
        }

        return true;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resourceBundle = resources;
    }

    public boolean isSaveClicked() {
        return saveClicked;
    }

    public void chgPhoto(MouseEvent mouseEvent) throws MalformedURLException {
        FileChooser chooser=new FileChooser();
        chooser.setTitle("Выберите фото контакта");
        String imagePath=chooser.showOpenDialog(((Node) mouseEvent.getSource()).getScene().getWindow()).getPath();
        this.file=new File(imagePath);
        setImageView(imagePath);
    }
    public void setImageView(String imagePath) throws MalformedURLException {
        Image image=new Image(new File(imagePath).toURI().toURL().toString());
        imgPhoto.setImage(image);
    }
    public  void importImage(File file) throws IOException {
        BufferedImage bufferedImage=ImageIO.read(file);
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        ImageIO.write(bufferedImage,"png",byteArrayOutputStream);
        byte [] imageByte=byteArrayOutputStream.toByteArray();
        person.setPhoto(imageByte);

    }

}
