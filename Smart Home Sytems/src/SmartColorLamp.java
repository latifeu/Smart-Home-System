import java.time.LocalDateTime;

public class SmartColorLamp {
    private String name;
    private boolean status;
    private int kelvin;
    private int brightness;
    private int colorCode;
    private LocalDateTime switchTime;

    public LocalDateTime getSwitchTime() {
        return switchTime;
    }

    public void setSwitchTime(LocalDateTime switchTime) {
        this.switchTime = switchTime;
    }

    public SmartColorLamp(String name) {
        this.name = name;
        this.status = false;
        this.kelvin = 4000;
        this.brightness = 100;
        this.colorCode = 0xFFFFFF;
    }

    public SmartColorLamp(String name, boolean status) {
        this.name = name;
        this.status = status;
        this.kelvin = 4000;
        this.brightness = 100;
        this.colorCode = 0xFFFFFF;
    }

    public SmartColorLamp(String name, boolean status, int kelvin, int brightness) {
        this.name = name;
        this.status = status;
        this.kelvin = kelvin;
        this.brightness = brightness;
        this.colorCode = 0xFFFFFF;
    }

    public SmartColorLamp(String name, boolean status, int colorCode, int brightness,boolean isColorMode) {
        this.name = name;
        this.status = status;
        this.colorCode = colorCode;
        this.kelvin = 0;
        this.brightness = brightness;
    }

    public String getName() {
        return name;
    }

    public boolean isStatus() {
        return status;
    }



    public int getKelvin() {
        return kelvin;
    }

    public int getBrightness() {
        return brightness;
    }

    public int getColorCode() {
        return colorCode;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void switchStatus() {
        this.status = !this.status;
        // Update total energy consumption accordingly
    }

    public void changeName(String newName) {
        this.name = newName;
    }

    public void setKelvin(int kelvin) {
        this.kelvin = kelvin;
        this.colorCode = 0xFFFFFF;
    }
    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }

    public void setColorCode(int colorCode) {
        this.colorCode = colorCode;
        this.kelvin = 0;
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
    public String switchOnOff(String situation) {
        String message="";
        if (situation.equals("On")){
            if (!status) {
                status = true;
            } else {
                message= "The device is already switched on!";
            }
        }else if (situation.equals("Off")){
            if (status) {
                status = false;
            } else {
                message="The device is already switched off!";
            }
        }return message;
    }
    public String setKelvinValue(int kelvin) {
        String message="";
        if (kelvin >= 2000 && kelvin <= 6500) {
            this.kelvin = kelvin;
        } else {
            message="ERROR: Kelvin value must be in range of 2000K-6500K!";
        }return message;
    }
    public String setColorCodeValue(String c_code){
        String message="";
        try {
            int num = Integer.decode(c_code);
            if (num > 0x000000 & num < 0xFFFFFF) {
                this.colorCode=num;

            }else{
                message="ERROR: Color code value must be in range of 0x0-0xFFFFFF!";
            }

            // num değişkeni artık integer olarak kullanılabilir.
        } catch (NumberFormatException e) {
            message="ERROR: Erroneous command!";
        }return message;
    }
    public String setColor(String c_code,String brightness){
        String message="";
        try {
            int num = Integer.decode(c_code);
            int brightness_num= Integer.parseInt(brightness);
            if (num >0x000000 & num <= 0xFFFFFF) {
                if (brightness_num >= 0 && brightness_num <= 100 ){
                    this.brightness=brightness_num;
                    this.colorCode=num;
                }else{
                    message="ERROR: Brightness must be in range of 0%-100%!";
                }
            }else{
                message="ERROR: Color code value must be in range of 0x0-0xFFFFFF!";


            }

            // num değişkeni artık integer olarak kullanılabilir.
        } catch (NumberFormatException e) {
            message="ERROR: Erroneous command!";
        }return message;
    }
    public String setBrightnessValue(int brightness) {
        String message="";
        if (brightness >= 0 && brightness <= 100) {
            this.brightness = brightness;
        } else {
            message="ERROR: Brightness must be in range of 0%-100%!";
        }return message;
    }
    public String  colorLampInfo() {
        String message="";
        String statusText = status ? "on" : "off";
        String colorText = (kelvin > 0) ? kelvin + "K" : String.format("#%06X", colorCode);
        String brightnessText = brightness + "%";
        String switchTimeText = (switchTime == null) ? "null" : switchTime.toString();

        message="Smart Color Lamp " + name + " is " + statusText + " and its color value is " + colorText +
                " with " + brightnessText + " brightness, and its time to switch its status is " + switchTimeText + ".";
        return message;
    }
}