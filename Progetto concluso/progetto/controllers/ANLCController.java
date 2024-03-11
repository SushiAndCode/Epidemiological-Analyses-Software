package progetto.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import progetto.App;
import progetto.Modello;
import progetto.Oggetti.AnnualeDecessi;
import progetto.Oggetti.Provincia;
import progetto.Oggetti.Regione;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ANLCController implements Initializable {
    private Modello modello;
    @FXML
    private Label idCredenziali;

    public ANLCController(){
        this.modello = Modello.getInstance();
    }


    @FXML private ChoiceBox<String> boxCaso;
    @FXML private ChoiceBox<Integer> boxAnno;
    @FXML private ChoiceBox<String> boxProvincia;
    @FXML private ChoiceBox<String> boxGrafico;


    @FXML private CategoryAxis asseX;
    @FXML private  NumberAxis asseY;

    @FXML private LineChart<String, Integer> lineChart;
    @FXML private BarChart<String, Integer> barChart;


    public void onLoad(){
        idCredenziali.setText(modello.getUtente().getNome() + " " + modello.getUtente().getCognome());
        boxCaso.getItems().addAll("Incidenti Stradali" , "Tumori", "Cardiovascolari" , "Contagiose");
        boxGrafico.getItems().addAll("Grafico a dispersione", "Istogramma");
        boxProvincia.getItems().add("--");
        boxAnno.getItems().add(-1);

        for(Regione regione : modello.getRegioni()){
            for(Provincia provincia : regione.getProvince()){
                boxProvincia.getItems().add(provincia.getNome());
                for(AnnualeDecessi anno : provincia.getDecessi()){
                    if(!boxAnno.getItems().contains(anno.getAnnoNonProperty()))
                        boxAnno.getItems().add(anno.getAnnoNonProperty());
                }
            }
        }


    }

    @FXML public void changeScreenTo_ANLView() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/ANLView.fxml"));
        ANLController controller =  new ANLController();
        loader.setController(controller);
        Parent parent_ANLView = loader.load();
        controller.onLoad();
        Scene scene_ANLView = new Scene(parent_ANLView);
        //adesso prendo le informazioni attuali dello stage
        Stage window = App.getApp().getView().window;
        window.setScene(scene_ANLView);
        window.show();
    }

    @FXML public void changeScreenTo_ANLView_data() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/ANLView_data.fxml"));
        ANLDController controller =  new ANLDController();
        loader.setController(controller);
        Parent parent_PCNView = loader.load();
        controller.onLoad();
        Scene scene_PCNView = new Scene(parent_PCNView);
        //adesso prendo le informazioni attuali dello stage
        Stage window = App.getApp().getView().window;;
        window.setScene(scene_PCNView);
        window.show();
    }

    @FXML public void changeScreenTo_ANLView_export() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/ANLView_export.fxml"));
        ANLEController controller =  new ANLEController();
        loader.setController(controller);
        Parent parent_PCNView = loader.load();
        controller.onLoad();
        Scene scene_PCNView = new Scene(parent_PCNView);
        //adesso prendo le informazioni attuali dello stage
        Stage window = App.getApp().getView().window;;
        window.setScene(scene_PCNView);
        window.show();
    }

    @FXML void changeScreenTo_LoginView() throws IOException {
        Parent parent_LoginView = FXMLLoader.load(getClass().getResource( "../view/LoginView.fxml"));
        Scene scene_loginView = new Scene(parent_LoginView);
        //adesso prendo le informazioni attuali dello stage
        Stage window = App.getApp().getView().window;;
        window.setScene(scene_loginView);
        window.show();
    }

    @FXML void changeScreenTo_SettingView() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/SettingsView.fxml"));
        SettingsController controller =  new SettingsController();
        loader.setController(controller);
        Parent parent_ANLEView = loader.load();
        controller.onLoad();
        Scene scene_ANLEView = new Scene(parent_ANLEView);
        //adesso prendo le informazioni attuali dello stage
        Stage window = App.getApp().getView().window;
        window.setScene(scene_ANLEView);
        window.show();

    }



    public void getGrafico(String caso , int anno , String provincia, String grafico){

        XYChart.Series<String, Integer> series1LineChart = new XYChart.Series<String, Integer>();
        XYChart.Series<String, Integer> series2LineChart = new XYChart.Series<String, Integer>();
        XYChart.Series<String, Integer> series1BarChart = new XYChart.Series<String, Integer>();
        XYChart.Series<String, Integer> series2BarChart = new XYChart.Series<String, Integer>();

        lineChart.getData().clear();
        barChart.getData().clear();
        series1LineChart.getData().clear();
        series2LineChart.getData().clear();
        series1BarChart.getData().clear();
        series2BarChart.getData().clear();

        //Con questo primo if guardo la malattia di cui sto contando i casi
       if(caso.equals("Incidenti Stradali")){
           //Con questo if sto controllando che tipo di grafico ho selezionato all'interno delle choicebox
           if(grafico.equals("Grafico a dispersione")){
               //Funzione per rendere visibile il grafico a dispersione e rendere invisibile l'istogramma ( non so se funzionino effettivamente )
               lineChart.setVisible(true);
               barChart.setVisible(false);
                //Controllo se nelle choiceBox ho scelto una provincia in particolare in quel caso controllo tutti gli anni le malattie in quella determinata provincia
               if(!(provincia.equals("")) && anno == -1){
                   for(Regione regione : modello.getRegioni()){
                       for(Provincia prov : regione.getProvince()){
                            //Scorro tutte le province e ad ogni provincia controllo se è quella che stavo cercando
                           if(prov.getNome().equals(provincia)){
                                //Se trovo la provincia che stavo cercando scorro tutti gli annuali decessi in modo da mettere nel grafico sull'asse y il numero di casi di quella malattia
                                //mentre nell'asse x mettere l' anno in cui è stato riscontrato quel numero di casi
                               for(AnnualeDecessi annuale : prov.getDecessi()){
                                    //con questo metodo praticamente creo una coordinata che poi sarà aggiunta al grafico
                                    //Con i cicli sto creando una serie di coordinate
                                   series2LineChart.getData().add(new XYChart.Data<String, Integer>(String.valueOf(annuale.getAnnoNonProperty()), annuale.incidentiStradaliProperty().getValue()));
                               }
                           }
                       }
                   }
                   //con questo metodo sto inserendo tutte le coordinate create prima all'interno del grafico a dispersione
                   lineChart.getData().add(series2LineChart);
               //Con questo else if controllo che voglia vedere tutte le province in uno specifico anno
               }
               else if(provincia.equals("") && anno != -1){
                   for(Regione regione : modello.getRegioni()){
                       for(Provincia prov : regione.getProvince()){
                           for(AnnualeDecessi annuale : prov.getDecessi()){
                                //Scorro tutti gli AnnualiDecessi di tutte le province per controllare di prendere solamente quelli che rispettano l'anno che ho scelto
                                //nella choiceBox
                               if(annuale.getAnnoNonProperty() == anno){
                                   series1LineChart.getData().add(new XYChart.Data<String, Integer>(prov.getNome(), annuale.incidentiStradaliProperty().getValue()));
                               }
                           }
                       }
                   }
                   lineChart.getData().add(series1LineChart);
               }
           }
           else {
                //Da qui in poi è uguale a prima solamente che si parla dell'istogramma e non più del grafico a dispersione
               lineChart.setVisible(false);
               barChart.setVisible(true);
               if(!(provincia.equals("")) && (anno == -1)){
                   for(Regione regione : modello.getRegioni()){
                       for(Provincia prov : regione.getProvince()){
                           if(prov.getNome().equals(provincia)){
                               for(AnnualeDecessi annuale : prov.getDecessi()){
                                   series2BarChart.getData().add(new XYChart.Data<String, Integer>(String.valueOf(annuale.getAnnoNonProperty()), annuale.incidentiStradaliProperty().getValue()));
                               }
                           }
                       }
                   }
                   barChart.getData().add(series2BarChart);
               }
               else if(provincia.equals("") && anno != -1){
                   for(Regione regione : modello.getRegioni()){
                       for(Provincia prov : regione.getProvince()){
                           for(AnnualeDecessi annuale : prov.getDecessi()){
                               if(annuale.getAnnoNonProperty() == anno){
                                   series1BarChart.getData().add(new XYChart.Data<String, Integer>(prov.getNome(), annuale.incidentiStradaliProperty().getValue()));
                               }
                           }
                       }
                   }
                   barChart.getData().add(series1BarChart);
               }
           }
       }
       else if(caso.equals("Tumori")){
           if(grafico.equals("Grafico a dispersione")){
               lineChart.setVisible(true);
               barChart.setVisible(false);
               if(!(provincia.equals("")) && anno == -1){
                   for(Regione regione : modello.getRegioni()){
                       for(Provincia prov : regione.getProvince()){
                           if(prov.getNome().equals(provincia)){
                               for(AnnualeDecessi annuale : prov.getDecessi()){
                                   series2LineChart.getData().add(new XYChart.Data<String, Integer>(String.valueOf(annuale.getAnnoNonProperty()), annuale.tumoriProperty().getValue()));
                               }
                           }
                       }
                   }
                   lineChart.getData().add(series2LineChart);
               }
               else if(provincia.equals("") && anno != -1){
                   for(Regione regione : modello.getRegioni()){
                       for(Provincia prov : regione.getProvince()){
                           for(AnnualeDecessi annuale : prov.getDecessi()){
                               if(annuale.getAnnoNonProperty() == anno){
                                   series1LineChart.getData().add(new XYChart.Data<String, Integer>(prov.getNome(), annuale.tumoriProperty().getValue()));
                               }
                           }
                       }
                   }
                   lineChart.getData().add(series1LineChart);
               }
           }
           else {
               lineChart.setVisible(false);
               barChart.setVisible(true);
               if(!(provincia.equals("")) && anno == -1){
                   for(Regione regione : modello.getRegioni()){
                       for(Provincia prov : regione.getProvince()){
                           if(prov.getNome().equals(provincia)){
                               for(AnnualeDecessi annuale : prov.getDecessi()){
                                   series2BarChart.getData().add(new XYChart.Data<String, Integer>(String.valueOf(annuale.getAnnoNonProperty()), annuale.tumoriProperty().getValue()));
                               }
                           }
                       }
                   }
                   barChart.getData().add(series2BarChart);
               }
               else if(provincia.equals("") && anno != -1){
                   for(Regione regione : modello.getRegioni()){
                       for(Provincia prov : regione.getProvince()){
                           for(AnnualeDecessi annuale : prov.getDecessi()){
                               if(annuale.getAnnoNonProperty() == anno){
                                   series1BarChart.getData().add(new XYChart.Data<String, Integer>(prov.getNome(), annuale.tumoriProperty().getValue()));
                               }
                           }
                       }
                   }
                   barChart.getData().add(series1BarChart);
               }
           }
       }
       else if(caso.equals("Cardiovascolari")){
           if(grafico.equals("Grafico a dispersione")){

               lineChart.setVisible(true);
               barChart.setVisible(false);


               if(!(provincia.equals("")) && anno == -1){

                   for(Regione regione : modello.getRegioni()){
                       for(Provincia prov : regione.getProvince()){
                           if(prov.getNome().equals(provincia)){
                               for(AnnualeDecessi annuale : prov.getDecessi()){
                                   series2LineChart.getData().add(new XYChart.Data<String, Integer>(String.valueOf(annuale.getAnnoNonProperty()), annuale.cardiovascolareProperty().getValue()));
                               }
                           }
                       }
                   }
                   lineChart.getData().add(series2LineChart);


               } else if(provincia.equals("") && anno != -1){

                   for(Regione regione : modello.getRegioni()){
                       for(Provincia prov : regione.getProvince()){
                           for(AnnualeDecessi annuale : prov.getDecessi()){
                               if(annuale.getAnnoNonProperty() == anno){
                                   series1LineChart.getData().add(new XYChart.Data<String, Integer>(prov.getNome(), annuale.cardiovascolareProperty().getValue()));
                               }
                           }
                       }
                   }
                   lineChart.getData().add(series1LineChart);

               }


           }
           else {
               lineChart.setVisible(false);
               barChart.setVisible(true);
               if(!(provincia.equals("")) && anno == -1){
                   for(Regione regione : modello.getRegioni()){
                       for(Provincia prov : regione.getProvince()){
                           if(prov.getNome().equals(provincia)){
                               for(AnnualeDecessi annuale : prov.getDecessi()){
                                   series2BarChart.getData().add(new XYChart.Data<String, Integer>(String.valueOf(annuale.getAnnoNonProperty()), annuale.tumoriProperty().getValue()));
                               }
                           }
                       }
                   }
                   barChart.getData().add(series2BarChart);
               }
               else if(provincia.equals("") && anno != -1){
                   for(Regione regione : modello.getRegioni()){
                       for(Provincia prov : regione.getProvince()){
                           for(AnnualeDecessi annuale : prov.getDecessi()){
                               if(annuale.getAnnoNonProperty() == anno){
                                   series1BarChart.getData().add(new XYChart.Data<String, Integer>(prov.getNome(), annuale.cardiovascolareProperty().getValue()));
                               }
                           }
                       }
                   }
                   barChart.getData().add(series1BarChart);
               }
           }
       }
       else {
           if(grafico.equals("Grafico a dispersione")){
               lineChart.setVisible(true);
               barChart.setVisible(false);
               if(!(provincia.equals("")) && anno == -1){

                   for(Regione regione : modello.getRegioni()){
                       for(Provincia prov : regione.getProvince()){
                           if(prov.getNome().equals(provincia)){
                               for(AnnualeDecessi annuale : prov.getDecessi()){
                                   series2LineChart.getData().add(new XYChart.Data<String, Integer>(String.valueOf(annuale.getAnnoNonProperty()), annuale.contagioseProperty().getValue()));
                               }
                           }
                       }
                   }
                   lineChart.getData().add(series2LineChart);
               }
               else if(provincia.equals("") && anno != -1){
                   for(Regione regione : modello.getRegioni()){
                       for(Provincia prov : regione.getProvince()){
                           for(AnnualeDecessi annuale : prov.getDecessi()){
                               if(annuale.getAnnoNonProperty() == anno){
                                   series1LineChart.getData().add(new XYChart.Data<String, Integer>(prov.getNome(), annuale.contagioseProperty().getValue()));
                               }
                           }
                       }
                   }
                   lineChart.getData().add(series1LineChart);
               }
           } else {
               lineChart.setVisible(false);
               barChart.setVisible(true);
               if(!(provincia.equals("")) && anno == -1){
                   for(Regione regione : modello.getRegioni()){
                       for(Provincia prov : regione.getProvince()){
                           if(prov.getNome().equals(provincia)){
                               for(AnnualeDecessi annuale : prov.getDecessi()){
                                   series2BarChart.getData().add(new XYChart.Data<String, Integer>(String.valueOf(annuale.getAnnoNonProperty()), annuale.contagioseProperty().getValue()));
                               }
                           }
                       }
                   }
                   barChart.getData().add(series2BarChart);
               }
               else if(provincia.equals("") && anno != -1){
                   for(Regione regione : modello.getRegioni()){
                       for(Provincia prov : regione.getProvince()){
                           for(AnnualeDecessi annuale : prov.getDecessi()){
                               if(annuale.getAnnoNonProperty() == anno){
                                   series1BarChart.getData().add(new XYChart.Data<String, Integer>(prov.getNome(), annuale.contagioseProperty().getValue()));
                               }
                           }
                       }
                   }
                   barChart.getData().add(series1BarChart);
               }
           }
       }

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //getGrafico("Tumori", -1,"Varese","Istogramma");
        //getGrafico("Tumori", -1,"Varese","Grafico a dispersione");

        boxAnno.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!(boxCaso.getValue() == null || boxGrafico.getValue() == null)) {
                    if(boxProvincia.getValue() == null){
                        //Se la provincia è null e l'anno non è -1 stampo grafico di quell'anno per ogni provincia
                        if(boxAnno.getValue() != -1)
                            getGrafico(boxCaso.getValue(), boxAnno.getValue(), "", boxGrafico.getValue());
                    }
                    else{
                        //Se la provincia non è null ed è --
                        if(boxProvincia.getValue().equals("--")){
                            //Se l'anno non è -1 stampo il grafico di quell'anno per ogni provincia
                            if(boxAnno.getValue() != -1) {
                                getGrafico(boxCaso.getValue(), boxAnno.getValue(), "", boxGrafico.getValue());
                            }
                        }
                        //Se la provincia non è null e nemmeno --
                        else{
                            //Se l'anno è -1 allora stampo il grafico di quella provincia per ogni anno
                            if(boxAnno.getValue() == -1){
                                getGrafico(boxCaso.getValue(), boxAnno.getValue(), boxProvincia.getValue(), boxGrafico.getValue());
                            }
                        }
                    }
                }
            }
        });

        boxCaso.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //Se il valore di Caso non è null allora posso eseguire una ricerca
                if(boxGrafico.getValue() != null) {
                    if(boxAnno.getValue() == null && boxProvincia.getValue() == null){}
                    else{
                        if (boxAnno.getValue() == null && boxProvincia.getValue() != null) {
                            //Se l'anno è null e la provincia non è "--" allora stampo il grafico della regione per ogni anno
                            if (!boxProvincia.getValue().equals("--"))
                                getGrafico(boxCaso.getValue(), -1, boxProvincia.getValue(), boxGrafico.getValue());
                        }
                        else {
                            //Se l'anno non è null ma è -1
                            if (boxAnno.getValue() == -1) {
                                //Se la provincia non è -- allora stampo il grafico di quella regione per ogni anno
                                if (!boxProvincia.getValue().equals("--")) {
                                    getGrafico(boxCaso.getValue(), -1, boxProvincia.getValue(), boxGrafico.getValue());
                                }
                            }
                            //Se l'anno non è null e nemmeno -1
                            else {
                                //Se la provincia è -- allora stampo il grafico di ogni provincia per quell'anno
                                if (boxProvincia.getValue().equals("--")) {
                                    getGrafico(boxCaso.getValue(), boxAnno.getValue(), "", boxGrafico.getValue());
                                }
                            }
                        }
                        if (boxProvincia.getValue() == null && boxAnno.getValue() != null) {
                            //Se la provincia è null e l'anno non è -1 stampo grafico di quell'anno per ogni provincia
                            if (boxAnno.getValue() != -1)
                                getGrafico(boxCaso.getValue(), boxAnno.getValue(), "", boxGrafico.getValue());
                        }
                        else {
                            //Se la provincia non è null ed è --
                            if (boxProvincia.getValue().equals("--")) {
                                //Se l'anno non è -1 stampo il grafico di quell'anno per ogni provincia
                                if (boxAnno.getValue() != -1) {
                                    getGrafico(boxCaso.getValue(), boxAnno.getValue(), "", boxGrafico.getValue());
                                }
                            }
                            //Se la provincia non è null e nemmeno --
                            else {
                                //Se l'anno è -1 allora stampo il grafico di quella provincia per ogni anno
                                if (boxAnno.getValue() == -1) {
                                    getGrafico(boxCaso.getValue(), boxAnno.getValue(), boxProvincia.getValue(), boxGrafico.getValue());
                                }
                            }
                        }
                    }
                }
            }
        });

        boxGrafico.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(boxCaso.getValue() != null){
                    if(boxAnno.getValue() == null && boxProvincia.getValue() == null){}
                    else{
                        if (boxAnno.getValue() == null && boxProvincia.getValue() != null) {
                            //Se l'anno è null e la provincia non è "--" allora stampo il grafico della regione per ogni anno
                            if (!boxProvincia.getValue().equals("--"))
                                getGrafico(boxCaso.getValue(), -1, boxProvincia.getValue(), boxGrafico.getValue());
                        }
                        else {
                            //Se l'anno non è null ma è -1
                            if (boxAnno.getValue() == -1) {
                                //Se la provincia non è -- allora stampo il grafico di quella regione per ogni anno
                                if (!boxProvincia.getValue().equals("--")) {
                                    getGrafico(boxCaso.getValue(), -1, boxProvincia.getValue(), boxGrafico.getValue());
                                }
                            }
                            //Se l'anno non è null e nemmeno -1
                            else {
                                //Se la provincia è -- allora stampo il grafico di ogni provincia per quell'anno
                                if (boxProvincia.getValue().equals("--")) {
                                    getGrafico(boxCaso.getValue(), boxAnno.getValue(), "", boxGrafico.getValue());
                                }
                            }
                        }
                        if (boxProvincia.getValue() == null && boxAnno.getValue() != null) {
                            //Se la provincia è null e l'anno non è -1 stampo grafico di quell'anno per ogni provincia
                            if (boxAnno.getValue() != -1)
                                getGrafico(boxCaso.getValue(), boxAnno.getValue(), "", boxGrafico.getValue());
                        }
                        else {
                            //Se la provincia non è null ed è --
                            if (boxProvincia.getValue().equals("--")) {
                                //Se l'anno non è -1 stampo il grafico di quell'anno per ogni provincia
                                if (boxAnno.getValue() != -1) {
                                    getGrafico(boxCaso.getValue(), boxAnno.getValue(), "", boxGrafico.getValue());
                                }
                            }
                            //Se la provincia non è null e nemmeno --
                            else {
                                //Se l'anno è -1 allora stampo il grafico di quella provincia per ogni anno
                                if (boxAnno.getValue() == -1) {
                                    getGrafico(boxCaso.getValue(), boxAnno.getValue(), boxProvincia.getValue(), boxGrafico.getValue());
                                }
                            }
                        }
                    }
                }
            }
        });

        boxProvincia.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!(boxCaso.getValue() == null || boxGrafico.getValue() == null)) {
                    if(boxAnno.getValue() == null){
                        //Se l'anno è null e la provincia non è "--" allora stampo il grafico della regione per ogni anno
                        if(!boxProvincia.getValue().equals("--"))
                            getGrafico(boxCaso.getValue(), -1, boxProvincia.getValue(), boxGrafico.getValue());
                    }
                    else{
                        //Se l'anno non è null ma è -1
                        if(boxAnno.getValue() == -1){
                            //Se la provincia non è -- allora stampo il grafico di quella regione per ogni anno
                            if(!boxProvincia.getValue().equals("--")) {
                                getGrafico(boxCaso.getValue(), -1, boxProvincia.getValue(), boxGrafico.getValue());
                            }
                        }
                        //Se l'anno non è null e nemmeno -1
                        else{
                            //Se la provincia è -- allora stampo il grafico di ogni provincia per quell'anno
                            if(boxProvincia.getValue().equals("--")){
                                getGrafico(boxCaso.getValue(), boxAnno.getValue(), "", boxGrafico.getValue());
                            }
                        }
                    }
                }
            }
        });

    }
}
