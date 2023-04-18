package client;

public class Main {
    public static void main(String[] args) {
        Terminal terminal = new Terminal();
        while (true) {
            terminal.refresh();
            terminal.start();
             if (terminal.continueUse().equals("end")){
                 break;
             }
        }
    }
}