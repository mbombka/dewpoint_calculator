package application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Calculator {

    public final static Double theramlResistanceRsi = 0.13 ; // Thermal resistance Rsi for horizontal heat flow (0.10 for upwards heat flow, 0.17 for downwards heat flow)
    public final static Double thicknessMapResolution = 1.0; // resolution for calculation of Temperature map 
    //outside - colder side
    //inside - hot side

    //calculate dewPoint at given temperature and humidity
    public static double dewPointTemperate(double humidity, int temperature) {
        double dewpoint = Math.round(Math.pow((humidity/100), 0.125)) * (112 + 0.9 * temperature) + (0.1 * temperature) - 112;
        
        return  Math.round(dewpoint * 10)/10.0;
    }

    //calculate temperature on inside(where the heat source is)  of partition, given air temperature on both sides, and thermal coefficient. 
    public static double partitionTemperatureStep(double insideTemperature, double outsideTemperature, double thermalCoefficient, double partitionThickness) {
        double heatConductivity = 1 / (partitionThickness * 100 / thermalCoefficient); //partition thickness is in [cm], but unit is [m] thats why *100
        double result = insideTemperature - (heatConductivity * (insideTemperature - outsideTemperature)* theramlResistanceRsi);
        result = Math.round(result * 100) / 100.0;
        return result;
    }

    //calculate map of temperature for given thickness of material
    public static Map<Double, Double> calculateTemperatureMap(int thickness, int insideTemperature, int outsideTemperature, double thermalCoefficient) {
        Map<Double, Double> temperatureMap = new HashMap<>();        
        int steps = (int) Math.round(thickness/thicknessMapResolution);
        Double[] temperatureArray = new Double[steps];
        temperatureArray[0] = (double) outsideTemperature; 
        temperatureArray[steps - 1] = partitionTemperatureStep(insideTemperature, outsideTemperature, thermalCoefficient, thicknessMapResolution);
        
        //fill array with temporary rounded values
        for(int i = 1; i < steps; i ++ ) {
            temperatureArray[i] = outsideTemperature +  ((double)(temperatureArray[steps - 1] - temperatureArray[0]) / steps) * i;  
        }

        //make actual calculations
        for(int i = 1; i < steps - 1; i ++ ) {
             temperatureArray[i] = partitionTemperatureStep(temperatureArray[i], temperatureArray[i - 1], thermalCoefficient, thicknessMapResolution);
        }

        //save all temperatures to map
        for (int i = 0; i < steps; i++) {
            temperatureMap.put(i * thicknessMapResolution, temperatureArray[i]);
        }

        return temperatureMap;
    }







    
}
