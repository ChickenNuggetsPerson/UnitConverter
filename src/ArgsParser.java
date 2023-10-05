import java.util.ArrayList;
import java.util.List;

public class ArgsParser {
    private class Argument {
        String name;
        String value;
        public Argument(String name, String value) {
            this.name = name;
            this.value = value;
        }
    }

    private List<Argument> argStorage = new ArrayList<>();

    public void parse(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (!args[i].startsWith("-")) { continue; }
            argStorage.add(new Argument(args[i].substring(1), args[i + 1]));

            System.out.println("Found arg: " + args[i].substring(1) + " with " + args[i + 1]);
        }
    }

    public String getArgument(String name) {
        for (Argument arg: argStorage) {
            if (!arg.name.equals(name)) { continue; }
            return arg.value;
        }
        return null;
    }
}
