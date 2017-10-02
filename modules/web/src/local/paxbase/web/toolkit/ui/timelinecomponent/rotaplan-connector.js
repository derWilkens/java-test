local_paxbase_web_toolkit_ui_timelinecomponent_RotaplanComponent = function() {
	var connector = this;
	var element = connector.getElement();
	$(element).html(
			"<div id='visualization'/>" 
					+ "<div style='padding: 5px 10px'>"
					+ "<h3>Items:</h3>" 
					+ "<ul class='items'>"
					+ "<li draggable='true' class='item'>"
					+ "Offshore " 
					+ "</li>"
					+ "<li draggable='true' class='item'>" 
					+ "Onshore"
					+ "</li>" 
					+ "<li draggable='true' class='item'>"
					+ "Offshore-Frei" 
					+ "</li>"
					+ "<li draggable='true' class='item'>"
					+ "Abwesend"
					+ "</li>" 
					+ "</ul>"
					+ "</div>");
	$(element).css("padding", "5px 10px");
	$(element).css("width", "1000");
	$(element).css("height", "500");
	
	var container = document.getElementById('visualization');

	// specify options
	var options = {
		stack : true,
		start : new Date(),
		end : new Date(1000 * 60 * 60 * 24 * 14 + (new Date()).valueOf()),
		editable : true,
		orientation : 'top',
		width : "100%",

		onAdd: function (item, callback) {
				if (item) {
		            //item.content = value;
					var newItem = {};
					newItem.start = item.start;
					newItem.end = item.end;
					newItem.content = item.content;
					newItem.group = item.group;
					connector.itemAdded(newItem);
		            //callback(item); // send back adjusted new item
		          }
		          else {
		            callback(null); // cancel item creation
		          }
		      },

		      onMove: function (item, callback) {
		    	  	connector.itemMoved(item);
		            callback(item); // send back item as confirmation (can be
									// changed)
	
		      }

	};

	//neue Daten vom Backend holen, wird vom Backend initiiert
	this.onStateChange = function() {

		var state = connector.getState();

		timeline.setGroups(new vis.DataSet(this.getState().timelineGroups));
		timeline.setItems(new vis.DataSet(this.getState().timelineItems));
	}
	
	// Create a Timeline

	var timeline = new vis.Timeline(container);
	timeline.setOptions(options);
	timeline.setGroups(new vis.DataSet(this.getState().timelineGroups));
	timeline.setItems(new vis.DataSet(this.getState().timelineItems));

	function handleDragStart(event) {
		dragSrcEl = event.target;

		event.dataTransfer.effectAllowed = 'move';
		var itemType = "range"
		var item = {
			id : new Date(),
			type : itemType,
			content : event.target.innerHTML.split('-')[0].trim()
			//,
			//start : new Date(),
			//end : new Date(1000 * 60 * 60 * 24 * 7 + (new Date()).valueOf())
		};
		event.dataTransfer.setData("text", JSON.stringify(item));
	}

	var items = document.querySelectorAll('.items .item');

	//an jedes Item den DragHandler setzen, damit dieses vorm Hinzufügen parametrisiert werden kann 
	for (var i = items.length - 1; i >= 0; i--) {
		var item = items[i];
		item.addEventListener('dragstart', handleDragStart.bind(this), false);
	}

}