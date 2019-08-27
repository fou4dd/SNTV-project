package sntv;

import com.jfoenix.controls.JFXButton;
import java.time.LocalTime;
import javafx.animation.AnimationTimer; 
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class BusQueue {
    
    private Lignes ligne;
    private AnimationTimer timer;
    public ObservableList<Bus> queue = FXCollections.observableArrayList();

    public BusQueue(Lignes ligne) {
        this.ligne = ligne;
    }
    
    public void run(){

        for(Bus bus : this.ligne.getListDesBus()){
             queue.add(bus);
        }
        
        timer = new AnimationTimer(){
            @Override
            public void handle(long now) {
                
               for(Bus bus : queue){
                   if(bus.isFull() == false){
                       if(java.time.LocalTime.now().isAfter(localTimeOf(bus.getSchedule().getLoadTime()))){
                            //make button enabled and the color green
                            JFXButton button = MainMenuController.buttons.get(bus.getMatricule());
                            button.setDisable(false);
                            button.getStyleClass().add("bus_buttons_special");
                            MainMenuController.updateButtons(bus, button);
                       }
                    } if (bus.isFull() == true || java.time.LocalTime.now().isAfter(localTimeOf(bus.getSchedule().getStartTime()))){
                           //put the bus at the end of the Queue list  
                            JFXButton button = MainMenuController.buttons.get(bus.getMatricule());
                            button.setDisable(true);
                            button.getStyleClass().add("bus_buttons");
                            MainMenuController.updateButtons(bus, button);   
                            
                       }
               }
               
               
                
            }
        };timer.start();
        
        
    }
    
    private LocalTime localTimeOf(String time){
        String[] timeUnits = time.split(":");
        int hours = Integer.parseInt(timeUnits[0]);
        int minuts = Integer.parseInt(timeUnits[1]);
        int seconds = Integer.parseInt(timeUnits[2]);

        LocalTime lt = LocalTime.of(hours, minuts, seconds);

        return lt;
    }
    
    private void stop(){
        timer.stop();
    }

    public Lignes getLigne() {
        return ligne;
    }

    public void setLigne(Lignes ligne) {
        this.ligne = ligne;
    }

    public ObservableList<Bus> getQueue() {
        return this.queue;
    }

    public void setQueue(ObservableList<Bus> queue) {
        this.queue = queue;
    }
    
    
}
