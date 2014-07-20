package hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions;

import static org.mockito.Mockito.*;
import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgument;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import static org.junit.Assert.*;

public class InstrHaltTests {

	@Test
	public void testInstrDecrement() {
		Computer c = mock(Computer.class);
		List<InstructionArgument> lista = new ArrayList<>();
		
		InstrHalt inst = new InstrHalt(lista);
		boolean b = inst.execute(c);
		assertEquals("execute mora vratiti true da se prekine rad procesora", true, b);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInstrDecException() {
		List<InstructionArgument> lista = new ArrayList<>();
		lista.add(new RegisterArgument(0));
		
		new InstrHalt(lista);
	}

	
}
