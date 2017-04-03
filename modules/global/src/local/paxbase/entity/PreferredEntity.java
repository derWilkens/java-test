package local.paxbase.entity;

import java.util.UUID;

public interface PreferredEntity {
	
	public String getGroupLabel(GroupedBy groupedBy);
	public UUID getGroupId(GroupedBy groupedBy);
	public UUID getPreferredUUID();
	public String getLabel();
}
