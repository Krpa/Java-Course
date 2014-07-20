package hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgument;
import hr.fer.zemris.java.tecaj_2.jcomp.Registers;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;


public class InstrIncrementTests {
	
	@Test
	public void testInstrIncrement() {
		Computer c = mock(Computer.class);
		Registers r = mock(Registers.class);
		
		when(c.getRegisters()).thenReturn(r);
		when(r.getRegisterValue(0)).thenReturn(1);
		
		List<InstructionArgument> lista = new ArrayList<>();
		lista.add(new RegisterArgument(0));
		
		InstrIncrement inst = new InstrIncrement(lista);
		boolean b = inst.execute(c);

		assertEquals("execute mora vratiti false da se ne prekine rad procesora", false, b);
		verify(c, times(2)).getRegisters();
		verify(r, times(1)).getRegisterValue(0);
		verify(r, times(1)).setRegisterValue(0, 2);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInstrIncException() {
		List<InstructionArgument> lista = new ArrayList<>();
		lista.add(new RegisterArgument(0));
		lista.add(new RegisterArgument(1));
		
		new InstrIncrement(lista);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInstrIncException2() {
		List<InstructionArgument> lista = new ArrayList<>();
		lista.add(new StringArgument("bla"));
		
		new InstrIncrement(lista);
	}
}
