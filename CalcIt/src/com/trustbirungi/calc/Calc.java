package com.trustbirungi.calc;

import javax.swing.JFrame;

public class Calc {
	public static void main(String[] args) {
		JFrame window = new CalcGUI();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
	}
}
