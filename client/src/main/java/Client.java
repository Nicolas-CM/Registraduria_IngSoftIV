import Demo.PrinterPrx;
import com.zeroc.Ice.*;
import com.zeroc.IceGrid.QueryPrx;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.Exception;

public class Client {
    public static void main(String[] args) {
        try (Communicator communicator = Util.initialize(args, "config.client")) {
            int status = run(communicator);
            System.exit(status);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static int run(Communicator communicator) {

        PrinterPrx hello = null;
        QueryPrx query = QueryPrx.checkedCast(
                communicator.stringToProxy("DemoIceGrid/Query"));
        hello = PrinterPrx.checkedCast(
                query.findObjectByType("::Demo::Printer"));

        if (hello == null) {
            System.err.println("Cannot connect to IceGrid query service");
            return 1;
        }

        menu();

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String line = null;

        do {
            try {
                System.out.print("==> ");
                System.out.flush();
                line = in.readLine();
                if (line == null) {
                    break;
                }

                // if (line.equals("1")){
                // hello.sayHello();
                // } else if (line.equals("2")){
                // System.out.println("Do Nothing");
                // } else if (line.equals("3")){
                // printer.shutdown();
                // } else if (line.equals("?")){
                // menu();
                // } else {
                // System.out.println("Invalid option");
                // menu();
                // }

                switch (line) {
                    case "1" -> hello.sayHello();
                    case "2" -> System.out.println("Do Nothing");
                    case "3" -> {
                        hello.shutdown();
                        System.out.println("Shutting down...");
                    }
                    case "?" -> menu();
                    default -> {
                        System.out.println("Invalid option");
                        menu();
                    }
                }
            } catch (IOException | LocalException ex) {
                ex.printStackTrace();
            }
        } while (!line.equals("3"));

        return 0;
    }

    private static void menu() {
        System.out.println("Options:");
        System.out.println("1: Say Hello");
        System.out.println("2: Do Nothing");
        System.out.println("3: Shutdown");
        System.out.println("?: Help");
    }
}