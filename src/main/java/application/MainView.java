package application;



import java.util.HashMap;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class MainView {

        LineChart<Number, Number> lineChart;
        NumberAxis xAxis;
        NumberAxis yAxis;
        Map<Double, Double> temperatureMap;

        private int thickness = 20; //thickness in cm 
        private double thermalConductivity = 2.7; // heat transfer coeficient
        private int temperatureInside = 55; // temperature inside side in Celcius
        private int temperatureOutside = 20; // temperature outside side in Celcius
        private int humidity = 80; // relative humidity in %   
        private double convectionHeatTransferCoefficient = 15;   // calculated from website - 370.4 
        private double density = 110; // density of material, for wool 110
        private double specificHeat = 1600; //specific heat capacity of material, for wool = 1600  

        private final String methods[] = {"Convection and conduction equation", "Temperature distribution" };  
        private String selectedMethod = methods[0];

        //visual setttings
        private Insets HorizontalPadding = new Insets(0, 8, 0, 8);
        private Insets bigPadding = new Insets(20);
        private int labelMinHeight = 30;


        public Parent getView() {
            BorderPane layout = new BorderPane();
            
              // create the x and y axis and Line chart
            xAxis= new NumberAxis(0, 30, 2);
            xAxis.setLabel("insulation [cm]");
            yAxis = new NumberAxis(-10, 20, 2);
            yAxis.setLabel("temperature [°C]");
            lineChart = new LineChart<>(xAxis, yAxis);
            lineChart.setTitle("");
            lineChart.setVerticalZeroLineVisible(false);
            lineChart.setHorizontalZeroLineVisible(false);
    
            layout.setCenter(lineChart);
    
            //VBox with thickness slider and parameters
            VBox parametersVBox = new VBox();   

            //Slider for thickness    
            Label textSlider1 = new Label("Insulation cm: ");
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
            
            Label labelTemp1 = new Label("Temperature inside [°C]");
            labelTemp1.setPadding(HorizontalPadding);
            labelTemp1.setPrefHeight(labelMinHeight);
            TextField textFieldTemp1 = new TextField(String.valueOf(temperatureInside));
            textFieldTemp1.setPrefHeight(labelMinHeight);
            textFieldTemp1.textProperty().addListener((change, oldValue, newValue) -> {
                if(!newValue.matches("-?\\d*")){
                    textFieldTemp1.setText(oldValue);

                } else if(newValue.matches("-?")) {
                } else {
                    try {
                        this.temperatureInside = Integer.valueOf(newValue.toString());
                    } catch (NumberFormatException e) {                   
                        textFieldTemp1.setText(oldValue);
                    }
                }    
            });

            Label labelTemp2 = new Label("Temperature outside [°C]");
            labelTemp2.setPadding(HorizontalPadding);
            labelTemp2.setPrefHeight(labelMinHeight);
            TextField textFieldTemp2 = new TextField(String.valueOf(temperatureOutside));
            textFieldTemp2.setPrefHeight(labelMinHeight);
            textFieldTemp2.textProperty().addListener((change, oldValue, newValue) -> {
                if(!newValue.matches("-?\\d*")){
                    textFieldTemp2.setText(oldValue);

                } else if(newValue.matches("-?")) {
                } else {
                    try {
                        this.temperatureOutside = Integer.valueOf(newValue.toString());
                    } catch (NumberFormatException e) {                   
                        textFieldTemp2.setText(oldValue);
                    }
                } 
            });

            Label labelHumidity = new Label("Humidity [%]");
            labelHumidity.setPadding(HorizontalPadding);
            labelHumidity.setPrefHeight(labelMinHeight);
            TextField textFieldHumidity = new TextField(String.valueOf(humidity));
            textFieldHumidity.setPrefHeight(labelMinHeight);
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

            Label labelThermalConductivity = new Label("Thermal conductivity K ");
            labelThermalConductivity.setPadding(HorizontalPadding);
            labelThermalConductivity.setPrefHeight(labelMinHeight);
            TextField textFieldThermalConductivity = new TextField(String.valueOf(thermalConductivity));
            textFieldThermalConductivity.setPrefHeight(labelMinHeight);
            textFieldThermalConductivity .setPadding(HorizontalPadding);
            textFieldThermalConductivity.textProperty().addListener((change, oldValue, newValue) -> {
                if(!newValue.matches("-?\\d*.?\\d*")){
                    textFieldThermalConductivity.setText(oldValue);

                } else if(newValue.matches("-?\\d?.?")) {
                } else {
                    try {
                        this.thermalConductivity = Double.valueOf(newValue.toString());
                    } catch (NumberFormatException e) {                   
                        textFieldThermalConductivity.setText(oldValue);
                    }
                } 
            });

            Label labelConvHeatCoefficient = new Label("Convection heat transf. coef. h");
            labelConvHeatCoefficient.setPadding(HorizontalPadding);
            labelConvHeatCoefficient.setPrefHeight(labelMinHeight);
            TextField textFieldConvHeatCoefficient = new TextField(String.valueOf(convectionHeatTransferCoefficient));
            textFieldConvHeatCoefficient.setPrefHeight(labelMinHeight);
            textFieldConvHeatCoefficient .setPadding(HorizontalPadding);
            textFieldConvHeatCoefficient.textProperty().addListener((change, oldValue, newValue) -> {
                if(!newValue.matches("\\d*.?\\d*")){
                    textFieldConvHeatCoefficient.setText(oldValue);

                } else if(newValue.matches("\\d?.?")) {
                } else {
                    try {
                        this.convectionHeatTransferCoefficient = Double.valueOf(newValue.toString());
                    } catch (NumberFormatException e) {                   
                        textFieldConvHeatCoefficient.setText(oldValue);
                    }
                } 
            });

       
            Label labelDensity = new Label("Density ");
            labelDensity.setPadding(HorizontalPadding);
            labelDensity.setPrefHeight(labelMinHeight);
            labelDensity.setVisible(false);
            TextField textFieldDensity = new TextField(String.valueOf(density));
            textFieldDensity.setPrefHeight(labelMinHeight);
            textFieldDensity .setPadding(HorizontalPadding);
            textFieldDensity.textProperty().addListener((change, oldValue, newValue) -> {
                if(!newValue.matches("\\d*.?\\d*")){
                    textFieldDensity.setText(oldValue);

                } else if(newValue.matches("\\d?.?")) {
                } else {
                    try {
                        this.density = Double.valueOf(newValue.toString());
                    } catch (NumberFormatException e) {                   
                        textFieldDensity.setText(oldValue);
                    }
                } 
            });
            textFieldDensity.setVisible(false);

            Label labelSpecificHeat = new Label("Specific heat ");
            labelSpecificHeat.setPadding(HorizontalPadding);
            labelSpecificHeat.setPrefHeight(labelMinHeight);
            TextField textSpecificHeat = new TextField(String.valueOf(specificHeat));
            textSpecificHeat.setPrefHeight(labelMinHeight);
            textSpecificHeat .setPadding(HorizontalPadding);
            textSpecificHeat.textProperty().addListener((change, oldValue, newValue) -> {
                if(!newValue.matches("\\d*.?\\d*")){
                    textSpecificHeat.setText(oldValue);

                } else if(newValue.matches("\\d?.?")) {
                } else {
                    try {
                        this.specificHeat = Double.valueOf(newValue.toString());
                    } catch (NumberFormatException e) {                   
                        textSpecificHeat.setText(oldValue);
                    }
                } 
            });

            GridPane gridTextParameters = new GridPane();
            gridTextParameters.setPadding(bigPadding);
            gridTextParameters.setVgap(10);
            gridTextParameters.setHgap(10);

            gridTextParameters.add(labelTemp1, 0, 0);
            gridTextParameters.add(textFieldTemp1, 1, 0);

            gridTextParameters.add(labelTemp2, 0, 1);
            gridTextParameters.add(textFieldTemp2, 1, 1);

            gridTextParameters.add(labelHumidity, 0, 2);
            gridTextParameters.add(textFieldHumidity, 1, 2);

            gridTextParameters.add(labelThermalConductivity, 0, 3);
            gridTextParameters.add(textFieldThermalConductivity, 1, 3);

            gridTextParameters.add(labelConvHeatCoefficient, 0, 4);
            gridTextParameters.add(textFieldConvHeatCoefficient, 1, 4);

            gridTextParameters.add(labelDensity, 0, 4);
            gridTextParameters.add(textFieldDensity, 1, 4);

            gridTextParameters.add(labelSpecificHeat, 0, 5);
            gridTextParameters.add(textSpecificHeat, 1, 5);


            //Combo box for method
            Label comboBoxLabel = new Label("Select method for calculation");
           
            ComboBox<String> comboBoxMethods = new ComboBox<>(FXCollections.observableArrayList(methods));
            comboBoxMethods.getSelectionModel().selectFirst();
            comboBoxMethods.setOnAction(event -> {
                selectedMethod = comboBoxMethods.getValue();

                //set visibility of labels and textDields
                if(selectedMethod.equals(methods[0])){ //
                    labelThermalConductivity.setVisible(selectedMethod.equals(methods[0]));
                    textFieldThermalConductivity.setVisible(selectedMethod.equals(methods[0]));

                    labelDensity.setVisible(selectedMethod.equals(methods[1]));
                    textFieldDensity.setVisible(selectedMethod.equals(methods[1]));
                    labelSpecificHeat.setVisible(selectedMethod.equals(methods[1]));
                    textSpecificHeat.setVisible(selectedMethod.equals(methods[1]));                    
                }
            });

            Label labelCalculatedDewPoint = new Label("Calculate dew point and temperature map for insulated air conduct.");
            labelCalculatedDewPoint.setPadding(new Insets(10));
            labelCalculatedDewPoint.setMinSize(200, 20);

            Button calculateButton = new Button(" Calculate ");
            calculateButton.setFont(new Font(16));
            calculateButton.setMinSize(200, 50);
            calculateButton.setOnAction((event) -> {
                xAxis.setLowerBound(0);
                xAxis.setUpperBound(thickness + 1);
                yAxis.setLowerBound(temperatureOutside); 
                yAxis.setUpperBound(temperatureInside);
                updateChart();
                labelCalculatedDewPoint.setText("Dew point calculated at: " + Calculator.dewPointTemperate(humidity, temperatureInside) + "[°C]")   ;
            });

            
            GridPane gridButton = new GridPane();
           // gridButton.setPadding(bigPadding);
            gridButton.setVgap(10);
            gridButton.setHgap(50);
            gridButton.add(comboBoxLabel, 0, 0);
            gridButton.add(comboBoxMethods, 1, 0);
            gridButton.add(calculateButton, 1, 1);
            gridButton.add(labelCalculatedDewPoint, 1, 4);


            
            BorderPane bottomBorderPane = new BorderPane();
            bottomBorderPane.setLeft(gridTextParameters);
            bottomBorderPane.setCenter(gridButton);

            
    
            parametersVBox.getChildren().addAll(borderPaneSliderThicknes,  bottomBorderPane);
    
            layout.setTop(parametersVBox); 
    
            return layout;
        }


        private void updateChart() {
            XYChart.Series<Number, Number> temperatureChart = new XYChart.Series<>();          

            temperatureChart.setName("temperature map");     
            temperatureMap = methodSelector(selectedMethod);
            temperatureMap.entrySet().stream().forEach(pair -> temperatureChart.getData().add(new XYChart.Data<Number, Number>(pair.getKey(), pair.getValue())) );  
           
            XYChart.Series<Number, Number> dewPointChart = new XYChart.Series<>();
            dewPointChart.setName("dew point");
            double dewPointTemperature = Calculator.dewPointTemperate(humidity, temperatureInside);
            dewPointChart.getData().add(new XYChart.Data<Number, Number>(0, dewPointTemperature));
            dewPointChart.getData().add(new XYChart.Data<Number, Number>(thickness, dewPointTemperature));
            
           
            lineChart.getData().clear();
            lineChart.getData().add(temperatureChart);
            temperatureChart.getNode().setStyle("-fx-stroke: orange;");
            lineChart.getData().add(dewPointChart);
            dewPointChart.getNode().setStyle("-fx-stroke: lightblue;");    
        }

        private Map<Double, Double> methodSelector(String method) {
            int methodNumber = 1;
            Map<Double, Double> temperatureMap = new HashMap<>();    
            for(int i = 0; i < this.methods.length; i++) {
                if(this.methods[i].equals(method)) {
                    methodNumber = i + 1;
                }
            }
            switch(methodNumber) {
                case 1 : temperatureMap =  Calculator.calculateTemperatureMapMethod1(thickness, temperatureInside, temperatureOutside, thermalConductivity, convectionHeatTransferCoefficient);
                case 2 : temperatureMap = Calculator.calculateTemperatureMapMethod2(thickness, temperatureInside, temperatureOutside, thermalConductivity, density, specificHeat);
            }
            return temperatureMap;
        }

        
}