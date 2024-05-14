import javax.swing.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class SmartCamera {
    private String name;
    private boolean status;
    private double storageUsed;
    private double megabytesPerRecord;
    private LocalDateTime switchTime;

    private LocalDateTime openTime;
    private LocalDateTime closeTime;
    public LocalDateTime getSwitchTime() {
        return switchTime;
    }

    public void setSwitchTime(LocalDateTime switchTime) {
        this.switchTime = switchTime;
    }

    public void setStorageUsed(double storageUsed) {
        this.storageUsed = storageUsed;
    }

    public void setMegabytesPerRecord(double megabytesPerRecord) {
        this.megabytesPerRecord = megabytesPerRecord;
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

    // Constructor without initial status
    public SmartCamera(String name, double megabytesPerRecord) {
        this.name = name;
        this.status = false;
        this.storageUsed = 0;
        this.megabytesPerRecord = megabytesPerRecord;
    }

    // Constructor with initial status
    public SmartCamera(String name, double megabytesPerRecord, boolean initialStatus) {
        this.name = name;
        this.status = initialStatus;
        this.storageUsed = 0;
        this.megabytesPerRecord = megabytesPerRecord;
    }

    // Method to turn on the camera
    public String switchOnOff(String situation,LocalDateTime time) {
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
                this.closeTime=time;
                status = false;
                try{
                    if (!openTime.isEqual(closeTime)){
                        usedStorage(openTime,closeTime); }
                }catch(NullPointerException e ){
                        int a=0;
                    }

                    }
            } else {
                message= "The device is already switched off!";
            }
        return message;
        }


    public boolean isStatus() {
        return status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setStorageUsed(int storageUsed) {
        this.storageUsed = storageUsed;
    }

    public void setMegabytesPerRecord(int megabytesPerRecord) {
        this.megabytesPerRecord = megabytesPerRecord;
    }


    // Method to get the name of the camera
    public String getName() {
        return this.name;
    }

    // Method to get the status of the camera
    public boolean getStatus() {
        return this.status;
    }

    // Method to get the storage used by the camera
    public double getStorageUsed() {
        return this.storageUsed;
    }

    // Method to get the megabytes per record of the camera
    public double getMegabytesPerRecord() {
        return this.megabytesPerRecord;
    }
    public void usedStorage(LocalDateTime date1,LocalDateTime date2){
        long minutesBetween = ChronoUnit.MINUTES.between(date1, date2);
        int intMinutesBetween = (int) minutesBetween;
        this.storageUsed= intMinutesBetween * megabytesPerRecord;

    }
    public String  cameraInfo() {
        String message="";
        message=String.format("Smart Camera %s is %s and used %.2f MB of storage so far (excluding current status), and its time to switch its status is %s.%n",
                this.name, this.status ? "on" : "off", this.storageUsed, this.switchTime);
        return message;
    }

}
