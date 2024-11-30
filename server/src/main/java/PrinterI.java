import com.zeroc.Ice.Current;
import Demo.Printer;

public class PrinterI implements Printer
{

    private final String _name;

    public PrinterI(String name){
        _name = name;
    }

    public void printString(String s, com.zeroc.Ice.Current current)
    {
        System.out.println(s);
    }

    @Override
    public void shutdown(Current current) {
            System.out.println(_name + "Shutting down...");
            current.adapter.getCommunicator().shutdown();
    }

    @Override
    public void sayHello(Current current) {
        System.out.println(_name + "says Hello World!");
    }

}