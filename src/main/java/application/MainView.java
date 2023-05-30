package application;


import java.util.HashMap;
import java.util.Map;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class MainView {

  
   
        private int thickness = 10; //thickness in cm 
        private double thermalCoefficient = 0.0; // heat transfer coeficient
        private int temperature1 = 0; // temperature in 1 side in Celcius
        private int temperature2 = 0; // temperature in 2 side in Celcius
        private double humidity = 0.0; // relative humidity in %

        LineChart<Number, Number> lineChart;
        Map<Double, Double> temperatureMap;

        //setttings
        private Insets HorizontalPadding = new Insets(0, 8, 0, 8);
        private Insets bigPadding = new Insets(20);


        public MainView() {
            
        }

        public Parent getView() {
            BorderPane layout = new BorderPane();
            
              // create the x and y axis and Line chart
            NumberAxis xAxis= new NumberAxis(0, 30, 2);
            NumberAxis yAxis = new NumberAxis();
            lineChart = new LineChart<>(xAxis, yAxis);
            lineChart.setTitle("Calculator");
    
            layout.setCenter(lineChart);
    
            //VBox with thickness slider and parameters
            VBox parametersVBox = new VBox();   

            //Slider for thickness    
            Label textSlider1 = new Label("Thickness in cm: ");
            textSlider1.setFont(new Font(20));
            Slider thicknesSlider = new Slider(1, 100, 10);
            thicknesSlider.setShowTickLabels(true);
            thicknesSlider.setMajorTickUnit(1);
            thicknesSlider.setBlockIncrement(10);
            thicknesSlider.setSnapToTicks(true);
            Label ValueThicknesSlider = new Label(String.valueOf(thickness) + " cm");
            ValueThicknesSlider.setFont(new Font(20));
            thicknesSlider.valueProperty().addListener((observable, oldValue, newValue) -> 
            {   
                ValueThicknesSlider.setText(String.valueOf(newValue.intValue()) + " cm");
                thickness = newValue.intValue();
    
            });

            BorderPane borderPaneSliderThicknes = new BorderPane();
            borderPaneSliderThicknes.setPadding(new Insets(20, 20, 20, 20));
            borderPaneSliderThicknes.setMinHeight(20);  
            borderPaneSliderThicknes.setLeft(textSlider1);    
            borderPaneSliderThicknes.setCenter(thicknesSlider);
            BorderPane.setMargin(thicknesSlider, new Insets(10));   
            borderPaneSliderThicknes.setRight(ValueThicknesSlider);
      
            //Text Parameters
            
            Label labelTemp1 = new Label("Temperature 1 [C]");
            labelTemp1.setPadding(HorizontalPadding);
            TextField textFieldTemp1 = new TextField();
            textFieldTemp1.textProperty().addListener((change, oldValue, newValue) -> {
                if(!newValue.matches("-?\\d*")){
                    textFieldTemp1.setText(oldValue);
                } else if(newValue.matches("-?")) {
                } else {
                    try {
                        this.temperature1 = Integer.valueOf(newValue.toString());
                    } catch (NumberFormatException e) {                   
                        textFieldTemp1.setText(oldValue);
                    }
                }    
            });

            Label labelTemp2 = new Label("Temperature 2 [C]");
            labelTemp2.setPadding(HorizontalPadding);
            TextField textFieldTemp2 = new TextField();
            textFieldTemp2.textProperty().addListener((change, oldValue, newValue) -> {
                if(!newValue.matches("-?\\d*")){
                    textFieldTemp2.setText(oldValue);
                } else if(newValue.matches("-?")) {
                } else {
                    try {
                        this.temperature2 = Integer.valueOf(newValue.toString());
                    } catch (NumberFormatException e) {                   
                        textFieldTemp2.setText(oldValue);
                    }
                } 
            });

            Label labelHumidity = new Label("Humidity [%]");
            labelHumidity.setPadding(HorizontalPadding);
            TextField textFieldHumidity = new TextField();
            textFieldHumidity.textProperty().addListener((change, oldValue, newValue) -> {
                if(!newValue.matches("-?\\d*")){
                    textFieldHumidity.setText(oldValue);
                } else if(newValue.matches("-?")) {
                } else {
                    try {
                        this.humidity = Integer.valueOf(newValue.toString());
                    } catch (NumberFormatException e) {                   
                        textFieldHumidity.setText(oldValue);
                    }
                } 
            });
           
            HBox textParametersHBox1 = new HBox();
            textParametersHBox1.setPadding(bigPadding);
            textParametersHBox1.setMinHeight(20);
            textParametersHBox1.getChildren().addAll(labelTemp1, textFieldTemp1, labelTemp2, textFieldTemp2,
                            labelHumidity, textFieldHumidity);

            Label labelThermalCoefficient = new Label("Thermal Resistance [] ");
            labelThermalCoefficient.setPadding(HorizontalPadding);
            TextField textFieldThermalCoefficient = new TextField();
            textFieldThermalCoefficient .setPadding(HorizontalPadding);
            textFieldThermalCoefficient.textProperty().addListener((change, oldValue, newValue) -> {
                if(!newValue.matches("-?(0.)?\\d*")){
                    textFieldThermalCoefficient.setText(oldValue);
                
                } else if(newValue.matches("-?0?.?")) {
                } else {
                    try {
                        this.thermalCoefficient = Double.valueOf(newValue.toString());
                    } catch (NumberFormatException e) {                   
                        textFieldThermalCoefficient.setText(oldValue);
                    }
                } 
            });

            Button calculateButton = new Button(" Calculate ");
            calculateButton.setFont(new Font(16));
            calculateButton.minHeight(30);
            calculateButton.minWidth(80);
            calculateButton.setOnAction((event) -> updateChart());

            
            HBox textParametersHBox2 = new HBox();
            textParametersHBox2.setPadding(bigPadding);
            textParametersHBox2.setMinHeight(20);
            textParametersHBox2.getChildren().addAll(labelThermalCoefficient, textFieldThermalCoefficient, calculateButton);     
            
            BorderPane bottomBorderPane = new BorderPane();
            bottomBorderPane.setLeft(textParametersHBox2);
            bottomBorderPane.setCenter(calculateButton);


    
            parametersVBox.getChildren().addAll(borderPaneSliderThicknes, textParametersHBox1, bottomBorderPane);
    
            layout.setTop(parametersVBox); 
    
    
            return layout;
        }


        private void updateChart() {
            XYChart.Series<Number, Number> temperatureChart = new XYChart.Series<>();
            temperatureChart.setName("temperature map");
            temperatureMap = Calculator.calculateTemperatureMap(thickness, temperature2, temperature1, thermalCoefficient);
            temperatureMap.entrySet().stream().forEach(pair -> temperatureChart.getData().add(new XYChart.Data<Number, Number>(pair.getKey(), pair.getValue())) );  
           
    
            lineChart.getData().clear();
            lineChart.getData().add(temperatureChart);
           
            
        }
}
