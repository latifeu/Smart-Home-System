import java.time.LocalDateTime;

public class SmartLamp {
    private String name;
    private boolean status;
    private int kelvin = 4000;
    private int brightness = 100;
    private LocalDateTime switchTime;

    public LocalDateTime getSwitchTime() {
        return switchTime;
    }

    public void setSwitchTime(LocalDateTime switchTime) {
        this.switchTime = switchTime;
    }

    public SmartLamp(String name) {
        this.name = name;
        this.status =false;
        this.kelvin = 4000;
        this.brightness = 100;
    }
    public SmartLamp(String name, boolean status) {
        this.name = name;
        this.status = status;
        this.kelvin = 4000;
        this.brightness = 100;
    }

    public SmartLamp(String name, boolean status, int kelvin, int brightness) {
        this.name = name;
        this.status = status;
        this.kelvin = kelvin;
        this.brightness = brightness;
    }


    public String  switchOnOff(String situation) {
        String message="";
        if (situation.equals("On")){
            if (!status) {
                status = true;
            } else {
                message="The device is already switched on!";
            }
        }else if (situation.equals("Off")){
            if (status) {
                status = false;
            } else {
                message="The device is already switched off!.";            }
        }return message;
    }


    public String setKelvinValue(int kelvin) {
        String message="";
        if (kelvin >= 2000 && kelvin <= 6500) {
            this.kelvin = kelvin;
        } else {
            message="ERROR: Kelvin value must be in range of 2000K-6500K!";
        }
        return message;
    }

    public String setBrightnessValue(int brightness) {
        String message="";
        if (brightness >= 0 && brightness <= 100) {
            this.brightness = brightness;
        } else {
            message="ERROR: Brightness must be in range of 0%-100%!";
        }
        return message;
    }

    public String setWhite(int kelvinValue, int brightnessValue) {
        String message="";
        if (kelvinValue >= 2000 && kelvinValue <= 6500){
            if (brightnessValue >= 0 && brightnessValue <= 100) {
                brightness=brightnessValue;
                kelvin=kelvinValue;
            }
            else{
                message="ERROR: Brightness must be in range of 0%-100%!";
            }
        }else{
            message="ERROR: Kelvin value must be in range of 2000K-6500K!";
        }
        return message;
    }

    // Getters and setters for attributes
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getKelvin() {
        return kelvin;
    }

    public void setKelvin(int kelvin) {
        this.kelvin = kelvin;
    }

    public int getBrightness() {
        return brightness;
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }
    public String lampInfo() {
        String message="";
        String lampStatus = status ? "on" : "off";
        String switchTimeStr = switchTime != null ? switchTime.toString() : "null";
        return ("Smart Lamp " + name + " is " + lampStatus + " and its kelvin value is " + kelvin + "K with " + brightness + "% brightness, and its time to switch its status is " + switchTimeStr + ".");
    }
}
