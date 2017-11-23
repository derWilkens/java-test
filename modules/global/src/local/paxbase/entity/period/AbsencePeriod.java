package local.paxbase.entity.period;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.haulmont.chile.core.annotations.NamePattern;

@NamePattern(" - , |")
@Table(name = "PAXBASE_ABSENCE_PERIOD")
@Entity(name = "paxbase$AbsencePeriod")
public class AbsencePeriod extends ShiftPeriod {
	private static final long serialVersionUID = 848103393103367871L;

}