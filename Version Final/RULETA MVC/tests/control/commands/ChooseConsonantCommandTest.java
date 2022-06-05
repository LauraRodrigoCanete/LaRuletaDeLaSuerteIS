package control.commands;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import control.commands.ChooseConsonantCommand;

public class ChooseConsonantCommandTest {

	@Test
	void error_handling() {
		ChooseConsonantCommand c = new ChooseConsonantCommand();
		//la letra debe ser una consonante
		String[] input = {"consonante", "a"};
		assertThrows(IllegalArgumentException.class, () -> c.parse(input));
	}
}
