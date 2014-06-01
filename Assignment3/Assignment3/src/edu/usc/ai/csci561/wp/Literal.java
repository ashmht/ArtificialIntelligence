package edu.usc.ai.csci561.wp;

public class Literal {
	private Guest guest;
	private Table table;
	private Boolean isNegated;

	// Xmn
	public Literal() {
		guest = new Guest();
		table = new Table();
	}

	public Literal(int guestNo, int tableNo, boolean isNegated) {
		//
		guest = new Guest();
		table = new Table();

		guest.setId(guestNo);
		table.setId(tableNo);
		this.isNegated = isNegated;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((guest == null) ? 0 : guest.hashCode());
		result = prime * result
				+ ((isNegated == null) ? 0 : isNegated.hashCode());
		result = prime * result + ((table == null) ? 0 : table.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Literal other = (Literal) obj;
		if (this.guest.getId() == other.guest.getId()
				&& this.table.getId() == other.table.getId()) {
			if (this.isNegated == other.isNegated) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		int tableId = table.getId() + 1;
		int guestId = guest.getId() + 1;
		if (!isNegated) {

			return "X" + guestId + tableId;
		} else
			return "-X" + guestId + tableId;
	}

	public boolean isNegatedLiteral() {
		return isNegated;
	}

	public boolean resolve(Literal l) {
		if (this.guest.getId() == l.guest.getId()
				&& this.table.getId() == l.table.getId()
				&& this.isNegated != l.isNegated) {
			return true;
		}
		return false;
	}

	public boolean resolve(Literal l1, Literal l2) {
		if (l1.guest.getId() == l2.guest.getId()
				&& l1.table.getId() == l2.table.getId()
				&& l1.isNegated != l2.isNegated) {
			return true;
		}
		return false;
	}
}
