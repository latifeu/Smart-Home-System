import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class SmartPlug {
    private LocalDateTime openTime;
    private LocalDateTime closeTime;
    private String name; // cihaz adı
    private boolean status; // açık/kapalı durum
    private boolean pluggedIn; // fişte olup olmadığı
    private int voltage; // voltaj
    private double  amperage; // amperaj
    private double energyConsumption; // toplam enerji tüketimi
    private LocalDateTime switchTime;

    public LocalDateTime getSwitchTime() {
        return switchTime;
    }

    public void setSwitchTime(LocalDateTime switchTime) {
        this.switchTime = switchTime;
    }

    // constructor methodları
    public SmartPlug(String name) {
        this(name, false, false, 220, 0, 0.0);
    }

    public SmartPlug(String name, boolean status) {
        this(name, status, false, 220, 0, 0.0);
    }

    public SmartPlug(String name, boolean status, double amperage) {
        this(name, status, false, 220, amperage, 0.0);
    }

    // tüm constructor methodları
    public SmartPlug(String name, boolean status, boolean pluggedIn, int voltage, double amperage, double energyConsumption) {
        this.name = name;
        this.status = status;
        this.pluggedIn = pluggedIn;
        this.voltage = voltage;
        this.amperage = amperage;
        this.energyConsumption = energyConsumption;
    }

    // setter/getter methodları
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean getStatus() {
        return status;
    }

    public void setPluggedIn(boolean pluggedIn) {
        this.pluggedIn = pluggedIn;
    }

    public boolean getPluggedIn() {
        return pluggedIn;
    }

    public void setVoltage(int voltage) {
        this.voltage = voltage;
    }

    public int getVoltage() {
        return voltage;
    }

    public void setAmperage(int amperage) {
        this.amperage = amperage;
    }

    public double getAmperage() {
        return amperage;
    }

    public void setEnergyConsumption(double energyConsumption) {
        this.energyConsumption = energyConsumption;
    }

    public double getEnergyConsumption() {
        return energyConsumption;
    }
    public void energyConsumption(LocalDateTime date1,LocalDateTime date2){
        long hoursBetween = ChronoUnit.MINUTES.between(date1, date2);
        int intHoursBetween = (int) hoursBetween;
        this.energyConsumption+= (amperage * voltage * intHoursBetween)/60;

    }

    public LocalDateTime getOpenTime() {
        return openTime;
    }

    public void setOpenTime(LocalDateTime openTime) {
        this.openTime = openTime;
    }

    public LocalDateTime getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(LocalDateTime closeTime) {
        this.closeTime = closeTime;
    }

    public boolean isStatus() {
        return status;
    }

    public boolean isPluggedIn() {
        return pluggedIn;
    }

    public void setAmperage(double amperage) {
        this.amperage = amperage;
    }

    public String switchOnOff(String situation, LocalDateTime time) {
        String message="";
        if (situation.equals("On")){
            if (!status) {
                this.openTime=time;
                status = true;
            } else {
                message="The device is already switched on!";
            }
        }else if (situation.equals("Off")){
            if (status) {
                System.out.println(amperage);
                this.closeTime=time;
                status = false;
                try{
                    if ( !openTime.isEqual(closeTime)){
                        energyConsumption(openTime,closeTime);
                    }
                }catch (NullPointerException e){
                    int a=0;
                }
            } else {
                message="The device is already switched off!";
            }
        }return message;
    }
    public String plugIn (String situation,double amperValue) {
        String message="";
        if (!pluggedIn) {
            pluggedIn = true;
            amperage=amperValue;
        } else {
            message="ERROR: There is already an item plugged in to that plug!";
        }return message;

    }
    public String plugOut(String sitiutaion){
        String message="";
        if (pluggedIn) {
            pluggedIn = false;
        } else {
            message="ERROR: This plug has no item to plug out from that plug!";
        }return message;

    }
    public String plugInfo() {
        String message="";
        String statusStr = status ? "on" : "off";
        message="Smart Plug " + this.name + " is " + statusStr + " and consumed " + String.format("%.2f", this.energyConsumption) + "W so far (excluding current device), and its time to switch its status is " + this.switchTime + ".";
        return message;
    }


}





