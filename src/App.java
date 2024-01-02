import java.util.Scanner;

public class App {

    /*
     *  Todo:
     *      X - Converter
     *      X - Unit String Parser
     *        - User Interface
     *        - History System
     *        -     Clear History
     *        -     Display History
     *      
     */



    public static void main(String[] args) throws Exception {
        ArgsParser parser = new ArgsParser();
        parser.parse(args);

        App app = new App();

        while (true) {
            app.singleConvert();
            System.out.println("Press Enter");
            try{System.in.read();} catch(Exception e){}
            System.out.println("\n\n\n\n");
        }

    }

    public String addPadding(String str, int width) {
        String newStr = str;
        for (int i = str.length(); i < width; i++) {
            newStr += " ";
        }
        return newStr;
    }
    public void renderState(String inType, String outType, String topLine, String bottomLine) {
        System.out.println("--------------------------");
        System.out.println("| " + this.addPadding(topLine, 23) + "|");
        System.out.println("--------------------------");
        System.out.print("| ");

        System.out.print(this.addPadding(inType, 11));

        System.out.print("| ");

        if (inType.length() > 11) {
            System.out.print(this.addPadding(outType, 10 - (inType.length() - 11)));
        } else {
            System.out.print(this.addPadding(outType, 10));
        }
        

        System.out.println("|");
        System.out.println("--------------------------");
        System.out.println("| " + this.addPadding(bottomLine, 23) + "|");
        System.out.println("--------------------------");
        System.out.println("");
    }
    public double singleConvert() {
        Scanner scanner = new Scanner(System.in);  

        Converter converter = new Converter();
        UnitParser unitParser = new UnitParser();
        
        String displayInType = "";
        String displayOutType = "";
        String displayMessage = "";

        displayMessage = "Enter Input";
    
        System.out.println("\n");


        UnitParser.ParseResult parsedInput = unitParser.createNullResult();

        while (true) {

            this.renderState(displayInType, displayOutType, displayMessage, "");

            String inStr = scanner.nextLine();
            if (inStr.toLowerCase().equals("exit")) { return Double.NaN; }

            parsedInput = unitParser.parse(inStr);

            if (Double.isNaN(parsedInput.val) || parsedInput.type == MeasureType.NAN) {

                System.out.println("\n\n\n\n\n\n\n\n");

                displayMessage = "Invalid Input";
                continue;
            }

            displayInType = parsedInput.val + " " + parsedInput.type.toString();
            break;
        }

        MeasureType outType = MeasureType.NAN;

        System.out.println("\n\n\n\n\n\n\n\n");
        displayMessage = "Enter Output Units";

        while (outType == MeasureType.NAN) {

            this.renderState(displayInType, displayOutType, displayMessage, "");

            String outStr = scanner.nextLine();
            if (outStr.toLowerCase().equals("exit")) { return Double.NaN; }


            outType = unitParser.parseType(outStr);

            if (outType == MeasureType.NAN) {
                System.out.println("\n\n\n\n\n\n\n\n");

                displayMessage = "Invalid Output Unit";
                continue;
            }

            displayOutType = outType.toString();

            break;
        }

        double convertedResult = converter.convert(parsedInput.type, outType, parsedInput.val);

        System.out.println("\n\n\n\n\n\n\n\n");

        displayMessage = "Result: ";

        if (!Double.isNaN(convertedResult)) {
            this.renderState(displayInType, displayOutType, displayMessage, convertedResult + " " + outType.toString());
        } else {
            this.renderState(displayInType, displayOutType, displayMessage, "- Invalid Conversion -");
        }

        //scanner.close();
        return convertedResult;
    
    }
}
