import java.util.ArrayList;
import java.util.List;

public class Converter {

    public static void main(String[] args) throws Exception {
        Converter converter = new Converter();

        double result = converter.convert(MeasureType.Meter, MeasureType.In, 1);
        if (!Double.isNaN(result)) {
            System.out.println(result);
        } else {
            System.out.println("Could not compute");
        }
        
    }


    private class ConvertConfig {
        public MeasureType input;  // Ft   In
        public MeasureType output; // In   Ft
        public double ratio;       // 12   1/12
        public ConvertConfig(MeasureType input, MeasureType output) {
            this.input = input;
            this.output = output;
        }
        public ConvertConfig(MeasureType input, MeasureType output, double ratio) {
            this.input = input;
            this.output = output;
            this.ratio = ratio;
        }
    }

    private List<ConvertConfig> configStorage = new ArrayList<>();
    private List<ConvertConfig> editHistory = new ArrayList<>();

    Converter() {

        // Distances
        configStorage.add(new ConvertConfig(MeasureType.Ft, MeasureType.In, 12));
        configStorage.add(new ConvertConfig(MeasureType.Yrd, MeasureType.Ft, 3));
        configStorage.add(new ConvertConfig(MeasureType.Mile, MeasureType.Ft, 5284));
        
        configStorage.add(new ConvertConfig(MeasureType.In, MeasureType.Cm, 2.54));
        configStorage.add(new ConvertConfig(MeasureType.Meter, MeasureType.Cm, 100));
        configStorage.add(new ConvertConfig(MeasureType.Kilometer, MeasureType.Meter, 1000));

        // Weight
        configStorage.add(new ConvertConfig(MeasureType.Lb, MeasureType.Gram, 453.592));
        configStorage.add(new ConvertConfig(MeasureType.Kilogram, MeasureType.Gram, 1000));

        // Volume
        configStorage.add(new ConvertConfig(MeasureType.Gallon, MeasureType.Quart, 4));
        configStorage.add(new ConvertConfig(MeasureType.Quart, MeasureType.Pint, 2));
        configStorage.add(new ConvertConfig(MeasureType.Pint, MeasureType.Cup, 2));
        configStorage.add(new ConvertConfig(MeasureType.Cup, MeasureType.Tablespoon, 16));
        configStorage.add(new ConvertConfig(MeasureType.Tablespoon, MeasureType.Teaspoon, 3));
        configStorage.add(new ConvertConfig(MeasureType.FluidOunce, MeasureType.Tablespoon, 2));

        configStorage.add(new ConvertConfig(MeasureType.Gallon, MeasureType.Liter, 3.78541));
        configStorage.add(new ConvertConfig(MeasureType.Liter, MeasureType.Mililiter, 1000));

        configStorage.add(new ConvertConfig(MeasureType.Gallon, MeasureType.CubicInch, 231));
        configStorage.add(new ConvertConfig(MeasureType.CubicFoot, MeasureType.CubicInch, 1728));
        configStorage.add(new ConvertConfig(MeasureType.CubicMeter, MeasureType.CubicFoot, 35.3147));

    }

    // Determine if we have already made that change
    private boolean changeInHistory(ConvertConfig config) {
        for (int i = 0; i < this.editHistory.size(); i++) {
            // Normal ratio
            if (config.input == this.editHistory.get(i).input && config.output == this.editHistory.get(i).output) { 
                return true;
            }
            // Reciprocal ratio
            if (config.input == this.editHistory.get(i).output && config.output == this.editHistory.get(i).input) { 
                return true;
            }
        }
        return false;
    }    
    // Find the linked config to the working type
    private List<ConvertConfig> determineAvaliableConfigs() {
        List<ConvertConfig> available = new ArrayList<>();
        for (int i = 0; i < configStorage.size(); i++) {
            if (!changeInHistory(configStorage.get(i))) {
                if (configStorage.get(i).input == this.workingType || configStorage.get(i).output == this.workingType) {
                    available.add(configStorage.get(i));
                }
            }
        }
        return available;
    }

    // Apply the config and store in history
    private void applyConfig(ConvertConfig config) {
        if (this.workingType == config.input) {
            this.workingNumber = this.workingNumber * config.ratio;
            editHistory.add(config);
            this.workingType = config.output;
        } else {
            this.workingNumber = this.workingNumber * ( 1 / config.ratio );
            editHistory.add(config);
            this.workingType = config.input;
        }
    }

    // Undo the config
    private void undoConfig(ConvertConfig config) {
        if (this.workingType == config.input) {
            this.workingNumber = this.workingNumber / config.ratio;
            editHistory.remove(editHistory.size() - 1);
            this.workingType = config.output;
        } else {
            this.workingNumber = this.workingNumber / ( 1 / config.ratio );
            editHistory.remove(editHistory.size() - 1);
            this.workingType = config.input;
        }
        
    }

    private boolean recursiveConvert() {
        // Check if the config is in the storage
        for (int i = 0; i < this.configStorage.size(); i++) {
            // Normal ratio
            if (this.workingType == this.configStorage.get(i).input && this.outputType == this.configStorage.get(i).output) { 
                applyConfig(new ConvertConfig(this.workingType, this.outputType, this.configStorage.get(i).ratio));
                return true;
            }
            // Reciprocal ratio
            if (this.workingType == this.configStorage.get(i).output && this.outputType == this.configStorage.get(i).input) { 
                applyConfig(new ConvertConfig(this.workingType, this.outputType, 1.00 / this.configStorage.get(i).ratio));
                return true;
            }
        }

        // Get Avaliable Options
        List<ConvertConfig> avaliable = determineAvaliableConfigs();
        for (int i = 0; i < avaliable.size(); i++) {
            applyConfig(avaliable.get(i));
            boolean result = recursiveConvert();
            if (!result) {
                undoConfig(avaliable.get(i));
                continue;
            }
            return true;
        }
    
        // If it can not get a stored config, start reciprocating
        return false;
    }


    private double roundVal(double input, int decimalPlaces) {
        double power = Math.pow(10, decimalPlaces);
        return Math.round(input * power) / power;
    }

    
    private double workingNumber;
    private MeasureType workingType;
    private MeasureType outputType;

    public double convert(MeasureType inputType, MeasureType outputType, double input) {
        return convert(inputType, outputType, input, 3);
    }   
    public double convert(MeasureType inputType, MeasureType outputType, double input, int decimalPlaces) {
        this.workingNumber = input;
        this.workingType = inputType;
        this.outputType = outputType;

        boolean result = recursiveConvert();
        if (result) {
            return roundVal(this.workingNumber, decimalPlaces);
        }
        return Double.NaN;
    }   
}
