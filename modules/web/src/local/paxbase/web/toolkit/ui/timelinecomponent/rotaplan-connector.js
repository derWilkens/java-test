local_paxbase_web_toolkit_ui_timelinecomponent_RotaplanComponent  = function() {
	var connector = this;
	var element = connector.getElement();
	$(element)
			.html(
					"<div id='visualization'/>" +
					"<div>" +
					"<h3>Items:</h3>" +
					"<ul class='items'>" +
					"<li draggable='true' class='item'>" +
					"item 1 - box 2125" +
					"</li>" +
					"<li draggable='true' class='item'>" +
					"item 2 - point" +
					"</li>" +
					"<li draggable='true' class='item'>" +
					"item 3 - range" +
					"</li>" +
					"<li draggable='true' class='item'>" +
					"item 3 - range - fixed times - <br>" +
					"(start: now, end: now + 10 min) " +
					"</li>" +
					"</ul>" +
					"</div>" 
					);
	$(element).css("padding", "5px 10px");
	$(element).css("width", "100%");
	var container = document.getElementById('visualization');
	  
	// specify options
	  var options = {
	    stack: true,
	    start: new Date(),
	    end: new Date(1000*60*60*24*14 + (new Date()).valueOf()),
	    editable: true,
	    orientation: 'top',
	    width: "70%"
	  };

	this.onStateChange = function() {
		
		var state = connector.getState();
				
		timeline.setGroups(new vis.DataSet(this.getState().timelineGroups));
		timeline.setItems(new vis.DataSet(this.getState().timelineItems));
	}


	// Configuration for the Timeline
	//var options = {start:this.getState().start,end:this.getState().end};
	//var options = {
	//		    groupOrder: 'content'  // groupOrder can be a property name or a sorting function
	//		  };

	// Create a Timeline
	
	  var timeline = new vis.Timeline(container);
	  timeline.setOptions(options);
	  timeline.setGroups(new vis.DataSet(this.getState().timelineGroups));
	  timeline.setItems(new vis.DataSet(this.getState().timelineItems));
	  
	  timeline.on('drop', handleDrop);
	  
	  //timeline.addEventListener('drop',handleDrop.bind(this), false);
	  
	  function handleDrop(properties) {
		  alert('selected items: ' + properties.items);
	  }
	  
	  function handleDragStart(event) {
		    dragSrcEl = event.target;

		    event.dataTransfer.effectAllowed = 'move';
		    var itemType = event.target.innerHTML.split('-')[1].trim();
		    var item = {
		      id: new Date(),
		      type: itemType,
		      content: event.target.innerHTML.split('-')[0].trim()
		    };

		    var isFixedTimes = (event.target.innerHTML.split('-')[2] && event.target.innerHTML.split('-')[2].trim() == 'fixed times')
		    if (isFixedTimes) {
		      item.start = new Date();
		      item.end = new Date(1000*60*10 + (new Date()).valueOf());
		    }
		    //connector.valueChanged(item);
		    event.dataTransfer.setData("text", JSON.stringify(item));
		    
		  }

		  var items = document.querySelectorAll('.items .item');

		  for (var i = items.length - 1; i >= 0; i--) {
		    var item = items[i];
		    item.addEventListener('dragstart', handleDragStart.bind(this), false);
		  }
		  
		  
}