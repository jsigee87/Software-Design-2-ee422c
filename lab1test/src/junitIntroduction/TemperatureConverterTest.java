package junitIntroduction;

import static org.junit.Assert.*;


import org.junit.Test;

public class TemperatureConverterTest {

	double epsilon = 0.01;
	
	@Test
	public void testZeroCelsiusToFahrenheit() {
		double c = 0;
		double f = 32;
		assertEquals(f, TemperatureConverter.celsiusToFahrenheit(c), epsilon);
	}
	
	@Test
	public void testPositiveCelsiusToFahrenheit() {
		double c = 100;
		double f = 212;
		assertEquals(f, TemperatureConverter.celsiusToFahrenheit(c), epsilon);
	}
	
	@Test
	public void testNegativeCelciusToFahrenheit() {
		double c = -15;
		double f = 5;
		assertEquals(f, TemperatureConverter.celsiusToFahrenheit(c), epsilon);
	}
	
	@Test
	public void testZeroFahrenheitToCelsius() {
		double c = -17.778;
		double f = 0;
		assertEquals(c, TemperatureConverter.fahrenheitToCelsius(f), epsilon);
	}

	@Test
	public void testPositiveFahrenheitToCelcius() {
		double c = 100;
		double f = 212;
		assertEquals(c, TemperatureConverter.fahrenheitToCelsius(f), epsilon);
	}
	
	@Test
	public void testNegativeFahrenheitToCelcius() {
		double c = -30;
		double f = -22;
		assertEquals(c, TemperatureConverter.fahrenheitToCelsius(f), epsilon);
	}
}
