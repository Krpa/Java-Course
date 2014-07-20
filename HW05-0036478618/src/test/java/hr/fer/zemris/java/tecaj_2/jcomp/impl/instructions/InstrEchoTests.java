package hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgument;
import hr.fer.zemris.java.tecaj_2.jcomp.Registers;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class InstrEchoTests {
	
	@Test
	public void testInstrEcho() {
		Computer c = mock(Computer.class);
		Registers r = mock(Registers.class);
		
		when(c.getRegisters()).thenReturn(r);
		when(r.getRegisterValue(0)).thenReturn(1);
		
		List<InstructionArgument> lista = new ArrayList<>();
		lista.add(new RegisterArgument(0));
		
		InstrEcho inst = new InstrEcho(lista);
		boolean b = inst.execute(c);
		
		assertEquals("execute mora vratiti false da se ne prekine rad procesora", false, b);
		verify(c, times(1)).getRegisters();
		verify(r, times(1)).getRegisterValue(0);;
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInstrEchoException() {
		List<InstructionArgument> lista = new ArrayList<>();
		lista.add(new RegisterArgument(0));
		lista.add(new RegisterArgument(1));
		
		new InstrEcho(lista);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInstrDecException2() {
		List<InstructionArgument> lista = new ArrayList<>();
		lista.add(new StringArgument("bla"));
		
		new InstrEcho(lista);
	}

}
