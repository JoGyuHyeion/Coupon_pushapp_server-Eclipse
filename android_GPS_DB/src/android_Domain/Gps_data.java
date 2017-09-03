package android_Domain;

public class Gps_data {
    double latitude;
    double longitude;
    int radius;
    final int effective_time = -1;
    String name;
    int id_num;

    public double getLatitude() {return latitude;}

    public void setLatitude(double latitude) {this.latitude = latitude;}

    public double getLongitude() {return longitude;}

    public void setLongitude(double longitude) {this.longitude = longitude;}

    public int getRadius() {return radius;}

    public void setRadius(int radius) {this.radius = radius;}

    public int getEffective_time() {return effective_time;}

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public int getId_num() {return id_num;}

    public void setId_num(int id_num) {this.id_num = id_num;}
}
