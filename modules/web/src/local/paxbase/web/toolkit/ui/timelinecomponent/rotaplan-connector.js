local_paxbase_web_toolkit_ui_timelinecomponent_RotaplanComponent = function() {
	var connector = this;
	var element = connector.getElement();
	$(element)
			.html(
					
							 "<div>"
							+ "<ul id='siteDuties' class='siteItems'/>"
							+ "<ul id='standardDuties' class='items'/>"
							+ "<button id='genSites'>Sites generieren</button>"
							+ "</div>"
							+ "<div class='cbTimeline' height='600px' overflow-y='scroll' overflow-x='hidden' id='visualization'/>"
							);
	$(element).css("padding", "5px 10px");
	$(element).css("width", "95%");

	var container = document.getElementById('visualization');

	// specify options
	var options = {

			locale: 'de',
		stack: true,
		start: new Date(),
		end: new Date(1000 * 60 * 60 * 24 * 14 + (new Date()).valueOf()),
		editable: true,
		orientation: 'top',
		width: '1800px', // 100% funktioniert nicht, auf der Komponente
							// gesetzt, verschwindet dass nach itemAdded
		groupOrder: function(a, b) {
			if (a.content < b.content)
				return -1;
			else if (a.content == b.content)
				return 0;
			else
				return 1;
		},
		onAdd : function(item, callback) {
			if (item) {
				// item.content = value;
				var newItem = {};
				newItem.start = item.start;
				newItem.end = item.end;
				newItem.content = item.content;
				newItem.group = item.group;
				connector.itemAdded(newItem);
				//callback(null); // send back adjusted new item 0918
			} else {
				callback(null); // cancel item creation
			}
		},

		onMove : function(item, callback) {
			connector.itemMoved(item);
			callback(item); // send back item as confirmation (can be
			// changed)

		},
		onRemove : function(item, callback) {
			if (item) {
				connector.itemDeleted(item);
				callback(item);
			} else {
				callback(null);
			}
		},

		onDropObjectOnItemX : function(objectData, item) {
			if (item) {
				// item.content = value;
				var newItem = {};
				newItem.start = item.start;
				newItem.end = item.end;
				newItem.content = item.content;
				newItem.group = item.group;
				connector.itemAdded(newItem);
			} 
		}
	
		
	};

	// neue Daten vom Backend holen, wird vom Backend initiiert
	this.onStateChange = function() {

		var state = connector.getState();

		timeline.setGroups(new vis.DataSet(this.getState().timelineGroups));
		timeline.setItems(new vis.DataSet(this.getState().timelineItems));
		createSiteItemList();
		createStandardDuties();
	}

	// Create a Timeline

	var timeline = new vis.Timeline(container);
	timeline.setOptions(options);
	timeline.setGroups(new vis.DataSet(this.getState().timelineGroups));
	timeline.setItems(new vis.DataSet(this.getState().timelineItems));

	timeline.on('doubleClick', function(props) {
		if (props.what = "item" && props.item) {
			connector.editItem(props.item);
		} else if (props.what = "background" && props.group) {
			var newItem = {};
			newItem.start = props.time;
			newItem.group = props.group;
			connector.itemAdded(newItem);
		} else {
			var newItem = {};
			newItem.start = props.time;
			connector.itemAdded(newItem);
		}
		console.log(props);
		props.event.preventDefault();
	});

	function createSiteItemList() {

		var siteDutyNode = document.getElementById("siteDuties");
		while (siteDutyNode.firstChild) {
			siteDutyNode.removeChild(siteDutyNode.firstChild);
		}
		var state = connector.getState();
		var siteItems = state.siteItems;
		for (var i = siteItems.length - 1; i >= 0; i--) {
			var node = document.createElement("LI"); // Create a <li> node
			node.setAttribute("draggable", "true");
			node.setAttribute("class", "siteItem");
			node.setAttribute("style", "background-color: "
					+ siteItems[i].color);
			node.addEventListener('dragstart', handleDragStart.bind(this),
					false);
			var textnode = document.createTextNode(siteItems[i].siteName);
			node.appendChild(textnode); // Append the text to <li>
			document.getElementById("siteDuties").appendChild(node);
		}
	}
	function createStandardDuties(){
		var standardDutyNode = document.getElementById("standardDuties");
		while (standardDutyNode.firstChild) {
			standardDutyNode.removeChild(standardDutyNode.firstChild);
		}
		var state = connector.getState();
		var standardDutyItems = state.standardDutyItems;
		for (var i = standardDutyItems.length - 1; i >= 0; i--) {
			var node = document.createElement("LI"); // Create a <li> node
			node.setAttribute("draggable", "true");
			node.setAttribute("class", "siteItem");
			//node.setAttribute("style", "background-color: " + standardDutyItems[i].color);
			node.addEventListener('dragstart', handleDragStart.bind(this),
					false);
			var textnode = document.createTextNode(standardDutyItems[i].categoryName);
			node.appendChild(textnode); // Append the text to <li>
			document.getElementById("standardDuties").appendChild(node);
		}
	}
	
	function handleDragStart(event) {
		dragSrcEl = event.target;

		event.dataTransfer.effectAllowed = 'move';
		var itemType = "range"
		var item = {
			id : new Date(),
			type : itemType,
			target:'item',
			content : event.target.innerHTML.trim()
		};
		event.dataTransfer.setData("text", JSON.stringify(item));
	}

	$("#genSites").click(createSiteItemList);
	var items = document.querySelectorAll('.items .item .siteItems');

	// an jedes Item den DragHandler setzen, damit dieses vorm Hinzufügen
	// parametrisiert werden kann
	for (var i = items.length - 1; i >= 0; i--) {
		var item = items[i];
		item.addEventListener('dragstart', handleDragStart.bind(this), false);
	}

}