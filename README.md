# CLArguments

_A flexible and extensible utility for defining and then parsing command line arguments in Java_

Features:-
* Flexible - _supports standard space delimited args or ```name=value``` style args with optional, defineable argument name prefixing and suffixing_ 
* Extensible - _use pre-defined argument types (string, integer, double, etc.) or define your own_
* Mandatory argument support
* Custom argument value converters and validators
* Support for flag and informational (e.g. ```-help```) arguments
* Alternative argument name support (e.g. ```-help```, ```-h```)
* Supports multi-valued arguments (i.e. same argument specified multiple times with different values)
* User friendly _(show immediate exceptions or list out exceptions at end of parsing)_ 
* In-built help display
* Argument definitions clearly coded - not encoded into special strings!

## Quick Start

```java
import com.adeptions.clarguments.*;
import com.adeptions.clarguments.definitions.*;

public class MyApplication {
    public static void main(String[] args) {
        // define the command line arguments expected/accepted...
        ArgumentDefinitions argumentDefinitions = new ArgumentDefinitions(
                new StringArgumentDefinition("say", "What to say").makeMandatory(),
                new InformationalArgumentDefinition(new String[] {"help", "h"}, "Display this help")
        );

        try {
            // parse the command line args...
            Arguments arguments = argumentDefinitions.parseArgs(args);

            if (!arguments.anythingSeen() || arguments.get("help").isSpecified()) {
                // the user didn't specify any args or asked for help...
                System.out.println("My application accepts the following arguments:-");
                System.out.println(argumentDefinitions.getHelp());
            } else if (arguments.hasParsingExceptions()) {
                // there were some problems with the args...
                for (BadArgException badArgException: arguments.getParsingExceptions()) {
                    System.err.println(badArgException.getMessage());
                }
            } else {
                // run the actual application...
                System.out.println("You asked me to say... '" + arguments.get("say").getValue() + "'");
            }
        } catch (BadArgException e) {
            e.printStackTrace();
        }
    }
}
```
