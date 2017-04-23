package local.paxbase.entity.dto;

import java.util.function.Function;

public class TimelineConfig {

	private Function<?, String> groupFunction;
	private Function<?, String> parentGroupIdFunction;
	private Function<?, String> itemLabelFunction;
	
	public Function<?, String> getParentGroupIdFunction() {
		return parentGroupIdFunction;
	}
	
	public void setParentGroupIdFunction(Function<?, String> parentGroupIdFunction) {
		this.parentGroupIdFunction = parentGroupIdFunction;
	}

	public Function<?, String> getGroupFunction() {
		return groupFunction;
	}
	
	public void setGroupFunction(Function<?, String> groupFunction) {
		this.groupFunction = groupFunction;
	}

	public Function<?, String> getItemLabelFunction() {
		return itemLabelFunction;
	}
	public void setItemLabelFunction(Function<?, String> itemLabelFunction) {
		this.itemLabelFunction = itemLabelFunction;
	}
	
}
