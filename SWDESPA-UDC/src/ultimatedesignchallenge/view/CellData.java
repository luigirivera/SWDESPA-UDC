package ultimatedesignchallenge.view;

public class CellData {
	private int day;
	private int row;
	private int col;

	public CellData(int day, int row, int col) {
		this.day = day;
		this.row = row;
		this.col = col;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}
	
	public boolean isAt(int row, int col) {
		return this.row==row && this.col==col;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof CellData)
			return this.day == ((CellData) o).getDay() && this.row == ((CellData) o).getRow()
					&& this.col == ((CellData) o).getCol();
		else
			return false;
	}
}
