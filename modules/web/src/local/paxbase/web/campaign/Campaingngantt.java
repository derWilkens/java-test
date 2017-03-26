package local.paxbase.web.campaign;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import com.haulmont.charts.gui.amcharts.model.ChartTheme;
import com.haulmont.charts.gui.amcharts.model.DatePeriod;
import com.haulmont.charts.gui.amcharts.model.Responsive;
import com.haulmont.charts.gui.amcharts.model.charts.GanttChart;
import com.haulmont.cuba.gui.components.AbstractWindow;
import com.haulmont.cuba.gui.components.Component;
import com.haulmont.cuba.gui.components.SplitPanel;
import com.haulmont.cuba.gui.data.Datasource;
import com.haulmont.cuba.web.gui.components.WebComponentsHelper;
import com.haulmont.cuba.web.gui.components.WebSplitPanel;
import com.vaadin.ui.Calendar;
import com.vaadin.ui.ComponentContainer;

import local.paxbase.entity.coredata.Site;

public class Campaingngantt extends AbstractWindow {
	
    @Inject
    private Datasource<Site> sitesDs;
	
	@Override
    public void init(Map<String, Object> params) {
        super.init(params);
        
        ComponentContainer cc = (ComponentContainer) WebComponentsHelper.unwrap(this.getComponent("splitBox"));
        
        GanttChart gantt = new GanttChart();
        List<String> fields = new ArrayList<String>();
        fields.add("campaigns");
        gantt.setAdditionalSegmentFields(fields);
        gantt.setBalloonDateFormat("NN:JJ");
        gantt.setBrightnessStep(7);
        gantt.setCategoryField("siteName");
        gantt.setColumnWidth(0.5);
        
        //datasource="sitesDs"
        gantt.setEndDateField("endDate");
        
        //height="100%"
        gantt.setMarginRight(70);
        gantt.setPeriod(DatePeriod.DAYS);
        gantt.setResponsive(new Responsive().setEnabled(true));
        gantt.setRotate(true);
        gantt.setSegmentsField("campaigns");
        gantt.setStartDate(new GregorianCalendar(2017, 1, 1).getTime());
        gantt.setStartDateField("beginDate");
        gantt.setTheme(ChartTheme.LIGHT);
        
        //cc.addComponent((com.vaadin.ui.Component) gantt);
        
       
	}
	
	

	
}