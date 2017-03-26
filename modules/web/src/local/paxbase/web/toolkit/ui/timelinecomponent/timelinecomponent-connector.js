local_paxbase_web_toolkit_ui_timelinecomponent_TimelineComponent = function() {
	var connector = this;
	var element = connector.getElement();
	$(element)
			.html(
					"<div id='visualization' style='width:1000px; height:400px;'></div>");
	$(element).css("padding", "5px 10px");

	var container = document.getElementById('visualization');

	this.onStateChange = function() {
		var state = connector.getState();
		var data = state.dataAttributes;
		var start = state.start;
		var end =state.end;
		var format = state.format;

	}
	
	var items = new vis.DataSet(this.getState().durationList);
	

	// Configuration for the Timeline
	var options = {start:this.getState().start,end:this.getState().end};

	// Create a Timeline
	var timeline = new vis.Timeline(container, items, options);
}