package local.paxbase.web.toolkit.ui.timelinecomponent;

import org.json.JSONArray;
import org.json.JSONObject;

import com.vaadin.annotations.JavaScript;
import com.vaadin.ui.AbstractJavaScriptComponent;

import elemental.json.JsonArray;

@JavaScript({"timelinecomponent-connector.js"})
public class TimelineComponent extends AbstractJavaScriptComponent {
	   
    public interface ValueChangeListener {
        void valueChanged(String[] newData);
    }

    private ValueChangeListener listener;
    
	
	public TimelineComponent() {
		addFunction("valueChanged", arguments -> {
            JsonArray array = arguments.getArray(0);
            String[] values = new String[1];
            values[0] = array.getString(0);
          
            listener.valueChanged(values);
        });
		
	}

    @Override
    protected TimelineComponentState getState() {
        return (TimelineComponentState) super.getState();
    }
}