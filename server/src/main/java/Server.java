import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Identity;
import com.zeroc.Ice.ObjectAdapter;
import com.zeroc.Ice.Properties;
import com.zeroc.Ice.Util;

public class Server
{
    public static void main(String[] args)
    {

        int status = 0;
        java.util.List<String> extraArgs = new java.util.ArrayList<String>();

        try(Communicator communicator = Util.initialize(args, extraArgs))
        {

            Runtime.getRuntime().addShutdownHook(new Thread(communicator::destroy));

            if (!extraArgs.isEmpty()){
                System.err.println("too many arguments");
                status = 1;
            } else {
                ObjectAdapter adapter = communicator.createObjectAdapter("Printer");
                Properties properties = communicator.getProperties();
                Identity id = Util.stringToIdentity(properties.getProperty("Identity"));
                adapter.add(new PrinterI(properties.getProperty("Ice.ProgramName")), id);
                adapter.activate();
            }
        }
        System.exit(status);
    }
}