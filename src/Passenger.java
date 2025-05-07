public class Passenger {
    private String name;
    private String nik;
    private String gender;
    private String pNumber;
    private String from;
    private String to;
    private double fare;

    public Passenger(){}

    public void setName(String name){ this.name = name;}
    public void setNik(String nik){ this.nik = nik;}
    public void setGender(String gender){ this.gender = gender;}
    public void setpNumber(String pNumber){ this.pNumber = pNumber;}
    public void setFrom(String from){ this.from = from;}
    public void setTo(String to){ this.to = to;}
    public void setFare(double fare){ this.fare = fare;}

    public String getName(){ return name;}
    public String getNik(){ return nik;}
    public String getGender(){ return gender;}
    public String getpNumber(){ return pNumber;}
    public String getFrom(){ return from;}
    public String getTo(){ return to;}
    public double getFare(){ return fare;}

}
