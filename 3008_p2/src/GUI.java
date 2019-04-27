import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.image.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class GUI extends Application {
        private ArrayList<ArrayList> images;
        private GridPane root;
        private Authentication authentication;
        private Map playActions;
        private Button start;
        private String steam_account;
        private String fitness_account;
        private String student_account;
        private ArrayList list;
        private Button confirm;
        private int NumberOfFailure;
        private int current_level = 0;
        private Memento Scene;
        private String id;
        private data db;
        private int correctness= 0;




    @Override
        public void start(Stage primaryStage) throws Exception {
        authentication = new Authentication();
            db = new data();
            getCurrentId();
            Scene = new Memento();
            NumberOfFailure = 3;
            // Set Up password
            initialPassword();
            // record user's action
            // set team icon
            InputStream iconImagePath = new FileInputStream("C:\\3008\\pictures//icon.png");
            Image iconImage = new Image(iconImagePath);
            primaryStage.getIcons().add(iconImage);
            // Set Title
            primaryStage.setTitle("Temp_Name_Project");
            // welcome picture
            ImageView welcome = getImage("welcome.png");
            welcome.setFitWidth(900);
            welcome.setFitHeight(750);
            Pane welcome_screen = new Pane();
            // start button
            start = new Button("Start");
            start.relocate(400,700);
            start.setPrefSize(100,50);
            welcome_screen.getChildren().addAll(welcome,start);
            // show the application
            Scene well = new Scene(welcome_screen,900,750);
            primaryStage.setScene(well);
            primaryStage.show();

            start.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                // Guideline for users
                public void handle(ActionEvent event) {
                    /*
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Message to User");
                    alert.setHeaderText("Important message");
                    alert.setContentText("Guideline");
                    alert.showAndWait();
                    */
                    db.insertNewData(id,"create",(String)playActions.get(steam_account),"start",steam_account);
                    showPassword(primaryStage,steam_account);
                }
            });
        }

    private void getCurrentId() {
        id = db.getId();
        int x = 0;
        if(id == null) id = "0";
        else {
            if(id.contains(",")) id = id.substring(0,1);
            id = String.valueOf((Integer.parseInt(id)+1));
        }
    }

    public static void main(String[] args){launch(args);}

        public ImageView getImage (String img_name){
            Image image = null;
            try {
                image = new Image(new FileInputStream("C:\\3008\\pictures//" + img_name));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            ImageView imageview = new ImageView(image);
            return imageview;
        }
        public void setImages(){
            images = new ArrayList<ArrayList>();
            int x = 1;
            for(int i =0; i < 5;i++){
                images.add(new ArrayList<String>());
                for(int u =0; u < 4; u++){
                    if(x < 10)  images.get(i).add("0" + x + ".png");
                    else images.get(i).add(x + ".png");
                    x++;
                }
            }
            //shuffle(images);

        }


     // initial passwords
    private void initialPassword() {
        playActions = new HashMap();
        list = new ArrayList();
        steam_account = authentication.generatePicturePassWord();
        fitness_account = authentication.generatePicturePassWord();
        student_account  = authentication.generatePicturePassWord();
        list.add(steam_account); list.add(fitness_account); list.add(student_account);
        playActions.put(steam_account,"steam");
        playActions.put(fitness_account,"fitness");
        playActions.put(student_account,"student");
    }
    public String randomPassword(){
        int ran = (int)(Math.random()*list.size());
        return (String)list.remove(ran);
    }

     // shuffle the images
    private void shuffle(ArrayList<ArrayList> images) {
        for(int i =0; i < images.size();i++){
            for(int u =0; u < images.get(i).size();u++){
                int a = (int)(Math.random()*5);
                int b = (int)(Math.random()*4);
                String img = (String)images.get(i).get(u);
                images.get(i).set(u, images.get(a).get(b));
                images.get(a).set(b, img);
            }
        }
    }

    //showing Password screen
    public void showPassword (Stage windows,String password){
        setImages();
        ArrayList<String> convertPassword = new ArrayList<String>();
        ArrayList<ImageView> hello = new ArrayList<ImageView>();
        PseudoClass imageViewBorder = PseudoClass.getPseudoClass("border");
        convert(convertPassword,password);
        root = new GridPane();
        GridPane pointer = new GridPane();
        root.setPadding(new Insets(20));
        root.setHgap(15);
        root.setVgap(15);
        //set Header
        ImageView header = null;
        if(current_level == 0)  header = getImage("steam.png");
        else if (current_level == 1) header = getImage("fitness.png");
        else header = getImage("student.png");

        header.setFitHeight(100);
        int index  = 0;
        header.setFitWidth(470);
        root.add(header,1,0,3,1);
        for(int i =0; i < images.size();i++){
            for(int u =1; u < images.get(i).size()+1;u++){
                String name = (String)images.get(i).get(u-1);
                ImageView imageview = getImage(name);
                imageview.setFitHeight(100);
                imageview.setFitWidth(150);
                BorderPane imageViewWrapper = new BorderPane(imageview);
                imageViewWrapper.setMaxSize(50,50);

                imageViewWrapper.getStyleClass().add("image-view-wrapper");

                BooleanProperty imageViewBorderActive = new SimpleBooleanProperty() {
                    @Override
                    protected void invalidated() {
                        imageViewWrapper.pseudoClassStateChanged(imageViewBorder, get());
                    }
                };
                /*
                if(convertPassword.contains(name.substring(0,2))){
                    imageViewBorderActive.set(!imageViewBorderActive.get());
                }
                */
                imageview.setOnMouseClicked(ev -> {
                    if(!imageViewBorderActive.get()) playSound(name);
                    imageViewBorderActive.set(!imageViewBorderActive.get()); });

                imageViewWrapper.getStylesheets().add(
                        getClass().getResource("application.css").toExternalForm());

                if(convertPassword.contains(name.substring(0,2))){
                    PseudoClass border_view = PseudoClass.getPseudoClass("border");
                    ImageView pw = getImage(name);
                    pw.setFitHeight(120);
                    pw.setFitWidth(120);
                    BorderPane border = new BorderPane(pw);
                    border.setMaxSize(50,50);
                    border.getStyleClass().add("pw-view-wrapper");
                    BooleanProperty borderpane = new SimpleBooleanProperty() {
                        @Override
                        protected void invalidated() {
                            border.pseudoClassStateChanged(border_view, get());
                        }
                    };
                    border.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
                    borderpane.set(!borderpane.get());
                    root.add(border,convertPassword.indexOf(name.substring(0,2)),7);
                }
                root.add(imageViewWrapper,i,u);
            } }

        confirm = new Button("Confirm");
        confirm.setPrefSize(150,65);
        root.add(confirm,2,8);
        confirm.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Scene.setScene(windows.getScene());
                confirmPassword(windows,password,true);
            }
        });
        /*
        Button show = new Button("ShowPassWord");
        show.setPrefSize(150,65);
        root.add(show,3,8);
        show.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                for(int i =0; i < root.getChildren().size();i++){
                    if(root.getChildren().get(i).getStyleClass().contains("image-view-wrapper")){
                        BorderPane a = (BorderPane) root.getChildren().get(i);
                        if(hello.contains(a.getChildren().get(0))) a.pseudoClassStateChanged(imageViewBorder,true);
                        else a.pseudoClassStateChanged(imageViewBorder,false);
                    }
                }
            }
        });
        */
        Scene scene = new Scene(root,900,900);
        windows.setScene(scene);
        windows.setResizable(false);
        windows.show();
    }
    public void playSound(String n){
        File file = new File(soundsURL(n));
        Media hit = new Media(file.toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(hit);
        mediaPlayer.play();



    }
    // confirm screen
    public void confirmPassword (Stage windows,String  password, boolean testing){
        // create a list to store password from users
        ArrayList<String> confirm_Password = new ArrayList<>();
        // design grid pane
        root = new GridPane();
        root.setPadding(new Insets(20));
        root.setHgap(15);
        root.setVgap(15);
        //set Header
        ImageView header = null;
        if(((String)playActions.get(password)).equals("steam"))  header = getImage("steam.png");
        else if (((String)playActions.get(password)).equals("fitness")) header = getImage("fitness.png");
        else header = getImage("student.png");
        if(!testing){
            db.insertNewData(id,"enter",(String)playActions.get(password),"start",password);
        }

        header.setFitHeight(100);
        header.setFitWidth(470);
        root.add(header,1,0,3,1);

        for(int i =0; i < images.size();i++){
            for(int u =1; u < images.get(i).size()+1;u++){
                String name = (String)images.get(i).get(u-1);
                ImageView imageview = getImage(name);
                imageview.setFitHeight(140);
                imageview.setFitWidth(150);
                BorderPane imageViewWrapper = new BorderPane(imageview);
                imageViewWrapper.setMaxSize(50,50);

                imageViewWrapper.getStyleClass().add("image-view-wrapper");
                PseudoClass imageViewBorder = PseudoClass.getPseudoClass("border");

                BooleanProperty imageViewBorderActive = new SimpleBooleanProperty() {
                    @Override
                    protected void invalidated() {
                        imageViewWrapper.pseudoClassStateChanged(imageViewBorder, get());
                    }
                };

                imageview.setOnMouseClicked(ev -> {
                    if(imageViewBorderActive.get()) confirm_Password.remove(name.substring(0,2));
                    else {
                        if(confirm_Password.size() == 0) confirm_Password.add(name.substring(0,2));

                        else confirm_Password.add(confirm_Password.size(),name.substring(0,2));
                        playSound(name);
                    }
                    imageViewBorderActive.set(!imageViewBorderActive.get());
                    }
                );

                imageViewWrapper.getStylesheets().add(
                        getClass().getResource("application.css").toExternalForm());
                root.add(imageViewWrapper,i,u);
            } }


        confirm = new Button("Submit");
        confirm.setPrefSize(150,50);
        root.add(confirm,2,6);
        //handle confirm button
        confirm.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                boolean correct = true;
                //check for valid password
                if(confirm_Password.size() != 5) correct = false;
                String pwd = "";
                for(int i =0; i < confirm_Password.size();i++){
                    pwd += confirm_Password.get(i);
                }
                if(password.equals(pwd))
                    correct = true;
                else correct = false;
                // in the testing of remember process then pop out the password if user need
                if(testing){
                    if(!correct){
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Message to User");
                        alert.setHeaderText("Incorrect password");
                        alert.setContentText("If you don't remember your current password. Click OK to show off your password.");
                        Optional<ButtonType> result = alert.showAndWait();
                        if(result.get() == ButtonType.OK){
                            showPassword(windows,password);
                        }
                    }
                    // if password is correct move to next stage.
                    else{
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Message to User");
                        alert.setHeaderText("Correct password");
                        current_level++;
                        if(current_level == 1){
                            db.insertNewData(id,"create",(String)playActions.get(fitness_account),"start",fitness_account);
                            alert.setContentText("Now let's move to the " +  "Fitness Account" + " account");
                            alert.showAndWait();
                            showPassword(windows,fitness_account);
                        }
                        else if (current_level == 2) {
                            db.insertNewData(id,"create",(String)playActions.get(student_account),"start",student_account);
                            alert.setContentText("Now let's move to the " +  "Student Account" + " account");
                            alert.showAndWait();
                            showPassword(windows,student_account);
                        }
                        else{
                            alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Message to User");
                            alert.setHeaderText("Congratulation");
                            alert.setContentText("Now, please type your passwords. For each password, you are allowed to type it wrong 3 times!!!");
                            alert.showAndWait();
                            confirmPassword(windows, randomPassword(), false);
                        }
                    }
                }
                // if user are actually in the experiment check for its correctness
                else{
                    db.insertNewData(id,"enter",(String)playActions.get(password),"PasswordSubmitted",password);
                    if(!correct){
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Message to User");
                        alert.setHeaderText("Incorrect password");
                        NumberOfFailure--;
                        boolean done = false;
                        String account = null;
                        if (NumberOfFailure != 0){
                            alert.setContentText("Your number of trials has left " + NumberOfFailure);
                            alert.showAndWait();}
                        else {
                            if(list.size() > 0) {
                                account = randomPassword();
                                alert.setContentText("Now let's move to the " + playActions.get(account) + " account");
                                alert.showAndWait();
                            }
                            else {done = true;
                                alert.setContentText("The research is done, thank you very much for your cooperation!");
                                alert.showAndWait();
                            }
                        }
                        if(NumberOfFailure == 0){
                            db.insertNewData(id,"login",(String)playActions.get(password),"failure",password);
                           // if this is the last one show then terminate
                            if(done){
                                ImageView goodOne = getImage("goodOne.png");
                                goodOne.setFitWidth(600);
                                goodOne.setFitHeight(400);
                                Pane bye_screen = new Pane();
                                bye_screen.getChildren().addAll(goodOne);
                                // show the picture
                                Scene bye = new Scene(bye_screen,600,400);
                                windows.setScene(bye);
                                windows.show();
                            }
                            else {
                                NumberOfFailure = 3;
                                confirmPassword(windows, account, false);
                            }
                        }
                        else confirmPassword(windows,password,false);
                    }
                    // if password is correct move to next stage.
                    else{
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Message to User");
                        alert.setHeaderText("Correct password");
                        alert.setContentText("Now let's move to another account's password");
                        alert.showAndWait();
                        db.insertNewData(id,"login",(String)playActions.get(password),"success",password);
                        correctness++;
                        // successfully pass the experiment
                        if(correctness == 3){
                            ImageView congratulation = getImage("congratulation.jpg");
                            congratulation.setFitWidth(900);
                            congratulation.setFitHeight(400);
                            Pane congratulation_screen = new Pane();
                            congratulation_screen.getChildren().addAll(congratulation);
                            // show the picture
                            Scene congratz = new Scene(congratulation_screen,900,400);
                            windows.setScene(congratz);
                            windows.show();
                        }
                        else if(list.size() == 0){
                            ImageView goodOne = getImage("goodOne.png");
                            goodOne.setFitWidth(600);
                            goodOne.setFitHeight(400);
                            Pane bye_screen = new Pane();
                            bye_screen.getChildren().addAll(goodOne);
                            // show the picture
                            Scene bye = new Scene(bye_screen,600,400);
                            windows.setScene(bye);
                            windows.show();
                        }
                        else {
                            NumberOfFailure = 3;
                            confirmPassword(windows, randomPassword(), false);}
                    }
                }
            }
        });

        Scene scene = new Scene(root,900,870);
        windows.setScene(scene);
        windows.show();
    }


    private void convert(ArrayList convertPassword, String password) {
        if (password.length() == 2) convertPassword.add(password);
        else {
            convertPassword.add(password.substring(0,2));
            convert(convertPassword,password.substring(2));}
    }

    public String soundsURL(String name){
        String output = "C:\\3008\\sounds\\";
        output += name +".mp3";
        return output;
    }

}
