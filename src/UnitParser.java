import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UnitParser {
    
    public static void main(String[] args) throws Exception {
        UnitParser parser = new  UnitParser();
        ParseResult result = parser.parse("14.64 feet");

        System.out.println(result.val);
        System.out.println(result.type);
    }

    private class MeasureRelation {
        String str;
        MeasureType type;
        public MeasureRelation(MeasureType unitType, String str) {
            this.type = unitType;
            this.str = str;
        }
    }

    private List<MeasureRelation> relationList = new ArrayList<>();
    UnitParser() {
        relationList.add(new MeasureRelation(MeasureType.In, "in"));
        relationList.add(new MeasureRelation(MeasureType.In, "inch"));
        relationList.add(new MeasureRelation(MeasureType.In, "inches"));

        relationList.add(new MeasureRelation(MeasureType.Ft, "ft"));
        relationList.add(new MeasureRelation(MeasureType.Ft, "foot"));
        relationList.add(new MeasureRelation(MeasureType.Ft, "feet"));
        
        relationList.add(new MeasureRelation(MeasureType.Yrd, "yrd"));
        relationList.add(new MeasureRelation(MeasureType.Yrd, "yard"));
        relationList.add(new MeasureRelation(MeasureType.Yrd, "yrds"));
        relationList.add(new MeasureRelation(MeasureType.Yrd, "yards"));

        relationList.add(new MeasureRelation(MeasureType.Mile, "mile"));
        relationList.add(new MeasureRelation(MeasureType.Mile, "miles"));

        relationList.add(new MeasureRelation(MeasureType.Cm, "cm"));
        relationList.add(new MeasureRelation(MeasureType.Cm, "centimeter"));
        relationList.add(new MeasureRelation(MeasureType.Cm, "centimeters"));

        relationList.add(new MeasureRelation(MeasureType.Meter, "m"));
        relationList.add(new MeasureRelation(MeasureType.Meter, "meter"));
        relationList.add(new MeasureRelation(MeasureType.Meter, "meters"));

        relationList.add(new MeasureRelation(MeasureType.Kilometer, "km"));
        relationList.add(new MeasureRelation(MeasureType.Kilometer, "kilometer"));
        relationList.add(new MeasureRelation(MeasureType.Kilometer, "kilometers"));

        relationList.add(new MeasureRelation(MeasureType.Lb, "lb"));
        relationList.add(new MeasureRelation(MeasureType.Lb, "lbs"));

        relationList.add(new MeasureRelation(MeasureType.Gram, "g"));
        relationList.add(new MeasureRelation(MeasureType.Gram, "gram"));
        relationList.add(new MeasureRelation(MeasureType.Gram, "grams"));

        relationList.add(new MeasureRelation(MeasureType.Kilogram, "kg"));
        relationList.add(new MeasureRelation(MeasureType.Kilogram, "kilogram"));
        relationList.add(new MeasureRelation(MeasureType.Kilogram, "kilograms"));

        relationList.add(new MeasureRelation(MeasureType.C, "c"));
        relationList.add(new MeasureRelation(MeasureType.C, "celsius"));
        
        relationList.add(new MeasureRelation(MeasureType.F, "f"));
        relationList.add(new MeasureRelation(MeasureType.F, "fahrenheit"));


        relationList.add(new MeasureRelation(MeasureType.K, "k"));
        relationList.add(new MeasureRelation(MeasureType.K, "kelvin"));

        relationList.add(new MeasureRelation(MeasureType.Gallon, "gal"));
        relationList.add(new MeasureRelation(MeasureType.Gallon, "gallon"));
        relationList.add(new MeasureRelation(MeasureType.Gallon, "gallons"));

        relationList.add(new MeasureRelation(MeasureType.Quart, "qt"));
        relationList.add(new MeasureRelation(MeasureType.Quart, "quart"));
        relationList.add(new MeasureRelation(MeasureType.Quart, "quarts"));

        relationList.add(new MeasureRelation(MeasureType.Pint, "pint"));
        relationList.add(new MeasureRelation(MeasureType.Pint, "pints"));

        relationList.add(new MeasureRelation(MeasureType.Cup, "cup"));
        relationList.add(new MeasureRelation(MeasureType.Cup, "cups"));

        relationList.add(new MeasureRelation(MeasureType.Tablespoon, "tbsp"));
        relationList.add(new MeasureRelation(MeasureType.Tablespoon, "tablespoon"));
        relationList.add(new MeasureRelation(MeasureType.Tablespoon, "tablespoons"));

        relationList.add(new MeasureRelation(MeasureType.Teaspoon, "tsp"));
        relationList.add(new MeasureRelation(MeasureType.Teaspoon, "teaspoon"));
        relationList.add(new MeasureRelation(MeasureType.Teaspoon, "teaspoons"));

        relationList.add(new MeasureRelation(MeasureType.FluidOunce, "floz"));
        relationList.add(new MeasureRelation(MeasureType.FluidOunce, "fluidounce"));

        relationList.add(new MeasureRelation(MeasureType.Liter, "l"));
        relationList.add(new MeasureRelation(MeasureType.Liter, "liter"));
        relationList.add(new MeasureRelation(MeasureType.Liter, "liters"));

        relationList.add(new MeasureRelation(MeasureType.Mililiter, "ml"));
        relationList.add(new MeasureRelation(MeasureType.Mililiter, "mililiter"));
        relationList.add(new MeasureRelation(MeasureType.Mililiter, "mililiters"));

        relationList.add(new MeasureRelation(MeasureType.CubicMeter, "cubicmeter"));
        relationList.add(new MeasureRelation(MeasureType.CubicMeter, "cubicmeters"));

        relationList.add(new MeasureRelation(MeasureType.CubicInch, "cubicinch"));
        relationList.add(new MeasureRelation(MeasureType.CubicInch, "cubicinches"));

        relationList.add(new MeasureRelation(MeasureType.CubicFoot, "cubicfoot"));
        relationList.add(new MeasureRelation(MeasureType.CubicFoot, "cubicfeet"));


    }


    public MeasureType parseType(String outType) {

        for (int i = 0; i < relationList.size(); i++) {
            if (outType.equals(relationList.get(i).str)) {
                return relationList.get(i).type;
            }
        }

        return MeasureType.NAN;
    }


    public class ParseResult {
        public double val;
        public MeasureType type;
        public ParseResult(double val, MeasureType type) {
            this.val = val;
            this.type = type;
        }
    }

    public ParseResult createNullResult() {
        return new ParseResult(Double.NaN, MeasureType.NAN);
    }

    public ParseResult parse(String str) {
        
        // To lowercase
        // remove invalid characters

        try {
            str = str.toLowerCase().trim();
            
            String[] splitStr = str.split(" ", 2);
        
            if (splitStr.length == 2) {
                ParseResult result = new ParseResult(0, MeasureType.NAN);
                result.val = Double.parseDouble(splitStr[0]);
                result.type = parseType(splitStr[1]);
                
                return result;
            } else {

                // The user has to put a spcae between the units and value
                return createNullResult();

                /*
                 * ParseResult result = new ParseResult(0, MeasureType.NAN);

                int index = -1;
                for (int i = 0; i < str.length(); i++) {
                    if (parseType(str.substring(i)) != MeasureType.NAN) {
                        index = i - 1;
                    }
                }
                if (index == -1) { return createNullResult(); }

                result.val = Double.parseDouble(str.substring(0, index));
                result.type = parseType(str.substring(index, str.length()));
        
                return result;
                 */
                
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            return createNullResult();
        }

    }



}
