package terminal;

public abstract class Interface implements Control {
    private String password_card;

    public Interface(String password_card) {
        this.password_card = password_card;
    }

    public abstract void showMenu();
}
