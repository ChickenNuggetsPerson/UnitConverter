import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.SysexMessage;

public class Converter {

    public static void main(String[] args) throws Exception {
        Converter converter = new Converter();

        System.out.println(converter.convert(Converter.MeasureType.Kilometer, Converter.MeasureType.Mile, 1));
    }



    public enum MeasureType {
        // Dist
        In, Ft, Yrd, Mile, 
        Cm, Meter, Kilometer,

        // Weight
        Lb,
        Kilogram,

        // Temp
        C,
        F,
        K,

        // Volume
        Gallon, Cup, Tablespoon, Teaspoon,
        Liter, Mililiter, CubicMeter
    }


    public class ConvertConfig {
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
        configStorage.add(new ConvertConfig(MeasureType.Ft, MeasureType.In, 12));
        configStorage.add(new ConvertConfig(MeasureType.Yrd, MeasureType.Ft, 3));
        configStorage.add(new ConvertConfig(MeasureType.Mile, MeasureType.Ft, 5284));
        
        configStorage.add(new ConvertConfig(MeasureType.In, MeasureType.Cm, 2.54));
        configStorage.add(new ConvertConfig(MeasureType.Meter, MeasureType.Cm, 100));
        configStorage.add(new ConvertConfig(MeasureType.Kilometer, MeasureType.Meter, 1000));
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
        System.out.println(this.workingNumber);

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
        //System.out.print("Avaliable: ");
        //System.out.println(avaliable.size());
        for (int i = 0; i < avaliable.size(); i++) {
            System.out.println(avaliable.get(i).ratio);

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

    private double workingNumber;
    private MeasureType workingType;
    private MeasureType inputType;
    private MeasureType outputType;

    public double convert(MeasureType inputType, MeasureType outputType, double input) {
        this.workingNumber = input;
        this.workingType = inputType;
        this.inputType = inputType;
        this.outputType = outputType;

        boolean result = recursiveConvert();
        System.out.println(result);
        return workingNumber;
    }   


}
