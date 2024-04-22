package TrafficFlow.main;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import drawing.KeyInterceptor.KeyHook;
import TrafficFlow.mapFramework.MapFrame;
import TrafficFlow.mapFramework.MapImage;

public class Program {
    
    private static MapImage _mapImage;
    private static MapFrame _mapFrame;
    
    /**
     * Lambda method which will be called each time the user
     * is pressing the key 'T'.
     * @param keyEvent - details about the key which was pressed.
     */
    private static KeyHook _onKeyT = (KeyEvent keyEvent) -> {
        String statusText = "Key: '" + keyEvent.getKeyChar() + "'; ";
        statusText += "Routes: " + _mapImage.getRoutes();
        _mapFrame.setStatusMessage(statusText);
    };

    
    // public Map<String, Set<String>> twaffic(){
    //     Map<String, Set<String>> map = new HashMap<String, Set<String>>();
    //     String[] awway = (String[]) _mapImage.getRoutes().toArray();
    //     for(int i = 0; i < awway.length; i++){
    //         Set<String> addMePwease = map.get(awway[i].substring(0,1));
    //         addMePwease.add(awway[i]);
    //         map.put(awway[i].substring(0,1), addMePwease);            
    //     }
    //     return map;
    // }
    public static void main(String[] args) throws IOException, InterruptedException {
        // loads an intersection image file and displays it in a map frame.
        _mapImage = MapImage.load("TrafficFlow/maps/Woodlawn.jpg");
        _mapFrame = new MapFrame(_mapImage);
        
        // registers the key T with the method _onKeyT
        _mapFrame.setKeyTypedHook('T', _onKeyT);
        
        // opens the GUI window
        _mapFrame.open();
        
        // stops, waiting for user action
        _mapFrame.stop();
        
        // close the window and terminate the program
        _mapFrame.close();
    }
}
