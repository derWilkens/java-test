package local.paxbase.entity.period;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.haulmont.chile.core.annotations.NamePattern;

@NamePattern(" - , |")
@Table(name = "PAXBASE_ATTENDENCE_PERIOD")
@Entity(name = "paxbase$AttendencePeriod")
public class AttendencePeriod extends ShiftPeriod {
	private static final long serialVersionUID = 848103393103367871L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "OPERATION_PERIOD_ID")
	protected OperationPeriod operationPeriod;

	public OperationPeriod getOperationPeriod() {
		return operationPeriod;
	}

	public void setOperationPeriod(OperationPeriod operationPeriod) {
		this.operationPeriod = operationPeriod;
	}

}