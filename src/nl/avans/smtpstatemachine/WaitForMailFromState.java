package nl.avans.smtpstatemachine;

import nl.avans.SmtpContext;

public class WaitForMailFromState implements SmtpStateInf {
    SmtpContext context;

    public WaitForMailFromState(SmtpContext context) {
        this.context=context;
    }

    @Override
    public void Handle(String data) {
        //Handle "MAIL FROM: <user@domain.nl>" Command & TRANSITION TO NEXT STATE
        //Handle "QUIT" Command
        //Generate "503 Error on invalid input"

        if(data.toUpperCase().startsWith("HELO ")) {
            context.SetHost(data.substring(5));
            context.SendData("250 Hello " + context.GetHost() + ", I am glad to meet you");
            context.SetNewState(new WaitForRcptToState(context));
            return;
        }
        if(data.toUpperCase().startsWith("QUIT")) {
            context.SendData("221 Bye");
            context.DisconnectSocket();
            return;
        }
        context.SendData("503 Error: Invalid input");

    }
}
