package control.commands;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import control.commands.BuyVowelCommand;

public class BuyVowelCommandTest {
	@Test
	void error_handling() {
		BuyVowelCommand c = new BuyVowelCommand();
		//la letra debe ser una vocal
		String[] input = {"comprar", "j"};
		assertThrows(IllegalArgumentException.class, () -> c.parse(input));
		
		
	}
}
