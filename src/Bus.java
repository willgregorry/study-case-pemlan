public class Bus {
    private final String[] departureBus = {"Magelang", "Jogjakarta", "Kartosuro", "Solo", "Gendingan", "Ngawi", "Wilangan"};
    private final String[] arrivalBus = {"Wilangan", "Ngawi", "Gendingan", "Solo", "Kartosuro", "Jogjakarta", "Magelang"};

    private final String[][] seat = {{  "A1",  "A2" , "A3" , "A4" , "A5" , "A6" ,
                                        "A7" , "A8" , "A9" , "A10", "A11", "A12",
                                        "A13", "A14", "A15", "A16", "A17", "A18"},

                                       {"B1" , "B2" , "B3" , "B4" ,  "B5", "B6" ,
                                        "B7" , "B8" , "B9" , "B10", "B11", "B12",
                                        "B13", "B14", "B15", "B16", "B17", "B18",
                                        "B19", "B20", "B21", "B22"              }};

    public Bus(){}

    public String[] getArrivalBus() { return arrivalBus;}
    public String[] getDepartureBus() { return departureBus;}
    public String[][] getSeat() { return seat;}

    public String getSeat(String from, String to) {
        for (int i = 0; i < departureBus.length; i++) {
            if (departureBus[i].equals(from)) {
                for (int j = 0; j < arrivalBus.length; j++) {
                    if (arrivalBus[j].equals(to)) {
                        return seat[i][j];
                    }
                }
            }
        }
        return null;

    }

}
