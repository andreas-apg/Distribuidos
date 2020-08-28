package cliente;

public class Main {

    private static Cliente cliente;
    private static Menu menu;

    public static void main(String[] args) throws Exception {

        cliente = new Cliente();
        //cliente.start();

        menu = new Menu();
        menu.startMenu();


    }
}