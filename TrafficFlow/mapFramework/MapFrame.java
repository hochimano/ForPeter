package TrafficFlow.mapFramework;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import drawing.DrawingFrame;
import drawing.KeyInterceptor;

/**
 * Class provide GUI functionality for interacting with a MapImage.
 * Allows the user to pan and zoom over the map, overlay
 * the embedded routes and control the execution flow. 
 * @see MapImage
 * @see #MapFrame(MapImage)
 * @see #step()
 * @see #stop()
 * @see #setStatusMessage(String)
 */
public class MapFrame extends DrawingFrame {
    private MapImage _mapImage;
    private String Collision;
    private HashMap<Integer, HashSet<String>> intervals;
    private HashSet<String> interval;
    private int mapKey;
    // Region: [private] Routes manual display
    private class RouteNodeInfo {
        private int _index;
        private ArrayList<String> _routes; 
        
        public RouteNodeInfo() {
            _index = -1;
            _routes = new ArrayList<String>();
        }
    }
    private HashMap<Character, RouteNodeInfo> _routeInfoMap = new HashMap<Character, RouteNodeInfo>();
    
    private void buildRouteInfoMap() {
        Set<String> routes = _mapImage.getRoutes();
        for(String routeName : routes) {
            char key = Character.toUpperCase(routeName.charAt(0));
            RouteNodeInfo info = null;
            if (_routeInfoMap.containsKey(key)) {
                info = _routeInfoMap.get(key);
            } else {
                info = new RouteNodeInfo();
                _routeInfoMap.put(key, info);
            }
            info._routes.add(routeName);
        }
    }

    private void ColorGraph(){
        ArrayList<String> set = new ArrayList<String>();
        set.addAll(_mapImage.getRoutes());
        HashSet<String> temp = new HashSet<String>();
        intervals.put(0,temp);
        int mapIndex = 1;
        boolean added = false;
        for(String str : set){
            added = false;
            for(HashSet<String> s : intervals.values()){
                if(!checkCollisions(s, str)){
                    s.add(str);
                    added = true;
                }
            }
            if(!added){
                temp = new HashSet<>();
                temp.add(str);
                intervals.put(mapIndex,temp);
                mapIndex++;
            }
        }
        for(Map.Entry<Integer, HashSet<String>> e : intervals.entrySet()){
            System.out.println("Group " + e.getKey());
            for(String string : e.getValue()){
                System.out.printf(string + ", ");
            }
            System.out.println();
        }
    }

    private void showIntervals(){
        if(interval == null){
            mapKey = 0;
            
        }
        mapKey++;
        interval = intervals.get((mapKey) % intervals.size());
        _mapImage.setOverlays(interval);
        setStatusMessage("Routes: " + interval);
        repaint();
        
        
    }

    private boolean checkCollisions(Set<String> s1, String s2){
        for(String s : s1){
            if(_mapImage.collide(s,s2)) return true;
        }
        return false;
    }   


    
    private void jacksShowRoutes(char key){
        List<String> routes = new ArrayList<String>();
        routes.addAll(_routeInfoMap.get(key)._routes);
        if(_mapImage.getOverlays().size() == 0){
            _mapImage.setOverlays(routes.get(0));
            Collision = routes.get(0);
            repaint();
            return;
        }
        String str = routes.get(routes.indexOf(_mapImage.getOverlays().iterator().next()) + 1 >= routes.size() ? 0 :
        routes.indexOf(_mapImage.getOverlays().iterator().next()) + 1);

        System.out.println(routes.indexOf(_mapImage.getOverlays().iterator().next()));

        _mapImage.setOverlays(str);
        setStatusMessage("Routes: " + str);
        repaint();
        Collision = str;
    }

    public void showCollisions(){
        HashSet<String> s = new HashSet<String>();
        if(_mapImage.getOverlays().size() > 1){
            s.add(Collision);
        } else {
            String head = _mapImage.getOverlays().iterator().next();
            s.add(head);
            for(String str : _mapImage.getRoutes()){
                if(_mapImage.collide(str, head)){
                    s.add(str);
                }
            }
        }
        _mapImage.setOverlays(s);
        setStatusMessage("Routes: " + s);
        repaint();
    }

    private void showRoutes() {
        List<String> routes = new ArrayList<String>();
        for(RouteNodeInfo rni : _routeInfoMap.values()) {
            if (rni._index >= 0) {
                routes.add(rni._routes.get(rni._index));
            }
        }
        _mapImage.setOverlays(routes);
        repaint();
    }
    // EndRegion [private]: Routes manual display

    // Region: [private] KeyInterceptor hooks
    private KeyInterceptor.KeyHook _onKeyABCDE = (keyEvent) -> {
        char key = Character.toUpperCase(keyEvent.getKeyChar());
        if (!_routeInfoMap.containsKey(key)) {
            return;
        }
        RouteNodeInfo oInfo = _routeInfoMap.get(key);
        oInfo._index++;
        if (oInfo._index == oInfo._routes.size()) {
            oInfo._index = -1;
        }
        //showRoutes();
        jacksShowRoutes(key);
    };
    
    private KeyInterceptor.KeyHook _onKeyDelete = (keyEvent) -> {
        for(RouteNodeInfo oi : _routeInfoMap.values()) {
            oi._index = -1;
        }
        showRoutes();
    };
    
    private KeyInterceptor.KeyHook _onKeyX = (keyEvent) -> {
        showCollisions(); 

    };

    private KeyInterceptor.KeyHook _onKeyM = (keyEvent) -> {
        showIntervals(); 

};
    
    // EndRegion: [private] KeyInterceptor hooks

    /**
     * Constructs a new MapFrame and loads it with a given MapImage.
     * @param mapImage - MapImage object to be loaded in the frame
     * @throws IOException resources needed to display the MapFrame,
     * such as bitmaps for the control buttons cannot be loaded from the disk.
     */
    public MapFrame(MapImage mapImage) throws IOException {
        super(mapImage);
        intervals = new HashMap<Integer, HashSet<String>>();
        
        
        // adjust window title
        setTitle("Map Framework GUI");
        
        _mapImage = mapImage;
        buildRouteInfoMap();
        
        // hook in the key intercepts
        setKeyTypedHook('A', _onKeyABCDE);
        setKeyTypedHook('B', _onKeyABCDE);
        setKeyTypedHook('C', _onKeyABCDE);
        setKeyTypedHook('D', _onKeyABCDE);
        setKeyTypedHook('E', _onKeyABCDE);
        setKeyTypedHook('X', _onKeyX);
        setKeyTypedHook('M', _onKeyM);

        setKeyTypedHook(KeyEvent.VK_DELETE, _onKeyDelete);

        ColorGraph();
    }

    public interface ColorHelper{
        public boolean check(String s1, String s2);
    }
}
