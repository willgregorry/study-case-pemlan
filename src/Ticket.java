public class Ticket extends Bus{
    private Passenger [][][]  pasenger = new Passenger[7][7][40];

    public Ticket() {
        super();
    }

    public void setPasenger(Passenger[][][] pasenger) {
        this.pasenger = pasenger;
    }

    public Passenger getPassenger(int row, int col, int position) {
        return pasenger[row][col][position];
    }
}
