import java.util.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.lang.Object;
public class Main {
    /**

     This method reads the lines from the file specified by the path argument and returns an array of strings containing
     each line as a separate element.
     @param path the path of the file to be read
     @return an array of strings containing the lines of the file
     */
    public static String[] readFile(String path){
        try{
            int i=0;
            // Get the number of lines in the file
            int length= Files.readAllLines(Paths.get(path)).size();
            String[] results=new String[length];
            // Read all the lines from the file and add them to the result array
            for (String line: Files.readAllLines(Paths.get(path))){
                results[i++]=line;
            }
            return results;
            // If there is an error while reading the file, print the stack trace and return null
        } catch (IOException e){
            e.printStackTrace();
            return null;
        }}
    public static void main(String[] args){
        try{
            FileWriter myWriter = new FileWriter(args[1]+"\n");
            /**

             This method reads the input file and creates an ArrayList for each type of device
             (plug, lamp, color lamp, and camera), an ArrayList for the switch times of each device,
             and an ArrayList for all the devices. It also initializes some variables for date and time.
             @param input the path of the input file to be read
             @return an ArrayList of String objects containing the non-empty lines of the input file
             */
            String input=args[0];
            ArrayList<Object>  allDevices = new ArrayList<Object> ();
            ArrayList<String> plugList = new ArrayList<String>();
            ArrayList<String> lampList = new ArrayList<String>();
            ArrayList<String> colorLampList = new ArrayList<String>();
            ArrayList<String> cameraList = new ArrayList<String>();
            ArrayList<ArrayList<String>> switchTimeList = new ArrayList<ArrayList<String>>();
            String[] lines=readFile (input);
            ArrayList<String> inputLines = new ArrayList<String>();
            ArrayList<LocalDateTime> onlySwitchTime = new ArrayList<LocalDateTime>();
            LocalDateTime initialTime=LocalDateTime.now();
            LocalDateTime newTime=LocalDateTime.of(0001, 01, 01, 01, 01, 01);
            LocalDateTime switchTime=LocalDateTime.of(0001, 01, 01, 01, 01, 01);
            LocalDateTime oldTime= LocalDateTime.of(0001, 01, 01, 01, 01, 01);
            ArrayList<LocalDateTime> biggerSwitchTime = new ArrayList<LocalDateTime>();
            for (int j=0;j<lines.length;j++){
                if (!lines[j].equals("")){
                    inputLines.add(lines[j]);
                }
            }
            /**

             Reads input lines from an array, processes each line to extract commands,
             and sets the initial time if the first command is "SetInitialTime".
             @param lines an array of input lines to process
             @return void
             */

            for(int i=0;i<inputLines.size();i++){
                myWriter.write("COMMAND: "+inputLines.get(i)+"\n");

                String[] splitCommands = inputLines.get(i).split("\t");
                ArrayList<String> commandsLine = new ArrayList<>(Arrays.asList(splitCommands));
                if (i==0 & commandsLine.get(0).equals("SetInitialTime")){
                    String date = commandsLine.get(1);
                    try{
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");
                        initialTime = LocalDateTime.parse(date, formatter);
                        myWriter.write("SUCCESS: "+initialTime+"!"+"\n");

                    } catch(DateTimeParseException e){
                        myWriter.write("ERROR: Format of the initial date is wrong! Program is going to terminate!"+"\n");
                        System.exit(0);
                    }
                }
                /**

                 Adds a SmartPlug device to the system based on the given command line.
                 @param commandsLine the command line containing information about the SmartPlug device to add
                 @return void
                 */
                else if (commandsLine.get(0).equals("Add")){
                    /**

                     Adds a SmartPlug device with the specified name and status to the system based on the given command line.
                     @param commandsLine the command line containing information about the SmartPlug device to add
                     @return void
                     */
                    if (commandsLine.get(1).equals("SmartPlug")){
                        if (commandsLine.size()==3){
                            String stringName=commandsLine.get(2);
                            if (!plugList.contains(stringName)& !cameraList.contains(stringName)& !lampList.contains(stringName)&!colorLampList.contains(stringName) ){
                                SmartPlug newPlug= new SmartPlug(stringName);
                                allDevices.add(newPlug);
                                plugList.add(newPlug.getName());
                            }else{
                                myWriter.write("ERROR: There is already a smart device with same name!"+"\n");
                            }

                        }
                        else if (commandsLine.size()==4){
                            String stringName=commandsLine.get(2);
                            if (!plugList.contains(stringName)& !cameraList.contains(stringName)& !lampList.contains(stringName)&!colorLampList.contains(stringName) ){

                                boolean plugStatus=false;

                                if(commandsLine.get(3).equals("On")){
                                    plugStatus=true;
                                    SmartPlug newPlug=new SmartPlug(stringName,plugStatus);
                                    newPlug.setOpenTime(initialTime);
                                    allDevices.add(newPlug);
                                    plugList.add(newPlug.getName());
                                }
                                else if(commandsLine.get(3).equals("Off")){
                                    plugStatus=false;
                                    SmartPlug newPlug=new SmartPlug(stringName,plugStatus);
                                    allDevices.add(newPlug);
                                    plugList.add(newPlug.getName());
                                }else{
                                    myWriter.write("ERROR: Erroneous command!"+"\n");
                                }
                            }else{
                                myWriter.write("ERROR: There is already a smart device with same name!"+"\n");
                            }


                        }
                        /**

                         Adds a SmartPlug device with the specified name, status, and ampere value to the system based on the given command line.
                         @param commandsLine the command line containing information about the SmartPlug device to add
                         @return void
                         */
                        else if (commandsLine.size()==5){
                            String stringName=commandsLine.get(2);
                            if (!plugList.contains(stringName)& !cameraList.contains(stringName)& !lampList.contains(stringName)&!colorLampList.contains(stringName) ){
                                try{
                                    String plugAmpereString=commandsLine.get(4);
                                    Double plugAmpereInt=Double.parseDouble(plugAmpereString);
                                    if(plugAmpereInt>0){
                                        boolean plugStatus=true;
                                        if(commandsLine.get(3).equals("On")){
                                            plugStatus=true;
                                            SmartPlug newPlug=new SmartPlug(stringName,plugStatus,plugAmpereInt);
                                            newPlug.setOpenTime(initialTime);
                                            allDevices.add(newPlug);
                                            plugList.add(newPlug.getName());

                                        }
                                        else if(commandsLine.get(3).equals("Off")){
                                            plugStatus=false;
                                            SmartPlug newPlug=new SmartPlug(stringName,plugStatus,plugAmpereInt);
                                            newPlug.switchOnOff("Off",initialTime);
                                            allDevices.add(newPlug);
                                            plugList.add(newPlug.getName());
                                        }else{
                                            myWriter.write("ERROR: Erroneous command!"+"\n");
                                        }

                                    }else{
                                        myWriter.write("ERROR: Ampere value must be a positive number!"+"\n");
                                    }
                                }catch (NumberFormatException e){
                                    myWriter.write("ERROR: Erroneous command"+"\n");

                                }
                            }else{
                                myWriter.write("ERROR: There is already a smart device with same name!"+"\n");

                            }
                        }else{
                            myWriter.write("ERROR: Erroneous command!"+"\n");
                        }
                    }/**
                     Creates a new SmartCamera object with given name and storage value, sets its open time to initialTime and adds it to allDevices and cameraList.
                     @param stringName the name of the SmartCamera object to be created
                     @param intStorage the storage value of the SmartCamera object to be created
                     @param initialTime the initial time of the SmartCamera object to be created
                     */
                    if (commandsLine.get(1).equals("SmartCamera")){

                        if (commandsLine.size()==4){
                            String stringName=commandsLine.get(2);
                            if (!plugList.contains(stringName)|| !cameraList.contains(stringName)|| !lampList.contains(stringName)||!colorLampList.contains(stringName) ){

                                try{
                                    /**
                                     Creates a new SmartCamera object with given name, storage value and status, sets its open time to initialTime and adds it to allDevices and cameraList.
                                     @param stringName the name of the SmartCamera object to be created
                                     @param intStorage the storage value of the SmartCamera object to be created
                                     @param cameraStatus the status of the SmartCamera object to be created
                                     @param initialTime the initial time of the SmartCamera object to be created
                                     */

                                    int intStorage=(Integer.parseInt(commandsLine.get(3)));
                                    if (intStorage>0){
                                        SmartCamera newCamera= new SmartCamera(stringName,intStorage);
                                        newCamera.setOpenTime(initialTime);
                                        allDevices.add(newCamera);
                                        cameraList.add(newCamera.getName());
                                    }else{
                                        myWriter.write("ERROR: Megabyte value must be a positive number!"+"\n");
                                    }

                                }catch (NumberFormatException e){
                                    myWriter.write("ERROR: Erroneous command!"+"\n");
                                }
                            }else{
                                myWriter.write("ERROR: There is already a smart device with same name!"+"\n");
                            }
                        }/**
                         Checks the size of the given commandsLine and creates a SmartCamera object with given parameters according to the size of the list.
                         @param commandsLine the list of commands to be processed
                         @param plugList the list of existing SmartPlug objects
                         @param cameraList the list of existing SmartCamera objects
                         @param lampList the list of existing SmartLamp objects
                         @param colorLampList the list of existing SmartColorLamp objects
                         @param allDevices the list of all existing smart devices
                         @param initialTime the initial time of the SmartCamera object to be created
                         */
                        else if (commandsLine.size()==5){
                            String stringName=commandsLine.get(2);
                            if (!plugList.contains(stringName)& !cameraList.contains(stringName)& !lampList.contains(stringName)&!colorLampList.contains(stringName) ){
                                try{
                                    boolean cameraStatus=false;
                                    double intStorage=Double.parseDouble(commandsLine.get(3));
                                    if(intStorage>0){
                                        if (commandsLine.get(4).equals("On")){
                                            cameraStatus=true;
                                            SmartCamera newCamera= new SmartCamera(stringName,intStorage,cameraStatus);
                                            newCamera.setOpenTime(initialTime);
                                            allDevices.add(newCamera);
                                            cameraList.add(newCamera.getName());
                                        }
                                        else if (commandsLine.get(4).equals("Off")){
                                            cameraStatus=false;
                                            SmartCamera newCamera= new SmartCamera(stringName,intStorage,cameraStatus);
                                            allDevices.add(newCamera);
                                            cameraList.add(newCamera.getName());
                                        }else{
                                            myWriter.write("ERROR: Erroneous command!"+"\n");
                                        }

                                    }else{
                                        myWriter.write("ERROR: Megabyte value must be a positive number!"+"\n");
                                    }
                                }catch (NumberFormatException e){
                                    myWriter.write("ERROR: Erroneous command!"+"\n");
                                }
                            }else{
                                myWriter.write("ERROR: There is already a smart device with same name!"+"\n");
                            }

                        }else{
                            myWriter.write("ERROR: Erroneous command!"+"\n");
                        }
                    }/**
                     Creates a new SmartLamp object with the given name and adds it to the list of all devices.
                     If a device with the same name already exists, an error message is printed.
                     @param commandsLine A list of command-line arguments.
                     */

                    if (commandsLine.get(1).equals("SmartLamp")){


                        if (commandsLine.size()==3){
                            String stringName=commandsLine.get(2);
                            if (!plugList.contains(stringName)& !cameraList.contains(stringName)& !lampList.contains(stringName)&!colorLampList.contains(stringName) ){
                                SmartLamp newLamp= new SmartLamp(stringName);
                                allDevices.add(newLamp);
                                lampList.add(newLamp.getName());
                            }else{
                                myWriter.write("ERROR: There is already a smart device with same name!"+"\n");
                            }
                        }

                        else if (commandsLine.size()==4){
                            String stringName=commandsLine.get(2);
                            if (!plugList.contains(stringName)& !cameraList.contains(stringName)& !lampList.contains(stringName)&!colorLampList.contains(stringName) ){
                                Boolean lampStatus=false;
                                if (commandsLine.get(3).equals("On")){
                                    lampStatus=true;
                                    SmartLamp newLamp= new SmartLamp(stringName,lampStatus);
                                    allDevices.add(newLamp);
                                    lampList.add(newLamp.getName());
                                }else if (commandsLine.get(3).equals("Off")){
                                    lampStatus=false;
                                    SmartLamp newLamp= new SmartLamp(stringName,lampStatus);
                                    allDevices.add(newLamp);
                                    lampList.add(newLamp.getName());
                                }
                                else{
                                    myWriter.write("ERROR: Erroneous command!"+"\n");
                                }
                            }else{
                                myWriter.write("ERROR: There is already a smart device with same name!"+"\n");
                            }

                        }
                        else if (commandsLine.size()==6){
                            String stringName=commandsLine.get(2);
                            if (!plugList.contains(stringName)|| !cameraList.contains(stringName)|| !lampList.contains(stringName)||!colorLampList.contains(stringName) ){
                                try{
                                    Boolean lampStatus=false;
                                    if (commandsLine.get(3).equals("On")){
                                        lampStatus=true;
                                        int intKelvin=Integer.parseInt(commandsLine.get(4));
                                        if (intKelvin >= 2000 && intKelvin <= 6500){
                                            int intBrightness=Integer.parseInt(commandsLine.get(5));
                                            if (intBrightness >= 0 && intBrightness <= 100) {
                                                SmartLamp newLamp= new SmartLamp(stringName,lampStatus,intKelvin,intBrightness);
                                                allDevices.add(newLamp);
                                                lampList.add(newLamp.getName());
                                            }
                                            else{
                                                myWriter.write("ERROR: Brightness must be in range of 0%-100%!"+"\n");
                                            }
                                        }else{
                                            myWriter.write("ERROR: Kelvin value must be in range of 2000K-6500K!"+"\n");
                                        }

                                    }else if (commandsLine.get(3).equals("Off")){
                                        lampStatus=false;
                                        int intKelvin=Integer.parseInt(commandsLine.get(4));
                                        int intBrightness=Integer.parseInt(commandsLine.get(5));
                                        SmartLamp newLamp= new SmartLamp(stringName,lampStatus,intKelvin,intBrightness);
                                        allDevices.add(newLamp);
                                        lampList.add(newLamp.getName());
                                    }else{
                                        myWriter.write("ERROR: Erroneous command!"+"\n");
                                    }

                                }catch (NumberFormatException e){
                                    myWriter.write("ERROR: Erroneous command!"+"\n");
                                }
                            }else{
                                myWriter.write("ERROR: There is already a smart device with same name!"+"\n");
                            }

                        }else{
                            myWriter.write("ERROR: Erroneous command!"+"\n");
                        }
                    }if (commandsLine.get(1).equals("SmartColorLamp"+"\n")){
                        /**

                         Parses the input commands for creating and controlling smart color lamps and adds the created lamps to the system.
                         @param commandsLine list of input commands containing the action and parameters for smart color lamps
                         @throws RuntimeException if the command is invalid or the device with the same name already exists in the system
                         */
                        if (commandsLine.size()==3){
                            String stringName=commandsLine.get(2);
                            if (!plugList.contains(stringName)& !cameraList.contains(stringName)& !lampList.contains(stringName)&!colorLampList.contains(stringName) ){

                                SmartColorLamp newColorLamp= new SmartColorLamp(stringName);
                                allDevices.add(newColorLamp);
                                colorLampList.add(newColorLamp.getName());
                            }else{
                                myWriter.write("ERROR: There is already a smart device with same name!"+"\n");
                            }

                        }/**

                         Adds a new SmartColorLamp to the system with the given name and status.
                         If the device already exists, prints an error message.
                         If the input command is incorrect, prints an error message.
                         @param commandsLine The command line input by the user.
                         */
                        else if(commandsLine.size()==4){
                            String stringName=commandsLine.get(2);
                            if (!plugList.contains(stringName)& !cameraList.contains(stringName)& !lampList.contains(stringName)&!colorLampList.contains(stringName) ){
                                Boolean colorLampStatus=false;
                                if (commandsLine.get(3).equals("On")){
                                    colorLampStatus=true;
                                    SmartColorLamp newColorLamp= new SmartColorLamp(stringName,colorLampStatus);
                                    allDevices.add(newColorLamp);
                                    colorLampList.add(newColorLamp.getName());
                                }
                                else if (commandsLine.get(3).equals("Off")){
                                    colorLampStatus=false;
                                    SmartColorLamp newColorLamp= new SmartColorLamp(stringName,colorLampStatus);
                                    allDevices.add(newColorLamp);
                                    colorLampList.add(newColorLamp.getName());
                                }else{
                                    myWriter.write("ERROR: Erroneous command!"+"\n");
                                }
                            }else{
                                myWriter.write("ERROR: There is already a smart device with same name!"+"\n");
                            }


                        }else if(commandsLine.size()==6){
                            /**

                             If the command line contains 6 elements and the second element is "SmartColorLamp",
                             this block creates a new SmartColorLamp object with the specified name, color code,
                             and brightness. If the name is not already used by another smart device, the new object
                             is added to the allDevices list and colorLampList. If the color code or brightness is
                             out of range, or if the command is formatted incorrectly, an error message is printed.
                             @param commandsLine a list of Strings containing the command and its parameters
                             */
                            String stringName=commandsLine.get(2);
                            if (!plugList.contains(stringName)& !cameraList.contains(stringName)& !lampList.contains(stringName)&!colorLampList.contains(stringName) ){
                                Boolean colorLampStatus=false;
                                int intColorLampBrightness=Integer.parseInt(commandsLine.get(5));
                                if (commandsLine.get(3).equals("On")){
                                    colorLampStatus=true;
                                    String codeControl=commandsLine.get(4).substring(0,2);
                                    if (codeControl.equals("0x")){
                                        try {
                                            int intColorCode=Integer.parseInt(commandsLine.get(4).substring(2), 16);
                                            if (intColorCode < 0x000000 || intColorCode > 0xFFFFFF){
                                                if (intColorLampBrightness >= 0 && intColorLampBrightness <= 100){
                                                    SmartColorLamp newColorLamp= new SmartColorLamp(stringName,colorLampStatus,intColorCode,intColorLampBrightness,true);
                                                    colorLampList.add(newColorLamp.getName());
                                                    allDevices.add(newColorLamp);
                                                }else{
                                                    myWriter.write("ERROR: Brightness must be in range of 0%-100%!"+"\n");
                                                }
                                            }else{
                                                myWriter.write("ERROR: Color code value must be in range of 0x0-0xFFFFFF!"+"\n");
                                            }

                                        } catch (NumberFormatException e) {
                                            myWriter.write("ERROR: Erroneous command!"+"\n");
                                        }

                                    }else {
                                        try{
                                            int intKelvin=Integer.parseInt(commandsLine.get(4));
                                            if (intKelvin >= 2000 && intKelvin <= 6500){
                                                int intBrightness=Integer.parseInt(commandsLine.get(5));
                                                if (intBrightness >= 0 && intBrightness <= 100) {
                                                    SmartColorLamp newColorLamp= new SmartColorLamp(stringName,colorLampStatus,intKelvin,intColorLampBrightness);
                                                    allDevices.add(newColorLamp);
                                                    colorLampList.add(newColorLamp.getName());
                                                }
                                                else{
                                                    myWriter.write("ERROR: Brightness must be in range of 0%-100%!"+"\n");
                                                }
                                            }else{
                                                myWriter.write("ERROR: Kelvin value must be in range of 2000K-6500K!"+"\n");
                                            }


                                        }catch (NumberFormatException e){
                                            myWriter.write("ERROR: Erroneous command!");
                                        }

                                    }
                                }else if (commandsLine.get(3).equals("Off")) {
                                    colorLampStatus=false;
                                    String codeControl=commandsLine.get(4).substring(0,2);
                                    if (codeControl.equals("0x")){
                                        try {
                                            int intColorCode=Integer.parseInt(commandsLine.get(4).substring(2), 16);
                                            if (intColorCode < 0x000000 || intColorCode > 0xFFFFFF){
                                                if (intColorLampBrightness >= 0 && intColorLampBrightness <= 100){
                                                    SmartColorLamp newColorLamp= new SmartColorLamp(stringName,colorLampStatus,intColorCode,intColorLampBrightness,true);
                                                    colorLampList.add(newColorLamp.getName());
                                                    allDevices.add(newColorLamp);
                                                }else{
                                                    myWriter.write("ERROR: Brightness must be in range of 0%-100%!"+"\n");
                                                }
                                            }else{
                                                myWriter.write("ERROR: Color code value must be in range of 0x0-0xFFFFFF!"+"\n");
                                            }

                                        } catch (NumberFormatException e) {
                                            myWriter.write("ERROR: Erroneous command!"+"\n");
                                        }
                                    }else {
                                        try{
                                            int intKelvin=Integer.parseInt(commandsLine.get(4));
                                            if (intKelvin >= 2000 && intKelvin <= 6500){
                                                int intBrightness=Integer.parseInt(commandsLine.get(5));
                                                if (intBrightness >= 0 && intBrightness <= 100) {
                                                    SmartColorLamp newColorLamp= new SmartColorLamp(stringName,colorLampStatus,intKelvin,intColorLampBrightness);
                                                    allDevices.add(newColorLamp);
                                                    colorLampList.add(newColorLamp.getName());
                                                }
                                                else{
                                                    myWriter.write("ERROR: Brightness must be in range of 0%-100%!"+"\n");
                                                }
                                            }else{
                                                myWriter.write("ERROR: Kelvin value must be in range of 2000K-6500K!"+"\n");
                                            }


                                        }catch (NumberFormatException e){
                                            myWriter.write("ERROR: Erroneous command!"+"\n");
                                        }
                                    }
                                }else{
                                    myWriter.write("ERROR: Erroneous command!"+"\n");
                                }
                            }else{
                                myWriter.write("ERROR: There is already a smart device with same name!"+"\n");
                            }
                        } else{
                            myWriter.write("ERROR: Erroneous command!"+"\n");}
                    }
                }
                else if (commandsLine.get(0).equals("Remove")){
                    /**

                     Removes a device from the system based on the command given in the commandsLine parameter.
                     The method first checks if the command line has two arguments, otherwise it prints an error message.
                     If the command is valid and the specified device exists in any of the device lists, the device is removed from the corresponding list.
                     If the specified device is a SmartCamera or a SmartPlug, it is removed from the allDevices list as well.
                     @param commandsLine A List of strings containing the command to remove a device and the name of the device.
                     @throws None
                     @return None
                     @see SmartCamera
                     @see SmartPlug
                     @see SmartLamp
                     @see SmartColorLamp
                     */
                    if(commandsLine.size()!=2){
                        myWriter.write("ERROR: Erroneous command!"+"\n");
                    }else{
                        if (colorLampList.contains(commandsLine.get(1))  || lampList.contains(commandsLine.get(1)) ||
                                cameraList.contains(commandsLine.get(1)) || plugList.contains(commandsLine.get(1))){
                            if (cameraList.contains(commandsLine.get(1))){
                                cameraList.remove(commandsLine.get(1));
                            }else if (plugList.contains(commandsLine.get(1))){
                                plugList.remove(commandsLine.get(1));
                            }
                            else if (lampList.contains(commandsLine.get(1))){
                                lampList.remove(commandsLine.get(1));
                            }
                            else if (colorLampList.contains(commandsLine.get(1))){
                                colorLampList.remove(commandsLine.get(1));
                            }
                            Object object ="";
                            for (Object device:allDevices){
                                if(device instanceof SmartCamera){
                                    if (((SmartCamera) device).getName().equals(commandsLine.get(1))){
                                        object=device;
                                        device=null;
                                    }
                                }
                                else if(device instanceof SmartPlug){
                                    if (((SmartPlug) device).getName().equals(commandsLine.get(1))){
                                        object=device;
                                        device=null;
                                    }
                                }
                                else if(device instanceof SmartLamp){
                                    if (((SmartLamp) device).getName().equals(commandsLine.get(1))){
                                        object=device;
                                        device=null;
                                    }
                                }
                                else if(device instanceof SmartColorLamp){
                                    if (((SmartColorLamp) device).getName().equals(commandsLine.get(1))){
                                        object=device;
                                        device=null;
                                    }
                                }
                            }allDevices.remove(object);
                        }else{
                            myWriter.write("ERROR: There is not such a device!"+"\n");

                        }
                    }

                }
                else if (commandsLine.get(0).equals("ChangeName")){
                    /**

                     Allows the user to rename a smart device with a new name.
                     Checks if the list size is equal to three and if the first command is not equal to the second command.
                     If it is a smart plug, updates the name in the plugList.
                     If it is a smart camera, updates the name in the cameraList.
                     If it is a smart color lamp, updates the name in the colorLampList.
                     If it is a smart lamp, updates the name in the lampList.
                     Prints an error message if the second command is already present in any of the smart device lists or if the first and second commands are the same.
                     Prints an error message if the list size is not equal to three.
                     */
                    if (commandsLine.size()==3){
                        if (!commandsLine.get(1).equals(commandsLine.get(2))){
                            if (plugList.contains(commandsLine.get(1))){
                                if (!plugList.contains(commandsLine.get(2))& !cameraList.contains(commandsLine.get(2))& !lampList.contains(commandsLine.get(2))&
                                        !colorLampList.contains(commandsLine.get(2))){
                                    if (plugList.contains(commandsLine.get(1))){
                                        plugList.remove(commandsLine.get(1));
                                        plugList.add(commandsLine.get(2));
                                    }else if (cameraList.contains(commandsLine.get(1))){
                                        cameraList.remove(commandsLine.get(1));
                                        cameraList.add(commandsLine.get(2));
                                    }
                                    else if (colorLampList.contains(commandsLine.get(1))){
                                        colorLampList.remove(commandsLine.get(1));
                                        colorLampList.add(commandsLine.get(2));
                                    }
                                    else if (lampList.contains(commandsLine.get(1))){
                                        lampList.remove(commandsLine.get(1));
                                        lampList.add(commandsLine.get(2));
                                    }
                                }else{
                                    myWriter.write("ERROR: There is already a smart device with same name!"+"\n");
                                }
                            }
                        }else{
                            myWriter.write("ERROR: Both of the names are the same, nothing changed!"+"\n");
                        }
                    }else{
                        myWriter.write("ERROR: Erroneous command!"+"\n");
                    }

                    for (Object device:allDevices){
                        if(device instanceof SmartCamera){
                            if (((SmartCamera) device).getName().equals(commandsLine.get(1))){
                                ((SmartCamera) device).setName(commandsLine.get(2));
                            }
                        }
                        else if(device instanceof SmartPlug){
                            if (((SmartPlug) device).getName().equals(commandsLine.get(1))){
                                ((SmartPlug) device).setName(commandsLine.get(2));
                            }
                        }
                        else if(device instanceof SmartLamp){
                            if (((SmartLamp) device).getName().equals(commandsLine.get(1))){
                                ((SmartLamp) device).setName(commandsLine.get(2));
                            }
                        }
                        else if(device instanceof SmartColorLamp){
                            if (((SmartColorLamp) device).getName().equals(commandsLine.get(1))){
                                ((SmartColorLamp) device).setName(commandsLine.get(2));
                            }
                        }
                    }}
                else if (commandsLine.get(0).equals("Switch")){
                    /**

                     This method allows the user to switch a smart device on or off based on its name.
                     It receives a list of commands from the user and checks if the size is equal to two.
                     If the size is two, it checks if the first command is a valid smart device name.
                     If the name is valid, it searches for the device in the list of all devices and switches it on or off based on the second command.
                     If the name is not valid, it prints an error message.
                     */
                    if (colorLampList.contains(commandsLine.get(1))  || lampList.contains(commandsLine.get(1)) ||
                            cameraList.contains(commandsLine.get(1)) || plugList.contains(commandsLine.get(1))){
                        for (Object device:allDevices){
                            if(device instanceof SmartCamera){
                                if (((SmartCamera) device).getName().equals(commandsLine.get(1))){
                                    ((SmartCamera) device).switchOnOff(commandsLine.get(2),initialTime);
                                    myWriter.write(((SmartCamera) device).switchOnOff(commandsLine.get(2),initialTime));

                                }
                            }
                            else if(device instanceof SmartPlug){
                                if (((SmartPlug) device).getName().equals(commandsLine.get(1))){
                                    ((SmartPlug) device).switchOnOff(commandsLine.get(2),initialTime);
                                    myWriter.write(((SmartPlug) device).switchOnOff(commandsLine.get(2),initialTime));
                                }
                            }else if (device instanceof  SmartLamp){
                                if (((SmartLamp) device).getName().equals(commandsLine.get(1))){
                                    ((SmartLamp) device).switchOnOff(commandsLine.get(2));
                                    myWriter.write(((SmartLamp) device).switchOnOff(commandsLine.get(2)));
                                }
                            }else if (device instanceof SmartColorLamp){
                                if (((SmartColorLamp) device).getName().equals(commandsLine.get(1))){
                                    ((SmartColorLamp) device).switchOnOff(commandsLine.get(2));
                                    myWriter.write(((SmartColorLamp) device).switchOnOff(commandsLine.get(2)));
                                }
                            }
                        }
                    }else{
                        myWriter.write("ERROR: There is not such a device!"+"\n");
                    }


                }
                else if (commandsLine.get(0).equals("PlugIn")){
                    /**

                     This method receives a list of commands from the user and checks if the list size is equal to three.
                     If the list size is not equal to three, it prints an error message.
                     If the first command is not a smart plug, it prints an error message.
                     If the first command is a smart plug, it finds the corresponding smart plug device and tries to plug it in with the given ampere value.
                     If the given ampere value is not a positive number, it prints an error message.
                     If the given ampere value is not a number, it prints an error message.
                     @param commandsLine A list of commands from the user including the device name and the ampere value
                     */
                    if(commandsLine.size()!=3){
                        myWriter.write("ERROR: Erroneous command!"+"\n");
                    }else{
                        if (!plugList.contains(commandsLine.get(1))){
                            myWriter.write("ERROR: This device is not a smart lamp!"+"\n");
                        }else{
                            for (Object device:allDevices){
                                if(device instanceof SmartPlug){
                                    if (((SmartPlug) device).getName().equals(commandsLine.get(1))){
                                        try{
                                            if(Double.parseDouble(commandsLine.get(2))>0){
                                                ((SmartPlug) device).plugIn("In",Double.parseDouble(commandsLine.get(2)));
                                                myWriter.write(((SmartPlug) device).plugIn("In",Double.parseDouble(commandsLine.get(2))));
                                            }else{
                                                myWriter.write("ERROR: Ampere value must be a positive number!"+"\n");
                                            }
                                        }catch (NumberFormatException e){
                                            myWriter.write("ERROR: Erroneous command!"+"\n");
                                        }

                                    }
                                }
                            }
                        }
                    }
                }
                else if (commandsLine.get(0).equals("PlugOut")){
                    /**

                     This method allows the user to unplug a smart plug device by turning it off and removing it from the socket.
                     It receives a list of commands from the user and checks if the list size is equal to two.
                     If the list size is not two, it prints an error message.
                     If the device specified in the command is not a smart plug, it prints an error message.
                     If the device is a smart plug, it removes it from the socket and turns it off by calling the plugOut() and switchOnOff() methods respectively.
                     @param commandsLine a list of commands containing the device name to unplug
                     @param plugList a list of all smart plug devices
                     @param allDevices a list of all smart devices
                     @param initialTime the initial time at which the command is executed
                     */
                    if(commandsLine.size()!=2){
                        myWriter.write("ERROR: Erroneous command!"+"\n");
                    }else{
                        if (!plugList.contains(commandsLine.get(1))){
                            myWriter.write("ERROR: This device is not a smart lamp!"+"\n");
                        }else{
                            for (Object device:allDevices){
                                if(device instanceof SmartPlug){
                                    if (((SmartPlug) device).getName().equals(commandsLine.get(1))){
                                        ((SmartPlug) device).plugOut("Out");
                                        myWriter.write(((SmartPlug) device).plugOut("Out"));
                                        ((SmartPlug) device).switchOnOff("Off",initialTime);
                                        myWriter.write(((SmartPlug) device).switchOnOff("Off",initialTime));
                                    }
                                }
                            }
                        }

                    }
                }
                else if (commandsLine.get(0).equals("SetKelvin")){
                    /**

                     Sets the Kelvin value of a smart lamp or color lamp based on the input command.
                     @param commandsLine The command input by the user, in the format "setKelvinValue [device name] [Kelvin value]"
                     */
                    if (commandsLine.size()!=3){
                        myWriter.write("ERROR: Erroneous command!"+"\n");
                    }
                    else{
                        if (lampList.contains(commandsLine.get(1)) || colorLampList.contains(commandsLine.get(1))
                                || cameraList.contains(commandsLine.get(1)) || plugList.contains(commandsLine.get(1))) {
                            if (lampList.contains(commandsLine.get(1))){
                                for (Object device: allDevices){
                                    if (device instanceof SmartLamp){
                                        if (((SmartLamp) device).getName().equals(commandsLine.get(1))){
                                            try {
                                                int num = Integer.parseInt(commandsLine.get(2));
                                                ((SmartLamp) device).setKelvinValue(num);
                                                myWriter.write(((SmartLamp) device).setKelvinValue(num));
                                            } catch (NumberFormatException e) {
                                                myWriter.write("ERROR: Erroneous command!"+"\n");
                                            }
                                        }
                                    }
                                }
                            }
                            else if (colorLampList.contains(commandsLine.get(1))){

                                for (Object device: allDevices){
                                    if (device instanceof SmartColorLamp){
                                        if (((SmartColorLamp) device).getName().equals(commandsLine.get(1))){
                                            try {
                                                int num = Integer.parseInt(commandsLine.get(2));
                                                ((SmartLamp) device).setKelvinValue(num);
                                                myWriter.write(((SmartLamp) device).setKelvinValue(num));
                                            } catch (NumberFormatException e) {
                                                myWriter.write("ERROR: Erroneous command!"+"\n");

                                            }
                                        }
                                    }
                                }
                            }else{
                                myWriter.write("ERROR: This device is not a smart lamp!"+"\n");
                            }


                        }else{
                            myWriter.write("ERROR: There is not such a device!"+"\n");
                        }
                    }

                }
                else if (commandsLine.get(0).equals("SetBrightness")){
                    /**

                     Sets the brightness value of a smart lamp or color lamp.
                     @param commandsLine a List of Strings representing the user command and its arguments
                     @param allDevices a List of Objects representing all devices in the system
                     @param lampList a List of Strings representing the names of all smart lamps in the system
                     @param colorLampList a List of Strings representing the names of all color lamps in the system
                     */
                    if (commandsLine.size()!=3){
                        myWriter.write("ERROR: Erroneous command!"+"\n");
                    }else{
                        if (lampList.contains(commandsLine.get(1)) || colorLampList.contains(commandsLine.get(1))
                                || cameraList.contains(commandsLine.get(1)) || plugList.contains(commandsLine.get(1))) {
                            if (lampList.contains(commandsLine.get(1))){
                                for (Object device: allDevices){
                                    if (device instanceof SmartLamp){
                                        if (((SmartLamp) device).getName().equals(commandsLine.get(1))){
                                            try {
                                                int num = Integer.parseInt(commandsLine.get(2));
                                                ((SmartLamp) device).setBrightnessValue(num);
                                                myWriter.write(((SmartLamp) device).setBrightnessValue(num));
                                            } catch (NumberFormatException e) {
                                                myWriter.write("ERROR: Erroneous command!"+"\n");
                                            }

                                        }
                                    }
                                }

                            }else if (colorLampList.contains(commandsLine.get(1))){
                                for (Object device: allDevices){
                                    if (device instanceof SmartColorLamp){
                                        if (((SmartColorLamp) device).getName().equals(commandsLine.get(1))){
                                            try {
                                                int num = Integer.parseInt(commandsLine.get(2));
                                                ((SmartColorLamp) device).setBrightnessValue(num);
                                                myWriter.write(((SmartColorLamp) device).setBrightnessValue(num));
                                            } catch (NumberFormatException e) {
                                                myWriter.write("ERROR: Erroneous command!"+"\n");
                                            }

                                        }
                                    }
                                }

                            }else{
                                myWriter.write("ERROR: This device is not a smart lamp!"+"\n");
                            }
                        }else{
                            myWriter.write("ERROR: There is not such a device!"+"\n");
                        }
                    }
                }
                else if (commandsLine.get(0).equals("SetColorCode")){
                    /**

                     Sets the color code value of a smart color lamp device.
                     @param commandsLine the list of user input commands
                     @param colorLampList the list of registered smart color lamps
                     @param allDevices the list of all registered smart devices
                     */
                    if(commandsLine.size()!=3){
                        myWriter.write("ERROR: Erroneous command!"+"\n");

                    }else{
                        if (lampList.contains(commandsLine.get(1)) || colorLampList.contains(commandsLine.get(1))
                                || cameraList.contains(commandsLine.get(1)) || plugList.contains(commandsLine.get(1))){
                            if (colorLampList.contains(commandsLine.get(1))){
                                for(Object device:allDevices){
                                    if (device instanceof SmartColorLamp){
                                        if (((SmartColorLamp) device).getName().equals(colorLampList.get(1))){
                                            ((SmartColorLamp) device).setColorCodeValue(colorLampList.get(2));
                                            myWriter.write(((SmartColorLamp) device).setColorCodeValue(colorLampList.get(2)));
                                        }
                                    }
                                }
                            }
                            else{
                                myWriter.write("ERROR: This device is not a smart color lamp!"+"\n");
                            }

                        }else{
                            myWriter.write("ERROR: There is not such a device!"+"\n");

                        }
                    }

                }
                else if (commandsLine.get(0).equals("SetWhite")){
                    /**

                     Sets the white color and brightness of a smart lamp or smart color lamp with the given name.
                     If the device is not found, prints an error message.
                     If the device is found but is not a smart lamp or smart color lamp, prints an error message.
                     If the command is not in the correct format, prints an error message.
                     @param commandsLine A List<String> containing the command line arguments.*/
                    if(commandsLine.size()!=4){
                        myWriter.write("ERROR: Erroneous command!");}
                    else{
                        if (lampList.contains(commandsLine.get(1)) || colorLampList.contains(commandsLine.get(1))
                                || cameraList.contains(commandsLine.get(1)) || plugList.contains(commandsLine.get(1))) {
                            if (lampList.contains(commandsLine.get(1))){
                                for (Object device: allDevices){
                                    if (device instanceof SmartLamp){
                                        if (((SmartLamp) device).getName().equals(commandsLine.get(1))){
                                            try {
                                                int brightness=Integer.parseInt(commandsLine.get(3));
                                                int kelvin = Integer.parseInt(commandsLine.get(2));
                                                ((SmartLamp) device).setWhite(kelvin,brightness);
                                                myWriter.write(((SmartLamp) device).setWhite(kelvin,brightness));
                                            } catch (NumberFormatException e) {
                                                myWriter.write("ERROR: Erroneous command!"+"\n");
                                            }

                                        }
                                    }
                                }

                            }else if (colorLampList.contains(commandsLine.get(1))){
                                for (Object device: allDevices){
                                    if (device instanceof SmartColorLamp){
                                        if (((SmartColorLamp) device).getName().equals(commandsLine.get(1))){
                                            try {
                                                int brightness=Integer.parseInt(commandsLine.get(3));
                                                int kelvin = Integer.parseInt(commandsLine.get(2));
                                                ((SmartColorLamp) device).setWhite(kelvin,brightness);
                                                myWriter.write(((SmartColorLamp) device).setWhite(kelvin,brightness));
                                            } catch (NumberFormatException e) {
                                                myWriter.write("ERROR: Erroneous command!"+"\n");
                                            }

                                        }
                                    }
                                }

                            }else{
                                myWriter.write("ERROR: This device is not a smart lamp!"+"\n");
                            }
                        }else{
                            myWriter.write("ERROR: There is not such a device!"+"\n");
                        }

                    }


                }
                else if(commandsLine.get(0).equals("SetColor")){
                    /**

                     Sets the color and brightness of the specified smart color lamp.
                     @param commandsLine the command line input containing device name, color code, and brightness
                     @throws NumberFormatException if the brightness value is not a valid integer
                     @throws NoSuchElementException if the specified device does not exist in the system
                     @throws ClassCastException if the specified device is not a smart color lamp
                     */
                    if (colorLampList.contains(commandsLine.get(1))){
                        for (Object device: allDevices){
                            if (device instanceof SmartColorLamp){
                                if (((SmartColorLamp) device).getName().equals(commandsLine.get(1))){
                                    String colorcd=commandsLine.get(2);
                                    String brightnessvalue= commandsLine.get(3);
                                    ((SmartColorLamp) device).setColor(colorcd,brightnessvalue);
                                    myWriter.write(((SmartColorLamp) device).setColor(colorcd,brightnessvalue));
                                }
                            }
                        }

                    }else{
                        myWriter.write("ERROR: This device is not a smart color lamp!"+"\n");
                    }
                }
                else if (commandsLine.get(0).equals("SetInitialTime") & i!=0){
                    /**

                     Sets the initial time of the system.
                     @param commandsLine the list of command parameters
                     @param i the current command index
                     @throws IndexOutOfBoundsException if the command index is out of bounds
                     */
                    myWriter.write("ERROR: Erroneous command!");
                }
                else if (commandsLine.get(0).equals("SetTime")){
                    /**

                     Sets the system time to the given time and updates the status of the devices according to their scheduled switch times.
                     If the given time is before the current time, an error message is printed.
                     If the given time is the same as the current time, nothing is changed.
                     @param commandsLine The list of command arguments, where the first argument is the command name and the second argument is the new time to be set.
                     */
                    String date = commandsLine.get(1);
                    try{
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");
                        newTime = LocalDateTime.parse(date, formatter);
                        if (initialTime.isBefore(newTime)){
                            oldTime=initialTime;
                            initialTime=newTime;
                            for(int k=0;k<switchTimeList.size();k++){
                                switchTime = LocalDateTime.parse(switchTimeList.get(k).get(1), formatter);
                                if (initialTime.isAfter(switchTime)){
                                    for (Object device:allDevices){
                                        if(device instanceof SmartCamera){
                                            if (((SmartCamera) device).getName().equals(switchTimeList.get(k).get(0))){
                                                ((SmartCamera) device).setStatus(!((SmartCamera) device).getStatus());
                                                ((SmartCamera) device).usedStorage(oldTime,((SmartCamera) device).getSwitchTime());
                                                ((SmartCamera) device).setSwitchTime(null);
                                            }
                                        }
                                        else if(device instanceof SmartPlug){
                                            if (((SmartPlug) device).getName().equals(switchTimeList.get(k).get(0))){
                                                ((SmartPlug) device).setStatus(((SmartPlug) device).getStatus());
                                                ((SmartPlug) device).energyConsumption(oldTime,((SmartPlug) device).getSwitchTime());
                                                ((SmartPlug) device).setSwitchTime(null);
                                            }
                                        }
                                        else if(device instanceof SmartLamp){
                                            if (((SmartLamp) device).getName().equals(switchTimeList.get(k).get(0))){
                                                ((SmartLamp) device).setStatus(((SmartLamp) device).getStatus());
                                                ((SmartLamp) device).setSwitchTime(null);
                                            }
                                        }
                                        else if(device instanceof SmartColorLamp){
                                            if (((SmartColorLamp) device).getName().equals(switchTimeList.get(k).get(0))){
                                                ((SmartColorLamp) device).setStatus(((SmartColorLamp) device).getStatus());
                                                ((SmartColorLamp) device).setSwitchTime(null);
                                            }
                                        }
                                    }

                                }
                            }
                        }else if(initialTime.isEqual(newTime)){
                            myWriter.write("ERROR: There is nothing to change!"+"\n");
                        }

                        else{
                            myWriter.write("Time cannot be reversed"+"\n");
                        }
                    } catch(DateTimeParseException e){
                        myWriter.write("ERROR: Time format is not correct!"+"\n");
                    }

                }
                else if (commandsLine.get(0).equals("SkipMinutes")){
                    /**

                     Skips the given number of minutes from the current time and updates the status of devices
                     according to their switch times if necessary.
                     @param commandsLine an ArrayList of String representing the command and its arguments
                     */
                    if (commandsLine.size()==2){
                        try{
                            int minutes = Integer.parseInt(commandsLine.get(1));
                            if (minutes>0){
                                oldTime=initialTime;
                                initialTime=initialTime.plusMinutes(minutes);
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");
                                for (ArrayList<String> strings : switchTimeList) {
                                    switchTime = LocalDateTime.parse(strings.get(1), formatter);
                                    if (initialTime.isAfter(switchTime)) {
                                        for (Object device : allDevices) {
                                            if (device instanceof SmartCamera) {
                                                if (((SmartCamera) device).getName().equals(strings.get(0))) {
                                                    ((SmartCamera) device).setStatus(!((SmartCamera) device).getStatus());
                                                    ((SmartCamera) device).usedStorage(oldTime, ((SmartCamera) device).getSwitchTime());
                                                }if(((SmartCamera) device).getStatus()){
                                                    ((SmartCamera) device).usedStorage(oldTime, ((SmartCamera) device).getSwitchTime());

                                                }
                                            } else if (device instanceof SmartPlug) {
                                                if (((SmartPlug) device).getName().equals(strings.get(0))) {
                                                    ((SmartPlug) device).setStatus(((SmartPlug) device).getStatus());
                                                    ((SmartPlug) device).energyConsumption(oldTime, ((SmartPlug) device).getSwitchTime());
                                                }if(((SmartPlug)device).getStatus()){
                                                    ((SmartPlug) device).energyConsumption(oldTime, ((SmartPlug) device).getSwitchTime());
                                                }
                                            } else if (device instanceof SmartLamp) {
                                                if (((SmartLamp) device).getName().equals(strings.get(0))) {
                                                    ((SmartLamp) device).setStatus(((SmartLamp) device).getStatus());
                                                }
                                            } else if (device instanceof SmartColorLamp) {
                                                if (((SmartColorLamp) device).getName().equals(strings.get(0))) {
                                                    ((SmartColorLamp) device).setStatus(((SmartColorLamp) device).getStatus());
                                                }
                                            }
                                        }
                                    }

                                }


                            }else if(minutes<0){
                                myWriter.write("ERROR: Time cannot be reversed!"+"\n");
                            }else{
                                myWriter.write("ERROR: There is nothing to skip!"+"\n");
                            }
                        }catch (NumberFormatException e) {
                            myWriter.write("ERROR: Erroneous Command!"+"\n");}
                    }else{
                        myWriter.write("ERROR: Erroneous Command!"+"\n");
                    }
                }
                else if (commandsLine.get(0).equals("SetSwitchTime")){
                    /**

                     Sets the switch time for a device and adds the switch time to the switchTimeList.
                     Also updates the switch time for the corresponding device in allDevices list.
                     @param commandsLine the list of commands entered by the user
                     @throws DateTimeParseException if the date format is incorrect
                     */


                    if (plugList.contains(commandsLine.get(1)) || cameraList.contains(commandsLine.get(1)) ||
                            lampList.contains(commandsLine.get(1)) ||colorLampList.contains(commandsLine.get(1))){
                        String date=commandsLine.get(2);
                        try{
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");
                            switchTime = LocalDateTime.parse(date, formatter);
                            if (initialTime.isBefore(switchTime)|| initialTime.isEqual(switchTime)){
                                ArrayList<String> row1 = new ArrayList<>();
                                row1.add(commandsLine.get(1));
                                row1.add(date);
                                onlySwitchTime.add(switchTime);
                                switchTimeList.add(row1);
                            }
                            else{
                                myWriter.write("ERROR: Switch time cannot be in the past!"+"\n");
                            }

                            for (Object device:allDevices){
                                if(device instanceof SmartCamera){
                                    if (initialTime.isBefore(switchTime)|| initialTime.isEqual(switchTime)){
                                        if (((SmartCamera) device).getName().equals(commandsLine.get(1))){
                                            ((SmartCamera) device).setSwitchTime(switchTime);
                                        }
                                    }
                                }
                                else if(device instanceof SmartPlug){
                                    if (initialTime.isBefore(switchTime)|| initialTime.isEqual(switchTime)){
                                        if (((SmartPlug) device).getName().equals(commandsLine.get(1))){
                                            ((SmartPlug) device).setSwitchTime(switchTime);
                                        }
                                    }

                                }
                                else if(device instanceof SmartLamp){
                                    if (initialTime.isBefore(switchTime)|| initialTime.isEqual(switchTime)){
                                        if (((SmartLamp) device).getName().equals(commandsLine.get(1))){
                                            ((SmartLamp) device).setSwitchTime(switchTime);
                                        }
                                    }

                                }
                                else if(device instanceof SmartColorLamp){
                                    if (initialTime.isBefore(switchTime)|| initialTime.isEqual(switchTime)){
                                        if (((SmartColorLamp) device).getName().equals(commandsLine.get(1))){
                                            ((SmartColorLamp) device).setSwitchTime(switchTime);
                                        }
                                    }
                                    if (((SmartColorLamp) device).getName().equals(commandsLine.get(1))){
                                        ((SmartColorLamp) device).setSwitchTime(switchTime);
                                    }
                                }
                            }
                        } catch(DateTimeParseException e){
                            myWriter.write("ERROR: Erroneous Command!"+"\n");
                        }
                    }else{
                        myWriter.write("ERROR: There is not such a device!"+"\n");
                    }
                }
                else if (commandsLine.get(0).equals("Nop")){
                    /**

                     Sets the switch time of a device and adds it to the switchTimeList.
                     If the switch time is in the past, prints an error message.
                     @param commandsLine the command line input as an ArrayList of Strings
                     @param initialTime the initial time of the system as a LocalDateTime object
                     @param switchTimeList the list of devices with their switch times as an ArrayList of ArrayLists of Strings
                     @param allDevices the list of all devices in the system as an ArrayList of Objects
                     @param onlySwitchTime the list of only switch times as an ArrayList of LocalDateTime objects
                     */

                    if(!onlySwitchTime.isEmpty()){
                        Collections.sort(onlySwitchTime);
                        try{
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");
                            for (int k=0;i<onlySwitchTime.size();k++){
                                if(onlySwitchTime.get(k).isAfter(initialTime));
                                newTime=onlySwitchTime.get(k);
                                break;
                            }
                            if (initialTime.isBefore(newTime)){
                                oldTime=initialTime;
                                initialTime=newTime;
                                for(int k=0;k<switchTimeList.size();k++){
                                    switchTime = LocalDateTime.parse(switchTimeList.get(k).get(1), formatter);
                                    if (initialTime.isAfter(switchTime)){
                                        for (Object device:allDevices){
                                            if(device instanceof SmartCamera){
                                                if (((SmartCamera) device).getName().equals(switchTimeList.get(k).get(0))){
                                                    ((SmartCamera) device).setStatus(!((SmartCamera) device).getStatus());
                                                    ((SmartCamera) device).usedStorage(oldTime,((SmartCamera) device).getSwitchTime());
                                                    ((SmartCamera) device).setSwitchTime(null);
                                                    onlySwitchTime.remove(newTime);
                                                }
                                            }
                                            else if(device instanceof SmartPlug){
                                                if (((SmartPlug) device).getName().equals(switchTimeList.get(k).get(0))){
                                                    ((SmartPlug) device).setStatus(((SmartPlug) device).getStatus());
                                                    ((SmartPlug) device).energyConsumption(oldTime,((SmartPlug) device).getSwitchTime());
                                                    ((SmartPlug) device).setSwitchTime(null);
                                                    onlySwitchTime.remove(newTime);
                                                }
                                            }
                                            else if(device instanceof SmartLamp){
                                                if (((SmartLamp) device).getName().equals(switchTimeList.get(k).get(0))){
                                                    ((SmartLamp) device).setStatus(((SmartLamp) device).getStatus());
                                                    ((SmartLamp) device).setSwitchTime(null);
                                                    onlySwitchTime.remove(newTime);
                                                }
                                            }
                                            else if(device instanceof SmartColorLamp){
                                                if (((SmartColorLamp) device).getName().equals(switchTimeList.get(k).get(0))){
                                                    ((SmartColorLamp) device).setStatus(((SmartColorLamp) device).getStatus());
                                                    ((SmartColorLamp) device).setSwitchTime(null);
                                                    onlySwitchTime.remove(newTime);
                                                }
                                            }
                                        }

                                    }
                                }
                            }else{
                                myWriter.write("Time cannot be reversed"+"\n");
                            }
                        } catch(DateTimeParseException e){
                            myWriter.write("ERROR: Erroneous Command!"+"\n");
                        }

                    }else{
                        myWriter.write("ERROR: There is nothing to switch!"+"\n");
                    }
                }
                else if (commandsLine.get(0).equals("ZReport")){
                    /**

                     Prints information about all devices in the system (smart cameras, smart lamps, smart color lamps, and smart plugs)
                     using their respective methods for generating device information.
                     */
                    myWriter.write(initialTime.toString()+"\n");
                    for (Object device:allDevices){
                        if(device instanceof SmartCamera){
                            ((SmartCamera) device).cameraInfo();
                            myWriter.write(((SmartCamera) device).cameraInfo());
                        }
                        if(device instanceof SmartLamp){
                            ((SmartLamp) device).lampInfo();
                            myWriter.write(((SmartLamp) device).lampInfo()+"\n");

                        }
                        if (device instanceof SmartColorLamp){
                            ((SmartColorLamp) device).colorLampInfo();
                            myWriter.write(((SmartColorLamp) device).colorLampInfo()+"\n");
                        }
                        if (device instanceof SmartPlug){
                            ((SmartPlug) device).plugInfo();
                            myWriter.write(((SmartPlug) device).plugInfo()+"\n");
                        }
                    }
                }else{
                    myWriter.write( "ERROR: Erroneous Command!"+"\n");
                }
            }
            if(!inputLines.get(inputLines.size()-1).equals("Zreport")){
                /**

                 If the last command is not "Zreport", prints the current time and information for all devices.
                 Information is printed using the corresponding device's method, such as cameraInfo() for SmartCamera.
                 @param inputLines the ArrayList of input lines
                 @param allDevices the ArrayList of all devices
                 @param initialTime the initial time of the system
                 */
                myWriter.write(initialTime.toString()+"\n");
                for (Object device:allDevices){
                    if(device instanceof SmartCamera){
                        ((SmartCamera) device).cameraInfo();
                        myWriter.write(((SmartCamera) device).cameraInfo());
                    }
                    if(device instanceof SmartLamp){
                        ((SmartLamp) device).lampInfo();
                        myWriter.write(((SmartLamp) device).lampInfo()+"\n");

                    }
                    if (device instanceof SmartColorLamp){
                        ((SmartColorLamp) device).colorLampInfo();
                        myWriter.write(((SmartColorLamp) device).colorLampInfo()+"\n");
                    }
                    if (device instanceof SmartPlug){
                        ((SmartPlug) device).plugInfo();
                        myWriter.write(((SmartPlug) device).plugInfo()+"\n");
                    }
                }

            }


        }
        catch (IOException e){
            e.printStackTrace();
        }

}
        }





