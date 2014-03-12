package com.trustbirungi.calc;

public class CalcLogic {
	// -- Instance variables.
	private int m_currentTotal; // The current total is all we need to remember.

	/** Constructor */
	public CalcLogic() {
		m_currentTotal = 0;
	}

	public String getTotalString() {
		return "" + m_currentTotal;
	}

	public void setTotal(String n) {
		m_currentTotal = convertToNumber(n);
	}

	public void add(String n) {
		m_currentTotal += convertToNumber(n);
	}

	public void subtract(String n) {
		m_currentTotal -= convertToNumber(n);
	}

	public void multiply(String n) {
		m_currentTotal *= convertToNumber(n);
	}

	public void divide(String n) {
		m_currentTotal /= convertToNumber(n);
	}

	private int convertToNumber(String n) {
		return Integer.parseInt(n);
	}
}
