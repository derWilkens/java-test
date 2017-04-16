package local.paxbase.entity.dto;

import java.util.function.Function;

public class TimelineConfig {

	private Function<?, String> groupFunction;
	private Function<?, String> nestedGroupFunction;
	private Function<?, String> itemLabelFunction;
	
	public Function<?, String> getGroupFunction() {
		return groupFunction;
	}
	public void setGroupFunction(Function<?, String> groupFunction) {
		this.groupFunction = groupFunction;
	}
	public Function<?, String> getNestedGroupFunction() {
		return nestedGroupFunction;
	}
	public void setNestedGroupFunction(Function<?, String> nestedGroupFunction) {
		this.nestedGroupFunction = nestedGroupFunction;
	}
	public Function<?, String> getItemLabelFunction() {
		return itemLabelFunction;
	}
	public void setItemLabelFunction(Function<?, String> itemLabelFunction) {
		this.itemLabelFunction = itemLabelFunction;
	}
	
}
